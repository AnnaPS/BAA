package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE MUSICOS/GRUPOS
 */
public class RecyclerAdapterMusico extends RecyclerView.Adapter<RecyclerAdapterMusico.ViewHolder> {

    private Context mContext;
    private ArrayList<Musico> listaM;

    public RecyclerAdapterMusico(Context context, ArrayList<Musico> listaMusicos) {
        mContext = context;
        listaM = listaMusicos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View vista = layoutInflater.inflate(R.layout.item_musicos, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);*/
        View vista;
        vista = LayoutInflater.from(mContext).inflate(R.layout.item_musicos, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterMusico.ViewHolder holder, int position) {

        Musico musicoItem = (Musico) listaM.get(position);
        CircleImageView imagenMusico = holder.img;
        TextView nom = holder.nombre;
        TextView ins = holder.instrumento;
        TextView est = holder.estilo;
        TextView desc = holder.descripcion;
        TextView anun = holder.anuncios;
        ImageView busc = holder.buscando;

        //imagenMusico.setImageResource(musicoItem.getImg());
        nom.setText(musicoItem.getNombre());
        ins.setText(musicoItem.getInstrumento());
        est.setText(musicoItem.getEstilo());
        desc.setText(musicoItem.getDescripcion());
//        anun.setText(musicoItem.getCantidadAnuncios());
        busc.setImageResource(musicoItem.getBuscandoInt());

    }


    @Override
    public int getItemCount() {
        return listaM.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView buscando;
        TextView nombre, instrumento, estilo, descripcion, anuncios;

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

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
