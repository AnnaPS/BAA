package com.example.pc.bandsnarts;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Autentificacion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Activity vLog;

    public Autentificacion(Activity loginActivity) {
        vLog = loginActivity;
    }



    // Paso de void a Firebase user para devolver el usuario
    public FirebaseUser registroMailPass(String user, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();



                            // pruebas..
                            FirebaseUser usuario = mAuth.getInstance().getCurrentUser();

                                // Name, email address, and profile photo Url
                                String name = usuario.getDisplayName();
                                String email = usuario.getEmail();
                                Uri photoUrl = usuario.getPhotoUrl();

                                // Check if user's email is verified
                                boolean emailVerified = usuario.isEmailVerified();

                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                // authenticate with your backend server, if you have one. Use
                                // FirebaseUser.getToken() instead.
                                String uid = usuario.getUid();

                            Toast.makeText(vLog, "nombre: "+name+"\ncorreo: "+email+"\nURL de la foto: "+photoUrl+"\nemail verificado: "+emailVerified, Toast.LENGTH_SHORT).show();



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        FirebaseUser usuario = mAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            // Name, email address, and profile photo Url
            String name = usuario.getDisplayName();
            String email = usuario.getEmail();
            Uri photoUrl = usuario.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = usuario.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = usuario.getUid();

            return usuario;
        }else{
            Toast.makeText(vLog, "usuario vale null", Toast.LENGTH_SHORT).show();
            return null;
        }

    }


    public void loginMailPass(String user, String password) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d("TAG", "loginUserWithEmail:success ");
                            Toast.makeText(vLog, "Todo bien.", Toast.LENGTH_SHORT).show();
                            //  FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            deslogueo();
                            Toast.makeText(vLog, "Se ha equivocado en sus credenciales ", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "loginUserWithEmail:failure", task.getException());
                        }
                    }
                });

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
