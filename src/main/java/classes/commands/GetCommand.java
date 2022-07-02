package classes.commands;

import classes.Command;
import classes.MessageInterpreter;

import java.io.IOException;
import java.io.PrintWriter;

public class GetCommand extends Command {

    public GetCommand(MessageInterpreter _messageInterpreter) {
        super(_messageInterpreter);
    }

    @Override
    public String run() throws IOException {
        readLine();
        String response = mI.listMap.get(readLine());
        PrintWriter out = new PrintWriter(mI.socket.getOutputStream(), true);

        if(response == null) {
            out.println("$-1");
        } else {
            out.println("+" + response);
        }

        return readLine();
    }
}
