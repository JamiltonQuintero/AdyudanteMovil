package com.jamilton.gestiondeltiempo.model.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jamilton.gestiondeltiempo.model.pojo.Evento;

import java.util.List;

public class EventoRepository {
    private EventoDao eventoDao;
    private LiveData<List<Evento>> allEventos;


    public EventoRepository(Application application){
        EventoDB eventoDB = EventoDB.getInstance(application);
        eventoDao = eventoDB.eventoDao();
        allEventos = eventoDao.getAllEventos();
    }


    public void insert(Evento evento){
        new InsertEventoAsynTask(eventoDao).execute(evento);
    }

    public void delete(Evento evento){
        new DeleteEventoAsynTask(eventoDao).execute(evento);
    }

    public void update(Evento evento){
        new UpdateEventoAsynTask(eventoDao).execute(evento);
    }

    public void deleteAll(){
        new DeleteAllEventoAsynTask(eventoDao).execute();
    }

    public LiveData<List<Evento>> getAllEvento(){
        return allEventos;
    }


    public static class InsertEventoAsynTask extends AsyncTask<Evento,Void,Void>{
        private EventoDao eventoDao;
        private InsertEventoAsynTask(EventoDao eventoDao){
            this.eventoDao = eventoDao;
        }
        @Override
        protected Void doInBackground(Evento... eventos) {
            eventoDao.insert(eventos[0]);
            return null;
        }
    }

    public static class DeleteEventoAsynTask extends AsyncTask<Evento,Void,Void>{
        private  EventoDao eventoDao;
        private DeleteEventoAsynTask(EventoDao eventoDao){
            this.eventoDao = eventoDao;
        }
        @Override
        protected Void doInBackground(Evento... eventos) {
            eventoDao.delete(eventos[0]);
            return null;
        }
    }

    public static class UpdateEventoAsynTask extends AsyncTask<Evento,Void,Void>{
        private  EventoDao eventoDao;
        private UpdateEventoAsynTask(EventoDao eventoDao){
            this.eventoDao = eventoDao;
        }
        @Override
        protected Void doInBackground(Evento... eventos) {
            eventoDao.update(eventos[0]);
            return null;
        }
    }

    public static class DeleteAllEventoAsynTask extends AsyncTask<Void,Void,Void>{
        private  EventoDao eventoDao;
        private DeleteAllEventoAsynTask(EventoDao eventoDao){
            this.eventoDao = eventoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventoDao.deleteAllEventos();
            return null;
        }
    }

}
