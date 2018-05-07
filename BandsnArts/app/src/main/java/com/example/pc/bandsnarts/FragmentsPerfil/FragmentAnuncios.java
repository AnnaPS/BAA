package com.example.pc.bandsnarts.FragmentsPerfil;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterAnuncioPropio;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;


public class FragmentAnuncios extends Fragment {
    View vista;
    FloatingActionButton miFAB;
    RecyclerView recyclerViewAnuncios;
    Anuncio anuncio;
    ArrayList<Anuncio> lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_anuncios_v_fragment_perfil, container, false);

        recyclerViewAnuncios = vista.findViewById(R.id.recycleranuncios);
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


        miFAB = (FloatingActionButton) vista.findViewById(R.id.fabAñadirAnuncio);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                FragmentDialogAñadirAnuncio alerta = new FragmentDialogAñadirAnuncio();
                alerta.show(fm, "AlertaAnuncio");
            }
        });


        return vista;
    }


}