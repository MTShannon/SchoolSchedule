package markshannon.android.schedule;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mshan on 12/28/2017.
 */

public class Schedule {

    public Schedule() {
        this(UUID.randomUUID());
    }

    // constructor used when reading schedule from database
    public Schedule(UUID id) {
        mId = id;

    }

    private String name;
    private UUID mId;
    private String teacher;
    private String location;
    private String time;
    private String days;

    private final static String TAG = "DateDisplayed";

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String formatDetails() {
        return (location + ": " + time + ", " + days);
    }



}
