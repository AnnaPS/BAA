package com.example.pc.bandsnarts.FragmentsPerfil;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * CLASE PARA ALERT DIALOG DE AÑADIR ANUNCIO
 */

public class FragmentDialogAñadirAnuncio extends DialogFragment{
    private static final String TAG = "AlertaAnuncio";
    Spinner spEstilo,spSexo,spInstrumento,spTipoBusqueda;
    Button atras;
    TextView fecha;
    EditText titulo,descripcionAnuncio;
    FloatingActionButton Fabguardar;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.alertdialoganadiranuncio, container, false);

        atras=vista.findViewById(R.id.btnAtrasAnuncio);
        fecha=vista.findViewById(R.id.txtFechaAnuncio);
        titulo=vista.findViewById(R.id.edtTituloAnuncio);
        descripcionAnuncio=vista.findViewById(R.id.edtAnuncio);

        spEstilo=vista.findViewById(R.id.spEstiloAnuncio);
        spSexo=vista.findViewById(R.id.spSexoAnuncio);
        spInstrumento=vista.findViewById(R.id.spInstrumentoAnuncio);
        spTipoBusqueda=vista.findViewById(R.id.spTipoBusqueda);


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spInstrumento.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spTipoBusqueda.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.tipobusqueda)));



        //OnClick floating

        Fabguardar = (FloatingActionButton) vista.findViewById(R.id.fabGuardarAnuncio);
        Fabguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "GUARDADO CON EXITO", Toast.LENGTH_SHORT).show();

                getDialog().dismiss();
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ATRAS", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });


        return vista;
    }


    /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach:ClassCastException: " + e.getMessage());
        }
    }*/

}
