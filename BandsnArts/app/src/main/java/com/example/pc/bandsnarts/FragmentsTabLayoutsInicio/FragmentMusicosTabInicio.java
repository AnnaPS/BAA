package com.example.pc.bandsnarts.FragmentsTabLayoutsInicio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.R;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE MUSICOS
 */
public class FragmentMusicosTabInicio extends Fragment{
    View vista;
    public FragmentMusicosTabInicio(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.tab_musicos_fragment,container,false);
        return vista;
    }
}
