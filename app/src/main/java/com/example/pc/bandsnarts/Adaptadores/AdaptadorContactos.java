package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Objetos.KeyChat;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Clase adaptadora para los mensajes
 */
public class AdaptadorContactos extends RecyclerView.Adapter<HolderContactos> {
    List<KeyChat> listaContactos;
    private Context contexto;

    public AdaptadorContactos(Context contexto, List<KeyChat> listaContactos) {
        this.contexto = contexto;
        this.listaContactos = listaContactos;
    }

    public void addContacto(KeyChat keyChat) {
        listaContactos.add(keyChat);
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
        final KeyChat item = (KeyChat) listaContactos.get(position);
        holder.KEYCHAT = item.getKey();
        holder.tipo = item.getTipo();
        System.out.println(Boolean.parseBoolean(item.getNotificaciones()) + "NOTIFICATION");
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals( holder.KEYCHAT.split("-")[0])) {
            if (Boolean.parseBoolean(item.getNotificaciones().split("-")[0])) {
                ((ImageView) holder.getImgNot()).setVisibility(View.VISIBLE);
            } else {
                ((ImageView) holder.getImgNot()).setVisibility(View.GONE);
            }
        }else{
            if (Boolean.parseBoolean(item.getNotificaciones().split("-")[1])) {
                ((ImageView) holder.getImgNot()).setVisibility(View.VISIBLE);
            } else {
                ((ImageView) holder.getImgNot()).setVisibility(View.GONE);
            }
        }
        BDBAA.accesoFotoNombrePerfilMensajes(0, position, holder.getFotoMensaje(), holder.getNombre(), contexto, item.getKey(), null);

    }


    @Override
    public int getItemCount() {
        return listaContactos.size();
    }
}
