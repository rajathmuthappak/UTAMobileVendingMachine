package com.example.utamobilevendingsystem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.utamobilevendingsystem.HomeScreens.ManagerHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.OperatorHomeScreen;
import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.domain.UserCredentials;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText usernameET,passwordET;
    String username,password;
    TextView register;
    SQLiteDatabase db;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        register = findViewById(R.id.register);
        db= dbHelper.getWritableDatabase();
        login = findViewById(R.id.button);
        insert();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameET.getText().toString();
                password=passwordET.getText().toString();
                switch (fetch(username, password)) {
                    case "User": {
                        Intent myInt = new Intent(LoginActivity.this, UserHomeScreen.class);
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(myInt);
                        break;
                    }
                    case "Operator": {
                        Intent myInt = new Intent(LoginActivity.this, OperatorHomeScreen.class);
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(myInt);
                        break;
                    }
                    case "Manager": {
                        Intent myInt = new Intent(LoginActivity.this, ManagerHomeScreen.class);
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(myInt);
                        break;
                    }
                    default:
                        usernameET.getText().clear();
                        passwordET.getText().clear();
                        usernameET.setError("Please enter correct username and password");
                        break;
                }
            }
        });



    }

    public void insert(){
        ContentValues items= new ContentValues();
        items.put("item_id","1");
        items.put("item_name","Sandwiches");
        items.put("item_price","5.75");
        db.insert(Resources.TABLE_ITEM,null, items);
        items.put("item_id","2");
        items.put("item_name","Drinks");
        items.put("item_price","1.5");
        db.insert(Resources.TABLE_ITEM,null, items);
        items.put("item_id","3");
        items.put("item_name","Snacks");
        items.put("item_price","1.25");
        db.insert(Resources.TABLE_ITEM,null, items);
        ContentValues location= new ContentValues();
        location.put("location_id","1");
        location.put("locationName","Cooper And UTA Blvd");
        location.put("schedule","2");
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","2");
        location.put("locationName","W Nedderman & Greek Row");
        location.put("schedule", 1);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","3");
        location.put("locationName","S Davis & W Mitchell");
        location.put("schedule", 2);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","4");
        location.put("locationName","Cooper & W Mitchell");
        location.put("schedule", 3);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","5");
        location.put("locationName","S Oak & UTA Blvd");
        location.put("schedule", 3);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","6");
        location.put("locationName","Spaniolo & W 1st");
        location.put("schedule", 4);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","7");
        location.put("locationName","Spaniolo & W Mitchell");
        location.put("schedule", 2);
        db.insert(Resources.TABLE_LOCATION,null, location);
        location.put("location_id","8");
        location.put("locationName","S Center & W Mitchell");
        location.put("schedule", 1);
        db.insert(Resources.TABLE_LOCATION,null, location);


        ContentValues vehicle= new ContentValues();
        vehicle.put("vehicle_id","1");
        vehicle.put("name","Truck 1");
        vehicle.put("type","Food Truck");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","2");
        vehicle.put("name","Truck 2");
        vehicle.put("type","Food Truck");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","3");
        vehicle.put("name","Cart 1");
        vehicle.put("type","Cart");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","4");
        vehicle.put("name","Cart 2");
        vehicle.put("type","Cart");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","5");
        vehicle.put("name","Cart 3");
        vehicle.put("type","Cart");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","6");
        vehicle.put("name","Cart 4");
        vehicle.put("type","Cart");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);
        vehicle.put("vehicle_id","7");
        vehicle.put("name","Cart 5");
        vehicle.put("type","Cart");
        vehicle.put("availability", Status.AVAILABLE.getDescription());
        db.insert(Resources.TABLE_VEHICLE,null, vehicle);

        ContentValues vehicle_inventory= new ContentValues();
        vehicle_inventory.put("id","1");
        vehicle_inventory.put("vehicle_id","1");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","2");
        vehicle_inventory.put("vehicle_id","1");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","3");
        vehicle_inventory.put("vehicle_id","1");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");

        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","4");
        vehicle_inventory.put("vehicle_id","2");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","5");
        vehicle_inventory.put("vehicle_id","2");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","6");
        vehicle_inventory.put("vehicle_id","2");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");

        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","7");
        vehicle_inventory.put("vehicle_id","3");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","8");
        vehicle_inventory.put("vehicle_id","3");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","9");
        vehicle_inventory.put("vehicle_id","3");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");

        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","10");
        vehicle_inventory.put("vehicle_id","4");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","11");
        vehicle_inventory.put("vehicle_id","4");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","12");
        vehicle_inventory.put("vehicle_id","4");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);

        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","13");
        vehicle_inventory.put("vehicle_id","5");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","14");
        vehicle_inventory.put("vehicle_id","5");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","15");
        vehicle_inventory.put("vehicle_id","5");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);

        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","16");
        vehicle_inventory.put("vehicle_id","6");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","17");
        vehicle_inventory.put("vehicle_id","6");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","18");
        vehicle_inventory.put("vehicle_id","6");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);


        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","19");
        vehicle_inventory.put("vehicle_id","7");
        vehicle_inventory.put("item_id","1");
        vehicle_inventory.put("quantity","10");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","20");
        vehicle_inventory.put("vehicle_id","7");
        vehicle_inventory.put("item_id","2");
        vehicle_inventory.put("quantity","5");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
        vehicle_inventory.put("id","21");
        vehicle_inventory.put("vehicle_id","7");
        vehicle_inventory.put("item_id","3");
        vehicle_inventory.put("quantity","7");
        db.insert(Resources.TABLE_VEHICLE_INVENTORY,null, vehicle_inventory);
    }

    public String fetch(String username,String password){
        String selectQuery = "SELECT  * FROM " + Resources.TABLE_USER_CREDS + " WHERE "
                + Resources.USER_CREDS_USERNAME + " = " + "'"+username +"'"+" AND " + Resources.USER_CREDS_PASSWORD  +" = "+"'"+password+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() > 0){
            c.moveToFirst();
        }
        else{
            return "";
        }

        UserCredentials userCredentials = new UserCredentials();
        int  uid=c.getInt(c.getColumnIndex(Resources.USER_CREDS_USER_ID));
        //Store current user id as cart_id for that user in the db
        ContentValues user_cart= new ContentValues();
        user_cart.put("user_id",String.valueOf(uid));
        user_cart.put("cart_id",String.valueOf(uid));
        db.insert(Resources.TABLE_USER_CART,null, user_cart);

        String userRole = c.getString(c.getColumnIndex(Resources.USER_CREDS_ROLE));
        String profileQuery= "SELECT * FROM "+ Resources.TABLE_USER_DETAILS+ " WHERE " +  Resources.USER_DETAILS_ID + " = " + uid;
        Cursor c1 = db.rawQuery(profileQuery, null);

        if (c1.getCount() >0){
            c1.moveToFirst();
        }

        SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
        editor.putInt("userid",(c1.getInt((c1.getColumnIndex(Resources.USER_DETAILS_ID)))));
        editor.putInt("utaid",(c1.getInt((c1.getColumnIndex(Resources.USER_DETAILS_UTA_ID)))));
        editor.putString("userRole",userRole);
        editor.putString("address",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ADDRESS)))));
        editor.putString("username",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_USERNAME)))));
        editor.putString("city",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_CITY)))));
        editor.putString("state",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_STATE)))));
        editor.putString("email",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_EMAIL_ID)))));
        editor.putString("fname",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_FNAME)))));
        editor.putString("lname",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_LNAME)))));
        editor.putString("phone",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_PHONE)))));
        editor.putString("dob",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_DOB)))));
        editor.putString("zip",(c1.getString((c1.getColumnIndex(Resources.USER_DETAILS_ZIP)))));
        editor.apply();
        return userRole;
    }


    public void navigateToRegister(View view) {
        register = findViewById(R.id.register);
        Intent navigateToRegister = new Intent(this, RegistrationActivity.class);
        startActivity(navigateToRegister);
    }
}
//tc
