package com.example.se2_12115984_einzelarbeit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button send;
    TextView matrikelnummer;
    public TextView antwort;
    private ServerConnection connection;

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
                String nr = matrikelnummer.getText().toString();
                Log.i("A_TAG", nr);
                (new Connector()).execute(nr);
            }
        });
    }

    class Connector extends AsyncTask<String, String, ServerConnection> {
        @Override
        protected ServerConnection doInBackground(String... strings) {
            Log.i("A_TAG", "Process started");
            connection = new ServerConnection(new ServerConnection.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    Log.i("A_TAG", message);
                    runOnUiThread(() ->{
                        antwort.setText(message);
                    });
                    connection.stopListening();
                }
            });
            connection.startListening();
            connection.sendMessage(strings[0]);
            return null;

        }
    }
}