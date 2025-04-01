package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ServiceReservations extends AppCompatActivity {

    private EditText txtRsID, txtServiceType, txtName, txtDate, txtTime;
    DBOperations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_reservations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtRsID = findViewById(R.id.txtReservationID);
        txtServiceType = findViewById(R.id.txtServiceTypeReservation);
        txtName = findViewById(R.id.txtFullNameReservations);
        txtDate = findViewById(R.id.txtDateReservations);
        txtTime = findViewById(R.id.txtTimeReservations);

        db = new DBOperations(this);

        String reservationID = generateServiceID();
        txtRsID.setText(reservationID);

        String selectServiceType = getIntent().getStringExtra("serviceType");
        if (selectServiceType != null){
            txtServiceType.setText(selectServiceType);
        }
    }
    private String generateServiceID() {
        int randomId = 100000 + (int)(Math.random() * 900000); // Generate a 6-digit random number
        return "RES-" + randomId;
    }

    public void reserveNowBtn(View view){
        Reservations reservations = new Reservations();
        String selectServiceType = txtServiceType.getText().toString();

        reservations.setReservationID(txtRsID.getText().toString());
        reservations.setServiceType(selectServiceType);
        reservations.setFullnameRes(txtName.getText().toString());
        reservations.setResDate(txtDate.getText().toString());
        reservations.setResTime(txtTime.getText().toString());

        if (db.insertReservations(reservations) > 0){
            Toast.makeText(this, "Service Reserved Successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ServicesViewActivity.class);
            startActivity(intent);
        }
    }

}