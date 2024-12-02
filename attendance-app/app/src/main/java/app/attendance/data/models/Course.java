package app.attendance.data.models;

import java.util.Locale;

import app.attendance.utils.DateTimeUtils;

public class Course {
    private String id;
    private String semester;
    private String year;
    private String classStartTime;
    private String classEndTime;
    private Subject subject;
    private Instructor instructor;

    public String getId() {
        return id;
    }

    public String getSemester() {
        return String.format(Locale.getDefault(), "%s %s", semester, year);
    }

    public String getClassStartTime() {
        String dateTime = DateTimeUtils.convertTimeToLocalTimezone(classStartTime);
        if (dateTime != null) {
            return DateTimeUtils.formatDateTime(dateTime, "hh:mm a");
        }
        return classStartTime.split("T")[1].replace(".000Z", "");
    }

    public String getClassEndTime() {
        String dateTime = DateTimeUtils.convertTimeToLocalTimezone(classEndTime);
        if (dateTime != null) {
            return DateTimeUtils.formatDateTime(dateTime, "hh:mm a");
        }
        return classEndTime.split("T")[1].replace(".000Z", "");
    }

    public Subject getSubject() {
        return subject;
    }

    public String getInstructorName() {
        return instructor != null ? instructor.getFullName() : "Not Assigned";
    }
}
