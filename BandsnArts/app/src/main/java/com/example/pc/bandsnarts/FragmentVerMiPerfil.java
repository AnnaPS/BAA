package com.example.pc.bandsnarts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentVerMiPerfil extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_verperfil_v_fragment_perfil, container, false);
        FloatingActionButton miFAB=(FloatingActionButton)vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }


}