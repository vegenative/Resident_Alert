package com.example.resident_alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText phone_et, password_et;
    private TextView signUp_tv,resetPassword_tv;
    private LinearLayout forgotPassword_ll;
    private CountryCodePicker countryCodePicker;

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

//Firebase
        fAuth = FirebaseAuth.getInstance();

///////////////////////////////////////OnClick/////////////////////////////////////////////////////

//login_btn
        login_btn.setOnClickListener(v -> {
            String phone = phone_et.getText().toString().trim();
            String fullPhone = "+" + countryCodePicker.getFullNumber() + phone;
            String password = password_et.getText().toString().trim();

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
                if(!isConnected(this)){
                    showCustomDialog();
                }


                //check user phone and password from the firebase
                Query checkUserTelephone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("telephone").equalTo(fullPhone);


                checkUserTelephone.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            phone_et.setError(null);

                            String systemPassword = snapshot.child(fullPhone).child("password").getValue(String.class);
                            if(systemPassword.equals(password)){
                                password_et.setError(null);

                                String nameCurrentuser = snapshot.child(fullPhone).child("name").getValue(String.class); //pobieramy imię użytkowniak z bazy danych

                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                            else{
                                password_et.setError("Nieprawidłowe hasło");
                                password_et.requestFocus();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Nie ma takiego konta o podanym numerze telefonu",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//SignUp_tv
        signUp_tv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });

//resetPassword_tv
        resetPassword_tv.setOnClickListener(v -> {

        });

    }





///////////////////////////////////////OnStart/////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();

        //if user is already login
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        }
    }

///////////////////////////////////////Methods/////////////////////////////////////////////////////

// chceck internet connection
private boolean isConnected(LoginActivity loginActivity) {
    ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    if(wifiConn !=null && wifiConn.isConnected() || mobileConn!=null && mobileConn.isConnected()){
        return true;
    }else{
        return true;
    }
}
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Aby przejść dalej połącz się z siecią")
                .setCancelable(false)
                .setPositiveButton("Połącz", (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Zamknij", (dialog, which) -> {
                    startActivity(new Intent(getApplicationContext(),StartActivity.class));
                    finish();
                });

    }

//reset password

}