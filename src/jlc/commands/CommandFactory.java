/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import jlc.commands.impl.ChangeDirectory;
import jlc.commands.impl.Dir;
import jlc.commands.impl.DirectoryTree;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandFactory {
    
    public static Command createCommand(Class<? extends Command> command, String currentDir, String arg) throws BadCommandArgumentException{
        if (command.equals(ChangeDirectory.class)){
            return new ChangeDirectory(currentDir, arg);
        }
        if (command.equals(Dir.class)) {
            if (arg != null)
                return new Dir(currentDir, arg);
            else
                return new Dir(currentDir);
        }
        if(command.equals(DirectoryTree.class)){
            return new DirectoryTree(currentDir);
        }
        throw new BadCommandArgumentException("комманда не найдена");
    }
}
