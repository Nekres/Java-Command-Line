/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.util.concurrent.ThreadFactory;
import jlc.commands.impl.ChangeDirectory;
import jlc.commands.impl.Dir;
import jlc.commands.impl.DirectoryTree;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandFactory implements ThreadFactory{
    
    public static Command createCommand(Class<Command> command, String currentDir, String[] arg) throws BadCommandArgumentException{
        if (command.equals(ChangeDirectory.class)){
            if (arg.length == 1)
            return new ChangeDirectory(arg[0]);
            else
                throw new BadCommandArgumentException("Ошибка: Неверное количество аргументов.");
        }
        if (command.equals(Dir.class)) {
            if (arg.length == 1)
                return new Dir(currentDir, arg[0]);
            else
                return new Dir(currentDir);
        }
        if(command.equals(DirectoryTree.class)){
            if (arg.length == 0)
            return new DirectoryTree(currentDir);
            else
                throw new BadCommandArgumentException();
        }
        throw new BadCommandArgumentException("Ошибка: комманда не найдена.");
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
