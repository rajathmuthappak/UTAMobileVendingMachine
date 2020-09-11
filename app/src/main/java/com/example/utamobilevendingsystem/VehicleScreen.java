package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.Vehicle;
import com.example.utamobilevendingsystem.domain.VehicleType;

import java.util.ArrayList;

public class VehicleScreen extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences preferences;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_screen);

        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        preferences= getSharedPreferences("currUser", MODE_PRIVATE);
        role= preferences.getString("userRole", "");
        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        ListView vehicleListView = (ListView) findViewById(R.id.vehicleList);

        String VEHICLE_LOCATION_QUERY = "select v.vehicle_id, v.name, v.type, v.availability, v.location_id, l.locationName from vehicle v left join location l on v.location_id = l.location_id";
        Cursor c = db.rawQuery(VEHICLE_LOCATION_QUERY, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            Vehicle vehicle;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                vehicle = new Vehicle();
                vehicle.setVehicleId(c.getInt(c.getColumnIndex(Resources.VEHICLE_ID)));
                vehicle.setVehicleName(c.getString(c.getColumnIndex(Resources.VEHICLE_NAME)));
                vehicle.setVehicleType("Food Truck".equalsIgnoreCase(c.getString(c.getColumnIndex(Resources.VEHICLE_TYPE))) ? VehicleType.FOOD_TRUCK : VehicleType.CART);
                vehicle.setAvailability((c.getString(c.getColumnIndex(Resources.VEHICLE_AVAILABILITY)).equalsIgnoreCase(Status.AVAILABLE.getDescription()) ? Status.AVAILABLE : Status.UNAVAILABLE));
                vehicle.setLocationId(c.getInt(c.getColumnIndex(Resources.VEHICLE_LOCATION_ID)));
                vehicle.setLocationName(c.getString(c.getColumnIndex(Resources.LOCATION_NAME)));
                vehicleList.add(vehicle);
            }
        }

        VehicleListAdapter adapter = new VehicleListAdapter(this, R.layout.vehicle_list_adaptor_view_layout, vehicleList);
        vehicleListView.setAdapter(adapter);
        vehicleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = view.findViewById(R.id.vehicleID);
                Intent intent = new Intent(VehicleScreen.this, VehicleDetailsScreen.class);
                intent.putExtra("vehicleID", tv.getText().toString());
                intent.putExtra("flag", "2");   //sending a flag value of 2 as manager view

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        if(role.equals("Manager")){
            menu.findItem(R.id.app_bar_search).setVisible(true);
        }
        else if(role.equals("Operator")) {
            menu.findItem(R.id.Optr_vehicledetails).setVisible(true);
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
            case R.id.Optr_vehicledetails:
                vehicleSearch_optr();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
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

    private void vehicleSearch_optr() {
    }

    private void vehicleSearch() {
        Intent myint = new Intent(this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders() {
        SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
        String role = preferences.getString("userRole", "");
        if (role == "user") {

            role = role + "OrderDetails";
            try {
                Class<?> cls = Class.forName("com.example.utamobilevendingsystem.users." + role);
                Intent homeIntent = new Intent(this, cls);
                startActivity(homeIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {

            role = role + "OrderDetails";
            try {
                Class<?> cls = Class.forName("com.example.utamobilevendingsystem." + role);
                Intent homeIntent = new Intent(this, cls);
                startActivity(homeIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(this, LoginActivity.class);
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
