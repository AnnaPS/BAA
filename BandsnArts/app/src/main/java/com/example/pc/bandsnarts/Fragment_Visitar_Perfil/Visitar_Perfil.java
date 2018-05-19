package com.example.pc.bandsnarts.Fragment_Visitar_Perfil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.bandsnarts.Adaptadores.RecyclerAdapterMusico;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.R;

@SuppressLint("ValidFragment")
public class Visitar_Perfil extends Fragment {

    View vista;
    ImageView imagenPerfil;
    String tipo;
    int pos;

    @SuppressLint("ValidFragment")
    public Visitar_Perfil(int pos, String tipo) {
        this.pos = pos;
        this.tipo = tipo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_visitar_perfil, container, false);
        imagenPerfil = vista.findViewById(R.id.imgPerfilVVisitarPerfil);
        TextView nombreUsuario = vista.findViewById(R.id.txtNombUsuarioVVisitarMiPerfil);
        TextView cajaEstilo = vista.findViewById(R.id.txtEstiloVVisitarPerfil);
        TextView cajaDescripcion = vista.findViewById(R.id.txtDescripcionVVisitarPerfil);
        TextView cajaProvincia = vista.findViewById(R.id.txtProvinciaVVisitarPerfil);
        TextView cajaLocalidad = vista.findViewById(R.id.txtLocalidadVVisitarPerfil);
        TextView tvSexo = vista.findViewById(R.id.tv_sexo);
        TextView cajaSexo = vista.findViewById(R.id.txtSexoVVisitarPerfil);
        TextView tvInstrumento = vista.findViewById(R.id.tv_instrumentos);
        View ll_inst1 = vista.findViewById(R.id.ll_inst1);
        TextView cajaInstru1 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil1);
        View ll_inst2 = vista.findViewById(R.id.ll_inst2);
        View ll_instsec1=vista.findViewById(R.id.ll_instsec1);
        TextView cajaInstru2 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil2);
        View ll_instsec2=vista.findViewById(R.id.ll_instsec2);
        TextView cajaInstru3 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil3);
        View ll_instsec3=vista.findViewById(R.id.ll_instsec3);
        TextView cajaInstru4 = vista.findViewById(R.id.txtInstrumentoVVisitarPerfil4);
        TextView[] instrumnetos = {cajaInstru1, cajaInstru2, cajaInstru3, cajaInstru4};
        BDBAA.cargarDatosVisitarPerfil(pos, vista, tipo, imagenPerfil, nombreUsuario, cajaEstilo, cajaDescripcion, cajaProvincia, cajaLocalidad, tvSexo, tvInstrumento, cajaSexo, instrumnetos, ll_inst1, ll_inst2,ll_instsec1,ll_instsec2,ll_instsec3);
        return vista;
    }
}
