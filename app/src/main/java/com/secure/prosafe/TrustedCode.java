package com.secure.prosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TrustedCode extends AppCompatActivity {
    private EditText code;
    private LinearLayout submit;
    private TinyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trusted_code);

        db = new TinyDB(getApplicationContext());
        code = findViewById(R.id.code);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code.getText().toString().length() < 3) {
                    print("Enter valid number");
                }
                else {
                    db.putString("code", code.getText().toString().trim().toUpperCase().replace(" ",""));
                    db.putBoolean("login", true);
                    Intent go = new Intent(TrustedCode.this, Dashboard.class);
                    startActivity(go);
                    finish();
                }
            }
        });
    }

    public void print(String message) {
        Toast.makeText(TrustedCode.this, message, Toast.LENGTH_SHORT).show();
    }

}
