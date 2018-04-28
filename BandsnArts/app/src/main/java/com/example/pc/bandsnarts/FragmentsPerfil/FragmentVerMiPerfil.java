package com.example.pc.bandsnarts.FragmentsPerfil;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentVerMiPerfil extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spLocalidad, spProvincia, spSexo, spEstilo,spinnerInstrumentos1,spinnerInstrumentos2,spinnerInstrumentos3,spinnerInstrumentos4;
    EditText txtLocalidad, txtProvincia, txtSexo, txtEstilo,txtDescripcion;
    TextView ins1,ins2,ins3,ins4;
    ImageView imgSiNo;
    FloatingActionButton miFAB;
    FloatingActionMenu miFABGuardarRechazar;
    Switch switchBuscar;
    BottomNavigationView navBotPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_verperfil_v_fragment_perfil, container, false);

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

        new BDBAA().cargarDatosPerfil(vista, "musico", getApplicationContext());
        new BDBAA().cargarDatosPerfil(vista, "grupo", getApplicationContext());
        //BOTON FLOTANTE PARA EDITAR EL PERFIL
        miFAB = (FloatingActionButton) vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();

                // En funcion de si el usuario es músico o grupo
                editaPerfil("musico");


            }
        });
        miFABGuardarRechazar=(FloatingActionMenu)vista.findViewById(R.id.floatingGuardarDescartar);
        miFABGuardarRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // En funcion de si el usuario es músico o grupo
                Toast.makeText(getActivity(), "HOLAAA", Toast.LENGTH_SHORT).show();



            }
        });
        //Poner de inicio a invisible el boton de guardar / descartar
        miFABGuardarRechazar.setVisibility(View.INVISIBLE);
        //Poner de incio a invisible el switch
        switchBuscar.setVisibility(View.INVISIBLE);
        loadSpinnerProvincias();
        return vista;
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


    private void mostrarSpinners() {
        spSexo.setVisibility(View.VISIBLE);
        spProvincia.setVisibility(View.VISIBLE);
        spLocalidad.setVisibility(View.VISIBLE);
        spEstilo.setVisibility(View.VISIBLE);
        spinnerInstrumentos1.setVisibility(View.VISIBLE);
        spinnerInstrumentos2.setVisibility(View.VISIBLE);
        spinnerInstrumentos3.setVisibility(View.VISIBLE);
        spinnerInstrumentos4.setVisibility(View.VISIBLE);
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
        if (tipo.equals("musico")) {
            ocultaTextviews();
            mostrarSpinners();
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
                    }else{
                        //es no
                        Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (switchBuscar.isChecked()){
                //si
            }else{
                //no
            }

            //navBotPerfil.setVisibility(View.INVISIBLE);
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

                // Retrieves an array
                TypedArray arrayLocalidades = getResources().obtainTypedArray(
                        R.array.array_provincia_a_localidades);
                CharSequence[] localidades = arrayLocalidades.getTextArray(pos);
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