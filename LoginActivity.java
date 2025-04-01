package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtpass;
    Button btnLogin;
    DBOperations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUser = findViewById(R.id.txtLoginEmail);
        txtpass = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        db = new DBOperations(this);
    }
    public void ResgisterBtn(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void adminLogin(View view){
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        String user = txtUser.getText().toString();
        String pass = txtpass.getText().toString();

        User user1 = new User();
        user1.setUsername(user);
        user1.setPassword(pass);

        if (db.userlogin(user1)){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("username", user);
            intent.putExtra("password", pass);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Login Faild, Try Again!", Toast.LENGTH_LONG).show();

        }
    }
}