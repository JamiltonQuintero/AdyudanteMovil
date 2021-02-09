package com.jamilton.gestiondeltiempo.model.notificaciones;


import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.jamilton.gestiondeltiempo.view.iu.activities.DetalleEve;
import com.jamilton.gestiondeltiempo.presenter.viewmodel.EventoViewModel;
import com.jamilton.gestiondeltiempo.R;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Notificaciones";

    public static final int DETALLE_EVENTO_REQUEST_HELPER = 5;

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(int id,String ampm,String titulo,String dia,String nombreDia,String hora,String descripcion,long fecha,int img) {

        Intent intent = new Intent(this, DetalleEve.class);
        intent.putExtra("EXTRA_EVENTO_ID",id);
        intent.putExtra("EXTRA_EVENTO_TITULO", titulo);
        intent.putExtra("EXTRA_EVENTO_DESCRIPCION", descripcion);
        intent.putExtra("EXTRA_EVENTO_HORA", hora);
        intent.putExtra("EXTRA_EVENTO_AMPM", ampm);
        intent.putExtra("EXTRA_EVENTO_DIA", dia);
        intent.putExtra("EXTRA_EVENTO_DIA_NOMBRE", nombreDia);
        intent.putExtra("EXTRA_EVENTO_LONG", fecha);
        intent.putExtra("EXTRA_EVENTO_IMG",img);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, DETALLE_EVENTO_REQUEST_HELPER , intent, PendingIntent.FLAG_UPDATE_CURRENT );
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_asismovillogosvg);
    }
}