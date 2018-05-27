package com.example.pc.bandsnarts.FragmentsMenuDrawer;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.AdaptadorMensajes;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Objetos.KeyChat;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMensajes extends Fragment {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText edtMensajes;
    private Button btnEnviar;
    public static AdaptadorMensajes adaptadorMensajes;

    //objetos para firebase


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista = inflater.inflate(R.layout.fragment_chat, container, false);

        //finds
        fotoPerfil = vista.findViewById(R.id.imgPerfilVChat);
        nombre = vista.findViewById(R.id.txtNombreVChat);
        rvMensajes = vista.findViewById(R.id.recyclerVChat);
        edtMensajes = vista.findViewById(R.id.edtEscrbirMensajeVChat);
        btnEnviar = vista.findViewById(R.id.btnEnviarMensajeVChat);

        BDBAA.recuperarMensajes(vista, BandsnArts.KEYCHAT, rvMensajes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvMensajes.setLayoutManager(linearLayoutManager);
        rvMensajes.setAdapter(adaptadorMensajes);

        //inicializacion de objetos de firebase


        //nodo principal, sala de chat
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BDBAA.nuevoMensaje(BandsnArts.KEYCHAT,edtMensajes.getText().toString());
                setScrollBar();
                edtMensajes.setText("");
            }
        });
        //para que a la vez que se van agregando items a la lista se vaya bajando la pantalla (parecido a un scroll automatico) se añade lo siguiente

        return vista;
    }

    //metodo que mueve la pantalla al utlimo item insertado
    private void setScrollBar() {
        rvMensajes.scrollToPosition(adaptadorMensajes.getItemCount() - 1);
    }


}


