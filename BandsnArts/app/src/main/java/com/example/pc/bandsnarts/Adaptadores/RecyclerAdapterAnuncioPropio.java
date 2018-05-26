package com.example.pc.bandsnarts.Adaptadores;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAñadirAnuncio;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * CLASE ADAPTADOR PARA LAS LISTAS DE ANUNCIOS
 */
public class RecyclerAdapterAnuncioPropio extends RecyclerView.Adapter<RecyclerAdapterAnuncioPropio.ViewHolder> {

    private Context mContext;
    public static ArrayList<Anuncio> listaA;
    public static RecyclerAdapterAnuncioPropio adapterAnuncioPropio;
    int op;

    public RecyclerAdapterAnuncioPropio(Context context, ArrayList<Anuncio> listaAnuncio, int op) {
        mContext = context;
        listaA = listaAnuncio;
        adapterAnuncioPropio = this;
        listaA = listaAnuncio;
        this.op = op;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anuncio, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterAnuncioPropio.ViewHolder holder, final int position) {

        final Anuncio anuncioItem = (Anuncio) listaA.get(position);
        TextView titulo = holder.tit;
        TextView estilo = holder.est;
        TextView desc = holder.mens;
        TextView fecha = holder.fec;
        TextView povincia = holder.prov;
        TextView localidad = holder.loc;
        TextView instrumento = holder.ins;
        TextView sexo = holder.sex;
        ImageButton btnMenu = holder.menuButton;


        titulo.setText(anuncioItem.getTitulo());
        estilo.setText(anuncioItem.getEstilo());
        desc.setText(anuncioItem.getDescripcion());
        fecha.setText(anuncioItem.getFecha());
        povincia.setText(anuncioItem.getProvincia());
        localidad.setText(anuncioItem.getLocalidad());
        instrumento.setText(anuncioItem.getInstrumento());
        sexo.setText(anuncioItem.getSexo());
        switch (op){
            case 0:
                btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //creacion del menu para el cardview
                        PopupMenu popupMenu = new PopupMenu(mContext, holder.menuButton);
                        popupMenu.inflate(R.menu.menu_anuncios_propios);
                        //Listener del menu
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.menu_anuncio_editar:
                                        android.app.FragmentManager fm = VentanaInicialApp.a.getFragmentManager();
                                        FragmentDialogAñadirAnuncio alerta = new FragmentDialogAñadirAnuncio(1, position);
                                        alerta.show(fm, "AlertaAnuncio");
                                        Toast.makeText(mContext, "Opcion editar", Toast.LENGTH_SHORT).show();
                                        break;

                                    case R.id.menu_anuncio_eliminar:
                                        listaA.remove(position);
                                        BDBAA.eliminarAnuncio(PreferenceManager.getDefaultSharedPreferences(mContext).getString("tipo", ""), FirebaseAuth.getInstance().getCurrentUser().getUid(), listaA);
                                        notifyDataSetChanged();
                                        Toast.makeText(mContext, "Eliminado con exito", Toast.LENGTH_SHORT).show();
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
                break;
            case 1:
                btnMenu.setVisibility(View.GONE);
                break;
        }



    }


    @Override
    public int getItemCount() {
        return listaA.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tit, est, ins, prov, loc, mens, fec, sex;
        ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);

            //finds de los componentes de los items

            tit = itemView.findViewById(R.id.txtTituloItemAnunciosPropio);
            fec = itemView.findViewById(R.id.txtfechaItemAnunciosPropio);
            est = itemView.findViewById(R.id.txtEstiloItemAnunciosPropio);
            ins = itemView.findViewById(R.id.txtInstrumentoItemAnunciosPropio);
            prov = itemView.findViewById(R.id.txtProvinciaItemAnunciosPropio);
            loc = itemView.findViewById(R.id.txtLocalidadItemAnunciosPropio);
            mens = itemView.findViewById(R.id.txtDescripcionItemAnuncioPropio);
            sex = itemView.findViewById(R.id.txtSexoItemAnunciosPropio);
            menuButton = itemView.findViewById(R.id.imageButtonMenu);


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
