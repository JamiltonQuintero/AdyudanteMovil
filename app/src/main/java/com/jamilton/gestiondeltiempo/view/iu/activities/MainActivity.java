package com.jamilton.gestiondeltiempo.view.iu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.adapter.EventoAdapter;
import com.jamilton.gestiondeltiempo.model.notificaciones.AlertReceiver;
import com.jamilton.gestiondeltiempo.model.notificaciones.JobServi;
import com.jamilton.gestiondeltiempo.model.notificaciones.NotificationHelper;
import com.jamilton.gestiondeltiempo.model.notificaciones.servicio.ProcessMainClass;
import com.jamilton.gestiondeltiempo.model.notificaciones.servicio.RestartServiceBroadcastReceiver;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.presenter.viewmodel.EventoViewModel;
import com.jamilton.gestiondeltiempo.view.iu.fragments.DetalleEvento;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EVENTO_ID =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_DETALLE";

    public static final String EXTRA_EVENTO_TITULO =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_TITULO";

    public static final String EXTRA_EVENTO_DESCRIPCION =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_DESCRIPCION";

    public static final String EXTRA_EVENTO_HORA =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_HORA";

    public static final String EXTRA_EVENTO_DIA =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_DIA";

    public static final String EXTRA_EVENTO_DIA_NOMBRE =
            "com.jamilton.mvvmlivedata.EXTRA_DIA_NOMBRE";

    public static final String EXTRA_EVENTO_AMPM =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_AMPM";

    public static final String EXTRA_EVENTO_LONG =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_LONG";

    public static final String EXTRA_EVENTO_ID_DRES =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_ID_DRES";

    public static final String EXTRA_EVENTO_IMG =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO_IMG";

    public static final int ADD_EVENTO_REQUEST = 1;
    public static final int EDIT_EVENTO_REQUEST = 2;
    public static final int DETALLE_EVENTO_REQUEST_MAIN = 3;

    private EventoViewModel evetoViewModel;
    private long l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.miToll);
        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle("Mis recordatorios");

        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditEventoActivivy.class);
                startActivityForResult(intent,ADD_EVENTO_REQUEST);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        final EventoAdapter adapter = new EventoAdapter(this);
        recyclerView.setAdapter(adapter);




        evetoViewModel = new ViewModelProvider(this).get(EventoViewModel.class);
        evetoViewModel.getAllEventos().observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> eventos) {
               adapter.setEventos(eventos);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                evetoViewModel.delete(adapter.getEventosAT(viewHolder.getAdapterPosition()));
                cancelAlarm(adapter.getEventosAT(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(MainActivity.this, "Recordatorio eliminado", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento evento) {
                Intent intent = new Intent(MainActivity.this, AddEditEventoActivivy.class);
                intent.putExtra(AddEditEventoActivivy.EXTRA_ID,evento.getId());
                startActivityForResult(intent,EDIT_EVENTO_REQUEST);

            }
        });

        adapter.setOnItemClickListenerrr(new EventoAdapter.OnItemClickListenerrr() {
            @Override
            public void onItemClick(Evento evento, View view) {
                Toast.makeText(MainActivity.this, "Recordatorio Fijado", Toast.LENGTH_SHORT).show();
                evento.setImg(R.drawable.ic_baseline_check_24);
                evetoViewModel.update(evento);
               // startAlarm(evento);
/*
                Long l = evento.getL() - System.currentTimeMillis();
                Data data = guardarData(evento);
                WorkManagerNotificacion.GuardarNoti(l,data,"1");*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    RestartServiceBroadcastReceiver.scheduleJob(getApplicationContext());
                } else {
                    ProcessMainClass bck = new ProcessMainClass();
                    bck.launchService(getApplicationContext());
                }
            }
        });

        adapter.setOnItemClickListenerrrno(new EventoAdapter.OnItemClickListenerrrno() {
            @Override
            public void onItemClick(Evento evento, View view) {
                DetalleEvento detalleEvento = new DetalleEvento();
                detalleEvento.show(getSupportFragmentManager(),"Detalle evento");
                Intent intent = new Intent();
                intent.putExtra(EXTRA_EVENTO_ID, evento.getId());
                intent.putExtra(EXTRA_EVENTO_TITULO, evento.getTitulo());
                intent.putExtra(EXTRA_EVENTO_DESCRIPCION, evento.getDescripcion());
                intent.putExtra(EXTRA_EVENTO_HORA, evento.getHora());
                intent.putExtra(EXTRA_EVENTO_AMPM, evento.getAmpm());
                intent.putExtra(EXTRA_EVENTO_DIA, evento.getDia());
                intent.putExtra(EXTRA_EVENTO_DIA_NOMBRE, evento.getDiaN());
                intent.putExtra(EXTRA_EVENTO_LONG, evento.getL());
                intent.putExtra(EXTRA_EVENTO_IMG,evento.getImg());
                detalleEvento.startActivityForResult(intent, DETALLE_EVENTO_REQUEST_MAIN);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.elimAllEvent:

                    evetoViewModel.getAllEventos().observe(this, new Observer<List<Evento>>() {
                        @Override
                        public void onChanged(List<Evento> eventos) {
                            for (Evento eve: eventos
                            ) {
                                int id;
                                id= eve.getId();
                                cancelAlarm(id);
                            }
                        }
                    });
                evetoViewModel.deleteAll();
                Toast.makeText(this, "Se eliminaron todos los recordatorios", Toast.LENGTH_SHORT).show();
                break;

            case R.id.desarrollador:

                Intent intent = new Intent(MainActivity.this, SobreElDesarrollador.class);
                startActivity(intent);
                break;

            case R.id.configNoti:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intent1 = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    intent1.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationHelper.channelID);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent1.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    intent1.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationHelper.channelID);
                    startActivity(intent1);
                }


            default:
                Toast.makeText(this, "Algo sucedio, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void startAlarm(Evento evento) {




        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            Intent intent = new Intent(this, AlertReceiver.class);

            intent.putExtra("ID", evento.getId());
            intent.putExtra("TITULO", evento.getTitulo());
            intent.putExtra("DESCRIPCION", evento.getDescripcion());
            intent.putExtra("HORA", evento.getHora());
            intent.putExtra("AMPM", evento.getAmpm());
            intent.putExtra("DIA", evento.getDia());
            intent.putExtra("NOMBREDIA", evento.getDiaN());
            intent.putExtra("FECHA", evento.getL());
            intent.putExtra("IMG", evento.getImg());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, evento.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, evento.getL(), pendingIntent);
        } else {

            PersistableBundle bundle = new  PersistableBundle();

            bundle.putInt("ID", evento.getId());
            bundle.putString("TITULO", evento.getTitulo());
            bundle.putString("DESCRIPCION", evento.getDescripcion());
            bundle.putString("HORA", evento.getHora());
            bundle.putString("AMPM", evento.getAmpm());
            bundle.putString("DIA", evento.getDia());
            bundle.putString("NOMBREDIA", evento.getDiaN());
            bundle.putLong("FECHA", evento.getL());
            bundle.putInt("IMG", evento.getImg());

            ComponentName componentName = new ComponentName(getApplicationContext(), JobServi.class);
            JobInfo jobInfo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobInfo = new JobInfo.Builder(evento.getId(), componentName)
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        //.setExtras(bundle)
                        .setMinimumLatency(evento.getL() - System.currentTimeMillis())
                        .build();
                Log.i("TAGGLLi", ""+ evento.getL());
                Log.i("TAGGLLo", ""+ (evento.getL() - System.currentTimeMillis()));
            } else {
                jobInfo = new JobInfo.Builder(evento.getId(), componentName)
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .setPeriodic(evento.getL() - System.currentTimeMillis())
                        //.setExtras(bundle)
                        .build();
                Log.d("TAGGL", ""+ evento.getL());
                Log.d("TAGGL", ""+ (evento.getL() - System.currentTimeMillis()));
            }
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultado = scheduler.schedule(jobInfo);
            if (resultado == JobScheduler.RESULT_SUCCESS) {
                Log.d("TAGG", "ACABADO");
            } else {
                Log.d("TAGG", "Job a fallado");
            }
        }
    }
    private void cancelAlarm(int id) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.cancel(pendingIntent);

    }

}