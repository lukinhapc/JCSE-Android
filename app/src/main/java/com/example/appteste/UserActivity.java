package com.example.appteste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserActivity extends AppCompatActivity {

    private ImageView imgView;
    private EditText inputNome, inputEmail,
            inputTelefone, inputCnpj, inputEndereco,
            inputCidade, inputUF, inputCep, inputMes,
            inputGasto, inputValor;

    private Button btnSave;
    private ProgressDialog progressDialog;

    private Uri imageUri = null;
    private static final int GALLERY_REQUEST = 1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        inputNome = findViewById(R.id.inputNome);
        inputEmail = findViewById(R.id.inputEmail);
        inputTelefone = findViewById(R.id.inputTelefone);
        inputCnpj = findViewById(R.id.inputCnpj);
        inputCep = findViewById(R.id.inputCep);
        inputEndereco = findViewById(R.id.inputEndereco);
        inputCidade = findViewById(R.id.inputCidade);
        inputUF = findViewById(R.id.inputUF);
        inputMes = findViewById(R.id.inputMes);
        inputGasto = findViewById(R.id.inputGasto);
        inputValor = findViewById(R.id.inputValor);


        btnSave = findViewById(R.id.btnSave);

        progressDialog = new ProgressDialog(this);

        imgView = findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });
    }

    private void startAdding() {
        progressDialog.setMessage("Enviando...");
        progressDialog.show();
        final String nome = inputNome.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        final String telefone = inputTelefone.getText().toString().trim();
        final String cnpj = inputCnpj.getText().toString().trim();
        final String cep = inputCep.getText().toString().trim();
        final String endereco = inputEndereco.getText().toString().trim();
        final String cidade = inputCidade.getText().toString().trim();
        final String uf = inputUF.getText().toString().trim();

        final String mes = inputMes.getText().toString().trim();
        final String gasto = inputGasto.getText().toString().trim();
        final String valor = inputValor.getText().toString().trim();


        if(!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(email) && imageUri != null){
            StorageReference filepath =mStorage.child("Conta_Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newUser = mDatabase.push();
                    newUser.child("nome").setValue(nome);
                    newUser.child("email").setValue(email);
                    newUser.child("telefone").setValue(telefone);
                    newUser.child("cnpj").setValue(cnpj);
                    newUser.child("cep").setValue(cep);
                    newUser.child("endereco").setValue(endereco);
                    newUser.child("cidade").setValue(cidade);
                    newUser.child("uf").setValue(uf);
                    //Gastos
                    newUser.child("mes").setValue(mes);
                    newUser.child("gasto").setValue(gasto);
                    newUser.child("valor").setValue(valor);
                    newUser.child("image").setValue(downloadUrl.toString());
                    progressDialog.dismiss();

                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imgView.setImageURI(imageUri);
        }
    }
}

