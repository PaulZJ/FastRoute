package com.zj.router;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

/**
 * Created by zhangjun on 2018/1/24.
 */

public interface Filter {
    String doFilter(String url);

    boolean startActivityForResult(Activity activity, String url, int requestCode);

    boolean start(Context context, String url);

    boolean startActivityForResult(Fragment fragment, String url, int requestCode);

    boolean startActivityForResult(android.support.v4.app.Fragment fragment, String url, int requestCode);
}
