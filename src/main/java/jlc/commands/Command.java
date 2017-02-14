/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jlc.commands.impl.Jobs;
import jlc.commands.impl.SystemTask;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;

/**
 *
 * @author desolation
 */
public interface Command extends Runnable,Callable<Boolean> {

    void invoke() throws BadCommandArgumentException, IOException;//return currentDir if dir not changed
    
    void setSeparator(String delim);
    
    String getSeparator();
    /**
     * @return - returns minimal count of args
     */
    int argsAmount();
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
     * or non-daemons(ouput will be written to System.in). 
     * @param commands - list of the commands to execute
     * @param daemon - value to choose execution type
     * @param logFilePath - directory where logs will be written
     * @throws BadCommandArgumentException
     * @throws IOException
     * @throws NoSuchCommandException
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    static void execute(List<Command> commands, boolean daemon, String logFilePath) throws BadCommandArgumentException, IOException, NoSuchCommandException, InterruptedException, ExecutionException {
        if (commands.isEmpty()) {
            throw new BadCommandArgumentException("Enter the command.");
        }
        if (daemon) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String f_separator = System.getProperty("file.separator");
                        for (Command c : commands) {
                            File logDIR = new File(logFilePath + f_separator+"logs"+f_separator + c.getName().toUpperCase());
                            if (!logDIR.exists()) {
                                logDIR.mkdirs();
                            }
                            File logFile = new File(logFilePath + f_separator+"logs"+f_separator + c.getName().toUpperCase() + f_separator + new Date().toString().replace(':', '.') + c.toString() + ".txt");
                            logFile.createNewFile();
                            try(FileOutputStream fos = new FileOutputStream(logFile)){
                            c.setOutputPath(fos);
                            Jobs.add(c,c.toString());
                            if(c.getClass().equals(SystemTask.class)){
                                c.run();
                            }
                            else
                            c.invoke();
                            }
                            Jobs.remove(c.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error: no such command.\n");
                    } catch (BadCommandArgumentException ex) {
                        System.out.println("Error: bad args.");
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } else {
                ExecutorService exec = Executors.newFixedThreadPool(commands.size());
                for(Command command : commands){
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

}
