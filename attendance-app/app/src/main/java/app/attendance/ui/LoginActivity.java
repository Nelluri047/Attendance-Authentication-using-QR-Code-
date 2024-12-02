package app.attendance.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Objects;

import app.attendance.AppController;
import app.attendance.BuildConfig;
import app.attendance.R;
import app.attendance.data.PreferencesManager;
import app.attendance.data.api.ApiManager;
import app.attendance.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(BuildConfig.DEBUG) {
            Objects.requireNonNull(binding.emailLayout.getEditText()).setText("stu1@att.app");
            Objects.requireNonNull(binding.passwordLayout.getEditText()).setText("123456");
        }

        binding.loginButton.setOnClickListener(this::onLogin);
    }

    private void onLogin(View view) {
        String email = Objects.requireNonNull(binding.emailLayout.getEditText()).getText().toString();
        String password = Objects.requireNonNull(binding.passwordLayout.getEditText()).getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            showToast(R.string.please_fill_all_fields);
            return;
        }

        performLogin(email, password);
    }

    private void performLogin(String email, String password) {
        showProgress();
        AppController.getApiManager().login(email, password, new ApiManager.ApiCallback<String>() {
            @Override
            public void onSuccess(String response) {
                hideProgress();
                showToast(response);
                MainActivity.start(LoginActivity.this);
                finish();
            }

            @Override
            public void onError(String message) {
                hideProgress();
                showErrorAlert(message);
            }
        });
    }
}
