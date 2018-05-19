package com.example.pc.bandsnarts.Fragment_Visitar_Perfil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.R;

public class Visitar_Perfil extends Fragment {

    View vista;
    ImageView imagenPerfil;
    TextView nombreUsuario, cajaEstilo, cajaDescripcion, cajaProvincia, cajaLocalidad, cajaSexo,
    cajaInstru1,cajaInstru2,cajaInstru3,cajaInstru4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_visitar_perfil, container, false);
        imagenPerfil =vista.findViewById(R.id.imgPerfilVVisitarPerfil);
        TextView nombreUsuario = vista.findViewById(R.id.txtNombUsuarioVVisitarMiPerfil);
        TextView cajaEstilo = vista.findViewById(R.id.txtEstiloVVisitarPerfil);
        TextView cajaDescripcion = vista.findViewById(R.id.txtDescripcionVVisitarPerfil);
        TextView cajaProvincia = vista.findViewById(R.id.txtProvinciaVVisitarPerfil);
        TextView cajaLocalidad = vista.findViewById(R.id.txtLocalidadVVisitarPerfil);
        TextView cajaSexo = vista.findViewById(R.id.txtSexoVVisitarPerfil);
        TextView cajaInstru1 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil1);
        TextView cajaInstru2 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil2);
        TextView cajaInstru3 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil3);
        TextView cajaInstru4 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil4);

        return vista;
    }
}
