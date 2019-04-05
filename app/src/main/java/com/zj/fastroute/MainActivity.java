package com.zj.fastroute;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zj.router.RouterHelper;
import com.zj.router.annotation.RouterActivity;

@RouterActivity({"main"})
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.getSecondActivityHelper().start(MainActivity.this);
            }
        });
    }
}
