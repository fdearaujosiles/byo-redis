package classes;

import enums.CommandEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static enums.CommandEnum.*;
import static enums.CommandEnum.PING;

public class ClientThread extends Thread {


    Socket _socket;
    HashMap<String, String> _listMap;
    int id;
    public ClientThread(Socket socket, int id) {

        this._socket = socket;
        this._listMap = new HashMap<String, String>();
        this.id = id;
    }
    @Override
    public void run() {
        try {
            MessageInterpreter mI = new MessageInterpreter(_socket, _listMap, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logger(String log) {
        if(Config.LOGS) System.out.println("Thread " + id + ": " + log);
    }


}
