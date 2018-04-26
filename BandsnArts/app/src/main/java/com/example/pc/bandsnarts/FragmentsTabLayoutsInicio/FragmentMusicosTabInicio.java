package com.example.pc.bandsnarts.FragmentsTabLayoutsInicio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE MUSICOS
 */
public class FragmentMusicosTabInicio extends Fragment {
    RecyclerView recyclerViewMusicos;
    View vista;
    ArrayList<Musico> listaMusicos = new ArrayList<>();

    public FragmentMusicosTabInicio() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.tab_musicos_fragment, container, false);
        recyclerViewMusicos = vista.findViewById(R.id.recyclerMusicos);
            new BDBAA().cargarDatos(listaMusicos, recyclerViewMusicos, getActivity(),"musico");
        return vista;
    }


}
