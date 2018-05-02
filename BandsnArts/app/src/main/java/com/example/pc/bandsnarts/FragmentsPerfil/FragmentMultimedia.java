package com.example.pc.bandsnarts.FragmentsPerfil;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.R;


public class FragmentMultimedia extends Fragment implements Runnable {

    View vista;
    Button reproducir;
    MediaPlayer mp;


    private Button playButton;
    private SeekBar positionBar, volumenBar;
    private TextView tiempoTranscurrido, tiempoRestante;
    private MediaPlayer mediaPlayer;
    private int totalTime;
    private Thread hiloMusica;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_multimedia_v_fragment_perfil, container, false);

        playButton = vista.findViewById(R.id.btnPlayVMultimedia);
        positionBar = vista.findViewById(R.id.progresoMusicaVMultimedia);
        volumenBar = vista.findViewById(R.id.volumenVMultimedia);
        tiempoRestante = vista.findViewById(R.id.tiempoRestanteVMultimedia);
        tiempoTranscurrido = vista.findViewById(R.id.tiempoTranscurridoVMultimedia);


        mediaPlayer = MediaPlayer.create(getActivity().getBaseContext(), R.raw.pushittothelimit);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f, 0.5f);
        totalTime = mediaPlayer.getDuration();

        //Toast.makeText(getActivity(), "Valor de media player: "+mediaPlayer, Toast.LENGTH_SHORT).show();

        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNumber = progress / 100f;
                mediaPlayer.setVolume(volumeNumber, volumeNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playButton.setBackgroundResource(R.drawable.stop);
                } else {
                    mediaPlayer.pause();
                    playButton.setBackgroundResource(R.drawable.play);
                }
            }
        });


        hiloMusica = new Thread(this);
        hiloMusica.start();

        return vista;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);

            String tiempoTranscurridoMusica = createTimeLable(currentPosition);
            tiempoTranscurrido.setText(tiempoTranscurridoMusica);
            String tiempoRestanteMusica = createTimeLable(totalTime - currentPosition);
            tiempoRestante.setText("- " + tiempoRestanteMusica);
        }
    };

    private String createTimeLable(int time) {
        String timeLable = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLable = min + ":";

        if (sec < 10)
            timeLable += 0;
        timeLable += sec;


        return timeLable;
    }


    @Override
    public void run() {
        while (mediaPlayer != null) {
            // Toast.makeText(getActivity(), "pasa por aqui", Toast.LENGTH_SHORT).show();
            try {
                Message message = new Message();
                message.what = mediaPlayer.getCurrentPosition();
                handler.sendMessage(message);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}