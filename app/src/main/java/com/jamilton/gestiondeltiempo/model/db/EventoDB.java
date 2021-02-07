package com.jamilton.gestiondeltiempo.model.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jamilton.gestiondeltiempo.model.pojo.Evento;

@Database(entities = {Evento.class},version = 1)
public abstract class EventoDB extends RoomDatabase {

    private static EventoDB instance;

    public abstract EventoDao eventoDao();

    public static synchronized EventoDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventoDB.class,"evento_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynTask(instance).execute();
        }
    };

    private static class PopulateDbAsynTask extends AsyncTask<Void,Void,Void>{
        private EventoDao eventoDao;

        private  PopulateDbAsynTask(EventoDB db){
            eventoDao = db.eventoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
