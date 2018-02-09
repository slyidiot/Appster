package com.example.hbg.appster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

public class LoginActivity extends AppCompatActivity {

    private MaterialLoginView login;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            //TODO: handle this
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = (MaterialLoginView) findViewById(R.id.login);

        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                final String email = loginUser.getEditText().getText().toString();
                final String pass = loginPass.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            PreferenceHelper.getInstance(LoginActivity.this).saveUserDetails(email, pass);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error logging in...please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                final String email = registerUser.getEditText().getText().toString();
                final String pass = registerPass.getEditText().getText().toString();
                String passrep = registerPass.getEditText().getText().toString();
                if(pass.equals(passrep)) {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                PreferenceHelper.getInstance(LoginActivity.this).saveUserDetails(email, pass);
                                try {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(email.replaceAll(".", "") + "_user");
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int cat = 0;
                                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                                if(snapshot.getKey().equalsIgnoreCase("cat")) {
                                                    cat = snapshot.getValue(Integer.class);
                                                }
                                            }
                                            PreferenceHelper.getInstance(LoginActivity.this).saveUserInfo("", cat, "");
                                            if(cat == 0) {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            } else if(cat == 1) {
                                                startActivity(new Intent(LoginActivity.this, CustActivity.class));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                } catch (Exception e) {
                                    //DO NOTHING
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Error registering...please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
