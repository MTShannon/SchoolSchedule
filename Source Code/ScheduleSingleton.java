package markshannon.android.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import markshannon.android.schedule.database.ScheduleBaseHelper;
import markshannon.android.schedule.database.ScheduleCursorWrapper;
import markshannon.android.schedule.database.ScheduleDbSchema;
import markshannon.android.schedule.database.ScheduleDbSchema.ScheduleTable;

/**
 * Created by mshan on 12/28/2017.
 */

//stores a list of the schedules, exists as long as application is in memory
public class ScheduleSingleton {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ScheduleSingleton sScheduleSingleton;

    private static final String TAGFILEPATH = "FilePath";



    public static ScheduleSingleton get(Context context) {
        if (sScheduleSingleton == null) {
            sScheduleSingleton = new ScheduleSingleton(context);
        }
        return sScheduleSingleton;
    }

    private ScheduleSingleton(Context context) {
        mContext = context.getApplicationContext();
        //When you call .getWritableDatabase(), CrimeBaseHelper creates a new database file if it DNE
        // it it's the first time the DB has been created calls onCreate(SQLite...), or calls
        // onUpgrade(SQLite... , int, int) depending on the version umbber
        mDatabase = new ScheduleBaseHelper(mContext).getWritableDatabase();

    }

    // adds a row to the database with a new schedule
    public void addSchedule(Schedule b) {

        ContentValues values = getContentValues(b);

        // first arg- table you want to insert to, last arg- data you want to put in
        mDatabase.insert(ScheduleTable.NAME, null, values);
    }

    // delete a schedule from the database
    public void deleteSchedule(Schedule b) {
        mDatabase.delete(ScheduleTable.NAME,
                ScheduleTable.Cols.UUID + " = ?",
                new String[] { b.getId().toString()});
    }

    public List<Schedule> getSchedules() {

        List<Schedule> schedules = new ArrayList<>();

        // (null, null) gets all rows?
        ScheduleCursorWrapper cursor = querySchedules(null, null);

        // go through the cursor, get a schedule, add it to the ArrayList, move cursor to next row data
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                schedules.add(cursor.getSchedule());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return schedules;
    }

    // returns a specific schedule
    public Schedule getSchedule(UUID id) {

        // search the database with a query for the id you're looking for
        ScheduleCursorWrapper cursor = querySchedules(
                ScheduleTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );


        try {
            // schedule with this id DNE
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSchedule();
        } finally {
            cursor.close();
        }

    }

    // update rows in the database when something is changed
    public void updateSchedule(Schedule schedule) {
        String uuidString = schedule.getId().toString();
        ContentValues values = getContentValues(schedule);

        // update(String, ContentValues, String, String[])
        // 1st arg (String) - table name you want to update
        // 2nd arg (ContentValues) - ContentValues you want to assign to each row you update
        // 3rd arg (String) - specify which row to update with a where clause
        // 4th arg (String[]) - specify values for the arguments in the where clause
        mDatabase.update(ScheduleTable.NAME, values,
                ScheduleTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }



    //read in data from SQLite
    private ScheduleCursorWrapper querySchedules(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ScheduleTable.NAME, // table to read from
                null, // columns - null selects all columns
                whereClause, // whereClause & args specify which row (UUID?)
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new ScheduleCursorWrapper(cursor);
    }

    // takes care of putting a Schedule into a ContentValues
    // ContentValues is a key-value store class, stores data
    private static ContentValues getContentValues(Schedule schedule) {
        // use column names as keys, _id is automatically created as a unique row ID
        ContentValues values = new ContentValues();
        values.put(ScheduleTable.Cols.UUID, schedule.getId().toString());
        values.put(ScheduleTable.Cols.PERSON, schedule.getName());
        values.put(ScheduleTable.Cols.TEACHER, schedule.getTeacher());
        values.put(ScheduleTable.Cols.LOCATION, schedule.getLocation());
        values.put(ScheduleTable.Cols.TIME, schedule.getTime());
        values.put(ScheduleTable.Cols.DAYS, schedule.getDays());

        return values;
    }


}



