package com.example.pc.bandsnarts.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.BBDD.BDBAA;

import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegistarRedSocial extends AppCompatActivity {
    Spinner spinnerInstrumentos, spinnerEstilos,spinnerSexo;
    EditText edtNombre, edtDescripcion;
    private int posEstilo;
    private int posInstrumento;
    private Activity a;
    //recogemos en una variable entera si es 0 es un musico y si es 1 es un grupo
    private int tipo;
    private Button btnCancelarAlerta, btnAceptarAlerta, btnReg;
    private CheckBox grupo, musico;
    private AlertDialog.Builder alertaBuilder;
    private AlertDialog alerta;
    private LayoutInflater inflador;
    private TextView textViewInstrumentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertaTipoRegistro();
        this.a = this;
        //finds///
        setContentView(R.layout.activity_registar_red_social);
        spinnerEstilos = findViewById(R.id.spEstiloVRegSocial);
        spinnerSexo=findViewById(R.id.spinnerSexoVRegSocial);
        textViewInstrumentos = findViewById(R.id.tvInstrumentoVRegSocial);
        //spinners para estilo musical e instrumentos
        spinnerInstrumentos = findViewById(R.id.spInstrumentoVRegSocial);
        spinnerEstilos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.estiloMusical)));
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerSexo.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sexo)));
        //Edit Text
        edtNombre = findViewById(R.id.edtNombreVRegSocial);
        edtDescripcion = findViewById(R.id.edtDescripcionVRegSocial);
        escuchadoresSpinner();

    }

    public void alertaTipoRegistro() {

        //sacar alert dialog para grupo o musico
        alertaBuilder = new AlertDialog.Builder(this);
        //si no, da error
        inflador = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View vista = inflador.inflate(R.layout.alertdialoggrupomusico, (ViewGroup) findViewById(R.id.alertaregistro));
        //hago aqui el find porque necesita la vista///////
        btnCancelarAlerta = vista.findViewById(R.id.btnCancelarVAlert);
        btnAceptarAlerta = vista.findViewById(R.id.btnAceptarVAlert);
        grupo = vista.findViewById(R.id.chkGrupoVAlert);
        musico = vista.findViewById(R.id.chkMusicoVAlert);
        ////////////////////////////////////////////////
        alerta = alertaBuilder.create();
        alerta.setView(vista);
        //para no poder usar el onbackpressed
        alerta.setCancelable(false);
        alerta.show();
        btnCancelarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cierra el alert
                alerta.cancel();
                FirebaseAuth.getInstance().signOut();
                a.finish();
            }
        });
        //al pulsar aceptar se abrira una u otra ventana
        btnAceptarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grupo.isChecked()) {
                    tipo = 1;
                    alerta.cancel();
                } else if (musico.isChecked()) {
                    tipo = 0;
                    alerta.cancel();
                } else {
                    Toast.makeText(RegistarRedSocial.this, "POR FAVOR, ELIJA ALGUNA OPCIÓN PARA CONTINUAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                }
                switch (tipo) {
                    case 1:
                        textViewInstrumentos.setVisibility(View.GONE);
                        spinnerInstrumentos.setVisibility(View.GONE);
                        spinnerSexo.setVisibility(View.GONE);
                        break;
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

    public void escuchadoresSpinner() {

        spinnerEstilos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posEstilo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInstrumento = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    public void onClickLogueo(View view) {

        if (!edtNombre.getText().toString().isEmpty()) {

                Intent i;
                Log.d("TIPO DE LOGUIEO", "onClickLogueo: " + tipo);
                switch (tipo) {
                    //Es un grupo
                    case 1:
                        i = new Intent()
                                .putExtra("tipo", tipo)
                                .putExtra("nom", edtNombre.getText().toString())
                                .putExtra("est", getResources().getStringArray(R.array.estiloMusical)[posEstilo])
                                .putExtra("des", edtDescripcion.getText().toString());
                        guardarBD(this, i);
                        break;
                    //Es un musico
                    case 0:
                        //  Intent i=new Intent();
                        //pendiente de implementacion de sexo
                        //    new BDBAA().agregarMusico(this, "default_musico.jpg", edtNombre.getText().toString(), getResources().getStringArray(R.array.sexo)[posSexo], getResources().getStringArray(R.array.estiloMusical)[posEstilo], getResources().getStringArray(R.array.instrumentos)[posInstrumento], edtDescripcion.getText().toString());
                        break;
                }
            }
        }




    private void guardarBD(Context cont, Intent data) {
        int tipo = data.getExtras().getInt("tipo");
        switch (tipo) {
            //Es un grupo
            case 1:
                new BDBAA().agregarGrupo(cont
                        ,edtNombre
                        , data.getStringExtra("img")
                        , data.getStringExtra("nom")
                        , data.getStringExtra("est")
                        , data.getStringExtra("des"));
                break;
            //Es un musico
            case 0:
                //pendiente de implementacion de sexo
                new BDBAA().agregarMusico(cont
                        ,edtNombre
                        , data.getStringExtra("img")
                        , data.getStringExtra("nom")
                        , data.getStringExtra("sex")
                        , data.getStringExtra("est")
                        , data.getStringExtra("ins")
                        , data.getStringExtra("des"));
                break;
        }

    }

    @Override
    public void onBackPressed() {
    }
}
