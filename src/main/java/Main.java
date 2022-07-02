import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import classes.*;
public class Main {
  public static void main(String[] args){
    ServerSocket serverSocket;
    Socket clientSocket = null;
    int port = 6379;

    try {
      serverSocket = new ServerSocket(port);
      serverSocket.setReuseAddress(true);

      // Wait for connection from client.
      clientSocket = serverSocket.accept();
      int idCounter = 0;
      while(clientSocket != null) {
        new ClientThread(clientSocket, idCounter);
        idCounter++;
        clientSocket = serverSocket.accept();
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}
