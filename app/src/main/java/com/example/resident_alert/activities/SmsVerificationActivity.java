package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.resident_alert.R;
import com.example.resident_alert.UserHelperClass;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SmsVerificationActivity extends AppCompatActivity {

    private String codeBySystem,email,password,name,surname,phone,street,city,block,flatLetter,flat,stringTel;
    private boolean resetPassword;
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



        sendPhoneVerification();

    }

    //verification phone
    private void sendPhoneVerification(){

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
        resetPassword = getIntent().getBooleanExtra("resetPassword",false);

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
            Toast.makeText(SmsVerificationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String smsCode){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,smsCode);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(SmsVerificationActivity.this, task -> {

                    if(task.isSuccessful()){

                        //if is called from ResetPasswordActivity
                        if(resetPassword == true){
                            updatePassword();
                        }
                        //if is called from SignInInActivity
                        else{
                            StoreUserData(email,password,name,surname,phone,city,street,block,flatLetter,flat);
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(SmsVerificationActivity.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

        UserHelperClass addNewUser =
                new UserHelperClass(email,password,name,surname,phone,street,block,city,flatLetter,flat);


        reference.child(phone).setValue(addNewUser);
    }

    public void goBack(View view) {
        // go back to previous activity depend on which one

        if(resetPassword == true){
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
        }
        else{
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        }

        //zapisywanie wpisanych wczesniej pól
    }

    private void updatePassword() {
        // chceck if is Verified Sms and back to Activity to change password
        Intent goNext = new Intent(SmsVerificationActivity.this,ResetPasswordActivity.class);
        goNext.putExtra("isVerified",true);
        goNext.putExtra("phone",phone);
        startActivity(goNext);
        finish();
    }
}