package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.resident_alert.R;
import com.example.resident_alert.SessionManager;
import com.example.resident_alert.UserHelperClass;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SmsVerificationActivity extends AppCompatActivity {

    private String codeBySystem,email,name,surname,phone,street,city,block,flatLetter,flat,fullPhone;
    private boolean isLogin;
    private TextView smsInfo;
    private PinView pinFromUser_pinView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        //TextView
        smsInfo = (TextView) findViewById(R.id.smsInfo);
        //pinView
        pinFromUser_pinView = (PinView) findViewById(R.id.pinView);
        //Firebase

        fullPhone = getIntent().getStringExtra("fullPhone");
        phone = getIntent().getStringExtra("phone");
        smsInfo.setText("Sms weryfikujący został wysłany na podany numer telefonu \n" + phone);




        sendPhoneVerification();

    }

    //verification phone
    private void sendPhoneVerification(){

        //get data from previous activity
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        surname = getIntent().getStringExtra("surname");
        //fullPhone = getIntent().getStringExtra("fullPhone");
        //phone = getIntent().getStringExtra("phone");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        block = getIntent().getStringExtra("block");
        flatLetter = getIntent().getStringExtra("flatLetter");
        flat = getIntent().getStringExtra("flat");
        isLogin = getIntent().getBooleanExtra("isLogin",false);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                fullPhone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                fCallbacks);

                //show progress bar
                progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progres_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks fCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeBySystem = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            progressDialog.dismiss();

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

                    //if User successful Auth (is already auth in firebase)
                    if(task.isSuccessful()){
                        Boolean isLogin = getIntent().getBooleanExtra("isLogin",false);
                        //if is called from SignIn,  first we need to save data in firebase and then check
                        if(!isLogin) {
                            StoreUserData(email,name,surname,fullPhone,city,street,block,flatLetter,flat);
                            isDataInFirebase(fullPhone);
                            progressDialog.dismiss();
                        }
                        //if is called from LoginActivity
                        else {
                            isDataInFirebase(fullPhone);
                            progressDialog.dismiss();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SmsVerificationActivity.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }


    //OnClickButton
    public void goToMenu(View view) {

        String code = pinFromUser_pinView.getText().toString();

        //show progress bar
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(!code.isEmpty()){
            verifyCode(code);
        }


    }

    //Firebase store data
    private void StoreUserData(String email,String name,String surname,
                               String fullPhone,String city,String street,String block,String flatLetter,String flat){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        UserHelperClass addNewUser =
                new UserHelperClass(email,name,surname,fullPhone,street,block,city,flatLetter,flat);


        reference.child(fullPhone).setValue(addNewUser);
    }

    public void goBack(View view) {
        // go back to previous activity and logout if he already login
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().signOut();
        }

        this.finish();

    }


    private void isDataInFirebase(String fullPhone){
        //check if User exists in Realtimedatabse
        Query checkUserTelephone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("telephone").equalTo(fullPhone);

        checkUserTelephone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // User exist
                if(snapshot.exists()){

                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

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



                    //Create a user Session with data from database
                    SessionManager sessionManager = new SessionManager(SmsVerificationActivity.this, SessionManager.SESSION_USERSESSION);
                    sessionManager.createLoginSession(name_user,surname_user,email_user,phone_user
                            ,city_user,street_user,block_user,flatLatter_user,flat_User);


                    startActivity(intent);

                }

                //User needs to SignUp
                else {

                    Toast.makeText(SmsVerificationActivity.this,"Nie ma takiego konta o podanym numerze telefonu.\n Zarejestruj się",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    intent.putExtra("fullPhone",fullPhone);
                    intent.putExtra("phone",phone);
                    intent.putExtra("isLogin",isLogin);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SmsVerificationActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}