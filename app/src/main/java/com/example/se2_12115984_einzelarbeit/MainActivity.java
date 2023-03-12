package com.example.se2_12115984_einzelarbeit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button send;
    private Button modifyMatNr;
    private TextView matrikelnummer;
    private TextView antwort;
    private ServerConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.button);
        modifyMatNr = findViewById(R.id.modifyMatrikelnummer);
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

        modifyMatNr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nr = matrikelnummer.getText().toString();
                antwort.setText(cutPrimes(nr));
            }
        });
    }

    private String cutPrimes(String matrikelnummer){
        char[] chars = matrikelnummer.toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = chars[i] - '0';
        }
        Arrays.sort(ints);

        String out = "";
        for (int i : ints) {
            if (!isPrime(i))
                out+=i;
        }

        return out;
    }

    private boolean isPrime(int number){
        if(number <= 1)
            return false;
        if(number %2 == 0 && number > 2)
            return false;

        for(int i = 3; i < (int)(Math.sqrt(number)); i += 2){
            if(number%i == 0)
                return false;
        }
        return true;
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