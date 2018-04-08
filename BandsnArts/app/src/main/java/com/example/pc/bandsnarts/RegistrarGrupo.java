package com.example.pc.bandsnarts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        spinnerEstilos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posEstilo = position;
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
                auth.registroMailPass(edtMailGrupo.getText().toString(), edtPassGrupo.getText().toString());
                // RECOGER DATOS DEL GRUPO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!
                new BDBAA().agregarGrupo(this, "default_grupo", edtNombreGrupo.getText().toString(), getResources().getStringArray(R.array.estiloMusical)[posEstilo], edtDescripcion.getText().toString());
                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
                //se lanza la info inicial
                startActivity(new Intent(this, VentanaSliderParteDos.class));
            }
        }
    }
}
