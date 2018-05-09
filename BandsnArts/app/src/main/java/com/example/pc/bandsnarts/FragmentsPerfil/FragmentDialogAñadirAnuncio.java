package com.example.pc.bandsnarts.FragmentsPerfil;

import android.app.DialogFragment;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * CLASE PARA ALERT DIALOG DE AÑADIR ANUNCIO
 */

public class FragmentDialogAñadirAnuncio extends DialogFragment {
    private static final String TAG = "AlertaAnuncio";
    Spinner spEstilo, spSexo, spInstrumento, spTipoBusqueda, spProvincia, spLocalidad;
    Button atras;
    TextView fecha;
    EditText titulo, descripcionAnuncio;
    FloatingActionButton Fabguardar;
    int posEstilo, posInst, posSexo, posTipo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View vista = inflater.inflate(R.layout.alertdialoganadiranuncio, container, false);

        atras = vista.findViewById(R.id.btnAtrasAnuncio);
        fecha = vista.findViewById(R.id.txtFechaAnuncio);
        titulo = vista.findViewById(R.id.edtTituloAnuncio);
        descripcionAnuncio = vista.findViewById(R.id.edtAnuncio);

        spEstilo = vista.findViewById(R.id.spEstiloAnuncio);
        spSexo = vista.findViewById(R.id.spSexoAnuncio);
        spInstrumento = vista.findViewById(R.id.spInstrumentoAnuncio);
        spTipoBusqueda = vista.findViewById(R.id.spTipoBusqueda);
        spProvincia = vista.findViewById(R.id.spProvinciaAnuncio);
        spLocalidad = vista.findViewById(R.id.spLocalidadAnuncio);


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spInstrumento.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spTipoBusqueda.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.tipobusqueda)));
        escuchadoresSpinner();

        BandsnArts.loadSpinnerProvincias(spProvincia);
        BDBAA.cargarDatosAnuncio(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""), vista.getContext(), spProvincia, spLocalidad);
        //OnClick floating

        Fabguardar = (FloatingActionButton) vista.findViewById(R.id.fabGuardarAnuncio);
        Fabguardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BandsnArts.banderaLocalidad = false;
                if (!titulo.getText().toString().equals("")) {
                    if (!descripcionAnuncio.getText().toString().equals("")) {
                        if (posEstilo != 0) {
                            if (posInst != 0) {
                                if (BandsnArts.posProvincia != 0) {
                                    BDBAA.agregarAnuncio(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                            PreferenceManager.getDefaultSharedPreferences(VentanaInicialApp.a).getString("tipo", ""),
                                            titulo.getText().toString(), descripcionAnuncio.getText().toString(),
                                            getResources().getStringArray(R.array.tipobusqueda)[posTipo],
                                            new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                                            getResources().getStringArray(R.array.provincias)[BandsnArts.posProvincia],
                                            BandsnArts.localidades[BandsnArts.posLocalidad].toString(),
                                            getResources().getStringArray(R.array.estiloMusical)[posEstilo],
                                            getResources().getStringArray(R.array.instrumentos)[posInst],
                                            getResources().getStringArray(R.array.sexo)[posSexo]);
                                    getDialog().dismiss();
                                }
                            }
                        }
                    }
                }
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ATRAS", Toast.LENGTH_SHORT).show();
                BandsnArts.banderaLocalidad = false;

                getDialog().dismiss();
            }
        });


        return vista;
    }

    public void escuchadoresSpinner() {
        spTipoBusqueda.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });


        spTipoBusqueda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posTipo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spSexo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });


        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSexo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spEstilo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spEstilo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posEstilo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        spInstrumento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spInstrumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

}
