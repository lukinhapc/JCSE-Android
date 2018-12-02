package com.example.appteste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;

    private ImageView mPostImage;
    private TextView mPostNome, mPostEmail, mPostTelefone, mPostCnpj,
    mPostEndereco, mPostCidade, mPostUF, mPostCep, mPostMes,
    mPostGasto, mPostoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mPostNome = findViewById(R.id.txtNome);
        mPostEmail = findViewById(R.id.txtEmail);
        mPostImage = findViewById(R.id.imageView2);

        mPostTelefone = findViewById(R.id.txtTelefone);
        mPostCnpj = findViewById(R.id.txtCnpj);
        mPostEndereco = findViewById(R.id.txtEndereco);
        mPostCidade = findViewById(R.id.txtCidade);
        mPostUF = findViewById(R.id.txtUF);
        mPostCep = findViewById(R.id.txtCep);
        mPostMes = findViewById(R.id.txtMes);
        mPostGasto = findViewById(R.id.txtGasto);
        mPostoValor = findViewById(R.id.txtValor);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        String mPost_key = getIntent().getExtras().getString("post_id");
        //Toast.makeText(this, post_key, Toast.LENGTH_SHORT).show();

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_name = (String) dataSnapshot.child("nome").getValue();
                String post_email = (String) dataSnapshot.child("email").getValue();
                String post_telefone = (String) dataSnapshot.child("telefone").getValue();
                String post_cnpj = (String) dataSnapshot.child("cnpj").getValue();
                String post_cep = (String) dataSnapshot.child("cep").getValue();
                String post_endereco = (String) dataSnapshot.child("endereco").getValue();
                String post_cidade = (String) dataSnapshot.child("cidade").getValue();
                String post_uf = (String) dataSnapshot.child("uf").getValue();

                //Gasto
                String post_mes = (String) dataSnapshot.child("mes").getValue();
                String post_gasto = (String) dataSnapshot.child("gasto").getValue();
                String post_valor = (String) dataSnapshot.child("valor").getValue();

                String post_image = (String) dataSnapshot.child("image").getValue();

                mPostNome.setText(post_name);
                mPostEmail.setText(post_email);
                mPostTelefone.setText(post_telefone);
                mPostCnpj.setText(post_cnpj);
                mPostCep.setText(post_cep);
                mPostEndereco.setText(post_endereco);
                mPostCidade.setText(post_cidade);
                mPostUF.setText(post_uf);
                mPostMes.setText(post_mes);
                mPostGasto.setText(post_gasto);
                mPostoValor.setText(post_valor);
                Picasso.get()
                        .load(post_image)
                        .into(mPostImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
