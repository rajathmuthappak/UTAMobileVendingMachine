package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.Item;
import com.example.utamobilevendingsystem.domain.Location;
import com.example.utamobilevendingsystem.domain.OrderItem;
import com.example.utamobilevendingsystem.domain.Payments;
import com.example.utamobilevendingsystem.domain.UserCart;
import com.example.utamobilevendingsystem.users.UserOrderDetails;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserOrder extends AppCompatActivity {
    SQLiteDatabase db;
    TextView swichAvl,drinksAvl,snacksAvl,totalPrice,switchPrice,drinksPrice,snacksPrice;
    EditText switchQty,drinksQty,snacksQty;
    Button placeOrder;
    UserCart userCart = new UserCart();
    HashMap<Integer,Integer> vehicleInventory = new HashMap<>();
    ContentValues cart = new ContentValues();
    SharedPreferences.Editor editor;
    int locationId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        Intent myint = getIntent();
        Bundle extras = myint.getExtras();
        String location="";

        if(extras!=null)
        {
             location = myint.getStringExtra("location");
             locationId = myint.getIntExtra("id",0);
        }

        db= DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        swichAvl= findViewById(R.id.swichAvl);
        drinksAvl= findViewById(R.id.drinksAvl);
        snacksAvl= findViewById(R.id.snacksAvl);
        totalPrice= findViewById(R.id.totalPrice);
        switchPrice= findViewById(R.id.switchPrice);
        drinksPrice= findViewById(R.id.drinksPrice);
        snacksPrice= findViewById(R.id.snacksPrice);
        switchQty= findViewById(R.id.switchQty);
        drinksQty= findViewById(R.id.drinksQty);
        snacksQty= findViewById(R.id.snacksQty);
        placeOrder= findViewById(R.id.placeOrder);
        editor = getSharedPreferences("userCart", MODE_PRIVATE).edit();
        getInventory(locationId);
    }

    private void getInventory(int locationId) {
        String selectQuery = "SELECT "+Resources.VEHICLE_ID+","+ Resources.VEHICLE_SCHEDULE_TIME+","+ Resources.VEHICLE_AVAILABILITY+" FROM " + Resources.TABLE_VEHICLE + " WHERE "
                + Resources.VEHICLE_LOCATION_ID + " = " + locationId;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            int  vehicleID =c.getInt(c.getColumnIndex(Resources.VEHICLE_ID));
            String schedule =c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME));
            String availabilty = c.getString(c.getColumnIndex(Resources.VEHICLE_AVAILABILITY));
            if("Available".equalsIgnoreCase(availabilty)) {
                if (validateTime(schedule)) {
                    editor.putInt("vehicleID", vehicleID);
                    String inventoryQuery = "SELECT * FROM " + Resources.TABLE_VEHICLE_INVENTORY + " WHERE " + Resources.VEHICLE_INVENTORY_VEHICLE_ID + " = " + vehicleID;
                    Cursor c1 = db.rawQuery(inventoryQuery, null);
                    int count = c1.getCount();
                    while (count > 0) {
                        c1.moveToPosition(count - 1);
                        int item_id = c1.getInt(c1.getColumnIndex(Resources.VEHICLE_INVENTORY_ITEM_ID));
                        int quantity = c1.getInt(c1.getColumnIndex(Resources.VEHICLE_INVENTORY_QUANTITY));
                        vehicleInventory.put(item_id, quantity);
                        count--;
                    }
                    swichAvl.setText(String.valueOf(vehicleInventory.get(1)));
                    drinksAvl.setText(String.valueOf(vehicleInventory.get(2)));
                    snacksAvl.setText(String.valueOf(vehicleInventory.get(3)));
                    placeOrderMethod();
                } else {
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "The vehicle schedule is between " + schedule + " only!", Toast.LENGTH_SHORT).show();
                }
            }else {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "No vehicle has been assigned to this location!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            onBackPressed();
            Toast.makeText(getApplicationContext(), "No vehicle has been assigned to this location!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateTime(String schedule) {
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        int hour =  Integer.valueOf(currentTime.substring(0,2));
        int min = Integer.valueOf(currentTime.substring(3));
        int startTimeHr = Integer.valueOf(schedule.substring(0,2));
        int startTimeMin = Integer.valueOf(schedule.substring(3,5));
        int endTimeHr = Integer.valueOf(schedule.substring(8,10));
        int endTimeMin = Integer.valueOf(schedule.substring(11));
        if(hour >= startTimeHr &&  hour <= endTimeHr){
            if(hour == startTimeHr && min < startTimeMin || hour == endTimeHr && min > endTimeMin){
                return false;
            }
            return true;
        }
        return false;
    }

    private void placeOrderMethod() {
        placeOrder.setClickable(true);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double swichPrice = Double.parseDouble(switchPrice.getText().toString().substring(1)) * Integer.parseInt(switchQty.getText().toString());
                double drnksPrice = Double.parseDouble(drinksPrice.getText().toString().substring(1)) * Integer.parseInt(drinksQty.getText().toString());
                double snksPrice = Double.parseDouble(snacksPrice.getText().toString().substring(1)) * Integer.parseInt(snacksQty.getText().toString());

                double total = swichPrice+drnksPrice+snksPrice;
                totalPrice.setText("Total = $"+total);
                editor.putString("swichPrice", String.valueOf(swichPrice));
                editor.putString("drinksPrice", String.valueOf(drnksPrice));
                editor.putString("snacksPrice", String.valueOf(snksPrice));
                boolean flag= true;
                int sandwichQTY = Integer.parseInt(switchQty.getText().toString());
                int drinksQTY = Integer.parseInt(drinksQty.getText().toString());
                int snacksQTY= Integer.parseInt(snacksQty.getText().toString());
                int sandwichAVL = Integer.parseInt(swichAvl.getText().toString());
                int drinksAVL = Integer.parseInt(drinksAvl.getText().toString());
                int snacksAVL = Integer.parseInt(snacksAvl.getText().toString());
                if(sandwichQTY> sandwichAVL){
                    switchQty.setError("Please enter according to availability of item");
                    flag= false;
                }
                if(drinksQTY> drinksAVL){
                    drinksQty.setError("Please enter according to availability of item");
                    flag= false;
                }
                if(snacksQTY>snacksAVL ){
                    snacksQty.setError("Please enter according to availability of item");
                    flag= false;
                }
                if(sandwichQTY==0 && drinksQTY==0 && snacksQTY==0){
                    switchQty.setError("Please select at least 1 item before placing order");
                    flag=false;
                }
                if(flag){
                    SQLiteDatabase db=DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
                    SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
                    int uid =prefs.getInt("userid",0);
                    cart.put("cart_ID",uid);
                    cart.put("cart_item_id",1);
                    cart.put("quantity",sandwichQTY);
                    db.insert(Resources.TABLE_CART,null, cart);
                    cart.put("cart_ID",uid);
                    cart.put("cart_item_id",2);
                    cart.put("quantity",drinksQTY);
                    db.insert(Resources.TABLE_CART,null, cart);
                    cart.put("cart_ID",uid);
                    cart.put("cart_item_id",3);
                    cart.put("quantity",snacksQTY);
                    db.insert(Resources.TABLE_CART,null, cart);
                    editor.putInt("swichAvl",sandwichAVL);
                    editor.putInt("drinksAvl",drinksAVL);
                    editor.putInt("snacksAvl",snacksAVL);
                    editor.putInt("swichQty",sandwichQTY);
                    editor.putInt("drinksQty",drinksQTY);
                    editor.putInt("snacksQty",snacksQTY);
                    editor.putInt("locationID",locationId);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Items added to cart!",Toast.LENGTH_SHORT).show();
                    Intent myint = new Intent(UserOrder.this, CardDetails.class);
                    myint.putExtra("total",total);
                    myint.putExtra("uid",uid);
                    startActivity(myint);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_location:
                viewLocationList();
                return true;
            case R.id.menu_view_orders:
                viewOrders();
                return true;
            case R.id.app_bar_search:
                vehicleSearch();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
                Intent homeIntent = new Intent(this, UserHomeScreen.class);
                startActivity(homeIntent);
                return true;
            case R.id.change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void vehicleSearch() {
        Intent myint = new Intent(this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        Intent myint = new Intent(this, UserOrderDetails.class);
        startActivity(myint);
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(this, LoginActivity.class);
        Toast.makeText(getApplicationContext(),"Logged out Successfully",Toast.LENGTH_SHORT).show();
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
