/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import jlc.commands.impl.RemoteMode;

/**
 *
 * @author desolation
 */
public class EchoThread implements Runnable{
    private static final String CLOSE = "close".intern();
    private Socket client;
    private PrintWriter out;
    
    
    public EchoThread(final Socket clientSocket){
        this.client = clientSocket;
    }

    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));PrintWriter out = new PrintWriter(client.getOutputStream(),true)){
                String text = null;
                while((text = br.readLine()) != null && text != CLOSE){
                    if(client.isConnected()){
                        RemoteMode.echo(text,client.getOutputStream());
                    }
                    else break;
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            client = null;
        }
        }
    public final void echo(String message){
        if(client != null)
        out.println(message);
    }
    }
