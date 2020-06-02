package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.BreakIterator;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity";
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;

    private EditText name;
    private EditText username;
    private EditText password;
    private EditText idToken;

    BreakIterator tvMessaggio;

    private final static String MY_PREFERENCES = "MyPref";
    private final static String TEXT_DATA_KEY = "textData";
    private ConstraintLayout loginLayout;

    //private DatabaseReference databaseReference = database


    //Google sign-in
    SignInButton button;
    GoogleSignInClient googleSignInClient;

    //per DB utente
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {

            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            Log.i(TAG, "nome" + name);

            String email = currentUser.getEmail();
            Log.i(TAG, "email" + email);

            Uri photoUrl = currentUser.getPhotoUrl();


            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();
            Log.i(TAG, "email verificata" + emailVerified);

            // The user's ID, unique to the Firebase project.
            //FirebaseUser.getIdToken();
            String uid = currentUser.getUid();
            Log.i(TAG, "uid" + uid);
            Log.i(TAG, "connesso");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        //sfondo

        loginLayout = (ConstraintLayout) this.findViewById(R.id.loginLayout);
        loginLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
    }

    private void initUI() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.et_email);

        username = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        button = findViewById(R.id.sign_in_button);

        // Configure Google Sign In
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient= GoogleSignIn.getClient(this, options);
        button.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                signIn();
            }
        });

        //inizializzazione database firebase
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference();






    }
    //Salvataggio preferenze
    public void savePreferencesData(View view) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        EditText outputView = (EditText) findViewById(R.id.et_email);
        EditText outputView1 = (EditText) findViewById(R.id.et_password);
        CharSequence textData = outputView.getText();
        CharSequence textData1 = outputView1.getText();
        if (textData != null) {
            editor.putString(TEXT_DATA_KEY, textData.toString());
            editor.commit();
        }
        if (textData1 != null) {
            editor.putString(TEXT_DATA_KEY, textData1.toString());
            editor.commit();
        }
    }
    //Leggo i dati
    public void loadPreferences (View view) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String textData = prefs.getString(TEXT_DATA_KEY, "Prefs not found!");
        TextView outputView = (EditText) findViewById(R.id.et_email);
        outputView.setText(textData);

    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i("TAG", "firebaseAuthWithGoogle:" + account.getEmail() + account.getDisplayName());
                firebaseAuthWithGoogle(account.getIdToken());
                saveUser(account.getEmail(),account.getDisplayName());
                Log.d(TAG, "tokengoogle");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void saveUser(String emailUtente,String nome){
        Utente user = new Utente(emailUtente);
        myRef.child("Utenti").push().setValue(user);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }



    public void registrati(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

//loggandoci inviamo messaggio a DB
    public void login(View view) {





        final String emailUtente = username.getText().toString();
        String passwordUtente = password.getText().toString();
        //String nomeUtente = ;



                mAuth.signInWithEmailAndPassword(emailUtente, passwordUtente)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);


                                   //se tutto va bene si salva nel DB
                               /*     myRef = database.getReference("utente");
                                    myRef.setValue(emailUtente);  */

                                    //modo per salvare i dati nel DB



//modo per salvere i dati su DB 3
                                    //FirebaseDatabase database = FirebaseDatabase.getInstance();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                            }
                        });


            }

/*
    public void leggiMessaggio() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                tvMessaggio.setText(value);

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    } */


}




