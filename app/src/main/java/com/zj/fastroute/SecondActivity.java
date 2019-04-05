package com.zj.fastroute;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zj.router.Router;
import com.zj.router.annotation.RouterActivity;
import com.zj.router.annotation.RouterField;

/**
 * Created by zhangjun on 2018/1/24.
 */
@RouterActivity({"second"})
public class SecondActivity extends AppCompatActivity {
    @RouterField(value = "key_int")
    private int mValueFromIntent = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Router.inject(this);
    }
}
