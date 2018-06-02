package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static android.app.DialogFragment.STYLE_NO_TITLE;
import static android.text.InputType.TYPE_NULL;


@SuppressLint("ValidFragment")
public class FragmentMultimedia extends Fragment {

    View vista;
    private Button playButton;
    private SeekBar volumenBar;


    public static Fragment fragment;

    private Button btnYoutube, btnFacebook, btnInstagram;
    // Boton añadir audio
    private Button subirAudio;
    // Referencia al storage para la subida del audio
    private StorageReference storageReference;

    public static ImageView progressBar;
    public static AnimationDrawable animationDrawable;
    private View scrollMedia;
    private View layout;
    private ImageView ivFacebook;
    private ImageView ivInstagram;
    private ImageView ivYoutube;
    private EditText edFacebook;
    private EditText edInstagram;
    private EditText edYoutube;

    private Button eliminarCancion;

    // Variable de control para la carga del Fragmento
    int num;



    @SuppressLint("ValidFragment")
    public FragmentMultimedia(int num) {
        this.num = num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_multimedia_v_fragment_perfil, container, false);

        playButton = vista.findViewById(R.id.btnPlayVMultimedia);
        BandsnArts.positionBar = vista.findViewById(R.id.progresoMusicaVMultimedia);
        volumenBar = vista.findViewById(R.id.volumenVMultimedia);
        BandsnArts.tiempoRestante = vista.findViewById(R.id.tiempoRestanteVMultimedia);
        BandsnArts.tiempoTranscurrido = vista.findViewById(R.id.tiempoTranscurridoVMultimedia);
        scrollMedia = vista.findViewById(R.id.svMedia);
        layout = vista.findViewById(R.id.multimedia);
        fragment = this;
        progressBar = vista.findViewById(R.id.progressBarVMedia);

        btnFacebook = vista.findViewById(R.id.btnEditarFacebookVMultimedia);
        btnInstagram = vista.findViewById(R.id.btnEditarInstagramVMultimedia);
        btnYoutube = vista.findViewById(R.id.btnEditarYoutubeVMultimedia);

        ivYoutube = vista.findViewById(R.id.ivYoutubeVMultimedia);
        ivFacebook = vista.findViewById(R.id.ivFacebookVMultimedia);
        ivInstagram = vista.findViewById(R.id.ivInstagramVMultimedia);

        edFacebook = vista.findViewById(R.id.edtFacebookVMultimedia);
        edInstagram = vista.findViewById(R.id.edtInstagramVMultimedia);
        edYoutube = vista.findViewById(R.id.edtYoutubeVMultimedia);

        eliminarCancion = vista.findViewById(R.id.btnEliminarVMultimedia);


        // COMPROBAR SI EL USUARIO TIENE AUDIO
        if (num != 1) {
            BDBAA.comprobacionAudioUsuario(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""), vista.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid(), subirAudio);
        } else {
            progressBar.setBackgroundResource(R.drawable.gif);
            animationDrawable = (AnimationDrawable) progressBar.getBackground();
            animationDrawable.start();
            esconderVistas();
        }
        subirAudio = vista.findViewById(R.id.btnAnadirVMultimedia);
        storageReference = FirebaseStorage.getInstance().getReference();

        //Toast.makeText(getActivity(), "Valor de media player: "+mediaPlayer, Toast.LENGTH_SHORT).show();


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

        volumenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BandsnArts.mediaPlayer != null) {
                    if (!BandsnArts.mediaPlayer.isPlaying()) {
                        BandsnArts.mediaPlayer.start();
                        playButton.setBackgroundResource(R.drawable.stop);
                    } else {
                        BandsnArts.mediaPlayer.pause();
                        playButton.setBackgroundResource(R.drawable.play);
                    }
                } else {
                    // PENDIENTE DEFINICION QUE HACER CUANDO EL USUARIO NO TIENE CANCION
                    Toast.makeText(vista.getContext(), "SUBE TU CANCION!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        subirAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultamos los tabs inferiores pasandole un 1 a la carga del fragmento
                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMultimedia(1)).commit();
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");
               // SELECCIONAR EL ButtonNavigationView MULTIMEDIA
                //////////////////////////////////////
                //////////////////////////////////////
                subirAudio();
            }
        });

        // Redes Sociales
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregaURLSocial("YouTube", "Agrega tu link de YouTube", edYoutube, " VISITA TU YOUTUBE");
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregaURLSocial("FaceBook", "Agrega tu link de FaceBook", edFacebook, " VISITA TU FACEBOOK");

            }
        });
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregaURLSocial("InstaGram", "Agrega tu link de Instagram", edInstagram, " VISITA TU INSTAGRAM");

            }
        });

        // Iconos de redes Sociales

        ivYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Traerse el tipo
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 0, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        });
        edYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Traerse el tipo
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 0, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        });
        edYoutube.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 0, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());

                return false;
            }
        });
        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 1, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });
        ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 2, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });
        edFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), 0, 1, null, null, null, FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        });

        BDBAA.recuperarURLredSocial(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""), -1, 0, edFacebook, edYoutube, edInstagram, FirebaseAuth.getInstance().getCurrentUser().getUid());


        eliminarCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(BandsnArts.mediaPlayer != null){
                    // LLAMAR AL ALERTDIALOG PARA EL BORRADO DE LA CANCION
                    android.app.FragmentManager fm = getActivity().getFragmentManager();
                    FragmentDialogDescartarCancion alerta = new FragmentDialogDescartarCancion(FragmentMultimedia.this, "¿ESTÁS SEGURO DE ELIMINAR EL AUDIO?", "La canción se perderá");
                    alerta.setCancelable(false);
                    //miFABGuardarRechazar.close(true);
                    alerta.show(fm, "AlertaDescartar");
                    /////////////////////////////////////////////////////
                }else{
                    Toast.makeText(getContext(), "No hay audio para eliminar", Toast.LENGTH_SHORT).show();
                }


/*                Toast.makeText(getContext(), "PRUEBAS", Toast.LENGTH_SHORT).show();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference cancion = storageRef.child("audios/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".mpeg");

                // Delete the file
                cancion.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        System.out.println("BORRADO CON EXITO!!!!!!!!!!!!");
                       final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", ""));

                        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    switch (PreferenceManager.getDefaultSharedPreferences(getContext()).getString("tipo", "")) {
                                        case "musico":
                                            Musico mus = data.getValue(Musico.class);
                                            mus.setAudio(null);
                                            bd.child(data.getKey()).setValue(mus);
                                            break;
                                        case "grupo":
                                            Grupo gr = data.getValue(Grupo.class);
                                            gr.setAudio(null);
                                            bd.child(data.getKey()).setValue(gr);
                                            break;
                                    }


                                }
                                BandsnArts.mediaPlayer = null;
                                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedormiperfil, new FragmentMultimedia(0)).commit();
                                ((AppCompatActivity)VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });*/
            }
        });

        return vista;
    }


    public static void agregaURLSocial(final String tipo, String mensaje, final EditText editText, final String mens) {
        LayoutInflater inflador = VentanaInicialApp.a.getLayoutInflater();
        final View vistainflada = inflador.inflate(R.layout.alertdialogredes, null);
        final EditText cajaredes = vistainflada.findViewById(R.id.edtRedesAlertRedes);
        Toast.makeText(VentanaInicialApp.a, tipo, Toast.LENGTH_SHORT).show();
        final AlertDialog ad = new AlertDialog.Builder(VentanaInicialApp.a).create();
        ad.setView(vistainflada);
        ad.setCancelable(false);
        ad.setTitle(tipo);
        ad.setMessage(mensaje);
        ad.setButton(Dialog.BUTTON_NEGATIVE, "CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.setButton(Dialog.BUTTON_POSITIVE, "ACEPTAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (BandsnArts.compruebaURL(tipo, cajaredes.getText().toString())) {
                    // guardar la URL en BD
                    BDBAA.guardarURL(PreferenceManager.getDefaultSharedPreferences(VentanaInicialApp.a.getApplicationContext()).getString("tipo", ""), tipo, cajaredes.getText().toString());
                    editText.setText(mens);
                    dialog.dismiss();

                } else {
                    Toast.makeText(VentanaInicialApp.a.getApplicationContext(), "NO VALIDA", Toast.LENGTH_SHORT).show();
                    cajaredes.setError("URL no válida");
                    ad.show();
                }
            }
        });


        ad.show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void subirAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Choose Sound File"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1) {
                Uri path1 = data.getData();

                if (BandsnArts.mediaPlayer != null && BandsnArts.mediaPlayer.isPlaying()) {
                    BandsnArts.mediaPlayer.stop();
                }
                BandsnArts.mediaPlayer = MediaPlayer.create(getActivity().getBaseContext(), path1);

                // Guardar audio
                // Nos posicionamos en el nodo de imagenes del storage
                StorageReference storage = FirebaseStorage.getInstance().getReference();
                StorageReference referenceAudio = storage.child("audios/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".mpeg");
                final UploadTask uploadTask = referenceAudio.putFile(path1);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        System.out.println("" + taskSnapshot.getMetadata().getSizeBytes());
                        System.out.println("" + taskSnapshot.getMetadata().getName());

                        BandsnArts.paraHilo = true;

                        // Guardamos la referencia del audio asociada al usuario en la BD
                        BDBAA.actualizarCancionPerfil(taskSnapshot.getMetadata().getName(), PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"), playButton);
                        Toast.makeText(vista.getContext(), "Referencia audio guardada en la BD", Toast.LENGTH_SHORT).show();

                        animationDrawable.stop();


                        android.app.FragmentManager fm = VentanaInicialApp.a.getFragmentManager();
                        FragmentDialogDescartarCambios alerta = new FragmentDialogDescartarCambios(this, "Se han guardado los cambios con exito", "");
                        alerta.setCancelable(false);
                        VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(1)).commit();

                        ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");

                        alerta.show(fm, "AlertaDescartar");


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getTotalByteCount() > 5000000) {
                            uploadTask.cancel();
                            VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(1)).commit();
                            ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");
                        }

                    }
                }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(vista.getContext(), "DESCARGA PAUSADA", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(vista.getContext(), "SUPERIOR A 5 MEGAS", Toast.LENGTH_SHORT).show();
                    }
                });

                for (int i = 0; i < BandsnArts.mediaPlayer.getTrackInfo().length; i++) {
                    System.out.println("" + BandsnArts.mediaPlayer.getTrackInfo()[i]);
                }
            }
        } else {
            VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(1)).commit();
            ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");
        }
    }

    private void esconderVistas() {
        progressBar.setVisibility(View.VISIBLE);
        scrollMedia.setVisibility(View.GONE);
        layout.setBackground(VentanaInicialApp.a.getDrawable(R.drawable.fondonegro));
    }

}