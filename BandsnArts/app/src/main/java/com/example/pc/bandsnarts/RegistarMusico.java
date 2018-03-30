package com.example.pc.bandsnarts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistarMusico extends AppCompatActivity {
    Spinner spinnerInstrumentos,spinnerEstilos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_musico);
        spinnerInstrumentos=findViewById(R.id.spInstrumentoVRegMusico);
        spinnerEstilos=findViewById(R.id.spEstiloVRegMusico);
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.instrumentos)));
        spinnerEstilos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.estiloMusical)));
    }
}
