package com.cosplay.kotlin.hw.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;
import com.tencent.soter.wrapper.SoterWrapperApi;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessAuthenticationResult;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessCallback;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessKeyPreparationResult;
import com.tencent.soter.wrapper.wrap_fingerprint.SoterFingerprintCanceller;
import com.tencent.soter.wrapper.wrap_fingerprint.SoterFingerprintStateCallback;
import com.tencent.soter.wrapper.wrap_net.ISoterNetCallback;
import com.tencent.soter.wrapper.wrap_net.IWrapGetChallengeStr;
import com.tencent.soter.wrapper.wrap_net.IWrapUploadSignature;
import com.tencent.soter.wrapper.wrap_task.AuthenticationParam;

public class WechatFingerPrintActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_prompt);
        imageView = findViewById(R.id.iv_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoterWrapperApi.prepareAuthKey(new SoterProcessCallback<SoterProcessKeyPreparationResult>() {
                    @Override
                    public void onResult(@NonNull SoterProcessKeyPreparationResult result) {
                        Log.e("aaaaa",result.getErrMsg());
                        Toast.makeText(getApplicationContext(),result.getErrMsg(),Toast.LENGTH_SHORT).show();
                    }
                },false, true, 0, null, null);
                AuthenticationParam param = new AuthenticationParam.AuthenticationParamBuilder()
                        .setScene(0)
                        .setContext(WechatFingerPrintActivity.this)
                        .setFingerprintCanceller(new SoterFingerprintCanceller())
                        .setPrefilledChallenge("test challenge")
                        .setSoterFingerprintStateCallback(new SoterFingerprintStateCallback() {
                            @Override
                            public void onStartAuthentication() {
                                Toast.makeText(getApplicationContext(),"onStartAuthentication",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationSucceed() {
                                Toast.makeText(getApplicationContext(),"onAuthenticationSucceed",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationCancelled() {
                                Toast.makeText(getApplicationContext(),"onAuthenticationCancelled",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errorString) {
                                Toast.makeText(getApplicationContext(),"onAuthenticationError",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                Toast.makeText(getApplicationContext(),"onAuthenticationHelp",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                Toast.makeText(getApplicationContext(),"onAuthenticationFailed",Toast.LENGTH_SHORT).show();
                            }
                        }).build();
                SoterWrapperApi.requestAuthorizeAndSign(new SoterProcessCallback<SoterProcessAuthenticationResult>() {
                    @Override
                    public void onResult(@NonNull SoterProcessAuthenticationResult result) {
                        Log.e("aaaaa",result.getErrMsg());
                        Toast.makeText(getApplicationContext(),result.getErrMsg(),Toast.LENGTH_SHORT).show();
                    }
                }, param);
            }
        });
    }
    SoterFingerprintCanceller mCanceller;
    private void startFingerprintAuthentication(SoterProcessCallback<SoterProcessAuthenticationResult> processCallback,
                                                final String title, IWrapUploadSignature uploadSignatureWrapper) {

        if (mCanceller != null) {
            mCanceller = null;
        }
        mCanceller = new SoterFingerprintCanceller();

        AuthenticationParam param = new AuthenticationParam.AuthenticationParamBuilder() // 通过Builder来构建认证请求
                .setScene(1) // 指定需要认证的场景。必须在init中初始化。必填
                .setContext(this) // 指定当前上下文。必填。
                .setFingerprintCanceller(mCanceller) // 指定当前用于控制指纹取消的控制器。当因为用户退出界面或者进行其他可能引起取消的操作时，需要开发者通过该控制器取消指纹授权。建议必填。
                .setIWrapGetChallengeStr(new IWrapGetChallengeStr() {
                    @Override
                    public void setRequest(@NonNull GetChallengeRequest requestDataModel) {

                    }

                    @Override
                    public void execute() {

                    }

                    @Override
                    public void setCallback(ISoterNetCallback<GetChallengeResult> callback) {

                    }
                }) // 用于获取挑战因子的网络封装结构体。如果在授权之前已经通过其他模块拿到后台挑战因子，则可以改为调用setPrefilledChallenge。如果两个方法都没有调用，则会引起错误。
//                .setPrefilledChallenge("prefilled challenge") // 如果之前已经通过其他方式获取了挑战因子，则设置此字段。如果设置了该字段，则忽略获取挑战因子网络封装结构体的设置。如果两个方法都没有调用，则会引起错误。
                .setIWrapUploadSignature(uploadSignatureWrapper) // 用于上传最终结果的网络封装结构体。该结构体一般来说不独立存在，而是集成在最终授权网络请求中，该请求实现相关接口即可。选填，如果没有填写该字段，则要求应用方自行上传该请求返回字段。
                .setSoterFingerprintStateCallback(new SoterFingerprintStateCallback() { // 指纹回调仅仅用来更新UI相关，不建议在指纹回调中进行任何业务操作。选填。

                    // 指纹回调仅仅用来更新UI相关，不建议在指纹回调中进行任何业务操作
                    // Fingerprint state callbacks are only used for updating UI. Any logic operation is not welcomed.
                    @Override
                    public void onStartAuthentication() {

                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        // 由于厂商实现不同，不建议在onAuthenticationHelp中做任何操作。
                    }

                    @Override
                    public void onAuthenticationSucceed() {

                        mCanceller = null;
                        // 可以在这里做相应的UI操作
                    }

                    @Override
                    public void onAuthenticationFailed() {


                    }

                    @Override
                    public void onAuthenticationCancelled() {

                        mCanceller = null;

                    }

                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errorString) {

                        mCanceller = null;
                    }
                }).build();
        SoterWrapperApi.requestAuthorizeAndSign(processCallback, param);
    }


}
