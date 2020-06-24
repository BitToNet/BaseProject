package com.example.library.utils;

import android.util.Log;

import com.example.library.application.BaseApplication;

public class LogUtils {

    public static void logMethod(Object msg) {
        try {
            if (BaseApplication.isDebug()) {
                StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
                d("method",
                  stackTrace.getFileName() + ":" + stackTrace.getMethodName() + ":" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(String tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.v(tag, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(Object tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.v(tag.getClass().getSimpleName(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(String tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.i(tag, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(Object tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.i(tag.getClass().getSimpleName(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.d(tag, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(Object tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.d(tag.getClass().getSimpleName(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void w(String tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.w(tag, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void w(Object tag, String msg) {
        try {
            if (BaseApplication.isDebug()) {
                Log.w(tag.getClass().getSimpleName(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(String tag, Object obj) {
        try {
            if (BaseApplication.isDebug()) {
                Log.e(tag, obj + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(Object tag, Object obj) {
        try {
            if (BaseApplication.isDebug()) {
                Log.e(tag.getClass().getSimpleName(), obj + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
