package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUsername = findViewById(R.id.txtAdminUsername);
        txtPassword = findViewById(R.id.txtAdminPassword);
        btnLogin = findViewById(R.id.btnAdminLogin);

    }
    public void adminLogin(View view){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (username.equals("admin") && password.equals("admin123")) {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(AdminLoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToMainLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}