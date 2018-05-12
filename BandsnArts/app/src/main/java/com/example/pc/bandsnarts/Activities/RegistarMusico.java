package com.example.pc.bandsnarts.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Login.Autentificacion;
import com.example.pc.bandsnarts.R;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegistarMusico extends AppCompatActivity {

    Spinner spinnerInstrumentos, spinnerEstilos, spinnerSexo;

    private EditText edtMailMusico, edtPassMusico, edtRepitePassMusico, edtNombreMusico, edtDescripcion;
    private Autentificacion auth;
    private int posSexo = 0, posEstilo = 0, posInstrumento = 0;

    View vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_musico);

        edtNombreMusico = findViewById(R.id.edtNombreVRegMusico);
        spinnerInstrumentos = findViewById(R.id.spInstrumentoVRegSocial);
        spinnerEstilos = findViewById(R.id.spEstiloVRegSocial);
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerEstilos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.estiloMusical)));
        spinnerSexo = findViewById(R.id.spinnerSexoVLogin);
        spinnerSexo.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sexo)));
        edtMailMusico = findViewById(R.id.edtEmailVRegMusico);
        edtPassMusico = findViewById(R.id.edtPassVRegMusico);
        edtRepitePassMusico = findViewById(R.id.edtRepetirPassVRegMusico);
        edtDescripcion = findViewById(R.id.edtDescripcionVRegMusico);



        // PROBLEMA AL PULSAR EN EL EDITTEXT DE DESCRIPCION EN EL REGISTRO
        /*vista = findViewById(R.id.impDescripcionVRegSocial);
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RUTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
            }
        });*/


       /* edtDescripcion.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if(edtDescripcion.getText().toString().contains("\n")){
                    Toast.makeText(getApplicationContext(), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa", Toast.LENGTH_SHORT).show();
                    edtDescripcion.setText(BandsnArts.quitarSaltos(edtDescripcion.getText().toString()));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });*/


        escuchadoresSpinner();
        //Guardamos el objeto para no tener que hacer nuevas instancias.
        auth = new Autentificacion(this);
    }

    public void escuchadoresSpinner() {

        spinnerSexo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(RegistarMusico.this);
                return false;
            }
        }) ;
        spinnerEstilos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(RegistarMusico.this);
                return false;
            }
        }) ;
        spinnerInstrumentos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(RegistarMusico.this);
                return false;

            }
        }) ;


        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSexo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
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
                posInstrumento=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void onClickVRegMusico(View view) {
        if (edtRepitePassMusico.getText().toString().isEmpty() || edtPassMusico.getText().toString().isEmpty()
                || edtMailMusico.getText().toString().isEmpty() || edtNombreMusico.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }  else if (posEstilo == 0) {
            Toast.makeText(this, "Debe seleccionar un estiloooooo", Toast.LENGTH_SHORT).show();
        } else if (posInstrumento == 0) {
            Toast.makeText(this, "Debe seleccionar un instrumento", Toast.LENGTH_SHORT).show();
        }  else {
            // Comprobamos que el patron de correo y de contraseña son correctos
            if (!auth.validarEmail(edtMailMusico.getText().toString())) {
                edtMailMusico.setError("e-mail no válido");
            } else if (!auth.comprobarPass(edtPassMusico.getText().toString())) {
                edtPassMusico.setError("Error al introducir contraseña");
                Toast.makeText(this, "Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número", Toast.LENGTH_LONG).show();
            } else if (!edtPassMusico.getText().toString().equals(edtRepitePassMusico.getText().toString())) {
                edtRepitePassMusico.setError("Las contraseñas no coinciden");
            } else {
                // Correo y password correctas
                view.setVisibility(View.INVISIBLE);
                auth.registroMailPass(edtMailMusico.getText().toString(), edtPassMusico.getText().toString());

                // Mensaje de control
                final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(edtMailMusico.getText().toString(), edtPassMusico.getText().toString());
                Toast.makeText(this, "\n\nREGISTRADO CON EXITO" + FirebaseAuth.getInstance().getCurrentUser(), Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null) {

                            ArrayList<String> intrumentos= new ArrayList<>();
                            intrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInstrumento]);

                             BDBAA.agregarMusico(RegistarMusico.this,RegistarMusico.this.findViewById(R.id.btnRegistrarVRegMusico),
                                     edtNombreMusico, "default_musico.jpg"
                                    , edtNombreMusico.getText().toString(),
                                     getResources().getStringArray(R.array.sexo)[posSexo],
                                     getResources().getStringArray(R.array.estiloMusical)[posEstilo]
                                    , intrumentos,
                                    BandsnArts.quitarSaltos(edtDescripcion.getText().toString()) );
                            // ENVIO CORREO VERIFICACION
                            Toast.makeText(RegistarMusico.this, "Correo electronico no verificado, por favor, verifique su correo.", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            FirebaseAuth.getInstance().removeAuthStateListener(this);
                        }
                    }
                });

            }
        }
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
