package com.example.pc.bandsnarts.Fragment_Visitar_Perfil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.R;

@SuppressLint("ValidFragment")
public class Visitar_Perfil extends Fragment {

    View vista;
    ImageView imagenPerfil;
    String tipo;
    String pos;
    public static String UIDvisited;
    CardView cvMultimedia;
    CardView cvRedesSociales;
    LinearLayout linearYoutube,linearFacebook,linearInstagram;
    SeekBar volumen;
    Button descargar;
    private boolean descargaCancion;
    ImageView imYoututbe,imFacebook,imInstagram;

    @SuppressLint("ValidFragment")
    public Visitar_Perfil(String pos, String tipo) {
        this.pos = pos;
        this.tipo = tipo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_visitar_perfil, container, false);
        imagenPerfil = vista.findViewById(R.id.imgPerfilVVisitarPerfil);
        TextView nombreUsuario = vista.findViewById(R.id.txtNombUsuarioVVisitarMiPerfil);
        TextView cajaEstilo = vista.findViewById(R.id.txtEstiloVVisitarPerfil);
        TextView cajaDescripcion = vista.findViewById(R.id.txtDescripcionVVisitarPerfil);
        TextView cajaProvincia = vista.findViewById(R.id.txtProvinciaVVisitarPerfil);
        TextView cajaLocalidad = vista.findViewById(R.id.txtLocalidadVVisitarPerfil);
        TextView tvSexo = vista.findViewById(R.id.tv_sexo);
        TextView cajaSexo = vista.findViewById(R.id.txtSexoVVisitarPerfil);
        TextView tvInstrumento = vista.findViewById(R.id.tv_instrumentos);
        View ll_inst1 = vista.findViewById(R.id.ll_inst1);
        TextView cajaInstru1 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil1);
        View ll_inst2 = vista.findViewById(R.id.ll_inst2);
        View ll_instsec1 = vista.findViewById(R.id.ll_instsec1);
        TextView cajaInstru2 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil2);
        View ll_instsec2 = vista.findViewById(R.id.ll_instsec2);
        TextView cajaInstru3 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil3);
        View ll_instsec3 = vista.findViewById(R.id.ll_instsec3);
        TextView cajaInstru4 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil4);
        TextView[] instrumnetos = {cajaInstru1, cajaInstru2, cajaInstru3, cajaInstru4};


        cvMultimedia = vista.findViewById(R.id.cvMultimediaVVisitarPerfil);
        cvRedesSociales = vista.findViewById(R.id.cvRedesSocialesVVisitarPerfil);

        linearFacebook = vista.findViewById(R.id.ll_facebookVVisitarPerfil);
        linearInstagram = vista.findViewById(R.id.ll_instagramVVisitarPerfil);
        linearYoutube = vista.findViewById(R.id.ll_youtubeVVisitarPerfil);

        BandsnArts.positionBar = vista.findViewById(R.id.progresoMusicaVVisitarPerfil);
        BandsnArts.tiempoRestante = vista.findViewById(R.id.tiempoRestanteVVisitarPerfil);
        BandsnArts.tiempoTranscurrido = vista.findViewById(R.id.tiempoTranscurridoVVisitarPerfil);

        volumen = vista.findViewById(R.id.volumenVVisitarPerfil);
        descargar = vista.findViewById(R.id.btnPlayVVisitarPerfil);

        imFacebook = vista.findViewById(R.id.im_facebook_VVisitarMiPerfil);
        imYoututbe = vista.findViewById(R.id.im_youtube_VVisitarMiPerfil);
        imInstagram = vista.findViewById(R.id.im_instagram_VVisitarMiPerfil);

        BDBAA.cargarDatosVisitarPerfil(pos, vista, tipo, imagenPerfil, nombreUsuario, cajaEstilo, cajaDescripcion, cajaProvincia, cajaLocalidad, tvSexo, tvInstrumento, cajaSexo, instrumnetos, ll_inst1, ll_inst2, ll_instsec1, ll_instsec2, ll_instsec3);

        BDBAA.cargarMultimediaVisitarPerfil(cvMultimedia, tipo, pos);

        BDBAA.cargarRedesSocialesViaitarPerfil(cvRedesSociales,tipo,pos,linearYoutube,linearFacebook,linearInstagram);

        BandsnArts.positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    BandsnArts.mediaPlayer.seekTo(progress);
                    BandsnArts.positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNumber = progress / 100f;
                if (BandsnArts.mediaPlayer != null) {
                    BandsnArts.mediaPlayer.setVolume(volumeNumber, volumeNumber);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!descargaCancion) {

                    System.out.println("UID:                                                                       " + UIDvisited);
                    BDBAA.comprobacionAudioUsuario(tipo, getContext(), UIDvisited,descargar);
                    descargaCancion = true;
                } else {
                    if (BandsnArts.mediaPlayer != null) {
                        if (!BandsnArts.mediaPlayer.isPlaying()) {
                            BandsnArts.mediaPlayer.start();
                            descargar.setBackgroundResource(R.drawable.stop);
                        } else {
                            BandsnArts.mediaPlayer.pause();
                            descargar.setBackgroundResource(R.drawable.play);
                        }
                    }
                }
            }
        });

        imYoututbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(tipo,0,1,null,null,null,UIDvisited);
            }
        });
        imFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(tipo,1,1,null,null,null,UIDvisited);
            }
        });
        imInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(tipo,2,1,null,null,null,UIDvisited);
            }
        });

        return vista;
    }
}
