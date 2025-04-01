package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ServicesViewActivity extends AppCompatActivity {

    ListView lstServices;
    ArrayList<Services> services;
    DBOperations db;
    String user, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        pass = intent.getStringExtra("password");


        lstServices = findViewById(R.id.lstServices);
        db = new DBOperations(this);

        services = db.viewAllServices();

        if (services != null){
            ServicesAdapter adapter = new ServicesAdapter(this, services);
            lstServices.setAdapter(adapter);

        }
    }

    public void BackBtn(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username", user);
        intent.putExtra("password", pass);
        startActivity(intent);
    }
}