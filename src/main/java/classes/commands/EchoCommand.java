package classes.commands;

import classes.Command;
import classes.MessageInterpreter;

import java.io.IOException;
import java.io.PrintWriter;

public class EchoCommand extends Command {

    public EchoCommand(MessageInterpreter _messageInterpreter) {
        super(_messageInterpreter, "echo");
    }

    @Override
    public String run() throws IOException {
        readLine();

        new PrintWriter(mI.socket.getOutputStream(), true).println("+" + readLine());
        return readLine();
    }

}
