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

/**
 *
 * @author desolation
 */
public interface Command extends Runnable{
     void invoke() throws BadCommandArgumentException,IOException;//return currentDir if dir not changed
     int argsAmount(); //minimal count of args
     static void execute(List<Command> commands, boolean daemon) throws BadCommandArgumentException, InterruptedException, ExecutionException, FileNotFoundException, IOException{
         if (commands.size() == 0)
            throw new BadCommandArgumentException();
        ExecutorService es;
        if (daemon) {
            es = Executors.newCachedThreadPool(new CommandFactory());
            for (Command c : commands) {
                File logFile = new File("logs/"+ c.getName() + "/" + new Date().toString() + c.toString()+".txt");
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
