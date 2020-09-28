package com.example.resident_alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText email_et, password_et;
    private TextView signUp_tv,resetPassword_tv;
    private LinearLayout forgotPassword_ll;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //editText
        email_et = (EditText) findViewById(R.id.emailRegister_et);
        password_et = (EditText) findViewById(R.id.password_et);
//Button
        login_btn = (Button) findViewById(R.id.login_btn);
//TextView
        signUp_tv = (TextView) findViewById(R.id.noAccount_tv);
        resetPassword_tv = (TextView) findViewById(R.id.resetPassword_tv);
//LinearLayout
        forgotPassword_ll = (LinearLayout) findViewById(R.id.forgotPassword_ll);

//Firebase
        fAuth = FirebaseAuth.getInstance();

///////////////////////////////////////OnClick/////////////////////////////////////////////////////

//login_btn
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email_et.setError("Pole nie może być puste ");
                    email_et.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email_et.setError("Pole musi być adresem e-mail");
                    email_et.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){
                    password_et.setError("Pole nie może być puste");
                    email_et.requestFocus();
                }
                else if(password.length()<6){
                    password_et.setError("Hasło musi posiadać co najmniej 6 znaków");
                    email_et.requestFocus();

                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Uzupełnij pola", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginUser(email,password);
                }
            }
        });

//SignUp_tv
        signUp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

//resetPassword_tv
        resetPassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

///////////////////////////////////////OnStart/////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();
        forgotPassword_ll.setVisibility(View.GONE);

        //if user is already login
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        }
    }

///////////////////////////////////////Methods/////////////////////////////////////////////////////

//Login
private void LoginUser(String email, String password) {
    fAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this,"Witaj ! ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    finish();
                }

            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(LoginActivity.this,"Nie ma takiego konta. \n Zresetuj hasło lub stwórz nowe konto",
                    Toast.LENGTH_LONG).show();
            forgotPassword_ll.setVisibility(View.VISIBLE);

        }
    });
}
//reset password
    private void resetPassword(){
        String email = email_et.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            email_et.setError("Pole nie może być puste");
        }
        // mail body doesn't fit
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError("Pole musi być adresem E-mail");
        }
        else{
            fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(LoginActivity.this, "Wiadomość została wysłana na podany E-mail", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Nie ma konta o takim E-mail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}