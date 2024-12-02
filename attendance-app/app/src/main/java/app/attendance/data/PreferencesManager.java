package app.attendance.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import app.attendance.data.models.User;

public class PreferencesManager {

    private final SharedPreferences preferences;

    public PreferencesManager(Context ctx) {
        preferences = ctx.getSharedPreferences("app.attendance", Context.MODE_PRIVATE);
    }

    public void setAccessToken(String token) {
        preferences.edit().putString("access_token", token).apply();
    }

    public String getAccessToken() {
        return preferences.getString("access_token", null);
    }

    public void setUser(User user) {
        String json = new Gson().toJson(user);
        preferences.edit().putString("user", json).apply();
    }

    public User getUser() {
        String json = preferences.getString("user", null);
        return new Gson().fromJson(json, User.class);
    }

    public boolean isLoggedIn() {
        return getAccessToken() != null && getUser() != null;
    }

    public void logout() {
        preferences.edit().clear().apply();
    }
}
