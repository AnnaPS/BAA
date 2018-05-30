package com.example.pc.bandsnarts.Container;


import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.Adaptadores.AdaptadorContactos;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentVerMiPerfil;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.KeyChat;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.github.library.bubbleview.BubbleDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes y metodos staticos en toda la App


public class BandsnArts extends Application implements Runnable {
    public static final int CODIGO_DE_INICIO = 777;
    public static final int CODIGO_DE_DESLOGUEO = 21;
    public static final int CODIGO_DE_REGISTRO = 000;
    public static final int CODIGO_DE_REGISTRO_RED_SOCIAL = 333;
    public static final int CODIGO_DE_REDSOCIAL = 111;
    public static final int CODIGO_DE_CIERRE = 22;


    public static CharSequence[] localidades;
    public static boolean banderaLocalidad = true;
    public static int posProvincia, posLocalidad;


    //MULTIMEDIA
    public static MediaPlayer mediaPlayer;
    public static int totalTime;
    public static Thread hiloMusica;
    public static boolean paraHilo;
    public static SeekBar positionBar;
    public static TextView tiempoTranscurrido, tiempoRestante;

    //Keys para busquedas
    public static String KEYCHAT;
    public static String nomChat;
    public static String imgChat;
    public static ArrayList<String> UID_MUSICO = new ArrayList<>();
    public static ArrayList<String> UID_GRUPO = new ArrayList<>();
    public static ArrayList<KeyChat> alContactos = new ArrayList();
    public static ArrayList<String> alContactosAUX = new ArrayList();

    public static AdaptadorContactos adaptadorContactos;
    public  static RecyclerView rvContactos;
    // Variable control posicion Tab
    public static int posicionTab;
    //escucha
    public static ChildEventListener bdbaa;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);

            String tiempoTranscurridoMusica = createTimeLable(currentPosition);
            tiempoTranscurrido.setText(tiempoTranscurridoMusica);
            String tiempoRestanteMusica = createTimeLable(BandsnArts.totalTime - currentPosition);
            tiempoRestante.setText("- " + tiempoRestanteMusica);
        }
    };


    public String createTimeLable(int time) {
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
        while (BandsnArts.mediaPlayer != null && !BandsnArts.paraHilo) {
            try {
                Message message = new Message();
                message.what = BandsnArts.mediaPlayer.getCurrentPosition();
                handler.sendMessage(message);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d("RUN", "run: ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" + BandsnArts.mediaPlayer);
    }
    /////////////////
    // MULTIMEDIA //
    ////////////////


    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static int posicionSpinner(String[] lista, String sp) {
        try {
            for (int i = 0; i < lista.length; i++) {
                if (sp.equals(lista[i])) {
                    return i;
                }
            }
        } catch (NullPointerException ex) {
            return 0;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return 0;
        }
        return 0;
    }

    public static int posicionSpinner(CharSequence[] lista, String sp) {
        try {
            for (int i = 0; i < lista.length; i++) {

                if (sp.equalsIgnoreCase(lista[i].toString())) {
                    System.out.println(lista[i]);
                    return i;
                }
            }
        } catch (NullPointerException ex) {
            return 0;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return 0;
        }
        return 0;
    }


    public static void cargarLocalidadProvincia(View vista, Object o, Spinner spProvincia, Spinner spLocalidad) {
        if (o instanceof Grupo) {
            Grupo grupo = (Grupo) o;
            BandsnArts.posProvincia = posicionSpinner(vista.getResources().getStringArray(R.array.provincias), grupo.getProvincia());
            BandsnArts.posLocalidad = posicionSpinner(BandsnArts.localidades, grupo.getLocalidad());
        } else if (o instanceof Musico) {
            Musico musico = (Musico) o;
            BandsnArts.posProvincia = posicionSpinner(vista.getResources().getStringArray(R.array.provincias), musico.getProvincia());
            BandsnArts.posLocalidad = posicionSpinner(BandsnArts.localidades, musico.getLocalidad());
        } else if (o instanceof Anuncio) {
            Anuncio anuncio = (Anuncio) o;
            BandsnArts.posProvincia = posicionSpinner(vista.getResources().getStringArray(R.array.provincias), anuncio.getProvincia());
            BandsnArts.posLocalidad = posicionSpinner(BandsnArts.localidades, anuncio.getLocalidad());
        }
        spProvincia.setSelection(BandsnArts.posProvincia);
        // Localidad
        TypedArray arrayLocalidades = vista.getResources().obtainTypedArray(
                R.array.array_provincia_a_localidades);
        BandsnArts.localidades = arrayLocalidades.getTextArray(BandsnArts.posProvincia);
        arrayLocalidades.recycle();
        // Create an ArrayAdapter using the string array and a default
        // spinner layout
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(
                vista.getContext(), R.layout.spinner_item,
                BandsnArts.localidades);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spLocalidad.setAdapter(adapter1);
        System.out.println(BandsnArts.posLocalidad);
        spLocalidad.setSelection(BandsnArts.posLocalidad);
        BandsnArts.escuchas(vista.getContext(), spProvincia, spLocalidad);

    }

    public static void loadSpinnerProvincias(Spinner spProvincia) {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                VentanaInicialApp.a.getApplicationContext(), R.array.provincias, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spProvincia.setAdapter(adapter);

    }

    public static void escuchas(final Context contextc, Spinner spProvincia, final Spinner spLocalidad) {
        spProvincia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BandsnArts.posProvincia = position;
                // Retrieves an array
                TypedArray arrayLocalidades = contextc.getResources().obtainTypedArray(
                        R.array.array_provincia_a_localidades);
                BandsnArts.localidades = arrayLocalidades.getTextArray(BandsnArts.posProvincia);
                arrayLocalidades.recycle();
                // Create an ArrayAdapter using the string array and a default
                // spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        VentanaInicialApp.a.getApplicationContext(), R.layout.spinner_item,
                        BandsnArts.localidades);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_item);
                // Apply the adapter to the spinner
                spLocalidad.setAdapter(adapter);
                if (BandsnArts.posLocalidad < BandsnArts.localidades.length) {
                    spLocalidad.setSelection(BandsnArts.posLocalidad);
                    BandsnArts.banderaLocalidad = false;
                } else {

                    spLocalidad.setSelection(0);
                    BandsnArts.banderaLocalidad = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spLocalidad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BandsnArts.posLocalidad = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public static void ocultaTeclado(Activity act) {
        View view = act.getCurrentFocus();
        view.clearFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String quitarSaltos(String cadena) {
        // Para el reemplazo usamos un string vacío
        return cadena.replaceAll("\n", "").trim();
    }


    // Metodo para reescalado de la imagen de perfil
    public static Bitmap reescalarImagen(@NonNull Bitmap imagen, int w, int h, int rotation) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz
        Matrix matrix = new Matrix();

        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(rotation);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(imagen, 0, 0,
                width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return resizedBitmap;
    }

    public static boolean compruebaURL(String tipo, String url) {
        String pattern = null;
        switch (tipo) {
            case ("YouTube"):
                pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
                break;
            case ("InstaGram"):
                pattern = "((http|https)://)?(www[.])?instagram.com/.+";
                break;
            case ("FaceBook"):
                pattern = "((http|https)://)?(www[.])?facebook.com/.+";
                break;
        }

        if (!url.isEmpty() && url.matches(pattern)) {
            return true;
        }
        return false;

    }

    public static void lanzarURLNavegador(String URL) {
        try {
            Uri uri = Uri.parse(URL);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            VentanaInicialApp.a.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(VentanaInicialApp.a.getApplicationContext(), "INSERTE UNA URL", Toast.LENGTH_SHORT).show();
        }

    }

    public static void recuperarToken(final String tipo) {
        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull final Task<GetTokenResult> task) {
                final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
                bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            switch (tipo) {
                                case "musico":
                                    Musico mus = dataSnapshot.getValue(Musico.class);
                                    for (String token : mus.getToken()) {
                                        if (!task.getResult().getToken().equals(token)) {
                                            mus.getToken().add(token);
                                            bd.child(ds.getKey()).setValue(mus);
                                        }
                                    }
                                    break;
                                case "grupo":
                                    Grupo gru = dataSnapshot.getValue(Grupo.class);
                                    for (String token : gru.getToken()) {
                                        if (!task.getResult().getToken().equals(token)) {
                                            gru.getToken().add(token);
                                            bd.child(ds.getKey()).setValue(gru);
                                        }
                                    }
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
