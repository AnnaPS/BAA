package com.example.pc.bandsnarts;

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegistarMusico extends AppCompatActivity {

    Spinner spinnerInstrumentos,spinnerEstilos,spinnerSexo;

    private EditText edtMailMusico, edtPassMusico, edtRepitePassMusico, edtNombreMusico;
    private Autentificacion auth;


    EditText edtMailMusico, edtPassMusico, edtRepitePassMusico, edtNombreMusico;
    private Autentificacion auth;
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

        //Guardamos el objeto para no tener que hacer nuevas instancias.
        auth = new Autentificacion(this);
    }

    public void onClickVRegMusico(View view) {
        if (edtRepitePassMusico.getText().toString().isEmpty() || edtPassMusico.getText().toString().isEmpty()
                || edtMailMusico.getText().toString().isEmpty() || edtNombreMusico.getText().toString().isEmpty()) {

            Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        } else {
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

                FirebaseUser usuario = auth.registroMailPass(edtMailMusico.getText().toString(), edtPassMusico.getText().toString());

                // Mensaje de control
                Toast.makeText(this, "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();

                if (usuario != null) {
                    if (!usuario.isEmailVerified()) {
                        // ENVIO CORREO VERIFICACION
                        Toast.makeText(RegistarMusico.this, "Correo electronico no verificado, por favor, verifique su correo.", Toast.LENGTH_SHORT).show();
                        usuario.sendEmailVerification();
                    }else{
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
