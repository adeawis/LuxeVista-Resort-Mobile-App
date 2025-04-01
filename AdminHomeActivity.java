package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void manageRoomsBtn(View view){
        Intent intent = new Intent(this, ManageRooms.class);
        startActivity(intent);
    }

    public void adminLogout(View view){
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }

    public void updateRooms(View view){
        Intent intent = new Intent(this, UpdateRooms.class);
        startActivity(intent);
    }

    public void manageServicesBtn(View view){
        Intent intent = new Intent(this, ManageServicesAdmin.class);
        startActivity(intent);
    }

    public void updateServices(View view){
        Intent intent = new Intent(this, UpdateServices.class);
        startActivity(intent);
    }


}