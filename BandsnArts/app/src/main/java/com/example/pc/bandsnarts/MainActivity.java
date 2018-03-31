package com.example.pc.bandsnarts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    TextView titulo, txtRegistro;
    Typeface fuenteTitulo;
    Activity ventanaPrincipal;
    Button btnCancelarAlerta,btnAceptarAlerta;
    CheckBox grupo,musico;
    private AlertDialog.Builder alertaBuilder;
    private AlertDialog alerta;
    private LayoutInflater inflador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            ventanaPrincipal=this;
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

    }


    public void onClickRegistrar(View view) {
        //sacar alert dialog para grupo o musico
        alertaBuilder = new AlertDialog.Builder(this);
        //si no, da error
        inflador=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View vista = inflador.inflate(R.layout.alertdialoggrupomusico, (ViewGroup) findViewById(R.id.alertaregistro));
        //hago aqui el find porque necesita la vista///////
        btnCancelarAlerta=vista.findViewById(R.id.btnCancelarVAlert);
        btnAceptarAlerta=vista.findViewById(R.id.btnAceptarVAlert);
        grupo=vista.findViewById(R.id.chkGrupoVAlert);
        musico=vista.findViewById(R.id.chkMusicoVAlert);
        ////////////////////////////////////////////////
        alerta = alertaBuilder.create();
        alerta.setView(vista);
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
        btnAceptarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grupo.isChecked()){

                    startActivity(new Intent(ventanaPrincipal, RegistrarGrupo.class));
                    alerta.cancel();
                }else if(musico.isChecked()){
                    startActivity(new Intent(ventanaPrincipal, RegistarMusico.class));
                    alerta.cancel();
                }else{
                    Toast.makeText(MainActivity.this, "TIENES QUE ELEGIR ALGUNA OPCIÓN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //control de los checkbox
        musico.setChecked(true);
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
}
