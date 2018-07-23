package com.zj.fastroute;

import android.app.Application;

import com.zj.api.core.KRouter;


/**
 * Created by zhangjun on 2018/1/24.
 */

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KRouter.init(this);
    }
}
