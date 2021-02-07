package com.jamilton.gestiondeltiempo.presenter.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.model.db.EventoRepository;

import java.util.List;

public class EventoViewModel extends AndroidViewModel {

    private EventoRepository repository;
    private LiveData<List<Evento>> allEventos;

    public EventoViewModel(@NonNull Application application) {
        super(application);

        repository = new EventoRepository(application);
        allEventos = repository.getAllEvento();
    }

    public void insert(Evento evento){
        repository.insert(evento);
    }

    public void delete(Evento evento){
        repository.delete(evento);
    }

    public void update(Evento evento){
        repository.update(evento);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Evento>> getAllEventos(){
        return allEventos;
    }

}
