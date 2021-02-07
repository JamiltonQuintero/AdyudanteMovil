package com.jamilton.gestiondeltiempo.view.iu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jamilton.gestiondeltiempo.MainActivity;
import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.notificaciones.AlertReceiver;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.presenter.DescripcionEventoFragmentPresenter;
import com.jamilton.gestiondeltiempo.presenter.IDescripcionEventoFragmentPresenter;
import com.jamilton.gestiondeltiempo.presenter.viewmodel.EventoViewModel;
import com.squareup.timessquare.CalendarPickerView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class AddEditEventoActivivy extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.jamilton.mvvmlivedata.EXTRA_ID";

    public static final String EXTRA_EVENTO =
            "com.jamilton.mvvmlivedata.EXTRA_EVENTO";

    public static final String EXTRA_LONG =
            "com.jamilton.mvvmlivedata.EXTRA_LONG";

    private EditText etTitulo, etDescripcion;
    private FloatingActionButton imgTitulo, igmDescripcion;
    private Button btnGuardar,btnCalendario,btnHora;
    private LinearLayout llTodo;
    private CalendarPickerView pickerView;
    private IDescripcionEventoFragmentPresenter mPresenter;
    private EventoViewModel evetoViewModel;
    private String diaNomm;
    private int diaa, anioa;
    private int mess;

    private static final int RECOGNIZER_RESULT_TITULO = 1;
    private static final int RECOGNIZER_RESULT_DESCRIPCION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_evento_activivy);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion= findViewById(R.id.etDescripcion);
        imgTitulo = findViewById(R.id.imgTitulo);
        igmDescripcion = findViewById(R.id.imgDescripcion);
        llTodo = findViewById(R.id.llTodo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnHora = findViewById(R.id.btnHora);
        btnCalendario = findViewById(R.id.btnFecha);
        pickerView = findViewById(R.id.tpFecha);
        evetoViewModel = new ViewModelProvider(this).get(EventoViewModel.class);
        mPresenter = new DescripcionEventoFragmentPresenter(etTitulo,etDescripcion,AddEditEventoActivivy.this,diaa,anioa,diaNomm,mess,pickerView);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Modificar Evento");

        }else {
            setTitle("Agregar evento");
        }

        imgTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(mPresenter.capturarVoz(),RECOGNIZER_RESULT_TITULO);
            }
        });

        igmDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(mPresenter.capturarVoz(),RECOGNIZER_RESULT_DESCRIPCION);
            }
        });


        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int h = calendar.get(Calendar.HOUR_OF_DAY);
                int m = calendar.get(Calendar.MINUTE);

              TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       int h = hourOfDay;
                       int m = minute;
                       mPresenter.hora(h,m);


                   }

               },h,m,true);

             timePicker.show();

            }
        });


        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llTodo.setVisibility(View.GONE);
                btnGuardar.setVisibility(View.GONE);
                pickerView.setVisibility(View.VISIBLE);

                pickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(Date date) {
                        mPresenter.Calendario(date);
                        llTodo.setVisibility(View.VISIBLE);
                        btnGuardar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDateUnselected(Date date) {

                    }
                });



            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = mPresenter.verirficarCampos();

                if (!check){
                    return;
                }

                Intent intent1 = new Intent(AddEditEventoActivivy.this, MainActivity.class);
                int id = getIntent().getIntExtra(EXTRA_ID,-1);

                if (id == -1){

                    Evento evento =mPresenter.crearEvento();
                    Toast.makeText(AddEditEventoActivivy.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    evetoViewModel.insert(evento);
                    intent1.putExtra(EXTRA_LONG,evento.getL());

                }
                else{

                    Evento evento= mPresenter.crearEvento();
                    evento.setId(id);
                    evetoViewModel.update(evento);
                    Toast.makeText(AddEditEventoActivivy.this, "Modificación Exitoso", Toast.LENGTH_SHORT).show();
                }

                setResult(RESULT_OK,intent1);
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       mPresenter.llenarEditText(requestCode,resultCode,data);
    }

    private void startAlarm(long c , int id) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c, pendingIntent);
        Log.i("Fecha", String.valueOf(c));
    }


}