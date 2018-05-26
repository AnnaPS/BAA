package com.example.pc.bandsnarts.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.pc.bandsnarts.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase contenedora de los componentes para los contactos
 */
public class HolderContactos extends RecyclerView.ViewHolder {

    private TextView nombre;
    private CircleImageView fotoMensaje;
    private String KEYCHAT;


    public HolderContactos(View itemView) {
        super(itemView);
        KEYCHAT = "";
        nombre = itemView.findViewById(R.id.txtUsuarioContactos);
        fotoMensaje = itemView.findViewById(R.id.imgUsuarioContactos);

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

    public CircleImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(CircleImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}
