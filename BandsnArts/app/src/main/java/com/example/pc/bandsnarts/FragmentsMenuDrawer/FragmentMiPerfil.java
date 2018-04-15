package com.example.pc.bandsnarts.FragmentsMenuDrawer;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.FragmentsPerfil.FragmentAnuncios;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentMultimedia;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentVerMiPerfil;
import com.example.pc.bandsnarts.R;


public class FragmentMiPerfil extends Fragment {

    ///Objeto necesario para los botones de navegacion
    private BottomNavigationView bottomNavigationView;
    private TextView info;
    final Fragment verperfil=new FragmentVerMiPerfil();
    final Fragment anuncios=new FragmentAnuncios();
    final Fragment multi=new FragmentMultimedia();
    View vista;

    ////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_fragment_mi_perfil, container, false);
        //Se establece como principal el fragment de inicio
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedormiperfil,new FragmentVerMiPerfil()).commit();
        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ///parte de los botones de navegacion
        bottomNavigationView = (BottomNavigationView)vista.findViewById(R.id.bottomnav);
        //info = vista.findViewById(R.id.info);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragment = getFragmentManager();
                int id = item.getItemId();
                if (id == R.id.menuPerfil) {
                    FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedormiperfil,verperfil).commit();
                    Toast.makeText(getActivity(), "ver perfil", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menuAnuncios) {
                    FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedormiperfil,anuncios).commit();
                    Toast.makeText(getActivity(), "anuncios", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.menuMultimedia) {
                    FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedormiperfil,multi).commit();
                    Toast.makeText(getActivity(), "play", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }
}
