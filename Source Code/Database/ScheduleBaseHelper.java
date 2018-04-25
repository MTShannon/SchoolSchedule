package markshannon.android.schedule.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import markshannon.android.schedule.database.ScheduleDbSchema.ScheduleTable;

/**
 * Created by mshan on 1/22/2018.
 */

// class helps with opening the database file by handing if it alread exists, creating it, check the
// version, upgrade version, etc.
public class ScheduleBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "scheduleBase.db";

    public ScheduleBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table definition code
        db.execSQL("create table " + ScheduleTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            ScheduleTable.Cols.UUID + ", " +
            ScheduleTable.Cols.PERSON + ", " +
            ScheduleTable.Cols.TEACHER + ", " +
            ScheduleTable.Cols.LOCATION + ", " +
            ScheduleTable.Cols.TIME + ", " +
            ScheduleTable.Cols.DAYS +
            ")"
        );
    }

    // can ignore for now because ScheduleReminder will have just 1 version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
