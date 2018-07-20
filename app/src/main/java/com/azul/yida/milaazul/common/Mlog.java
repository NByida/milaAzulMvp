package com.azul.yida.milaazul.common;

import android.util.Log;

import com.azul.yida.milaazul.BuildConfig;

public class Mlog {

    public static void i(String tag,String log){
        if(BuildConfig.DEBUG){
            Log.i(tag,log);
        }
    }

    public static void d(String tag,String log){
        Log.d(tag,log);
        }

    public static void t(String log){
        if(BuildConfig.DEBUG){
            Log.d("tag_test:",log);
        }
    }

    public static void v(String log){
        if(BuildConfig.DEBUG){
            Log.d("tag_view:",log);
        }
    }

    public static void e(String log){
        if(BuildConfig.DEBUG){
            Log.d("tag_error:",log);
        }
    }
}
