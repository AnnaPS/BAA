package com.example.pc.bandsnarts.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Login.Autentificacion;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseUser;

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
        spinnerEstilos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posEstilo=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    public void clickRegGrupo(View view) {
        if (edtMailGrupo.getText().toString().isEmpty() || edtPassGrupo.getText().toString().isEmpty() || edtRepitePassGrupo.getText().toString().isEmpty() || edtNombreGrupo.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        } else {
            // DEFINIR MAXIMO DE CARACTERES EN CADA CAMPO EN LA PARTE DE DISEÑO!!!!!!!!!!

            // Comprobamos que el patron de correo y de contraseña son correctos
            if (!auth.validarEmail(edtMailGrupo.getText().toString())) {
                edtMailGrupo.setError("e-mail no válido");
            } else if (!auth.comprobarPass(edtPassGrupo.getText().toString())) {
                edtPassGrupo.setError("Error al introducir contraseña");
                Toast.makeText(this, "Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número", Toast.LENGTH_LONG).show();
            } else if (!edtPassGrupo.getText().toString().equals(edtRepitePassGrupo.getText().toString())) {
                edtRepitePassGrupo.setError("Las contraseñas no coinciden");
            } else {
                // Correo y password correctas
                FirebaseUser usuario =  auth.registroMailPass(edtMailGrupo.getText().toString(), edtPassGrupo.getText().toString());
                // RECOGER DATOS DEL GRUPO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!
                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
                //se lanza la info inicial
                startActivity(new Intent(this, VentanaSliderParteDos.class));
                if (usuario != null) {
                    if (!usuario.isEmailVerified()) {
                        // ENVIO CORREO VERIFICACION
                        new BDBAA().agregarGrupo(this, "default_grupo.jpg", edtNombreGrupo.getText().toString(), getResources().getStringArray(R.array.estiloMusical)[posEstilo], edtDescripcion.getText().toString());
                        Toast.makeText(this, "Correo electronico no verificado, por favor, verifique su correo.", Toast.LENGTH_SHORT).show();
                        usuario.sendEmailVerification();
                    } else {
                        // RECOGER DATOS DEL USUARIO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!
                        // Name, email address, and profile photo Url
                        String name = usuario.getDisplayName();
                        String email = usuario.getEmail();
                        Uri photoUrl = usuario.getPhotoUrl();

                        // Check if user's email is verified
                        boolean emailVerified = usuario.isEmailVerified();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        String uid = usuario.getUid();

                        Toast.makeText(auth, "nombre: " + name + "\ncorreo: " + email +
                                "\nURL de la foto: " + photoUrl + "\nemail verificado: "
                                + emailVerified, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
