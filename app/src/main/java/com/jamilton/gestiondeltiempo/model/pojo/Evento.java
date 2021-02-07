package com.jamilton.gestiondeltiempo.model.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "evento_table")

public class Evento {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;

    private String dia;

    private String diaN;

    private String hora;

    private String ampm;

    private String descripcion;

    private long l;

    private int img;



    public Evento(String titulo, String dia, String diaN, String hora, String ampm, String descripcion, long l, int img) {
        this.titulo = titulo;
        this.dia = dia;
        this.diaN = diaN;
        this.hora = hora;
        this.ampm = ampm;
        this.descripcion = descripcion;
        this.l = l;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDia() {
        return dia;
    }

    public String getDiaN() {
        return diaN;
    }

    public String getHora() {
        return hora;
    }

    public String getAmpm() {
        return ampm;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public long getL() {
        return l;
    }


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
