package com.example.pc.bandsnarts;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView titulo;
    Typeface fuenteTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo=findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo=Typeface.createFromAsset(getAssets(),"fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);
    }


}
