/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.exceptions.JCLException;
import jlc.view.TextStyle;
import org.apache.commons.io.output.CloseShieldOutputStream;

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
                        RemoteMode.remoteExecute(text,client.getOutputStream(),this);
                    }
                    else break;
                }
        }catch (SocketException e){
        } //When client tries to kill himself remotely (use"slay remote mode")
            catch (IOException ex) {
        } catch (JCLException ex) {
            try {
                this.write(ex.getMessage());
            } catch (IOException ex1) {
                ex1.printStackTrace();
        }
        }finally{
            System.out.println(TextStyle.colorText(client.getInetAddress()+ " User has been disconnected.", TextStyle.Color.CYAN));
            try {
                RemoteMode.CLIENT_LIST.remove(this);
                if(!client.isClosed())
                client.close();
            } catch (IOException ex3) {
                throw new RuntimeException(ex3);
            }
            client = null;
        }
    }
    @Override
    public String toString() {
        return client.getInetAddress().toString();
    }
    public void close() throws IOException{
        this.client.close();
    }
    public boolean isClosed(){
        return this.client.isClosed();
    }
    public final void write(final String message) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new CloseShieldOutputStream(client.getOutputStream())))){
            bw.write("\n" + message + "\n");
            bw.write(System.getProperty("user.dir")+":");
            bw.flush();
        }
    }
    public Socket getSocket(){
        return this.client;
    }
    }
