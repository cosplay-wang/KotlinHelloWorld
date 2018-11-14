package com.cosplay.kotlin.hw.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cosplay.kotlin.hw.util.permisstion.InstallRationale;
import com.cosplay.kotlin.hw.util.permisstion.PermissionS;
import com.yanzhenjie.permission.Permission;
import com.cosplay.kotlin.hw.MainActivity;
import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.util.permisstion.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;


import java.io.File;
import java.util.List;

public class AndpermisstionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andpermisstion);
        findViewById(R.id.tv_sd).setOnClickListener(this);
        findViewById(R.id.tv_record).setOnClickListener(this);
        findViewById(R.id.tv_play).setOnClickListener(this);
        findViewById(R.id.tv_camera).setOnClickListener(this);
        findViewById(R.id.tv_muti).setOnClickListener(this);
        findViewById(R.id.tv_zu).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sd:{
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);//Manifest.permission.WRITE_EXTERNAL_STORAGE,
                break;
            }
            case R.id.tv_record:{
                requestPermission(Manifest.permission.RECORD_AUDIO);
                break;
            }
            case R.id.tv_play:{
               // requestPermission(Manifest.permission.);
                break;
            }
            case R.id.tv_camera:{
                 requestPermission(Manifest.permission.CAMERA);
                break;
            }
            case R.id.tv_muti:{
                requestPermission(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            }
            case R.id.tv_zu:{
                requestPermission(Permission.Group.LOCATION);
                break;
            }
        }
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
                        Toast.makeText(AndpermisstionActivity.this,"获取权限成功",Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toast.makeText(AndpermisstionActivity.this,R.string.failure,Toast.LENGTH_SHORT).show();
                        if (AndPermission.hasAlwaysDeniedPermission(AndpermisstionActivity.this, permissions)) {
                            showSettingDialog(AndpermisstionActivity.this, permissions);
                        }
                    }
                })
                .start();
    }
    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    /**
     * Install package.
     */
    private void installPackage() {
        AndPermission.with(this)
                .install()
                .file(new File(Environment.getExternalStorageDirectory(), "android.apk"))
                .rationale(new InstallRationale())
                .onGranted(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        // Installing.
                    }
                })
                .onDenied(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        // The user refused to install.
                    }
                })
                .start();
    }

}
