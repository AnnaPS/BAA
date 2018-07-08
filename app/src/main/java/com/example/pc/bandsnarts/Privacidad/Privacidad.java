package com.example.pc.bandsnarts.Privacidad;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogAceptaPolitica;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogDescartarCancion;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentDialogSalirPolitica;
import com.example.pc.bandsnarts.FragmentsPerfil.FragmentMultimedia;
import com.example.pc.bandsnarts.Login.LoginActivity;
import com.example.pc.bandsnarts.R;

public class Privacidad extends AppCompatActivity {
    private TextView privacidad;
    private CheckBox chkAceptarPolitica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad);
        privacidad = findViewById(R.id.privacidad);
        privacidad.setMovementMethod(new ScrollingMovementMethod());
        chkAceptarPolitica = findViewById(R.id.chkAceptarPolitica);
        // Comprobar si recuerda la privacidad
        if (!PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("privacidad", "").equalsIgnoreCase("")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void clickSalirPrivacidad(View view) {
        //Alertdialog de advertencia
        android.app.FragmentManager fm = this.getFragmentManager();
        FragmentDialogSalirPolitica alerta = new FragmentDialogSalirPolitica(Privacidad.this, "¿ESTÁ SEGURO DE SALIR DE BANDS N'ARTS??", "Pulse Aceptar para salir.");
        alerta.setCancelable(false);
        alerta.show(fm, "AlertaPolitica");

    }

    public void clickAceptarPoli(View view) {
        // Si acepta sin marcar checkBox
        if(!chkAceptarPolitica.isChecked()){
            //Alertdialog de advertencia
            android.app.FragmentManager fm = this.getFragmentManager();
            FragmentDialogAceptaPolitica alerta = new FragmentDialogAceptaPolitica(Privacidad.this, "DEBE ACEPTAR LA POLÍTICA DE PRIVACIDAD", "");
            alerta.setCancelable(false);
            alerta.show(fm, "AlertaPolitica");
        }else{
            // Guardamos en preferencias
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("privacidad", "ACEPTO").commit();
            // Lanzamos la siguiente actividad y finalizamos esta
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
