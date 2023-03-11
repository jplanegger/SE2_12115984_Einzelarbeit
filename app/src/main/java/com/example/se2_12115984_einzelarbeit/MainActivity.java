package com.example.se2_12115984_einzelarbeit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button send;
    TextView matrikelnummer;
    TextView antwort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.button);
        matrikelnummer = findViewById(R.id.matrikelnummer);
        antwort = findViewById(R.id.serverReply);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nr = Integer.parseInt(matrikelnummer.getText().toString());

                antwort.setText(nr + "");
            }
        });
    }
}