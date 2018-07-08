package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.FragmentsAyuda.FragmentCambiarFoto;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentCancion;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentContactarAyuda;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentContactarConNosotros;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentFiltrar;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentModificarPerfil;
import com.example.pc.bandsnarts.FragmentsAyuda.FragmentPublicarAnuncio;
import com.example.pc.bandsnarts.R;


public class FragmentAyuda extends Fragment {

    View vista;
    CardView modificar, foto, cancion, anuncio, contactar, filtrar;
    TextView contactarConNosotros,privacidad,valorar;
    final String docPrivacidad="https://firebasestorage.googleapis.com/v0/b/bandsnarts-ad1cd.appspot.com/o/BNA%2FPoliticaPrivacidadBnA.pdf?alt=media&token=02275433-a559-46cc-bd94-fa149897ec3f";
    final String encuesta="https://goo.gl/forms/D8hrj1yq2ncadCut2 ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_ayuda, container, false);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        modificar = vista.findViewById(R.id.cardViewModificarPerfil);
        foto = vista.findViewById(R.id.cardViewCambiarFotoPerfil);
        cancion = vista.findViewById(R.id.cardViewSubirCancion);
        anuncio = vista.findViewById(R.id.cardViewPublicarAnuncio);
        contactar = vista.findViewById(R.id.cardViewContactarConUsuario);
        filtrar = vista.findViewById(R.id.cardViewFiltrar);
        contactarConNosotros = vista.findViewById(R.id.txtContactarConNosotros);
        privacidad=vista.findViewById(R.id.privacidad);
        valorar=vista.findViewById(R.id.valorarExperiencia);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentModificarPerfil()).commit();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentCambiarFoto()).commit();
            }
        });
        cancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentCancion()).commit();
            }
        });

        anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentPublicarAnuncio()).commit();
            }
        });
        contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentContactarAyuda()).commit();
            }
        });
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentFiltrar()).commit();
            }
        });
        contactarConNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentContactarConNosotros()).commit();
            }
        });
        privacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(docPrivacidad);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        valorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(encuesta);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return vista;
    }


}
