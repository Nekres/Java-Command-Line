/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.util.List;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandController {
    
    public static String invokeCommand(Command command) throws BadCommandArgumentException{
    //    command.
        return command.invoke();
    }
    public static String invokeCommand(List<Command> commands) throws BadCommandArgumentException{
        String result = "";
        if (commands.size() == 0)
            throw new BadCommandArgumentException();
        for (Command c : commands){
            result = c.invoke();
        }
        return result;
    }
//    d
    
    public static void tree(String currentDir){
       // DirectoryTree tree = new DirectoryTree(new File(currentDir));
    }
}
