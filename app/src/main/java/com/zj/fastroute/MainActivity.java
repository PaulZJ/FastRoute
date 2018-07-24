package com.zj.fastroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zj.annotation.Route;
import com.zj.api.core.KRouter;

@Route(path = "main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KRouter.INSTANCE.create("second").request();
            }
        });
    }
}
