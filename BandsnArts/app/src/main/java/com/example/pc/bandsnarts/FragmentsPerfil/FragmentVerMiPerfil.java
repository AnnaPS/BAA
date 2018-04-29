package com.example.pc.bandsnarts.FragmentsPerfil;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.R;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentVerMiPerfil extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spLocalidad, spProvincia, spSexo, spEstilo,spinnerInstrumentos1,spinnerInstrumentos2,spinnerInstrumentos3,spinnerInstrumentos4;
    EditText txtLocalidad, txtProvincia, txtSexo, txtEstilo,txtDescripcion;
    TextView ins1,ins2,ins3,ins4,preguntaInstrumentos;
    ImageView imgSiNo;
    FloatingActionButton miFAB;
    com.github.clans.fab.FloatingActionButton guardar,descartar;
    FloatingActionMenu miFABGuardarRechazar;
    Switch switchBuscar;
    BottomNavigationView navBotPerfil;
    View vista;
    private int posSexo,posEstilo,posLocalidad,posProvincia,posInst1,posInst2,posInst3,posInst4;
    private String buscando;

    CharSequence[] localidades;

    //Recogemos el AppBarLayout de instrumentos para poder esconderlo cuando edite un grupo
    CardView contenedorInstrumentos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_verperfil_v_fragment_perfil, container, false);

        spEstilo = vista.findViewById(R.id.spEstiloVVerMiPerfil);
        spLocalidad = vista.findViewById(R.id.spLocaliVVerMiPerfil);
        spProvincia = vista.findViewById(R.id.spProvinVVerMiPerfil);
        spSexo = vista.findViewById(R.id.spSexoVVerMiPerfil);
        spinnerInstrumentos1=vista.findViewById(R.id.spInstrumentoVVerMiPerfil1);
        spinnerInstrumentos2=vista.findViewById(R.id.spInstrumentoVVerMiPerfil2);
        spinnerInstrumentos3=vista.findViewById(R.id.spInstrumentoVVerMiPerfil3);
        spinnerInstrumentos4=vista.findViewById(R.id.spInstrumentoVVerMiPerfil4);
        ins1=vista.findViewById(R.id.txtInstrumentoVVerMiPerfil1);
        ins2=vista.findViewById(R.id.txtInstrumentoVVerMiPerfil2);
        ins3=vista.findViewById(R.id.txtInstrumentoVVerMiPerfil3);
        ins4=vista.findViewById(R.id.txtInstrumentoVVerMiPerfil4);
        guardar=vista.findViewById(R.id.fabGuardar);
        descartar=vista.findViewById(R.id.fabDescartar);
        txtDescripcion=vista.findViewById(R.id.txtDescripcionVVerMiPerfil);
        switchBuscar=vista.findViewById(R.id.swBuscando);
        imgSiNo=vista.findViewById(R.id.imgBuscandoVerMiPerfil);
       // navBotPerfil=vista.findViewById(R.id.bottomnav);
        txtEstilo = vista.findViewById(R.id.txtEstiloVVerMiPerfil);
        txtLocalidad = vista.findViewById(R.id.txtLocalidadVVerMiPerfil);
        txtProvincia = vista.findViewById(R.id.txtProvinciaVVerMiPerfil);
        txtSexo = vista.findViewById(R.id.txtSexoVVerMiPerfil);

        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spinnerInstrumentos1.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos2.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos3.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos4.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        ocultarSpinners();

        contenedorInstrumentos = vista.findViewById(R.id.appBarLayoutInstrumentos);
        preguntaInstrumentos = vista.findViewById(R.id.txtPregInstrum);

        new BDBAA().cargarDatosPerfil(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo","musico"), getApplicationContext());

        //BOTON FLOTANTE PARA EDITAR EL PERFIL
        miFAB = (FloatingActionButton) vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();

                // En funcion de si el usuario es músico o grupo
                editaPerfil(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo","musico"));
            }
        });
        miFABGuardarRechazar=(FloatingActionMenu)vista.findViewById(R.id.floatingGuardarDescartar);
        /*miFABGuardarRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // En funcion de si el usuario es músico o grupo
                Toast.makeText(getActivity(), "HOLAAA", Toast.LENGTH_SHORT).show();



            }
        });*/
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo","musico")){
                    case("musico"):
                        //Actualizamos los datos del musico
                        ArrayList<String> instrumentos= new ArrayList<>();
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst1]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst2]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst3]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst4]);

                        new BDBAA().modificarDatosUsuario("musico",view.getContext(),"default_musico.jpg",getResources().getStringArray(R.array.sexo)[posSexo]
                                ,getResources().getStringArray(R.array.estiloMusical)[posEstilo],instrumentos,txtDescripcion.getText().toString()
                                ,getResources().getStringArray(R.array.provincias)[posProvincia],localidades[posLocalidad].toString(),
                                buscando);
                        break;
                    case("grupo"):
                        //Actualizamos los datos del grupo
                        new BDBAA().modificarDatosUsuario("grupo",view.getContext(),"default_grupo.jpg",null
                                ,getResources().getStringArray(R.array.estiloMusical)[posEstilo],new ArrayList<String>(),txtDescripcion.getText().toString()
                                ,getResources().getStringArray(R.array.provincias)[posProvincia],localidades[posLocalidad].toString(),
                                buscando);
                        break;
                }


                Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_SHORT).show();

            }
        });
        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Descartar", Toast.LENGTH_SHORT).show();


                ///////// REVISAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!
                ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo","musico"));
                //////// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



            }
        });
        //Poner de inicio a invisible el boton de guardar / descartar
        miFABGuardarRechazar.setVisibility(View.INVISIBLE);
        //Poner de incio a invisible el switch
        switchBuscar.setVisibility(View.INVISIBLE);

        loadSpinnerProvincias();

        escuchadoresSpinner();

        return vista;
    }

    public void escuchadoresSpinner() {
        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSexo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
   /*     spLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posLocalidad=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posProvincia=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });*/
        spinnerInstrumentos1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst1=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst2=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst3=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst4=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    private void ocultarSpinners() {
        spSexo.setVisibility(View.INVISIBLE);
        spProvincia.setVisibility(View.INVISIBLE);
        spLocalidad.setVisibility(View.INVISIBLE);
        spEstilo.setVisibility(View.INVISIBLE);
        spinnerInstrumentos1.setVisibility(View.INVISIBLE);
        spinnerInstrumentos2.setVisibility(View.INVISIBLE);
        spinnerInstrumentos3.setVisibility(View.INVISIBLE);
        spinnerInstrumentos4.setVisibility(View.INVISIBLE);
    }


    private void mostrarSpinners(String tipo) {
        switch (tipo){
            case("musico"):
                spSexo.setVisibility(View.VISIBLE);
                spProvincia.setVisibility(View.VISIBLE);
                spLocalidad.setVisibility(View.VISIBLE);
                spEstilo.setVisibility(View.VISIBLE);
                spinnerInstrumentos1.setVisibility(View.VISIBLE);
                spinnerInstrumentos2.setVisibility(View.VISIBLE);
                spinnerInstrumentos3.setVisibility(View.VISIBLE);
                spinnerInstrumentos4.setVisibility(View.VISIBLE);
                break;
            case("grupo"):
                spProvincia.setVisibility(View.VISIBLE);
                spLocalidad.setVisibility(View.VISIBLE);
                spEstilo.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void ocultarSpinners(String tipo) {
        switch (tipo){
            case("musico"):
                spSexo.setVisibility(View.INVISIBLE);
                spProvincia.setVisibility(View.INVISIBLE);
                spLocalidad.setVisibility(View.INVISIBLE);
                spEstilo.setVisibility(View.INVISIBLE);
                spinnerInstrumentos1.setVisibility(View.INVISIBLE);
                spinnerInstrumentos2.setVisibility(View.INVISIBLE);
                spinnerInstrumentos3.setVisibility(View.INVISIBLE);
                spinnerInstrumentos4.setVisibility(View.INVISIBLE);
                break;
            case("grupo"):
                spProvincia.setVisibility(View.INVISIBLE);
                spLocalidad.setVisibility(View.INVISIBLE);
                spEstilo.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private void ocultaTextviews() {
        txtEstilo.setVisibility(View.INVISIBLE);
        txtLocalidad.setVisibility(View.INVISIBLE);
        txtProvincia.setVisibility(View.INVISIBLE);
        txtSexo.setVisibility(View.INVISIBLE);
        ins1.setVisibility(View.INVISIBLE);
        ins2.setVisibility(View.INVISIBLE);
        ins3.setVisibility(View.INVISIBLE);
        ins4.setVisibility(View.INVISIBLE);

    }

    private void mostrarComponentes() {
        txtEstilo.setVisibility(View.VISIBLE);
        txtLocalidad.setVisibility(View.VISIBLE);
        txtProvincia.setVisibility(View.VISIBLE);
        txtSexo.setVisibility(View.VISIBLE);
        ins1.setVisibility(View.VISIBLE);
        ins2.setVisibility(View.VISIBLE);
        ins3.setVisibility(View.VISIBLE);
        ins4.setVisibility(View.VISIBLE);
    }


    private void editaPerfil(String tipo) {

        switch (tipo){
            case("musico"):
                ocultaTextviews();
                mostrarSpinners("musico");
                miFAB.setVisibility(View.INVISIBLE);
                imgSiNo.setVisibility(View.INVISIBLE);
                miFABGuardarRechazar.setVisibility(View.VISIBLE);
                txtDescripcion.setEnabled(true);
                switchBuscar.setVisibility(View.VISIBLE);
                //Listener para el switch
                switchBuscar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                        if (check){
                            //si esta chequeado, es si
                            Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_SHORT).show();
                            buscando = "si";
                        }else{
                            //es no
                            Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                            buscando = "no";
                        }
                    }
                });
   /*         if (switchBuscar.isChecked()){
                //si
            }else{
                //no
            }
*/                //navBotPerfil.setVisibility(View.INVISIBLE);
                new BDBAA().cargarDatosPerfilEditar(vista, tipo, getApplicationContext());
                break;

            case("grupo"):
                ocultaTextviews();
                mostrarSpinners("grupo");
                miFAB.setVisibility(View.INVISIBLE);
                imgSiNo.setVisibility(View.INVISIBLE);
                miFABGuardarRechazar.setVisibility(View.VISIBLE);
                txtDescripcion.setEnabled(true);
                switchBuscar.setVisibility(View.VISIBLE);
                contenedorInstrumentos.setVisibility(View.GONE);
                preguntaInstrumentos.setVisibility(View.GONE);
                break;

        }


    }

    private void botonCancelarEdicionPerfil(){
        switch (PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo","musico")){

            case("grupo"):
                contenedorInstrumentos.setVisibility(View.GONE);
                preguntaInstrumentos.setVisibility(View.GONE);

            case("musico"):
                mostrarComponentes();
                ocultarSpinners();
                miFAB.setVisibility(View.VISIBLE);
                imgSiNo.setVisibility(View.VISIBLE);
                miFABGuardarRechazar.setVisibility(View.INVISIBLE);
                txtDescripcion.setEnabled(false);
                switchBuscar.setVisibility(View.INVISIBLE);
                break;

        }
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
            case R.id.spProvinVVerMiPerfil:
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

            case R.id.spLocaliVVerMiPerfil:
                posLocalidad=pos;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Callback method to be invoked when the selection disappears from this
        // view. The selection can disappear for instance when touch is
        // activated or when the adapter becomes empty.


    }


}