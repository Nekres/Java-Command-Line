/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;

/**
 *
 * @author desolation
 */
public interface Command extends Runnable{
     void invoke() throws BadCommandArgumentException,IOException,NoSuchCommandException;//return currentDir if dir not changed
     int argsAmount(); //minimal count of args
     static void execute(List<Command> commands, boolean daemon,String logFilePath) throws BadCommandArgumentException, InterruptedException, ExecutionException, FileNotFoundException, IOException, NoSuchCommandException{
         if (commands.size() == 0)
            throw new BadCommandArgumentException("Enter the command.");
        
        ExecutorService es;
        if (daemon) {
            es = Executors.newCachedThreadPool(new CommandFactory());
            for (Command c : commands) {
                File logDIR = new File(logFilePath + "/logs/" + c.getName().toUpperCase());
                if(!logDIR.exists())
                    logDIR.mkdirs();
                File logFile = new File(logFilePath + "/logs/"+ c.getName().toUpperCase() + "/" + new Date().toString() + c.toString()+".txt");
                logFile.createNewFile();
                c.setOutputPath(new PrintStream(logFile));
                es.execute(c);
               // es.shutdown();
            }
            es.shutdown();
        }
        else
            for(Command c: commands)
                c.invoke();
    }
     void setOutputPath(PrintStream path);
     String getName();
        
}
