package com.example.resident_alert.activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
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

        //countryCodePicker
        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePickerLogin);

        //if user is coming from smsVerification but don't have data i database set his number to et
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String _phone = getIntent().getStringExtra("phone");
            phone_et.setText(_phone);
        }
    }




    public void goToLoginScreen(View view) {

        //if user go back, log out if exist and back to LoginActivity
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().signOut();

            SessionManager sessionManager = new SessionManager(SignInActivity.this, SessionManager.SESSION_USERSESSION);
            sessionManager.logoutUserSession();
        }

        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    public void goToSignUp(View view) {

        String phone = phone_et.getText().toString().trim();
        String fullPhone = "+" + countryCodePicker.getFullNumber() + phone;



        validation(fullPhone,phone);
    }

    //pass values and goes to the next activity
    private void passValuesToActivity(String fullPhone,String phone){

        Intent goToActivity = new Intent(SignInActivity.this, SignUpActivity.class);

        goToActivity.putExtra("fullPhone",fullPhone);
        goToActivity.putExtra("phone",phone);


        startActivity(goToActivity);

    }

    private void validation(String fullPhone,String phone){
        if (TextUtils.isEmpty(phone)){
            phone_et.setError("Pole nie może być puste");
            phone_et.requestFocus();
        }
        else if (phone.length() != 9){
            phone_et.setError("Numer telefonu musi posiadać 9 cyfr");
            phone_et.requestFocus();

        }
        else{
            passValuesToActivity(fullPhone,phone);
        }


    }

}