package com.example.pc.bandsnarts.BBDD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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

import com.example.pc.bandsnarts.Adaptadores.AdaptadorContactos;
import com.example.pc.bandsnarts.Adaptadores.AdaptadorMensajes;
import com.example.pc.bandsnarts.Adaptadores.HolderMensajes;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterAnuncioPropio;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterGrupo;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterLocales;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterSalas;
import com.example.pc.bandsnarts.Contactos.FragmentContactos;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.Visitar_Perfil;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMensajes;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentAnuncios;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAñadirAnuncio;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogDescartarCambios;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentMultimedia;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentVerMiPerfil;
import com.example.pc.bandsnarts.FragmentsTabLayoutsInicio.FragmentMusicosTabInicio;
import com.example.pc.bandsnarts.Objetos.Anuncio;
import com.example.pc.bandsnarts.Objetos.Grupo;
import com.example.pc.bandsnarts.Objetos.KeyChat;
import com.example.pc.bandsnarts.Objetos.Local;
import com.example.pc.bandsnarts.Objetos.Mensajes2;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.Objetos.Sala;
import com.example.pc.bandsnarts.R;
import com.github.library.bubbleview.BubbleDrawable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.pc.bandsnarts.Container.BandsnArts.encontrado;
import static com.example.pc.bandsnarts.Container.BandsnArts.keyP1;
import static com.example.pc.bandsnarts.Container.BandsnArts.keyP2;
import static com.example.pc.bandsnarts.Container.BandsnArts.nombre;
import static com.facebook.FacebookSdk.getApplicationContext;

public class BDBAA extends AppCompatActivity {


    public BDBAA() {
    }

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
                                final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
                                switch (tipo) {
                                    case "musico":
                                        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "musico").commit();
                                        Musico mus = new Musico(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, BandsnArts.quitarSaltos(nombre).trim(), sexo, estilo, instrumento, descripcion.trim());
                                        bd.child(bd.push().getKey()).setValue(mus);
                                        break;
                                    case "grupo":
                                        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("tipo", "grupo").commit();
                                        Grupo gru = new Grupo(FirebaseAuth.getInstance().getCurrentUser().getUid(), imagen, BandsnArts.quitarSaltos(nombre).trim(), estilo, BandsnArts.quitarSaltos(descripcion).trim());
                                        bd.child(bd.push().getKey()).setValue(gru);
                                        break;
                                }
                                FirebaseDatabase.getInstance().getReference("uids").child(bd.push().getKey()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                context.startActivity(new Intent(context, VentanaSliderParteDos.class));
                                ((Activity) context).setResult(BandsnArts.CODIGO_DE_REGISTRO_RED_SOCIAL);
                                ((Activity) context).finish();
                                Log.d("INSERTADO", "Insertado con exito");
                                FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {

                                                } else {
                                                    task.getException();
                                                }
                                            }
                                        });


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

    public static void borrarPerfil(Context context, final String uid, int op) {
        switch (op) {
            case 0:

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference cancion = storageRef.child("audios/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".mpeg");
                BDBAA.eliminarCancion(cancion);

                StorageReference foto = storageRef.child("imagenes/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
                BDBAA.eliminarCancion(foto);
                final DatabaseReference bda = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(context).getString("tipo", ""));
                Query qa = bda.orderByChild("uid").equalTo(uid);
                Log.d("UID", "onDataChange: " + uid);
                qa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            bda.child(data.getKey()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            case 1:
                final DatabaseReference bd = FirebaseDatabase.getInstance().getReference("uids");
                Query q = bd.orderByChild("uid").equalTo(uid);
                Log.d("UID", "onDataChange: " + uid);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            bd.child(data.getKey()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }
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
                    Log.d("Encontrado", "onDataChange: " + encontrado);
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
                }
                if (encontrado) {
                    // Es musico, lo guardamos en preferencias
                    PreferenceManager.getDefaultSharedPreferences(cont).edit().putString("tipo", "musico").commit();
                } else {
                    // Es grupo, lo guardamos en preferencias
                    PreferenceManager.getDefaultSharedPreferences(cont).edit().putString("tipo", "grupo").commit();
                }
                //BandsnArts.recuperarToken(PreferenceManager.getDefaultSharedPreferences(cont).getString("tipo", ""));
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
                            BandsnArts.nomChat = data.getValue(Musico.class).getNombre();
                            BandsnArts.imgChat = data.getValue(Musico.class).getImagen();
                            break;
                        case "grupo":
                            nombre.setText(data.getValue(Grupo.class).getNombre());
                            accesoFotoPerfil("grupo", fotoPerfil, context, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            BandsnArts.nomChat = data.getValue(Grupo.class).getNombre();
                            BandsnArts.imgChat = data.getValue(Grupo.class).getImagen();
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
            final String pos
            , final View vista, final String tipo
            , final ImageView imagenPerfil, final TextView nombreUsuario
            , final TextView cajaEstilo, final TextView cajaDescripcion
            , final TextView cajaProvincia, final TextView cajaLocalidad
            , final TextView tvSexo, final TextView tvInstrumento
            , final TextView cajaSexo, final TextView[] instrumnetos
            , final View ll_inst1, final View ll_inst2
            , final View ll_instsec1, final View ll_instsec2, final View ll_instsec3) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        System.out.println("UID------------------------------------>" + pos);
        Query q = bd.orderByChild("uid").equalTo(pos);
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            // Recuperamos y cargamos los datos del Musico
                            // Asignamos la UID a la varible static declarada en Visitar_Perfil
                            Visitar_Perfil.UIDvisited = musico.getUid();
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
                            // Asignamos la UID a la varible static declarada en Visitar_Perfil
                            Visitar_Perfil.UIDvisited = grupo.getUid();
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


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarMultimediaVisitarPerfil(final CardView multimedia, final String tipo, final String pos) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(pos);
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            // Recuperamos y cargamos los datos del Musico
                            if (musico.getAudio() == null) {
                                multimedia.setVisibility(View.GONE);

                            }
                            break;
                        case "grupo":

                            Grupo grupo = data.getValue(Grupo.class);
                            // Recuperamos y cargamos los datos del Musico
                            if (grupo.getAudio() == null) {
                                multimedia.setVisibility(View.GONE);
                            }
                            break;
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cargarRedesSocialesViaitarPerfil(final CardView cvRedes, final String tipo
            , final String pos, final LinearLayout linearYoutube, final LinearLayout linearFaceBook, final LinearLayout linearInstagram) {

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(pos);
        q.addListenerForSingleValueEvent(new ValueEventListener() {


            ArrayList<String> redes = null;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            Musico musico = data.getValue(Musico.class);
                            // Recuperamos y cargamos los datos del Musico
                            redes = musico.getRedsocial();
                            break;
                        case "grupo":

                            Grupo grupo = data.getValue(Grupo.class);
                            // Recuperamos y cargamos los datos del Musico
                            redes = grupo.getRedsocial();
                            break;
                    }

                    if (redes.get(0).equals("youtube") && redes.get(1).equals("facebook") && redes.get(2).equals("instagram")) {
                        cvRedes.setVisibility(View.GONE);
                    } else {
                        for (String red : redes) {
                            switch (red) {
                                case ("youtube"):
                                    linearYoutube.setVisibility(View.GONE);
                                    break;
                                case ("facebook"):
                                    linearFaceBook.setVisibility(View.GONE);
                                    break;
                                case ("instagram"):
                                    linearInstagram.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    }
                    break;

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

                            // Instrumentos
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

    public static void cargarAnuncios(final ArrayList lista, final RecyclerView recyclerView, final Activity activity, String uid, final String tipo, final int op, View view) {
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
        if(lista.isEmpty()){
            // IMAGEN DE FONDO PARA CUANDO EL USUARIO NO TIENE ANUNCIOS
            view.setBackground(VentanaInicialApp.a.getResources().getDrawable(R.drawable.lennon));
        }
    }

    public static void cargarVisitarAnuncios(final String tipo, final RecyclerView recyclerView, final Activity activity, final int op, final String position, final View view) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(position);
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList lista;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    switch (tipo) {
                        case "musico":
                            lista = data.getValue(Musico.class).getAnuncio();
                            break;
                        case "grupo":
                            lista = data.getValue(Grupo.class).getAnuncio();
                            break;
                    }
                }
                RecyclerAdapterAnuncioPropio adapter = new RecyclerAdapterAnuncioPropio(activity.getApplicationContext(), lista, op);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);

                if(lista.isEmpty()){
                    // IMAGEN DE FONDO PARA CUANDO EL USUARIO NO TIENE ANUNCIOS
                    view.setBackground(VentanaInicialApp.a.getResources().getDrawable(R.drawable.lennon));
                }
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
                        BandsnArts.UID_GRUPO.clear();
                        RecyclerAdapterGrupo adapterG = new RecyclerAdapterGrupo(activity.getApplicationContext(), lista);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapterG);
                        break;
                    case "musico":
                        BandsnArts.UID_MUSICO.clear();
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

    public static void actualizarCancionPerfil(final String refCancion, final String tipo, final View view) {
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
                    comprobacionAudioUsuario(tipo, VentanaInicialApp.a.getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getUid(), view);
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


    //Creación nodos chat
    public static void nuevoMensaje(final String KEYCHAT, final String mens) {
        final DatabaseReference bd;
        System.out.println(KEYCHAT);
        bd = FirebaseDatabase.getInstance().getReference("keychat");
        Query q = bd.orderByChild("key").equalTo(KEYCHAT);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    KeyChat chat = data.getValue(KeyChat.class);
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(KEYCHAT.split("-")[0])) {
                        chat.setNotificaciones(chat.getNotificaciones().split("-")[0] + "-true");
                    } else {
                        chat.setNotificaciones("true-" + chat.getNotificaciones().split("-")[1]);
                    }
                    chat.getHistorcoMensajes().add(new Mensajes2(mens, BandsnArts.nomChat, BandsnArts.imgChat, new SimpleDateFormat("HH:mm").format(new Date()), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                    bd.child(data.getKey()).setValue(chat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //ComprobarChatexistente
    public static void comprobarChatexistente(final String uid, final String pos, final String tipo, final Musico musico, final Grupo grupo, final DatabaseReference bd, final DataSnapshot data) {

        final DatabaseReference bda = FirebaseDatabase.getInstance().getReference("keychat");
        Query q = bda;
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    KeyChat keyChat = ds.getValue(KeyChat.class);
                    if ((uid + "-" + pos).equals(keyChat.getKey())) {
                        BandsnArts.encontrado = true;
                        BandsnArts.keyP1 = uid;
                        BandsnArts.keyP2 = musico.getUid();
                        BandsnArts.KEYCHAT = uid + "-" + pos;
                        break;
                    } else if ((pos + "-" + uid).equals(keyChat.getKey())) {
                        BandsnArts.encontrado = true;
                        BandsnArts.keyP1 = grupo.getUid();
                        BandsnArts.keyP2 = uid;
                        BandsnArts.KEYCHAT = pos + "-" + uid;
                        break;
                    }
                }

                switch (tipo) {
                    case "musico":
                        if (!BandsnArts.encontrado) {
                            BandsnArts.keyP1 = musico.getUid();
                            BandsnArts.keyP2 = uid;
                            BandsnArts.KEYCHAT = BandsnArts.keyP1 + "-" + uid;
                        }
                        BandsnArts.nombre = musico.getNombre();
                        BandsnArts.img = musico.getImagen();
                        musico.getKeyChat().add(BandsnArts.KEYCHAT);
                        bd.child(data.getKey()).setValue(musico);
                        break;
                    case "grupo":
                        if (!BandsnArts.encontrado) {
                            BandsnArts.keyP1 = grupo.getUid();
                            BandsnArts.keyP2 = uid;
                            BandsnArts.KEYCHAT = BandsnArts.keyP1 + "-" + uid;
                        }
                        BandsnArts.nombre = grupo.getNombre();
                        BandsnArts.img = grupo.getImagen();
                        grupo.getKeyChat().add(BandsnArts.KEYCHAT);
                        bd.child(data.getKey()).setValue(grupo);
                        break;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Comprobar conversación existente
    public static void comprobarConversacionExistente(final String tipo, final String uid, final String pos, final Activity ctx) {
        final DatabaseReference bd;
        BandsnArts.nombre = "";
        BandsnArts.img = "";
        BandsnArts.encontrado = false;
        bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(pos);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (final DataSnapshot data : dataSnapshot.getChildren()) {
                        switch (tipo) {
                            case "musico":
                                final Musico musico = data.getValue(Musico.class);
                                if (musico.getKeyChat().isEmpty()) {
                                    BDBAA.comprobarChatexistente(uid, pos, "musico", musico, null, bd, data);
                                } else {
                                    for (String key : musico.getKeyChat()) {
                                        BandsnArts.keyP1 = key.split("-")[0];
                                        BandsnArts.keyP2 = key.split("-")[1];
                                        if (uid.equals(BandsnArts.keyP1) || uid.equals(BandsnArts.keyP2)) {
                                            BandsnArts.KEYCHAT = key;
                                            BandsnArts.nombre = musico.getNombre();
                                            BandsnArts.encontrado = true;
                                            break;
                                        }
                                    }
                                    if (!BandsnArts.encontrado) {
                                        BDBAA.comprobarChatexistente(uid, pos, "musico", musico, null, bd, data);
                                    }
                                }

                                break;
                            case "grupo":
                                final Grupo grupo = data.getValue(Grupo.class);
                                if (grupo.getKeyChat().isEmpty()) {
                                    BDBAA.comprobarChatexistente(uid, pos, "grupo", null, grupo, bd, data);
                                } else {
                                    for (String key : grupo.getKeyChat()) {
                                        BandsnArts.keyP1 = key.split("-")[0];
                                        BandsnArts.keyP2 = key.split("-")[1];
                                        if (uid.equals(BandsnArts.keyP1) || uid.equals(BandsnArts.keyP2)) {
                                            BandsnArts.KEYCHAT = key;
                                            BandsnArts.nombre = grupo.getNombre();
                                            BandsnArts.encontrado = true;
                                            break;
                                        }
                                    }
                                    if (!BandsnArts.encontrado) {
                                        BDBAA.comprobarChatexistente(uid, pos, "grupo", null, grupo, bd, data);
                                    }
                                }
                                break;
                        }
                    }
                    if (!BandsnArts.encontrado) {
                        BandsnArts.encontrado = false;
                        Query q = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).orderByChild("uid").equalTo(uid);
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                BandsnArts.encontrado = false;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    switch (PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")) {
                                        case "musico":
                                            Musico musico = data.getValue(Musico.class);
                                            for (String key : musico.getKeyChat()) {
                                                if (BandsnArts.KEYCHAT.equals(key)) {
                                                    BandsnArts.encontrado = true;
                                                    break;
                                                }
                                            }
                                            if (!BandsnArts.encontrado) {
                                                musico.getKeyChat().add(BandsnArts.KEYCHAT);
                                                BandsnArts.nombre = BandsnArts.nombre + "-" + musico.getNombre();
                                                BandsnArts.img = BandsnArts.img + "-" + musico.getImagen();
                                                FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).child(data.getKey()).setValue(musico);
                                            }
                                            break;
                                        case "grupo":
                                            Grupo grupo = data.getValue(Grupo.class);
                                            for (String key : grupo.getKeyChat()) {
                                                if (BandsnArts.KEYCHAT.equals(key)) {
                                                    BandsnArts.encontrado = true;
                                                    break;
                                                }
                                            }
                                            if (!BandsnArts.encontrado) {
                                                grupo.getKeyChat().add(BandsnArts.KEYCHAT);
                                                BandsnArts.nombre = BandsnArts.nombre + "-" + grupo.getNombre();
                                                BandsnArts.img = BandsnArts.img + "-" + grupo.getImagen();
                                                FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).child(data.getKey()).setValue(grupo);
                                            }
                                            break;
                                    }
                                }

                                final DatabaseReference bd = FirebaseDatabase.getInstance().getReference("keychat");
                                bd.orderByChild("key").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        BandsnArts.encontrado = false;
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            KeyChat keyChat = ds.getValue(KeyChat.class);
                                            if (BandsnArts.KEYCHAT.equals(keyChat.getKey())) {
                                                BandsnArts.encontrado = true;
                                                break;
                                            }
                                        }
                                        if (!BandsnArts.encontrado) {
                                            bd.child(bd.push().getKey()).setValue(new KeyChat(BandsnArts.img, BandsnArts.nombre, (BandsnArts.keyP1 + "-" + uid)));
                                        }
                                        VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMensajes()).commit();
                                        ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle(BandsnArts.nombre.split("-")[0]);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        System.out.println(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", ""));
                        Query q = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).orderByChild("uid").equalTo(uid);
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                BandsnArts.encontrado = false;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    switch (PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")) {
                                        case "musico":
                                            Musico musico = data.getValue(Musico.class);
                                            for (String key : musico.getKeyChat()) {
                                                if (BandsnArts.KEYCHAT.equals(key)) {
                                                    BandsnArts.encontrado = true;
                                                    break;
                                                }
                                            }
                                            if (!BandsnArts.encontrado) {
                                                musico.getKeyChat().add(BandsnArts.KEYCHAT);
                                                BandsnArts.nombre = BandsnArts.nombre + "-" + musico.getNombre();
                                                BandsnArts.img = BandsnArts.img + "-" + musico.getImagen();
                                                FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).child(data.getKey()).setValue(musico);
                                            }
                                            break;
                                        case "grupo":
                                            Grupo grupo = data.getValue(Grupo.class);
                                            for (String key : grupo.getKeyChat()) {
                                                if (BandsnArts.KEYCHAT.equals(key)) {
                                                    BandsnArts.encontrado = true;
                                                    break;
                                                }
                                            }
                                            if (!BandsnArts.encontrado) {
                                                grupo.getKeyChat().add(BandsnArts.KEYCHAT);
                                                BandsnArts.nombre = BandsnArts.nombre + "-" + grupo.getNombre();
                                                BandsnArts.img = BandsnArts.img + "-" + grupo.getImagen();
                                                FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(ctx).getString("tipo", "")).child(data.getKey()).setValue(grupo);
                                            }
                                            break;
                                    }
                                    VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentMensajes()).commit();
                                    ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle(BandsnArts.nombre.split("-")[0]);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                } catch (ConcurrentModificationException ex) {
                    System.out.println("Concurrente");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /**
     * @param databaseReference
     * @param key
     */
    public static void escuchaChat(final DatabaseReference databaseReference, final String key) {
        //agregar hijo al nodo
        try {
            databaseReference.removeEventListener(BandsnArts.bdbaa);
        } catch (NullPointerException ex) {
        }

        BandsnArts.bdbaa = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    HashMap hashMapm = (HashMap) dataSnapshot.getValue();
                    Collection lista = hashMapm.values();
                    for (Object keyChat : lista.toArray()) {
                        System.out.println(keyChat.getClass());
                        if (keyChat instanceof HashMap) {
                            String chat = (String) ((HashMap) keyChat).get("key");
                            System.out.println(key + "  <-->   " + chat);
                            if (key.equals(chat)) {

                                ArrayList listado = (ArrayList) ((HashMap) keyChat).get("historcoMensajes");
                                HashMap mapa = null;
                                for (Object o : listado) {
                                    if (o instanceof HashMap) {
                                        mapa = (HashMap) o;
                                    }
                                }
                                System.out.println(hashMapm);
                                Mensajes2 mens = new Mensajes2(mapa.get("mensaje").toString(), mapa.get("nombre").toString(), mapa.get("fotoPerfil").toString(), mapa.get("hora").toString(), mapa.get("uid").toString());
                                FragmentMensajes.adaptadorMensajes.addMensaje(mens);

                                FragmentMensajes.setScrollBar();
                                break;
                            }
                        }
                    }

                } catch (NullPointerException ex) {
                }
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


    //Recuperar conversación
    public static void recuperarConversaciones(final View view) {
        BandsnArts.alContactos.clear();


        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", ""));
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "")) {
                        case "musico":
                            Musico mus = ds.getValue(Musico.class);
                            BandsnArts.alContactosAUX = mus.getKeyChat();
                            break;

                        case "grupo":
                            Grupo gru = ds.getValue(Grupo.class);
                            BandsnArts.alContactosAUX = gru.getKeyChat();
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bd = FirebaseDatabase.getInstance().getReference("keychat");
        q = bd.orderByChild("key");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    final KeyChat keyChat = data.getValue(KeyChat.class);
                    for (String key : BandsnArts.alContactosAUX) {
                        if (key.equals(keyChat.getKey())) {
                            BandsnArts.alContactos.add(keyChat);
                            break;
                        }
                    }
                }

                BandsnArts.adaptadorContactos = new AdaptadorContactos(view.getContext(), BandsnArts.alContactos);
                BandsnArts.rvContactos.setLayoutManager(new LinearLayoutManager(view.getContext()));
                BandsnArts.rvContactos.setAdapter(BandsnArts.adaptadorContactos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void eliminarContacto(final View view, final KeyChat keyChat) {

        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", ""));
        Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean bandera;
            Musico mus;
            Grupo gru;
            String clave;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "")) {
                            case "musico":
                                mus = ds.getValue(Musico.class);
                                for (String key : mus.getKeyChat()) {
                                    System.out.println(keyChat.getKey());
                                    System.out.println(key);
                                    bandera = keyChat.getKey().equals(key);
                                    if (bandera) {
                                        clave = ds.getKey();
                                        mus.getKeyChat().remove(key);
                                    }
                                }
                                bd.child(ds.getKey()).setValue(mus);
                                break;
                            case "grupo":
                                gru = ds.getValue(Grupo.class);
                                for (String key : gru.getKeyChat()) {
                                    if (key.equals(keyChat.getKey())) {
                                        clave = ds.getKey();
                                        gru.getKeyChat().remove(key);
                                    }
                                }
                                bd.child(ds.getKey()).setValue(gru);
                                break;
                        }

                    }
                } catch (ConcurrentModificationException ex) {
                    switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "")) {
                        case ("musico"):
                            bd.child(clave).setValue(mus);
                            break;
                        case ("grupo"):
                            bd.child(clave).setValue(gru);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void recuperarMensajes(final View view, final String KEYCHAT) {
        final DatabaseReference bd = FirebaseDatabase.getInstance().getReference("keychat");
        Query q = bd.orderByChild("key").equalTo(KEYCHAT);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Mensajes2> lista = new ArrayList<>();
            KeyChat keyChat;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot datas : dataSnapshot.getChildren()) {
                    keyChat = datas.getValue(KeyChat.class);
                    lista = keyChat.getHistorcoMensajes();

                    DatabaseReference bda = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", ""));
                    Query qa = bda.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    qa.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "")) {
                                    case "musico":
                                        Musico mus = data.getValue(Musico.class);
                                        for (final Mensajes2 men : lista) {
                                            if (men.getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                if (!men.getNombre().equals(mus.getNombre())) {
                                                    men.setNombre(mus.getNombre());

                                                }
                                                if (!men.getFotoPerfil().equals(mus.getImagen())) {
                                                    men.setFotoPerfil(mus.getImagen());

                                                }

                                                if (keyChat.getKey().split("-")[0].equals(men.getUID())) {
                                                    keyChat.setNombre(mus.getNombre() + "-" + keyChat.getNombre().split("-")[1]);
                                                    keyChat.setImg(mus.getImagen() + "-" + keyChat.getImg().split("-")[1]);
                                                } else {
                                                    keyChat.setNombre(keyChat.getNombre().split("-")[0] + "-" + mus.getNombre());
                                                    keyChat.setImg(keyChat.getImg().split("-")[0] + "-" + mus.getImagen());
                                                }
                                                keyChat.setHistorcoMensajes(lista);
                                                bd.child(datas.getKey()).setValue(keyChat);


                                            }

                                        }
                                        break;
                                    case "grupo":

                                        Grupo gru = data.getValue(Grupo.class);
                                        for (final Mensajes2 men : lista) {
                                            if (men.getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                if (!men.getNombre().equals(gru.getNombre())) {
                                                    men.setNombre(gru.getNombre());

                                                }
                                                if (!men.getFotoPerfil().equals(gru.getImagen())) {
                                                    men.setFotoPerfil(gru.getImagen());

                                                }

                                                if (keyChat.getKey().split("-")[0].equals(men.getUID())) {
                                                    keyChat.setNombre(gru.getNombre() + "-" + keyChat.getNombre().split("-")[1]);
                                                    keyChat.setImg(gru.getImagen() + "-" + keyChat.getImg().split("-")[1]);
                                                } else {
                                                    keyChat.setNombre(keyChat.getNombre().split("-")[0] + "-" + gru.getNombre());
                                                    keyChat.setImg(keyChat.getImg().split("-")[0] + "-" + gru.getImagen());
                                                }
                                                keyChat.setHistorcoMensajes(lista);
                                                bd.child(datas.getKey()).setValue(keyChat);

                                            }


                                        }
                                        break;
                                }
                            }

                            BDBAA.escuchaChat(bd.getParent(), BandsnArts.KEYCHAT);
                            FragmentMensajes.adaptadorMensajes = new AdaptadorMensajes(view.getContext(), lista);
                            BandsnArts.rvMensajes.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            BandsnArts.rvMensajes.setAdapter(FragmentMensajes.adaptadorMensajes);
                            FragmentMensajes.adaptadorMensajes.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                @Override
                                public void onItemRangeInserted(int positionStart, int itemCount) {
                                    super.onItemRangeInserted(positionStart, itemCount);
                                    view.setVerticalScrollBarEnabled(true);
                                }
                            });
                            FragmentMensajes.setScrollBar();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // Metodo para recuperar las redes sociales al inicio del Fragmento(opcion 0) ó
// para lanzar la URL de la red social en el navegador cuando se pulsa la Imagen de la red Social
    public static void recuperarURLredSocial(final String tipo, final int pos, final int opcion, final EditText facebook, final EditText youtube, final EditText instagram, String uid) {

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(uid);
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
                                        youtube.setText(" AÑADE TU YOUTUBE  --->");
                                        break;
                                    case ("instagram"):
                                        instagram.setText(" AÑADE TU INSTAGRAM  --->");
                                        break;
                                    case ("facebook"):
                                        facebook.setText(" AÑADE TU FACEBOOK  --->");
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

    public static void busqueda(final int posEstilo, final int posInst, final int posSexo, final int posProvincia, final int posLocalidad, final String tipo) {
        final int[] ind = {-1, -1, -1, -1, -1};
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = null;
        switch (tipo) {
            case ("musico"):
                if (posSexo != 0) {
                    query = reference.child(tipo).orderByChild("sexo").equalTo(VentanaInicialApp.a.getResources().getStringArray(R.array.sexo)[posSexo]);
                    ind[0] = 0;
                }
                if (posInst != 0) {
                    query = reference.child(tipo).orderByChild("instrumento/0").equalTo(VentanaInicialApp.a.getResources().getStringArray(R.array.instrumentos)[posInst]);
                    ind[2] = 0;
                }
            case ("grupo"):
                if (posEstilo != 0) {
                    System.out.println("                -------------------->" + VentanaInicialApp.a.getResources().getStringArray(R.array.estiloMusical)[posEstilo]);
                    query = reference.child(tipo).orderByChild("estilo").equalTo(VentanaInicialApp.a.getResources().getStringArray(R.array.estiloMusical)[posEstilo]);
                    ind[1] = 0;
                }
                if (posProvincia != 0) {
                    query = reference.child(tipo).orderByChild("provincia").equalTo(VentanaInicialApp.a.getResources().getStringArray(R.array.provincias)[posProvincia]);
                    ind[3] = 0;
                }
                if (posLocalidad != 0) {
                    query = reference.child(tipo).orderByChild("localidad").equalTo(BandsnArts.localidades[posLocalidad].toString());
                    ind[4] = 0;
                }
                break;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList listado = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listado.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (tipo.equals("musico")) {
                        Musico mus = data.getValue(Musico.class);
                        listado.add(mus);
                    } else {
                        Grupo gr = data.getValue(Grupo.class);
                        listado.add(gr);
                    }
                }
                try {
                    for (Object item : listado) {
                        if (item instanceof Musico) {
                            Musico item1 = (Musico) item;
                            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(item1.getUid())) {
                                listado.remove(item);
                            } else {
                                // Si ha seleccionado la localidad y NO coincide con la localidad del musico
                                if (ind[4] == 0 && !BandsnArts.localidades[posLocalidad].toString().equals(item1.getLocalidad())) {
                                    listado.remove(item);
                                } else if ((ind[3] == 0) && !VentanaInicialApp.a.getResources().getStringArray(R.array.provincias)[posProvincia].equals(item1.getProvincia())) {
                                    listado.remove(item);
                                    //  Si ha seleccionado un instrumento y NO coincide con el instrumento principal del musico por el que estamos pasando
                                } else if ((ind[2] == 0) && !VentanaInicialApp.a.getResources().getStringArray(R.array.instrumentos)[posInst].equals(item1.getInstrumento().get(0))) {
                                    System.out.println("                  INSTRUMENTO     " + VentanaInicialApp.a.getResources().getStringArray(R.array.instrumentos)[posInst]);
                                    listado.remove(item);
                                    //  Si ha seleccionado un estilo y NO coincide con el estilo del musico por el que estamos pasando
                                } else if (ind[1] == 0 && !VentanaInicialApp.a.getResources().getStringArray(R.array.estiloMusical)[posEstilo].equals(item1.getEstilo())) {
                                    listado.remove(item);
                                    //  Si ha seleccionado un sexo y NO coincide con el sexo del musico por el que estamos pasando
                                } else if (ind[0] == 0 && !VentanaInicialApp.a.getResources().getStringArray(R.array.sexo)[posSexo].equals(item1.getSexo())) {
                                    listado.remove(item);
                                }
                            }
                        } else {
                            Grupo item1 = (Grupo) item;
                            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(item1.getUid())) {
                                listado.remove(item);
                            } else {
                                // Si ha seleccionado la localidad
                                if (ind[4] == 0 && !BandsnArts.localidades[posLocalidad].toString().equals(item1.getLocalidad())) {
                                    listado.remove(item);
                                } else if ((ind[3] == 0) && !VentanaInicialApp.a.getResources().getStringArray(R.array.provincias)[posProvincia].equals(item1.getProvincia())) {
                                    listado.remove(item);
                                    //  Si ha seleccionado un instrumento y NO coincide con el instrumento principal del musico por el que estamos pasando
                                } else if (ind[1] == 0 && !VentanaInicialApp.a.getResources().getStringArray(R.array.estiloMusical)[posEstilo].equals(item1.getEstilo())) {
                                    listado.remove(item);
                                    //  Si ha seleccionado un sexo y NO coincide con el sexo del musico por el que estamos pasando
                                }
                            }
                        }
                    }
                } catch (ConcurrentModificationException e) {

                }

                Collections.sort(listado, new Comparator<Musico>() {
                    @Override
                    public int compare(Musico o1, Musico o2) {
                        if (o1.getNombre().compareTo(o2.getNombre()) > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                switch (tipo) {
                    case ("musico"):
                        // Limpiamos el Arrreglo de UID para la carga del listado filtrado
                        BandsnArts.UID_MUSICO.clear();
                        RecyclerAdapterMusico adapterM = new RecyclerAdapterMusico(VentanaInicialApp.a.getApplicationContext(), listado);
                        ((RecyclerView) VentanaInicialApp.a.findViewById(R.id.recyclerMusicos)).setLayoutManager(new LinearLayoutManager(VentanaInicialApp.a));
                        ((RecyclerView) VentanaInicialApp.a.findViewById(R.id.recyclerMusicos)).setAdapter(adapterM);
                        break;
                    case ("grupo"):
                        // Limpiamos el Arrreglo de UID para la carga del listado filtrado
                        BandsnArts.UID_GRUPO.clear();
                        RecyclerAdapterGrupo adapterG = new RecyclerAdapterGrupo(VentanaInicialApp.a.getApplicationContext(), listado);
                        ((RecyclerView) VentanaInicialApp.a.findViewById(R.id.recyclerGrupos)).setLayoutManager(new LinearLayoutManager(VentanaInicialApp.a));
                        ((RecyclerView) VentanaInicialApp.a.findViewById(R.id.recyclerGrupos)).setAdapter(adapterG);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    ///////////////////////////////////////////////////////////////STORAGE/////////////////////////////////////////////////////////////////////////////////
    public static void comprobacionAudioUsuario(final String tipo, final Context ctx, String uid, final View view) {
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference(tipo);
        Query q = bd.orderByChild("uid").equalTo(uid);

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

                                // PONER LA CANCION EN EL REPRODUCTOR
                                BandsnArts.mediaPlayer = MediaPlayer.create(ctx, task.getResult());
                                BandsnArts.mediaPlayer.seekTo(0);
                                BandsnArts.mediaPlayer.setVolume(0.5f, 0.5f);
                                BandsnArts.totalTime = BandsnArts.mediaPlayer.getDuration();
                                BandsnArts.positionBar.setMax(BandsnArts.totalTime);
                                BandsnArts.paraHilo = false;
                                BandsnArts.hiloMusica = new Thread(new BandsnArts());
                                BandsnArts.hiloMusica.start();


                                if (view != null) {
                                    view.setBackgroundDrawable(ctx.getDrawable(R.drawable.play));
                                }


                                BandsnArts.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        BandsnArts.mediaPlayer.pause();
                                        view.setBackgroundDrawable(VentanaInicialApp.a.getApplicationContext().getDrawable(R.drawable.play));

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

    public static void accesoFotoNombrePerfilMensajes(final int op, final int pos, final ImageView vista, final TextView nombre, final Context context, String KEYCHAT, final LinearLayout bubbleLinearLayout) {
        // Nos posicionamos en el nodo segun el tipo
        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("keychat");
        Query q = bd.orderByChild("key").equalTo(KEYCHAT);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String img, nombreS = null;

                StorageReference ref = FirebaseStorage.getInstance().getReference("imagenes");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    img = data.getValue(KeyChat.class).getImg();
                    switch (op) {
                        case 0:
                            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.getValue(KeyChat.class).getKey().split("-")[0])) {
                                nombreS = data.getValue(KeyChat.class).getNombre().split("-")[1];
                                img = data.getValue(KeyChat.class).getImg().split("-")[1];
                            } else if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.getValue(KeyChat.class).getKey().split("-")[1])) {
                                nombreS = data.getValue(KeyChat.class).getNombre().split("-")[0];
                                img = data.getValue(KeyChat.class).getImg().split("-")[0];
                            }
                            break;

                        case 1:
                            try {

                                if (data.getValue(KeyChat.class).getHistorcoMensajes().get(pos).getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                    BDBAA.cargarbubble(1, bubbleLinearLayout);
                                } else {
                                    BDBAA.cargarbubble(0, bubbleLinearLayout);
                                }
                                nombreS = data.getValue(KeyChat.class).getHistorcoMensajes().get(pos).getNombre();
                                img = data.getValue(KeyChat.class).getHistorcoMensajes().get(pos).getFotoPerfil();
                            } catch (IndexOutOfBoundsException ex) {
                                try {
                                    if (data.getValue(KeyChat.class).getHistorcoMensajes().get(data.getValue(KeyChat.class).getHistorcoMensajes().size() - 1).getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        BDBAA.cargarbubble(1, bubbleLinearLayout);
                                    } else {
                                        BDBAA.cargarbubble(0, bubbleLinearLayout);
                                    }
                                    nombreS = data.getValue(KeyChat.class).getHistorcoMensajes().get(data.getValue(KeyChat.class).getHistorcoMensajes().size() - 1).getNombre();
                                    img = data.getValue(KeyChat.class).getHistorcoMensajes().get(data.getValue(KeyChat.class).getHistorcoMensajes().size() - 1).getFotoPerfil();
                                } catch (IndexOutOfBoundsException e) {
                                }
                            } catch (NullPointerException ex) {

                            }


                            break;
                    }


                    nombre.setText(nombreS);
                    ref.child(img).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (!VentanaInicialApp.a.isFinishing()) {
                                try {
                                    Glide.with(context).load(task.getResult()).override(100, 100).into(vista);
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

    public static void cargarbubble(int op, LinearLayout bubbleLinearLayout) {

        switch (op) {
            case 0:
                bubbleLinearLayout.setBackgroundColor(VentanaInicialApp.a.getResources().getColor(R.color.md_white_1000));
                break;
            case 1:
                bubbleLinearLayout.setBackgroundColor(VentanaInicialApp.a.getResources().getColor(R.color.md_light_green_A200));
                break;
        }
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

    public static void eliminarCancion(StorageReference cancion) {
        // Delete the file
        cancion.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                System.out.println("BORRADO CON EXITO!!!!!!!!!!!!");
                final DatabaseReference bd = FirebaseDatabase.getInstance().getReference(PreferenceManager.getDefaultSharedPreferences(VentanaInicialApp.a).getString("tipo", ""));
                Query q = bd.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            switch (PreferenceManager.getDefaultSharedPreferences(VentanaInicialApp.a).getString("tipo", "")) {
                                case "musico":
                                    Musico mus = data.getValue(Musico.class);
                                    mus.setAudio(null);
                                    bd.child(data.getKey()).setValue(mus);
                                    break;
                                case "grupo":
                                    Grupo gr = data.getValue(Grupo.class);
                                    gr.setAudio(null);
                                    bd.child(data.getKey()).setValue(gr);
                                    break;
                            }
                        }
                        if (BandsnArts.mediaPlayer.isPlaying()) {
                            BandsnArts.mediaPlayer.stop();
                        }

                        BandsnArts.mediaPlayer = null;
                        VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedormiperfil, new FragmentMultimedia(0)).commit();
                        ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Mi Perfil");
                        FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
//////////////////////////////////
////       PERSISTENCIA      /////
//////////////////////////////////

    public static void persistirDatos() {
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().getReference("grupo").keepSynced(true);
            FirebaseDatabase.getInstance().getReference("musico").keepSynced(true);
            FirebaseDatabase.getInstance().getReference("uids").keepSynced(true);
            FirebaseDatabase.getInstance().getReference("locales").keepSynced(true);
            FirebaseDatabase.getInstance().getReference("salas").keepSynced(true);
        } catch (com.google.firebase.database.DatabaseException ex) {
        }
    }


    public static void compruebaConexion(final View view) {
        // Comprobamos la conexion con FireBase
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                } else if (!BandsnArts.isOnlineNet()) {
                    // Además, controlamos que tenga conexion a Internet
                    System.out.println("not connected");
                    // make snackbar
                    try {
                        Snackbar mSnackbar = Snackbar.make(view, "NO TIENES CONEXION", Snackbar.LENGTH_LONG);
                        // get snackbar view
                        View mView = mSnackbar.getView();
                        // get textview inside snackbar view
                        TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                        // set text to center
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        else
                            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        // show the snackbar
                        mSnackbar.show();
                    } catch (IllegalArgumentException e) {
                        System.out.println("----------------------------------------- errorrrrrrrrrrrrr");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }
}
