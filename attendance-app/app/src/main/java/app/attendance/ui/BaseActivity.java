package app.attendance.ui;

import android.app.ProgressDialog;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.attendance.R;

/** @noinspection deprecation*/
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setCancelable(false);
        } else {
            progressDialog.setMessage(getString(R.string.please_wait));
        }
        progressDialog.show();
    }

    public void hideProgress() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showErrorAlert(@StringRes int resource) {
        showErrorAlert(getString(resource));
    }

    public void showErrorAlert(String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    public void showToast(@StringRes int resource) {
        showToast(getString(resource));
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
