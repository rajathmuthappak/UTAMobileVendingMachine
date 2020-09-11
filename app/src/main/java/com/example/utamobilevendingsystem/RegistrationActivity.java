package com.example.utamobilevendingsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButtonUser;
    RadioButton radioButtonOperator;
    String role;
    EditText firstName;
    EditText lastName;
    EditText enterPassword;
    EditText confirmPassword;
    Button submitButton;
    EditText username;
    EditText dob;
    EditText utaid;
    EditText email;
    EditText phone;
    EditText city;
    EditText zip;
    EditText userState;
    EditText address;
    SQLiteDatabase dbObject;
    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        helper = new DatabaseHelper(this);
        dbObject = helper.getWritableDatabase();
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonUser = findViewById(R.id.radio_user);
        radioButtonOperator = findViewById(R.id.radio_operator);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        enterPassword = findViewById(R.id.enterPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        username = findViewById(R.id.username);
        dob = findViewById(R.id.dateOfBirth);
        utaid = findViewById(R.id.utaID);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        zip = findViewById(R.id.zipCode);
        userState = findViewById(R.id.state);
        address = findViewById(R.id.address);
        onRadioButtonClicked( new View(getApplicationContext()));

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String password = enterPassword.getText().toString();
                String confirmPwd = confirmPassword.getText().toString();
                String uname = username.getText().toString();
                String phNo = phone.getText().toString();
                String utaID ="";
                if(role.equalsIgnoreCase("user")){
                    utaID = utaid.getText().toString();
                }
                String dateOfBirth = dob.getText().toString();
                String mailAddress = email.getText().toString();
                String userCity = city.getText().toString();
                String zipCode = zip.getText().toString();
                String state = userState.getText().toString();
                String adress = address.getText().toString();

                if (password.equals(confirmPwd) && (password != "") && (confirmPwd!= "")) {
                    boolean flag = true;
                    if (!verifyUsername(uname)) {
                        flag = false;
                        Toast.makeText(RegistrationActivity.this, "Please try with a different username", Toast.LENGTH_SHORT).show();
                    }
                    else if (!verifyAddress(adress)){
                        flag = false;
                        Toast.makeText(RegistrationActivity.this, "Please enter Address in correct format", Toast.LENGTH_SHORT).show();
                    }
                    else if (!verifydob(dob.getText().toString())){
                        flag = false;
                        Toast.makeText(RegistrationActivity.this, "Please enter DOB in correct format", Toast.LENGTH_SHORT).show();
                    }
                    else if (!verifyEmail(mailAddress)){
                        flag = false;
                        Toast.makeText(RegistrationActivity.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    }

                    if (flag){
                        ContentValues contentValues=new ContentValues();
                        contentValues.put("username",uname);
                        contentValues.put("password",password);
                        contentValues.put("role", role);
                        long value = dbObject.insert(Resources.TABLE_USER_CREDS,null, contentValues);
                        ContentValues values = new ContentValues();
                        values.put("user_id", value);
                        values.put("username", uname);
                        values.put("first_name", fname);
                        values.put("last_name", lname);
                        if(role.equalsIgnoreCase("user")){
                            values.put("uta_id", utaID);
                        }

                        values.put("dob", dateOfBirth);
                        values.put("phone",phNo);
                        values.put("emailid",mailAddress);
                        values.put("address",adress);
                        values.put("city",userCity);
                        values.put("state",state);
                        values.put("zip",zipCode);
                        long id = dbObject.insert(Resources.TABLE_USER_DETAILS,null, values);
                        Log.i("Registration","Registration ID "+id);
                        Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void onRadioButtonClicked(View view){
        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        radioButtonUser = findViewById(selectedRadio);
        utaid = findViewById(R.id.utaID);
        role = radioButtonUser.getText().toString();
        if(!role.equalsIgnoreCase("user")){
            utaid.setVisibility(View.INVISIBLE);
        } else {
            utaid.setVisibility(View.VISIBLE);
        }
    }

    public boolean verifyUsername(String userName){
        for(int i=0;i<userName.length();i++){
            if(!Character.isLetterOrDigit(userName.charAt((i)))){
                return false;
            }
        }
        return true;
    }

    public boolean verifyName(String name){
        name=name.trim();
        if(name.length()<1){
            return false;
        }
        for(int i=0;i<name.length();i++){
            if(!Character.isAlphabetic(name.charAt((i))) || name.charAt(i)==' '){
                return false;
            }
        }
        return true;
    }
    public boolean verifyEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();

    }

    public boolean verifydob(String dateOfBirth){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
        sdfrmt.setLenient(false);
        try {
            sdfrmt.parse(dateOfBirth);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public boolean verifyPassword(String password){
        return password.length() >= 6;
    }
    public boolean verifyAddress(String address){
        return address.length() >= 1;
    }
    public boolean verifyPhone(String phone){
        if(phone.length()!=10){
            return false;
        }
        for(int i=0;i<phone.length();i++){
            if(Integer.parseInt(String.valueOf(phone.charAt(i)))>=0 ||Integer.parseInt(String.valueOf(phone.charAt(i)))<=9 ){
                continue;
            }
            else {
                return false;
            }
        }
        return true;

    }


}



