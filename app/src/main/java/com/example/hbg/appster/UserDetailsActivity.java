package com.example.hbg.appster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsActivity extends AppCompatActivity{

    private EditText name, contact;
    private TextView admin, cust;
    private int selectedCat = -1;
    private Button next;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = findViewById(R.id.name_input);
        contact = findViewById(R.id.contact_input);
        admin = findViewById(R.id.admin_btn);
        cust = findViewById(R.id.cust_btn);
        next = findViewById(R.id.next_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String c = contact.getText().toString();
                if(selectedCat == -1) {
                    Toast.makeText(UserDetailsActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
                } else if(n.equals("") || c.equals("")) {
                    Toast.makeText(UserDetailsActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    UserDetail detail = PreferenceHelper.getInstance(UserDetailsActivity.this).getUserInfo();
                    databaseReference.child(detail.email.replaceAll(".", "")+"_user").child("cat").setValue(selectedCat);
                    databaseReference.child(detail.email.replaceAll(".", "")+"_user").child("name").setValue(n);
                    databaseReference.child(detail.email.replaceAll(".", "")+"_user").child("contact").setValue(c);
                    PreferenceHelper.getInstance(UserDetailsActivity.this).saveUserInfo(n, selectedCat, c);
                    if(selectedCat == 0) {
                        startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
                    } else if(selectedCat == 1) {
                        startActivity(new Intent(UserDetailsActivity.this, CustActivity.class));
                    }
                }
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setBackground(getDrawable(R.drawable.blue_shadow_bg));
                cust.setBackground(getDrawable(R.drawable.white_shadow_bg));
                selectedCat = 0;
            }
        });
        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust.setBackground(getDrawable(R.drawable.blue_shadow_bg));
                admin.setBackground(getDrawable(R.drawable.white_shadow_bg));
                selectedCat = 1;
            }
        });
    }
}
