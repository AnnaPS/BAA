package com.example.pc.bandsnarts.Fragment_Visitar_Perfil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentAnuncios;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Visitar_Anuncios extends Fragment {
    View vista;
    RecyclerView recyclerViewAnuncios;

    String pos;
    String tipo;

    public Visitar_Anuncios(String pos,String tipo) {
        this.pos = pos;
        this.tipo= tipo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_visitar_anuncios, null);

        recyclerViewAnuncios = vista.findViewById(R.id.recyclervisitaranuncios);
        recyclerViewAnuncios.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rcLayoutManager = layoutManager;
        recyclerViewAnuncios.setLayoutManager(rcLayoutManager);
        //Carga de datos en ArrayList BDBAA
        BDBAA.cargarVisitarAnuncios(tipo, recyclerViewAnuncios, getActivity(),1,pos);

        return vista;
    }
}
