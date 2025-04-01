package com.example.luxevistaresort;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManageUserActivity extends AppCompatActivity {

    EditText txtEditEmail, txteditPass;
    TextView txtemail, txtpass;
    DBOperations db;

    private static final String CHANNEL_ID = "LuxeVista Notification Channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEditEmail = findViewById(R.id.txtManageUserEmail);
        txteditPass = findViewById(R.id.txtManageUserPassword);
        txtemail = findViewById(R.id.txtManageUserEmail);
        txtpass = findViewById(R.id.txtManageUserPassword);
        db = new DBOperations(this);
        Intent intent = getIntent();

        txtemail.setText(intent.getStringExtra("username"));
        txtpass.setText(intent.getStringExtra("password"));


    }

    public void updateUser(View view){
        String email = txtEditEmail.getText().toString();
        String pass = txteditPass.getText().toString();

        User user = new User();
        user.setUsername(email);
        user.setPassword(pass);
        if (db.updateUser(user) == 1){
            Toast.makeText(this, "User Account Updated Successfully!", Toast.LENGTH_LONG).show();

        }
    }

    public void deleteUser(View view){
        String email = txtEditEmail.getText().toString();

        new AlertDialog.Builder(this).setTitle("Delete Account").setMessage("Are you Sure you want to Delete the Account?")
                .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Yes", (dialog, which) -> {
        if (db.deleteUser(email) > 0){
            Toast.makeText(this,"Account Deleted Successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
                }).setNegativeButton("No", null).show();
    }

    public void cancelBtn(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void allowNotification(View view){
        showNotify();
    }

    private void showNotify(){
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Uri customRingtone = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.whistle_notify_sound);
        long[] vibrationPattern = {0,300,100,500};

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "LuxeVista Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Receive important updates and announcements from LuxeVista Resort.");

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            channel.setSound(ringtone, audioAttributes);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrationPattern);

            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.bell_icon);
        builder.setContentTitle("Welcome to LuxeVista!");
        builder.setContentText("Thank you for choosing LuxeVista Resort. We're thrilled to host you!");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        builder.setSound(ringtone);
        builder.setVibrate(vibrationPattern);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.POST_NOTIFICATIONS}, 111);

        }
        notificationManagerCompat.notify(1, builder.build());
    }





}