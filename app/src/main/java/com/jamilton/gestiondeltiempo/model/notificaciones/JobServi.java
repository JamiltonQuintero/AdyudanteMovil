package com.jamilton.gestiondeltiempo.model.notificaciones;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class JobServi extends JobService {

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("TAGG", "onStarJob");

        doBackWork(params);
        return true;
    }

    private void doBackWork(JobParameters params){
        Log.d("TAGG", "doBackWork");
        new Thread (new Runnable() {
            @Override
            public void run() {
                for (int i= 0; i< 1; i++){
                    if (jobCancelled){
                        return;
                    }
                    Log.d("TAGG", "RUN" + i);
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){

                    }
                }

                Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
/*
                int id = params.getExtras().getInt("ID");
                String ampm =  params.getExtras().getString("AMPM");
                String titulo = params.getExtras().getString("TITULO");
                String dia = params.getExtras().getString("DIA");
                String nombreDia = params.getExtras().getString("NOMBREDIA");
                String hora = params.getExtras().getString("HORA");
                String descripcion = params.getExtras().getString("DESCRIPCION");
                long fecha = params.getExtras().getLong("FECHA", -1);
                int img = params.getExtras().getInt("IMG", -1);
/*
                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("DESCRIPCION", descripcion);
                intent.putExtra("HORA", hora);
                intent.putExtra("AMPM", ampm);
                intent.putExtra("DIA", dia);
                intent.putExtra("NOMBREDIA", nombreDia);
                intent.putExtra("FECHA", fecha);
                intent.putExtra("IMG", img);*/

                Log.i("ESTOY AQUI","YA sabes donde" );


                Log.d("TAGG", "Job finished" );
                jobFinished(params, false);
/*
                NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification(id ,ampm, titulo, dia, nombreDia, hora, descripcion, fecha, img);
                notificationHelper.getManager().notify(id, nb.build());*/

            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAGG", "onStopJob");

        jobCancelled = true;

        return false;
    }
}

/*Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);

                int id = params.getExtras().getInt("ID");
                String ampm =  params.getExtras().getString("AMPM");
                String titulo = params.getExtras().getString("TITULO");
                String dia = params.getExtras().getString("DIA");
                String nombreDia = params.getExtras().getString("NOMBREDIA");
                String hora = params.getExtras().getString("HORA");
                String descripcion = params.getExtras().getString("DESCRIPCION");
                long fecha = params.getExtras().getLong("FECHA", -1);
                int img = params.getExtras().getInt("IMG", -1);

                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("DESCRIPCION", descripcion);
                intent.putExtra("HORA", hora);
                intent.putExtra("AMPM", ampm);
                intent.putExtra("DIA", dia);
                intent.putExtra("NOMBREDIA", nombreDia);
                intent.putExtra("FECHA", fecha);
                intent.putExtra("IMG", img);

                startActivity(intent);*/