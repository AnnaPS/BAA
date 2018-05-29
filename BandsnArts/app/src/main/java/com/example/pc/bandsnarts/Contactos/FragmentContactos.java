package com.example.pc.bandsnarts.Contactos;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.AdaptadorContactos;
import com.example.pc.bandsnarts.Adaptadores.AdaptadorMensajes;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

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
    private void setScrollBar() {
        BandsnArts.rvContactos.scrollToPosition(adaptadorContactos.getItemCount() - 1);
    }


}


