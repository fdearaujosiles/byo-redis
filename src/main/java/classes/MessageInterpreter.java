package classes;

import enums.CommandEnum;
import jdk.nashorn.internal.objects.annotations.Function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;


public class MessageInterpreter {

    private Socket socket;
    private BufferedReader in;
    private HashMap<String, String> listMap;
    private CommandEnum command;
    private ClientThread thread;
    public String message = "";
    public String content = "";

    public MessageInterpreter(Socket socket, HashMap<String, String> listMap, ClientThread thread) throws IOException {
        this.socket = socket;
        this.listMap = listMap;
        this.thread = thread;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = readLine();
        while(line != null) {
            this.message = "";
            this.content = "";
            readLine();
            setCommand(readLine());
            thread.logger("NEW ACTION: " + this.message);

//            thread.logger("messageLength: " + ((this.messageLength * 2) - 2) );
            switch (this.command) {
                case PING:
                    line = handlePing();
                    break;
                case SET:
                    line = handleSet();
                    break;
                case ECHO:
                    line = handleEcho();
                    break;
                case GET:
                    line = handleGet();
                    break;
            }
        }
    }

    private CommandEnum setCommand(String message) {
        if ("ping".equals(message)) {
            this.command = CommandEnum.PING;
        } else if ("echo".equals(message)) {
            this.command = CommandEnum.ECHO;
        } else if ("set".equals(message)) {
            this.command = CommandEnum.SET;
        } else if ("get".equals(message)) {
            this.command = CommandEnum.GET;
        }
        return null;
    }

    private String handlePing() throws IOException {
        new PrintWriter(this.socket.getOutputStream(), true).println("+PONG");
        return readLine();
    }
    private String handleSet() throws IOException {
        String key = setNewPair();
        thread.logger("key set: " + key);
        new PrintWriter(this.socket.getOutputStream(), true).println("+OK");

        String line = readLine();
        thread.logger("81 line: " + line);

        if(line.charAt(0) != '*' && line != null) {
            readLine();
            readLine();
            line = readLine();

            Integer timeout = Integer.parseInt(line);
            thread.logger("timeout: " + timeout);
            timeout = timeout < 0 ? timeout * -1 : timeout;
            new TimeBomb(key, timeout, listMap).start();

            line = readLine();
        }

        return line;
    }
    private String handleGet() throws IOException {
        readLine();
        this.content = readLine();
        String response = this.listMap.get(this.content);
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

        if(response == null) {
            out.println("$-1");
        } else {
            out.println("+" + response);
        }

        return readLine();
    }
    private String handleEcho() throws IOException {
        readLine();
        this.content = readLine();

        new PrintWriter(this.socket.getOutputStream(), true).println("+" + this.content);
        return readLine();
    }
    private String setNewPair() {
        readLine();
        String key = readLine();
        readLine();
        String value = readLine();
        this.listMap.put(key, value);
        thread.logger("setNewPair: " + key + " -> " + value);

        return key;
    }

    private String readLine() {
        try {
            String line = this.in.readLine();
            if (line == null) return null;
            this.message += line;
//            if (Config.LOGS) thread.logger("line: " + line);
            return line;
        } catch (Exception e) {return null;}
    }
}
