/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl.rm;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import jlc.commands.Command;
import jlc.commands.impl.AbstractCommand;
import jlc.commands.impl.ActiveCommandsManager;
import jlc.exceptions.JCLException;
import jlc.exceptions.ProcessKilledException;
import jlc.parse.CLParser;
import jlc.view.TextStyle;
import org.apache.commons.io.output.CloseShieldOutputStream;

/**
 *
 * @author desolation
 */
public class RemoteMode extends AbstractCommand implements Command{
    public static final List<EchoThread> CLIENT_LIST = new ArrayList<>();
    public static final String NAME = "remote".intern();
    public static final int DEFAULT_PORT = 5000;
    public static boolean ENABLED = true;
    private ServerSocket server;
    private final int port;

    public RemoteMode(int port){
        this.port = port;
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws ProcessKilledException {
        try {
            server = new ServerSocket(port,20);
            System.out.println("\nRemote mode is listening for incoming connections on "+ port +" port...");
            System.out.println("Run remote in daemon using\"remote &\" to have a possibility to write from keyboard or discard remote mode\n"
                    + "You can use \"who\" to get list of all clients and \"write\" to send message for everyone connected");
        } catch (IOException ex) {
            System.out.println(TextStyle.colorText("could not listen on port: "+ port, TextStyle.Color.RED));
            System.out.println(TextStyle.colorText("Try to use another port.", TextStyle.Color.RED));
            ActiveCommandsManager.remove(this.getID());
            return false;
        } 
        Socket s = null;
            try{
                while(!server.isClosed()){
                s = server.accept();
                System.out.println(TextStyle.colorText("Connection established with" + s.getInetAddress(), TextStyle.Color.BLUE));
                EchoThread et = new EchoThread(s);
                CLIENT_LIST.add(et);
                Thread t = new Thread(et);
                t.start();
                }
            } catch (IOException ex) {
                throw new ProcessKilledException("Remote mode killed.");
            } 
            finally{
            try {
                ActiveCommandsManager.remove(this.getID());
                if (!server.isClosed())
                server.close();
                if(s != null && !s.isClosed())
                s.close();
                CLIENT_LIST.clear();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            }
            return true;
    }
    synchronized public final void destroy(){
        try {
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    synchronized public static final void remoteExecute(final String command, final OutputStream os) throws JCLException{
        System.out.println(command);
            if(command.equals("quit")){
                System.out.println(TextStyle.colorText("Bye.\nWe'll miss you.",TextStyle.Color.BRIGHT));
                System.exit(0);
            }
                List<Command> commandList = CLParser.analyze(jlc.JLC.settings, command);
                Command.executeToStream(commandList,os, true);
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new CloseShieldOutputStream(os)))){
                   bw.write(System.getProperty("user.dir").intern()+":");
                }
                    catch(IOException e){
                    throw new ProcessKilledException("Connection was refused.");
                }
    }
    }
