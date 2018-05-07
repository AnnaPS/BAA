package com.example.pc.bandsnarts.FragmentsPerfil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


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

    // Boton añadir audio
    private Button subirAudio;
    // Referencia al storage para la subida del audio
    private StorageReference storageReference;

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

        subirAudio = vista.findViewById(R.id.btnAnadirVMultimedia);
        storageReference = FirebaseStorage.getInstance().getReference();

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

        subirAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirAudio();
            }
        });

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


    private void subirAudio() {
        StorageReference filepath = storageReference.child("Audio");


        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Choose Sound File"), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            Uri path1 = data.getData();
            File file = new File(path1.getLastPathSegment());

            mediaPlayer = MediaPlayer.create(getActivity().getBaseContext(), path1);

            // Guardar audio
            // Nos posicionamos en el nodo de imagenes del storage
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            StorageReference referenceAudio = storage.child("audios/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".mp3");
            final UploadTask uploadTask = referenceAudio.putFile(path1);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    System.out.println("" + taskSnapshot.getMetadata().getSizeBytes());
                    System.out.println("" + taskSnapshot.getMetadata().getName());

                    // GENERAR METODO PARA GUARDAR EN LA BASE DE DATOS LA REFERENCIA !!!
                    //
                    //
                    // *****************************************************************

                    // METODO PARA GUARDAR EL EL STORAGE LA FOTO DE PERFIL
                    new BDBAA().actualizarCancionPerfil(taskSnapshot.getMetadata().getName(), PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));
                    Toast.makeText(vista.getContext(), "Referencia audio guardada en la BD", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    if (taskSnapshot.getTotalByteCount() > 5000000) {
                        uploadTask.cancel();
                        Toast.makeText(vista.getContext(), "SUPERIOR A 5 MEGAS", Toast.LENGTH_SHORT).show();
                        System.out.println("SUPERIOR A 5 MEGAS");
                    }


                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });


            for (int i = 0; i < mediaPlayer.getTrackInfo().length; i++) {
                System.out.println("" + mediaPlayer.getTrackInfo()[i]);

            }
            Toast.makeText(vista.getContext(), "" + path1, Toast.LENGTH_SHORT).show();
            Log.d("PRUEBAS", "path:                 " + file.getPath());
        }
    }
}