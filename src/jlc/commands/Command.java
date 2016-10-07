/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.util.List;
import java.util.concurrent.Callable;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public interface Command extends Callable<String> {
    public String invoke() throws BadCommandArgumentException;//return currentDir if dir not changed
    public int argsAmount();
    public static String execute(Command command) throws BadCommandArgumentException{
        return command.invoke();
    }
    public static String execute(List<Command> commands) throws BadCommandArgumentException{
        String result = "";
        if (commands.size() == 0)
            throw new BadCommandArgumentException();
        for (Command c : commands){
            result = c.invoke();
        }
        return result;
    }
}
