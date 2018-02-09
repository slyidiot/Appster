package com.example.hbg.appster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!PreferenceHelper.getInstance(MainActivity.this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if(!PreferenceHelper.getInstance(MainActivity.this).isDataSaved()) {
            startActivity(new Intent(this, UserDetailsActivity.class));
        }

        add = findViewById(R.id.add_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItem.class));
            }
        });
    }
}
