package com.example.pc.bandsnarts.FragmentsPerfil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
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
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.bandsnarts.Activities.RegistarMusico;
import com.example.pc.bandsnarts.Activities.VentanaInicialApp;
import com.example.pc.bandsnarts.BBDD.BDBAA;
import com.example.pc.bandsnarts.Container.BandsnArts;
import com.example.pc.bandsnarts.FragmentsMenuDrawer.FragmentInicio;
import com.example.pc.bandsnarts.R;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentVerMiPerfil extends Fragment
        //implements AdapterView.OnItemSelectedListener
         {

    Spinner spLocalidad, spProvincia, spSexo, spEstilo, spinnerInstrumentos1, spinnerInstrumentos2, spinnerInstrumentos3, spinnerInstrumentos4;
    EditText txtLocalidad, txtProvincia, txtSexo, txtEstilo, txtDescripcion;
    TextView ins1, ins2, ins3, ins4, preguntaInstrumentos;
    ImageView imgSiNo;
    FloatingActionButton miFAB, fabFoto;
    CircleImageView imgFotoPerfil;
    com.github.clans.fab.FloatingActionButton guardar, descartar;
    FloatingActionMenu miFABGuardarRechazar;
    Switch switchBuscar;
    BottomNavigationView navBotPerfil;
    FrameLayout mrView;
    View vista;
    private int posSexo, posEstilo, posInst1, posInst2, posInst3, posInst4;

   public static int posProvincia, posLocalidad;

    private String buscando;


    /////VARIABLES PARA USAR CAMARA Y GALERIA
    private static String APP_DIRECTORY = "MisBnAImagenes/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "BnAImagenes";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String mPath;

    //////////////////////

  public static CharSequence[] localidades;

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

        if (mayRequestStoragePermission()) {
            fabFoto.setEnabled(true);
        } else {
            fabFoto.setEnabled(false);
        }


        spEstilo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.estiloMusical)));
        spSexo.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.sexo)));
        spinnerInstrumentos1.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos2.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos3.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        spinnerInstrumentos4.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.instrumentos)));
        ocultarSpinners();

        contenedorInstrumentos = vista.findViewById(R.id.appBarLayoutInstrumentos);
        preguntaInstrumentos = vista.findViewById(R.id.txtPregInstrum);

        new BDBAA().cargarDatosPerfil(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"), getApplicationContext());

        //BOTON FLOTANTE PARA EDITAR EL PERFIL
        miFAB = (FloatingActionButton) vista.findViewById(R.id.floatingBPerfil);
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLEMENTAR LA FUNCIONALIDAD DEL BOTON
                Toast.makeText(getActivity(), "HAS PULSADO", Toast.LENGTH_SHORT).show();

                // En funcion de si el usuario es músico o grupo
                editaPerfil(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));
                ///////// REVISAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!
                ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));
                mostrarComponentes();
                botonCancelarEdicionPerfil();
                //////// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                editaPerfil(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));

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

                switch (PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("tipo", "musico")) {
                    case ("musico"):
                        //Actualizamos los datos del musico
                        ArrayList<String> instrumentos = new ArrayList<>();
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst1]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst2]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst3]);
                        instrumentos.add(getResources().getStringArray(R.array.instrumentos)[posInst4]);

                        new BDBAA().modificarDatosUsuario("musico", view.getContext(), getResources().getStringArray(R.array.sexo)[posSexo]
                                , getResources().getStringArray(R.array.estiloMusical)[posEstilo], instrumentos, txtDescripcion.getText().toString()
                                , getResources().getStringArray(R.array.provincias)[posProvincia], localidades[posLocalidad].toString(),
                                buscando);
                        break;
                    case ("grupo"):
                        //Actualizamos los datos del grupo
                        new BDBAA().modificarDatosUsuario("grupo", view.getContext(), null
                                , getResources().getStringArray(R.array.estiloMusical)[posEstilo], new ArrayList<String>(), txtDescripcion.getText().toString()
                                , getResources().getStringArray(R.array.provincias)[posProvincia], localidades[posLocalidad].toString(),
                                buscando);
                        break;
                }
                ///////// REVISAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!
                ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));
                mostrarComponentes();
                botonCancelarEdicionPerfil();
                ///////////////////////////////////////////////////
                Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_SHORT).show();


                /*final Fragment verperfil=new FragmentVerMiPerfil();
                FragmentManager fragment = getFragmentManager();
                FragmentTransaction fragmentTransaction=fragment.beginTransaction();
                fragmentTransaction.replace(R.id.contenedormiperfil,verperfil).commit();
                Toast.makeText(getActivity(), "ver perfil", Toast.LENGTH_SHORT).show();*/

                startActivity(new Intent(view.getContext(), VentanaInicialApp.class));
                VentanaInicialApp.a.finish();
            }
        });
        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Descartar", Toast.LENGTH_SHORT).show();


                ////LLAMADA AL ALERT DIALOG///////
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                FragmentDialogDescartarCambios alerta = new FragmentDialogDescartarCambios();
                alerta.show(fm, "AlertaDescartar");
                ////////////////////////////////////////

                ///////// REVISAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!
                ocultarSpinners(PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"));
                mostrarComponentes();
                botonCancelarEdicionPerfil();
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
    }//on create


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



        spinnerInstrumentos1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        spinnerInstrumentos3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posInst3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void ocultarSpinners(String tipo) {
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

    private void mostrarComponentes() {
        switch (PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico")) {
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
        new BDBAA().cargarDatosPerfil(vista, PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico"), getApplicationContext());

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
                            Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_SHORT).show();
                            buscando = "si";
                        } else {
                            //es no
                            Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                            buscando = "no";
                        }
                    }
                });

                ///boton para cambiar foto de perfil
                fabFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showOptions();


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
                            Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_SHORT).show();
                            buscando = "si";
                        } else {
                            //es no
                            Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                            buscando = "no";
                        }
                    }
                });
                fabFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showOptions();


                    }
                });

                break;

        }
        new BDBAA().cargarDatosPerfilEditar(vista, tipo, getApplicationContext());


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
        }
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(mrView, "Los permisos son necesarios para usar la aplicacion.",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //si le da al ok se aceptan los permisos
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        } else {
            //si es la primera vez que se les pide los permisos pasa por aqui
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;


    }


    //mostrar opciones
    private void showOptions() {
        Toast.makeText(vista.getContext(), "HOLIHA", Toast.LENGTH_SHORT).show();
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
            //Usamos el fecha/hora/minutos/segundos/milisegundos para poner nombre a la imagen y asi conseguir que no se repita el nombre
            Long timestamp = System.currentTimeMillis() / 1000;

            // MIRAR GUARDAR LA FOTO ASOCIANDO EL UID DEL USUARIO!!!!!!
            //  String imageName = timestamp.toString() + ".jpg";
            String imageName = FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg";


            //Le decimos donde queremos que se guarde la imagen. File.separator es lo mismo que /
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + imageName;
            Log.d("", "openCamera:                                 "+mPath);
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
    //para poder usar lo guardado


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
                    //ponerlo en la imagen


                    //decodofica la ruta y coge la imagen que esta contenida en la ruta
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                    Toast.makeText(vista.getContext(), "" + mPath, Toast.LENGTH_SHORT).show();

                    imgFotoPerfil.setImageBitmap(bitmap);
                    Log.d("PRUEBAS", "mPath:                 " + mPath);
                    //Guardamos la foto en el storage

                    new BDBAA().almacenarFotoPerfil(mPath.toString(), vista.getContext(), 0, null);
                    break;

                case SELECT_PICTURE:
                    //cogemos el dato que nos envia el activity result con data
                    Uri path = data.getData();


                    imgFotoPerfil.setImageURI(path);
                    Log.d("PRUEBAS", "path:                 " + path.getPath());
                    Log.d("PRUEBAS", "mPath:                 " + mPath);
                    new BDBAA().almacenarFotoPerfil(mPath, vista.getContext(), 1, path);


                    break;
            }
        }
    }


    //acepte o deniege los permisos pasa por aqui

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS) {
            //GRANDRESULTS hace referencia al array de los permisos de external y camera
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "PERMISOS ACEPTADOS", Toast.LENGTH_SHORT).show();
                fabFoto.setEnabled(true);

            } else {
                showExplanation();
            }
        }
    }

    //muestra la explicacion de porque se necesitan los permisos
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                getActivity().finish();
            }
        });
        builder.show();

    }

    private void botonCancelarEdicionPerfil() {
        switch (PreferenceManager.getDefaultSharedPreferences(vista.getContext()).getString("tipo", "musico")) {

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
      /*  this.spProvincia.setOnItemSelectedListener(this);
        this.spLocalidad.setOnItemSelectedListener(this);*/

    }

    public static void escuchas(final Context contextc, Spinner spProvincia, final Spinner spLocalidad ) {
        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posProvincia = position;
                // Retrieves an array
                TypedArray arrayLocalidades = contextc.getResources().obtainTypedArray(
                        R.array.array_provincia_a_localidades);
                localidades = arrayLocalidades.getTextArray(position);
                arrayLocalidades.recycle();

                // Create an ArrayAdapter using the string array and a default
                // spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        getApplicationContext(), R.layout.spinner_item,
                        localidades);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_item);

                // Apply the adapter to the spinner
                spLocalidad.setAdapter(adapter);
                spLocalidad.setSelection(FragmentVerMiPerfil.posLocalidad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posLocalidad = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int pos,
//                               long id) {
//        switch (parent.getId()) {
//            case R.id.spProvinVVerMiPerfil:
//                posProvincia = pos;
//                // Retrieves an array
//                TypedArray arrayLocalidades = getResources().obtainTypedArray(
//                        R.array.array_provincia_a_localidades);
//                localidades = arrayLocalidades.getTextArray(pos);
//                arrayLocalidades.recycle();
//
//                // Create an ArrayAdapter using the string array and a default
//                // spinner layout
//                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
//                        getApplicationContext(), R.layout.spinner_item,
//                        localidades);
//
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(R.layout.spinner_item);
//
//                // Apply the adapter to the spinner
//                this.spLocalidad.setAdapter(adapter);
//                break;
//
//            case R.id.spLocaliVVerMiPerfil:
//                posLocalidad = pos;
//                break;
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Callback method to be invoked when the selection disappears from this
//        // view. The selection can disappear for instance when touch is
//        // activated or when the adapter becomes empty.
//

    //}


}