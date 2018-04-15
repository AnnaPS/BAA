package com.example.pc.bandsnarts.Login;


import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes en toda la App




public class BandsnArts extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);



    }



}
