package com.example.resident_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    public EditText email_et, password_et,cnfPassword_et;
    private Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //button
        signUpBtn = (Button) findViewById(R.id.signup_button);
        //edit text
        email_et = (EditText) findViewById(R.id.emailRegister_et);
        password_et = (EditText) findViewById(R.id.password_et);
        cnfPassword_et = (EditText) findViewById(R.id.cnfPassword_et);
    }




    public void goToLoginScreen(View view) {
        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    public void goToSignUp(View view) {
        String email = email_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        String cnfPassword = cnfPassword_et.getText().toString().trim();




        if(cnfPassword.equals(password) && validateEmail(email)){

            //pass values to smsVerificationActivity
            passValuesToActivity(email,password);
            //go to SignUpActivity
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        else{
            cnfPassword_et.setError("Hasła muszą być takie same");
        }
    }
    private void passValuesToActivity(String email,String password){
        //pass all fields to the next activity
        Intent goToActivity = new Intent(SignInActivity.this,SmsVerification.class);
        goToActivity.putExtra("email",email);
        goToActivity.putExtra("password",password);
        //powinien dac dane
        
    }

    private boolean validateEmail(String email){
        if (TextUtils.isEmpty(email)){
            email_et.setError("Pole nie może być puste");
            email_et.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError("Pole musi być adresem E-mail");
            email_et.requestFocus();
            return false;
        }
        else
        {
            return true;
        }

    }

}