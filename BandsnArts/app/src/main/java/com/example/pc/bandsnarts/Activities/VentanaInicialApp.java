package com.example.pc.bandsnarts.Activities;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.bumptech.glide.Glide;

import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentAyuda;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentCerrarSesion;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentConfiguracion;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentInicio;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//IMPORTATE IMPLEMENTAR EL LISTENER DE CADA FRAGMENT
public class VentanaInicialApp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {


    // Objeto FirebaseAuth y su escuchador
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener escuchador;
    private ImageView fotoPerfil;
    private TextView txtNombre,txtCorreo;

    // Objeto para el usuario de Google
    private GoogleApiClient clienteGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_inicial_app2);

        //LO CREA POR DEFECTO CON EL LAYOUT DE NAVIGATION DRAWER//////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


      //Se establece como principal el fragment de inicio
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new FragmentInicio()).commit();
        //////////////////////////

        //CREADO POR DEFECTO CON LA ACTIVIDAD
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////

        // Nos traemos la vista del NavigationHeder para poder pintar los datos del usuario.
        View vista = navigationView.getHeaderView(0);
        fotoPerfil = vista.findViewById(R.id.ivFotoPerfilNav);
        txtNombre =vista.findViewById(R.id.txtNombreNavH);
        txtCorreo = vista.findViewById(R.id.txtCorreoNavH);

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
                }            }

        };

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ventana_inicial_app, menu);
        return true;
    }

    //METODO PARA EL MENU DEFAULT DE LA DERECHA
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //METODO PARA CONTROLAR CADA OPCION DEL NAVIGATION DRAWER
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragment = getSupportFragmentManager();

        int id = item.getItemId();

        if (id == R.id.perfilMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            Toast.makeText(this, "perfil", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.configuracionMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentConfiguracion()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            Toast.makeText(this, "conf", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.ayudaMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentAyuda()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            Toast.makeText(this, "ayuda", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.cerrarMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentCerrarSesion()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            Toast.makeText(this, "salir", Toast.LENGTH_SHORT).show();
            // Llamada al metodo de cerrar sesion.
            cerrarSesion();

        }else if(id==R.id.inicioMenuDrawer2){
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentInicio()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            Toast.makeText(this, "inicio", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    ///////////////////////////////////////////////////////



    private void datosUsuario(FirebaseUser usuario) {
        // Pintamos los datos del usuario
        txtNombre.setText(usuario.getDisplayName());
        txtCorreo.setText(usuario.getEmail());
        //tamaño estatico con override para la fotografia 
        Glide.with(getApplicationContext()).load(usuario.getPhotoUrl()).override(200,200).into(fotoPerfil);
        // identUsuGoogle.setText(usuario.getUid());
        // Mostramos por consola la URL de la imagen
        // Log.d("MIAPP", cuentaUsuario.getPhotoUrl().toString());
    }

    public void cerrarSesion() {
        //Deslogueo en Google
        firebaseAuth.signOut();
        // deslogueo en Facebook
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(clienteGoogle).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Toast.makeText(VentanaInicialApp.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                    finish();
                    // volverActivityLogin();
                }else{
                    Toast.makeText(VentanaInicialApp.this, "Sesion no cerrada", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
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
}
