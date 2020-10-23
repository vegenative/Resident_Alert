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
import com.hbb20.CountryCodePicker;


import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpBtn;
    public EditText name_et,surname_et,tel_et,street_et,block_et,city_et,postalCode_et,flat_et,flatLetter_et;

    private CountryCodePicker countryCodePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //button
        signUpBtn = (Button) findViewById(R.id.signup_button);
        //edit text
        //email_et = (EditText) findViewById(R.id.emailRegister_et);
        //password_et = (EditText) findViewById(R.id.passwordRegister_et);
        name_et = (EditText) findViewById(R.id.name_et);
        surname_et = (EditText) findViewById(R.id.surname_et);
        tel_et = (EditText) findViewById(R.id.phone_et);
        street_et = (EditText) findViewById(R.id.street_et);
        block_et = (EditText) findViewById(R.id.blockNumber_et);
        city_et = (EditText) findViewById(R.id.city_et);
        postalCode_et = (EditText) findViewById(R.id.postalCode_et);
        flat_et = (EditText) findViewById(R.id.blockOfFlatNumber_et);
        flatLetter_et = (EditText) findViewById(R.id.flatLetter_et);

        countryCodePicker = findViewById(R.id.countryCodePicker);









///////////////////////////////////////OnClick/////////////////////////////////////////////////////

        signUpBtn.setOnClickListener((v)->{
            //String email = email_et.getText().toString().trim();
            //String password = password_et.getText().toString().trim();
            //String cnfPassword = cnfPassword_et.getText().toString().trim();
            String name = name_et.getText().toString().trim();
            String surname = surname_et.getText().toString().trim();

            String stringTel = tel_et.getText().toString().trim();
            String phone = "+" + countryCodePicker.getFullNumber() + stringTel;

            String street = street_et.getText().toString();
            String blockNumber = block_et.getText().toString().trim();
            String flatLetter = flatLetter_et.getText().toString().trim();
            String blockOfFlatNumber = flat_et.getText().toString().trim();
            String city = city_et.getText().toString().trim();
            String postalCode = postalCode_et.getText().toString().trim();





            if (TextUtils.isEmpty(name)){
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
            else if (TextUtils.isEmpty(postalCode)){
                postalCode_et.setError("Pole nie może być puste");
                postalCode_et.requestFocus();
            }
            else if (TextUtils.isEmpty(city)){
                city_et.setError("Pole nie może być puste");
                city_et.requestFocus();
            }
            else if (TextUtils.isEmpty(blockNumber)){
                block_et.setError("Pole nie może być puste");
                block_et.requestFocus();
            }
            else if (TextUtils.isEmpty(flatLetter)){
                city_et.setError("Pole nie może być puste");
                city_et.requestFocus();
            }
            else if (TextUtils.isEmpty(blockOfFlatNumber)){
                city_et.setError("Pole nie może być puste");
                city_et.requestFocus();
            }

            else {
                passValuesToActivity(name,surname,phone,street,city,blockNumber,flatLetter,blockOfFlatNumber,stringTel);

            }
        });
    }

    ///////////////////////////////////////Methods//////////////////////////////////////////////////


    //verification email
    /*private void sendVerificationEmail(){
        FirebaseUser user = fAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(SignUpActivity.this, "Kod weryfikacyjny został wysłany na podany E-mail", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(SignUpActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    */

    private void passValuesToActivity(String name,String surname,String phone,String street,String city, String block,String flatLetter,String flat,String stringTel){
        //pass all fields to the next activity
        Intent goNext = new Intent(this,SmsVerification.class);

        goNext.putExtra("name",name);
        goNext.putExtra("surname",surname);
        goNext.putExtra("phone",phone);
        goNext.putExtra("street",street);
        goNext.putExtra("city",city);
        goNext.putExtra("block",block);
        goNext.putExtra("flatLetter",flatLetter);
        goNext.putExtra("flat",flat);
        goNext.putExtra("stringTel", stringTel);

        Toast.makeText(SignUpActivity.this, "Witaj " + name ,Toast.LENGTH_SHORT).show();

        startActivity(goNext);


    }

}