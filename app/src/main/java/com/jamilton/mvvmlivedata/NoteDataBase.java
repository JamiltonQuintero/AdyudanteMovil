package com.jamilton.mvvmlivedata;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopolateDbAsynTask(instance).execute();
        }
    };

    private static class PopolateDbAsynTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private PopolateDbAsynTask(NoteDataBase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("title 1", "Descripcion 1 ", 1));
            noteDao.insert(new Note("title 2", "Descripcion 1 ", 2));
            noteDao.insert(new Note("title 2", "Descripcion 1 ", 3));
            return null;
        }
    }

}
