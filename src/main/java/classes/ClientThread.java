package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {

    Socket _socket;


    public ClientThread(Socket socket) {
        this._socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        String msg = null;

        try {
            in = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
            out = new PrintWriter(this._socket.getOutputStream(), true);
            msg = in.readLine();
            while(msg != null) {
                System.out.println(msg);
                msg += in.readLine();
            }
            out.println(createResponse(msg));
        } catch (IOException e) { }


    }


    private static String createResponse(String msg) {
        if(msg.compareTo("ping") == 0) return "+PONG";

        return "+PONG";
    }
}
