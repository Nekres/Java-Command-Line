/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class RemoteMode extends AbstractCommand implements Command{
    public static final List<EchoThread> CLIENT_LIST = new ArrayList<>();
    public static boolean ENABLED = true;
    private static final PipedOutputStream pos = new PipedOutputStream();
    private final String name = "Remote mode";
    private ServerSocket server;
    private final int port;
    private static PrintWriter pw = new PrintWriter(pos);

    public RemoteMode(int port){
        this.port = port;
    }
    

    @Override
    public void invoke() throws BadCommandArgumentException, IOException {
        this.run();
    }

    @Override
    public int argsAmount() {
        return 0;
    }

    @Override
    public int getID() {
        return ++INSTANCE_ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.println("Remote mode is listening for incoming connections on "+ port +" port...");
        } catch (IOException ex) {
            System.out.println("could not listen on port: "+ port);
            System.out.println("Try to use another port.");
            return;
        }
        while(true){
            try(Socket s = server.accept()) {
                System.out.println("Connection established.");
                EchoThread et = new EchoThread(s);
                Thread t = new Thread(et);
                CLIENT_LIST.add(et);
                t.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    @Override
    public Boolean call() throws Exception {
        return true;
    }

    public PipedOutputStream getPos() {
        return pos;
    }
    public static void write(String line) throws IOException{
        pw.write(line+"\n");
        pw.flush();
        
    }
    synchronized private static final void echo(PrintStream s){
//        for(Socket socket : CLIENT_LIST){
//        BufferedWriter bw = new BufferedWriter(new PrintWriter(s));
//        }
    }
    
    
}
