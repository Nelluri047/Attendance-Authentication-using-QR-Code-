package app.attendance.data.models;

public class Instructor {
    private String id;
    private User user;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return user.getFullName();
    }
}
