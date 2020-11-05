package com.example.resident_alert.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resident_alert.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ResetPasswordActivity extends AppCompatActivity {

    Button next_btn,acceptChanges_btn;
    EditText resetNumber_et,changePassword_et,cnfChangePassword_et;
    TextView resetPasswordInfo;
    CountryCodePicker countryCodePicker;
    LinearLayout phoneVerification_ll,changePassword_ll;
    ProgressBar progressBar;
    Boolean isVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        next_btn = (Button) findViewById(R.id.next_btn);
        acceptChanges_btn = (Button) findViewById(R.id.acceptChanges_btn);

        resetNumber_et = (EditText) findViewById(R.id.resetPassword_et);
        changePassword_et = (EditText) findViewById(R.id.changePassword_et);
        cnfChangePassword_et = (EditText) findViewById(R.id.cnfChangePassword_et);

        resetPasswordInfo = (TextView) findViewById(R.id.resetPasswordInfo_tv);

        countryCodePicker = (CountryCodePicker)findViewById(R.id.countryCodePickerResetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        phoneVerification_ll = (LinearLayout) findViewById(R.id.phoneVerification_ll);
        changePassword_ll = (LinearLayout) findViewById(R.id.changePassword_ll);


        //check if is from LoginActivity or smsVerificationActivity
        isVerified = getIntent().getBooleanExtra("isVerified", isVerified);

        if(isVerified==true){

            //change layout if phone number was verified
            phoneVerification_ll.setVisibility(View.GONE);
            changePassword_ll.setVisibility(View.VISIBLE);
            resetPasswordInfo.setText("Wpisz nowe hasło i je potwierdź aby dokonać zmiany");

        }
        else{
            phoneVerification_ll.setVisibility(View.VISIBLE);
            changePassword_ll.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void goNext(View view) {
        String phone = resetNumber_et.getText().toString().trim();
        String fullPhone = "+" + countryCodePicker.getFullNumber() + phone;

        if(TextUtils.isEmpty(phone)){
            resetNumber_et.setError("Pole nie może być puste ");
            resetNumber_et.requestFocus();
        }
        else if(phone.charAt(0)=='0'){
            phone = phone.substring(1); //if user adds 0 on start, it will be removed
        }
        else if(phone.length()!= 9){
            resetNumber_et.setError("Numer telefonu musi posiadać 9 cyfr");
            resetNumber_et.requestFocus();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);

            //check if user phone exists in the firebase
            Query checkUserTelephone = FirebaseDatabase.getInstance().getReference("Users").orderByChild("telephone").equalTo(fullPhone);
            checkUserTelephone.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        resetNumber_et.setError(null);
                        progressBar.setVisibility(View.GONE);


                        //push values and go to next activity to VerifyNumber
                        Intent goToActivity = new Intent(ResetPasswordActivity.this, SmsVerificationActivity.class);
                        goToActivity.putExtra("fullPhone",fullPhone);
                        goToActivity.putExtra("stringTel",fullPhone); // nie da się przekazać phone
                        goToActivity.putExtra("resetPassword",true);
                        startActivity(goToActivity);

                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, "Nie ma takiego konta o podanym numerze telefonu", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ResetPasswordActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void saveNewData(View view) {

        //take phone Number that was verified
        String fullPhone = getIntent().getStringExtra("fullPhone");

        // take new password from the fields
        String newPassword = changePassword_et.getText().toString().trim();
        String cnfNewPassword = cnfChangePassword_et.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)){
            changePassword_et.setError("Pole nie może być puste");
            changePassword_et.requestFocus();
        }
        else if(newPassword.length() < 6){
            changePassword_et.setError("Hasło musi posiadać co najmniej 6 znaków");
            changePassword_et.requestFocus();
        }
        else if(!cnfNewPassword.equals(newPassword))
        {
            cnfChangePassword_et.setError("Hasła muszą być takie same");
            cnfChangePassword_et.requestFocus();
        }
        else{

            //UpdateData
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(fullPhone).child("password").setValue(newPassword);

            //make toast and go to Login
            Toast.makeText(ResetPasswordActivity.this, "Hasło zostało zmienione", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}