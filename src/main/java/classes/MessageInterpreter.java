package classes;

import classes.commands.EchoCommand;
import classes.commands.GetCommand;
import classes.commands.PingCommand;
import classes.commands.SetCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;


public class MessageInterpreter {
    private final BufferedReader in;
    private Command findCommandHandler(String command)  {
        switch (command) {
            case "ping":  return new PingCommand(this);
            case "echo":  return new EchoCommand(this);
            case "set":   return new SetCommand(this);
            case "get":   return new GetCommand(this);
            default: throw new IllegalArgumentException("Command not implemented");
        }
    }
    public final Socket socket;
    public final HashMap<String, String> listMap;
    public String readLine() {
        try {
            return in.readLine();
        } catch (Exception e) {return null;}
    }
    public MessageInterpreter(Socket _socket, HashMap<String, String> _listMap) throws IOException {
        socket = _socket;
        listMap = _listMap;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = readLine();
        while(line != null) {
            readLine();
            Command command = findCommandHandler(readLine());
            line = command.run();
        }
    }
}