package com.jamilton.gestiondeltiempo.view.iu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.presenter.viewmodel.EventoViewModel;

public class DetalleEve extends AppCompatActivity {

    Evento evento;

    private EventoViewModel evetoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_eve);
        Button finalizar = findViewById(R.id.btnFinalizarActi);

        int id = getIntent().getIntExtra("EXTRA_EVENTO_ID", -1);


        Log.i("TAGGGID", ""+ id);

        evento = new Evento(getIntent().getStringExtra("EXTRA_EVENTO_TITULO"),
                getIntent().getStringExtra("EXTRA_EVENTO_DIA"),
                getIntent().getStringExtra("EXTRA_EVENTO_DIA_NOMBRE"),
                getIntent().getStringExtra("EXTRA_EVENTO_HORA"),
                getIntent().getStringExtra("EXTRA_EVENTO_AMPM"),
                getIntent().getStringExtra("EXTRA_EVENTO_DESCRIPCION"),
                getIntent().getLongExtra("EXTRA_EVENTO_LONG", -1),
                getIntent().getIntExtra("EXTRA_EVENTO_IMG", -1));


        evento.setId(id);
        TextView titulo = findViewById(R.id.tvTituloDesActi);
        TextView dia = findViewById(R.id.tvFechaDesActi);
        TextView diaN = findViewById(R.id.tvDiaNomDesActi);
        TextView hora = findViewById(R.id.tvHoraDesActi);
        TextView ampm = findViewById(R.id.tvAmPmDesActi);
        TextView descripcion = findViewById(R.id.tvDescripcionDesActi);

        titulo.setText(evento.getTitulo().toUpperCase());
        dia.setText(evento.getDia().toUpperCase());
        diaN.setText(evento.getDiaN().toUpperCase());
        hora.setText(evento.getHora().toUpperCase());
        ampm.setText(evento.getAmpm().toUpperCase());
        descripcion.setText(evento.getDescripcion().toUpperCase());


        evetoViewModel = new ViewModelProvider(this).get(EventoViewModel.class);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalleEve.this, MainActivity.class);
                startActivity(intent);
                evetoViewModel.delete(evento);

                finish();

            }
        });
    }

}