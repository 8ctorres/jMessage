package es.udc.jmessage.threads;

import java.net.*;
import java.io.*;

/**
 * Multithread TCP jmessage server.
 * @author Carlos Torres
 */

public class Server {
    private static ReadingThread recibir;
    private static WritingThread escribir;

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: TcpServer <port>");
            System.exit(-1);
        }
        try{
            ServerSocket servidor = new ServerSocket(Integer.parseInt(argv[0]));
            //Establish TCP connection with the other end
            Socket chat = servidor.accept();
            System.out.println("Connected!");
            System.out.println("Client IP Address: " + chat.getInetAddress().toString());
            //Creates two threads, one that receives and one that sends the messages
            recibir = new ReadingThread(chat);
            escribir = new WritingThread(chat);
            recibir.start();
            escribir.start();
        }
        catch (IOException e){
            e.printStackTrace();
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