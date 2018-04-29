package com.example.pc.bandsnarts.FragmentsPerfil;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pc.bandsnarts.R;


public class FragmentAnuncios extends Fragment {
    View vista;
    FloatingActionButton miFAB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       vista= inflater.inflate(R.layout.fragment_anuncios_v_fragment_perfil, container, false);


        miFAB = (FloatingActionButton) vista.findViewById(R.id.fabAñadirAnuncio);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fm=getActivity().getFragmentManager();
                FragmentDialogAñadirAnuncio alerta = new FragmentDialogAñadirAnuncio();
                alerta.show(fm,"AlertaAnuncio");
            }
        });





       return vista;
    }
}