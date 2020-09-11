package com.example.utamobilevendingsystem;

import android.content.Context;
import android.graphics.Color;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.List;

public class VehicleListAdapter extends ArrayAdapter<Vehicle> {

    private Context mContext;
    int mResource;

    public VehicleListAdapter(@NonNull Context context, int resource, @NonNull List<Vehicle> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String vehicleName = getItem(position).getVehicleName();
        int location = getItem(position).getLocationId();
        VehicleType type = getItem(position).getVehicleType();
        int id = getItem(position).getVehicleId();
        String locationName = getItem(position).getLocationName();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.vehicleName);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.vehicleLocation);
        TextView tvType = (TextView) convertView.findViewById(R.id.vehicleType);
        TextView vehicleIDTV = (TextView) convertView.findViewById(R.id.vehicleID);
        if(getItem(position).isAvailability().equals(Status.UNAVAILABLE)){
            tvName.setTextColor(Color.rgb(2,1,1));
        }
        tvName.setText(vehicleName);
        tvLocation.setText("Location: " + (location == 0 ? Status.UNASSIGNED.getDescription() :locationName));
        tvType.setText(String.valueOf(type.name()));
        vehicleIDTV.setText(String.valueOf(id));
        return convertView;
    }
}
