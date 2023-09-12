package com.material.components.data;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.chunxia.mmkv.KVUtils;
import com.material.components.mine.MyNotificationManager;

public class ThisApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyNotificationManager.getInstance().createNotificationChannel(this);
        MyNotificationManager.getInstance().initNotification(this);

        KVUtils.get().init(this);
    }

}
