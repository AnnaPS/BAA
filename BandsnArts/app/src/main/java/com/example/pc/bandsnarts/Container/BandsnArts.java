package com.example.pc.bandsnarts.Container;


import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes en toda la App




public class BandsnArts extends Application{
    public static final int CODIGO_DE_INICIO = 777;
    public static final int CODIGO_DE_DESLOGUEO=21;
    public static final int CODIGO_DE_REGISTRO=000;
    public static final int CODIGO_DE_REDSOCIAL=111;
    public static final int CODIGO_DE_CIERRE=22;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);



    }



}
