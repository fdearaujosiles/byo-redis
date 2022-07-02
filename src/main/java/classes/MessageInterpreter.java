package classes;

import enums.CommandEnum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;


public class MessageInterpreter {
    private final Socket socket;
    private final BufferedReader in;
    private final HashMap<String, String> listMap;
    private CommandEnum command;
    public String message = "";
    public String content = "";

    public MessageInterpreter(Socket socket, HashMap<String, String> listMap) throws IOException {
        this.socket = socket;
        this.listMap = listMap;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = readLine();
        while(line != null) {
            this.message = "";
            this.content = "";
            readLine();
            setCommand(readLine());
            line = findCommandHandler(this.command);
        }
    }

    private String findCommandHandler(CommandEnum command) throws IOException  {
        switch (command) {
            case PING:  return handlePing();
            case ECHO:  return handleEcho();
            case SET:   return handleSet();
            case GET:   return handleGet();
            default: throw new IllegalArgumentException("Command not implemented");
        }
    }

    private void setCommand(String message) {
        if ("ping".equals(message)) {
            this.command = CommandEnum.PING;
        } else if ("echo".equals(message)) {
            this.command = CommandEnum.ECHO;
        } else if ("set".equals(message)) {
            this.command = CommandEnum.SET;
        } else if ("get".equals(message)) {
            this.command = CommandEnum.GET;
        }
    }

    private String handlePing() throws IOException {
        new PrintWriter(this.socket.getOutputStream(), true).println("+PONG");
        return readLine();
    }
    private String handleSet() throws IOException {
        String key = setNewPair();
        new PrintWriter(this.socket.getOutputStream(), true).println("+OK");

        String line = readLine();
        if(line != null && line.charAt(0) != '*') {
            readLine();
            readLine();
            line = readLine();

            assert line != null;
            int timeout = Integer.parseInt(line);
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

        return key;
    }

    private String readLine() {
        try {
            String line = this.in.readLine();
            if (line == null) return null;
            this.message += line;
            return line;
        } catch (Exception e) {return null;}
    }
}