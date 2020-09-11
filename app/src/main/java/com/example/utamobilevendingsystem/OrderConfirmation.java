package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.users.UserOrderDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderConfirmation extends AppCompatActivity {
    int userId;
    TextView swichQty, swichPrice, drinksQty, drinksPrice, snacksQty, snacksPrice, total;
    int swichQuantity, drinksQuantity, snacksQuantity, sandwichAVL, drinksAVL, snacksAVL;
    String switchAmt, drinksAmt, snacksAmt;
    double totalAmt;
    int userID;
    String role;
    int locationId, vehicleId;
    private static DecimalFormat df = new DecimalFormat("0.00");

    private void fetchSharedPref() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        userID = prefs.getInt("userid", 0);
        role = prefs.getString("userRole", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        Intent newint = getIntent();
        fetchSharedPref();
        userId = newint.getIntExtra("userid", 0);
        totalAmt = newint.getDoubleExtra("totalPrice", 0.0);
        swichQty = findViewById(R.id.swichQty);
        swichPrice = findViewById(R.id.swichPrice);
        drinksQty = findViewById(R.id.drinksQty);
        drinksPrice = findViewById(R.id.drinksPrice);
        snacksQty = findViewById(R.id.snacksQty);
        snacksPrice = findViewById(R.id.snacksPrice);
        total = findViewById(R.id.totalPrice);
        total.setText("$" + df.format(totalAmt));
        fetchCurrOrder();
    }

    private void fetchCurrOrder() {
        SharedPreferences preferences = getSharedPreferences("userCart", MODE_PRIVATE);
        swichQuantity = preferences.getInt("swichQty", 0);
        drinksQuantity = preferences.getInt("drinksQty", 0);
        snacksQuantity = preferences.getInt("snacksQty", 0);
        switchAmt = preferences.getString("swichPrice", "");
        drinksAmt = preferences.getString("drinksPrice", "");
        snacksAmt = preferences.getString("snacksPrice", "");
        locationId = preferences.getInt("locationID", 0);
        vehicleId = preferences.getInt("vehicleID", 0);
        sandwichAVL = preferences.getInt("swichAvl", 0);
        drinksAVL = preferences.getInt("drinksAvl", 0);
        snacksAVL = preferences.getInt("snacksAvl", 0);
        swichQty.setText(String.valueOf(swichQuantity));
        swichPrice.setText(switchAmt);
        drinksQty.setText(String.valueOf(drinksQuantity));
        drinksPrice.setText(drinksAmt);
        snacksQty.setText(String.valueOf(snacksQuantity));
        snacksPrice.setText(snacksAmt);
        confirmOrder();
    }

    private void confirmOrder() {
        SQLiteDatabase db = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        String lastOrder = "SELECT " + Resources.ORDER_ID + " FROM " + Resources.TABLE_ORDER;
        Cursor c = db.rawQuery(lastOrder, null);
        int order_id = 1;
        if (c.getCount() > 0) {
            c.moveToLast();
            order_id = c.getInt(c.getColumnIndex(Resources.ORDER_ID)) + 1;
        }
        //Insert values into orders table
        ContentValues order = new ContentValues();
        order.put("order_id", order_id);
        order.put("order_item_id", 1);
        order.put(Resources.ORDER_VEHICLE_ID, locationId);
        order.put("order_item_quantity", swichQuantity);
        order.put("order_item_price", switchAmt);
        order.put("order_status_id", Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER, null, order);
        order.put("order_id", order_id);
        order.put(Resources.ORDER_VEHICLE_ID, locationId);
        order.put("order_item_id", 2);
        order.put("order_item_quantity", drinksQuantity);
        order.put("order_item_price", drinksAmt);
        order.put("order_status_id", Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER, null, order);
        order.put("order_id", order_id);
        order.put(Resources.ORDER_VEHICLE_ID, locationId);
        order.put("order_item_id", 3);
        order.put("order_item_quantity", snacksQuantity);
        order.put("order_item_price", snacksAmt);
        order.put("order_status_id", Status.CONFIRMED.getDescription());
        db.insert(Resources.TABLE_ORDER, null, order);

        //Insert values into user orders table
        ContentValues userOrders = new ContentValues();
        userOrders.put("user_id", userId);
        userOrders.put("order_id", order_id);
        db.insert(Resources.TABLE_USER_ORDER, null, userOrders);

        //Modify vehicle inventory after order
        int swichCount = sandwichAVL - swichQuantity;
        int drinksCount = drinksAVL - drinksQuantity;
        int snacksCount = snacksAVL - snacksQuantity;
        String sandwichUpdate = "UPDATE " + Resources.TABLE_VEHICLE_INVENTORY + " SET quantity = " + swichCount + " WHERE item_id = '1' AND vehicle_id = " + vehicleId;
        db.execSQL(sandwichUpdate);
        String drinksUpdate = "UPDATE " + Resources.TABLE_VEHICLE_INVENTORY + " SET quantity = " + drinksCount + " WHERE item_id = '2' AND vehicle_id = " + vehicleId;
        db.execSQL(drinksUpdate);
        String snacksUpdate = "UPDATE " + Resources.TABLE_VEHICLE_INVENTORY + " SET quantity = " + snacksCount + " WHERE item_id = '3' AND vehicle_id = " + vehicleId;
        db.execSQL(snacksUpdate);
        String deleteCart = "DELETE FROM " + Resources.TABLE_CART + " WHERE " + Resources.CART_ID + " = " + userId;
        db.execSQL(deleteCart);
        SharedPreferences.Editor editor = getSharedPreferences("userCart", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        if ("Manager".equalsIgnoreCase(role)) {
            menu.findItem(R.id.app_bar_search).setVisible(true);
        }
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
                viewOrders(role);
                return true;
            case R.id.app_bar_search:
                vehicleSearch();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
                SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
                String role = preferences.getString("userRole", "");
                role = role + "HomeScreen";
                try {
                    Class<?> cls = Class.forName("com.example.utamobilevendingsystem.HomeScreens." + role);
                    Intent homeIntent = new Intent(this, cls);
                    startActivity(homeIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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

    private void viewOrders(String role) {
        if (role.equals("User")) {
            Intent viewOrders = new Intent(this, UserOrderDetails.class);
            SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
            userID = prefs.getInt("userid", 0);
            viewOrders.putExtra("userId", String.valueOf(userID));
            startActivity(viewOrders);
        }
        if (role.equals("Manager")) {
            Intent myint = new Intent(this, ManagerOrderDetails.class);
            startActivity(myint);
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(this, LoginActivity.class);
        Toast.makeText(getApplicationContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList() {
        Intent changePasswordIntent = new Intent(this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
