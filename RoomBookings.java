package com.example.luxevistaresort;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoomBookings extends AppCompatActivity {

    private EditText txtBkID, txtRoomType, txtChkInDate, txtChkOutDate, txtFN, txtemail, txtContNo;
    DBOperations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtBkID = findViewById(R.id.txtBookingID);
        txtRoomType = findViewById(R.id.txtRoomTypeBooking);
        txtChkInDate = findViewById(R.id.txtCheckInDate);
        txtChkOutDate = findViewById(R.id.txtCheckOutDate);
        txtFN = findViewById(R.id.txtFullName);
        txtemail = findViewById(R.id.txtEmail);
        txtContNo = findViewById(R.id.txtContactNumber);

        db = new DBOperations(this);

        String bookingID = generateBookingId();
        txtBkID.setText(bookingID);

        String selectedRoomType = getIntent().getStringExtra("roomType");
        if (selectedRoomType != null){
            txtRoomType.setText(selectedRoomType);
        }
    }
    private String generateBookingId() {
        int randomId = 100000 + (int)(Math.random() * 900000); // Generate a 6-digit random number
        return "BOOK-" + randomId;
    }

    public void bookNowBtn(View view){
        Bookings bookings = new Bookings();
        String selectedRoomType = txtRoomType.getText().toString();

        bookings.setBookingID(txtBkID.getText().toString());
        bookings.setRoomType(selectedRoomType);
        bookings.setCheckInDate(txtChkInDate.getText().toString());
        bookings.setCheckOutDate(txtChkOutDate.getText().toString());
        bookings.setFullname(txtFN.getText().toString());
        bookings.setEmail(txtemail.getText().toString());
        bookings.setContNo(Integer.parseInt(txtContNo.getText().toString()));

        if (db.insertBookings(bookings) > 0){
            Toast.makeText(this, "Room Booked Successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, RoomsViewActivity.class);
            startActivity(intent);
        }
    }
}