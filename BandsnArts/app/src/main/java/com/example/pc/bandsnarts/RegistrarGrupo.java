package com.example.pc.bandsnarts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrarGrupo extends AppCompatActivity {
    Spinner spinnerEstilos;


    EditText edtMailGrupo,edtPassGrupo,edtRepitePassGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_grupo);

        spinnerEstilos=findViewById(R.id.spEstiloVRegGrupo);
        spinnerEstilos.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.estiloMusical)));


        //Nos traemos las vistas
        edtMailGrupo=findViewById(R.id.edtEmailVRegGrupo);
        edtPassGrupo=findViewById(R.id.edtPassVRegGrupo);
        edtRepitePassGrupo=findViewById(R.id.edtRepetirPassVRegGrupo);

    }


    public void clickRegGrupo(View view) {
        if(edtMailGrupo.getText().toString().isEmpty()||edtPassGrupo.getText().toString().isEmpty()||edtRepitePassGrupo.getText().toString().isEmpty()){
            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }else{

            // DEFINIR MAXIMO DE CARACTERES EN CADA CAMPO EN LA PARTE DE DISEÑO!!!!!!!!!!

            // Comprobamos que el patron de correo y de contraseña son correctos
            if(!new Autentificacion().validarEmail(edtMailGrupo.getText().toString())){
                edtMailGrupo.setError("e-mail no válido");
            }else if(!new Autentificacion().comprobarPass(edtPassGrupo.getText().toString())){
                edtPassGrupo.setError("Error al introducir contraseña");
                Toast.makeText(this, "Minimo 6 carácteres\nUna Mayuscula\nUna Minuscula\nUn número", Toast.LENGTH_LONG).show();
            }else if(!edtPassGrupo.getText().toString().equals(edtRepitePassGrupo.getText().toString())){
                edtRepitePassGrupo.setError("Las contraseñas no coinciden");
            }else{
                // Correo y password correctas
                new Autentificacion().registroMailPass(edtMailGrupo.getText().toString(),edtPassGrupo.getText().toString());
                // RECOGER DATOS DEL GRUPO Y LANZAR ACTIVIDAD DE BIENVENIDA !!!

                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
                //se lanza la info inicial
                startActivity(new Intent(this, VentanaSliderParteDos.class));
            }
        }
    }
}
