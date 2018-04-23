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

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterGrupo;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE GRUPOS
 */
public class FragmentGruposTabInicio extends Fragment {
    RecyclerView recyclerViewGrupos;
    View vista;
    ArrayList<Grupo> listaGrupos=new ArrayList<>();

    public FragmentGruposTabInicio() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.tab_grupos_fragment, container, false);
        recyclerViewGrupos = vista.findViewById(R.id.recyclerGrupos);
            new BDBAA().cargarDatos(listaGrupos, recyclerViewGrupos, getActivity(),"grupo");
        return vista;
    }
}
