package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.R;

@SuppressLint("ValidFragment")
public class FragmentDialogDescartarCambios extends DialogFragment {
    Button btnAcepar, btnCancelar;
    TextView tv_titulo, tv_subtitulo;
    private static final String TAG = "AlertaDescartar";
    private String titulo, subtilo;
    public static Object a = null;

    private interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    @SuppressLint("ValidFragment")
    public FragmentDialogDescartarCambios(Object a, String titulo, String subTitulo) {
        this.a = a;
        this.titulo = titulo;
        this.subtilo = subTitulo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.alertdiaolgdescartarcambios, container, false);

        btnAcepar = vista.findViewById(R.id.btnAceptarVDescartar);
        btnCancelar = vista.findViewById(R.id.btnCancelarVDescartar);
        tv_titulo = vista.findViewById(R.id.tv_titlo_alerta_descartar);
        tv_subtitulo = vista.findViewById(R.id.tv_subtitlo_alerta_descartar);
        tv_titulo.setText(titulo);
        tv_subtitulo.setText(subtilo);
        if(subtilo.equals("")){
            btnCancelar.setVisibility(View.GONE);
        }else{
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                    getDialog().dismiss();
                }
            });
        }

        btnAcepar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a instanceof FragmentVerMiPerfil){
                    VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(0)).commit();
                    ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil");
                    FragmentVerMiPerfil a = (FragmentVerMiPerfil) FragmentDialogDescartarCambios.a;
                    a.ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(VentanaInicialApp.a).getString("tipo", ""));
                    a.mostrarComponentes();
                    a.botonCancelarEdicionPerfil();
                    FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                }

                getDialog().dismiss();
            }
        });

        return vista;
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach:ClassCastException: " + e.getMessage());
        }
    }*/
}
