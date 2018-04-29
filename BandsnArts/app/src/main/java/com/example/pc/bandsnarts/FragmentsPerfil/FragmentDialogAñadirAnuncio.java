package com.example.pc.bandsnarts.FragmentsPerfil;

import android.app.DialogFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FragmentDialogAñadirAnuncio extends DialogFragment implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "AlertaAnuncio";
    Spinner spEstilo,spSexo,spInstrumento,spTipoBusqueda,spProvincia,spLocalidad;
    Button atras;
    TextView fecha;
    EditText titulo,descripcionAnuncio;
    FloatingActionButton Fabguardar;
    int posProvincia,posLocalidad;
    CharSequence[] localidades;

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
        spProvincia=vista.findViewById(R.id.spProvinciaAnuncio);
        spLocalidad=vista.findViewById(R.id.spLocalidadAnuncio);


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spInstrumento.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spTipoBusqueda.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.tipobusqueda)));


        loadSpinnerProvincias();
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
    private void loadSpinnerProvincias() {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.provincias, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        this.spProvincia.setAdapter(adapter);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spProvincia.setOnItemSelectedListener(this);
        this.spLocalidad.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
        switch (parent.getId()) {
            case R.id.spProvinciaAnuncio:
                posProvincia=pos;
                // Retrieves an array
                TypedArray arrayLocalidades = getResources().obtainTypedArray(
                        R.array.array_provincia_a_localidades);
                localidades = arrayLocalidades.getTextArray(pos);
                arrayLocalidades.recycle();

                // Create an ArrayAdapter using the string array and a default
                // spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        getApplicationContext(), R.layout.spinner_item,
                        localidades);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_item);

                // Apply the adapter to the spinner
                this.spLocalidad.setAdapter(adapter);
                break;

            case R.id.spLocalidadAnuncio:
                posLocalidad=pos;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
