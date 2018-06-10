package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.R;


public class FragmentConfiguracion extends Fragment {
    private Button cambiarPass, dejarRecordarPass, eliminarCuenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_fragment_configuracion, container, false);

        eliminarCuenta=vista.findViewById(R.id.btnEliminarCuentaConfiguracion);




        return vista;
    }


}
