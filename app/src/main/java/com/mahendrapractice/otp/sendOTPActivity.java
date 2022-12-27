package com.mahendrapractice.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class sendOTPActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        final EditText inputMobileNumber=findViewById(R.id.inputMobileNumber);
        final Button getOtpButton=findViewById(R.id.getOtpButton);
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        final CountryCodePicker contryCodePicker=findViewById(R.id.contryCodePicker);
              contryCodePicker.registerCarrierNumberEditText(inputMobileNumber);



        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (inputMobileNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(sendOTPActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                getOtpButton.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(contryCodePicker.getFullNumberWithPlus().replace("",""),
                        20, TimeUnit.SECONDS,sendOTPActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                getOtpButton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getOtpButton.setVisibility(View.VISIBLE);
                                Toast.makeText(sendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getOtpButton.setVisibility(View.VISIBLE);

                                Intent intent=new Intent(getApplicationContext(),verifyOTPActivity.class);
                                intent.putExtra("mobileNumber",contryCodePicker.getFullNumberWithPlus().replace("",""));
                                intent.putExtra("verificationCode",verificationCode);
                                startActivity(intent);
                                finish();
                            }
                        });




            }
        });
    }
}