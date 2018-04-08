package com.example.pc.bandsnarts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BDBAA extends AppCompatActivity {
    DatabaseReference bd;

    public void agregarMusico(final Context context, final String imagen, final String nombre, final String sexo, final String estilo, final String instrumento, final String descripcion, final String provincia, final String localidad, final String titulo, final String descripcionAn) {
        bd = FirebaseDatabase.getInstance().getReference("musico");
        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Toast.makeText(context, "Ya existe un usuario con con el nombre "+nombre, Toast.LENGTH_SHORT).show();
                    encontrado= true;
                }
                if(!encontrado){
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("musico");
                    Musico mus = new Musico(imagen, nombre, sexo, estilo, instrumento, descripcion, provincia, localidad, new Anuncio(titulo, descripcionAn));
                    bd.child(bd.push().getKey()).setValue(mus);
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void agregarGrupo(final Context context, final String imagen, final String nombre,  final String estilo,  final String descripcion, final String provincia, final String localidad, final String titulo, final String descripcionAn) {
        bd = FirebaseDatabase.getInstance().getReference("grupo");
        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Toast.makeText(context, "Ya existe un grupo con con el nombre "+nombre, Toast.LENGTH_SHORT).show();
                    encontrado= true;
                }
                if(!encontrado){
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("grupo");
                    Grupo gru= new Grupo(imagen, nombre, estilo, descripcion, provincia, localidad, new Anuncio(titulo, descripcionAn));
                    bd.child(bd.push().getKey()).setValue(gru);
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
