package com.example.pc.bandsnarts.Activities;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Contactos.FragmentContactos;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.FragmentDialogFiltrarBusqueda;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentAyuda;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentConfiguracion;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentInicio;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMensajes;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Login.LoginActivity;
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
    public static ImageView fotoPerfil;
    private TextView txtNombre;
    public static int id;
    // Objeto para el usuario de Google
    public static GoogleApiClient clienteGoogle;
    public static Activity a;
    public static FragmentManager fragment;
    public NavigationView navigationView;

    public View vista;

    public static Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_inicial_app2);

        id = R.id.inicioMenuDrawer2;
        fragment = getSupportFragmentManager();
        //LO CREA POR DEFECTO CON EL LAYOUT DE NAVIGATION DRAWER//////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        a = this;
        getSupportActionBar().setTitle("Inicio");
        //Se establece como principal el fragment de inicio
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentInicio()).commit();
        //////////////////////////

        //CREADO POR DEFECTO CON LA ACTIVIDAD
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(id);

        //////////////////////////////////////////

        // Nos traemos la vista del NavigationHeder para poder pintar los datos del usuario.
        vista = navigationView.getHeaderView(0);
        fotoPerfil = vista.findViewById(R.id.ivFotoPerfilNav);
        txtNombre = vista.findViewById(R.id.txtNombreNavH);


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


        BDBAA.compruebaConexion(vista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BDBAA.compruebaConexion(vista);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        //En caso de que este desplegado el menu drawer al accionar este evento solo lo ocultara y
        // sino pues cerrara la app en segundo plano
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.inicioMenuDrawer2) {
            setResult(BandsnArts.CODIGO_DE_CIERRE);
            finish();
        } else if (id == R.id.mensajesChat) {
            // Estando en Perfil, volvemo a Inicio
            id = R.id.mensajesMenuDrawer2;
            navigationView.setCheckedItem(id);
            VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentContactos()).commit();
            ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Contactos");
        } else if (id == R.id.FragmentCambiarFoto|id == R.id.FragmentCancion|id == R.id.FragmentContactarAyuda|id == R.id.FragmentContactarConNosotros|id == R.id.FragmentFiltrar|id == R.id.FragmentModificarPerfil|id == R.id.FragmentPublicarAnuncio) {
            id = R.id.ayudaMenuDrawer2;
            VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentAyuda()).commit();
            ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Ayuda");
        } else if (id == R.id.perfilMenuDrawer2 || id == R.id.configuracionMenuDrawer2 || id == R.id.ayudaMenuDrawer2 || id == R.id.mensajesMenuDrawer2 || id == R.id.visitaperfil) {
            // Estando en Perfil, volvemo a Inicio
            id = R.id.inicioMenuDrawer2;
            navigationView.setCheckedItem(id);
            VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentInicio()).commit();
            ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Inicio");
            menu.getItem(0).setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ventana_inicial_app, menu);
        this.menu = menu;
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
        if (id == R.id.action_buscar) {
            FragmentDialogFiltrarBusqueda alerta = null;
            android.app.FragmentManager fm = VentanaInicialApp.a.getFragmentManager();

            if (VentanaInicialApp.id == R.id.inicioMenuDrawer2) {
                switch (FragmentInicio.viewPager.getCurrentItem()) {
                    case (0):
                        System.out.println("--------------->MUSICO");
                        alerta = new FragmentDialogFiltrarBusqueda("musico");
                        alerta.show(fm, "AlertaBusquedas");
                        break;
                    case (1):
                        System.out.println("--------------->GRUPO");
                        alerta = new FragmentDialogFiltrarBusqueda("grupo");
                        alerta.show(fm, "AlertaBusquedas");
                        break;
                    case (2):
                        System.out.println("--------------->SALAS");
                        break;
                    case (3):
                        System.out.println("--------------->LOCALES");
                        break;
                }

                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }


    //METODO PARA CONTROLAR CADA OPCION DEL NAVIGATION DRAWER
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragment = getSupportFragmentManager();
        id = item.getItemId();

        menu.getItem(0).setVisible(false);
        if (id == R.id.perfilMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentMiPerfil(0)).commit();
            getSupportActionBar().setTitle(item.getTitle());
            navigationView.setCheckedItem(id);
        } else if (id == R.id.configuracionMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentConfiguracion()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            id = R.id.configuracionMenuDrawer2;
            navigationView.setCheckedItem(id);
        } else if (id == R.id.ayudaMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentAyuda()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            id = R.id.ayudaMenuDrawer2;
            navigationView.setCheckedItem(id);
        } else if (id == R.id.cerrarMenuDrawer2) {
            //fragment.beginTransaction().replace(R.id.contenedor, new FragmentCerrarSesion()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            // Llamada al metodo de cerrar sesion.
            cerrarSesion();
        } else if (id == R.id.inicioMenuDrawer2) {
            menu.getItem(0).setVisible(true);
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentInicio()).commit();
            getSupportActionBar().setTitle(item.getTitle());
        } else if (id == R.id.mensajesMenuDrawer2) {
            fragment.beginTransaction().replace(R.id.contenedor, new FragmentContactos()).commit();
            getSupportActionBar().setTitle(item.getTitle());
            id = R.id.mensajesMenuDrawer2;
        }
        BandsnArts.paraHilo = true;
        if (BandsnArts.mediaPlayer != null) {
            BandsnArts.mediaPlayer.stop();
        }
        BDBAA.compruebaConexion(vista);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        BandsnArts.ocultaTeclado(VentanaInicialApp.a);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void datosUsuario(FirebaseUser usuario) {
        // Pintamos los datos del usuario
        BDBAA.cargarDrawerPerfil(this, PreferenceManager.getDefaultSharedPreferences(this).getString("tipo", ""), fotoPerfil, txtNombre);

        // identUsuGoogle.setText(usuario.getUid());
        // Mostramos por consola la URL de la imagen
        // Log.d("MIAPP", cuentaUsuario.getPhotoUrl().toString());
    }

    public void cerrarSesion() {
        firebaseAuth.signOut();
        // deslogueo en Facebook
        LoginManager.getInstance().logOut();
        Auth.GoogleSignInApi.signOut(clienteGoogle).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    clienteGoogle.disconnect();
                    System.out.println("Sesion cerrada");
                    //El primer digito indica la ventana y el segundo la vez que
                    setResult(BandsnArts.CODIGO_DE_DESLOGUEO);
                    VentanaInicialApp.this.finish();
                    // volverActivityLogin();
                } else {
                    System.out.println("Sesion no cerrada");
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
        if (escuchador != null) {
            firebaseAuth.removeAuthStateListener(escuchador);
        }
    }


    public void onclickPrueba(View view) {
        Toast.makeText(a, "Pruebas", Toast.LENGTH_SHORT).show();
    }
}
