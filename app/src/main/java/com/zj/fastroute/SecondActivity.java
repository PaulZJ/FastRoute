package com.zj.fastroute;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.zj.router.annotation.RouterActivity;

/**
 * Created by zhangjun on 2018/1/24.
 */
@RouterActivity({"second"})
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
