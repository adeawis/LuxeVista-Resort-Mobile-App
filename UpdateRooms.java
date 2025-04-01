package com.example.luxevistaresort;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateRooms extends AppCompatActivity {

    EditText txtroomID, txtroomType, txtroomPrice, txtdate, txtdetails;
    ImageView imgUpdatepic;
    DBOperations db;
    private byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBOperations(this);
        txtroomID = findViewById(R.id.txtUpdateRoomID);
        txtroomType = findViewById(R.id.txtUpdateRoomType);
        txtroomPrice = findViewById(R.id.txtUpdateRoomPrice);
        txtdate = findViewById(R.id.txtUpdateDate);
        txtdetails = findViewById(R.id.txtUpdateDetails);
        imgUpdatepic = findViewById(R.id.imgUpdateRoom);


    }
    private void clear(){
        txtroomID.setText(null);
        txtroomType.setText(null);
        txtroomPrice.setText(null);
        txtdate.setText(null);
        txtdetails.setText(null);
        imgUpdatepic.setImageBitmap(null);
    }
    public void clearAll(View view){
        clear();
    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 160);
        intent.putExtra("return-date", true);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111){
            if (data != null){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    imageByte = outputStream.toByteArray();
                    imgUpdatepic.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }

    public void searchRooom(View view){
        String roomType = txtroomType.getText().toString();
        Rooms rooms = db.searchRoom(roomType);
        if (rooms != null){
            txtroomID.setText(rooms.getRoomID());
            txtroomType.setText(rooms.getRoomType());
            txtroomPrice.setText(String.valueOf(rooms.getPrice()));
            txtdate.setText(rooms.getAvailability());
            txtdetails.setText(rooms.getDetails());


            if (rooms.getRoomImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(rooms.getRoomImage(), 0, rooms.getRoomImage().length);
                imgUpdatepic.setImageBitmap(bitmap);

            }else{
                imgUpdatepic.setImageResource(R.drawable.default_room);
            }
        }
        else{
            Toast.makeText(this, "Room Not Found!", Toast.LENGTH_LONG).show();
            clear();
        }
    }

    public void updateRoom(View view){
        Rooms rooms = new Rooms();
        rooms.setRoomID(txtroomID.getText().toString());
        rooms.setRoomType(txtroomType.getText().toString());
        rooms.setPrice(Double.parseDouble(txtroomPrice.getText().toString()));
        rooms.setAvailability(txtdate.getText().toString());
        rooms.setDetails(txtdetails.getText().toString());
        rooms.setRoomImage(imageByte);

        if (db.updateRooms(rooms) == 1){
            Toast.makeText(this, "Room Updated Successfully!", Toast.LENGTH_LONG).show();
            clear();
        }

    }

    public void deleteRoom(View view){
        String roomID = txtroomID.getText().toString();

        new AlertDialog.Builder(this).setTitle("Delete Room").setMessage("Are you Sure you want to Delete This Room?")
                .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Yes", (dialog, which) -> {
        if (db.deleteRooms(roomID) > 0){
            Toast.makeText(this, "Room Deleted Successfully!", Toast.LENGTH_LONG).show();
            clear();
        }else{
            Toast.makeText(this, "Room Deletion Canceled!", Toast.LENGTH_LONG).show();
        }
    }).setNegativeButton("No", null).show();
    }




}