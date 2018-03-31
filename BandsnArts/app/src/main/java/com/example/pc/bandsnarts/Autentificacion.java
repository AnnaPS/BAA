package com.example.pc.bandsnarts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Autentificacion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean login;

    public boolean registroMailPass(String user, String password) {
        cambiaBoolean(false);
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            cambiaBoolean(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        return login;
    }


    public boolean loginMailPass(String user, String password) {
        cambiaBoolean(false);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            cambiaBoolean(true);
                            Log.d("TAG", "loginUserWithEmail:success " + login);

                            //  FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "loginUserWithEmail:failure", task.getException());
                        }
                    }
                });
        return login;
    }

    private void cambiaBoolean(boolean valor) {
        login = valor;
    }

    public void deslogueo() {
        mAuth = FirebaseAuth.getInstance();
        // PARA CONTROL DEL DESLOGUEO 
        try {
            // COMPROBACION INTERNA DE DESLOGUEO
            Log.w("TAG", "" + mAuth.getCurrentUser().getUid());
        } catch (NullPointerException e) {
            Log.w("TAG", "USUARIO YA DESLOGUEADO");
        }
        mAuth.signOut();
        // Toast.makeText(this, ""+mAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();
    }

    public boolean compruebaLogueado() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            deslogueo();
            return true;
        } else {
            return false;
        }
    }


    public boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public boolean comprobarPass(String pass) {
        Pattern p = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}");
        return p.matcher(pass).matches();
    }

}
