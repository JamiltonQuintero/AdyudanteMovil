package com.jamilton.gestiondeltiempo.model.notificaciones;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.view.iu.activities.MainActivity;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra("ID",-1);
                String ampm = intent.getStringExtra("AMPM");
                String titulo = intent.getStringExtra("TITULO");
                String dia =intent.getStringExtra("DIA");
                String nombreDia = intent.getStringExtra("NOMBREDIA");
                String hora =  intent.getStringExtra("HORA");
               String descripcion =  intent.getStringExtra("DESCRIPCION");
               long fecha = intent.getLongExtra("FECHA", -1);
                int img =  intent.getIntExtra("IMG", -1);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification( id,ampm, titulo,dia, nombreDia, hora, descripcion,fecha,img);
        notificationHelper.getManager().notify(1, nb.build());
        Toast.makeText(context, "Tienes un recordatiorio pendiente: " + titulo, Toast.LENGTH_SHORT).show();
    }
}