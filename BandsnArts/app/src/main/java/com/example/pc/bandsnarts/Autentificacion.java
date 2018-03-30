package com.example.pc.bandsnarts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Autentificacion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean login;


    public boolean registroMailPass(String user, String password) {
        login = false;
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            login = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());

                        }

                        // ...
                    }
                });

        return login;
    }


    public boolean loginMailPass(String user, String password) {
        login = false;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "loginUserWithEmail:success");

                          //  FirebaseUser user = mAuth.getCurrentUser();


                            // NO FUNCIONA !??
                            login = false;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "loginUserWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                });

        return login;
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

    public boolean compruebaLogueado(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth!=null){
            deslogueo();
            return true;

        }else{

            return false;
        }

    }

}
