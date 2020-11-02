package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
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
    private EditText phone_et, password_et;
    private TextView signUp_tv,resetPassword_tv;
    private LinearLayout forgotPassword_ll;
    private CountryCodePicker countryCodePicker;
    private Switch rememberMe_switch;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //editText
        phone_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
//Button
        login_btn = (Button) findViewById(R.id.login_btn);
//TextView
        signUp_tv = (TextView) findViewById(R.id.noAccount_tv);
        resetPassword_tv = (TextView) findViewById(R.id.resetPassword_tv);
//LinearLayout
        forgotPassword_ll = (LinearLayout) findViewById(R.id.forgotPassword_ll);

        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePickerLogin);
        rememberMe_switch = (Switch) findViewById(R.id.rememberMe_switch);

//Firebase
        fAuth = FirebaseAuth.getInstance();

        // check if values are in session.... if yes, set values to edit texts
        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phone_et.setText(rememberMeDetails.get(sessionManager.KEY_PHONE_rememberme));
            password_et.setText((rememberMeDetails.get(sessionManager.KEY_PASSWORD)));
        }

///////////////////////////////////////OnClick/////////////////////////////////////////////////////

//login_btn
        login_btn.setOnClickListener(v -> {
            String phone = phone_et.getText().toString().trim();
            String fullPhone = "+" + countryCodePicker.getFullNumber() + phone;
            String password = password_et.getText().toString().trim();

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
            else if(TextUtils.isEmpty(password)){
                password_et.setError("Pole nie może być puste");
                password_et.requestFocus();
            }
            else if(password.length() < 6){
                password_et.setError("Hasło musi posiadać co najmniej 6 znaków");
                password_et.requestFocus();
            }
            else if(phone.isEmpty() && password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Uzupełnij pola", Toast.LENGTH_SHORT).show();
            }
            else {

                //show progress bar
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progres_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                //check user phone and password from the firebase
                Query checkUserTelephone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("telephone").equalTo(fullPhone);


                checkUserTelephone.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            phone_et.setError(null);

                            // RememberMe
                            if(rememberMe_switch.isChecked()){
                                final String phone = phone_et.getText().toString().trim();
                                SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
                                sessionManager.createRememberMeSession(phone,password);

                            }

                            String systemPassword = snapshot.child(fullPhone).child("password").getValue(String.class);
                            if(systemPassword.equals(password)){
                                password_et.setError(null);

<<<<<<< HEAD:app/src/main/java/com/example/resident_alert/activities/LoginActivity.java
                                //get data from database
                                String name_user = snapshot.child(fullPhone).child("name").getValue(String.class);
                                String surname_user = snapshot.child(fullPhone).child("surname").getValue(String.class);
                                String email_user = snapshot.child(fullPhone).child("email").getValue(String.class);
                                String phone_user = snapshot.child(fullPhone).child("telephone").getValue(String.class);
                                String city_user = snapshot.child(fullPhone).child("city").getValue(String.class);
                                String street_user = snapshot.child(fullPhone).child("street").getValue(String.class);
                                String block_user = snapshot.child(fullPhone).child("block").getValue(String.class);
                                String flatLatter_user = snapshot.child(fullPhone).child("flatLetter").getValue(String.class);
                                String flat_User = snapshot.child(fullPhone).child("flat").getValue(String.class);

=======
                                String nameCurrentuser = snapshot.child(fullPhone).child("name").getValue(String.class); //pobieramy imię użytkowniak z bazy danych
                                String phoneCurrentuser = snapshot.child(fullPhone).child("telephone").getValue(String.class);
>>>>>>> KETickets:app/src/main/java/com/example/resident_alert/LoginActivity.java

                                //Create a user Session with data from database
                                SessionManager sessionManager = new SessionManager(LoginActivity.this, SessionManager.SESSION_USERSESSION);
                                sessionManager.createLoginSession(name_user,surname_user,email_user,phone_user
                                        ,city_user,street_user,block_user,flatLatter_user,flat_User);

                                //end progress bar
                                progressDialog.dismiss();

                                // go to new activity
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                intent.putExtra("phone",phoneCurrentuser);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                //end progress bar
                                progressDialog.dismiss();

                                password_et.setError("Nieprawidłowe hasło");
                                password_et.requestFocus();
                            }
                        }
                        else {
                            //end progress bar
                            progressDialog.dismiss();

                            Toast.makeText(LoginActivity.this,"Nie ma takiego konta o podanym numerze telefonu",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //end progress bar
                        progressDialog.dismiss();
                        
                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//SignUp_tv
        signUp_tv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

//resetPassword_tv
        resetPassword_tv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }





///////////////////////////////////////OnStart/////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();

        //if user is already login
//        FirebaseUser currentUser = fAuth.getCurrentUser();
//        if(currentUser!=null){
//            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
//            finish();
//        }
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

    private void showProgressDialog(){

    }


}