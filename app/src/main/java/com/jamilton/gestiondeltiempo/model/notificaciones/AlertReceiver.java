package com.jamilton.gestiondeltiempo.model.notificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
        Toast.makeText(context, "Notificado", Toast.LENGTH_SHORT).show();
    }
}