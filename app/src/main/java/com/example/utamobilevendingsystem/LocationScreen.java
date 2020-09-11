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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.OperatorHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.users.UserOrderDetails;

public class LocationScreen extends AppCompatActivity {

    DatabaseHelper dbHelper;
    Cursor c, d;
    SQLiteDatabase db;
    int userID;
    String role;
    String vehicleID, screen_ref;
    Button VIEW_SCHEDULE;
    TextView cooperUtaTV, nedderGreekTV, davisMitchellTV, cooperMitchellTV, oakUtaTV, spanioloWTV, spanioloMitchellTv, centerMitchellTV, removeAllocationTV;
    String cooperUta, neederGreek, davisMitchell, cooperMitchell, oakUta, spanioloW, spanioloMithcell, centerMitchell, removeAllocation;
    boolean isCallingActivityVehicleDetailScreen;

    private void fetchSharedPref() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        userID = prefs.getInt("userid", 0);
        role = prefs.getString("userRole", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_screen);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        fetchSharedPref();
        vehicleID = getIntent().getStringExtra("vehicleID");
        screen_ref = getIntent().getStringExtra("screen_ref");
        if (null != getIntent().getStringExtra("callingActivity")) {
            isCallingActivityVehicleDetailScreen = (getIntent().getStringExtra("callingActivity")).contains("VehicleDetailsScreen") ? true : false;
        }
        cooperUtaTV = findViewById(R.id.cooperUtaTV);
        nedderGreekTV = findViewById(R.id.nedderGreekTV);
        davisMitchellTV = findViewById(R.id.davisMitchellTV);
        cooperMitchellTV = findViewById(R.id.cooperMitchellTV);
        oakUtaTV = findViewById(R.id.oakUtaTV);
        spanioloWTV = findViewById(R.id.spanioloWTV);
        spanioloMitchellTv = findViewById(R.id.spanioloMitchellTv);
        centerMitchellTV = findViewById(R.id.centerMitchellTV);
        removeAllocationTV = findViewById(R.id.removeAllocationTV);

        VIEW_SCHEDULE = findViewById(R.id.updatelocation_schedule);

        if (isCallingActivityVehicleDetailScreen) {
            removeAllocationTV.setVisibility(View.VISIBLE);
        }

        cooperUta = cooperUtaTV.getText().toString();
        neederGreek = nedderGreekTV.getText().toString();
        davisMitchell = davisMitchellTV.getText().toString();
        cooperMitchell = cooperMitchellTV.getText().toString();
        oakUta = oakUtaTV.getText().toString();
        spanioloW = spanioloWTV.getText().toString();
        spanioloMithcell = spanioloMitchellTv.getText().toString();
        centerMitchell = centerMitchellTV.getText().toString();
        removeAllocation = removeAllocationTV.getText().toString();
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        String role = prefs.getString("userRole", "");
        if (role != null && role.equals("User") || isCallingActivityVehicleDetailScreen) {
            onClicks();
        }
        if (role.equals("Manager")) {
            VIEW_SCHEDULE.setVisibility(View.VISIBLE);
        }
        if (screen_ref != (null)) {
            if (screen_ref.equals("hidebutton")) {
                VIEW_SCHEDULE.setVisibility(View.INVISIBLE);
                screen_ref = "";
            }
        }

        VIEW_SCHEDULE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewschedule();
            }
        });


        String VEHICLE_DETAILS_SCREEN_QUERY_FOR_OPTR = "select v.name, l.locationName, v.type, v.availability, l.schedule, u.first_name, v.user_id, v.schedule_time " +
                "from vehicle v LEFT JOIN location l on l.location_id = v.location_id " +
                "LEFT JOIN user_details u on v.user_id = u.user_id WHERE u.user_id =\"" + userID + "\"";    //Query for getting all details of the operator from DB
        c = db.rawQuery(VEHICLE_DETAILS_SCREEN_QUERY_FOR_OPTR, null);

    }

    private void onClicks() {
        cooperUtaTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("Cooper And UTA Blvd", "1");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", cooperUta);
                myint.putExtra("id", 1);
                startActivity(myint);
            }
        });
        nedderGreekTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("W Nedderman & Greek Row", "2");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", neederGreek);
                myint.putExtra("id", 2);
                startActivity(myint);
            }

        });
        davisMitchellTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("S Davis & W Mitchell", "3");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", davisMitchell);
                myint.putExtra("id", 3);
                startActivity(myint);
            }

        });
        cooperMitchellTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("Cooper & W Mitchell", "4");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", centerMitchell);
                myint.putExtra("id", 4);
                startActivity(myint);
            }
        });
        oakUtaTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("S Oak & UTA Blvd", "5");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", oakUta);
                myint.putExtra("id", 5);
                startActivity(myint);
            }
        });
        spanioloWTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("Spaniolo & W 1st", "6");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", spanioloW);
                myint.putExtra("id", 6);
                startActivity(myint);
            }
        });
        spanioloMitchellTv.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("Spaniolo & W Mitchell", "7");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", spanioloMithcell);
                myint.putExtra("id", 7);
                startActivity(myint);
            }
        });
        centerMitchellTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation("S Center & W Mitchell", "8");
            } else {
                Intent myint = new Intent(LocationScreen.this, UserOrder.class);
                myint.putExtra("location", centerMitchell);
                myint.putExtra("id", 8);
                startActivity(myint);
            }
        });

        removeAllocationTV.setOnClickListener(v -> {
            if (isCallingActivityVehicleDetailScreen) {
                updateVehicleLocation(Status.UNASSIGNED.getDescription(), "null");
            }
        });

    }

    public void viewschedule() {

        Intent intent = new Intent(LocationScreen.this, UpdateLocationSchedule.class);
        startActivity(intent);
    }

    private void updateVehicleLocation(String locationName, String locationID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resources.VEHICLE_LOCATION_ID, locationID);
        if ("null".equalsIgnoreCase(locationID)) {
            contentValues.putNull(Resources.VEHICLE_SCHEDULE_TIME);
        }
        db.update(Resources.TABLE_VEHICLE, contentValues, "vehicle_id = ?", new String[]{vehicleID});
        Intent output = new Intent();
        output.putExtra("locationName", locationName);
        setResult(RESULT_OK, output);
        finish();
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
        if ("Operator".equalsIgnoreCase(role)) {
            menu.findItem(R.id.Optr_vehicledetails).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
            case R.id.Optr_vehicledetails:
                vehicleSearch_optr();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
                Homescreen(role);
                return true;
            case R.id.change_password:
                changePassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(getApplicationContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
        startActivity(logout);
    }

    private void vehicleSearch() {
        Intent myint = new Intent(LocationScreen.this, VehicleScreen.class);
        startActivity(myint);
    }

    private void viewOrders(String role) {
        if (role.equals("User")) {
            Intent viewOrders = new Intent(this, UserOrderDetails.class);
            viewOrders.putExtra("userId", String.valueOf(userID));
            startActivity(viewOrders);
        }
        if (role.equals("Manager")) {
            Intent myint = new Intent(this, ManagerOrderDetails.class);
            startActivity(myint);
        }
        if (role.equals("Operator")) {
            Intent viewOrders = new Intent(this, OperatorOrderDetails.class);
            viewOrders.putExtra("userId", String.valueOf(userID));
            startActivity(viewOrders);
        }
    }

    private void vehicleSearch_optr() {
        if (c.getCount() > 0) {   //checking if operator has a vehicle assigned
            Intent op_vehicle = new Intent(LocationScreen.this, VehicleDetailsScreen.class);
            op_vehicle.putExtra("flag", "1");   //Sending a flag variable "1" as well
            startActivity(op_vehicle);
        } else {
            Toast.makeText(getApplicationContext(), "No Vehicle assigned for this operator.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Homescreen(String role) {
        if (role.equals("Manager")) {
            Intent myint = new Intent(this, ManagerHomeScreen.class);
            startActivity(myint);
        }
        if (role.equals("User")) {
            Intent myint = new Intent(this, UserHomeScreen.class);
            startActivity(myint);
        }
        if (role.equals("Operator")) {
            Intent myint = new Intent(this, OperatorHomeScreen.class);
            startActivity(myint);
        }
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(getApplicationContext(), ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList() {
        Intent changePasswordIntent = new Intent(getApplicationContext(), LocationScreen.class);
        startActivity(changePasswordIntent);
    }

}
//tc
