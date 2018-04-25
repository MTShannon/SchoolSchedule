package markshannon.android.schedule.database;

/**
 * Created by mshan on 1/22/2018.
 */

public class ScheduleDbSchema {

    // exists to define string constants needed to describe pieces of the table definition
    public static final class ScheduleTable {
        public static final String NAME = "schedules";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PERSON = "title";
            public static final String TEACHER = "teacher";
            public static final String LOCATION = "location";
            public static final String TIME = "time";
            public static final String DAYS = "days";
        }
    }
}
