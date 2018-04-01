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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView titulo;
    Typeface fuenteTitulo;

    EditText edtUser,edtPass;

    public static final int CODIGO_DE_INICIO =777;

    // Objeto para conectar con la API del Cliente Google
    private GoogleApiClient clienteGoogle;

    // Objeto FirebaseAuth y su escuchador
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener escuchador;

    private Autentificacion auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo=findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo=Typeface.createFromAsset(getAssets(),"fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);

        edtUser = findViewById(R.id.edtUsuarioVLogin);
        edtPass = findViewById(R.id.edtPassVLogin);
        //Guardamos el objeto para no tener que hacer nuevas instancias.
        auth=new Autentificacion(this);

        //Opciones de inicio con google, obtenemos un token de usuario
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Configuramos el cliente google, pasandole las opciones de inicio
        clienteGoogle = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        // Inicializamos el FireBaseAuth y su escuchador
        firebaseAuth = FirebaseAuth.getInstance();
        escuchador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Este metodo se ejecuta cuando cambia el estado de la autenticacion
                // Verificamos si estamos autentocados en Firebase
                FirebaseUser usuario = firebaseAuth.getCurrentUser();

                if(usuario!=null){
                    Toast.makeText(LoginActivity.this, "Usuario verificado", Toast.LENGTH_SHORT).show();
                   siguienteActivity();
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Aqui escucharemos los cambios de estado de la autenticacion
        firebaseAuth.addAuthStateListener(escuchador);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // En este metodo paramos el escuchador
        if(escuchador!=null){
            firebaseAuth.removeAuthStateListener(escuchador);
        }
    }

    public void onClickIngresarVLogin(View view) {
        if(edtPass.getText().toString().isEmpty()||edtUser.getText().toString().isEmpty()){
            Toast.makeText(this, "DEBE INSERTAR AMBOS DATOS", Toast.LENGTH_SHORT).show();
        }else{
            auth.loginMailPass(edtUser.getText().toString(),edtPass.getText().toString());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // SI ALGO SALE MAL EN LA CONEXION...INFORMAR AL USUARIO
    }

    public void onClickIngresoGoogle(View view) {
        Intent g = Auth.GoogleSignInApi.getSignInIntent(clienteGoogle);
        startActivityForResult(g,CODIGO_DE_INICIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODIGO_DE_INICIO){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            compruebaResultado(result);
        }
    }

    private void compruebaResultado(GoogleSignInResult result) {
        if(result.isSuccess()){
            // Llamada al metodo para autenticar al usuario en Firebase y le mandamos la cuenta
            autenticarEnFirebase(result.getSignInAccount());
            //siguienteActivity();

        }else{
            Toast.makeText(this, "ERROR AL LOGAR", Toast.LENGTH_SHORT).show();
        }
    }

    private void autenticarEnFirebase(GoogleSignInAccount signInAccount) {
        // Creamos una credencial y guardamos en ella el Token obtenido del objeto cuenta, el segundo
        // parametro es es access Token que no es necesario, le pasamos null
        AuthCredential credencial = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);

        // Autenticamos con firebase y agragamos un escuchador que nos dirá cuando termina
        firebaseAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "No se pudo autenticar con Firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void siguienteActivity() {
        Intent i = new Intent(this,InicioGoogle.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}