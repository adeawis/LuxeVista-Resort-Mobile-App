package com.example.luxevistaresort;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class ManageServicesAdmin extends AppCompatActivity {

    private EditText txtSID, txtsType, txtsDetails, txtsPrice;
    private ImageView serviceImage;
    private byte[] imageByte;
    DBOperations db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_services_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBOperations(this);
        txtSID = findViewById(R.id.txtEnterServiceID);
        txtsType = findViewById(R.id.txtEnterServiceType);
        txtsDetails = findViewById(R.id.txtEnterServiceDetails);
        txtsPrice = findViewById(R.id.txtEnterServicePrice);
        serviceImage = findViewById(R.id.imgServiceInsert);
    }

    public void selectServiceImage(View view){
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (data != null){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    imageByte = outputStream.toByteArray();
                    serviceImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void addService(View view){
        Services services = new Services();

        services.setServiceID(txtSID.getText().toString());
        services.setServiceType(txtsType.getText().toString());
        services.setServiceDetails(txtsDetails.getText().toString());
        services.setServicePrice(Double.parseDouble(txtsPrice.getText().toString()));
        services.setImageService(imageByte);

        if (db.insertServices(services)>0){
            Toast.makeText(this, "New Service Added Successfully!", Toast.LENGTH_LONG).show();
            clear();
        }
    }

    private void clear(){
        txtSID.setText(null);
        txtsType.setText(null);
        txtsDetails.setText(null);
        txtsPrice.setText(null);
        serviceImage.setImageDrawable(null);
        txtSID.requestFocus();
    }

    public void viewAllServices(View view){
        Intent intent = new Intent(this, ServicesViewActivity.class);
        startActivity(intent);
    }

}