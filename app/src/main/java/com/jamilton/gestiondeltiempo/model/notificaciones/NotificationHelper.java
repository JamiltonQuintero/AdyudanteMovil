package com.jamilton.gestiondeltiempo.model.notificaciones;


import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.jamilton.gestiondeltiempo.MainActivity;
import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.view.iu.DetalleEvento;

import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_AMPM;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_DESCRIPCION;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_DIA;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_DIA_NOMBRE;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_HORA;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_ID;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_LONG;
import static com.jamilton.gestiondeltiempo.MainActivity.EXTRA_EVENTO_TITULO;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

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


        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MainActivity.DETALLE_EVENTO_REQUEST, intent, 0);


        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Gestion Del tiempo")
                .setContentText("Tienes un pendiente, revisa la aplicación")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background);

    }
}