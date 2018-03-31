package com.example.pc.bandsnarts;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView titulo;
    Typeface fuenteTitulo;

    EditText edtUser,edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo=findViewById(R.id.tituloVLogin);
        //asignar nueva fuente
        fuenteTitulo=Typeface.createFromAsset(getAssets(),"fonts/VtksSimplizinha.ttf");
        titulo.setTypeface(fuenteTitulo);

        edtUser = findViewById(R.id.edtUsuarioVLogin);
        edtPass = findViewById(R.id.edtPassVLogin);
    }



    public void onClickIngresarVLogin(View view) {
        if(edtPass.getText().toString().isEmpty()||edtUser.getText().toString().isEmpty()){
            Toast.makeText(this, "DEBE INSERTAR AMBOS DATOS", Toast.LENGTH_SHORT).show();
        }else{
            boolean a = new Autentificacion().loginMailPass(edtUser.getText().toString(),edtPass.getText().toString());
            Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();
        }
    }
}
