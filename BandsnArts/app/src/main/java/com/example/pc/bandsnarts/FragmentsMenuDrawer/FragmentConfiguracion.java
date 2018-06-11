package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentConfiguracion extends Fragment {
    private Button cambiarPass, dejarRecordarPass, eliminarCuenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_fragment_configuracion, container, false);

        eliminarCuenta = vista.findViewById(R.id.btnEliminarCuentaConfiguracion);

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(v.getContext());
                alerta.setTitle("Borrar cuenta");
                alerta.setMessage("¿Está seguro de querer borrar su cuenta?");
                alerta.setNegativeButton("CANCELAR", null);
                alerta.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       BDBAA.borrarPerfil(v.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid(),0);

                    }
                });
                alerta.show();
            }
        });
        return vista;
    }


}
