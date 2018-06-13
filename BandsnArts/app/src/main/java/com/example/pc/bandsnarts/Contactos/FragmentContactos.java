package com.example.pc.bandsnarts.Contactos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.AdaptadorContactos;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentContactos extends Fragment {

    private CircleImageView fotoPerfil;
    private TextView nombre;

    private AdaptadorContactos adaptadorContactos;
    private CardView cardContactos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_contactos, container, false);
        //finds
        fotoPerfil = vista.findViewById(R.id.imgPerfilVContactos);
        nombre = vista.findViewById(R.id.txtNombreVContactos);
        BandsnArts.rvContactos = vista.findViewById(R.id.recyclerVContactos);
        cardContactos=vista.findViewById(R.id.cardContactos);
        BDBAA.recuperarConversaciones(vista);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        BandsnArts.rvContactos.setLayoutManager(linearLayoutManager);
        BandsnArts.rvContactos.setAdapter(adaptadorContactos);
        return vista;
    }

    //metodo que mueve la pantalla al utlimo item insertado
    public static void setScrollBar() {
        BandsnArts.rvContactos.scrollToPosition(BandsnArts.adaptadorContactos.getItemCount() - 1);
    }


}


