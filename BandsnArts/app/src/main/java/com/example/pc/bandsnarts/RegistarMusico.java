package com.example.pc.bandsnarts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistarMusico extends AppCompatActivity {
    Spinner spinnerInstrumentos, spinnerEstilos;

    EditText edtMailMusico, edtPassMusico, edtRepitePassMusico, edtNombreMusico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_musico);
        spinnerInstrumentos = findViewById(R.id.spInstrumentoVRegSocial);
        spinnerEstilos = findViewById(R.id.spEstiloVRegSocial);
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerEstilos.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.estiloMusical)));

        edtMailMusico = findViewById(R.id.edtEmailVRegMusico);
        edtPassMusico = findViewById(R.id.edtPassVRegMusico);
        edtRepitePassMusico = findViewById(R.id.edtRepetirPassVRegMusico);
        edtNombreMusico = findViewById(R.id.edtNombreVRegMusico);
    }

    public void onClickVRegMusico(View view) {
        if (edtRepitePassMusico.getText().toString().isEmpty() || edtPassMusico.getText().toString().isEmpty() || edtMailMusico.getText().toString().isEmpty() || edtNombreMusico.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        } else {
            // Comprobamos que el patron de correo y de contraseña son correctos
            if (!new Autentificacion().validarEmail(edtMailMusico.getText().toString())) {
                edtMailMusico.setError("e-mail no válido");
            } else if (!new Autentificacion().comprobarPass(edtPassMusico.getText().toString())) {
                edtPassMusico.setError("Error al introducir contraseña");
                Toast.makeText(this, "Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número", Toast.LENGTH_LONG).show();
            } else if (!edtPassMusico.getText().toString().equals(edtRepitePassMusico.getText().toString())) {
                edtRepitePassMusico.setError("Las contraseñas no coinciden");
            } else {
                // Correo y password correctas
                new Autentificacion().registroMailPass(edtMailMusico.getText().toString(), edtPassMusico.getText().toString());
                // RECOGER DATOS DEL USUARIO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!

                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
