package com.cosplay.kotlin.hw.ui.fingerprint;

import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Author:wangzhiwei on 2018/10/30.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
@TargetApi(23)
public class FingerAuthCallBack2 extends FingerprintManager.AuthenticationCallback {
    Handler handler;
    public static final int AuthError = 1;
    public static final int AuthSuccess = 2;
    public static final int AuthFailure = 3;
    public static final int AuthHelp = 4;
    public FingerAuthCallBack2(Handler handler) {
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
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        handler.sendEmptyMessage(AuthSuccess);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        handler.sendEmptyMessage(AuthFailure);
    }
}
