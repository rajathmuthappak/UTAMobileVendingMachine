package com.example.utamobilevendingsystem.users;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utamobilevendingsystem.ManagerOrderDetails;
import com.example.utamobilevendingsystem.OperatorOrderDetails;
import com.example.utamobilevendingsystem.OrderSummary;
import com.example.utamobilevendingsystem.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserOrderDetailsAdapter extends RecyclerView.Adapter<UserOrderDetailsAdapter.ViewHolder> {
    private Object context = this;
    String TAG = "UserOrderDetailsAdapter";
    ArrayList<String> orderID;
    ArrayList<String> orderItemID;
    ArrayList<String> orderItemQuantity;
    ArrayList<String> orderItemPrice;
    ArrayList<String> orderStatusID;

    public UserOrderDetailsAdapter(Context mContext, ArrayList<String> orderID, ArrayList<String> orderItemQuantity, ArrayList<String> orderItemPrice, ArrayList<String> orderStatusID) {
        this.TAG = "UserOrderDetailsAdapter Constructor from ManagerOrderDetails";
        this.context = mContext;
        this.orderID = orderID;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemPrice = orderItemPrice;
        this.orderStatusID = orderStatusID;
    }

    public UserOrderDetailsAdapter(Context mContext, ArrayList<String> orderID, ArrayList<String> orderItemID, ArrayList<String> orderItemQuantity, ArrayList<String> orderItemPrice, ArrayList<String> orderStatusID) {
        this.TAG = "UserOrderDetailsAdapter Constructor from UserOrderDetails";
        this.context = mContext;
        this.orderID = orderID;
        this.orderItemID = orderItemID;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemPrice = orderItemPrice;
        this.orderStatusID = orderStatusID;
    }


    @NonNull
    @Override
    public UserOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context.getClass() == UserOrderDetails.class){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_user_orders_list, parent, false);
            UserOrderDetailsAdapter.ViewHolder holder = new UserOrderDetailsAdapter.ViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: User Home Adapter called for UserOrderDetails");
            return holder;
        }

        else if (context.getClass() == ManagerOrderDetails.class){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_user_orders_list, parent, false);
            UserOrderDetailsAdapter.ViewHolder holder = new UserOrderDetailsAdapter.ViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: User Home Adapter called for ManagerOrderDetails");
            return holder;
        }

        else if (context.getClass() == OperatorOrderDetails.class){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_user_orders_list, parent, false);
            UserOrderDetailsAdapter.ViewHolder holder = new UserOrderDetailsAdapter.ViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: User Home Adapter called for OperatorOrderDetails");
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderDetailsAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: User Home Adapter called.");

        if (context.getClass() == UserOrderDetails.class ) {
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, " Neenu ottiro button idu untu --> "+position);
                    Intent intent = new Intent( view.getContext() , OrderSummary.class);
                    intent.putExtra("context", "ManagerOrders");
                    intent.putExtra("orderID", orderID.get(position));
                    intent.putExtra("totalPrice", orderItemPrice.get(position));
                    view.getContext().startActivity(intent);
                }
            });

            holder.orderID.setText(orderID.get(position));
            holder.orderStatus.setText(orderStatusID.get(position));
            double totalOrderPrice = Double.valueOf(orderItemPrice.get(position)) * 0.0825 + Double.valueOf(orderItemPrice.get(position));
            DecimalFormat df = new DecimalFormat("####0.00");
            holder.orderPrice.setText(String.valueOf(df.format(totalOrderPrice)));
        }
         else if (context.getClass() == ManagerOrderDetails.class){
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, " Neenu ottiro button idu untu --> "+position);
                    Intent intent = new Intent( view.getContext() , OrderSummary.class);
                    intent.putExtra("context", "UserOrders");
                    intent.putExtra("orderID", orderID.get(position));
                    intent.putExtra("totalPrice", orderItemPrice.get(position));
                    view.getContext().startActivity(intent);
                }

            });
            holder.orderID.setText(orderID.get(position));
            holder.orderStatus.setText(orderStatusID.get(position));
            double totalOrderPrice = Double.valueOf(orderItemPrice.get(position)) * 0.0825 + Double.valueOf(orderItemPrice.get(position));
            DecimalFormat df = new DecimalFormat("####0.00");
            holder.orderPrice.setText(String.valueOf(df.format(totalOrderPrice)));
        }
        else if (context.getClass() == OperatorOrderDetails.class){
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, " Neenu ottiro button idu untu --> "+position);
                    Intent intent = new Intent( view.getContext() , OrderSummary.class);
                    intent.putExtra("context", "UserOrders");
                    intent.putExtra("orderID", orderID.get(position));
                    intent.putExtra("totalPrice", orderItemPrice.get(position));
                    view.getContext().startActivity(intent);
                }

            });
            holder.orderID.setText(orderID.get(position));
            holder.orderStatus.setText(orderStatusID.get(position));
            double totalOrderPrice = Double.valueOf(orderItemPrice.get(position)) * 0.0825 + Double.valueOf(orderItemPrice.get(position));
            DecimalFormat df = new DecimalFormat("####0.00");
            holder.orderPrice.setText(String.valueOf(df.format(totalOrderPrice)));
        }


    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: User Home Adapter called.");
        return orderID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderID,orderPrice, orderStatus ;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "Constructor: ViewHolder in UserOrderDetailsAdapter.");
            orderID = itemView.findViewById(R.id.orderID);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            parentLayout = itemView.findViewById(R.id.pleaseBaa);
        }
    }
}
