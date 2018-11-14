package com.cosplay.kotlin.hw.ui.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.cosplay.kotlin.hw.APP;
import com.cosplay.kotlin.hw.ui.activity.FingerPrintActivity;
import com.cosplay.kotlin.hw.ui.activity.FingerPrintCompatActivity;
import com.cosplay.kotlin.hw.util.SPUtils;

import java.io.UnsupportedEncodingException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * Author:wangzhiwei on 2018/10/30.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class FingerAuthCallBack extends FingerprintManagerCompat.AuthenticationCallback {
    Handler handler;
    public static final int AuthError = 1;
    public static final int AuthSuccess = 2;
    public static final int AuthFailure = 3;
    public static final int AuthHelp = 4;
    public FingerAuthCallBack(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        Message message = handler.obtainMessage();
        message.what = AuthError;
        message.arg1 = errMsgId;
        handler.sendMessage(message);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
        Message message = handler.obtainMessage();
        message.what = AuthHelp;
        message.arg1 = helpMsgId;
        handler.sendMessage(message);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Message message = handler.obtainMessage();
        Cipher cipher = result.getCryptoObject().getCipher();
        if(FingerPrintCompatActivity.fingerPurpose == FingerPrintCompatActivity.getFingerPurpose){
            //取出secret key并返回
            String data = SPUtils.getString(APP.Companion.getContext(),FingerPrintActivity.token);
            if (TextUtils.isEmpty(data)) {
                onAuthenticationFailed();
                return;
            }
            try {
                byte[] original = cipher.doFinal(Base64.decode(data, Base64.URL_SAFE));
                try {
                    String originalString = new String(original,"utf-8");
                    message.obj = originalString;
                    Log.e("originalString","解密的："+originalString+"---"+data);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                onAuthenticationFailed();
            }

        }else{
               //将前面生成的data包装成secret key，存入沙盒
            try {
                byte[] encrypted = cipher.doFinal(FingerPrintActivity.token.getBytes());
                byte[] IV = cipher.getIV();
                String se = Base64.encodeToString(encrypted, Base64.URL_SAFE);
                String siv = Base64.encodeToString(IV, Base64.URL_SAFE);
                Log.e("originalString","加密的："+se+"----"+siv);
                SPUtils.setString(APP.Companion.getContext(),FingerPrintActivity.token,se);
                SPUtils.setString(APP.Companion.getContext(),"iv",siv);
                message.obj = FingerPrintActivity.token;
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                onAuthenticationFailed();
            }

        }
//        byte[] decrypted  = new byte[0];
//        try {
//            decrypted = cipher.doFinal(FingerPrintActivity.token.getBytes());
//
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        byte[] IV = cipher.getIV();
//        String se = Base64.encodeToString(decrypted , Base64.URL_SAFE);
//
//        String siv = Base64.encodeToString(IV, Base64.URL_SAFE);
//        Log.e("aass","---"+se+"----"+siv);
        message.what = AuthSuccess;

        handler.sendMessage(message);
       // handler.sendEmptyMessage(AuthSuccess);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        handler.sendEmptyMessage(AuthFailure);
    }
}
