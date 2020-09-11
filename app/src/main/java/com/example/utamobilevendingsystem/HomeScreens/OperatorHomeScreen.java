package com.example.utamobilevendingsystem.HomeScreens;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.utamobilevendingsystem.ChangePassword;
import com.example.utamobilevendingsystem.DatabaseHelper;
import com.example.utamobilevendingsystem.LocationScreen;
import com.example.utamobilevendingsystem.LoginActivity;
import com.example.utamobilevendingsystem.OperatorOrderDetails;
import com.example.utamobilevendingsystem.Resources;
import com.example.utamobilevendingsystem.VehicleDetailsScreen;
import com.example.utamobilevendingsystem.VehicleScreen;
import com.example.utamobilevendingsystem.domain.RegistrationHelper;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.R;

public class OperatorHomeScreen extends RegistrationHelper {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    Cursor c;

    String firstName,lastName,username,dob,phoneNummber,email,address,city,state,zip;
    int userID;
    TextView fNameTV,lNameTV,usernameTV,dobTV,phoneNummberTV,emailTV,addressTV,cityTV,stateTV,zipTV;
    EditText emailET,addressET,cityET,stateET,zipET,phoneET,dobET;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        setContentView(R.layout.activity_vendor_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fNameTV = findViewById(R.id.fNameTV);
        lNameTV= findViewById(R.id.lNameTV);
        usernameTV= findViewById(R.id.userNameTV);
        dobTV= findViewById(R.id.dobTV);
        phoneNummberTV= findViewById(R.id.phoneTV);
        emailTV= findViewById(R.id.emailTV);
        addressTV= findViewById(R.id.addressTV);
        cityTV= findViewById(R.id.cityTV);
        stateTV= findViewById(R.id.stateTV);
        zipTV= findViewById(R.id.zipTV);
        emailET= findViewById(R.id.emailET);
        addressET= findViewById(R.id.addressET);
        cityET= findViewById(R.id.cityET);
        stateET= findViewById(R.id.stateET);
        zipET= findViewById(R.id.zipET);
        phoneET= findViewById(R.id.phoneET);
        dobET= findViewById(R.id.dobET);
        update = findViewById(R.id.updateProfile);
        fetchSharedPref();
        setUserProfile();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = getEmail(emailET);
                phoneNummber = getPhone(phoneET);
                city = getCity(cityET);
                state = getAddress(stateET);
                dob = getDateOfBirth(dobET);
                address=getAddress(addressET);
                zip=getAddress(zipET);
                boolean flag = true;
                if(city.length()<1){
                    cityET.setError("Enter a City");
                    flag=false;
                }
                if(state.length()<1){
                    stateET.setError("Enter a  State");
                    flag=false;
                }
                if(zip.length()<6){
                    stateET.setError("Enter a valid zip");
                    flag=false;
                }
                if(!verifyAddress(address)){
                    addressET.setError("Enter an Address");
                    flag=false;
                }
                if (!verifyPhone(phoneNummber)) {
                    phoneET.setError("Enter a valid 10 digit phone number");
                    flag = false;
                }
                if (!verifyEmail(email)) {
                    emailTV.setError("Enter a valid e-mail address");
                    flag = false;
                }
                if (!verifydob(dob)) {
                    dobET.setError("Enter a valid date");
                    flag = false;
                }
                if (flag) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OperatorHomeScreen.this);
                    //new AlertDialog.Builder(getApplication())
                    builder.setTitle("Update Profile");
                    builder.setMessage("Are you sure you want to make these changes to your profile?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
                                    editor.putString("address",address);
                                    editor.putString("city",city);
                                    editor.putString("state",state);
                                    editor.putString("email",email);
                                    editor.putString("phone",phoneNummber);
                                    editor.putString("dob",dob);
                                    editor.putString("zip",zip);
                                    editor.apply();

                                    ContentValues user_details= new ContentValues();
                                    user_details.put("dob",dob);
                                    user_details.put("phone",phoneNummber);
                                    user_details.put("emailid",email);
                                    user_details.put("address",address);
                                    user_details.put("city",city);
                                    user_details.put("state",state);
                                    user_details.put("zip",zip);
                                    SQLiteDatabase db = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
                                    db.update(Resources.TABLE_USER_DETAILS,user_details, "user_id="+userID,null);
                                    Log.i("Update","Updated");
                                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });



           //condition check for vehicle assigned for operator


            String VEHICLE_DETAILS_SCREEN_QUERY_FOR_OPTR = "select v.name, l.locationName, v.type, v.availability, l.schedule, u.first_name, v.user_id, v.schedule_time " +
                    "from vehicle v LEFT JOIN location l on l.location_id = v.location_id " +
                    "LEFT JOIN user_details u on v.user_id = u.user_id WHERE u.first_name =\"" + firstName + "\"";    //Query for getting all details of the operator from DB
            System.out.println("--------------------------------------------\n\n\n\n\n\n" + VEHICLE_DETAILS_SCREEN_QUERY_FOR_OPTR);
            c = db.rawQuery(VEHICLE_DETAILS_SCREEN_QUERY_FOR_OPTR, null);


        }

    private void setUserProfile() {
        fNameTV.setText("First Name: "+firstName);
        lNameTV.setText("Last Name: "+lastName);
        usernameTV.setText("Welcome, "+username);
        dobTV.setText("Date of Birth: ");
        dobET.setText(dob);
        phoneNummberTV.setText("Phone: ");
        phoneET.setText(phoneNummber);
        emailTV.setText("Email: ");
        emailET.setText(email);
        addressTV.setText("Address: ");
        addressET.setText(address);
        cityTV.setText("City: ");
        cityET.setText(city);
        stateTV.setText("State: ");
        stateET.setText(state);
        zipTV.setText("ZIP: ");
        zipET.setText(zip);
    }

    private void fetchSharedPref() {
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        firstName =prefs.getString("fname","");
        lastName =prefs.getString("lname","");
        username =prefs.getString("username","");
        dob =prefs.getString("dob","");
        phoneNummber =prefs.getString("phone","");
        email =prefs.getString("email","");
        address =prefs.getString("address","");
        city =prefs.getString("city","");
        state =prefs.getString("state","");
        zip =prefs.getString("zip","");
        userID= prefs.getInt("userid",0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        menu.findItem(R.id.Optr_vehicledetails).setVisible(true);   //making the Vehicle Details tab visible for this page
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("currUser", MODE_PRIVATE);
        String role = preferences.getString("userRole","");
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_location:
                viewLocationList();
                return true;
            case R.id.menu_view_orders:
                role= role+"OrderDetails";
                if (role == "User"){
                    try {
                        Class<?> cls = Class.forName("com.example.utamobilevendingsystem.users."+role);
                        Intent homeIntent = new Intent(this, cls);
                        startActivity(homeIntent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        Class<?> cls = Class.forName("com.example.utamobilevendingsystem."+role);
                        Intent homeIntent = new Intent(this, cls);
                        startActivity(homeIntent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            case R.id.Optr_vehicledetails:
                vehicleSearch_optr();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_home:
                return true;
            case R.id.change_password:
                changePassword();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewOrders() {
        Intent viewOrders = new Intent(OperatorHomeScreen.this, OperatorOrderDetails.class);
        viewOrders.putExtra("userId", String.valueOf(userID));
        startActivity(viewOrders);
    }

    private void vehicleSearch_optr() {
        if (c.getCount() > 0) {   //checking if operator has a vehicle assigned
            Intent op_vehicle = new Intent(OperatorHomeScreen.this, VehicleDetailsScreen.class);
            op_vehicle.putExtra("flag", "1");   //Sending a flag variable "1" as well
            startActivity(op_vehicle);
        }
        else{
            Toast.makeText(getApplicationContext(),"No Vehicle assigned for this operator.",Toast.LENGTH_SHORT).show();
        }

    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(getApplicationContext(),"Logged out Successfully",Toast.LENGTH_SHORT).show();
        startActivity(logout);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(OperatorHomeScreen.this, ChangePassword.class);
        startActivity(changePasswordIntent);
    }

    private void viewLocationList(){
        Intent changePasswordIntent = new Intent(OperatorHomeScreen.this, LocationScreen.class);
        startActivity(changePasswordIntent);
    }

    @Override
    protected void sendData() {

    }
}

//tc
