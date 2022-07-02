package classes;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread {

    int id;
    Socket _socket;
    HashMap<String, String> _listMap;
    public ClientThread(Socket socket, int id) {
        this._socket = socket;
        this._listMap = new HashMap<String, String>();
        this.id = id;
    }
    @Override
    public void run() {
        try {
            MessageInterpreter mI = new MessageInterpreter(_socket, _listMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
