package com.example.pc.bandsnarts.FragmentsPerfil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.R;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class FragmentVerMiPerfil extends Fragment {

    Spinner spLocalidad, spProvincia, spSexo, spEstilo, spinnerInstrumentos1, spinnerInstrumentos2, spinnerInstrumentos3, spinnerInstrumentos4;
    EditText txtLocalidad, txtProvincia, txtSexo, txtEstilo, txtDescripcion;
    TextView ins1, ins2, ins3, ins4, preguntaInstrumentos;
    ImageView imgSiNo;
    FloatingActionButton miFAB, fabFoto;
    CircleImageView imgFotoPerfil;
    com.github.clans.fab.FloatingActionButton guardar, descartar;
    public static FloatingActionMenu miFABGuardarRechazar;
    Switch switchBuscar;
    FrameLayout mrView;
    View vista;

    private int posSexo, posEstilo, posInst1, posInst2, posInst3, posInst4, op;


    private String buscando;


    /////VARIABLES PARA USAR CAMARA Y GALERIA
    private static String APP_DIRECTORY = "MisBnAImagenes/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "BnAImagenes";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String mPath;
    private FragmentVerMiPerfil fragmentVerMiPerfil;
    private Uri rutaFotoPerfil;
    private ImageView progressEditarPerfil;
    //////////////////////


    //Recogemos el AppBarLayout de instrumentos para poder esconderlo cuando edite un grupo
    CardView contenedorInstrumentos;

    @SuppressLint("ValidFragment")
    public FragmentVerMiPerfil(int op) {
        if (op == 1) {
            this.op = op;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_verperfil_v_fragment_perfil, container, false);
        fragmentVerMiPerfil = this;
        progressEditarPerfil = vista.findViewById(R.id.progressBarVLoginEditarPerfil);
        spEstilo = vista.findViewById(R.id.spEstiloVVerMiPerfil);
        spLocalidad = vista.findViewById(R.id.spLocaliVVerMiPerfil);
        spProvincia = vista.findViewById(R.id.spProvinVVerMiPerfil);
        spSexo = vista.findViewById(R.id.spSexoVVerMiPerfil);
        spinnerInstrumentos1 = vista.findViewById(R.id.spInstrumentoVVerMiPerfil1);
        spinnerInstrumentos2 = vista.findViewById(R.id.spInstrumentoVVerMiPerfil2);
        spinnerInstrumentos3 = vista.findViewById(R.id.spInstrumentoVVerMiPerfil3);
        spinnerInstrumentos4 = vista.findViewById(R.id.spInstrumentoVVerMiPerfil4);
        ins1 = vista.findViewById(R.id.txtInstrumentoVVerMiPerfil1);
        ins2 = vista.findViewById(R.id.txtInstrumentoVVerMiPerfil2);
        ins3 = vista.findViewById(R.id.txtInstrumentoVVerMiPerfil3);
        ins4 = vista.findViewById(R.id.txtInstrumentoVVerMiPerfil4);
        guardar = vista.findViewById(R.id.fabGuardar);
        descartar = vista.findViewById(R.id.fabDescartar);
        txtDescripcion = vista.findViewById(R.id.txtDescripcionVVerMiPerfil);
        switchBuscar = vista.findViewById(R.id.swBuscando);
        imgSiNo = vista.findViewById(R.id.imgBuscandoVerMiPerfil);

        // navBotPerfil=vista.findViewById(R.id.bottomnav);
        txtEstilo = vista.findViewById(R.id.txtEstiloVVerMiPerfil);
        txtLocalidad = vista.findViewById(R.id.txtLocalidadVVerMiPerfil);
        txtProvincia = vista.findViewById(R.id.txtProvinciaVVerMiPerfil);
        txtSexo = vista.findViewById(R.id.txtSexoVVerMiPerfil);

        ///finds para usar cambio de foto de perfil
        imgFotoPerfil = vista.findViewById(R.id.imgPerfilVPerfil);
        fabFoto = (FloatingActionButton) vista.findViewById(R.id.fabFotoPerfil);
        mrView = vista.findViewById(R.id.vermiperfil);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spinnerInstrumentos1.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos2.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos3.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos4.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        ocultarSpinners();

        contenedorInstrumentos = vista.findViewById(R.id.appBarLayoutInstrumentos);
        preguntaInstrumentos = vista.findViewById(R.id.txtPregInstrum);

        BDBAA.cargarDatosPerfil(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""));

        //BOTON FLOTANTE PARA EDITAR EL PERFIL
        miFAB = (FloatingActionButton) vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();
                VentanaInicialApp.fragment.beginTransaction().replace(R.id.contenedor, new FragmentVerMiPerfil(1)).commit();
                ((AppCompatActivity) VentanaInicialApp.a).getSupportActionBar().setTitle("Perfil");


            }
        });
        miFABGuardarRechazar = (FloatingActionMenu) vista.findViewById(R.id.floatingGuardarDescartar);

        //poner a invisible al inicio el boton de cambiar foto
        fabFoto.setVisibility(View.INVISIBLE);
        miFABGuardarRechazar = (FloatingActionMenu) vista.findViewById(R.id.floatingGuardarDescartar);
        miFABGuardarRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // En funcion de si el usuario es músico o grupo
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posEstilo != 0) {
                    if (spinnerInstrumentos1.isShown() && posInst1 == 0) {
                        Toast.makeText(vista.getContext(), "Por favor seleccione almenos un instrumento principal.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!BandsnArts.quitarSaltos(txtDescripcion.getText().toString()).isEmpty()) {
                            switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "")) {
                                case ("musico"):
                                    //Actualizamos los datos del musico
                                    ArrayList<String> instrumentos = new ArrayList<>();
                                    instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst1]);
                                    instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst2]);
                                    instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst3]);
                                    instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst4]);
                                    BDBAA.modificarDatosUsuario("musico", view.getContext(), getResources().getStringArray(R.array.sexo)[posSexo]
                                            , getResources().getStringArray(R.array.estiloMusical)[posEstilo], instrumentos, BandsnArts.quitarSaltos(txtDescripcion.getText().toString())
                                            , getResources().getStringArray(R.array.provincias)[BandsnArts.posProvincia], BandsnArts.localidades[BandsnArts.posLocalidad].toString(),
                                            buscando);
                                    break;
                                case ("grupo"):
                                    //Actualizamos los datos del grupo
                                    BDBAA.modificarDatosUsuario("grupo", view.getContext(), null
                                            , getResources().getStringArray(R.array.estiloMusical)[posEstilo], new ArrayList<String>(), BandsnArts.quitarSaltos(txtDescripcion.getText().toString())
                                            , getResources().getStringArray(R.array.provincias)[BandsnArts.posProvincia], BandsnArts.localidades[BandsnArts.posLocalidad].toString(),
                                            buscando);
                                    break;
                            }

                            miFABGuardarRechazar.close(true);
                            if (rutaFotoPerfil != null) {
                                BDBAA.almacenarFotoPerfil(vista, rutaFotoPerfil, progressEditarPerfil);
                            } else {
                                BandsnArts.banderaLocalidad = false;
                                BDBAA.accesoFotoPerfil(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""), VentanaInicialApp.fotoPerfil, vista.getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());


                                android.app.FragmentManager fm = getActivity().getFragmentManager();

                                FragmentDialogDescartarCambios alerta = new FragmentDialogDescartarCambios(FragmentVerMiPerfil.this, "Se han guardado los cambios con exito", "");
                                alerta.setCancelable(false);
                                miFABGuardarRechazar.close(true);
                                alerta.show(fm, "AlertaDescartar");

                            }
                        } else {
                            Toast.makeText(vista.getContext(), "Por favor, inserte una descripción.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else

                {
                    Toast.makeText(vista.getContext(), "Por favor seleccione un estilo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        descartar.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Descartar", Toast.LENGTH_SHORT).show();

                BandsnArts.banderaLocalidad = false;
                ////LLAMADA AL ALERT DIALOG///////
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                FragmentDialogDescartarCambios alerta = new FragmentDialogDescartarCambios(FragmentVerMiPerfil.this, "¿ESTÁS SEGURO DE DESCARTAR LOS CAMBIOS?", "Los cambios se perderán");
                alerta.setCancelable(false);
                miFABGuardarRechazar.close(true);
                alerta.show(fm, "AlertaDescartar");
                ////////////////////////////////////////


            }
        });
        //Poner de inicio a invisible el boton de guardar / descartar
        miFABGuardarRechazar.setVisibility(View.INVISIBLE);
        //Poner de incio a invisible el switch
        switchBuscar.setVisibility(View.INVISIBLE);

        BandsnArts.loadSpinnerProvincias(spProvincia);

        escuchadoresSpinner();
        if (op == 1)        {
            // En funcion de si el usuario es músico o grupo
            ///////// REVISAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!
            ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""));
            mostrarComponentes();
            botonCancelarEdicionPerfil();
            //////// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            editaPerfil(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""));
        }
        return vista;
    }//on create


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


        spinnerInstrumentos1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spinnerInstrumentos1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spinnerInstrumentos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spinnerInstrumentos3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spinnerInstrumentos4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BandsnArts.ocultaTeclado(VentanaInicialApp.a);
                return false;
            }
        });
        spinnerInstrumentos4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst4 = position;
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
        switch (tipo) {
            case ("musico"):
                spSexo.setVisibility(View.VISIBLE);
                spProvincia.setVisibility(View.VISIBLE);
                spLocalidad.setVisibility(View.VISIBLE);
                spEstilo.setVisibility(View.VISIBLE);
                spinnerInstrumentos1.setVisibility(View.VISIBLE);
                spinnerInstrumentos2.setVisibility(View.VISIBLE);
                spinnerInstrumentos3.setVisibility(View.VISIBLE);
                spinnerInstrumentos4.setVisibility(View.VISIBLE);
                break;
            case ("grupo"):
                spProvincia.setVisibility(View.VISIBLE);
                spLocalidad.setVisibility(View.VISIBLE);
                spEstilo.setVisibility(View.VISIBLE);
                break;
        }

    }

    public void ocultarSpinners(String tipo) {
        switch (tipo) {
            case ("musico"):
                spSexo.setVisibility(View.INVISIBLE);
                spProvincia.setVisibility(View.INVISIBLE);
                spLocalidad.setVisibility(View.INVISIBLE);
                spEstilo.setVisibility(View.INVISIBLE);
                spinnerInstrumentos1.setVisibility(View.INVISIBLE);
                spinnerInstrumentos2.setVisibility(View.INVISIBLE);
                spinnerInstrumentos3.setVisibility(View.INVISIBLE);
                spinnerInstrumentos4.setVisibility(View.INVISIBLE);
                fabFoto.setVisibility(View.INVISIBLE);
                break;
            case ("grupo"):
                spProvincia.setVisibility(View.INVISIBLE);
                spLocalidad.setVisibility(View.INVISIBLE);
                spEstilo.setVisibility(View.INVISIBLE);
                fabFoto.setVisibility(View.INVISIBLE);
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

    public void mostrarComponentes() {
        switch (PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "")) {
            case "musico":
                txtEstilo.setVisibility(View.VISIBLE);
                txtLocalidad.setVisibility(View.VISIBLE);
                txtProvincia.setVisibility(View.VISIBLE);
                txtSexo.setVisibility(View.VISIBLE);
                ins1.setVisibility(View.VISIBLE);
                ins2.setVisibility(View.VISIBLE);
                ins3.setVisibility(View.VISIBLE);
                ins4.setVisibility(View.VISIBLE);
            case "grupo":
                txtEstilo.setVisibility(View.VISIBLE);
                txtLocalidad.setVisibility(View.VISIBLE);
                txtProvincia.setVisibility(View.VISIBLE);
                break;
        }
        BDBAA.cargarDatosPerfil(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", ""));

    }


    private void editaPerfil(String tipo) {

        switch (tipo) {
            case ("musico"):
                ocultaTextviews();
                mostrarSpinners("musico");
                miFAB.setVisibility(View.INVISIBLE);
                imgSiNo.setVisibility(View.INVISIBLE);
                //poner a invisible al inicio el boton de cambiar foto
                fabFoto.setVisibility(View.VISIBLE);
                miFABGuardarRechazar.setVisibility(View.VISIBLE);
                txtDescripcion.setEnabled(true);
                switchBuscar.setVisibility(View.VISIBLE);
                //Listener para el switch
                switchBuscar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                        if (check) {
                            //si esta chequeado, es si
                            buscando = "si";
                        } else {
                            //es no
                            buscando = "no";
                        }
                    }
                });


                break;

            case ("grupo"):
                ocultaTextviews();
                mostrarSpinners("grupo");
                miFAB.setVisibility(View.INVISIBLE);
                fabFoto.setVisibility(View.VISIBLE);
                imgSiNo.setVisibility(View.INVISIBLE);
                miFABGuardarRechazar.setVisibility(View.VISIBLE);
                txtDescripcion.setEnabled(true);
                switchBuscar.setVisibility(View.VISIBLE);
                contenedorInstrumentos.setVisibility(View.GONE);
                preguntaInstrumentos.setVisibility(View.GONE);
                switchBuscar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                        if (check) {
                            //si esta chequeado, es si
                            buscando = "si";
                        } else {
                            //es no
                            buscando = "no";
                        }
                    }
                });

                break;

        }
        ///boton para cambiar foto de perfil
        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mayRequestStoragePermission()) {
                    showOptions();
                } else {
                    // showExplanation();
                }
            }
        });
        // FragmentMiPerfil.bottomTools.setVisibility(View.INVISIBLE);

        BDBAA.cargarDatosPerfilEditar(vista, tipo, getApplicationContext());


    }

    //saber si tiene permisos para cambiar foto y abrir camara
    private boolean mayRequestStoragePermission() {
        //si el so es menor a la version 6 de android no se ven los permisos
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        //comprueba si los permisos estan aceptados
        if ((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && getContext().checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;


    }

    //mostrar opciones
    private void showOptions() {

        //contiene todas las opciones que contiene el alert dialog
        final CharSequence[] option = {"Hacer foto", "Elegir de galeria", "Cancelar"};
        //alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elija una opción");
        //carga las opciones y los onclicks de estas opciones
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (option[i] == "Hacer foto") {
                    openCamera();
                } else if (option[i] == "Elegir de galeria") {
                    //usamos un intent para abrir la aplicacion de galeria. Con ACTION_PICK se
                    //indica que se quiere elegir entre aplicaciones y el segugo parametro indica la aplicacion
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //escoge todas las imagenes que tenga en su alamacenamiento del telefono
                    intent.setType("image/*");
                    //createChooser abre un cuadro de dialogo con todas las apps que contienen imagenes para poder seleccionar donde
                    //SELECT_PICTURE indica de donde fue llamado el activity for result
                    startActivityForResult(intent.createChooser(intent, "¿Dónde está esa imagen tan molona?"), SELECT_PICTURE);
                } else {
                    //cancelar
                    dialog.dismiss();
                }
            }

        });
        builder.show();


    }

    //Metodo para abrir la camara
    private void openCamera() {
        Toast.makeText(getActivity(), "camaaaara", Toast.LENGTH_SHORT).show();
        //esta variable guarda la ruta del alamacenamiento externo del dispositivo
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        //indica si el directorio media_directory esta creado o no
        boolean isDirectoryCreated = file.exists();

        //comprueba si existe y si no crea los dos
        if (!isDirectoryCreated) {
            isDirectoryCreated = file.mkdirs();
        }
        //si ya esta creado
        if (isDirectoryCreated) {
            //  String imageName = timestamp.toString() + ".jpg";
            String imageName = FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg";

            //Le decimos donde queremos que se guarde la imagen. File.separator es lo mismo que /
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + imageName;
            Log.d("", "openCamera:                                 " + mPath);
            File newFile = new File(mPath);

            //Abrir la camara
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);

        }

    }

    //justo antes de que opencamera termine va a guardar  guarda el path
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    //para ver la foto que acabamos de hacer en la galeria
                    MediaScannerConnection.scanFile(getContext(),
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("External storage", " Scanned " + path + ":");
                                    Log.i("External storage", " --> Uri = " + uri);

                                }
                            });
                    //decodofica la ruta y coge la imagen que esta contenida en la ruta
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    rutaFotoPerfil = nuevaUri(bitmap, 0);
                    break;

                case SELECT_PICTURE:
                    //cogemos el dato que nos envia el activity result con data
                    Uri path1 = data.getData();
                    try {
                        // Creamos un objeto bitmap a partir de la URI
                        Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(mrView.getContext().getContentResolver(), path1);
                        rutaFotoPerfil = nuevaUri(bitmap2, 0);

                        Log.d("PRUEBAS", "path:                 " + path1);
                        Log.d("PRUEBAS", "mPath:                 " + mPath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Uri nuevaUri(Bitmap bitmap, int rotation) {
        // Reescalamos la imagen
        bitmap = BandsnArts.reescalarImagen(bitmap, 500, 500, rotation);
        // Establecemos la imagen en el fragement de edicion (NO SE GUARDA AUN EN BBDD)
        imgFotoPerfil.setImageBitmap(bitmap);

        // Creamos de nuevo la URI con la imagen reescalada para porsteriormente guardarla en la BBDD
        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes2);
        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Title", null);
        rutaFotoPerfil = Uri.parse(path);
        Log.d("PRUEBAS", "mPath:                 " + path);
        return rutaFotoPerfil;
    }


    //acepte o deniegue los permisos pasa por aqui
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS) {
            //GRANDRESULTS hace referencia al array de los permisos de external y camera
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "PERMISOS ACEPTADOS", Toast.LENGTH_SHORT).show();
                fabFoto.setEnabled(true);

            }
        }
    }

    //muestra la explicacion de porque se necesitan los permisos
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VentanaInicialApp.a);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para cambiar la foto necesita aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //abrir la configuracion para que le de permisos justo en el detalle de nuestra app
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
//                getActivity().finish();
            }
        });
        builder.show();

    }

    public void botonCancelarEdicionPerfil() {
        switch (PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "")) {

            case ("grupo"):
                contenedorInstrumentos.setVisibility(View.GONE);
                preguntaInstrumentos.setVisibility(View.GONE);

            case ("musico"):
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


}