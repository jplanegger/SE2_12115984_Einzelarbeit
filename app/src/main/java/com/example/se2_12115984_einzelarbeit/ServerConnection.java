package com.example.se2_12115984_einzelarbeit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerConnection {
    private OnMessageReceived messageListener;
    private static final String DOMAIN = "se2-isys.aau.at";
    private static final int PORT = 53212;
    private BufferedWriter bWriter;
    private BufferedReader bReader;
    private Socket socket;

    public ServerConnection(OnMessageReceived listener){
        this.messageListener = listener;
    }

    public void sendMessage(String message){
        if(bWriter != null){
            try {
                bWriter.write(message);
                bWriter.newLine();
                bWriter.flush();

                this.messageListener.messageReceived(bReader.readLine());
            } catch (IOException e) {
                Log.e("A_TAG", "There was an IO Exception when sending the message " + message);
                throw new RuntimeException(e);
            }
        }
    }

    public void stopListening(){

        try {
            bWriter.flush();
            bWriter.close();
            bReader.close();
            socket.close();
            Log.i("A_TAG", "Stopped Listening");
        } catch (IOException e) {
            Log.e("A_TAG", "Encountered an IO Exception when closing the Connection");
            throw new RuntimeException(e);
        }



        bReader = null;
        bWriter = null;
        messageListener = null;
    }
    public void startListening(){
        try {
            socket = new Socket(DOMAIN, PORT);

            bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        } catch (IOException e) {
            Log.e("A_TAG", "Encountered an IO Exception");
            throw new RuntimeException(e);
        }
    }

    public interface OnMessageReceived {
        void messageReceived(String message);
    }

}
