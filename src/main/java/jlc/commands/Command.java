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
public interface Command extends Callable<String> {
     String invoke() throws BadCommandArgumentException;//return currentDir if dir not changed
     int argsAmount();
     static String execute(Command command, boolean daemon) throws BadCommandArgumentException{
        return command.invoke();
    }
     static String execute(List<Command> commands) throws BadCommandArgumentException, InterruptedException, ExecutionException{
        if (commands.size() == 0)
            throw new BadCommandArgumentException();
        ExecutorService es = Executors.newFixedThreadPool(commands.size());
        String result = "";
        for (Command c : commands){
            result = es.submit(c).get();
        }
        return result;
    }
}
