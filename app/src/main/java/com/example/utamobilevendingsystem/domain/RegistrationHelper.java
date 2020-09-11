package com.example.utamobilevendingsystem.domain;


import android.app.Activity;
import android.app.AppComponentFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;


public abstract class RegistrationHelper extends AppCompatActivity {
    String password,fullName,userName,email,phone,city,state,dateOfBirth,confirmPass,address;
    ArrayList<String> emailPhone = new ArrayList<>();


    public boolean emailExists(String email){
        return emailPhone.contains(email);

    }
    public boolean phoneExists(String phone){
        return emailPhone.contains(phone);

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
    public boolean verifyConfirmPass(String confirmPass,String password){
        return (this.password).equals(this.confirmPass);
    }
    protected abstract void sendData();

    public String getPassword(EditText editText){
        this.password=editText.getText().toString();
        return  this.password;
    }
    public String getFullName(EditText editText){
        this.fullName=editText.getText().toString();
        return  this.fullName;
    }
    public String getUserName(EditText editText){
        this.userName=editText.getText().toString();
        return  this.userName;
    }
    public String getEmail(EditText editText){
        this.email=editText.getText().toString();
        return  this.email;
    }
    public String getPhone(EditText editText){
        this.phone=editText.getText().toString();
        return  this.phone;
    }
    public String getCity(EditText editText){
        this.city=editText.getText().toString();
        return  this.city;
    }
    public String getAddress(EditText editText){
        this.address=editText.getText().toString();
        return  this.address;
    }
    public String getState(Spinner spinner){
        this.state=spinner.getSelectedItem().toString();
        return  this.state;
    }
    public String getDateOfBirth(EditText editText){
        this.dateOfBirth=editText.getText().toString();
        return  this.dateOfBirth;
    }
    public String getConfirmPass(EditText editText){
        this.confirmPass=editText.getText().toString();
        return  this.confirmPass;
    }
}
