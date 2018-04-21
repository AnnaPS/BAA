package com.example.pc.bandsnarts.FragmentsTabLayoutsInicio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE LOCALES
 */
public class FragmentLocalesTabInicio extends Fragment{
    View vista;



    TextView nombreLocal,direcLocal,cpLocal,localidadLocal;
    public FragmentLocalesTabInicio(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.activity_inicio_google,container,false);
        nombreLocal = vista.findViewById(R.id.txtNombreVGoogle);
        direcLocal=vista.findViewById(R.id.txtDirecVGoogle);
        cpLocal = vista.findViewById(R.id.txtCPVGoogle);
        localidadLocal = vista.findViewById(R.id.txtLocVGoogle);


        return vista;
    }

    public void pintarDatosLocal(){

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("locales");


    }

}
