package com.example.pc.bandsnarts;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

//IMPORTATE IMPLEMENTAR EL LISTENER DE CADA FRAGMENT
public class VentanaInicialApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentMiPerfil.OnFragmentInteractionListener,
        FragmentConfiguracion.OnFragmentInteractionListener,FragmentAyuda.OnFragmentInteractionListener,FragmentCerrarSesion.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_inicial_app2);


        //LO CREA POR DEFECTO CON EL LAYOUT DE NAVIGATION DRAWER//////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        Fragment fragment = null;
        //para comprobar si estamos en algun fragment
        boolean transaccionFragment = false;

        int id = item.getItemId();

        if (id == R.id.perfilMenuDrawer2) {
            fragment = new FragmentMiPerfil();
            transaccionFragment = true;

        } else if (id == R.id.configuracionMenuDrawer2) {
            Log.i("NavigationDrawer", "Opcion configuracion");

        } else if (id == R.id.ayudaMenuDrawer2) {
            Log.i("NavigationDrawer", "Opcion ayuda");
        } else if (id == R.id.cerrarMenuDrawer2) {
            Log.i("NavigationDrawer", "Opcion salir");
        }

        if (transaccionFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_ventana_inicial, fragment)
                    .commit();
            //el item viene por parametro,usamos estas lineas para saber en que opcion está
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //METODO IMPLEMENTADO DE LOS DIFERENTES FRAGMENTS CREADOS
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    ///////////////////////////////////////////////////////
}
