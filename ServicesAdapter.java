package com.example.luxevistaresort;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ServicesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Services> services;
    private TextView serviceType, servicePrice, serviceDetails;
    private ImageView serviceImage;
    private Button btnReserveServices;

    public ServicesAdapter(Context context, ArrayList<Services> services){
        this.context = context;
        this.services = services;
    }


    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View serviceView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        serviceView = inflater.inflate(R.layout.custom_services_layout, null);

        serviceType = serviceView.findViewById(R.id.txtViewServiceType);
        serviceDetails = serviceView.findViewById(R.id.txtViewServiceDetails);
        servicePrice = serviceView.findViewById(R.id.txtViewServicePrice);
        serviceImage = serviceView.findViewById(R.id.imgViewService);
        btnReserveServices = serviceView.findViewById(R.id.btnServiceReserve);

        Services services1 = services.get(position);

        serviceType.setText(services1.getServiceType());
        serviceDetails.setText(services1.getServiceDetails());
        servicePrice.setText("LKR " + String.format("%.2f", services1.getServicePrice()));

        if (services1.getImageService() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(services1.getImageService(), 0, services1.getImageService().length);
            serviceImage.setImageBitmap(bitmap);

        }else{
            serviceImage.setImageResource(R.drawable.default_room);
        }

        btnReserveServices.setTag(position);

        btnReserveServices.setOnClickListener(view -> {
            String selectedServiceType = services1.getServiceType();

            Intent intent = new Intent(context, ServiceReservations.class);
            intent.putExtra("serviceType", selectedServiceType);
            context.startActivity(intent);
        });
        return serviceView;
    }
}
