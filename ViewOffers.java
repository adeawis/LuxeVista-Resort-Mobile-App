package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewOffers extends AppCompatActivity {

    ImageView imgOffer, imgsports, imgtours, imgdining;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_offers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgOffer = findViewById(R.id.imgOffer);
        imgtours = findViewById(R.id.imgbeachTours);
        imgsports = findViewById(R.id.imgwaterSports);
        imgdining = findViewById(R.id.imgdiningideas);


        imgOffer.setImageResource(R.drawable.offer_banner);
        imgtours.setImageResource(R.drawable.beach_tours);
        imgsports.setImageResource(R.drawable.water_sports);
        imgdining.setImageResource(R.drawable.dining_rec);
    }

    public void backToRooms(View view){
        Intent intent = new Intent(this, RoomsViewActivity.class);
        startActivity(intent);
    }

    public void backToServices(View view){
        Intent intent = new Intent(this, ServicesViewActivity.class);
        startActivity(intent);
    }
}