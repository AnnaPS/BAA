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
    Spinner spinnerInstrumentos,spinnerEstilos,spinnerSexo;

    EditText edtMailMusico,edtPassMusico,edtRepitePassMusico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_musico);
        spinnerInstrumentos=findViewById(R.id.spInstrumentoVRegSocial);
        spinnerEstilos=findViewById(R.id.spEstiloVRegSocial);
        spinnerInstrumentos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.instrumentos)));
        spinnerEstilos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.estiloMusical)));
        spinnerSexo=findViewById(R.id.spinnerSexoVLogin);
        spinnerSexo.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.sexo)));
        edtMailMusico=findViewById(R.id.edtEmailVRegMusico);
        edtPassMusico=findViewById(R.id.edtPassVRegMusico);
        edtRepitePassMusico=findViewById(R.id.edtRepetirPassVRegMusico);
    }

    public void onClickVRegMusico(View view) {
        if(edtRepitePassMusico.getText().toString().isEmpty()||edtPassMusico.getText().toString().isEmpty()||edtMailMusico.getText().toString().isEmpty()){
            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }else{

            // DEFINIR MAXIMO DE CARACTERES EN CADA CAMPO EN LA PARTE DE DISEÑO!!!!!!!!!!

            // Comprobamos que el patron de correo y de contraseña son correctos
            if(!new Autentificacion().validarEmail(edtMailMusico.getText().toString())){
                edtMailMusico.setError("e-mail no válido");
            }else if(!new Autentificacion().comprobarPass(edtPassMusico.getText().toString())){
                edtPassMusico.setError("Error al introducir contraseña");
                Toast.makeText(this, "Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número", Toast.LENGTH_LONG).show();
            }else if(!edtPassMusico.getText().toString().equals(edtRepitePassMusico.getText().toString())){
                edtRepitePassMusico.setError("Las contraseñas no coinciden");
            }else{
                // Correo y password correctas
                new Autentificacion().registroMailPass(edtMailMusico.getText().toString(),edtPassMusico.getText().toString());
                // RECOGER DATOS DEL USUARIO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!

                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
