package es.udc.jmessage.threads;

import java.net.*;
import java.io.*;

/**
 * Multithreaded TCP jMessage client
 * @author Carlos Torres
 */
public class Client {
    private static ReadingThread recibir;
    private static WritingThread escribir;

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.err.println("Format: TcpClient <server_address> <port_number>");
            System.exit(-1);
        }
        Socket socket = null;
        try {
            // Obtains the server IP address
            InetAddress serverAddress = InetAddress.getByName(argv[0]);
            // Obtains the server port
            int serverPort = Integer.parseInt(argv[1]);
            // Creates the socket and establishes connection with the server
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connection established with "
                    + serverAddress.toString() + " port " + serverPort);
            escribir = new WritingThread(socket);
            recibir = new ReadingThread(socket);
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        public static void endChat() throws InterruptedException{
        recibir.endChat();
        recibir.join();
        escribir.endChat();
        escribir.join();
        System.out.println("Chat has ended");
        System.exit(0);
    }
}