/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.jmessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * TCP Client for jMessage
 * @author Carlos Torres
 */
public class Client {
    public static void main(String argv[]) throws IOException {
        if (argv.length != 2) {
            System.err.println("Format: TcpClient <server_address> <port_number>");
            System.exit(-1);
        }
        Socket socket = null;
        BufferedReader sInput = null;
        PrintWriter sOutput = null;
        boolean keep_open = true;
        try {
            // Obtains the server IP address
            InetAddress serverAddress = InetAddress.getByName(argv[0]);
            // Obtains the server port
            int serverPort = Integer.parseInt(argv[1]);
            // Creates the socket and establishes connection with the server
            socket = new Socket(serverAddress, serverPort);
            // Set a max. Timeout of 300 secs
            socket.setSoTimeout(300000);
            System.out.println("Connection established with "
                    + serverAddress.toString() + " port " + serverPort);

            // Set the input channel
            sInput = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
            // Set the output channel
            sOutput = new PrintWriter(socket.getOutputStream(), true);
            // Set the keyboard scanner
            Scanner scanner = new Scanner(System.in);
            // Main loop
            while (keep_open){
                System.out.print("Send message: ");
                String message = scanner.nextLine();
                // Send message to the server
                sOutput.println(message);
                if (message.equals("exit")){
                    keep_open = false;
                }else{
                // Wait to receive server response
                Thread.sleep(1);
                String recibido = sInput.readLine();
                System.out.println("Server said: " + recibido);
                if(recibido.equals("exit")){
                    keep_open = false;
                }
                }
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 5 minutes");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                sInput.close();
                sOutput.close();
                socket.close();
                System.out.println("Connection closed. Bye!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
