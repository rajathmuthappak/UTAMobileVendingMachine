package com.example.utamobilevendingsystem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
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
import android.widget.Toast;
import com.example.utamobilevendingsystem.domain.UserDetails;
import java.util.ArrayList;
public class OperatorList extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String vehicleID;
    final String OPERATOR_LIST_QUERY = "select ud.first_name, ud.last_name, ud.user_id from user_details ud, user_creds uc where ud.user_id=uc.user_id and uc.role = \"Operator\" ORDER BY (ud.last_name) ASC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);
        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        vehicleID = getIntent().getStringExtra("vehicleID");
        ArrayList<UserDetails> operatorList = new ArrayList<>();
        ListView operatorListView = (ListView) findViewById(R.id.lvOperatorList);
        Cursor c = db.rawQuery(OPERATOR_LIST_QUERY, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            UserDetails user;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                user = new UserDetails();
                user.setfName(c.getString(c.getColumnIndex(Resources.USER_DETAILS_FNAME)));
                user.setlName(c.getString(c.getColumnIndex(Resources.USER_DETAILS_LNAME)));
                user.setUserId(c.getInt(c.getColumnIndex(Resources.USER_CREDS_USER_ID)));
                operatorList.add(user);
            }
        }
        OperatorListAdapter adapter = new OperatorListAdapter(this, R.layout.activity_operator_list_adapter, operatorList);
        operatorListView.setAdapter(adapter);
        if((getIntent().getStringExtra("callingActivity")).contains("ManagerHomeScreen")){
            operatorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = view.findViewById(R.id.textViewOperatorID);
                    Intent intent = new Intent(OperatorList.this,OperatorDetails.class);
                    intent.putExtra("userID", tv.getText().toString());
                    startActivity(intent);
                }
            });
        } else {
            operatorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateOperatorVehicle(((TextView)view.findViewById(R.id.textViewOperatorID)).getText().toString());
                    Intent output = new Intent();
                    output.putExtra("userName", ((TextView)view.findViewById(R.id.textViewOperatorName)).getText().toString());
                    setResult(RESULT_OK, output);
                    finish();
                }
            });
        }
    }
    private void updateOperatorVehicle(String userID){
        ContentValues contentValues1 = new ContentValues();
        contentValues1.putNull(Resources.VEHICLE_USER_ID);
        db.update(Resources.TABLE_VEHICLE, contentValues1, "user_id = ?", new String[] {userID});
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resources.VEHICLE_USER_ID, userID);
        db.update(Resources.TABLE_VEHICLE,contentValues, "vehicle_id = ?", new String[] {vehicleID});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        menu.findItem(R.id.app_bar_search).setVisible(true);
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
                String role = preferences.getString("userRole","");
                role= role+"HomeScreen";
                try {
                    Class<?> cls = Class.forName("com.example.utamobilevendingsystem.HomeScreens."+role);
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