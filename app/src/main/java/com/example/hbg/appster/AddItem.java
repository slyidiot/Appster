package com.example.hbg.appster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItem extends AppCompatActivity {

    private TextView back, add;
    private EditText name, qty, dis;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        back = findViewById(R.id.back_btn);
        add = findViewById(R.id.addit_btn);
        name = findViewById(R.id.item_name);
        qty = findViewById(R.id.item_quantity);
        dis = findViewById(R.id.item_discount);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String q = qty.getText().toString();
                String d = dis.getText().toString();
                if(!n.equals("") && !q.equals("") && !d.equals("")) {
                    databaseReference.child(n+"_xyz").child("name").setValue(n);
                    databaseReference.child(n+"_xyz").child("qty").setValue(q);
                    databaseReference.child(n+"_xyz").child("dis").setValue(d);
                    Toast.makeText(AddItem.this, "data added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddItem.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
