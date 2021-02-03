package com.jamilton.mvvmlivedata;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;



public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteASynTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteASynTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteASynTask(noteDao).execute(note);
    }

    public void deteleAll(Note note){

        new DeleteAllNoteASynTask(noteDao).execute();

    }




    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteASynTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private InsertNoteASynTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteASynTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private UpdateNoteASynTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteASynTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private DeleteNoteASynTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteASynTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private DeleteAllNoteASynTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
