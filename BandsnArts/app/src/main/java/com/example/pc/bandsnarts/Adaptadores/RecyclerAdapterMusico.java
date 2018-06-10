package com.example.pc.bandsnarts.Adaptadores;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAñadirAnuncio;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;


import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.SQLOutput;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE MUSICOS
 */
public class RecyclerAdapterMusico extends RecyclerView.Adapter<RecyclerAdapterMusico.ViewHolder> {

    private Context mContext;
    private int mExpandedPosition = -1;
    private ArrayList<Musico> listaM;



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
    public void onBindViewHolder(final RecyclerAdapterMusico.ViewHolder holder, final int position) {

        final Musico musicoItem = (Musico) listaM.get(position);
        CircleImageView imagenMusico = holder.img;
        TextView nom = holder.nombre;
        TextView ins = holder.instrumento;
        TextView est = holder.estilo;
        TextView desc = holder.descripcion;
        TextView anun = holder.anuncios;
        ImageView busc = holder.buscando;

        BandsnArts.UID_MUSICO.add(musicoItem.getUid());


        System.out.println("------------->NOMBRE" + musicoItem.getNombre());
        System.out.println("*******************************************");
        System.out.println(BandsnArts.UID_MUSICO.size() + "------->" + BandsnArts.UID_MUSICO.get(position));
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
                                //op 0 lanza perfil
                                System.out.println(BandsnArts.UID_MUSICO.size() + "------->" + BandsnArts.UID_MUSICO.get(position));
                                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_MUSICO.get(position), "musico", 0)).commit();
                                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Visitar Perfil");
                                VentanaInicialApp.id = R.id.visitaperfil;
                                System.out.println("visitar perfil");
                                break;
                            case R.id.itemanunciosvisitado:
                                //op 1 lanza anuncio del perfil
                                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_MUSICO.get(position), "musico", 1)).commit();
                                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Visitar Perfil");
                                VentanaInicialApp.id = R.id.visitaperfil;
                                System.out.println("visitar anuncio");
                                break;
                            default:
                                System.out.println("Por si acaso.");
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(BandsnArts.UID_MUSICO.size() + "------->" + BandsnArts.UID_MUSICO.get(getAdapterPosition()));
                    VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_MUSICO.get(getAdapterPosition()), "musico", 0)).commit();
                    ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Visitar Perfil");
                    VentanaInicialApp.id = R.id.visitaperfil;
                    System.out.println("visitar perfil");
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
