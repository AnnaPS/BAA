package com.example.pc.bandsnarts.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarGrupo extends AppCompatActivity {
    Spinner spinnerEstilos;
    int posEstilo = 0;

    EditText edtMailGrupo, edtPassGrupo, edtRepitePassGrupo, edtNombreGrupo, edtDescripcion;
    private Autentificacion auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_grupo);

        spinnerEstilos = findViewById(R.id.spEstiloVRegGrupo);
        spinnerEstilos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item
                , getResources().getStringArray(R.array.estiloMusical)));
        escuchadorSpinner();
        //Nos traemos las vistas
        edtNombreGrupo = findViewById(R.id.edtNombreVRegGrupo);
        edtDescripcion = findViewById(R.id.edtDescripcionVRegGrupo);
        edtMailGrupo = findViewById(R.id.edtEmailVRegGrupo);
        edtPassGrupo = findViewById(R.id.edtPassVRegGrupo);
        edtRepitePassGrupo = findViewById(R.id.edtRepetirPassVRegGrupo);
        edtNombreGrupo = findViewById(R.id.edtNombreVRegGrupo);
        //Guardamos el objeto para no tener que hacer nuevas instancias.
        auth = new Autentificacion(this);
    }

    public void escuchadorSpinner() {
        spinnerEstilos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(RegistrarGrupo.this);
                return false;
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

    }

    public void clickRegGrupo(View view) {
        if (edtNombreGrupo.getText().toString().isEmpty()) {
            edtNombreGrupo.setError("Debe Insertar su nombre");
            edtNombreGrupo.requestFocus();
        }else if(edtDescripcion.getText().toString().isEmpty()){
            edtDescripcion.setError("Debe Insertar una descripción");
            edtDescripcion.requestFocus();
        }else if (posEstilo == 0) {
            TextView errorText = (TextView) spinnerEstilos.getSelectedView();
            // errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Seleccione un estilo");//changes the selected item text to this
            edtNombreGrupo.requestFocus();
        } else {
            // DEFINIR MAXIMO DE CARACTERES EN CADA CAMPO EN LA PARTE DE DISEÑO!!!!!!!!!!

            // Comprobamos que el patron de correo y de contraseña son correctos
            if (!auth.validarEmail(edtMailGrupo.getText().toString())) {
                edtMailGrupo.setError("email no válido");
            } else if (!auth.comprobarPass(edtPassGrupo.getText().toString())) {
                edtPassGrupo.setError("Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número");
                edtPassGrupo.setText("");
                edtRepitePassGrupo.setText("");
                edtPassGrupo.requestFocus();
            } else if (!edtPassGrupo.getText().toString().equals(edtRepitePassGrupo.getText().toString())) {
                edtPassGrupo.setError("Las contraseñas no coinciden");
                edtPassGrupo.setText("");
                edtRepitePassGrupo.setText("");
                edtPassGrupo.requestFocus();
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
            } else {
                // Correo y password correctas
                view.setVisibility(View.INVISIBLE);
                auth.registroMailPass(edtMailGrupo.getText().toString(), edtPassGrupo.getText().toString());

                FirebaseAuth.getInstance().signInWithEmailAndPassword(edtMailGrupo.getText().toString(), edtPassGrupo.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        RegistrarGrupo.this.findViewById(R.id.btnRegistrarVRegGrupo).setVisibility(View.VISIBLE);
                    }
                });

                //se lanza la info inicial
                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null) {
                            BDBAA.agregarFackingMaster("grupo",
                                    RegistrarGrupo.this,
                                    RegistrarGrupo.this.findViewById(R.id.btnRegistrarVRegGrupo),
                                    edtMailGrupo,
                                    edtNombreGrupo, "default_grupo.jpg",
                                    edtNombreGrupo.getText().toString(),
                                    null,
                                    getResources().getStringArray(R.array.estiloMusical)[posEstilo],
                                    null,
                                    BandsnArts.quitarSaltos(edtDescripcion.getText().toString()));
                            // ENVIO CORREO VERIFICACION
                            Toast.makeText(RegistrarGrupo.this, "Correo electronico no verificado, por favor, verifique su correo.", Toast.LENGTH_SHORT).show();
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
