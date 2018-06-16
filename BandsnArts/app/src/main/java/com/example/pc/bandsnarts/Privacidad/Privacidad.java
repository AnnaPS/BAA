package com.example.pc.bandsnarts.Privacidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.pc.bandsnarts.R;

public class Privacidad extends AppCompatActivity {
TextView privacidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad);
        privacidad=findViewById(R.id.privacidad);
        privacidad.setMovementMethod(new ScrollingMovementMethod());
    }
}
