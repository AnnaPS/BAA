package com.example.pc.bandsnarts.Adaptadores;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
                BandsnArts.KEYCHAT = KEYCHAT;
                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMensajes()).commit();
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle(nombre.getText().toString());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(v.getContext());
                alerta.setTitle("Borrar contacto");
                alerta.setMessage("¿Está seguro de querer borrar el contacto?");
                alerta.setNegativeButton("CANCELAR",null );
                alerta.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            BDBAA.eliminarContacto(v, BandsnArts.alContactos.get(BandsnArts.rvContactos.getChildLayoutPosition(v)));
                            BandsnArts.alContactos.remove(BandsnArts.alContactos.get(BandsnArts.rvContactos.getChildLayoutPosition(v)));
                            BandsnArts.rvContactos.setAdapter(BandsnArts.adaptadorContactos);
                        } catch (IndexOutOfBoundsException ex) {
                            BDBAA.eliminarContacto(v, BandsnArts.alContactos.get(BandsnArts.rvContactos.getChildLayoutPosition(v) - 1));
                            BandsnArts.alContactos.remove(BandsnArts.alContactos.get(BandsnArts.rvContactos.getChildLayoutPosition(v) - 1));
                            BandsnArts.rvContactos.setAdapter(BandsnArts.adaptadorContactos);
                        }
                    }
                });
               alerta.show();
                return true;
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
