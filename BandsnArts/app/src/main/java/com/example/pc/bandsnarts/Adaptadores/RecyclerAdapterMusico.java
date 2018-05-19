package com.example.pc.bandsnarts.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.Activities.VisitarPerfilDeseado;
import com.example.pc.bandsnarts.BBDD.BDBAA;

import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAñadirAnuncio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;


import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE MUSICOS
 */
public class RecyclerAdapterMusico extends RecyclerView.Adapter<RecyclerAdapterMusico.ViewHolder> {

    private Context mContext;
    private ArrayList<Musico> listaM;
    FragmentMusicosTabInicio a;

    public RecyclerAdapterMusico(Context context, ArrayList<Musico> listaMusicos) {
        mContext = context;
        listaM = listaMusicos;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musicos, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterMusico.ViewHolder holder, int position) {

        final Musico musicoItem = (Musico) listaM.get(position);
        CircleImageView imagenMusico = holder.img;
        TextView nom = holder.nombre;
        TextView ins = holder.instrumento;
        TextView est = holder.estilo;
        TextView desc = holder.descripcion;
        TextView anun = holder.anuncios;
        ImageView busc = holder.buscando;


        nom.setText(musicoItem.getNombre());
        ins.setText(musicoItem.getInstrumento().get(0));
        est.setText(musicoItem.getEstilo());
        desc.setText(musicoItem.getDescripcion());
        anun.setText(String.valueOf(musicoItem.getAnuncio().size()));
        ImageButton btnMenu = holder.menuButton;

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creacion del menu para el cardview
                PopupMenu popupMenu = new PopupMenu(mContext, holder.menuButton);
                popupMenu.inflate(R.menu.visitar_perfil);
                //Listener del menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        Intent i;
                        switch (menuItem.getItemId()) {
                            case R.id.itemperfilvisitado:
                               i = new Intent(mContext,VisitarPerfilDeseado.class);
                                Toast.makeText(mContext, "visitar perfil", Toast.LENGTH_SHORT).show();
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                break;

                            case R.id.itemanunciosvisitado:
                                i = new Intent(mContext,VisitarPerfilDeseado.class);
                                Toast.makeText(mContext, "visitar anuncio", Toast.LENGTH_SHORT).show();
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                break;
                            default:
                                break;
                        }

                        return false;
                    }
                });
                //mostrar menu
                popupMenu.show();

            }
        });

        try {
            if (musicoItem.getBuscando().equalsIgnoreCase("si")) {
                busc.setImageDrawable(mContext.getDrawable(R.drawable.yes));
            } else {
                busc.setImageDrawable(mContext.getDrawable(R.drawable.no));
            }
        } catch (NullPointerException ex) {
            System.out.println("Sale por aqui en caso de que venga del primer registro");
        }
         BDBAA.accesoFotoPerfilRecycler(imagenMusico, mContext, listaM.get(position));
    }


    @Override
    public int getItemCount() {
        return listaM.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView buscando;
        TextView nombre, instrumento, estilo, descripcion, anuncios;
        ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            //finds de los componentes de los items
            img = itemView.findViewById(R.id.imgItemMusico);
            buscando = itemView.findViewById(R.id.imgBuscandoItemMusico);
            nombre = itemView.findViewById(R.id.txtNombreMusicoItemMusico);
            instrumento = itemView.findViewById(R.id.txtInstrumentoItemMusico);
            estilo = itemView.findViewById(R.id.txtEstiloItemMusico);
            descripcion = itemView.findViewById(R.id.txtDescripcionItemMusico);
            anuncios = itemView.findViewById(R.id.txtCantidadAnunciosItemMusico);
            menuButton = itemView.findViewById(R.id.btnMenuMusicos);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
