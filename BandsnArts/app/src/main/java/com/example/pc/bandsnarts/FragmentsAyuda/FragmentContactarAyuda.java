package com.example.pc.bandsnarts.FragmentsAyuda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.R;


public class FragmentContactarAyuda extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        VentanaInicialApp.id = R.id.FragmentContactarAyuda;
        return inflater.inflate(R.layout.fragment_fragment_contactar_ayuda, container, false);
    }


}
