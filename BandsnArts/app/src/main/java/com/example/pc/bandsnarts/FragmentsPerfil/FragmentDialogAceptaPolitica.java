package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.bandsnarts.R;

@SuppressLint("ValidFragment")
public class FragmentDialogAceptaPolitica extends DialogFragment {
    Button btnAcepar, btnCancelar;
    TextView tv_titulo, tv_subtitulo;
    private static final String TAG = "AlertaPolitica";
    private String titulo, subtilo;
    public static Object a = null;

    private interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    @SuppressLint("ValidFragment")
    public FragmentDialogAceptaPolitica(Object a, String titulo, String subTitulo) {
        this.a = a;
        this.titulo = titulo;
        this.subtilo = subTitulo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.alertdiaolgaceptapolitica, container, false);

        btnAcepar = vista.findViewById(R.id.btnAceptarPolitica);
        btnCancelar = vista.findViewById(R.id.btnCancelarPolitica);
        tv_titulo = vista.findViewById(R.id.tv_titlo_alerta_politica);
        tv_subtitulo = vista.findViewById(R.id.tv_subtitlo_alerta_politica);
        tv_titulo.setText(titulo);
        tv_subtitulo.setText(subtilo);
        if(subtilo.equals("")){
            btnCancelar.setVisibility(View.GONE);
        }else{
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                    getDialog().dismiss();
                }
            });
        }
        btnAcepar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();
            }
        });
        return vista;
    }
}
