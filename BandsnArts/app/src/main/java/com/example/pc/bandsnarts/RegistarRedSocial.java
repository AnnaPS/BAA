package com.example.pc.bandsnarts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistarRedSocial extends AppCompatActivity {
    Spinner spinnerInstrumentos,spinnerEstilos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //finds///
        setContentView(R.layout.activity_registar_red_social);
        spinnerEstilos=findViewById(R.id.spEstiloVRegSocial);

        //spinners para estilo musical e instrumentos
        spinnerInstrumentos=findViewById(R.id.spInstrumentoVRegSocial);
        spinnerEstilos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.estiloMusical)));
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.instrumentos)));
    }

    public void onClickLogueo(View view) {

        setResult(000);
        finish();
    }
}
