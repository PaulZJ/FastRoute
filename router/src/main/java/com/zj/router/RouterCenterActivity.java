package com.zj.router;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zhangjun on 2018/1/24.
 */

public class RouterCenterActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            String url = getIntent().getDataString();
            if (data.getScheme().equals("http") && !TextUtils.isEmpty(Router.getHttpHost()) && Router.getHttpHost().equals(data.getHost())) {
                url = url.replaceFirst("http", Router.getScheme()).replace(Router.getHttpHost() + "/", "");
            }
            Router.startActivity(this, url);
        }
        this.finish();
        Log.e("中央路由", "RouterCenterActivity");
    }
}
