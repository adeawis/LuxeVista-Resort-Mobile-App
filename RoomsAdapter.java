package com.example.luxevistaresort;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Rooms> rooms;
    private TextView roomType, roomPrice, roomDetails;
    private ImageView roomImage;
    private Button btnBookRoom;

    public RoomsAdapter(Context context, ArrayList<Rooms> rooms){
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount(){
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View roomView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        roomView = inflater.inflate(R.layout.custom_room_layout, null);

        roomType = roomView.findViewById(R.id.txtViewRoomType1);
        roomDetails = roomView.findViewById(R.id.txtViewRoomDetails);
        roomPrice = roomView.findViewById(R.id.txtViewRoomPrice);
        roomImage = roomView.findViewById(R.id.imgViewRoom);
        btnBookRoom = roomView.findViewById(R.id.btnBookRoom);

        Rooms rooms1 = rooms.get(position);
        if (rooms1 == null){
            Log.e("RoomsAdapter", "Room at position" + position + "is null");
        }else{
            Log.e("RoomsAdapter", "Room type" + rooms1.getRoomType());
        }

        roomType.setText(rooms1.getRoomType());
        roomDetails.setText(rooms1.getDetails());
        roomPrice.setText("LKR " + String.format("%.2f", rooms1.getPrice()));

        if (rooms1.getRoomImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(rooms1.getRoomImage(), 0, rooms1.getRoomImage().length);
            roomImage.setImageBitmap(bitmap);

        }else{
            roomImage.setImageResource(R.drawable.default_room);
        }
        btnBookRoom.setTag(position);

        btnBookRoom.setOnClickListener(view -> {
            String selectedRoomType = rooms1.getRoomType();

            Intent intent = new Intent(context, RoomBookings.class);
            intent.putExtra("roomType", selectedRoomType);
            context.startActivity(intent);
        });
        return roomView;



    }
}
