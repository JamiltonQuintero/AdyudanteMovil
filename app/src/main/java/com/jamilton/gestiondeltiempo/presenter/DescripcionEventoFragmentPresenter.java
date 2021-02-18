package com.jamilton.gestiondeltiempo.presenter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class DescripcionEventoFragmentPresenter implements IDescripcionEventoFragmentPresenter {

    private EditText etTitulo, etDescripcion;
    private Context context;
    private String  AMPM, horaCompleta,diaNom;
    private int mess,hora, minuto,diaa,anioo;
    private TimePickerDialog timePickerP;
    private CalendarPickerView pickerView;


    private static final int RECOGNIZER_RESULT_TITULO = 1;
    private static final int RECOGNIZER_RESULT_DESCRIPCION = 2;


    public DescripcionEventoFragmentPresenter(EditText etTitulo, EditText etDescripcion, Context context, int dia, int anio, String diaNom, int mes,CalendarPickerView pickerView) {
        this.etTitulo = etTitulo;
        this.etDescripcion = etDescripcion;
        this.context = context;
        this.diaa = dia;
        this.mess = mes;
        this.anioo = anio;
        this.diaNom = diaNom;
        this.pickerView = pickerView;
        initCalendario();

    }

    @Override
    public Intent capturarVoz() {

        Intent intTitulo = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intTitulo.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intTitulo.putExtra(RecognizerIntent.EXTRA_PROMPT,"Por favor habla fuerte y claro");
        return intTitulo;
    }

    @Override
    public void llenarEditText(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RECOGNIZER_RESULT_TITULO && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            etTitulo.setText(matches.get(0).toString());
        }

        if (requestCode == RECOGNIZER_RESULT_DESCRIPCION && resultCode == RESULT_OK){
            ArrayList<String> matchesTwo = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            etDescripcion.setText(matchesTwo.get(0).toString());
        }

    }


    @Override
    public void Calendario(Date date) {

        Calendar calSelected = Calendar.getInstance();
        calSelected.setTime(date);

        diaa =calSelected.get(Calendar.DAY_OF_MONTH);
        mess =calSelected.get(Calendar.MONTH);
        anioo =calSelected.get(Calendar.YEAR);
        diaNom =calSelected.get(Calendar.DAY_OF_WEEK)+"";

        pickerView.setVisibility(View.GONE);
    }



    private Calendar alarm(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH,diaa);
        date.set(Calendar.MONTH,mess);
        date.set(Calendar.YEAR,anioo);
        date.set(Calendar.HOUR_OF_DAY,hora);
        date.set(Calendar.MINUTE,minuto);
        date.set(Calendar.SECOND,0);
        return date;
    }



    @Override
    public void initCalendario() {
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        pickerView.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);


    }

    @Override
    public void hora(int h, int m) {

        hora = h;
        minuto = m;

    }


    @Override
    public Boolean verirficarCampos(){

        String titulo = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        boolean check;

        if (titulo.trim().isEmpty()){
            Toast.makeText(context, "No has ingresado el titulo", Toast.LENGTH_SHORT).show();
           check = false;
        }else if (descripcion.trim().isEmpty()){
            Toast.makeText(context, "No has ingresado la descripcion del evento del evento", Toast.LENGTH_SHORT).show();
            check = false;
        }else if(diaa == 0){
            Toast.makeText(context, "No has ingresado la fecha", Toast.LENGTH_SHORT).show();
            check = false;
        }else if (hora == 0 && minuto == 0){
            Toast.makeText(context, "No has ingresado la hora", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            check = true;
        }
        return check;
    }


    @Override
    public Evento crearEvento() {

        String titulo = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        int img = R.drawable.ic_baseline_access_alarm_24;


        verirficarCampos();

        if (hora >= 12){
            AMPM = "PM";
        } else {
            AMPM = "AM";
        }


        horaCompleta = hora +":"+ minuto;

        if ("1".equals(diaNom)){
            diaNom = "Domingo";
        } else if("2".equals(diaNom)){
            diaNom = "Lunes";
        } else if("3".equals(diaNom)){
            diaNom = "Martes";
        }else if("4".equals(diaNom)){
            diaNom = "Miercoles";
        }else if("5".equals(diaNom)){
            diaNom = "Jueves";
        }else if("6".equals(diaNom)){
            diaNom = "Viernes";
        } else {
            diaNom = "Sabado";
        }

        Evento evento = new Evento(titulo,String.valueOf(diaa),diaNom,horaCompleta,AMPM,descripcion,alarm().getTimeInMillis(),img);

        return evento;
    }



}
