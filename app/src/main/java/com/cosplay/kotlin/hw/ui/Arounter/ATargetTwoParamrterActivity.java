package com.cosplay.kotlin.hw.ui.Arounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cosplay.kotlin.hw.R;

@Route(path = "/test/ATargetTwoParamrterActivity")
public class ATargetTwoParamrterActivity extends AppCompatActivity {
    //获取数据三种方式
    //1.通过Autowired注解表明key   &  需要在onCreate中调用ARouter.getInstance().inject(this);配合使用
    @Autowired(name = "key1")
    public long data;
    //2.通过Autowired注解 & 将key1作为属性的名称   &  需要在onCreate中调用ARouter.getInstance().inject(this);配合使用
    @Autowired()
    public String key3;

    @Autowired()
    public ARouteEntity key4;
    @Autowired()
    public ARouteEntitySer key5;
    //3.通过Bundle获取
    // getIntent().getExtras().getLong("key1")
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atarget_two_paramrter);
        ARouter.getInstance().inject(this);
        Toast.makeText(this, data + "----" + key3 + "---", Toast.LENGTH_SHORT).show();
        // TODO: 2019/8/1 可能是需要在别的组件里 才能正常初始化
       // JsonParserService jsonParserService = ARouter.getInstance().navigation(JsonParserService.class);
       // jsonParserService.init(this);
      //  ARouteEntity aRouteEntity = jsonParserService.parseObject(getIntent().getStringExtra("key4"),ARouteEntity.class);
        textView3 = findViewById(R.id.textView3);
        textView3.setText("带参数的activity" + data + "----" + key3 + "---"+key4.getName()+"------"+key5.getName());
    }
}
