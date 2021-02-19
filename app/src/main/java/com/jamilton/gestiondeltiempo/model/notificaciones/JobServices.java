package com.jamilton.gestiondeltiempo.model.notificaciones;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class JobServices extends JobService {

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

                if (jobCancelled){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);

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

                startActivity(intent);

                Log.d("TAGG", "Job finished" );
                jobFinished(params, false);

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
