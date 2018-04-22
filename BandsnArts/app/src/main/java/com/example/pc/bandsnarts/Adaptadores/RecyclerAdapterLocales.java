package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.Objetos.Local;
import com.example.pc.bandsnarts.Objetos.Sala;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE SALAS
 */
public class RecyclerAdapterLocales extends RecyclerView.Adapter<RecyclerAdapterLocales.ViewHolder> {

    private Context mContext;
    private ArrayList<Local> listaL;

    public RecyclerAdapterLocales(Context context, ArrayList<Local> listaLocales) {
        mContext = context;
        listaL = listaLocales;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterLocales.ViewHolder holder, int position) {

        final Local localItem = (Local) listaL.get(position);

        TextView nom = holder.nombre;
        TextView dir = holder.direccion;
        TextView cod = holder.cp;
        TextView loc = holder.localidad;
        nom.setText(localItem.getNombre());
        dir.setText(localItem.getDireccion());
        cod.setText(localItem.getCp());
        loc.setText(localItem.getLocalidad());


    }


    @Override
    public int getItemCount() {
        return listaL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView nombre, direccion, cp, localidad;

        public ViewHolder(View itemView) {
            super(itemView);
            //finds de los componentes de los items
            nombre = itemView.findViewById(R.id.txtNombreItemLocal);
            direccion = itemView.findViewById(R.id.txtDireccionItemLocal);
            cp = itemView.findViewById(R.id.txtCPItemLocal);
            localidad = itemView.findViewById(R.id.txtLocalidadItemLocal);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
