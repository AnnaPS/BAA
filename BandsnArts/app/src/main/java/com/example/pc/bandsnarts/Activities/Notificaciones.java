package com.example.pc.bandsnarts.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.pc.bandsnarts.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notificaciones extends FirebaseMessagingService {

    public static final String TAG="NOTICIAS";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from=remoteMessage.getFrom();
        Log.d(TAG,"Mensaje recibido de: "+from);

        if(remoteMessage.getNotification()!=null){
            Log.d(TAG,"NOTIFICACION: "+remoteMessage.getNotification().getBody());

        }
        //si hay datos en el mensaje los mostramos
        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Data: "+remoteMessage.getData());
        }

        mostrarNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }

    private void mostrarNotificacion(String title, String body) {

        //VENTANA QUE LANZAREMOS AL PULSAR SOBRE LA NOTIFICACION
        Intent i=new Intent(this,VentanaInicialApp.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //CUANDO SE PULSA SOBRE LA NOTIFICACION PASA POR AQUI
        PendingIntent ventanaPendiente=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        //SONIDO CUANDO LLEGA LA NOTIFICACION
        Uri sonidoNotificacion= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificacion=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sonidoNotificacion)
                .setContentIntent(ventanaPendiente);

        NotificationManager notifi= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifi.notify(0,notificacion.build());
    }
}
