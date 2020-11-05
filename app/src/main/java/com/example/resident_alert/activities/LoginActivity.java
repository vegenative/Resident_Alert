package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resident_alert.SessionManager;
import com.example.resident_alert.network.CheckInternet;
import com.example.resident_alert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText phone_et;
    private TextView signUp_tv;
    private CountryCodePicker countryCodePicker;
    private FirebaseAuth fAuth;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //editText
        phone_et = (EditText) findViewById(R.id.email_et);

        //Button
        login_btn = (Button) findViewById(R.id.login_btn);
        //TextView
        signUp_tv = (TextView) findViewById(R.id.noAccount_tv);
        //resetPassword_tv = (TextView) findViewById(R.id.resetPassword_tv);
        //LinearLayout
        //forgotPassword_ll = (LinearLayout) findViewById(R.id.forgotPassword_ll);
        //password_ll = (LinearLayout) findViewById(R.id.password_ll);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePickerLogin);


        //Firebase
        fAuth = FirebaseAuth.getInstance();


        // if is back from verification show password
        isLogin = getIntent().getBooleanExtra("isLogin",true);




        // check if values are in session.... if yes, set values to edit texts
        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phone_et.setText(rememberMeDetails.get(sessionManager.KEY_PHONE_rememberme));
        }

///////////////////////////////////////OnClick/////////////////////////////////////////////////////

        //login_btn
        login_btn.setOnClickListener(v -> {
            String phone = phone_et.getText().toString().trim();
            String fullPhone = "+" + countryCodePicker.getFullNumber() + phone;


            //if is not connected to internet
            CheckInternet checkInternet = new CheckInternet();
            if(!checkInternet.isConnected(this)){
                showCustomDialog();
            }

            if(TextUtils.isEmpty(phone)){
                phone_et.setError("Pole nie może być puste ");
                phone_et.requestFocus();
            }
            else if(phone.charAt(0)=='0'){
                phone = phone.substring(1); //if user adds 0 on start, it will be removed
            }
            else if(phone.length()!= 9){
                phone_et.setError("Numer telefonu musi posiadać 9 cyfr");
                phone_et.requestFocus();
            }


            else {

                //show progress bar
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progres_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                //if User click Login for the first time go to smsVerification
                // give phone data to verified
                Intent intent = new Intent(LoginActivity.this, SmsVerificationActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("fullPhone",fullPhone);
                intent.putExtra("isLogin",true);
                progressDialog.dismiss();
                startActivity(intent);

            }
        });

//SignUp_tv
        signUp_tv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });

    }


///////////////////////////////////////OnStart/////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();

        ///
        /// to idzie w onStart() w HomeActivity
        ///
        ///
        //if user is already logged in
        /*
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        }

         */

    }

///////////////////////////////////////Methods/////////////////////////////////////////////////////


    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Aby przejść dalej połącz się z siecią")
                .setCancelable(false)
                .setPositiveButton("Połącz", (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Zamknij", (dialog, which) -> {
                    startActivity(new Intent(getApplicationContext(), StartActivity.class));
                    finish();
                });

    }



}