package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivityPartner extends AppCompatActivity {

    private static final String PROVA = "RegisterActivityPartner";

    private ConstraintLayout registerPartnerLayout;
    private FirebaseAuth mAuth;
    private EditText etPIva;
    private EditText etConfermaPassword;
    private EditText etPassword;
    private EditText etEmailPartner;
    private TextView tvLoginPartner;
    private Button bLoginPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_partner);

        //Settaggio colore sfondo
        registerPartnerLayout = (ConstraintLayout) this.findViewById(R.id.registerPartnerLayout);
        registerPartnerLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPartner));

        mAuth = FirebaseAuth.getInstance();

        initUI();

    }

    public void initUI(){
        etPIva=(EditText) findViewById(R.id.etPIva);
        etConfermaPassword=(EditText)findViewById(R.id.etConfermaPassword);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etEmailPartner=(EditText)findViewById(R.id.etEmailPartner);
        tvLoginPartner=(TextView)findViewById(R.id.tvLoginPartner);
        bLoginPartner=(Button)findViewById(R.id.bLoginPartner);
        mAuth = FirebaseAuth.getInstance();
    }

    public void registraPartner (View view) {
        String PIvaPartner = etPIva.getText().toString();
        String emailPartner = etEmailPartner.getText().toString();
        String passwordPartner = etPassword.getText().toString();
        String confermaPasswordPartner = etConfermaPassword.getText().toString();
        etPIva.setText("");
        etEmailPartner.setText("");
        etPassword.setText("");
        etConfermaPassword.setText("");

        //controllo se i campi sono corretti e se si si crea l'utente (funzioni invocate per i controlli sottostanti)
        if (!PIvaValida(PIvaPartner))
            Toast.makeText(this, "Inserire una PartitaIva corretta di 11 caratteri", Toast.LENGTH_SHORT).show();
        else if (!emailPartnerValida(emailPartner))
            Toast.makeText(this, "Inserire una e-mail corretta", Toast.LENGTH_SHORT).show();
        else if (!passwordPartnerValida(passwordPartner, confermaPasswordPartner))
            Toast.makeText(this, "Occhio alle password: devono essere lunghe almeno 6 caratteri", Toast.LENGTH_SHORT).show();
        else
            createFirebaseUser(emailPartner,passwordPartner,PIvaPartner);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createFirebaseUser(String email, String password, final String PIva) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivityPartner.this, "Registrazione avvenuta con successo",
                                    Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(PROVA, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setPIva(PIva);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(PROVA, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivityPartner.this, "Autenticazione fallita",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });

    }


    public String getPIva() {

        String name = etPIva.getText().toString();
        return name;
    }

    private void setPIva(String PIva) {
        FirebaseUser user= mAuth.getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(PIva)
                .build();

        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.i(PROVA, "completata con successo");
                else
                    Log.i(PROVA, "errore");
            }
        });
    }

    private void updateUI(FirebaseUser user) {

    }

    public void logInPartner(View view) {
        Intent intent =new Intent(RegisterActivityPartner.this, LoginActivity.class);
        startActivity(intent);
    }

    //controllo sui campi inseriti per registrarsi

    private boolean PIvaValida(String PIva){
        if (PIva.length()==11)
            return true;
        else
            return false;
    }

    private boolean emailPartnerValida(String email){
        if (email.contains("@") && email.contains("."))
            return true;
        else
            return false;
    }

    private boolean passwordPartnerValida(String password1, String password2){
        if (password1.compareTo(password2) == 0 && password1.length()>5)
            return true;
        else
            return false;
    }


}
