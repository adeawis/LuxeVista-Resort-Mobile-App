package com.example.luxevistaresort;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewBookingsHistory extends AppCompatActivity {

    ListView lstbooking;
    DBOperations db;
    ArrayList<Bookings> bookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_bookings_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lstbooking = findViewById(R.id.lstBookings);
        db = new DBOperations(this);

        bookings = db.viewAllBookings();

        if (bookings != null){
            BookingsAdapter adapter = new BookingsAdapter(this, bookings);
            lstbooking.setAdapter(adapter);
        }
    }
}