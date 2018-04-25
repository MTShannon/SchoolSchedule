package markshannon.android.schedule.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.UUID;

import markshannon.android.schedule.Schedule;
import markshannon.android.schedule.database.ScheduleDbSchema.ScheduleTable;

/**
 * Created by mshan on 1/22/2018.
 */

// CursorWrapper lets you wrap a Cursor you received from another place
// and add new methods on top of it
public class ScheduleCursorWrapper extends CursorWrapper {

    private final static String DBTAG = "DBTAG";
    public ScheduleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // pulls out relevant column data
    public Schedule getSchedule() {
        String uuidString = getString(getColumnIndex(ScheduleTable.Cols.UUID));
        String personName = getString(getColumnIndex(ScheduleTable.Cols.PERSON));
        String teacher = getString(getColumnIndex(ScheduleTable.Cols.TEACHER));
        String location = getString(getColumnIndex(ScheduleTable.Cols.LOCATION));
        String time = getString(getColumnIndex(ScheduleTable.Cols.TIME));
        String days = getString(getColumnIndex(ScheduleTable.Cols.DAYS));


        Schedule schedule = new Schedule(UUID.fromString(uuidString));
        Log.i(DBTAG, personName + ", " + teacher + ", " + location);
        schedule.setName(personName);
        schedule.setTeacher(teacher);
        schedule.setLocation(location);
        schedule.setTime(time);
        schedule.setDays(days);
        return schedule;
    }
}
