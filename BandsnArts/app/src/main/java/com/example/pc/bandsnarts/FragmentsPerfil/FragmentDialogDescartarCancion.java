package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentMiPerfil;
import com.example.pc.bandsnarts.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

@SuppressLint("ValidFragment")
public class FragmentDialogDescartarCancion extends DialogFragment {
    Button btnAcepar, btnCancelar;
    TextView tv_titulo, tv_subtitulo;
    private static final String TAG = "AlertaDescartar";
    private String titulo, subtilo;
    public static Object a = null;

    private interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    @SuppressLint("ValidFragment")
    public FragmentDialogDescartarCancion(Object a, String titulo, String subTitulo) {
        this.a = a;
        this.titulo = titulo;
        this.subtilo = subTitulo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.alertdiaolgdescartarcancion, container, false);

        btnAcepar = vista.findViewById(R.id.btnAceptarVDescartar);
        btnCancelar = vista.findViewById(R.id.btnCancelarVDescartar);
        tv_titulo = vista.findViewById(R.id.tv_titlo_alerta_descartar);
        tv_subtitulo = vista.findViewById(R.id.tv_subtitlo_alerta_descartar);
        tv_titulo.setText(titulo);
        tv_subtitulo.setText(subtilo);
        if(subtilo.equals("")){
            btnCancelar.setVisibility(View.GONE);
        }else{
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentMiPerfil.bottomTools.setVisibility(View.VISIBLE);
                    getDialog().dismiss();
                }
            });
        }

        btnAcepar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a instanceof FragmentMultimedia){
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference cancion = storageRef.child("audios/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                            + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".mpeg");
                    BDBAA.eliminarCancion(cancion);
                }
                getDialog().dismiss();
            }
        });


        return vista;
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
