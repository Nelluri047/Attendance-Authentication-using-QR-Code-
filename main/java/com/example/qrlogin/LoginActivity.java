package com.example.qrlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setVariable();
    }

    private void setVariable() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEdt.getText().toString().isEmpty() && passEdt.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill the form to login", Toast.LENGTH_SHORT).show();
                } else if (userEdt.getText().toString().equals("Admin") && passEdt.getText().toString().equals("Admin")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText1);
        passEdt = findViewById(R.id.editTextText2);
        loginBtn = findViewById(R.id.Login);
    }
}
