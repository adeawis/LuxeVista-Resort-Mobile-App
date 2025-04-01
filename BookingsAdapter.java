package com.example.luxevistaresort;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookingsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bookings> bookings;

    private TextView bookingID, roomType, checkInDate, checkOutDate;
    private Button btnDeleteBooking;
    private DBOperations db;

    public BookingsAdapter(Context context, ArrayList<Bookings> bookings){
        this.context = context;
        this.bookings = bookings;
        db = new DBOperations(context);
    }


    @Override
    public int getCount() {
        return bookings.size();
    }

    @Override
    public Object getItem(int position) {
        return bookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View bookingsView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bookingsView = inflater.inflate(R.layout.custom_bookings_view_layout, null);

        bookingID = bookingsView.findViewById(R.id.txtViewBookingID);
        roomType = bookingsView.findViewById(R.id.txtViewRoomTypeBooking);
        checkInDate = bookingsView.findViewById(R.id.txtViewCheckInDate);
        checkOutDate = bookingsView.findViewById(R.id.txtViewCheckOutDate);
        btnDeleteBooking = bookingsView.findViewById(R.id.btnDeleteBooking);

        Bookings bookings1 = bookings.get(position);

        bookingID.setText(bookings1.getBookingID());
        roomType.setText(bookings1.getRoomType());
        checkInDate.setText(bookings1.getCheckInDate());
        checkOutDate.setText(bookings1.getCheckOutDate());

        btnDeleteBooking.setOnClickListener(view -> {
            int isDeleted = db.deleteBooking(bookings1.getBookingID());
            if (isDeleted > 0){
                bookings.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Booking Deleted Successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(context, "Failed to Delete Booking!", Toast.LENGTH_LONG).show();
            }
        });
        return bookingsView;
    }

    private void startActivity(Intent intent) {
    }
}
