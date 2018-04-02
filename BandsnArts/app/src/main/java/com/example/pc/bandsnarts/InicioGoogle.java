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
import com.facebook.login.LoginManager;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioGoogle extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView nombreGoogle, emailGoogle, identUsuGoogle;
    private ImageView fotoGoogle;


    // Variable para el usuario de Google
    private GoogleApiClient clienteGoogle;


    // Objeto FirebaseAuth y su escuchador
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener escuchador;

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


        // Inicializamos el FireBaseAuth y su escuchador
        firebaseAuth = FirebaseAuth.getInstance();
        escuchador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Obtenemos al usuario actual
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if (usuario != null) {
                    // Si hay usuario, pintamos sus datos
                    datosUsuario(usuario);
                }

            }

        };
    }

    private void datosUsuario(FirebaseUser usuario) {
        // Pintamos los datos del usuario
        nombreGoogle.setText(usuario.getDisplayName());
        emailGoogle.setText(usuario.getEmail());
        identUsuGoogle.setText(usuario.getUid());

        // Mostramos por consola la URL de la imagen
        // Log.d("MIAPP", cuentaUsuario.getPhotoUrl().toString());

        Glide.with(this).load(usuario.getPhotoUrl()).into(fotoGoogle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(escuchador);
    }


    private void volverActivityLogin() {
        Intent i = new Intent(this,LoginActivity.class);
        // Esta linea a continuacion es lo mismo que hacer un ficish de esta actividad
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void onClickLogOut(View view) {

        //Deslogueo en Google
        firebaseAuth.signOut();
        // deslogueo en Facebook
        LoginManager.getInstance().logOut();

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
        firebaseAuth.signOut();

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

    @Override
    protected void onStop() {
        super.onStop();
        // En este metodo paramos el escuchador
        if(escuchador!=null){
            firebaseAuth.removeAuthStateListener(escuchador);
        }
    }
}
