package com.example.utamobilevendingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.UserDetails;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.List;

public class OperatorListAdapter extends ArrayAdapter<UserDetails> {
    private Context mContext;
    int mResource;

    public OperatorListAdapter(Context mContext, int mResource, List<UserDetails> operatorList) {
        super(mContext, mResource, operatorList);
        this.mContext = mContext;
        this.mResource = mResource;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getmResource() {
        return mResource;
    }

    public void setmResource(int mResource) {
        this.mResource = mResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String firstName = getItem(position).getfName();
        String lastName = getItem(position).getlName();
        int userId = getItem(position).getUserId();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textViewOperatorID = (TextView) convertView.findViewById(R.id.textViewOperatorID);
        TextView textViewOperatorName = (TextView) convertView.findViewById(R.id.textViewOperatorName);

        textViewOperatorID.setText(String.valueOf(userId));
        textViewOperatorName.setText(firstName +" "+lastName);
        return convertView;
    }
}
