package com.example.pc.bandsnarts.BBDD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.pc.bandsnarts.Activities.RegistarRedSocial;
import com.example.pc.bandsnarts.Activities.RegistrarGrupo;
import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.Activities.VentanaSliderParteDos;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterAnuncioPropio;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterGrupo;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterLocales;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterSalas;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentAnuncios;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAñadirAnuncio;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogDescartarCambios;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentMultimedia;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentVerMiPerfil;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.Local;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.Objetos.Sala;
import com.example.pc.bandsnarts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BDBAA extends AppCompatActivity {


    public BDBAA() {
    }

    /*public static void agregarMusico(final Context context, final View view, final EditText edtnombre, final String imagen, final String nombre, final String sexo, final String estilo, final ArrayList<String> instrumento, final String descripcion) {
        // Nos posicionamos
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("musico");

        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("insert", "No pudo insertar");
                    Toast.makeText(context, "Ya existe un usuario con con el nombre " + nombre, Toast.LENGTH_SHORT).show();
                    edtnombre.setError("EL nombre Ya existe pruebe con otro", context.getDrawable(android.R.drawable.stat_notify_error));
                    // limpiar campo !!!!!
                    view.setVisibility(View.VISIBLE);
                    encontrado = true;
                }
                if (!encontrado) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "musico").commit();
                    Log.d("insert", "Insertado con exito");
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("musico");
                    Musico mus = new Musico(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, sexo, estilo, instrumento, descripcion);
                    bd.child(bd.push().getKey()).setValue(mus);
                    FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, VentanaSliderParteDos.class));
                    ((Activity) context).setResult(BandsnArts.CODIGO_DE_REGISTRO_RED_SOCIAL);
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void agregarGrupo(final Context context, final View view, final EditText edtnombre, final String imagen, final String nombre, final String estilo, final String descripcion) {

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("grupo");
        Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("INSERt", "No insertado ");
                    Grupo gru = ds.getValue(Grupo.class);

                    Toast.makeText(context, "Ya existe un grupo con con el nombre " + nombre, Toast.LENGTH_SHORT).show();
                    edtnombre.setError("EL nombre Ya existe pruebe con otro", context.getDrawable(android.R.drawable.stat_notify_error));
                    view.setVisibility(View.VISIBLE);

                    encontrado = true;
                }

                if (!encontrado) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "grupo").commit();
                    Log.d("INSERt", "Insertado ");
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference("grupo");
                    Grupo gru = new Grupo(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, estilo, descripcion);
                    bd.child(bd.push().getKey()).setValue(gru);
                    FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, VentanaSliderParteDos.class));
                    ((Activity) context).setResult(BandsnArts.CODIGO_DE_REGISTRO_RED_SOCIAL);
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR BD", "\n\nonCancelled: " + databaseError.getMessage() + "\n\n");
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    public static void agregarFackingMaster(final String tipo, final Context context, final View view, final EditText correo, final EditText edtnombre, final String imagen, final String nombre, final String sexo, final String estilo, final ArrayList instrumento, final String descripcion) {
        Query q = FirebaseDatabase.getInstance().getReference("uids").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean encontrado = false;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    view.setVisibility(View.VISIBLE);
                    correo.setError("Ese correo ya esta registrado.");
                    FirebaseAuth.getInstance().signOut();
                    encontrado = true;
                }
                if (!encontrado) {
                    DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
                    Query q = bd.orderByChild("nombre").equalTo(nombre.toString());
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        boolean encontrados = false;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Log.d("INSERT", "No insertado ");
                                edtnombre.setError("EL nombre ya esta en uso pruebe con otro", context.getDrawable(android.R.drawable.stat_notify_error));
                                edtnombre.requestFocus();
                                view.setVisibility(View.VISIBLE);

                                encontrados = true;
                            }
                            if (!encontrados) {
                                DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
                                switch (tipo) {
                                    case "musico":
                                        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "musico").commit();
                                        Musico mus = new Musico(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, sexo, estilo, instrumento, descripcion);
                                        bd.child(bd.push().getKey()).setValue(mus);
                                        break;
                                    case "grupo":
                                        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "grupo").commit();
                                        Grupo gru = new Grupo(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, nombre, estilo, descripcion);
                                        bd.child(bd.push().getKey()).setValue(gru);
                                        break;
                                }
                                FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                context.startActivity(new Intent(context, VentanaSliderParteDos.class));
                                ((Activity) context).setResult(BandsnArts.CODIGO_DE_REGISTRO_RED_SOCIAL);
                                ((Activity) context).finish();
                                Log.d("INSERTADO", "Insertado con exito");

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("ERROR BD", "\n\nonCancelled: " + databaseError.getMessage() + "\n\n");
                            Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void agregarEditarAnuncio(final int posControl, String uid, final String tipos, final String titulo, final String descripcion, final String tipo, final String fecha, final String provincia, final String localidad, final String estilo, final String instrumento, final String sexo) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipos);
        Query q = bd.orderByChild("uid").equalTo(uid);
        Log.d("UID!", "onDataChange: " + uid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("UID!", "onDataChange: PEPEPEPE");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Anuncio anu = new Anuncio(
                            titulo,
                            descripcion,
                            tipo,
                            fecha,
                            provincia,
                            localidad,
                            estilo,
                            instrumento,
                            sexo);
                    switch (tipos) {
                        case "musico":
                            Musico mus = ds.getValue(Musico.class);
                            if (posControl == -1) {
                                mus.setAnuncios(anu);
                                RecyclerAdapterAnuncioPropio.listaA.add(anu);
                            } else {
                                mus.getAnuncio().get(posControl).setTitulo(titulo);
                                mus.getAnuncio().get(posControl).setDescripcion(descripcion);
                                mus.getAnuncio().get(posControl).setTipo(tipo);
                                mus.getAnuncio().get(posControl).setFecha(fecha);
                                mus.getAnuncio().get(posControl).setProvincia(provincia);
                                mus.getAnuncio().get(posControl).setLocalidad(localidad);
                                mus.getAnuncio().get(posControl).setEstilo(estilo);
                                mus.getAnuncio().get(posControl).setInstrumento(instrumento);
                                mus.getAnuncio().get(posControl).setSexo(sexo);
                                RecyclerAdapterAnuncioPropio.listaA.set(posControl, anu);
                            }
                            bd.child(ds.getKey()).setValue(mus);
                            break;
                        case "grupo":
                            Grupo gru = ds.getValue(Grupo.class);
                            if (posControl == -1) {
                                gru.setAnuncios(anu);
                                RecyclerAdapterAnuncioPropio.listaA.add(anu);
                            } else {
                                gru.getAnuncio().get(posControl).setTitulo(titulo);
                                gru.getAnuncio().get(posControl).setDescripcion(descripcion);
                                gru.getAnuncio().get(posControl).setTipo(tipo);
                                gru.getAnuncio().get(posControl).setFecha(fecha);
                                gru.getAnuncio().get(posControl).setProvincia(provincia);
                                gru.getAnuncio().get(posControl).setLocalidad(localidad);
                                gru.getAnuncio().get(posControl).setEstilo(estilo);
                                gru.getAnuncio().get(posControl).setInstrumento(instrumento);
                                gru.getAnuncio().get(posControl).setSexo(sexo);
                                RecyclerAdapterAnuncioPropio.listaA.set(posControl, anu);
                            }
                            bd.child(ds.getKey()).setValue(gru);
                            break;

                    }
                    Toast.makeText(VentanaInicialApp.a.getApplicationContext(), "GUARDADO CON EXITO", Toast.LENGTH_SHORT).show();

                    RecyclerAdapterAnuncioPropio.adapterAnuncioPropio.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void borrarPerfil(final String uid) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference("uids");
        Query q = bd.orderByChild("uid").equalTo(uid);
        Log.d("UID", "onDataChange: " + uid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    bd.child(data.getKey()).removeValue();
                    encontrado = true;
                }
                if (!encontrado) {
                    Log.d("Encontrado", "onDataChange: " + encontrado);

                } else {
                    Log.d("Encontrado", "onDataChange: " + encontrado);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void eliminarAnuncio(final String type, String uid, final ArrayList lista) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(type);
        Query q = bd.orderByChild("uid").equalTo(uid);
        Log.d("UID", "onDataChange: " + uid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (type) {
                        case "musico":
                            Musico mus = data.getValue(Musico.class);
                            mus.setAnuncio(lista);
                            bd.child(data.getKey()).setValue(mus);
                            break;
                        case "grupo":
                            Grupo gru = data.getValue(Grupo.class);
                            gru.setAnuncio(lista);
                            bd.child(data.getKey()).setValue(gru);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void eliminarNodo(String type, String uid) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(type);
        Query q = bd.orderByChild("uid").equalTo(uid);
        Log.d("UID", "onDataChange: " + uid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    bd.child(data.getKey()).removeValue();
                    encontrado = true;
                }
                if (!encontrado) {
                    Log.d("Encontrado2", "onDataChange: " + encontrado);
                } else {
                    Log.d("Encontrado2", "onDataChange: " + encontrado);
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void comprobarUID(final Context cont, final String uid) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("uids");
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
                    ((Activity) cont).startActivityForResult(new Intent(cont, RegistarRedSocial.class), 111);
                } else {
                    comprobarTipo(cont, uid);
                    ((Activity) cont).startActivityForResult(new Intent(cont, VentanaInicialApp.class), 222);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void comprobarNumAnuncios(final String tipo, String uid) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(uid);

        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numAnuncios = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            numAnuncios = data.getValue(Musico.class).getAnuncio().size();
                            break;
                        case "grupo":
                            numAnuncios = data.getValue(Grupo.class).getAnuncio().size();
                            break;
                    }
                    if (numAnuncios < 4) {
                        android.app.FragmentManager fm = VentanaInicialApp.a.getFragmentManager();
                        FragmentDialogAñadirAnuncio alerta = new FragmentDialogAñadirAnuncio(0, -1);
                        alerta.show(fm, "AlertaAnuncio");
                    } else {
                        Toast.makeText(VentanaInicialApp.a.getApplicationContext(), "Solo puede añadir hasta 4 anuncios.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void comprobarTipo(final Context cont, String uid) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("musico");
        Query q = bd.orderByChild("uid").equalTo(uid);

        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    encontrado = true;
                    Log.d("", "onDataChange: PEPE");
                }
                if (encontrado) {
                    // Es musico, lo guardamos en preferencias
                    PreferenceManager.getDefaultSharedPreferences(cont).edit().putString("tipo", "musico").commit();
                } else {
                    // Es grupo, lo guardamos en preferencias
                    PreferenceManager.getDefaultSharedPreferences(cont).edit().putString("tipo", "grupo").commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarDrawerPerfil(final Context context, final String tipo, final ImageView fotoPerfil, final TextView nombre) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            nombre.setText(data.getValue(Musico.class).getNombre());
                            accesoFotoPerfil("musico", fotoPerfil, context, FirebaseAuth.getInstance().getCurrentUser().getUid());

                            break;
                        case "grupo":
                            nombre.setText(data.getValue(Grupo.class).getNombre());
                            accesoFotoPerfil("grupo", fotoPerfil, context, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void cargarDatosPerfil(final View vista, final String tipo) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            // Recuperamos y cargamos los datos del Musico
                            // nombre
                            ((TextView) vista.findViewById(R.id.txtNombUsuarioVVerMiPerfil)).setText(musico.getNombre());
                            // FotoPerfil
                            accesoFotoPerfil("musico", ((ImageView) vista.findViewById(R.id.imgPerfilVPerfil)), vista.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                            // Estilo
                            ((TextView) vista.findViewById(R.id.txtEstiloVVerMiPerfil)).setText(musico.getEstilo());
                            // Provincia
                            ((TextView) vista.findViewById(R.id.txtProvinciaVVerMiPerfil)).setText(musico.getProvincia());
                            // Localidad
                            ((TextView) vista.findViewById(R.id.txtLocalidadVVerMiPerfil)).setText(musico.getLocalidad());
                            // Sexo....
                            ((TextView) vista.findViewById(R.id.txtSexoVVerMiPerfil)).setText(musico.getSexo());
                            // Descripcion
                            ((TextView) vista.findViewById(R.id.txtDescripcionVVerMiPerfil)).setText(musico.getDescripcion());
                            //Instrumentos
                            ((TextView) vista.findViewById(R.id.txtInstrumentoVVerMiPerfil1)).setText(musico.getInstrumento().get(0));
                            //Buscando

                            if (musico.getBuscando().equalsIgnoreCase("si")) {
                                ((ImageView) vista.findViewById(R.id.imgBuscandoVerMiPerfil)).setImageDrawable(vista.getResources().getDrawable(R.drawable.yes));
                            } else {
                                ((ImageView) vista.findViewById(R.id.imgBuscandoVerMiPerfil)).setImageDrawable(vista.getResources().getDrawable(R.drawable.no));
                            }

                            try {
                                ((TextView) vista.findViewById(R.id.txtInstrumentoVVerMiPerfil2)).setText(musico.getInstrumento().get(1));
                                ((TextView) vista.findViewById(R.id.txtInstrumentoVVerMiPerfil3)).setText(musico.getInstrumento().get(2));
                                ((TextView) vista.findViewById(R.id.txtInstrumentoVVerMiPerfil4)).setText(musico.getInstrumento().get(3));
                            } catch (IndexOutOfBoundsException e) {
                                // En caso de que solo tenga el instrumento principal
                                System.out.println("Si me salgo de rango");
                            }
                            break;
                        case "grupo":

                            Grupo grupo = data.getValue(Grupo.class);
                            // Recuperamos y cargamos los datos del Musico
                            // nombre
                            ((TextView) vista.findViewById(R.id.txtNombUsuarioVVerMiPerfil)).setText(grupo.getNombre());
                            // FotoPerfil
                            accesoFotoPerfil("grupo", ((ImageView) vista.findViewById(R.id.imgPerfilVPerfil)), vista.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                            // Estilo
                            ((TextView) vista.findViewById(R.id.txtEstiloVVerMiPerfil)).setText(grupo.getEstilo());
                            // Provincia
                            ((TextView) vista.findViewById(R.id.txtProvinciaVVerMiPerfil)).setText(grupo.getProvincia());
                            // Localidad
                            ((TextView) vista.findViewById(R.id.txtLocalidadVVerMiPerfil)).setText(grupo.getLocalidad());
                            // Sexo....
                            ((LinearLayout) vista.findViewById(R.id.llSexoVVerMiPerfil)).setVisibility(View.GONE);

                            // Descripcion
                            ((TextView) vista.findViewById(R.id.txtDescripcionVVerMiPerfil)).setText(grupo.getDescripcion());
                            //Buscando

                            if (grupo.getBuscando().equalsIgnoreCase("si")) {
                                ((ImageView) vista.findViewById(R.id.imgBuscandoVerMiPerfil)).setImageDrawable(vista.getResources().getDrawable(R.drawable.yes));
                            } else {
                                ((ImageView) vista.findViewById(R.id.imgBuscandoVerMiPerfil)).setImageDrawable(vista.getResources().getDrawable(R.drawable.no));
                            }

                            // Ocultamos los Instrumentos por tratarse de un grupo
                            vista.findViewById(R.id.appBarLayoutInstrumentos).setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarDatosVisitarPerfil(
            final int pos
            , final View vista, final String tipo
            , final ImageView imagenPerfil, final TextView nombreUsuario
            , final TextView cajaEstilo, final TextView cajaDescripcion
            , final TextView cajaProvincia, final TextView cajaLocalidad
            , final TextView tvSexo, final TextView tvInstrumento
            , final TextView cajaSexo, final TextView[] instrumnetos
            , final View ll_inst1, final View ll_inst2
            , final View ll_instsec1, final View ll_instsec2, final View ll_instsec3) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("nombre");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int i = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    System.out.println("Posición " + pos + " Puntero " + i);
                    if (i == pos) {
                        switch (tipo) {
                            case "musico":
                                Musico musico = data.getValue(Musico.class);
                                // Recuperamos y cargamos los datos del Musico
                                // nombre
                                nombreUsuario.setText(musico.getNombre());
                                // FotoPerfil
                                accesoFotoPerfil("musico", imagenPerfil, vista.getContext(), musico.getUid());
                                // Estilo
                                cajaEstilo.setText(musico.getEstilo());
                                // Provincia
                                cajaProvincia.setText(musico.getProvincia());
                                // Localidad
                                cajaLocalidad.setText(musico.getLocalidad());
                                // Sexo....
                                cajaSexo.setText(musico.getSexo());
                                // Descripcion
                                cajaDescripcion.setText(musico.getDescripcion());
                                //Instrumentos
                                instrumnetos[0].setText(musico.getInstrumento().get(0));
                                try {
                                    if ("Sin especificar".equalsIgnoreCase(musico.getInstrumento().get(1))) {
                                        ll_instsec1.setVisibility(View.GONE);
                                    } else {
                                        instrumnetos[1].setText(musico.getInstrumento().get(1));
                                    }
                                    if ("Sin especificar".equalsIgnoreCase(musico.getInstrumento().get(2))) {

                                        ll_instsec2.setVisibility(View.GONE);
                                    } else {
                                        instrumnetos[2].setText(musico.getInstrumento().get(2));
                                    }
                                    if ("Sin especificar".equalsIgnoreCase(musico.getInstrumento().get(3))) {
                                        ll_instsec3.setVisibility(View.GONE);
                                    } else {
                                        instrumnetos[3].setText(musico.getInstrumento().get(3));
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    // En caso de que solo tenga el instrumento principal
                                    System.out.println("Si me salgo de rango");
                                }
                                break;
                            case "grupo":

                                Grupo grupo = data.getValue(Grupo.class);
                                // Recuperamos y cargamos los datos del Musico
                                // nombre
                                nombreUsuario.setText(grupo.getNombre());
                                // FotoPerfil
                                accesoFotoPerfil("grupo", imagenPerfil, vista.getContext(), grupo.getUid());
                                // Estilo
                                cajaEstilo.setText(grupo.getEstilo());
                                // Provincia
                                cajaProvincia.setText(grupo.getProvincia());
                                // Localidad
                                cajaLocalidad.setText(grupo.getLocalidad());
                                // Sexo....
                                tvSexo.setVisibility(View.GONE);
                                cajaSexo.setVisibility(View.GONE);

                                // Descripcion
                                cajaDescripcion.setText(grupo.getDescripcion());
                                // Ocultamos los Instrumentos por tratarse de un grupo
                                tvInstrumento.setVisibility(View.GONE);
                                ll_inst1.setVisibility(View.GONE);
                                ll_inst2.setVisibility(View.GONE);
                                break;
                        }
                        break;
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarDatosPerfilEditar(final View vista, final String tipo, final Context context) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int posicion;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            // Recuperamos y cargamos los datos del Musico
                            // FotoPerfil
                            accesoFotoPerfil("musico", ((ImageView) vista.findViewById(R.id.imgPerfilVPerfil)), context, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            // Estilo
                            posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.estiloMusical), musico.getEstilo());
                            ((Spinner) vista.findViewById(R.id.spEstiloVVerMiPerfil)).setSelection(posicion);
                            Toast.makeText(context, "" + posicion, Toast.LENGTH_SHORT).show();
                            //localidaProvincia
                            BandsnArts.cargarLocalidadProvincia(vista, musico, (Spinner) vista.findViewById(R.id.spProvinVVerMiPerfil), ((Spinner) vista.findViewById(R.id.spLocaliVVerMiPerfil)));

                            // Sexo....
                            posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.sexo), musico.getSexo());
                            ((Spinner) vista.findViewById(R.id.spSexoVVerMiPerfil)).setSelection(posicion);
                            // Descripcion
                            ((TextView) vista.findViewById(R.id.txtDescripcionVVerMiPerfil)).setText(musico.getDescripcion());
                            //Buscando

                            if (musico.getBuscando().equalsIgnoreCase("si")) {
                                ((Switch) vista.findViewById(R.id.swBuscando)).setChecked(true);
                            } else {
                                ((Switch) vista.findViewById(R.id.swBuscando)).setChecked(false);
                            }

                            //Instrumentos
                            // Instumento Principal
                            posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), musico.getInstrumento().get(0));
                            ((Spinner) vista.findViewById(R.id.spInstrumentoVVerMiPerfil1)).setSelection(posicion);

                            try {
                                posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), musico.getInstrumento().get(1));
                                ((Spinner) vista.findViewById(R.id.spInstrumentoVVerMiPerfil2)).setSelection(posicion);
                                posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), musico.getInstrumento().get(2));
                                ((Spinner) vista.findViewById(R.id.spInstrumentoVVerMiPerfil3)).setSelection(posicion);
                                posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), musico.getInstrumento().get(3));
                                ((Spinner) vista.findViewById(R.id.spInstrumentoVVerMiPerfil4)).setSelection(posicion);
                            } catch (IndexOutOfBoundsException e) {
                                // En caso de que solo tenga el instrumento principal
                            }

                            break;
                        case "grupo":
                            Grupo grupo = data.getValue(Grupo.class);
                            // Recuperamos y cargamos los datos del Musico
                            // FotoPerfil
                            accesoFotoPerfil("grupo", ((ImageView) vista.findViewById(R.id.imgPerfilVPerfil)), context, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            // Estilo
                            posicion = BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.estiloMusical), grupo.getEstilo());
                            ((Spinner) vista.findViewById(R.id.spEstiloVVerMiPerfil)).setSelection(posicion);
                            //localidadProvincia
                            BandsnArts.cargarLocalidadProvincia(vista, grupo, (Spinner) vista.findViewById(R.id.spProvinVVerMiPerfil), ((Spinner) vista.findViewById(R.id.spLocaliVVerMiPerfil)));
                            // Sexo....
                            ((LinearLayout) vista.findViewById(R.id.llSexoVVerMiPerfil)).setVisibility(View.GONE);
                            // Descripcion
                            ((TextView) vista.findViewById(R.id.txtDescripcionVVerMiPerfil)).setText(grupo.getDescripcion());
                            //Buscando
                            if (grupo.getBuscando().equalsIgnoreCase("si")) {
                                ((Switch) vista.findViewById(R.id.swBuscando)).setChecked(true);
                            } else {
                                ((Switch) vista.findViewById(R.id.swBuscando)).setChecked(false);
                            }
                            // Ocultamos los Instrumentos por tratarse de un grupo
                            vista.findViewById(R.id.appBarLayoutInstrumentos).setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarAnuncio(final View vista, final int position, final String tipo, final Context context, final Spinner spProvincia, final Spinner spLocalidad, final Spinner spTipoBusqueda,
                                     final TextView fecha, final EditText titulo, final TextView descripcionAnuncio, final Spinner spEstilo,
                                     final Spinner spSexo, final Spinner spInstrumento) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            Anuncio anuM = data.getValue(Musico.class).getAnuncio().get(position);
                            spTipoBusqueda.setAdapter(new ArrayAdapter(context, R.layout.spinner_item, vista.getResources().getStringArray(R.array.tipobusquedamusico)));
                            spTipoBusqueda.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.tipobusquedamusico), anuM.getTipo()));
                            spEstilo.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.estiloMusical), anuM.getEstilo()));
                            spSexo.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.sexo), anuM.getSexo()));
                            spInstrumento.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), anuM.getInstrumento()));
                            fecha.setText(anuM.getFecha());
                            titulo.setText(anuM.getTitulo());
                            descripcionAnuncio.setText(anuM.getDescripcion());
                            BandsnArts.cargarLocalidadProvincia(vista, anuM, spProvincia, spLocalidad);
                            break;
                        case "grupo":
                            Anuncio anuG = data.getValue(Grupo.class).getAnuncio().get(position);
                            spTipoBusqueda.setAdapter(new ArrayAdapter(context, R.layout.spinner_item, vista.getResources().getStringArray(R.array.tipobusquedamusico)));
                            spTipoBusqueda.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.tipobusquedamusico), anuG.getTipo()));
                            spEstilo.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.estiloMusical), anuG.getEstilo()));
                            spSexo.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.sexo), anuG.getSexo()));
                            spInstrumento.setSelection(BandsnArts.posicionSpinner(vista.getResources().getStringArray(R.array.instrumentos), anuG.getInstrumento()));
                            fecha.setText(anuG.getFecha());
                            titulo.setText(anuG.getTitulo());
                            descripcionAnuncio.setText(anuG.getDescripcion());
                            BandsnArts.cargarLocalidadProvincia(vista, anuG, spProvincia, spLocalidad);
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarDatosAnuncio(final View vista, final String tipo, final Context context, final Spinner spProvincia, final Spinner spLocalidad, final Spinner spTipoBusqueda) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            spTipoBusqueda.setAdapter(new ArrayAdapter(context, R.layout.spinner_item, vista.getResources().getStringArray(R.array.tipobusquedamusico)));
                            BandsnArts.cargarLocalidadProvincia(vista, musico, spProvincia, spLocalidad);
                            break;
                        case "grupo":
                            Grupo grupo = data.getValue(Grupo.class);
                            spTipoBusqueda.setAdapter(new ArrayAdapter(context, R.layout.spinner_item, vista.getResources().getStringArray(R.array.tipobusquedagrupo)));
                            BandsnArts.cargarLocalidadProvincia(vista, grupo, spProvincia, spLocalidad);
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void modificarDatosUsuario(final String tipo, final Context context, final String sexo, final String estilo, final ArrayList<String> instrumento, final String descripcion, final String provincia, final String localidad, final String buscando) {
        // Nos posicionamos
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        // Recuperamos al usuario a través de su UID
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    switch (tipo) {

                        case ("musico"):

                            Musico mus = ds.getValue(Musico.class);
                            mus.setSexo(sexo);
                            mus.setEstilo(estilo);
                            mus.setInstrumento(instrumento);
                            mus.setDescripcion(descripcion);
                            mus.setProvincia(provincia);
                            mus.setLocalidad(localidad);
                            mus.setBuscando(buscando);

                            bd.child(ds.getKey()).setValue(mus);
                            Log.d("insert", "Insertado con exito");
                            Toast.makeText(context, "Actualizado músico con exito", Toast.LENGTH_SHORT).show();
                            break;
                        case ("grupo"):
                            Grupo gr = ds.getValue(Grupo.class);
                            gr.setEstilo(estilo);
                            gr.setDescripcion(descripcion);
                            gr.setProvincia(provincia);
                            gr.setLocalidad(localidad);
                            gr.setBuscando(buscando);

                            bd.child(ds.getKey()).setValue(gr);
                            Log.d("insert", "Actualizado grupo con exito");
                            Toast.makeText(context, "Actualizado grupo con exito", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No se pudo agregar con exito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void cargarAnuncios(final ArrayList lista, final RecyclerView recyclerView, final Activity activity, String uid, final String tipo, final int op) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(uid);
        lista.clear();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "grupo":
                            Grupo grp = data.getValue(Grupo.class);
                            for (Anuncio anu : grp.getAnuncio()) {
                                lista.add(anu);
                            }

                            break;
                        case "musico":
                            Musico mus = data.getValue(Musico.class);
                            for (Anuncio anu : mus.getAnuncio()) {
                                lista.add(anu);
                            }
                            break;

                    }
                }
                RecyclerAdapterAnuncioPropio adapter = new RecyclerAdapterAnuncioPropio(activity.getApplicationContext(), lista, op);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarVisitarAnuncios(final String tipo, final RecyclerView recyclerView, final Activity activity, final int op, final int position) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("nombre");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int i = 0;
            ArrayList lista;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    System.out.println("Posición " + position + " Puntero " + i);
                    if (i == position) {
                        switch (tipo) {
                            case "musico":
                                lista = data.getValue(Musico.class).getAnuncio();
                                break;
                            case "grupo":
                                lista = data.getValue(Grupo.class).getAnuncio();
                                break;
                        }
                        break;
                    }
                    i++;
                }
                RecyclerAdapterAnuncioPropio adapter = new RecyclerAdapterAnuncioPropio(activity.getApplicationContext(), lista, op);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static void cargarDatos(final ArrayList lista, final RecyclerView recyclerView, final Activity activity, final String tipo) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("nombre");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    switch (tipo) {
                        case "grupo":
                            Grupo grp = data.getValue(Grupo.class);
                            if (!grp.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                lista.add(grp);
                            break;
                        case "musico":
                            Musico mus = data.getValue(Musico.class);
                            if (!mus.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                lista.add(mus);
                            break;
                        case "locales":
                            Local loc = data.getValue(Local.class);
                            lista.add(loc);
                            break;
                        case "salas":
                            Sala sal = data.getValue(Sala.class);
                            lista.add(sal);
                            break;
                    }
                }
                switch (tipo) {
                    case "grupo":
                        RecyclerAdapterGrupo adapterG = new RecyclerAdapterGrupo(activity.getApplicationContext(), lista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapterG);
                        break;
                    case "musico":
                        RecyclerAdapterMusico adapterM = new RecyclerAdapterMusico(activity.getApplicationContext(), lista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapterM);
                        break;
                    case "locales":
                        RecyclerAdapterLocales adapterL = new RecyclerAdapterLocales(activity.getApplicationContext(), lista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapterL);
                        break;
                    case "salas":
                        RecyclerAdapterSalas adapterS = new RecyclerAdapterSalas(activity.getApplicationContext(), lista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapterS);
                        break;
                }
                recyclerView.setNestedScrollingEnabled(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void actualizarFotoPerfil(final String refFoto, final String tipo) {
// Nos posicionamos en el nodo tipo que nos venga por paraetro (musico o grupo)
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        // Ordenamos por uid dentro del nodo tipo en le que estabamos
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case ("musico"):
                            Musico mus = ds.getValue(Musico.class);
                            mus.setImagen(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + refFoto);
                            bd.child(ds.getKey()).setValue(mus);
                            break;
                        case ("grupo"):
                            Grupo gr = ds.getValue(Grupo.class);
                            gr.setImagen(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + refFoto);
                            bd.child(ds.getKey()).setValue(gr);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void actualizarCancionPerfil(final String refCancion, final String tipo) {
// Nos posicionamos en el nodo tipo que nos venga por paraetro (musico o grupo)
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        // Ordenamos por uid dentro del nodo tipo en le que estabamos
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case ("musico"):
                            Musico mus = ds.getValue(Musico.class);
                            mus.setAudio(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + refCancion);
                            bd.child(ds.getKey()).setValue(mus);

                            break;
                        case ("grupo"):
                            Grupo gr = ds.getValue(Grupo.class);
                            gr.setAudio(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + refCancion);
                            bd.child(ds.getKey()).setValue(gr);

                            break;
                    }
                    comprobacionAudioUsuario(tipo, VentanaInicialApp.a.getApplicationContext());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static void guardarURL(final String tipo, final String tipoRed, final String urlRedSocial) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList redes;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Musico mus = null;
                    Grupo grup = null;
                    switch (tipo) {
                        case ("musico"):
                            mus = data.getValue(Musico.class);
                            redes = mus.getRedsocial();
                            break;
                        case ("grupo"):
                            grup = data.getValue(Grupo.class);
                            redes = grup.getRedsocial();
                            break;
                    }

                    switch (tipoRed) {
                        case ("YouTube"):
                            redes.set(0, urlRedSocial);
                            break;
                        case ("FaceBook"):
                            redes.set(1, urlRedSocial);
                            break;
                        case ("InstaGram"):
                            redes.set(2, urlRedSocial);
                            break;
                    }

                    switch (tipo) {
                        case ("musico"):
                            bd.child(data.getKey()).setValue(mus);
                            break;
                        case ("grupo"):
                            bd.child(data.getKey()).setValue(grup);
                            break;
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Metodo para recuperar las redes sociales al inicio del Fragmento(opcion 0) ó
// para lanzar la URL de la red social en el navegador cuando se pulsa la Imagen de la red Social
    public static void recuperarURLredSocial(final String tipo, final int pos, final int opcion, final EditText facebook, final EditText youtube, final EditText instagram) {

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<String> urls = null;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case ("musico"):
                            Musico m = data.getValue(Musico.class);
                            urls = m.getRedsocial();
                            break;
                        case ("grupo"):
                            Grupo g = data.getValue(Grupo.class);
                            urls = g.getRedsocial();
                            break;
                    }
                    switch (opcion) {
                        case (0):
                            // Para la carga inicial del fragmento
                            for (String uri : urls) {
                                switch (uri) {
                                    case ("youtube"):
                                        youtube.setText(" AÑADE TU YOUTUBE");
                                        break;
                                    case ("instagram"):
                                        instagram.setText(" AÑADE TU INSTAGRAM");
                                        break;
                                    case ("facebook"):
                                        facebook.setText(" AÑADE TU FACEBOOK");
                                        break;
                                }
                            }
                            break;
                        case (1):
                            // Ir a la URL
                            BandsnArts.lanzarURLNavegador(urls.get(pos));
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    ///////////////////////////////////////////////////////////////STORAGE/////////////////////////////////////////////////////////////////////////////////
    public static void comprobacionAudioUsuario(final String tipo, final Context ctx) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String audio = null;
                StorageReference ref = FirebaseStorage.getInstance().getReference("audios");

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            audio = data.getValue(Musico.class).getAudio();
                            break;
                        case "grupo":
                            audio = data.getValue(Grupo.class).getAudio();
                            break;
                    }
                    if (audio != null) {
                        ref.child(audio).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Log.d("AAAAA", "onComplete: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                                // PONER LA CANCION EN EL REPRODUCTOR
                                FragmentMultimedia.mediaPlayer = MediaPlayer.create(ctx, task.getResult());
                                FragmentMultimedia.mediaPlayer.seekTo(0);
                                FragmentMultimedia.mediaPlayer.setVolume(0.5f, 0.5f);
                                FragmentMultimedia.totalTime = FragmentMultimedia.mediaPlayer.getDuration();
                                FragmentMultimedia.positionBar.setMax(FragmentMultimedia.totalTime);
                                FragmentMultimedia.paraHilo = false;
                                FragmentMultimedia.hiloMusica = new Thread((Runnable) FragmentMultimedia.fragment);
                                FragmentMultimedia.hiloMusica.start();


                                if ((VentanaInicialApp.a).findViewById(R.id.btnPlayVMultimedia) != null) {
                                    (VentanaInicialApp.a).findViewById(R.id.btnPlayVMultimedia)
                                            .setBackgroundDrawable(ctx.getDrawable(R.drawable.play));
                                }


                                FragmentMultimedia.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {

                                        FragmentMultimedia.mediaPlayer.pause();
                                        (VentanaInicialApp.a).findViewById(R.id.btnPlayVMultimedia).setBackgroundDrawable(VentanaInicialApp.a.getApplicationContext().getDrawable(R.drawable.play));

                                    }
                                });

                            }

                        });


                    } else {
                        // OCULTAR REPRODUCTOR
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static void accesoFotoPerfil(final String tipo, final ImageView vista, final Context context, String uid) {
        // Nos posicionamos en el nodo segun el tipo
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = null;
        q = bd.orderByChild("uid").equalTo(uid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String img = null;
                StorageReference ref = FirebaseStorage.getInstance().getReference("imagenes");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            img = data.getValue(Musico.class).getImagen();
                            break;
                        case "grupo":
                            img = data.getValue(Grupo.class).getImagen();
                            break;
                    }
                    ref.child(img).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (!VentanaInicialApp.a.isFinishing()) {
                                try {
                                    Glide.with(context).load(task.getResult()).override(200, 200).into(vista);
                                } catch (IllegalArgumentException | RuntimeExecutionException ex) {
                                    System.out.println("Cerro antes de tiempo de carga");
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static void accesoFotoPerfilRecycler(final ImageView vista, final Context context, Object o) {

        String img;
        StorageReference ref = FirebaseStorage.getInstance().getReference("imagenes");

        if (o instanceof Musico) {
            img = ((Musico) o).getImagen();

        } else {
            img = ((Grupo) o).getImagen();

        }
        try {
            ref.child(img).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (!VentanaInicialApp.a.isFinishing()) {

                        Glide.with(context).load(task.getResult()).override(200, 200).into(vista);

                    }
                }
            });
        } catch (IllegalArgumentException | RuntimeExecutionException ex) {
            System.out.println("Cerro antes de tiempo de carga");
        }

    }


    public static void almacenarFotoPerfil(final View ctx, Uri uri, final ImageView imageProgressView) {
        // Nos posicionamos en el nodo de imagenes del storage
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        Uri file;
        file = uri;
        StorageReference referenceFoto = storage.child("imagenes/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
        UploadTask uploadTask = referenceFoto.putFile(file);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                //FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                //FragmentMiPerfil.bottomTools.setBackgroundColor(ctx.getContext().getColor(R.color.md_light_green_600));
                imageProgressView.setVisibility(View.INVISIBLE);
                FragmentVerMiPerfil.miFABGuardarRechazar.setVisibility(View.VISIBLE);
                ((Activity) ctx.getContext()).findViewById(R.id.sv_fragment_v_perfil).setVisibility(View.VISIBLE);
                ((Activity) ctx.getContext()).findViewById(R.id.floatingBPerfil).setVisibility(View.VISIBLE);
                ((Activity) ctx.getContext()).findViewById(R.id.vermiperfil).setBackgroundColor(ctx.getContext().getResources().getColor(R.color.md_white_1000));
                Toast.makeText(ctx.getContext(), "No pudo subirse la foto con exito pruebe su conexión a la red.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // METODO PARA GUARDAR EL EL STORAGE LA FOTO DE PERFIL
                BDBAA.actualizarFotoPerfil(taskSnapshot.getMetadata().getName(), PreferenceManager.getDefaultSharedPreferences(ctx.getContext()).getString("tipo", ""));
                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(0)).commit();
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil");
                BDBAA.cargarDatosPerfil(ctx, PreferenceManager.getDefaultSharedPreferences(ctx.getContext()).getString("tipo", ""));
                BDBAA.accesoFotoPerfil(PreferenceManager.getDefaultSharedPreferences(ctx.getContext()).getString("tipo", ""), VentanaInicialApp.fotoPerfil, ctx.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                AnimationDrawable animationDrawable;
                imageProgressView.setBackgroundResource(R.drawable.gif);
                animationDrawable = (AnimationDrawable) imageProgressView.getBackground();
                animationDrawable.start();
                imageProgressView.setVisibility(View.VISIBLE);
                FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                FragmentVerMiPerfil.miFABGuardarRechazar.setVisibility(View.INVISIBLE);
                //FragmentMiPerfil.bottomTools.setBackgroundColor(ctx.getContext().getColor(R.color.md_black_1000));
                ((Activity) ctx.getContext()).findViewById(R.id.sv_fragment_v_perfil).setVisibility(View.INVISIBLE);
                ((Activity) ctx.getContext()).findViewById(R.id.floatingBPerfil).setVisibility(View.INVISIBLE);
                ((Activity) ctx.getContext()).findViewById(R.id.vermiperfil).setBackground(ctx.getContext().getDrawable(R.drawable.fondonegro));
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        });

    }

}
