package app.attendance.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Objects;

import app.attendance.AppController;
import app.attendance.R;
import app.attendance.data.api.ApiManager;
import app.attendance.data.models.Attendance;
import app.attendance.databinding.ActivityAttendanceBinding;
import app.attendance.ui.adapter.AttendanceAdapter;

public class AttendanceActivity extends BaseActivity {
    private ActivityAttendanceBinding binding;

    private AttendanceAdapter adapter;

    private String courseId;

    public static void start(Context context, String title, String courseId) {
        Intent intent = new Intent(context, AttendanceActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("courseId", courseId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        courseId = getIntent().getStringExtra("courseId");

        if(courseId == null) {
            showToast("Course ID is required");
            finish();
            return;
        }

        setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        adapter = new AttendanceAdapter();
        binding.list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.list.setAdapter(adapter);

        getAttendance(courseId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attendance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            getAttendance(courseId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAttendance(String courseId) {
        showProgress();
        AppController.getApiManager().getAttendance(courseId, new ApiManager.ApiCallback<List<Attendance>>() {
            @Override
            public void onSuccess(List<Attendance> data) {
                hideProgress();
                adapter.clear();
                if (data.isEmpty()) {
                    binding.emptyView.setVisibility(View.VISIBLE);
                    binding.list.setVisibility(View.GONE);
                } else {
                    binding.emptyView.setVisibility(View.GONE);
                    binding.list.setVisibility(View.VISIBLE);
                    adapter.addAll(data);
                }
            }

            @Override
            public void onError(String message) {
                hideProgress();
                showErrorAlert(message);
            }
        });
    }
}
