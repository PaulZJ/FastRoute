package com.zj.fastroute;

import android.app.Application;

import com.zj.router.Router;

/**
 * Created by zhangjun on 2018/1/24.
 */

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init("demo");
    }
}
