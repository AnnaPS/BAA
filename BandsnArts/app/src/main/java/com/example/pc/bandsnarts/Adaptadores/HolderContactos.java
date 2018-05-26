package com.example.pc.bandsnarts.Adaptadores;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMensajes;
import com.example.pc.bandsnarts.Objetos.KeyChat;
import com.example.pc.bandsnarts.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase contenedora de los componentes para los contactos
 */
public class HolderContactos extends RecyclerView.ViewHolder {

    private TextView nombre;
    private CircleImageView fotoMensaje;
    String KEYCHAT;

    public HolderContactos(View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.txtUsuarioContactos);
        fotoMensaje = itemView.findViewById(R.id.imgUsuarioContactos);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BandsnArts.KEYCHAT= KEYCHAT;
                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMensajes()).commit();
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle(nombre.getText().toString());
            }
        });
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
