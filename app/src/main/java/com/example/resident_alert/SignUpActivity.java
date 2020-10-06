package com.example.resident_alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpBtn;
    private EditText email_et, password_et,name_et,surname_et,cnfPassword_et,tel_et,street_et,blockNumber_et,city_et,postalCode_et;
    private String userID,verificationCodeBySystem;
    private boolean isSmsVerified = false;
    private FirebaseAuth fAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //button
        signUpBtn = (Button) findViewById(R.id.signup_button);
        //edit text
        email_et = (EditText) findViewById(R.id.emailRegister_et);
        password_et = (EditText) findViewById(R.id.passwordRegister_et);
        name_et = (EditText) findViewById(R.id.name_et);
        surname_et = (EditText) findViewById(R.id.surname_et);
        cnfPassword_et = (EditText) findViewById(R.id.cnfPassword_et);
        tel_et = (EditText) findViewById(R.id.phone_et);
        street_et = (EditText) findViewById(R.id.street_et);
        blockNumber_et = (EditText) findViewById(R.id.blockNumber_et);
        city_et = (EditText) findViewById(R.id.city_et);
        postalCode_et = (EditText) findViewById(R.id.postalCode_et);


        fAuth = FirebaseAuth.getInstance();






///////////////////////////////////////OnClick/////////////////////////////////////////////////////

        signUpBtn.setOnClickListener((v)->{
            String email = email_et.getText().toString().trim();
            String password = password_et.getText().toString().trim();
            String cnfPassword = cnfPassword_et.getText().toString().trim();
            String name = name_et.getText().toString().trim();
            String surname = surname_et.getText().toString().trim();
            String stringTel = tel_et.getText().toString().trim();
            String street = street_et.getText().toString();
            String blockNumber = blockNumber_et.getText().toString().trim();
            String city = city_et.getText().toString().trim();
            String postalCode = postalCode_et.getText().toString().trim();




            if (TextUtils.isEmpty(email)){
                email_et.setError("Pole nie może być puste");
                email_et.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email_et.setError("Pole musi być adresem E-mail");
                email_et.requestFocus();
            }
            else if (TextUtils.isEmpty(name)){
                name_et.setError("Pole nie może być puste");
                name_et.requestFocus();
            }
            else if (TextUtils.isEmpty(surname)){
                surname_et.setError("Pole nie może być puste");
                surname_et.requestFocus();
            }
            else if (TextUtils.isEmpty(stringTel)){
                tel_et.setError("Pole nie może być puste");
                tel_et.requestFocus();
            }
            else if (stringTel.length() != 9){
                tel_et.setError("Numer telefonu musi zawierać 9 znaków");
                tel_et.requestFocus();
            }
            else if (TextUtils.isEmpty(street)){
                street_et.setError("Pole nie może być puste");
                street_et.requestFocus();
            }
            else if (TextUtils.isEmpty(blockNumber)){
                blockNumber_et.setError("Pole nie może być puste");
                blockNumber_et.requestFocus();
            }
            else if (TextUtils.isEmpty(postalCode)){
                postalCode_et.setError("Pole nie może być puste");
                postalCode_et.requestFocus();
            }
            else if (TextUtils.isEmpty(city)){
                city_et.setError("Pole nie może być puste");
                city_et.requestFocus();
            }
            else if(cnfPassword.equals(password)){
                signUp(email,password,name,surname,stringTel,street,blockNumber,postalCode,city);
            }
            else{
                cnfPassword_et.setError("Hasła muszą być takie same");
            }
        });
    }

    ///////////////////////////////////////Methods//////////////////////////////////////////////////

    //signUp
    private void signUp(String email, String password,String name,String surname, String stringTel,
                        String street, String blockNumber, String postalCode,String city) {
        fAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                StoreUserData(email,password,name,surname,stringTel,street,blockNumber,postalCode,city);
                SignUpActivity.this.sendVerificationEmail();
                sendPhoneVerification(stringTel);

                Toast.makeText(SignUpActivity.this, "Witaj " + name ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();

            }else {
                Toast.makeText(SignUpActivity.this,"Error ! "
                        + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    //verification email
    private void sendVerificationEmail(){
        FirebaseUser user = fAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(SignUpActivity.this, "Kod weryfikacyjny został wysłany na podany E-mail", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(SignUpActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    //veryfication phone
    private void sendPhoneVerification(String stringTel){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+48" + stringTel,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                fCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks fCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String smsCode = phoneAuthCredential.getSmsCode();
            if(smsCode != null){
                verifyCode(smsCode);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignUpActivity.this, task -> {

                    if(task.isSuccessful()){

                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Firebase store data
    private void StoreUserData(String email,String password,String name,String surname,
                      String stringTel,String city,String postalCode,String street,String blockNumber){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        UserHelperClass helperClass =
                new UserHelperClass(email,password,name,surname,stringTel,city,postalCode,street,blockNumber);


        reference.child(stringTel).setValue(helperClass);
    }
}