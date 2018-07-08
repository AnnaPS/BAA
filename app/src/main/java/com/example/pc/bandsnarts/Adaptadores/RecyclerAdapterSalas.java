package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.Objetos.Sala;
import com.example.pc.bandsnarts.R;

import java.util.ArrayList;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE SALAS
 */
public class RecyclerAdapterSalas extends RecyclerView.Adapter<RecyclerAdapterSalas.ViewHolder> {

    private Context mContext;
    private ArrayList<Sala> listaM;

    public RecyclerAdapterSalas(Context context, ArrayList<Sala> listaSala) {
        mContext = context;
        listaM = listaSala;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salas, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterSalas.ViewHolder holder, int position) {

        final Sala salaItem = (Sala) listaM.get(position);

        TextView nom = holder.nombre;
        TextView dir = holder.direccion;
        TextView cod = holder.cp;
        TextView loc = holder.localidad;

        nom.setText(salaItem.getNombre());
        dir.setText(salaItem.getDireccion());
        cod.setText(salaItem.getCp());
        loc.setText(salaItem.getLocalidad());


    }


    @Override
    public int getItemCount() {
        return listaM.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView nombre, direccion, cp, localidad;

        public ViewHolder(View itemView) {
            super(itemView);
            //finds de los componentes de los items
            nombre = itemView.findViewById(R.id.txtNombreItemSala);
            direccion = itemView.findViewById(R.id.txtDireccionItemSala);
            cp = itemView.findViewById(R.id.txtCPItemSala);
            localidad = itemView.findViewById(R.id.txtLocalidadItemSalas);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
