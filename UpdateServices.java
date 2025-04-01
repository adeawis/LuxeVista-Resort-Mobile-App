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

public class UpdateServices extends AppCompatActivity {

    EditText txtID, txtserviceType, txtservicePrice, txtdetails;
    ImageView imgSvsUpdate;
    DBOperations db;
    private byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_services);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBOperations(this);
        txtID = findViewById(R.id.txtUpdateServiceID);
        txtserviceType = findViewById(R.id.txtUpdateServiceType);
        txtservicePrice = findViewById(R.id.txtUpdateServicePrice);
        txtdetails = findViewById(R.id.txtUpdateServiceDetails);
        imgSvsUpdate = findViewById(R.id.imgUpdateService);
    }

    private void clear(){
        txtID.setText(null);
        txtserviceType.setText(null);
        txtservicePrice.setText(null);
        txtdetails.setText(null);
        imgSvsUpdate.setImageBitmap(null);
    }
    public void clearAllServices(View view){
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
        if (requestCode == 111) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    imageByte = outputStream.toByteArray();
                    imgSvsUpdate.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void searchServicesBtn(View view){
        String serviceType = txtserviceType.getText().toString();
        Services services = db.searchServices(serviceType);
        if (services != null){
            txtID.setText(services.getServiceID());
            txtserviceType.setText(services.getServiceType());
            txtdetails.setText(services.getServiceDetails());
            txtservicePrice.setText(String.valueOf(services.getServicePrice()));


            if (services.getImageService() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(services.getImageService(), 0, services.getImageService().length);
                imgSvsUpdate.setImageBitmap(bitmap);

            }else{
                imgSvsUpdate.setImageResource(R.drawable.default_service);
            }
        }
        else{
            Toast.makeText(this, "Service Not Found!", Toast.LENGTH_LONG).show();
            clear();
        }
    }

    public void updateServiceBtn(View view){
        Services services = new Services();
        services.setServiceID(txtID.getText().toString());
        services.setServiceType(txtserviceType.getText().toString());
        services.setServicePrice(Double.parseDouble(txtservicePrice.getText().toString()));
        services.setServiceDetails(txtdetails.getText().toString());
        services.setImageService(imageByte);

        if (db.updateServices(services) == 1){
            Toast.makeText(this, "Service Updated Successfully!", Toast.LENGTH_LONG).show();
            clear();
        }else {
            Toast.makeText(this, "Failed to update service.", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteServicesBtn(View view){
        String serviceID = txtID.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle("Delete Service")
                .setMessage("Are you sure you want to Delete this Service?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (db.deleteService(serviceID) > 0){
                        Toast.makeText(this, "Service Deleted Successfully!", Toast.LENGTH_LONG).show();
                        clear();
                    }else{
                        Toast.makeText(this, "Service Deletion Canceled!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", null).show();
    }

}