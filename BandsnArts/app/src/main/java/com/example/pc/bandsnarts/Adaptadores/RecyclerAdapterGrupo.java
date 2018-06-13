package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
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

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.Activities.VisitarPerfilDeseado;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE GRUPOS
 */
public class RecyclerAdapterGrupo extends RecyclerView.Adapter<RecyclerAdapterGrupo.ViewHolder> {

    private Context mContext;
    private ArrayList<Grupo> listaG;

    public RecyclerAdapterGrupo(Context context, ArrayList<Grupo> listaGrupo) {
        mContext = context;
        listaG = listaGrupo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupos, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterGrupo.ViewHolder holder, final int position) {

        final Grupo grupoItem = (Grupo) listaG.get(position);
        CircleImageView imagenMusico = holder.img;
        TextView nom = holder.nombre;
        TextView est = holder.estilo;
        TextView desc = holder.descripcion;
        TextView anun = holder.anuncios;
        ImageView busc = holder.buscando;
        BandsnArts.UID_GRUPO.add(grupoItem.getUid());

        nom.setText(grupoItem.getNombre());
        est.setText(grupoItem.getEstilo());
        desc.setText(grupoItem.getDescripcion());
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
                        switch (menuItem.getItemId()) {
                            case R.id.itemperfilvisitado:
                                //op 0 lanza perfil
                                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_GRUPO.get(position), "grupo", 0)).commit();
                                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Visitar Perfil");
                                VentanaInicialApp.id = R.id.visitaperfil;
                                System.out.println("Visitar Perfil");
                                break;
                            case R.id.itemanunciosvisitado:
                                //op 1 lanza anuncio del perfil
                                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_GRUPO.get(position), "grupo", 1)).commit();
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
            if (grupoItem.getBuscando().equalsIgnoreCase("si")) {
                busc.setImageDrawable(mContext.getDrawable(R.drawable.yes));
            } else {
                busc.setImageDrawable(mContext.getDrawable(R.drawable.no));
            }
        } catch (NullPointerException ex) {
            System.out.println("Sale por aqui en caso de que venga del primer registro");
        }
        anun.setText(String.valueOf(grupoItem.getAnuncio().size()));
        BDBAA.accesoFotoPerfilRecycler(imagenMusico, mContext, listaG.get(position));

    }


    @Override
    public int getItemCount() {
        return listaG.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView buscando;
        TextView nombre, estilo, descripcion, anuncios;
        ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            //finds de los componentes de los items
            img = itemView.findViewById(R.id.imgItemGrupo);
            buscando = itemView.findViewById(R.id.imgBuscandoItemGrupo);
            nombre = itemView.findViewById(R.id.txtNombreMusicoItemGrupo);
            estilo = itemView.findViewById(R.id.txtEstiloItemGrupo);
            descripcion = itemView.findViewById(R.id.txtDescripcionItemGrupo);
            anuncios = itemView.findViewById(R.id.txtCantidadAnunciosItemGrupo);
            menuButton = itemView.findViewById(R.id.btnMenuGrupos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new VisitarPerfilDeseado(BandsnArts.UID_GRUPO.get(getAdapterPosition()), "grupo", 0)).commit();
                    ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Visitar Perfil");
                    VentanaInicialApp.id = R.id.visitaperfil;
                    System.out.println("Visitar Perfil");
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
