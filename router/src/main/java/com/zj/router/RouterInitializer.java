package com.zj.router;

import android.app.Activity;

import java.util.Map;

/**
 * Created by zhangjun on 2018/1/24.
 */

public interface RouterInitializer {
    void init(Map<String, Class<? extends Activity>> router);
}
