package com.azul.yida.milaazul.AzulApplication;

import android.app.Application;

/**
 * Created by xuyimin on 2018/7/20.
 * E-mail codingyida@qq.com
 */

public class AzulApp extends Application {
    public static AzulApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static AzulApp getInstance() {
        return instance;
    }
}
