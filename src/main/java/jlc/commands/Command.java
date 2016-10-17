/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public interface Command extends Runnable{
     void invoke() throws BadCommandArgumentException;//return currentDir if dir not changed
     int argsAmount(); //minimal count of args
     static void execute(Command command) throws BadCommandArgumentException{
        command.invoke();
    }
     static void execute(List<Command> commands, boolean daemon) throws BadCommandArgumentException, InterruptedException, ExecutionException{
        if (commands.size() == 0)
            throw new BadCommandArgumentException();
        ExecutorService es;
        if (daemon) {
            es = Executors.newCachedThreadPool(new CommandFactory());
            String result = "";
            for (Command c : commands) {
                es.execute(c);
                es.shutdown();
            }
        }
        else
            for(Command c: commands)
                Command.execute(c);
    }
}
