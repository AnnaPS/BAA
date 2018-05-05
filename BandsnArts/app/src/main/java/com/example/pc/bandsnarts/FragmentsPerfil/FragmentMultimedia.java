package com.example.pc.bandsnarts.FragmentsPerfil;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.bandsnarts.Container.ComprobadorConexion;
import com.example.pc.bandsnarts.R;


public class FragmentMultimedia extends Fragment {

    View vista;
    Button reproducir;
    MediaPlayer mp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista=inflater.inflate(R.layout.fragment_multimedia_v_fragment_perfil, container, false);

        reproducir=vista.findViewById(R.id.btnReproducir);

        mp=MediaPlayer.create(getActivity(),R.raw.musica);
        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "PULSADO", Toast.LENGTH_SHORT).show();
                if(mp.isPlaying()){

                    mp.pause();
                    reproducir.setBackgroundResource(R.drawable.pause);
                    //reproducir.setBackgroundColor(getResources().getColor(R.color.md_light_green_A700));
                }else{
                    mp.start();
                    reproducir.setBackgroundResource(R.drawable.play);
                    reproducir.setBackgroundColor(getResources().getColor(R.color.md_red_800));

                }

            }
        });

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!ComprobadorConexion.isConnected()){
            ComprobadorConexion.simpleSnackbar(vista.findViewById(R.id.multimedia));
        }
    }
}