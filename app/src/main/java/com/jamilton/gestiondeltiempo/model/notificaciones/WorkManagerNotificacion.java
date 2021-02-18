package com.jamilton.gestiondeltiempo.model.notificaciones;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class WorkManagerNotificacion extends Worker {

    private Context context;

    public WorkManagerNotificacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    public static void GuardarNoti(long duracion, Data data, String tag){
        OneTimeWorkRequest notification = new OneTimeWorkRequest.Builder(WorkManagerNotificacion.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notification);
    }

    @NonNull
    @Override
    public Result doWork() {

        int id =  getInputData().getInt("ID", -1);
        String ampm = getInputData().getString("AMPM");
        String titulo = getInputData().getString("TITULO");
        String dia = getInputData().getString("DIA");
        String nombreDia = getInputData().getString("NOMBREDIA");
        String hora = getInputData().getString("HORA");
        String descripcion = getInputData().getString("DESCRIPCION");
        long fecha = getInputData().getLong("FECHA", -1);
        int img = getInputData().getInt("IMG", -1);
/*
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(id, ampm, titulo, dia, nombreDia, hora, descripcion, fecha, img);
        notificationHelper.getManager().notify(1, nb.build());
*/
        return Result.success();
    }

}

