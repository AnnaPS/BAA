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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * CLASE PARA INFLAR EL FRAGMENT DE LA VENTANA DE INICIO EN EL TAB DE LOCALES
 */
public class FragmentLocalesTabInicio extends Fragment{
    View vista;
    TextView nombreLocal,direcLocal,cpLocal,localidadLocal;
    public FragmentLocalesTabInicio(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.activity_inicio_google,container,false);
        nombreLocal = vista.findViewById(R.id.txtNombreVGoogle);
        direcLocal=vista.findViewById(R.id.txtDirecVGoogle);
        cpLocal = vista.findViewById(R.id.txtCPVGoogle);
        localidadLocal = vista.findViewById(R.id.txtLocVGoogle);

        pintarDatosLocal();
        return vista;
    }

    public void pintarDatosLocal(){

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference("locales");
        Query q = bd.orderByChild("nombre");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Log.d("\n\nPRUEBA","holaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                    Local loc =data.getValue(Local.class);
                    nombreLocal.setText(loc.getNombre());
                    direcLocal.setText(loc.getDireccion());
                    cpLocal.setText(loc.getCp());
                    localidadLocal.setText(loc.getLocalidad());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
