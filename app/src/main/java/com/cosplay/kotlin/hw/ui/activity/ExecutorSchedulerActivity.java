package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.executor.ExecRunable;
import com.cosplay.kotlin.hw.executor.OScheduler;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.cosplay.kotlin.hw.executor.OScheduler.getOScheduler;

public class ExecutorSchedulerActivity extends AppCompatActivity {
    TextView tvChange;
    private final static  String TAG = ExecutorSchedulerActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_scheduler);
        initView();
    }
    private void initView(){

        tvChange = findViewById(R.id.tv_change);
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OScheduler.getOScheduler().createWorker().schedule(new ExecRunable(){
                    @Override
                    public void start() {
                        super.start();
                        Log.e(TAG,"start");
                    }

                    @Override
                    public void doTimetTak() {
                        super.doTimetTak();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            error();
                            e.printStackTrace();
                        }
                        Log.e(TAG,"doTimetTak");

                    }

                    @Override
                    public void complete() {
                        super.complete();
                        Log.e(TAG,"complete");
                    }
                });
            }
        });
    }
    private  void initRx(){
        Observable novel= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });
        //观察者
        Observer<String> reader=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.e(TAG,"onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)){

                    return;
                }
                Log.e(TAG,"onNext:"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError="+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete()");
            }
        };
        novel.subscribe(reader);//一行代码搞定
    }
}
