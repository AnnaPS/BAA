package com.example.pc.bandsnarts.Container;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes y metodos staticos en toda la App


public class BandsnArts extends Application {
    public static final int CODIGO_DE_INICIO = 777;
    public static final int CODIGO_DE_DESLOGUEO = 21;
    public static final int CODIGO_DE_REGISTRO = 000;
    public static final int CODIGO_DE_REGISTRO_RED_SOCIAL = 333;
    public static final int CODIGO_DE_REDSOCIAL = 111;
    public static final int CODIGO_DE_CIERRE = 22;
    private static BandsnArts mInstance;

    public static CharSequence[] localidades;
    public static boolean banderaLocalidad = true;
    public static int posProvincia, posLocalidad;

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


    public static void cargarLocalidadProvincia(View vista, Object o,Spinner spProvincia,Spinner spLocalidad) {
        if (o instanceof Grupo) {
            Grupo grupo = (Grupo) o;
            // Provincia
            BandsnArts.posProvincia = posicionSpinner(vista.getResources().getStringArray(R.array.provincias), grupo.getProvincia());
            BandsnArts.posLocalidad = posicionSpinner(BandsnArts.localidades, grupo.getLocalidad());
        }else{
            Musico musico=(Musico) o;
            BandsnArts.posProvincia = posicionSpinner(vista.getResources().getStringArray(R.array.provincias), musico.getProvincia());
            BandsnArts.posLocalidad = posicionSpinner(BandsnArts.localidades, musico.getLocalidad());
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
        BandsnArts.escuchas(vista.getContext(), spProvincia,spLocalidad);
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
                if (BandsnArts.banderaLocalidad) {
                    spLocalidad.setSelection(BandsnArts.posLocalidad);
                    BandsnArts.banderaLocalidad = false;
                } else {
                    BandsnArts.posLocalidad = 0;
                    spLocalidad.setSelection(BandsnArts.posLocalidad);
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

    public static synchronized BandsnArts getInstance() {
        return mInstance;
    }
}
