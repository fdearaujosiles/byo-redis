package classes.commands;

import classes.Command;
import classes.MessageInterpreter;
import classes.TimeBomb;

import java.io.IOException;
import java.io.PrintWriter;

public class SetCommand extends Command {

    public SetCommand(MessageInterpreter _messageInterpreter) {
        super(_messageInterpreter, "set");
    }

    @Override
    public String run() throws IOException {
        String key = setNewPair();
        new PrintWriter(mI.socket.getOutputStream(), true).println("+OK");

        String line = readLine();
        if(line != null && line.charAt(0) != '*') {
            readLine();
            readLine();
            line = readLine();

            assert line != null;
            int timeout = Integer.parseInt(line);
            timeout = timeout < 0 ? timeout * -1 : timeout;
            new TimeBomb(key, timeout, mI.listMap).start();

            line = readLine();
        }

        return line;
    }

    private String setNewPair() {
        readLine();
        String key = readLine();
        readLine();
        String value = readLine();
        mI.listMap.put(key, value);

        return key;
    }
}
