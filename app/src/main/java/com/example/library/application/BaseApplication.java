package com.example.library.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2019/6/18  14:41
 * desc   : 基类
 */
public class BaseApplication extends Application {

    public Context context;

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication instance;

    public static final List<Activity> activities = new LinkedList<>();

    private static final boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        context = getApplicationContext();

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 完全退出
     * 一般用于“退出程序”功能
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
