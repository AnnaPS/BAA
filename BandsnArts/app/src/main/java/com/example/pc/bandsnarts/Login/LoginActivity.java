package com.example.pc.bandsnarts.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*import com.crashlytics.android.Crashlytics;*/
import com.example.pc.bandsnarts.Activities.RegistarMusico;
import com.example.pc.bandsnarts.Activities.RegistrarGrupo;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView titulo;
    private Typeface fuenteTitulo;
    private Activity ventanaPrincipal;
    private EditText edtUser, edtPass;
    private Button btnCancelarAlerta, btnAceptarAlerta;//, btnReg;
    private CheckBox grupo, musico;
    private AlertDialog.Builder alertaBuilder;
    private AlertDialog alerta;
    private LayoutInflater inflador;
    //Objeto para conectar con la api de facebook

    // Objeto para conectar con la API del Cliente Google
    private GoogleApiClient clienteGoogle;
    boolean gooogle;
    // Objeto FirebaseAuth y su escuchador
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener escuchador;
    private Autentificacion auth;
    private LoginButton botonFaceBook;
    // Objeto de clase CallbackManager para detectar acciones en el boton FaceBook
    private CallbackManager callbackManager;
    private Activity estaVentana;
    private ImageView progressBar;
    // Boton Login con Google
    com.google.android.gms.common.SignInButton botonGoogle;


    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //   Forzar CRASHEO
        //   Crashlytics.getInstance().crash(); // Force a crash

        titulo = findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo = Typeface.createFromAsset(getAssets(), "fonts/VtksSimplizinha.ttf");

        titulo.setTypeface(fuenteTitulo);
        ventanaPrincipal = this;
        //btnReg = findViewById(R.id.btnRegistrarVLogin);
        edtUser = findViewById(R.id.edtUsuarioVLogin);
        edtPass = findViewById(R.id.edtPassVLogin);
        progressBar = findViewById(R.id.progressBarVLogin);


        //Guardamos el objeto para no tener que hacer nuevas instancias.
        auth = new Autentificacion(this);
        estaVentana = this;

        // ACCION BOTON GOOGLE
        botonGoogle = findViewById(R.id.btnGoogleVLogin);
        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIngresoGoogle(null);
            }
        });

        //Opciones de inicio con google, obtenemos un token de usuario
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        //Configuramos el cliente google, pasandole las opciones de inicio
        clienteGoogle = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Inicializamos el FireBaseAuth y su escuchador
        firebaseAuth = FirebaseAuth.getInstance();
        escuchador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Este metodo se ejecuta cuando cambia el estado de la autenticacion
                // Verificamos si estamos autenticados en Firebase
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if (usuario != null) {
                    visualizarBotones(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Usuario Verificado", Toast.LENGTH_SHORT).show();
                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()|| clienteGoogle.isConnecting()) {
                        new BDBAA().comprobarUID(estaVentana, usuario.getUid());
                    } else {
                        visualizarBotones(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario null", Toast.LENGTH_SHORT).show();
                    visualizarBotones(View.VISIBLE);
                }
            }
        };



        // Inicializamos CallbackManager
        callbackManager = CallbackManager.Factory.create();
        // Recogemos el Boton
        botonFaceBook = findViewById(R.id.btnFacebookVLogin);
        // Establecemos permisos para leer el correo electronico del usuario
        botonFaceBook.setReadPermissions(Arrays.asList("email"));
        botonFaceBook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                // Cuando el login con Facebook sea exitoso, podemos acceder a los datos del usuario
                // Le pasamos al metodo el Token del usuario a traves del loginResult
                manejadorTokenFacebook(loginResult.getAccessToken());
                //Apartamos este metodo de aqui ya que sino autentica directamente sin pasar por la actividad de registrar los datos
                // manejadorTokenFacebook(loginResult.getAccessToken());
                Toast.makeText(estaVentana, "ACCESO CON FACEBOOK CORRECTO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // Cuando se cancele el inicio de sesion.
                Toast.makeText(estaVentana, "Inicio Cancelado.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                // Algun error como conexion u otros.
                Toast.makeText(estaVentana, "Ocurrió algun error al iniciar sesión.", Toast.LENGTH_SHORT).show();
            }
        });
        visualizarBotones(View.INVISIBLE);
    }

    public void visualizarBotones(int vis) {
        findViewById(R.id.llVerticalVLogin).setVisibility(vis);
        findViewById(R.id.llhVLogin).setVisibility(vis);
        findViewById(R.id.vVLoging).setVisibility(vis);
        if (vis == View.INVISIBLE) {
            progressBar.setBackgroundResource(R.drawable.gif);
            animationDrawable = (AnimationDrawable) progressBar.getBackground();
            animationDrawable.start();
            progressBar.setVisibility(View.VISIBLE);
        } else {
            animationDrawable.stop();
            progressBar.setVisibility(View.INVISIBLE);
        }


    }

    private void manejadorTokenFacebook(AccessToken accessToken) {
        //Creamos una credencial en base al Token recibido por parametro

        AuthCredential credencial = FacebookAuthProvider.getCredential(accessToken.getToken());
        // Autenticamos al usuario el Firebase con esa credencial obtenida
        firebaseAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(estaVentana, "Error de login en Firebase con FaceBook", Toast.LENGTH_SHORT).show();
                } else {
                    visualizarBotones(View.INVISIBLE);
                    Toast.makeText(estaVentana, "Login en Firebase con FaceBook", Toast.LENGTH_SHORT).show();
                    new BDBAA().comprobarUID(estaVentana, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.d("AUTENTICADO", "onComplete: Autenticado con facebook");

                }
            }
        });

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
        if (escuchador != null) {
            firebaseAuth.removeAuthStateListener(escuchador);
        }
    }

    public void onClickIngresarVLogin(View view) {
        if (edtPass.getText().toString().isEmpty() || edtUser.getText().toString().isEmpty()) {
            Toast.makeText(this, "DEBE INSERTAR AMBOS DATOS", Toast.LENGTH_SHORT).show();
        } else {
            auth.loginMailPass(this, edtUser.getText().toString(), edtPass.getText().toString());
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // SI ALGO SALE MAL EN LA CONEXION...INFORMAR AL USUARIO
        Toast.makeText(ventanaPrincipal, "Por favor compruebe su conexión a la red.", Toast.LENGTH_SHORT).show();
    }

    public void onClickIngresoGoogle(View view) {
        Auth.GoogleSignInApi.signOut(clienteGoogle);
        Intent g = Auth.GoogleSignInApi.getSignInIntent(clienteGoogle);
        startActivityForResult(g, BandsnArts.CODIGO_DE_INICIO);
        gooogle = true;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!ON ACTIVITY FOR RESULT!!!!!!!!!!!!!!!!!
    /*
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Aqui !!!!!!!!!!!!!!!!
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case (BandsnArts.CODIGO_DE_INICIO):
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                compruebaResultado(result);
                break;
            //Este if es para saber que ha rellenado todo lo necesario en el logueo
            case (BandsnArts.CODIGO_DE_REGISTRO):
                FirebaseAuth.getInstance().signOut();
                break;
            default:
                switch (resultCode) {
                    case (BandsnArts.CODIGO_DE_CIERRE):
                        Toast.makeText(ventanaPrincipal, "Codigo cierre", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case (BandsnArts.CODIGO_DE_DESLOGUEO):
                        visualizarBotones(View.VISIBLE);
                        System.out.println("Ha sido deslogueado");
                        Toast.makeText(ventanaPrincipal, "Gracias por usar BANDS N' ARTS \n<3", Toast.LENGTH_LONG).show();
                        break;
                    case (BandsnArts.CODIGO_DE_REDSOCIAL):
                        // Clear data
                        LoginManager.getInstance().logOut();
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(ventanaPrincipal, "Codigo REDSOCIAL", Toast.LENGTH_LONG).show();
                        break;
                    case BandsnArts.CODIGO_DE_REGISTRO_RED_SOCIAL:
                        if (gooogle) {
                            Intent g = Auth.GoogleSignInApi.getSignInIntent(clienteGoogle);
                            startActivityForResult(g, BandsnArts.CODIGO_DE_INICIO);
                            gooogle = false;
                        }
                        Toast.makeText(ventanaPrincipal, "Codigo cierreRED", Toast.LENGTH_LONG).show();
                        break;
                }

        }
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            // Facebbook
        }
    }

    private void compruebaResultado(GoogleSignInResult result) {

        if (result.isSuccess()) {
            autenticarEnFirebase(result.getSignInAccount(), this);
        } else {
            Toast.makeText(this, "ERROR AL LOGAR", Toast.LENGTH_SHORT).show();
        }
    }

    private void autenticarEnFirebase(GoogleSignInAccount signInAccount, final Context context) {
        // Creamos una credencial y guardamos en ella el Token obtenido del objeto cuenta, el segundo
        // parametro es es access Token que no es necesario, le pasamos null

        AuthCredential credencial = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        // Autenticamos con firebase y agragamos un escuchador que nos dirá cuando termina
        firebaseAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "No se pudo autenticar con Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    visualizarBotones(View.INVISIBLE);
                    new BDBAA().comprobarUID(context, FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
        });
    }

    /*  private void siguienteActivity() {
          Intent i = new Intent(this, VentanaInicialApp.class);
          startActivity(i);
      }
  */
    public void onClickRegistrarVLogin(View view) {
        //sacar alert dialog para grupo o musico
        alertaBuilder = new AlertDialog.Builder(this);
        //si no, da error
        inflador = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View vista = inflador.inflate(R.layout.alertdialoggrupomusico, (ViewGroup) findViewById(R.id.alertaregistro));
        //hago aqui el find porque necesita la vista///////
        btnCancelarAlerta = vista.findViewById(R.id.btnCancelarVAlert);
        btnAceptarAlerta = vista.findViewById(R.id.btnAceptarVAlert);
        grupo = vista.findViewById(R.id.chkGrupoVAlert);
        musico = vista.findViewById(R.id.chkMusicoVAlert);
        ////////////////////////////////////////////////
        alerta = alertaBuilder.create();
        alerta.setView(vista);
        //para no poder usar el onbackpressed
        alerta.setCancelable(false);
        alerta.show();
        btnCancelarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cierra el alert
                alerta.cancel();
            }
        });
        //al pulsar aceptar se abrira una u otra ventana
        btnAceptarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(estaVentana).edit().putInt("intentos", 0).commit();
                if (grupo.isChecked()) {
                    startActivityForResult(new Intent(ventanaPrincipal, RegistrarGrupo.class), BandsnArts.CODIGO_DE_REGISTRO);
                    alerta.cancel();
                } else if (musico.isChecked()) {
                    startActivityForResult(new Intent(ventanaPrincipal, RegistarMusico.class), BandsnArts.CODIGO_DE_REGISTRO);
                    alerta.cancel();
                } else {
                    Toast.makeText(LoginActivity.this, "POR FAVOR, ELIJA ALGUNA OPCIÓN PARA CONTINUAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //control de los checkbox
        musico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grupo.setChecked(false);
            }
        });
        grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musico.setChecked(false);
            }
        });
    }

    /* public void onclick(View view) {
        startActivity(new Intent(this, VentanaInicialApp.class));
    }*/


    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();

    }
}
