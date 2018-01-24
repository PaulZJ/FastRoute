package com.zj.router;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.zj.router.annotation.RouterActivity;
import com.zj.router.annotation.RouterField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjun on 2018/1/24.
 */

public class Router {
    private static Map<String, Class<? extends Activity>> mRouter = new HashMap<>();
    private static String mScheme = "router";
    private static String mHttpHost = "";
    private static Filter mFilter;

    private Router(Activity activity) {

    }

    private static List<Field> getDeclaredFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    public static void inject(Activity activity) {
        SafeBundle bundle = new SafeBundle(activity.getIntent().getExtras(), activity.getIntent().getData());
        Class clazz = activity.getClass();
        List<Field> fields = getDeclaredFields(clazz);
        System.out.println(fields.size());
        for (Field field : fields) {
            RouterField annotation = field.getAnnotation(RouterField.class);
            if (annotation == null) {
                continue;
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            String[] names = annotation.value();
            try {
                for (String name : names) {
                    if (!bundle.containsKey(name)) {
                        continue;
                    }
                    if (type.equals("double")) {
                        field.set(activity, bundle.getDouble(name, field.getDouble(activity)));
                        continue;
                    } else if (type.equals("float")) {
                        field.set(activity, bundle.getFloat(name, field.getFloat(activity)));
                        continue;
                    } else if (type.equals("int")) {
                        field.set(activity, bundle.getInt(name, field.getInt(activity)));
                        continue;
                    } else if (type.equals("boolean")) {
                        field.set(activity, bundle.getBoolean(name, field.getBoolean(activity)));
                        continue;
                    }
                    Object defaultValue = field.get(activity);
                    if (field.getGenericType() == String.class) {
                        field.set(activity, bundle.getString(name, (String) defaultValue));
                    } else if (field.getGenericType() == Double.class) {
                        field.set(activity, bundle.getDouble(name, defaultValue != null ? (Double) defaultValue : 0));
                    } else if (field.getGenericType() == Float.class) {
                        field.set(activity, bundle.getFloat(name, defaultValue != null ? (Float) defaultValue : 0));
                    } else if (field.getGenericType() == Integer.class) {
                        field.set(activity, bundle.getInt(name, defaultValue != null ? (Integer) defaultValue : 0));
                    } else if (field.getGenericType() == Boolean.class) {
                        field.set(activity, bundle.getBoolean(name, defaultValue != null ? (Boolean) defaultValue : false));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 二级跳转
        if (activity.getIntent().getData() != null) {
            String url = activity.getIntent().getDataString();
            RouterActivity routerActivity = (RouterActivity) clazz.getAnnotation(RouterActivity.class);
            if (routerActivity != null) {
                for (String path : routerActivity.value()) {
                    if (url.contains(path + "/")) {
                        url = url.replace(path + "/", "");
                        Router.startActivity(activity, url);
                        break;
                    }
                }
            }
        }
    }

    public static void register(RouterInitializer routerInitializer) {
        routerInitializer.init(mRouter);
    }

    private static Class<? extends Activity> getActivityClass(String url, Uri uri) {
        String key;
        int tmp = url.indexOf("?");
        if (tmp > 0) {
            key = url.substring(0, tmp);
        }else {
            key = url;
        }
        Class<? extends Activity> clazz = mRouter.get(key);
        if (null != clazz) {
            return clazz;
        }
        if (mScheme.equals(uri.getScheme())) {
            key = uri.getHost();
            return mRouter.get(key);
        }
        return null;
    }

    public static boolean startActivity(Context context, String url) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
            if (mFilter.start(context, url)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        Class clazz = getActivityClass(url,uri);
        if (null != clazz) {
            Intent intent = new Intent(context, clazz);
            intent.setData(uri);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        }else {
            new Throwable(url + " can not startActivity").printStackTrace();
        }
        return false;
    }

    public static boolean startActivityForResult(Activity activity, String url, int requestCode) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
            if (mFilter.startActivityForResult(activity, url, requestCode)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        Class clazz = getActivityClass(url,uri);
        if (null != clazz) {
            Intent intent = new Intent(activity, clazz);
            intent.setData(uri);
            activity.startActivityForResult(intent, requestCode);
            return true;
        }else {
            new Throwable(url + " can not startActivity").printStackTrace();
        }
        return false;
    }

    public static boolean startActivityForResult(Fragment fragment, String url, int requestCode) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
            if (mFilter.startActivityForResult(fragment, url, requestCode)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        Class clazz = getActivityClass(url,uri);
        if (null != clazz) {
            Intent intent = new Intent(fragment.getActivity(), clazz);
            intent.setData(uri);
            fragment.startActivityForResult(intent, requestCode);
            return true;
        }else {
            new Throwable(url + " can not startActivity").printStackTrace();
        }
        return false;
    }

    public static boolean startActivityForResult(android.support.v4.app.Fragment fragment, String url, int
            requestCode) {
        if (mFilter != null) {
            url = mFilter.doFilter(url);
            if (mFilter.startActivityForResult(fragment, url, requestCode)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        Class clazz = getActivityClass(url,uri);
        if (null != clazz) {
            Intent intent = new Intent(fragment.getActivity(), clazz);
            intent.setData(uri);
            fragment.startActivityForResult(intent, requestCode);
            return true;
        }else {
            new Throwable(url + " can not startActivity").printStackTrace();
        }
        return false;
    }

    public static String getHttpHost() {
        return mHttpHost;
    }

    public static void setHttpHost(String httpHost) {
        Router.mHttpHost = httpHost;
    }

    public static String getScheme() {
        return mScheme;
    }

    public static void init(String scheme) {
        Router.mScheme = scheme;
        try {
            Class.forName("com.zj.router.AptRouterInitializer");
        }catch (ClassNotFoundException e) {

        }
    }

    public static void setFilter(Filter filter) {
        Router.mFilter = filter;
    }
}
