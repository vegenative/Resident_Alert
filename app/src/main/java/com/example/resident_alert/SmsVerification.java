package com.example.resident_alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SmsVerification extends AppCompatActivity {

    private String codeBySystem,email,password,name,surname,phone,street,city,block,flatLetter,flat,stringTel;
    private TextView smsInfo;
    private PinView pinFromUser_pinView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        //TextView
        smsInfo = (TextView) findViewById(R.id.smsInfo);
        //pinView
        pinFromUser_pinView = (PinView) findViewById(R.id.pinView);
        //Firebase

        phone = getIntent().getStringExtra("phone");
        stringTel = getIntent().getStringExtra("stringTel");
        smsInfo.setText("Sms weryfikujący został wysłany na podany numer telefonu \n" + stringTel);



        sendPhoneVerification(phone);

    }

    //verification phone
    private void sendPhoneVerification(String stringTel){

        //get data from previous activity
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        name = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");
        phone = getIntent().getStringExtra("phone");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        block = getIntent().getStringExtra("block");
        flatLetter = getIntent().getStringExtra("flatLetter");
        flat = getIntent().getStringExtra("flat");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                fCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks fCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

             codeBySystem = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String smsCode = phoneAuthCredential.getSmsCode();
            if(smsCode != null){
                pinFromUser_pinView.setText(smsCode);
                verifyCode(smsCode);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(SmsVerification.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String smsCode){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,smsCode);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(SmsVerification.this, task -> {

                    if(task.isSuccessful()){

                        StoreUserData(email,password,name,surname,phone,city,street,block,flatLetter,flat);
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        finish();

                    }
                    else{
                        Toast.makeText(SmsVerification.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //OnClickButton
    public void goToMenu(View view) {

        String code = pinFromUser_pinView.getText().toString();

        if(!code.isEmpty()){
            verifyCode(code);
        }

    }

    //Firebase store data
    private void StoreUserData(String email,String password,String name,String surname,
                               String phone,String city,String street,String block,String flatLetter,String flat){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        //nie zapisuje email i password z SignInActivity
        UserHelperClass addNewUser =
                new UserHelperClass(email,password,name,surname,phone,city,street,block,flatLetter,flat);


        reference.child(phone).setValue(addNewUser);
    }

    public void goToSignUp(View view) {

        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        //zapisywanie wpisanych wczesniej pól
    }
}