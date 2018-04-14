package com.example.pc.bandsnarts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Login.InicioGoogle;
import com.example.pc.bandsnarts.Login.LoginActivity;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView titulo, txtRegistro;
    private Typeface fuenteTitulo;
    private Activity ventanaPrincipal;
    private Button btnCancelarAlerta, btnAceptarAlerta;
    private CheckBox grupo, musico;
    private AlertDialog.Builder alertaBuilder;
    private AlertDialog alerta;
    private LayoutInflater inflador;


    // Objeto FirebaseAuth y su escuchador para comprobacion de inicio de sesion
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener escuchador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ventanaPrincipal = this;
        //finds//////////
        final ImageView imagenCentro = findViewById(R.id.imgImagenVMain);
        titulo = findViewById(R.id.tituloVMain);
        txtRegistro = findViewById(R.id.txtRegistrarVMain);
        //////////////////

        //asignar nueva fuente//////////
        fuenteTitulo = Typeface.createFromAsset(getAssets(), "fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);
        /////////////////////////////////

        //Animacion imagen central
        final Animation animacionZoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        imagenCentro.startAnimation(animacionZoom);
        /////////////////////////


        // Comprobación de sesion iniciada en FaceBook
        if (AccessToken.getCurrentAccessToken() != null) {
            // Lanzamos la actividad de Inicio
            Intent i = new Intent(this, InicioGoogle.class);
            startActivity(i);
            finish();
        }


        // Inicializamos el FireBaseAuth y su escuchador para comprobacion de inicio de sesion
        firebaseAuth = FirebaseAuth.getInstance();
        escuchador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Este metodo se ejecuta cuando cambia el estado de la autenticacion
                // Verificamos si estamos autenticados en Firebase
                FirebaseUser usuario = firebaseAuth.getCurrentUser();

                if (usuario != null) {
                    Log.d("prueba", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    Intent i = new Intent(ventanaPrincipal, InicioGoogle.class);
                    startActivity(i);
                    finish();

                } else {
                    Log.d("prueba", "ESTOY PASAAAAAAANDOOOOO PORQUE SOY NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }
            }
        };

    }


    public void onClickRegistrar(View view) {
        //sacar alert dialog para grupo o musico
        alertaBuilder = new AlertDialog.Builder(this);
        //si no, da error
        inflador = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflador.inflate(R.layout.alertdialoggrupomusico, (ViewGroup) findViewById(R.id.alertaregistro));
        //hago aqui el find porque necesita la vista///////
        btnCancelarAlerta = view.findViewById(R.id.btnCancelarVAlert);
        btnAceptarAlerta = view.findViewById(R.id.btnAceptarVAlert);
        grupo = view.findViewById(R.id.chkGrupoVAlert);
        musico = view.findViewById(R.id.chkMusicoVAlert);
        ////////////////////////////////////////////////
        alerta = alertaBuilder.create();
        alerta.setView(view);
        //para no poder usar el onbackpressed
        alerta.setCancelable(false);
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //muestra el alert
                alerta.show();
            }
        });
        btnCancelarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cierra el alert
                alerta.cancel();
            }
        });
        //al pulsar aceptar se abrira una u otra ventana
        btnAceptarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grupo.isChecked()) {
                    startActivity(new Intent(ventanaPrincipal, RegistrarGrupo.class));
                    alerta.cancel();
                } else if (musico.isChecked()) {
                    startActivity(new Intent(ventanaPrincipal, RegistarMusico.class));
                    alerta.cancel();
                } else {
                    Toast.makeText(MainActivity.this, "POR FAVOR, ELIJA ALGUNA OPCIÓN PARA CONTINUAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //control de los checkbox
        musico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grupo.setChecked(false);
            }
        });

        grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musico.setChecked(false);
            }
        });
    }

    public void onClickIniciarVMain(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Aqui escucharemos los cambios de estado de la autenticacion
        firebaseAuth.addAuthStateListener(escuchador);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // En este metodo paramos el escuchador
        if (escuchador != null) {
            firebaseAuth.removeAuthStateListener(escuchador);
        }
    }


    public void click(View view) {
        startActivity(new Intent(this, VentanaInicialApp.class));
    }
}
