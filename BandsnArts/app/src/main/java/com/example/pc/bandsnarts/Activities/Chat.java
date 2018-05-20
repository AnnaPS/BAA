package com.example.pc.bandsnarts.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.AdaptadorMensajes;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

 /*   private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText edtMensajes;
    private Button btnEnviar;
    private AdaptadorMensajes adaptadorMensajes;

    //objetos para firebase
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        //finds
        fotoPerfil = findViewById(R.id.imgPerfilVChat);
        nombre = findViewById(R.id.txtNombreVChat);
        rvMensajes = findViewById(R.id.recyclerVChat);
        edtMensajes = findViewById(R.id.edtEscrbirMensajeVChat);
        btnEnviar = findViewById(R.id.btnEnviarMensajeVChat);

        adaptadorMensajes = new AdaptadorMensajes(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(linearLayoutManager);
        rvMensajes.setAdapter(adaptadorMensajes);

        //inicializacion de objetos de firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//nodo principal, sala de chat
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // adaptadorMensajes.addMensaje(new Mensajes2(edtMensajes.getText().toString(), nombre.getText().toString(), "", "1", "11:00"));
                databaseReference.push().setValue(new Mensajes2(edtMensajes.getText().toString(), nombre.getText().toString(), "", "1", "11:00"));
                edtMensajes.setText("");
            }
        });
        //para que a la vez que se van agregando items a la lista se vaya bajando la pantalla (parecido a un scroll automatico) se añade lo siguiente
        adaptadorMensajes.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();

            }
        });

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
    }

    //metodo que mueve la pantalla al utlimo item insertado
    private void setScrollBar() {
        rvMensajes.scrollToPosition(adaptadorMensajes.getItemCount() - 1);
    }*/
}
