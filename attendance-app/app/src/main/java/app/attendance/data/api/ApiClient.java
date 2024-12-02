package app.attendance.data.api;

import java.util.List;
import java.util.Map;

import app.attendance.data.api.request.LoginRequest;
import app.attendance.data.api.response.LoginResponse;
import app.attendance.data.api.response.SuccessResponse;
import app.attendance.data.models.Attendance;
import app.attendance.data.models.StudentCourse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiClient {
    @POST("api/auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/courses")
    Call<List<StudentCourse>> courses();

    @POST("api/mark-attendance")
    Call<SuccessResponse> markAttendance(@Body Map<String, String> params);

    @GET
    Call<List<Attendance>> getAttendance(@Url String url);
}
