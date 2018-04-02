package com.example.pc.bandsnarts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class InicioGoogle extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView nombreGoogle, emailGoogle, identUsuGoogle;
    private ImageView fotoGoogle;


    // Variable para el usuario de Google
    private GoogleApiClient clienteGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_google);

        nombreGoogle = findViewById(R.id.txtNombreVGoogle);
        emailGoogle = findViewById(R.id.txtMailVGoogle);
        identUsuGoogle = findViewById(R.id.txtUIDVGoogle);
        fotoGoogle = findViewById(R.id.imvFotoVGoogle);

        // Opciones de inicio con google para login silencioso porque ya se realizo la autenticacion
        // y para poder acceder a los datos del usuarios
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Configuramos el cliente google, pasandole las opciones de inicio
        clienteGoogle = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Para realizar el login silencioso, recibiendolo el un objeto de tipo OptionalPendingResult
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(clienteGoogle);

        if (opr.isDone()) {
            //si la sesion esta iniciada, podremos acceder al objeto resultado de la actividad anterior,
            // para acceder a la informacion del usuario
            GoogleSignInResult result = opr.get();
            // Mandamos el resultado al metodo de gestion de resultados
            compruebaResultado(result);
        } else {
            // O no hemos iniciado sesion, o la sesion expiro o algun problema con la conexion momentaneo
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    // Mandamos el resultado al metodo de gestion de resultados
                    compruebaResultado(googleSignInResult);
                }
            });
        }
    }

    private void compruebaResultado(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Si el resultado es exitoso, podemos acceder a la informacion del usuario
            GoogleSignInAccount cuentaUsuario = result.getSignInAccount();

            // Pintamos los datos del usuario
            nombreGoogle.setText(cuentaUsuario.getDisplayName());
            emailGoogle.setText(cuentaUsuario.getEmail());
            identUsuGoogle.setText(cuentaUsuario.getId());

            // Mostramos por consola la URL de la imagen
            // Log.d("MIAPP", cuentaUsuario.getPhotoUrl().toString());

            Glide.with(this).load(cuentaUsuario.getPhotoUrl()).into(fotoGoogle);


        } else {
            // No se ha iniciado sesion
            volverActivityLogin();
        }
    }

    private void volverActivityLogin() {
        Intent i = new Intent(this,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void onClickLogOut(View view) {
        Auth.GoogleSignInApi.signOut(clienteGoogle).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(InicioGoogle.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                    volverActivityLogin();
                }else{
                    Toast.makeText(InicioGoogle.this, "Sesion no cerrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickRevocar(View view) {
        Auth.GoogleSignInApi.revokeAccess(clienteGoogle).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(InicioGoogle.this, "Sesion revocada", Toast.LENGTH_SHORT).show();
                    volverActivityLogin();
                }else{
                    Toast.makeText(InicioGoogle.this, "Sesion no revocada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
