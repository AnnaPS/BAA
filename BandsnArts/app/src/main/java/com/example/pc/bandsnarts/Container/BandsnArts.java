package com.example.pc.bandsnarts.Container;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes y metodos staticos en toda la App


public class BandsnArts extends Application{
    public static final int CODIGO_DE_INICIO = 777;
    public static final int CODIGO_DE_DESLOGUEO=21;
    public static final int CODIGO_DE_REGISTRO=000;
    public static final int CODIGO_DE_REGISTRO_RED_SOCIAL=333;
    public static final int CODIGO_DE_REDSOCIAL=111;
    public static final int CODIGO_DE_CIERRE=22;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static void ocultaTeclado(Activity act){
        View view = act.getCurrentFocus();
        view.clearFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    // Metodo para reescalado de la imagen de perfil
    public static Bitmap reescalarImagen(@NonNull Bitmap imagen, int w, int h,int rotation) {
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

}
