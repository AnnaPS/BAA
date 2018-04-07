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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView titulo;
    Typeface fuenteTitulo;
    Activity ventanaPrincipal;
    EditText edtUser, edtPass;
    Button btnCancelarAlerta, btnAceptarAlerta, btnReg;
    CheckBox grupo, musico;
    private AlertDialog.Builder alertaBuilder;
    private AlertDialog alerta;
    private LayoutInflater inflador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo = findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo = Typeface.createFromAsset(getAssets(), "fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);
            ventanaPrincipal=this;
        btnReg = findViewById(R.id.btnRegistrarVLogin);
        edtUser = findViewById(R.id.edtUsuarioVLogin);
        edtPass = findViewById(R.id.edtPassVLogin);
    }


    public void onClickIngresarVLogin(View view) {
        if (edtPass.getText().toString().isEmpty() || edtUser.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE INSERTAR AMBOS DATOS", Toast.LENGTH_SHORT).show();
        } else {
            boolean a = new Autentificacion().loginMailPass(edtUser.getText().toString(), edtPass.getText().toString());
            Toast.makeText(this, "" + a, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickRegistrarVLogin(View view) {
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
        btnReg.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this, "POR FAVOR, ELIJA ALGUNA OPCIÓN PARA CONTINUAR EL REGISTRO", Toast.LENGTH_SHORT).show();
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
}
