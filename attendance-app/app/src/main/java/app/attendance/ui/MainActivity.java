package app.attendance.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

import app.attendance.AppController;
import app.attendance.R;
import app.attendance.data.api.ApiManager;
import app.attendance.data.models.StudentCourse;
import app.attendance.data.models.User;
import app.attendance.databinding.ActivityMainBinding;
import app.attendance.ui.adapter.CoursesAdapter;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private CoursesAdapter adapter;

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    markAttendance(result.getContents());
                }
            });

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = AppController.getPreferencesManager().getUser();
        if (user == null) {
            LoginActivity.start(this);
            finish();
            return;
        }

        binding.studentName.setText(user.getFullName());
        binding.studentId.setText(user.getStudentId());

        adapter = new CoursesAdapter(studentCourse -> {
            AttendanceActivity.start(this,
                    studentCourse.getCourse().getSubject().getName(),
                    studentCourse.getCourse().getId());
        });

        binding.list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.list.setAdapter(adapter);

        binding.scanButton.setOnClickListener(v -> {
            ScanOptions scanOptions = new ScanOptions();
            scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            scanOptions.setPrompt(getString(R.string.scan_qr_code));
            scanOptions.setCameraId(0);
            scanOptions.setBeepEnabled(true);
            scanOptions.setBarcodeImageEnabled(false);
            scanOptions.setOrientationLocked(false);

            qrCodeLauncher.launch(scanOptions);
        });

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            showConfirmationDialog();
            return true;
        } else if(item.getItemId() == R.id.action_refresh) {
            loadData();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirmation)
                .setMessage(R.string.logout_confirmation)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    AppController.getPreferencesManager().logout();
                    LoginActivity.start(this);
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void loadData() {
        showProgress();
        AppController.getApiManager().getEnrolledCourses(new ApiManager.ApiCallback<List<StudentCourse>>() {
            @Override
            public void onSuccess(List<StudentCourse> data) {
                hideProgress();
                adapter.clear();
                if (data.isEmpty()) {
                    binding.emptyView.setVisibility(View.VISIBLE);
                    binding.listLayout.setVisibility(View.GONE);
                } else {
                    binding.emptyView.setVisibility(View.GONE);
                    binding.listLayout.setVisibility(View.VISIBLE);
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

    private void markAttendance(String qrCode) {
        showProgress();
        AppController.getApiManager().markAttendance(qrCode, new ApiManager.ApiCallback<String>() {
            @Override
            public void onSuccess(String  message) {
                hideProgress();
                showToast(message);
            }

            @Override
            public void onError(String message) {
                hideProgress();
                showErrorAlert(message);
            }
        });
    }
}