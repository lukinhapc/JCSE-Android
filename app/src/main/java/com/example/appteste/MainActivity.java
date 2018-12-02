package com.example.appteste;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.keepSynced(true);

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseUsers.keepSynced(true);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent login = new Intent(MainActivity.this, Login.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                }
            }
        };

        listView = findViewById(R.id.userList);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.user_row,
                UserViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setNome(model.getNome());
                viewHolder.setEmail(model.getEmail());
              //  viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, post_key, Toast.LENGTH_SHORT).show();
                        Intent profile = new Intent(MainActivity.this, Profile.class);
                        profile.putExtra("post_id", post_key);
                        startActivity(profile);
                    }
                });
            }
        };

        listView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setNome(String nome) {
            TextView post_nome = mView.findViewById(R.id.post_nome);
            post_nome.setText(nome);
        }

        public void setEmail(String email) {
            TextView post_email = mView.findViewById(R.id.post_email);
            post_email.setText(email);
        }

      /* public void setImage(Context ctx, String image) {
            ImageView post_image = mView.findViewById(R.id.post_image);
            Picasso.get()
                    .load(image)
                    .into(post_image);
        } */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        }
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }
}
