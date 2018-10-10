package com.secure.prosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TrustedContact extends AppCompatActivity {
    private EditText number;
    private LinearLayout submit;
    private TinyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trusted_contact);

        db = new TinyDB(getApplicationContext());
        number = findViewById(R.id.number);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().length() < 10) {
                    print("Enter valid number");
                }
                else {
                    db.putString("number", number.getText().toString().trim());
                    Intent go = new Intent(TrustedContact.this, TrustedCode.class);
                    startActivity(go);
                }
            }
        });
    }

    public void print(String message) {
        Toast.makeText(TrustedContact.this, message, Toast.LENGTH_SHORT).show();
    }

}
