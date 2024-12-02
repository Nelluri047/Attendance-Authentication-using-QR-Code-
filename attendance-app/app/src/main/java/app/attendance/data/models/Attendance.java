package app.attendance.data.models;

import app.attendance.ui.adapter.CoursesAdapter;
import app.attendance.utils.DateTimeUtils;

public class Attendance {
    private String id;
    private String status;
    private String time;
    private CourseClass courseClass;

    public String getStatus() {
        if(status.equals("PRESENT")) {
            return "Present";
        } else if(status.equals("LATE")) {
            return "Late";
        } else {
            return "Absent";
        }
    }

    public String getTime() {
        String timeString = DateTimeUtils.convertTimeToLocalTimezone(time);
        return DateTimeUtils.formatDateTime(timeString, "hh:mm a");
    }

    public String getDate() {
        String timeString = DateTimeUtils.convertTimeToLocalTimezone(courseClass.getStartedAt());
        return DateTimeUtils.formatDateTime(timeString, "MMM dd, yyyy");
    }
}
