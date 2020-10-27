package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class SignInActivity extends AppCompatActivity {

    public EditText phone_et, password_et,cnfPassword_et;
    private Button signUpBtn;
    private CountryCodePicker countryCodePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //button
        signUpBtn = (Button) findViewById(R.id.signup_button);
        //edit text
        phone_et = (EditText) findViewById(R.id.phone_et);
        password_et = (EditText) findViewById(R.id.password_et);
        cnfPassword_et = (EditText) findViewById(R.id.cnfPassword_et);
        //countryCodePicker
        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePickerLogin);
    }




    public void goToLoginScreen(View view) {
        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    public void goToSignUp(View view) {

        String stringTel = phone_et.getText().toString().trim();
        String phone = "+" + countryCodePicker.getFullNumber() + stringTel;

        String password = password_et.getText().toString().trim();
        String cnfPassword = cnfPassword_et.getText().toString().trim();


        validation(phone,password,cnfPassword,stringTel);
    }

    //pass values and goes to the next activity
    private void passValuesToActivity(String phone,String password,String stringTel){

        Intent goToActivity = new Intent(SignInActivity.this,SignUpActivity.class);

        goToActivity.putExtra("phone",phone);
        goToActivity.putExtra("stringTel",stringTel);
        goToActivity.putExtra("password",password);

        startActivity(goToActivity);

    }

    private void validation(String phone,String password, String cnfPassword,String stringTel){
        if (TextUtils.isEmpty(stringTel)){
            phone_et.setError("Pole nie może być puste");
            phone_et.requestFocus();
        }
        else if (stringTel.length() != 9){
            phone_et.setError("Numer telefonu musi posiadać 9 cyfr");
            phone_et.requestFocus();

        }
        else if(password.length() < 6){
            password_et.setError("Hasło musi posiadać co najmniej 6 znaków");
            password_et.requestFocus();
        }
        else if(!cnfPassword.equals(password))
        {
            cnfPassword_et.setError("Hasła muszą być takie same");
            password_et.requestFocus();
        }
        else{
            passValuesToActivity(phone,password,stringTel);
        }


    }

}