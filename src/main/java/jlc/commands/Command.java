/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import jlc.commands.impl.ActiveCommandsManager;
import jlc.exceptions.*;
import org.apache.commons.io.output.CloseShieldOutputStream;

/**
 *
 * @author desolation
 */
public interface Command extends Callable<Boolean> {
    public static final String DIRECTORY_SEPARATOR = System.getProperty("file.separator");
    
    void setSeparator(String delim);
    
    String getSeparator();
    /**
     * @param out - stream where you redirecting sysout of command
     */
    void setOutputStream(OutputStream out);
    void setErrStream(PrintStream err);
    void setInputStream(InputStream in);

    int getID();
    /**
     * 
     * @return - returns the name of the specific command
     */
    String getName();
    /**
     * Executs commands one by one if as daemons(output will be written to file) 
     * or non-daemons(ouput will be written to System.out). 
     * @param commands - list of the commands to execute
     * @param daemon - value to choose execution type
     * @param logFilePath - directory where logs will be written
     * @throws BadCommandArgumentException
     * @throws NoSuchCommandException
     */
    static void execute(List<Command> commands, boolean daemon, String logFilePath) throws BadCommandArgumentException, NoSuchCommandException, JCLException {
        if (commands.isEmpty()) {
            throw new BadCommandArgumentException("Enter the command.");
        }
        if (daemon) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ExecutorService exec = Executors.newSingleThreadExecutor();
                        for (Command c : commands) {
                            File logDIR = new File(logFilePath + DIRECTORY_SEPARATOR+"logs"+DIRECTORY_SEPARATOR + c.getName().toUpperCase());
                            if (!logDIR.exists()) {
                                logDIR.mkdirs();
                            }
                            File logFile = new File(logFilePath + DIRECTORY_SEPARATOR+"logs"+DIRECTORY_SEPARATOR + c.getName().toUpperCase() + DIRECTORY_SEPARATOR + new Date().toString().replace(':', '.') + c.toString() + ".txt");
                            logFile.createNewFile();
                            try(FileOutputStream fos = new FileOutputStream(logFile)){
                            c.setOutputStream(fos);
                            Future<Boolean> result = exec.submit((Callable)c);
                            ActiveCommandsManager.add(c,c.getID(),result);
                            if(!result.get().booleanValue()){
                            if(c.getSeparator() == null)
                            break;
                            if(c.getSeparator().equals("&&"))
                            break;
                            }
                    }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error: no such command.\n");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch(ExecutionException ex){
                        System.out.println(ex.getCause().getMessage());
                    } catch(CancellationException c){
                        System.out.println("Process was killed by \"slay\" command.");
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } else {
            executeToStream(commands, System.out, true);
        }
    }
    /**
     * Executing command to selected OutputStream.
     * @param commands - list of the command to execute
     * @param os - stream where output will be written
     * @param protectStream - protect stream by CloseShieldOutputStream. This is need when you not done with stream
     */
    public static void executeToStream(final List<Command> commands, final OutputStream os, boolean protectStream) throws JCLException{
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(Command command : commands){
            if (protectStream) {
                command.setOutputStream(new CloseShieldOutputStream(os));
            } else {
                command.setOutputStream(os);
            }
            Future<Boolean> result = exec.submit((Callable)command);
            ActiveCommandsManager.add(command,command.getID(),result);
            try {
                if(!result.get().booleanValue()){
                    if(command.getSeparator() == null)
                        break;
                    if(command.getSeparator().equals("&&"))
                        break;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new CloseShieldOutputStream(os)))){
                    if (os != null)
                    bw.write(ex.getCause().getMessage()+"\n");
                }catch(IOException e){
                    System.out.println("Connection was refused.");
                }
            }
            catch(CancellationException c){
                        throw new JCLException("Process was killed by \"slay\" command.",c);
                    }
            ActiveCommandsManager.remove(command.getID());
        }
        exec.shutdown();
    }
    public static void executeToStream(final Command command, final OutputStream os, boolean protectStream) throws JCLException{
        ArrayList<Command> list = new ArrayList<>();
        list.add(command);
        executeToStream(list, os, protectStream);
    }
}
