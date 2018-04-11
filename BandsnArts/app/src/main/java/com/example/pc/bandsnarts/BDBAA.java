package com.example.pc.bandsnarts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BDBAA extends AppCompatActivity {
    DatabaseReference bd;

    public BDBAA() {
    }

    public void agregarMusico(final Context context, final String imagen, final String nombre, final String sexo, final String estilo, final String instrumento, final String descripcion) {
        bd = FirebaseDatabase.getInstance().getReference("musico");
        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("insert", "No pudo insertar");
                    Toast.makeText(context, "Ya existe un usuario con con el nombre " + nombre, Toast.LENGTH_SHORT).show();
                    encontrado = true;
                }
                if (!encontrado) {
                    Log.d("insert", "Insertado con exito");


                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("musico");
                    Musico mus = new Musico(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, sexo, estilo, instrumento, descripcion);
                    bd.child(bd.push().getKey()).setValue(mus);

                    FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseAuth.getInstance().getCurrentUser().delete();
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void agregarGrupo(final Context context, final String imagen, final String nombre, final String estilo, final String descripcion) {

        bd = FirebaseDatabase.getInstance().getReference("grupo");
        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("INSERt", "No insertado ");
                    Toast.makeText(context, "Ya existe un grupo con con el nombre " + nombre, Toast.LENGTH_SHORT).show();
                    encontrado = true;
                }
                if (!encontrado) {
                    Log.d("INSERt", "Insertado ");
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("grupo");
                    Grupo gru = new Grupo(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, estilo, descripcion);
                    bd.child(bd.push().getKey()).setValue(gru);
                    FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR BD", "\n\nonCancelled: " + databaseError.getMessage() + "\n\n");
                FirebaseAuth.getInstance().getCurrentUser().delete();
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void comprobarUID(final Context cont, String uid) {
        bd = FirebaseDatabase.getInstance().getReference("uids");
        Query q = bd.orderByChild("uid").equalTo(uid);
        Log.d("UID", "onDataChange: " + uid);

        /* Query q = FirebaseDatabase.getInstance().getReference("uid").equalTo(uid);*/
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    encontrado = true;
                }
                if (!encontrado) {
                    Log.d("Encontrado", "onDataChange: " + encontrado);
                    ((Activity) cont).startActivity(new Intent(cont, RegistarRedSocial.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
