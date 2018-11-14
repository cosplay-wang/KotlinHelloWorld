package com.cosplay.kotlin.hw.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;

public class BiometricPromptActivity extends AppCompatActivity {
    BiometricPrompt biometricPrompt;
    CancellationSignal cancellationSignal;
    BiometricPrompt.AuthenticationCallback authenticationCallback;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_prompt);
        imageView = findViewById(R.id.iv_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();

            }
        });
    }
    @TargetApi(28)
    void init(){
        biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("指纹识别")
                .setDescription("我需要指纹识别")
                .setNegativeButton("取消", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"取消识别的弹窗",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"取消识别",Toast.LENGTH_SHORT).show();
            }
        });
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),"onAuthenticationError",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                Toast.makeText(getApplicationContext(),"onAuthenticationHelp",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),"onAuthenticationSucceeded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),"onAuthenticationFailed",Toast.LENGTH_SHORT).show();
            }
        };
        biometricPrompt.authenticate(cancellationSignal,getMainExecutor(),authenticationCallback);
    }
}
