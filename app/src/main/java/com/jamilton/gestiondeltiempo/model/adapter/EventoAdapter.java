package com.jamilton.gestiondeltiempo.model.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jamilton.gestiondeltiempo.model.notificaciones.AlertReceiver;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class    EventoAdapter extends RecyclerView.Adapter<EventoAdapter.NoteHolder> {

    private List<Evento>eventos = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemClickListenerrr listenerrr;
    private OnItemClickListenerrrno listenerrrno;
    private Context context;

    public EventoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eventos,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

            Evento evento = eventos.get(position);


             holder.dia.setText(evento.getDiaN());
             holder.diaN.setText(evento.getDia());
             holder.hora.setText(evento.getHora());
             holder.hPmAm.setText(evento.getAmpm());
             holder.descripcion.setText(evento.getDescripcion());
             holder.titulo.setText(evento.getTitulo());
             holder.recordar.setImageResource(evento.getImg());


    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
        notifyDataSetChanged();
    }

    public Evento getEventosAT(int posicion){

        return eventos.get(posicion);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        private TextView hora, dia ,diaN, hPmAm, titulo, descripcion, modificar,detalle;
        private ImageView recordar;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            hora = itemView.findViewById(R.id.tvHoraEvento);

            dia = itemView.findViewById(R.id.tvDia);
            diaN = itemView.findViewById(R.id.tvDiaN);
            hPmAm = itemView.findViewById(R.id.tvHoraEventoAMPM);
            titulo = itemView.findViewById(R.id.tvTituloEvento);
            descripcion = itemView.findViewById(R.id.tvDescripcionEvento);
            modificar = itemView.findViewById(R.id.tvModificar);
            recordar = itemView.findViewById(R.id.recordar);
            detalle = itemView.findViewById(R.id.tvDetalle);

            recordar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (listenerrr !=null &&  posicion != RecyclerView.NO_POSITION){
                        listenerrr.onItemClick(eventos.get(posicion),v);
                        notifyItemChanged(posicion);
                    }

                }
            });


            modificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (listener != null && posicion != RecyclerView.NO_POSITION){
                        listener.onItemClick(eventos.get(posicion));
                    }
                }
            });

            detalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (listenerrrno != null && posicion != RecyclerView.NO_POSITION){
                        listenerrrno.onItemClick(eventos.get(posicion),v);
                    }
                }
            });

        }
    }





    public interface OnItemClickListener{
        void onItemClick(Evento evento);
    }

    public interface OnItemClickListenerrr{
        void onItemClick(Evento evento,View view);
    }

    public interface OnItemClickListenerrrno{
        void onItemClick(Evento evento,View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener= listener;
    }


    public void setOnItemClickListenerrr(OnItemClickListenerrr listener){
        this.listenerrr= listener;
    }

    public void setOnItemClickListenerrrno(OnItemClickListenerrrno listener){
        this.listenerrrno = listener;

    }

}
