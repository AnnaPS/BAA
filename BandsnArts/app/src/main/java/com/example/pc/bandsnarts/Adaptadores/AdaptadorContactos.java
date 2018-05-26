package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.bandsnarts.Objetos.Contactos;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.R;

import java.util.List;

/**
 * Clase adaptadora para los mensajes
 */
public class AdaptadorContactos extends RecyclerView.Adapter<HolderContactos> {
    List<Contactos> listaContactos;
    private Context contexto;

    public AdaptadorContactos(Context contexto, List<Contactos> listaContactos) {
        this.contexto = contexto;
        this.listaContactos = listaContactos;
    }

    public void addContacto(Contactos contactos) {
        listaContactos.add(contactos);
        //envia una notificacion cuando se inserta un nuevo elemento
        notifyItemInserted(listaContactos.size());

    }

    @Override
    public HolderContactos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.item_contactos, parent, false);
        return new HolderContactos(vista);
    }

    @Override
    public void onBindViewHolder(HolderContactos holder, int position) {
        holder.getNombre().setText(listaContactos.get(position).getNombre());
       //HACER ON CLICK DEL CARDVIEW PARA LANZAR LA VENTANA DE CHAT DE ESA PERSONA

    }


    @Override
    public int getItemCount() {
        return listaContactos.size();
    }
}
