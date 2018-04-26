package com.example.pc.bandsnarts.Container;


import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clase necesaria para conocer los logueos con FaceBook
// Reutilizar esta clase para Datos comunes en toda la App




public class BandsnArts extends Application{
    public static final int CODIGO_DE_INICIO = 777;
    public static final int CODIGO_DE_DESLOGUEO=21;
    public static final int CODIGO_DE_REGISTRO=000;
    public static final int CODIGO_DE_FACEBOOK=111;
    public static final int CODIGO_DE_CIERRE=22;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);



    }


    public static boolean isUrl(String s) {
        Pattern face = Pattern.compile("https://www\\.facebook\\.com\\/(.+)");
        Pattern twitter = Pattern.compile("https://www\\.twitter\\.com\\/(.+)");
        Pattern youtube=Pattern.compile("https://www\\.youtube\\.com\\/(.+)");


        Matcher f = face.matcher(s);
        Matcher t = twitter.matcher(s);
        Matcher y = youtube.matcher(s);

        if(f.matches()||t.matches()||y.matches()){

            System.out.println("FACEBOOK: "+f.matches());
            System.out.println("TWITTER: "+t.matches());
            System.out.println("YOUTUBE: "+y.matches());

            //SI LA URL ES UNA DE LAS ANTERIORES ES VÁLIDA, RETORNMOS VERDADERO
            return true;
        }else{
            //SI NO ES UNA URL VALIDA RETORNAMOS FALSO
            return false;
        }

    }


}
