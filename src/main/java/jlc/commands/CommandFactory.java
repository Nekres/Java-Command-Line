/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ThreadFactory;
import jlc.commands.impl.*;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandFactory implements ThreadFactory{
    
    public static Command createCommand(String command, String[] arg) throws BadCommandArgumentException{
        if (command.equals(ChangeDirectory.NAME)){
            if (arg.length == 1)
            return new ChangeDirectory(arg[0]);
            else
                throw new BadCommandArgumentException("Ошибка: Неверное количество аргументов.");
        }
        if (command.equals(Dir.NAME)) {
            if (arg.length == 1)
                return new Dir(arg[0]);
            else
                return new Dir();
        }
        if(command.equals(DirectoryTree.NAME)){
            if (arg.length == 0)
            return new DirectoryTree();
            else
                throw new BadCommandArgumentException("Ошибка: Неверное количество аргументов.");
        }
        if(arg.length > 0)
            return new SystemTask(command,arg);
        else
            return new SystemTask(command);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
