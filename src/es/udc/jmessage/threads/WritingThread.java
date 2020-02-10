package es.udc.jmessage.threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class WritingThread extends Thread{
    private final Socket socket;
    private PrintWriter output;
    private final Scanner scanner;
    boolean is_open = true;
    public WritingThread(Socket socket){
        this.socket = socket;
        scanner = new Scanner(System.in);        
    }
    @Override
    public void run(){
        try{
            //Set input channel
            output = new PrintWriter(socket.getOutputStream(), true);
            while(is_open){
                //Read the message to send
                String message = scanner.nextLine();
                //Send message to the other end
                output.println(message);
                output.flush();
            }
            //After communication ends, close output stream
            output.close();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
        public void endChat(){
        is_open = false;
        this.interrupt();
    }
}