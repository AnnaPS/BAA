package com.example.pc.bandsnarts.FragmentsPerfil;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.R;

import static com.facebook.FacebookSdk.getApplicationContext;



public class FragmentVerMiPerfil extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spLocalidad,spProvincia,spSexo,spEstilo;
    TextView txtLocalidad,txtProvincia,txtSexo,txtEstilo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_verperfil_v_fragment_perfil, container, false);

        spEstilo = vista.findViewById(R.id.spEstiloVVerMiPerfil);
        spLocalidad = vista.findViewById(R.id.spLocaliVVerMiPerfil);
        spProvincia = vista.findViewById(R.id.spProvinVVerMiPerfil);
        spSexo = vista.findViewById(R.id.spSexoVVerMiPerfil);

        txtEstilo = vista.findViewById(R.id.txtEstiloVVerMiPerfil);
        txtLocalidad = vista.findViewById(R.id.txtLocalidadVVerMiPerfil);
        txtProvincia = vista.findViewById(R.id.txtProvinciaVVerMiPerfil);
        txtSexo = vista.findViewById(R.id.txtSexoVVerMiPerfil);

        ocultarSpinners();

        new BDBAA().cargarDatosPerfil(vista,"musico",getApplicationContext());
        new BDBAA().cargarDatosPerfil(vista,"grupo",getApplicationContext());
        //BOTON FLOTANTE PARA EDITAR EL PERFIL
        FloatingActionButton miFAB=(FloatingActionButton)vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();

                // En funcion de si el usuario es músico o grupo
                editaPerfil("musico");



        }
        });

        loadSpinnerProvincias();
        return vista;
    }



    private void ocultarSpinners(){
        spSexo.setVisibility(View.INVISIBLE);
        spProvincia.setVisibility(View.INVISIBLE);
        spLocalidad.setVisibility(View.INVISIBLE);
        spEstilo.setVisibility(View.INVISIBLE);
    }


    private void mostrarSpinners(){
        spSexo.setVisibility(View.VISIBLE);
        spProvincia.setVisibility(View.VISIBLE);

        spLocalidad.setVisibility(View.VISIBLE);
        spEstilo.setVisibility(View.VISIBLE);
    }

    private void ocultaTextviews(){
        txtEstilo.setVisibility(View.INVISIBLE);
        txtLocalidad.setVisibility(View.INVISIBLE);
        txtProvincia.setVisibility(View.INVISIBLE);
        txtSexo.setVisibility(View.INVISIBLE);
    }


    private void editaPerfil(String tipo){
        if(tipo.equals("musico")){
            ocultaTextviews();
            mostrarSpinners();
        }
    }

    private void loadSpinnerProvincias() {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.provincias, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                        getApplicationContext(), android.R.layout.simple_spinner_item,
                        android.R.id.text1, localidades);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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