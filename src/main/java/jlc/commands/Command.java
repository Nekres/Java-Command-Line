/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import jlc.commands.impl.Jobs;
import jlc.exceptions.*;
import org.apache.commons.io.output.CloseShieldOutputStream;

/**
 *
 * @author desolation
 */
public interface Command extends Runnable,Callable<Boolean> {
    public static final String DIRECTORY_SEPARATOR = System.getProperty("file.separator");
    void invoke() throws BadCommandArgumentException, IOException;//return currentDir if dir not changed
    
    void setSeparator(String delim);
    
    String getSeparator();
    /**
     * @return - returns minimal count of args
     */
    /**
     * @param path - stream where you redirecting sysout of command
     */
    void setOutputPath(OutputStream path);

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
    static void execute(List<Command> commands, boolean daemon, String logFilePath) throws BadCommandArgumentException, NoSuchCommandException, InterruptedException, ExecutionException {
        if (commands.isEmpty()) {
            throw new BadCommandArgumentException("Enter the command.");
        }
        if (daemon) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ExecutorService exec = Executors.newFixedThreadPool(commands.size());
                        for (Command c : commands) {
                            File logDIR = new File(logFilePath + DIRECTORY_SEPARATOR+"logs"+DIRECTORY_SEPARATOR + c.getName().toUpperCase());
                            if (!logDIR.exists()) {
                                logDIR.mkdirs();
                            }
                            File logFile = new File(logFilePath + DIRECTORY_SEPARATOR+"logs"+DIRECTORY_SEPARATOR + c.getName().toUpperCase() + DIRECTORY_SEPARATOR + new Date().toString().replace(':', '.') + c.toString() + ".txt");
                            logFile.createNewFile();
                            try(FileOutputStream fos = new FileOutputStream(logFile)){
                            c.setOutputPath(fos);
                            Jobs.add(c,c.toString());
                            Future<Boolean> result = exec.submit((Callable)c);
                            if(!result.get().booleanValue()){
                            if(c.getSeparator() == null)
                            break;
                            if(c.getSeparator().equals("&&"))
                            break;
                            }
                    }
                            Jobs.remove(c.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error: no such command.\n");
                    } catch (InterruptedException ex) {
                    } catch (ExecutionException ex) {
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
     * when you want to close
     * @param commands - list of the command to execute
     * @param os - stream where output will be written
     * @param protectStream - protect stream by CloseShieldOutputStream. This is need when you not done with stream
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public static void executeToStream(final List<Command> commands, final OutputStream os, boolean protectStream) throws InterruptedException, ExecutionException{
        ExecutorService exec = Executors.newFixedThreadPool(commands.size());
        for(Command command : commands){
            if (protectStream) {
                command.setOutputPath(new CloseShieldOutputStream(os));
            } else {
                command.setOutputPath(os);
            }
            Future<Boolean> result = exec.submit((Callable)command);
                    if(!result.get().booleanValue()){
                        if(command.getSeparator() == null)
                            break;
                        if(command.getSeparator().equals("&&"))
                            break;
                    }
        }
        exec.shutdown();
    }

}
