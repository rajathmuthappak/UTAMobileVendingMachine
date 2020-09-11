package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.UserDetails;

import android.view.MenuItem;
import android.widget.Toast;

public class OperatorDetails extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    TextView odFirstNameDesctv, odLastNameDesctv, odPhoneDesctv, odEmailDesctv, odLocationDesctv, odVehicleDesctv, odScheduleDesctv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_details);
        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        odFirstNameDesctv = findViewById(R.id.odFirstNameDesctv);
        odLastNameDesctv = findViewById(R.id.odLastNameDesctv);
        odPhoneDesctv = findViewById(R.id.odPhoneDesctv);
        odEmailDesctv = findViewById(R.id.odEmailDesctv);
        odLocationDesctv = findViewById(R.id.odLocationDesctv);
        odVehicleDesctv = findViewById(R.id.odVehicleDesctv);
        odScheduleDesctv = findViewById(R.id.odScheduleDesctv);
        String userID = getIntent().getStringExtra("userID");
        String operatorDetaolsQuery = "select u.first_name, u.last_name,u.emailid, u.phone, v.name,v.schedule_time, l.locationName from " +
                "user_details u LEFT JOIN vehicle v on v.user_id = u.user_id  " +
                "LEFT JOIN location l on v.location_id = l.location_id " +
                "where u.user_id = ?";
        Cursor c = db.rawQuery(operatorDetaolsQuery, new String[]{userID});
        if (c.getCount() > 0) {
            c.moveToFirst();
            UserDetails user;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                odFirstNameDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                odLastNameDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_LNAME)));
                odPhoneDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_PHONE)));
                odEmailDesctv.setText(c.getString(c.getColumnIndex(Resources.USER_DETAILS_EMAIL_ID)));
                odLocationDesctv.setText(c.getString(c.getColumnIndex(Resources.LOCATION_NAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                odVehicleDesctv.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
                odScheduleDesctv.setText(c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME)) == null ? Status.UNASSIGNED.getDescription() : c.getString(c.getColumnIndex(Resources.VEHICLE_SCHEDULE_TIME)));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
        String role = preferences.getString("userRole", "");
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
                viewOrders();
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

    private void viewOrders() {
        Intent myint = new Intent(this, ManagerOrderDetails.class);
        startActivity(myint);
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



