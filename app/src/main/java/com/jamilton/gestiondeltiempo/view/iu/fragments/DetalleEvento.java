package com.jamilton.gestiondeltiempo.view.iu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.jamilton.gestiondeltiempo.model.notificaciones.NotificationHelper;
import com.jamilton.gestiondeltiempo.view.iu.activities.MainActivity;
import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.model.pojo.Evento;
import com.jamilton.gestiondeltiempo.presenter.viewmodel.EventoViewModel;

public class DetalleEvento extends DialogFragment {

    private Evento evento;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_fragment_detalle,container,false);

        Button finalizar = view.findViewById(R.id.btnFinalizar);

        TextView titulo = view.findViewById(R.id.tvTituloDes);
        TextView dia = view.findViewById(R.id.tvFechaDes);
        TextView diaN = view.findViewById(R.id.tvDiaNomDes);
        TextView hora = view.findViewById(R.id.tvHoraDes);
        TextView ampm = view.findViewById(R.id.tvAmPmDes);
        TextView descripcion = view.findViewById(R.id.tvDescripcionDes);

        titulo.setText(evento.getTitulo().toUpperCase());
        dia.setText(evento.getDia().toUpperCase());
        diaN.setText(evento.getDiaN().toUpperCase());
        hora.setText(evento.getHora().toUpperCase());
        ampm.setText(evento.getAmpm().toUpperCase());
        descripcion.setText(evento.getDescripcion().toUpperCase());


        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        return view;

    }


    public void startActivityForResult(Intent intent, int requesCode) {


        if (requesCode == MainActivity.DETALLE_EVENTO_REQUEST_MAIN){
            int id = intent.getIntExtra(MainActivity.EXTRA_EVENTO_ID, -1);
            String ampm = intent.getStringExtra(MainActivity.EXTRA_EVENTO_AMPM);

            evento = new Evento(intent.getStringExtra(MainActivity.EXTRA_EVENTO_TITULO),
                    intent.getStringExtra(MainActivity.EXTRA_EVENTO_DIA),
                    intent.getStringExtra(MainActivity.EXTRA_EVENTO_DIA_NOMBRE),
                    intent.getStringExtra(MainActivity.EXTRA_EVENTO_HORA),
                    ampm,
                    intent.getStringExtra(MainActivity.EXTRA_EVENTO_DESCRIPCION),
                    intent.getLongExtra(MainActivity.EXTRA_EVENTO_LONG, -1),
                    intent.getIntExtra(MainActivity.EXTRA_EVENTO_IMG, -1));
            evento.setId(id);

        }

    }


}
