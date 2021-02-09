package com.jamilton.gestiondeltiempo.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import java.util.List;


@Dao
    public interface EventoDao   {

        @Insert
        void insert(Evento evento);

        @Update
        void update(Evento evento);

        @Delete
        void delete(Evento evento);

        @Query("DELETE FROM evento_table")
        void deleteAllEventos();

        @Query("SELECT * FROM evento_table ORDER BY l ASC")
        LiveData<List<Evento>> getAllEventos();



}
