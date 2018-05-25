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
    private AdaptadorMensajes adaptadorMensajes;

    //objetos para firebase
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_chat, container, false);

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
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//nodo principal, sala de chat
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                adaptadorMensajes.addMensaje(new Mensajes2(edtMensajes.getText().toString(), nombre.getText().toString(), "", "1", "11:00"));
                databaseReference.push().setValue(new Mensajes2(edtMensajes.getText().toString(), nombre.getText().toString(), "", "1", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
                edtMensajes.setText("");
            }
        });
        //para que a la vez que se van agregando items a la lista se vaya bajando la pantalla (parecido a un scroll automatico) se añade lo siguiente

        //agregar hijo al nodo
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensajes2 mensajes2 = dataSnapshot.getValue(Mensajes2.class);
                adaptadorMensajes.addMensaje(mensajes2);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return vista;
    }

    //metodo que mueve la pantalla al utlimo item insertado
    private void setScrollBar() {
        rvMensajes.scrollToPosition(adaptadorMensajes.getItemCount() - 1);
    }


}


