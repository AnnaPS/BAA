package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pc.bandsnarts.R;

/**
 * CLASE PARA ALERT DIALOG DE AÑADIR ANUNCIO
 */

@SuppressLint("ValidFragment")
public class FragmentDialogCambiarContraseña extends DialogFragment {
    private static final String TAG = "AlertaAnuncio";
    Button atras;
    EditText passAntigua, passNueva, passNuevaConfirmar;
    FloatingActionButton Fabguardar;
    View vista;


    public FragmentDialogCambiarContraseña() {

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.alertdialogcambiarpass, container, false);
        //finds
        passAntigua = vista.findViewById(R.id.edtCambiarPass);
        passNueva = vista.findViewById(R.id.edtContraseñaNuevaCambiarPass);
        passNuevaConfirmar=vista.findViewById(R.id.edtRepetirContraseñaCambiarPass);
        Fabguardar=vista.findViewById(R.id.fabGuardarCambiarContraseña);
        atras=vista.findViewById(R.id.btnAtrasCambiarPass);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        Fabguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GUARDAR EN BBDD
            }
        });


        return vista;
    }


}
