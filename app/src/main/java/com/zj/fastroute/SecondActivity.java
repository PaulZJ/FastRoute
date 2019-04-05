package com.zj.fastroute;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zj.annotation.Inject;
import com.zj.annotation.Route;
import com.zj.api.core.KRouter;

/**
 * Created by zhangjun on 2018/1/24.
 */
@Route(path = "second")
public class SecondActivity extends AppCompatActivity {
    @Inject(name = "int_value")
    private int valueFromIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        KRouter.INSTANCE.inject(this);
    }
}
