package com.secure.prosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private TinyDB db;
    private Button move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new TinyDB(getApplicationContext());

        if (db.getBoolean("login")) {
            Intent go = new Intent(MainActivity.this, Dashboard.class);
            startActivity(go);
            finish();
        }

        move = findViewById(R.id.move);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this, TrustedContact.class);
                startActivity(go);
            }
        });
    }
}
