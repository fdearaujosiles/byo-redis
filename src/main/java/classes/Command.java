package classes;

import java.io.IOException;

public abstract class Command {

    public String Name;
    protected MessageInterpreter mI;
    protected String readLine() { return mI.readLine(); }
    public abstract String run() throws IOException;

    public Command(MessageInterpreter _messageInterpreter, String _Name) {
        mI = _messageInterpreter;
        Name = _Name;
    }

}
