package com.example.resident_alert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.resident_alert.R;
import com.example.resident_alert.UserHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Button signUpBtn;
    public EditText name_et,surname_et,street_et,block_et,postalCode_et,flat_et,flatLetter_et,email_et;
    private Spinner city_spinner;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private boolean isFlat = true;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //button
        signUpBtn = (Button) findViewById(R.id.signup_button);
        //edit text
        email_et = (EditText) findViewById(R.id.email_et);
        name_et = (EditText) findViewById(R.id.name_et);
        surname_et = (EditText) findViewById(R.id.surname_et);
        street_et = (EditText) findViewById(R.id.street_et);
        block_et = (EditText) findViewById(R.id.blockNumber_et);
        postalCode_et = (EditText) findViewById(R.id.postalCode_et);
        flat_et = (EditText) findViewById(R.id.blockOfFlatNumber_et);
        flatLetter_et = (EditText) findViewById(R.id.flatLetter_et);
        //radioButton
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //spinner
        city_spinner = (Spinner) findViewById(R.id.city_et);
        setSpinner();



///////////////////////////////////////OnClick/////////////////////////////////////////////////////

        signUpBtn.setOnClickListener((v)->{

            String email = email_et.getText().toString().trim();
            String name = name_et.getText().toString().trim();
            String surname = surname_et.getText().toString().trim();
            String street = street_et.getText().toString();
            String blockNumber = block_et.getText().toString().trim();
            String flatLetter = flatLetter_et.getText().toString().trim();
            String blockOfFlatNumber = flat_et.getText().toString().trim();
            String city = city_spinner.getSelectedItem().toString();
            String postalCode = postalCode_et.getText().toString().trim();





            if (TextUtils.isEmpty(name)){
                name_et.setError("Pole nie może być puste");
                name_et.requestFocus();
            }
            else if (TextUtils.isEmpty(surname)){
                surname_et.setError("Pole nie może być puste");
                surname_et.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)){
                email_et.setError("Pole musi być adresem email");
                email_et.requestFocus();
            }
            else if (TextUtils.isEmpty(street)){
                street_et.setError("Pole nie może być puste");
                street_et.requestFocus();
            }
            else if (TextUtils.isEmpty(postalCode)){
                postalCode_et.setError("Pole nie może być puste");
                postalCode_et.requestFocus();
            }
            else if (TextUtils.isEmpty(blockNumber)){
                block_et.setError("Pole nie może być puste");
                block_et.requestFocus();
            }

            else if (TextUtils.isEmpty(blockOfFlatNumber) && isFlat==true){
                flat_et.setError("Pole nie może być puste");
                flat_et.requestFocus();
            }

            else {
                //get values from previous activity
                String phone = getIntent().getStringExtra("phone");
                String fullPhone = getIntent().getStringExtra("fullPhone");

                // if is verified by SmsCode (from LoginActivity) store Data in firebase and go to activity
                if(fAuth.getCurrentUser() != null){

                    StoreUserData(email,name,surname,fullPhone,city,street,blockNumber,flatLetter,blockOfFlatNumber);

                    Toast.makeText(SignUpActivity.this, "Witaj " + name ,Toast.LENGTH_LONG).show();
                    Intent goNext = new Intent(this, MenuActivity.class);
                    startActivity(goNext);
                    finish();
                }
                //else go to SmsVerification
                else {
                    passValuesToActivity(name,surname,fullPhone,street,city,blockNumber,flatLetter,blockOfFlatNumber,phone,email);

                }


            }
        });
    }

    ///////////////////////////////////////Methods//////////////////////////////////////////////////



    private void passValuesToActivity(String name,String surname,String fullPhone,String street,String city, String block,
                                      String flatLetter,String flat,String phone,String email){
        //pass all fields to the next activity
        Intent goNext = new Intent(this, SmsVerificationActivity.class);

        goNext.putExtra("name",name);
        goNext.putExtra("surname",surname);
        goNext.putExtra("fullPhone",fullPhone);
        goNext.putExtra("street",street);
        goNext.putExtra("city",city);
        goNext.putExtra("block",block);
        goNext.putExtra("flatLetter",flatLetter);
        goNext.putExtra("flat",flat);
        goNext.putExtra("phone", phone);
        goNext.putExtra("email",email);


        Toast.makeText(SignUpActivity.this, "Witaj " + name ,Toast.LENGTH_SHORT).show();

        startActivity(goNext);


    }

//RadioButton
    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        switch(radioId){
            case R.id.flat_Rbtn:
                block_et.setHint("Nr bloku");
                flatLetter_et.setVisibility(View.VISIBLE);
                flat_et.setVisibility(View.VISIBLE);
                break;
            case R.id.house_Rbtn:
                isFlat = false;
                block_et.setHint("Nr domu");
                flatLetter_et.setVisibility(View.GONE);
                flat_et.setVisibility(View.GONE);
                break;
        }
    }
//Adapter
    private void setSpinner(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.cities));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(myAdapter);
    }

    //Firebase store data
    private void StoreUserData(String email,String name,String surname,
                               String fullPhone,String city,String street,String block,String flatLetter,String flat){
        FirebaseDatabase rootNode;
        DatabaseReference reference;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        UserHelperClass addNewUser =
                new UserHelperClass(email,name,surname,fullPhone,street,block,city,flatLetter,flat);

        reference.child(fullPhone).setValue(addNewUser);
    }
}