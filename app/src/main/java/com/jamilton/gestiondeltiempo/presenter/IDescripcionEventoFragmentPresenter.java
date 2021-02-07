package com.jamilton.gestiondeltiempo.presenter;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.jamilton.gestiondeltiempo.model.pojo.Evento;

import java.util.Calendar;
import java.util.Date;

public interface IDescripcionEventoFragmentPresenter {

    public Intent capturarVoz();

    public void llenarEditText(int requestCode, int resultCode, @Nullable Intent data);

    public Evento crearEvento();

    public void Calendario(Date date);

    public void initCalendario();

    public void hora(int hora, int minuto);

    public Boolean verirficarCampos();




}
