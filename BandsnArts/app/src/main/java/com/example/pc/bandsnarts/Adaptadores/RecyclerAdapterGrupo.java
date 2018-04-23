package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.Musico;
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
    public void onBindViewHolder(RecyclerAdapterGrupo.ViewHolder holder, int position) {

        final Grupo grupoItem = (Grupo) listaG.get(position);
        CircleImageView imagenMusico = holder.img;
        TextView nom = holder.nombre;
        TextView est = holder.estilo;
        TextView desc = holder.descripcion;
        TextView anun = holder.anuncios;
        ImageView busc = holder.buscando;


        nom.setText(grupoItem.getNombre());
        est.setText(grupoItem.getEstilo());
        desc.setText(grupoItem.getDescripcion());
        busc.setImageDrawable(mContext.getDrawable(R.drawable.no));
        new BDBAA().accesoFotoPerfil("grupo",imagenMusico,mContext);

    }


    @Override
    public int getItemCount() {
        return listaG.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView buscando;
        TextView nombre, estilo, descripcion, anuncios;

        public ViewHolder(View itemView) {
            super(itemView);

            //finds de los componentes de los items
            img = itemView.findViewById(R.id.imgItemGrupo);
            buscando = itemView.findViewById(R.id.imgBuscandoItemGrupo);
            nombre = itemView.findViewById(R.id.txtNombreMusicoItemGrupo);
            estilo = itemView.findViewById(R.id.txtEstiloItemGrupo);
            descripcion = itemView.findViewById(R.id.txtDescripcionItemGrupo);
            anuncios = itemView.findViewById(R.id.txtCantidadAnunciosItemGrupo);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
