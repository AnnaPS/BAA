package com.example.pc.bandsnarts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistrarGrupo extends AppCompatActivity {
    Spinner spinnerEstilos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_grupo);

        spinnerEstilos=findViewById(R.id.spEstiloVRegGrupo);
        spinnerEstilos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.estiloMusical)));
    }
}
