package com.example.pc.bandsnarts.FragmentsTabLayoutsInicio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.bandsnarts.Objetos.Local;
import com.example.pc.bandsnarts.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE SALAS
 */
public class FragmentSalasTabInicio extends Fragment {
    View vista;

    TextView nombreSala, direcSala, cpSala, localidadSala;

    public FragmentSalasTabInicio() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* vista=inflater.inflate(R.layout.tab_salas_fragment,container,false);*/
        vista = inflater.inflate(R.layout.activity_inicio_google, container, false);

        nombreSala = vista.findViewById(R.id.txtNombreVGoogle);
        direcSala = vista.findViewById(R.id.txtDirecVGoogle);
        cpSala = vista.findViewById(R.id.txtCPVGoogle);
        localidadSala = vista.findViewById(R.id.txtLocVGoogle);
        pintarDatosSala();
        return vista;
    }

    public void pintarDatosSala() {

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("salas");
        Query q = bd.orderByChild("nombre");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Local loc = data.getValue(Local.class);
                    nombreSala.setText(loc.getNombre());
                    direcSala.setText(loc.getDireccion());
                    cpSala.setText(loc.getCp());
                    localidadSala.setText(loc.getLocalidad());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
