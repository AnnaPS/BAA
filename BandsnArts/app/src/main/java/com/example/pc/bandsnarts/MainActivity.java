package com.example.pc.bandsnarts;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView titulo;
    Typeface fuenteTitulo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //finds//////////
        final ImageView imagenCentro=findViewById(R.id.imgImagenVMain);
        titulo=findViewById(R.id.tituloVMain);
        //////////////////

        //asignar nueva fuente//////////
        fuenteTitulo=Typeface.createFromAsset(getAssets(),"fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);
        /////////////////////////////////

        //Animacion imagen central
        final Animation animacionZoom= AnimationUtils.loadAnimation(this,R.anim.zoom);
        imagenCentro.startAnimation(animacionZoom);
        /////////////////////////

    }


    public void onClickEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onClickRegistrar(View view) {
        //sacar alert dialog para grupo o musico
    }
}
