package classes.commands;

import classes.Command;
import classes.MessageInterpreter;

import java.io.IOException;
import java.io.PrintWriter;

public class PingCommand extends Command {

    public PingCommand(MessageInterpreter _messageInterpreter) {
        super(_messageInterpreter);
    }

    @Override
    public String run() throws IOException {
        new PrintWriter(mI.socket.getOutputStream(), true).println("+PONG");
        return readLine();
    }

}
