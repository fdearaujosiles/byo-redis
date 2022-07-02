package classes;

import java.io.IOException;

public abstract class Command {

    protected MessageInterpreter mI;
    protected String readLine() { return mI.readLine(); }
    public abstract String run() throws IOException;

    public Command(MessageInterpreter _messageInterpreter) {
        mI = _messageInterpreter;
    }

}
