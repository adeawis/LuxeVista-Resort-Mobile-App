package com.example.luxevistaresort;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ManageRooms extends AppCompatActivity {

    private EditText txtID, txtRoomType, txtPrice, txtDetails, txtDate;
    private ImageView roomImg;
    private byte[] imageByte;
    DBOperations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBOperations(this);
        txtID = findViewById(R.id.txtEnterID);
        txtRoomType = findViewById(R.id.txtEnterRoomType);
        txtPrice = findViewById(R.id.txtEnterRoomPrice);
        txtDetails = findViewById(R.id.txtEnterDetails);
        roomImg = findViewById(R.id.insertRoomsIMG);
        txtDate = findViewById(R.id.txtSelectedDate);
    }

    public void inserImgBtn(View view){
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
    protected void onActivityResult(int requestCode, int  resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111){
            if (data != null){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    imageByte = outputStream.toByteArray();
                    roomImg.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

    }

    public void addRoomBtn(View view){
        Rooms rooms = new Rooms();

        rooms.setRoomID(txtID.getText().toString());
        rooms.setRoomType(txtRoomType.getText().toString());
        rooms.setPrice(Double.parseDouble(txtPrice.getText().toString()));
        rooms.setDetails(txtDetails.getText().toString());
        rooms.setAvailability(txtDate.getText().toString());
        rooms.setRoomImage(imageByte);

        if (db.insertRooms(rooms)>0){
            Toast.makeText(this, "New Room Added Successfully!", Toast.LENGTH_SHORT).show();
            clear();
        }
    }

    private void clear(){
        txtID.setText(null);
        txtRoomType.setText(null);
        txtPrice.setText(null);
        roomImg.setImageDrawable(null);
        txtDetails.setText(null);
        txtDate.setText(null);
        txtID.requestFocus();
    }

    public void viewAllRoomsBtn(View view){
        Intent intent = new Intent(this, RoomsViewActivity.class);
        startActivity(intent);
    }


}