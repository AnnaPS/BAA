package com.example.pc.bandsnarts.Container;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes en toda la App




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


}
