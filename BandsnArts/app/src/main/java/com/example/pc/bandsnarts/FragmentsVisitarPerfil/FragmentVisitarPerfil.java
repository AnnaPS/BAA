package com.example.pc.bandsnarts.FragmentsVisitarPerfil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.pc.bandsnarts.R;

public class FragmentVisitarPerfil extends Fragment {

    ///Objeto necesario para los botones de navegacion
    private BottomNavigationView bottomNavigationView;
    final Fragment visitarperfil=new FragmentVisitarPerfil_Perfil();
    final Fragment visitaranuncios=new FragmentVisitarPerfil_Anuncios();

    View vista;

    ////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.activity_fragment_visitar_perfil, container, false);
        //Se establece como principal el fragment de inicio
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedormivisitarperfil,new FragmentVisitarPerfil_Perfil()).commit();
        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ///parte de los botones de navegacion
        bottomNavigationView = (BottomNavigationView)vista.findViewById(R.id.btnNavigationVisitarPerfil);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragment = getFragmentManager();
                int id = item.getItemId();
                if (id == R.id.itemVisitarPerfil) {
                    FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedormivisitarperfil,visitarperfil).commit();
                    Toast.makeText(getActivity(), "ver perfil", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.itemVisitarAnuncios) {
                    FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedormivisitarperfil,visitaranuncios).commit();
                    Toast.makeText(getActivity(), "anuncios", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });


    }

}
