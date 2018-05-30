package com.example.pc.bandsnarts.Adaptadores;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pc.bandsnarts.R;
import com.github.library.bubbleview.BubbleLinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase contenedora de los componentes para los mensajes
 */
public class HolderMensajes extends RecyclerView.ViewHolder {

    private TextView nombre, mensaje, hora;
    private CircleImageView fotoMensaje;
    private String KEYCHAT;
    private com.github.library.bubbleview.BubbleLinearLayout bubbleLinearLayout;

    public HolderMensajes(View itemView) {
        super(itemView);
        KEYCHAT = "";
        nombre = itemView.findViewById(R.id.txtUsuarioMensajes);
        mensaje = itemView.findViewById(R.id.txtMensajeMensajes);
        hora = itemView.findViewById(R.id.txtHoraMensajes);
        fotoMensaje = itemView.findViewById(R.id.imgUsuarioMensajes);
        bubbleLinearLayout = itemView.findViewById(R.id.bubbleMensaje);
    }

    public BubbleLinearLayout getBubbleLinearLayout() {
        return bubbleLinearLayout;
    }

    public void setBubbleLinearLayout(BubbleLinearLayout bubbleLinearLayout) {
        this.bubbleLinearLayout = bubbleLinearLayout;
    }

    public String getKEYCHAT() {
        return KEYCHAT;
    }

    public void setKEYCHAT(String KEYCHAT) {
        this.KEYCHAT = KEYCHAT;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(CircleImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}
