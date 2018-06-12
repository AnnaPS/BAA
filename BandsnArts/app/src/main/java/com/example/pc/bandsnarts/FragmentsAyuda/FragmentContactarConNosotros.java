package com.example.pc.bandsnarts.FragmentsAyuda;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.R;

public class FragmentContactarConNosotros extends Fragment {
    View vista;

    Button enviarMail;
    final String receptor = "bandsnarts@gmail.com";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VentanaInicialApp.id = R.id.FragmentContactarConNosotros;
        vista = inflater.inflate(R.layout.fragment_fragment_contactar_con_nosotros, container, false);

        enviarMail = vista.findViewById(R.id.btnEnviarMail);
        enviarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] TO = {receptor}; //Direcciones email  a enviar.


                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PROBLEMA EN BANDS N' ARTS");


                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email."));
                    Log.i("EMAIL", "Enviando email...");
                    //Toast.makeText(getContext(),"Email enviado con exito. Gracias por tu colaboración",Toast.LENGTH_LONG).show();


                }
                catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();

                }

            }
        });
        return vista;
    }


}
