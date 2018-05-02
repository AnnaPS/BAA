package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.bandsnarts.R;

@SuppressLint("ValidFragment")
public class FragmentDialogDescartarCambios extends DialogFragment {
    Button btnAcepar, btnCancelar;
    private static final String TAG = "AlertaDescartar";
    FragmentVerMiPerfil a;
    private interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    @SuppressLint("ValidFragment")
    public FragmentDialogDescartarCambios(FragmentVerMiPerfil a) {
        this.a=a;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.alertdiaolgdescartarcambios, container, false);

        btnAcepar = vista.findViewById(R.id.btnAceptarVDescartar);
        btnCancelar = vista.findViewById(R.id.btnCancelarVDescartar);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CANCELAR", Toast.LENGTH_SHORT).show();

                getDialog().dismiss();
            }
        });
        btnAcepar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ACEPTAR", Toast.LENGTH_SHORT).show();
                a.ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(a.getContext()).getString("tipo", "musico"));
                a.mostrarComponentes();
                a.botonCancelarEdicionPerfil();
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
