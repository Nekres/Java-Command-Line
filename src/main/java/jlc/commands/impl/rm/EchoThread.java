/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import jlc.view.TextStyle;

/**
 *
 * @author desolation
 */
public class EchoThread implements Runnable{
    private static final String CLOSE = "close";
    private Socket client;
    
    
    public EchoThread(final Socket clientSocket){
        this.client = clientSocket;
    }

    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))){
                String text = null;
                while((text = br.readLine()) != null && !text.equals(CLOSE)){
                    if(client.isConnected()){
                        RemoteMode.remoteExecute(text,client.getOutputStream());
                    }
                    else break;
                }
        }catch (SocketException e){} //When client tries to kill himself remotely use "slay remote mode"
        catch (IOException ex) {
        }finally{
            System.out.println(TextStyle.colorText(client.getInetAddress()+ " User has been disconnected.", TextStyle.Color.CYAN));
            try {
                RemoteMode.CLIENT_LIST.remove(this);
                if(!client.isClosed())
                client.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            client = null;
        }
        }

    @Override
    public String toString() {
        return client.getInetAddress().toString();
    }
    
    }
