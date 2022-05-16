import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import classes.*;
public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 6379;

    try {
      serverSocket = new ServerSocket(port);
      serverSocket.setReuseAddress(true);

      // Wait for connection from client.
      clientSocket = serverSocket.accept();
      int idCounter = 0;
      while(clientSocket != null) {
        ClientThread thread = new ClientThread(clientSocket, idCounter);
        thread.start();
        idCounter++;
        clientSocket = serverSocket.accept();
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
          System.out.println("Final");
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}
