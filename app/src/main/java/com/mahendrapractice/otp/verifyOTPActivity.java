package com.mahendrapractice.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOTPActivity extends AppCompatActivity {

    private EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    private TextView mobileNumber,resendOTP;
    private ProgressBar progressBar;
    private Button verifyButton;


    String verificationCode,mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        inputCode1=findViewById(R.id.inputCode1);
        inputCode2=findViewById(R.id.inputCode2);
        inputCode3=findViewById(R.id.inputCode3);
        inputCode4=findViewById(R.id.inputCode4);
        inputCode5=findViewById(R.id.inputCode5);
        inputCode6=findViewById(R.id.inputCode6);
        mobileNumber=findViewById(R.id.mobileNumber);
        progressBar=findViewById(R.id.progressBar);
        resendOTP=findViewById(R.id.resendOTP);
        verifyButton=findViewById(R.id.verifyButton);

        setUpOTPInputs();
        mNumber=getIntent().getStringExtra("mobileNumber");
        mobileNumber.setText(mNumber);

        verificationCode=getIntent().getStringExtra("verificationCode");


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputCode1.getText().toString().trim().isEmpty()
                    ||inputCode2.getText().toString().trim().isEmpty()
                    ||inputCode3.getText().toString().trim().isEmpty()
                    ||inputCode4.getText().toString().trim().isEmpty()
                    ||inputCode5.getText().toString().trim().isEmpty()
                    ||inputCode6.getText().toString().trim().isEmpty()){

                    Toast.makeText(verifyOTPActivity.this, "Please Enter 6 Digit OTP Code...", Toast.LENGTH_SHORT).show();
                    return;
                }

                String icode=inputCode1.getText().toString()+inputCode2.getText().toString()+
                        inputCode3.getText().toString()+inputCode4.getText().toString()+
                        inputCode5.getText().toString()+inputCode6.getText().toString();


                if (verificationCode!=null){

                    progressBar.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(verificationCode,icode);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressBar.setVisibility(View.INVISIBLE);
                            verifyButton.setVisibility(View.VISIBLE);

                            if (task.isSuccessful()){


                                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isUserLogin", true);
                                editor.commit();

                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(verifyOTPActivity.this, "Invalid OTP Code....", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PhoneAuthProvider.getInstance().verifyPhoneNumber(mNumber ,//to resend mobile Number
                        20, TimeUnit.SECONDS,verifyOTPActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(verifyOTPActivity.this, "Please Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationCode=newverificationCode;
                                Toast.makeText(verifyOTPActivity.this, "OTP Send....", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

    public  void setUpOTPInputs(){

        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}