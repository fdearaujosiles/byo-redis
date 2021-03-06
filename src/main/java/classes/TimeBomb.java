package classes;

import java.util.HashMap;

public class TimeBomb extends Thread {
    String key;
    Integer timeout;
    HashMap<String, String> listMap;
    public TimeBomb(String key, Integer timeout, HashMap<String, String> listMap) {
        this.key = key;
        this.timeout = timeout;
        this.listMap = listMap;
        start();
    }
    @Override
    public void run() {
        try {
            Thread.sleep(timeout < 0 ? timeout * (-1L) : timeout);
            listMap.remove(key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
