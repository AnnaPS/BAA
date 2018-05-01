package com.example.pc.bandsnarts.FragmentsVisitarPerfil;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterAnuncioPropio;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

public class FragmentVisitarPerfil_Anuncios extends android.support.v4.app.Fragment {
    View vista;
    RecyclerView recyclerViewAnuncios;
    ArrayList<Anuncio> lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.activity_fragment_visitar_perfil__anuncios, container, false);

        recyclerViewAnuncios = vista.findViewById(R.id.recycleranunciosvisitarperfil);
        recyclerViewAnuncios.setNestedScrollingEnabled(false);
        lista=new ArrayList<>();
        lista.add(new Anuncio("NECESITAMOS BAJISTA ","Hola, somos un grupo que necesita incorporar a un bajista","25/05/2018","Asturias","Comillas","Pop/Rock","Bajista","Mujer"));
        lista.add(new Anuncio("NECESITAMOS BAJISTA ","Hola, somos un grupo que necesita incorporar a un bajista","25/05/2018","Asturias","Comillas","Pop/Rock","Bajista","Mujer"));
        lista.add(new Anuncio("NECESITAMOS BAJISTA ","Hola, somos un grupo que necesita incorporar a un bajista","25/05/2018","Asturias","Comillas","Pop/Rock","Bajista","Mujer"));
        lista.add(new Anuncio("NECESITAMOS BAJISTA ","Hola, somos un grupo que necesita incorporar a un bajista","25/05/2018","Asturias","Comillas","Pop/Rock","Bajista","Mujer"));
        lista.add(new Anuncio("NECESITAMOS BAJISTA ","Hola, somos un grupo que necesita incorporar a un bajista","25/05/2018","Asturias","Comillas","Pop/Rock","Bajista","Mujer"));

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rcLayoutManager =layoutManager;
        recyclerViewAnuncios.setLayoutManager(rcLayoutManager);
        RecyclerAdapterAnuncioPropio adapterAnuncio=new RecyclerAdapterAnuncioPropio(getActivity(),lista);
        recyclerViewAnuncios.setAdapter(adapterAnuncio);



        return vista;
    }
}
