package app.attendance.data.api.response;

import app.attendance.data.models.User;

public class LoginResponse {
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
