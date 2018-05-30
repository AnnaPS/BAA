package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora para los mensajes
 */
public class AdaptadorMensajes extends RecyclerView.Adapter<HolderMensajes> {
    List<Mensajes2> listaMensajes;
    private Context contexto;

    public AdaptadorMensajes(Context contexto, List<Mensajes2> listaMensajes) {
        this.contexto = contexto;
        this.listaMensajes = listaMensajes;
    }

    public void addMensaje(Mensajes2 m) {
        listaMensajes.add(m);
        //envia una notificacion cuando se inserta un nuevo elemento
        notifyItemInserted(listaMensajes.size());
    }

    @Override
    public HolderMensajes onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.card_view_mensajes, parent, false);
        return new HolderMensajes(vista);
    }

    @Override
    public void onBindViewHolder(HolderMensajes holder, int position) {
        holder.getHora().setText(listaMensajes.get(position).getHora());
        holder.getMensaje().setText(listaMensajes.get(position).getMensaje());
        BDBAA.accesoFotoNombrePerfilMensajes(1,position,holder.getFotoMensaje(), holder.getNombre(), VentanaInicialApp.a,BandsnArts.KEYCHAT,holder.getBubbleLinearLayout());
    }


    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }
}
