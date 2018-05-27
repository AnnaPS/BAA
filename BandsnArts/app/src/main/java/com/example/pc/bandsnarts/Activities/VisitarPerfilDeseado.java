package com.example.pc.bandsnarts.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.Visitar_Anuncios;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.Visitar_Perfil;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("ValidFragment")
public class VisitarPerfilDeseado extends Fragment
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    String pos;
    String tipo;
    int op;
    public static Fragment fragment;
    public static Activity a;
    View vista;

    public VisitarPerfilDeseado() {
    }

    public VisitarPerfilDeseado(String pos, String tipo, int op) {
        this.pos = pos;
        this.op=op;
        this.tipo = tipo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.activity_actvity_visitar_perfil, container, false);
        fragment = this;
        a = this.getActivity();

        final Activity activity = getActivity();

        FloatingActionButton fbChat = vista.findViewById(R.id.fabEnviarMensaje);
        fbChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MCHAT");
                BDBAA.comprobarConversacionExistente(tipo, FirebaseAuth.getInstance().getCurrentUser().getUid(), pos, activity);
            }
        });
        BottomNavigationView navigation = vista.findViewById(R.id.navVisitarPerfil);
        navigation.setOnNavigationItemSelectedListener(this);
        //carga de inicio visitar perfil
        switch (op) {
            case 0:
                cargarFragment(new Visitar_Perfil(pos,tipo));
                break;
            case 1:
                cargarFragment(new Visitar_Anuncios(pos, tipo));
                break;
            default:
                break;
        }
        ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil Visitado");
        return vista;
    }

    private boolean cargarFragment(Fragment fragment) {
        if (fragment != null) {
            VentanaInicialApp.fragment
                    .beginTransaction()
                    .replace(R.id.contenedor_visitar_perfil, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    //Este metodo se llama al tocar en el menu y cambiar de fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment frg = null;
        //saber que opcion esta seleccionada
        switch (item.getItemId()) {
            case R.id.itemperfilvisitado:
                frg = new Visitar_Perfil(pos,tipo);
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil Visitado");
                break;
            case R.id.itemanunciosvisitado:
                frg = new Visitar_Anuncios(pos, tipo);
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil Visitado");
                BandsnArts.paraHilo = true;
                if (BandsnArts.mediaPlayer != null)
                    BandsnArts.mediaPlayer.stop();
                break;
        }

        return cargarFragment(frg);
    }
}
