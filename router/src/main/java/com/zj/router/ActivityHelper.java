package com.zj.router;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangjun on 2018/1/24.
 *
 * Activity 跳转的帮助类
 *
 * <p>通过apt的方式自动生成每个Activity跳转的帮助类，自动拼接url</p>
 */

public class ActivityHelper {

    /** 跳转Activity的Path */
    protected final String host;

    public ActivityHelper(String host) {
        this.host = host;
    }

    /** Activity跳转所携带的参数 */
    protected Map<String, String> params = new HashMap<>();

    /** 拼接跳转的url */
    public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(Router.getScheme()).append("://").append(host);
        Set<String> keys = params.keySet();
        int i = 0;
        for (String key : keys) {
            String value = params.get(key);
            if (value == null){
                continue;
            }
            if (i == 0) {
                builder.append('?');
            }
            try {
                builder.append(key).append('=').append(URLEncoder.encode(value,"UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i < (keys.size() - 1)) {
                builder.append('&');
            }
            i++;
        }
        return builder.toString();
    }

    public void start(Context context) {
        Router.startActivity(context, getUrl());
    }

    public void startForResult(Activity activity, int requestCode) {
        Router.startActivityForResult(activity, getUrl(), requestCode);
    }
    public void startForResult(Fragment fragment, int requestCode) {
        Router.startActivityForResult(fragment, getUrl(), requestCode);
    }
    public void startForResult(android.support.v4.app.Fragment fragment, int requestCode) {
        Router.startActivityForResult(fragment, getUrl(), requestCode);
    }


    public String put(String key, String value) {
        return params.put(key, value);
    }

    public String put(String key, double value) {
        return params.put(key, String.valueOf(value));
    }

    public String put(String key, float value) {
        return params.put(key, String.valueOf(value));
    }

    public String put(String key, int value) {
        return params.put(key, String.valueOf(value));
    }

    public String put(String key, boolean value) {
        return params.put(key, String.valueOf(value));
    }

}
