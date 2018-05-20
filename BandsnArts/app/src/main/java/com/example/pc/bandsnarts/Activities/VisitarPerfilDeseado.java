package com.example.pc.bandsnarts.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.Visitar_Anuncios;
import com.example.pc.bandsnarts.Fragment_Visitar_Perfil.Visitar_Perfil;
import com.example.pc.bandsnarts.R;

public class VisitarPerfilDeseado extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actvity_visitar_perfil);


        BottomNavigationView navigation = findViewById(R.id.navVisitarPerfil);
        navigation.setOnNavigationItemSelectedListener(this);
        //carga de inicio visitar perfil
        switch (getIntent().getExtras().getInt("op")) {
            case 0:
                cargarFragment(new Visitar_Perfil(getIntent().getIntExtra("pos",-1),getIntent().getStringExtra("tipo")));
                getSupportActionBar().setTitle("Perfil");
                break;
            case 1:
                cargarFragment(new Visitar_Anuncios(getIntent().getIntExtra("pos",-1),getIntent().getStringExtra("tipo")));
                getSupportActionBar().setTitle("Anuncio");
                break;
            default:
                break;
        }
    }

    private boolean cargarFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_visitar_perfil, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    //Este metodo se llama al tocar en el menu y cambiar de fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        //saber que opcion esta seleccionada
        switch (item.getItemId()) {
            case R.id.itemperfilvisitado:
                fragment = new Visitar_Perfil(getIntent().getIntExtra("pos",-1),getIntent().getStringExtra("tipo"));
                getSupportActionBar().setTitle("Perfil");
                break;
            case R.id.itemanunciosvisitado:
                fragment = new Visitar_Anuncios(getIntent().getIntExtra("pos",-1),getIntent().getStringExtra("tipo"));
                getSupportActionBar().setTitle("Anuncio");
                BandsnArts.paraHilo = true;
                if (BandsnArts.mediaPlayer != null)
                    BandsnArts.mediaPlayer.stop();
                break;
        }

        return cargarFragment(fragment);
    }

    @Override
    public void onBackPressed() {

        BandsnArts.paraHilo = true;
        if (BandsnArts.mediaPlayer != null)
            BandsnArts.mediaPlayer.stop();
        finish();

    }
}
