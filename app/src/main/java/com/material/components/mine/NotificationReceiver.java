package com.material.components.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.material.components.activity.menu.MenuDrawerNews;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 创建并显示通知

        Intent intent1 = new Intent(context, MenuDrawerNews.class);
        MyNotificationManager.getInstance().showNotification(context, "通知", "请上报您的健康数据", intent1);

    }


}
