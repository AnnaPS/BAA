package com.example.pc.bandsnarts;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView titulo;
    Typeface fuenteTitulo;

    EditText edtUser, edtPass;

    // Objeto para registro con Google
    private GoogleApiClient clienteGoogle;

    private static final int CODIGO_DE_LOGUEO = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo = findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo = Typeface.createFromAsset(getAssets(), "fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);

        edtUser = findViewById(R.id.edtUsuarioVLogin);
        edtPass = findViewById(R.id.edtPassVLogin);

        // Para definir las opciones de loguin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        // Configuracion del usuario de Google, le pasamos las opciones definidas
        clienteGoogle = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    public void onClickIngresarVLogin(View view) {
        if (edtPass.getText().toString().isEmpty() || edtUser.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE INSERTAR AMBOS DATOS", Toast.LENGTH_SHORT).show();
        } else {
            boolean a = new Autentificacion().loginMailPass(edtUser.getText().toString(), edtPass.getText().toString());
            Toast.makeText(this, "" + a, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // algo salio mal en la conexion
    }

    public void onClickLoginGoogle(View view) {
        // Intent para iniciar sesion en una cuenta Google
        Intent i = Auth.GoogleSignInApi.getSignInIntent(clienteGoogle);
        startActivityForResult(i, CODIGO_DE_LOGUEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_DE_LOGUEO) {
            // Obtenemos el resultado del logueo
            GoogleSignInResult resultado = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            accionResultado(resultado);
        }
    }

    private void accionResultado(GoogleSignInResult resultado) {
        if (resultado.isSuccess()) {
            Intent i = new Intent(this, InicioGoogle.class);
            startActivity(i);
            Toast.makeText(this, "LOGUEO CORRECTO.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No se pudo iniciar sesión.", Toast.LENGTH_SHORT).show();
        }
    }
}
