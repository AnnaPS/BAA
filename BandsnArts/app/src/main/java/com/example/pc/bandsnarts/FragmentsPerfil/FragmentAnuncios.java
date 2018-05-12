package com.example.pc.bandsnarts.FragmentsPerfil;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterAnuncioPropio;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class FragmentAnuncios extends Fragment {
    View vista;
    FloatingActionButton miFAB;
    RecyclerView recyclerViewAnuncios;
    ArrayList<Anuncio> lista;
    public static Fragment fragAnu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_anuncios_v_fragment_perfil, container, false);
        fragAnu = FragmentAnuncios.this;
        recyclerViewAnuncios = vista.findViewById(R.id.recycleranuncios);
        recyclerViewAnuncios.setNestedScrollingEnabled(false);
        lista = new ArrayList<>();
        //Carga de datos en ArrayList BDBAA

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rcLayoutManager = layoutManager;
        recyclerViewAnuncios.setLayoutManager(rcLayoutManager);

        miFAB = (FloatingActionButton) vista.findViewById(R.id.fabAñadirAnuncio);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BDBAA.comprobarNumAnuncios(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo",""),FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });
        BDBAA.cargarAnuncios(lista, recyclerViewAnuncios, getActivity(), FirebaseAuth.getInstance().getCurrentUser().getUid(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""));

        return vista;
    }
}