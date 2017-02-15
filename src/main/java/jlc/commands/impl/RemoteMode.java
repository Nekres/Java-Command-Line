/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.JLC;
import jlc.commands.Command;
import jlc.exceptions.JCLException;
import jlc.parse.CLParser;
import jlc.view.TextStyle;
import org.apache.commons.io.output.CloseShieldOutputStream;

/**
 *
 * @author desolation
 */
public class RemoteMode extends AbstractCommand implements Command{
    public static final String NAME = "remote".intern();
    public static final int DEFAULT_PORT = 5000;
    public static boolean ENABLED = true;
    private ServerSocket server;
    private final int port;
    private boolean successFinished = true;

    public RemoteMode(int port){
        this.port = port;
    }
    

    @Override
    public void invoke() {
        this.run();
    }

    @Override
    public int getID() {
        return ++INSTANCE_ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.println("\nRemote mode is listening for incoming connections on "+ port +" port...");
        } catch (IOException ex) {
            System.out.println(TextStyle.colorText("could not listen on port: "+ port, TextStyle.Color.RED));
            System.out.println(TextStyle.colorText("Try to use another port.", TextStyle.Color.RED));
            successFinished = false;
            return;
        }
        Socket s = null;
            try{
                s = server.accept();
                System.out.println(TextStyle.colorText("Connection established.", TextStyle.Color.BLUE));
                EchoThread et = new EchoThread(s);
                Thread t = new Thread(et);
                t.start();
                t.join();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
        }finally{
            try {
                server.close();
                if(s != null)
                s.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            }
    }
    @Override
    public Boolean call() throws Exception {
        this.invoke();
        return successFinished;
    }
    public static final void echo(final String command, final OutputStream os){
        System.out.println(command);
        String arr[] = command.intern().split(" ");
            if(command.equals("quit")){
                System.out.println(TextStyle.colorText("Bye.\nWe'll miss you.",TextStyle.Color.BRIGHT));
                System.exit(0);
            }
            try {
                List<Command> commandList = CLParser.analyze(jlc.JLC.settings, arr);
                Command.executeToStream(commandList,os, true);
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new CloseShieldOutputStream(os)))){
                   bw.write(System.getProperty("user.dir").intern());
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            } catch (JCLException e) {
                System.out.println(e.getMessage());
            }
            catch(InterruptedException ie){
                throw new RuntimeException();
            }
            catch(ExecutionException ee){
                throw new RuntimeException();
            }
    }
    
    
}
