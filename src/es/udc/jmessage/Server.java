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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * TCP Server for jMessage
 * @author Carlos Torres
 */
public class Server {
    public static void main(String argv[]){
        ServerSocket servidor = null;
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        boolean keep_open = true;
        if (argv.length != 1) {
            System.err.println("Format: TcpServer <port>");
            System.exit(-1);
        }
        try{
            //Listen for connection
            servidor = new ServerSocket(Integer.parseInt(argv[0]));
            servidor.setSoTimeout(300000);
            //Create TCP socket
            socket = servidor.accept();
            System.out.println("Client connected");
            System.out.println("Client IP Address: " + socket.getInetAddress().toString());
            //Set input channel
            input = new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream()));
            //Set output channel
            output = new PrintWriter(socket.getOutputStream(),true);
            //Set keyboard scanner
            Scanner scanner = new Scanner(System.in);
            //Main loop
            while (keep_open){
                String entrante = input.readLine();
                System.out.println("Client said: " + entrante);
                if(entrante.equals("exit")){
                    keep_open = false;
                }else{
                //Send response
                System.out.print("Send message: ");
                String saliente = scanner.nextLine();
                output.println(saliente);
                output.flush();
                if(saliente.equals("exit")){
                    keep_open = false;
                }
                }
            }
        }
        catch(SocketTimeoutException e){
            System.out.println("Nothing received in 5 minutes");
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        finally{
            try{
                input.close();
                output.close();
                socket.close();
                System.out.println("Connection closed. Bye!");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
