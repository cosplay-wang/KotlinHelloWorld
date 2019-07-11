package com.cosplay.kotlin.hw.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.fingerprint.FingerAuthCallBack;
import com.cosplay.kotlin.hw.ui.fingerprint.FingerAuthCallBack2;
import com.cosplay.kotlin.hw.util.SPUtils;
import com.cosplay.kotlin.hw.util.permisstion.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class FingerPrintCompatActivity extends AppCompatActivity {
    FingerprintManagerCompat fingerprintManager;
    KeyguardManager keyguardManager;
    Handler fingerHandler;

    // This can be key name you want. Should be unique for the app.
    static final String KEY_NAME = "com.createchance.android.sample.fingerprint_authentication_key";

    // We always use this keystore on Android.
    static final String KEYSTORE_NAME = "AndroidKeyStore";

    // Should be no need to change these values.
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" +
            BLOCK_MODE + "/" +
            ENCRYPTION_PADDING;
     KeyStore _keystore;
    CancellationSignal cancellationSignal;
    TextView imageView,textView;
    EditText ed_uid;
    public static  String fingerPurpose ;
    public static  final String saveFingerPurpose = "saveFinger";
    public static  final String getFingerPurpose = "getFinger";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finger_print);
        imageView = findViewById(R.id.iv_image);
        textView = findViewById(R.id.iv_image_de);
        ed_uid = findViewById(R.id.ed_uid);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerPurpose = saveFingerPurpose;
                FingerPrintActivity.token = ed_uid.getText().toString();
                requestPermission(new String[]{Manifest.permission.USE_FINGERPRINT});

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerPurpose = getFingerPurpose;
                requestPermission(new String[]{Manifest.permission.USE_FINGERPRINT});

            }
        });



    }
    void init() {
        fingerHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case FingerAuthCallBack.AuthError:{
                        handleErrorCode(msg.arg1);
                       // imageView.setImageResource(R.drawable.opponents_circle);
                        Toast.makeText(getApplicationContext(),"AuthError",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case FingerAuthCallBack.AuthSuccess:{
                       // imageView.setImageResource(R.drawable.f4);
                        Toast.makeText(getApplicationContext(),"AuthSuccess",Toast.LENGTH_SHORT).show();
                        if(fingerPurpose == saveFingerPurpose){
                            imageView.setText(msg.obj.toString());
                        }else{
                            textView.setText(msg.obj.toString());
                        }
                        break;
                    }
                    case FingerAuthCallBack.AuthFailure:{
                      //  imageView.setImageResource(R.drawable.opponents_circle);
                        Toast.makeText(getApplicationContext(),"AuthFailure",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case FingerAuthCallBack.AuthHelp:{
                        handleHelpCode(msg.arg1);
                        Toast.makeText(getApplicationContext(),"AuthHelp",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        };
        fingerprintManager = FingerprintManagerCompat.from(this);
        keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if(fingerprintManager.isHardwareDetected()){
             if(keyguardManager.isKeyguardSecure()){
                 Log.e("mmm","锁屏已开启，指纹识别可以使用");
                 if(fingerprintManager.hasEnrolledFingerprints()){
                     imageView.setVisibility(View.VISIBLE);
                     CryptoObjectHelper();
                     cancellationSignal  = new CancellationSignal(); //必须重新实例化，否则cancel 过一次就不能再使用了
                     fingerprintManager.authenticate(getFingerprintManagerCryptoObject(),0,cancellationSignal,new FingerAuthCallBack(fingerHandler),fingerHandler);


                 }else{
                     Toast.makeText(getApplicationContext(),"您的设备没有录入指纹",Toast.LENGTH_SHORT).show();
                 }
             }else{
                 Toast.makeText(getApplicationContext(),"您的设备没有开启锁屏",Toast.LENGTH_SHORT).show();
             }
        }else{
            Toast.makeText(getApplicationContext(),"您的设备不支持指纹识别",Toast.LENGTH_SHORT).show();
        }
    }
    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:

                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:

                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:

                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:

                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:

                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:

                break;
        }
    }
    private void handleHelpCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:

                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:

                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:

                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:

                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:

                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:

                break;
        }
    }

    public FingerprintManagerCompat.CryptoObject getFingerprintManagerCryptoObject() {
        Cipher cipher;
        try {
             cipher =createCipher(true);
            return  new FingerprintManagerCompat.CryptoObject(cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @TargetApi(23)
    public FingerprintManager.CryptoObject getFingerprintManagerCryptoObject1() {
        Cipher cipher;
        try {
            cipher =createCipher(true);
            return  new FingerprintManager.CryptoObject(cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @TargetApi(23)
    Cipher createCipher(boolean retry) throws Exception
    {
            //加密
            Key key = GetKey();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            try {
                if(FingerPrintCompatActivity.fingerPurpose != FingerPrintCompatActivity.getFingerPurpose) {
                    cipher.init(Cipher.ENCRYPT_MODE, key);// | Cipher.DECRYPT_MODE
                }else{
                    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(Base64.decode(SPUtils.getString(FingerPrintCompatActivity.this,"iv"),Base64.URL_SAFE)));// | Cipher.DECRYPT_MODE
                }
            } catch (KeyPermanentlyInvalidatedException e) {
                _keystore.deleteEntry(KEY_NAME);
                if (retry) {
                    createCipher(false);
                } else {
                    throw new Exception("Could not create the cipher for fingerprint authentication.", e);
                }
            }
            return cipher;

    }
    String token = "123456789";
    Key GetKey() throws Exception
    {
        Key secretKey;
        if(!_keystore.isKeyEntry(KEY_NAME))
        {
            CreateKey();
        }

        secretKey = _keystore.getKey(KEY_NAME, null);
        return secretKey;
    }
    @TargetApi(23)
    void CreateKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        keyGen.generateKey();
    }

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        init();
                        Toast.makeText(FingerPrintCompatActivity.this,"获取权限成功"+permissions,Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toast.makeText(FingerPrintCompatActivity.this,R.string.failure,Toast.LENGTH_SHORT).show();
                        if (AndPermission.hasAlwaysDeniedPermission(FingerPrintCompatActivity.this, permissions)) {
                          //  showSettingDialog(FingerPrintActivity.this, permissions);
                        }
                    }
                })
                .start();
    }@TargetApi(23)
    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        try {
            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        } catch (Throwable e) {
          //  FPLog.log("have not class FingerprintManager");
        }
        return fingerprintManager;
    }
    public void CryptoObjectHelper() {
        try {
            _keystore = KeyStore.getInstance(KEYSTORE_NAME);
            _keystore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        for (int count = 0; count < 10 ; count++) {
            System.out.println(count);

        }
    }
}



