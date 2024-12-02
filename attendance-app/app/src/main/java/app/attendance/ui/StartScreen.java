package app.attendance.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;

import app.attendance.AppController;
import app.attendance.databinding.ActivityStartBinding;

public class StartScreen extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        ActivityStartBinding binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progress.postDelayed(() -> {
            if (AppController.getPreferencesManager().isLoggedIn()) {
                MainActivity.start(this);
            } else {
                LoginActivity.start(this);
            }
            finish();
        }, 1500);
    }
}
