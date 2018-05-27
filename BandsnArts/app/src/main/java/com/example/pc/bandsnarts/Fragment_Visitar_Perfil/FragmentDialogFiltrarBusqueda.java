package com.example.pc.bandsnarts.Fragment_Visitar_Perfil;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
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
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentInicio;
import com.example.pc.bandsnarts.Objetos.Musico;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * CLASE PARA ALERT DIALOG DE AÑADIR ANUNCIO
 */

@SuppressLint("ValidFragment")
public class FragmentDialogFiltrarBusqueda extends DialogFragment {
    private static final String TAG = "AlertaAnuncio";
    Spinner spEstilo, spSexo, spInstrumento, spProvincia, spLocalidad;
    FloatingActionButton Fabguardar;
    int posEstilo, posInst, posSexo, posControl, posEditar;
    View vista;
    String tipo;
    Button atras;
    TextView sexo,instrumento;

    public FragmentDialogFiltrarBusqueda() {
    }

    public FragmentDialogFiltrarBusqueda(String tipo) {
        this.tipo = tipo;
    }

    public FragmentDialogFiltrarBusqueda(int posControl, int posEditar) {
        this.posControl = posControl;
        this.posEditar = posEditar;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.alertdialog_filtrar_busqueda, container, false);
        atras = vista.findViewById(R.id.btnAtrasBusqueda);

        spEstilo = vista.findViewById(R.id.spEstiloFiltrar);
        spSexo = vista.findViewById(R.id.spSexoFiltrar);
        spInstrumento = vista.findViewById(R.id.spInstrumentoFiltrar);

        spProvincia = vista.findViewById(R.id.spProvinciaFiltrar);
        spLocalidad = vista.findViewById(R.id.spLocalidadFiltrar);


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spInstrumento.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        escuchadoresSpinner();

        BandsnArts.loadSpinnerProvincias(spProvincia);
        BandsnArts.escuchas(vista.getContext(), spProvincia, spLocalidad);

        sexo = vista.findViewById(R.id.txtTextoSexoAlertBusquedas);
        instrumento = vista.findViewById(R.id.txtTextoInstrumentoAlertBusquedas);

        switch (FragmentInicio.viewPager.getCurrentItem()) {
            case (0):
                System.out.println("TAB MUSICO");
                break;
            case (1):
                System.out.println("TAB GRUPO");
                spInstrumento.setVisibility(View.GONE);
                spSexo.setVisibility(View.GONE);
                sexo.setVisibility(View.GONE);
                instrumento.setVisibility(View.GONE);
                break;
        }

        Fabguardar = (FloatingActionButton) vista.findViewById(R.id.fabGuardarFiltrar);
        Fabguardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BandsnArts.banderaLocalidad = false;

                if (posEstilo == 0 && posInst == 0 && posSexo == 0 && BandsnArts.posProvincia == 0) {
                    Toast.makeText(vista.getContext(), "Debe seleccionar al menos un dato", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("-------------------------->  FragmentInicio.viewPager.getCurrentItem()= "+FragmentInicio.viewPager.getCurrentItem());
                    FragmentInicio.viewPager.getCurrentItem();
                    switch (FragmentInicio.viewPager.getCurrentItem()) {
                        case(0):
                            BDBAA.busqueda(posEstilo, posInst, posSexo, BandsnArts.posProvincia, BandsnArts.posLocalidad, "musico");
                            break;

                        case(1):
                            BDBAA.busqueda(posEstilo, posInst, posSexo, BandsnArts.posProvincia, BandsnArts.posLocalidad, "grupo");
                            break;

                    }

                    dismiss();
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return vista;
    }

    public void escuchadoresSpinner() {

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
