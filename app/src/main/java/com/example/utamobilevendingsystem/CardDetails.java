package com.example.utamobilevendingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utamobilevendingsystem.HomeScreens.UserHomeScreen;
import com.example.utamobilevendingsystem.domain.CardType;
import com.example.utamobilevendingsystem.domain.Payments;
import com.example.utamobilevendingsystem.domain.Status;
import com.example.utamobilevendingsystem.users.UserOrderDetails;

import java.text.DecimalFormat;

public class CardDetails extends AppCompatActivity {
    EditText expiryED,cvvED,cardNumberED;
    TextView totalPrice,taxAmt;
    Button validate;
    int cvv;
    String cardNumber;
    String expiry;
    int userId;
    double total,tax;
    private static DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        setContentView(R.layout.activity_card_details);
        Intent newint = getIntent();
        userId=newint.getIntExtra("uid",0);
        total=newint.getDoubleExtra("total",0.0);
        expiryED= findViewById(R.id.expiry);
        cvvED= findViewById(R.id.cvv);
        cardNumberED= findViewById(R.id.cardNumber);
        totalPrice=findViewById(R.id.totalPrice);
        taxAmt= findViewById(R.id.taxAmt);
        totalPrice.setText("Cart Total : $"+total);
        generateTax(total);
        validate= findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expiry= expiryED.getText().toString();
                cardNumber= cardNumberED.getText().toString();
                boolean flag=true;
                if(cardNumberED.getText().toString().length()!=16){
                    flag=false;
                    cardNumberED.setError("Please enter 16 digit card number");
                }
                if(cvvED.getText().toString().length()!=3){
                    flag=false;
                    cvvED.setError("Please enter 3 digit CVV");
                }

                if(flag){
                    cvv= Integer.parseInt(cvvED.getText().toString());
                    Payments payments = new Payments();
                    payments.setExpirationDate(expiry);
                    payments.setCardType(CardType.CREDIT);
                    ContentValues cardDetails= new ContentValues();
                    cardDetails.put("user_id",userId);
                    cardDetails.put("card_number",cardNumber);
                    cardDetails.put("expiration_date",expiry);
                    cardDetails.put("card_type",CardType.CREDIT.name());
                    SQLiteDatabase db = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
                    db.insert(Resources.TABLE_PAYMENTS,null, cardDetails);
                    Intent myint= new Intent(CardDetails.this,OrderConfirmation.class);
                    myint.putExtra("userid",userId);
                    myint.putExtra("totalPrice",total+tax);
                    startActivity(myint);
                }
            }
        });
    }

    private void generateTax(double total) {
        tax= (8.25/100)*total;
        taxAmt.setText("Tax : $"+ df.format(tax));
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
