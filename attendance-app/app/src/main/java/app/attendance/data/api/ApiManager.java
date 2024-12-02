package app.attendance.data.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import app.attendance.R;
import app.attendance.data.PreferencesManager;
import app.attendance.data.api.request.LoginRequest;
import app.attendance.data.api.response.ErrorResponse;
import app.attendance.data.api.response.LoginResponse;
import app.attendance.data.api.response.SuccessResponse;
import app.attendance.data.models.Attendance;
import app.attendance.data.models.StudentCourse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    private static final String TAG = ApiManager.class.getSimpleName();

    private final Context context;

    private final PreferencesManager preferencesManager;
    private final ApiClient client;

    public ApiManager(Context context, ApiClient client, PreferencesManager preferencesManager) {
        this.context = context;
        this.client = client;
        this.preferencesManager = preferencesManager;
    }

    private <T> ErrorResponse getErrorBody(Response<T> response) {
        try(ResponseBody body = response.errorBody()) {
            return new Gson().fromJson(body.string(), ErrorResponse.class);
        } catch (Exception e) {
            return new ErrorResponse();
        }
    }

    public void login(String email, String password, ApiCallback<String> callback) {
        client.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    LoginResponse body = response.body();
                    preferencesManager.setAccessToken(body.getToken());
                    preferencesManager.setUser(body.getUser());
                    callback.onSuccess(context.getString(R.string.logged_in));
                } else {
                    ErrorResponse error = getErrorBody(response);
                    callback.onError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
                callback.onError(context.getString(R.string.no_internet_connection));
            }
        });
    }

    public void getEnrolledCourses(ApiCallback<List<StudentCourse>> callback) {
        client.courses().enqueue(new Callback<List<StudentCourse>>() {
            @Override
            public void onResponse(Call<List<StudentCourse>> call, Response<List<StudentCourse>> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    ErrorResponse error = getErrorBody(response);
                    callback.onError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<StudentCourse>> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
                callback.onError(context.getString(R.string.no_internet_connection));
            }
        });
    }

    public void markAttendance(String code, ApiCallback<String> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);

        client.markAttendance(params).enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    ErrorResponse error = getErrorBody(response);
                    callback.onError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
                callback.onError(context.getString(R.string.no_internet_connection));
            }
        });
    }

    public void getAttendance(String courseId, ApiCallback<List<Attendance>> apiCallback) {
        String url = String.format("/api/courses/%s/attendance", courseId);
        client.getAttendance(url).enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                if(response.isSuccessful()) {
                    apiCallback.onSuccess(response.body());
                } else {
                    ErrorResponse error = getErrorBody(response);
                    apiCallback.onError(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
                apiCallback.onError(context.getString(R.string.no_internet_connection));
            }
        });
    }

    public interface ApiCallback<T> {
        void onSuccess(T response);
        void onError(String message);
    }
}
