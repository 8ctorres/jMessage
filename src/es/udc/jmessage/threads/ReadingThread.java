package es.udc.jmessage.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class ReadingThread extends Thread{
    private final Socket socket;
    private BufferedReader input;
    boolean is_open = true;
    public ReadingThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try{
            //Set input channel
            input = new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream()));
            while(is_open){
                //Read incoming message
                String incoming = input.readLine();
                //Print incoming message
                System.out.print("Partner says: ");
                System.out.println(incoming);
            }
            //After communication ends, close input stream
            input.close();            
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