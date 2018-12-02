package com.example.appteste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText emailLogin, senhaLogin;
    private TextView txtCadastrar;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseUsers.keepSynced(true);

        emailLogin = findViewById(R.id.emailLogin);
        senhaLogin = findViewById(R.id.senhaLogin);
        txtCadastrar = findViewById(R.id.txtCadastrar);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(Login.this, Signup.class);
                startActivity(signup);
            }
        });
    }

    private void checkLogin() {
        String email = emailLogin.getText().toString().trim();
        String senha = senhaLogin.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)){
            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent login = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(login);
                    }else{
                        Toast.makeText(Login.this, "Error Login", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

   /* private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent login = new Intent(Login.this, MainActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);

                }else{

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    } */
}
