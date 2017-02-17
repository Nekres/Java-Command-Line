/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import jlc.commands.impl.rm.RemoteMode;
import java.util.Arrays;
import java.util.concurrent.ThreadFactory;
import jlc.commands.impl.*;
import jlc.commands.impl.rm.Who;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandFactory implements ThreadFactory {

    public static Command createCommand(String command, String[] arg) throws BadCommandArgumentException {
        if (command.equals(ChangeDirectory.NAME)) {
            if (arg.length > 0) {
                return new ChangeDirectory(arg);
            } else {
                throw new BadCommandArgumentException();
            }
        }
        if (command.equals(Dir.NAME)) {
            if (arg.length == 1) {
                return new Dir(arg[0]);
            } else {
                return new Dir();
            }
        }
        if (command.equals(DirectoryTree.NAME)) {
            if (arg.length == 0) {
                return new DirectoryTree();
            } else {
                throw new BadCommandArgumentException();
            }
        }
        if (command.equals(ActiveCommandsManager.NAME)) {
            if (arg.length == 0) {
                return new ActiveCommandsManager();
            } else {
                throw new BadCommandArgumentException();
            }
        }
        if (command.equals(RemoteMode.NAME)){
            if (arg.length == 0){
                return new RemoteMode(RemoteMode.DEFAULT_PORT);
            }
            else if (arg.length == 1){
                return new RemoteMode(Integer.parseInt(arg[0]));
            }
            throw new BadCommandArgumentException();
        }
        if (command.equals(Kill.NAME)){
            if (arg.length == 1){
                try{
                int a = Integer.parseInt(arg[0]);
                return new Kill(a);
                }catch(NumberFormatException efx){
                    throw new BadCommandArgumentException("ID of the command must be a number. Look for id of process you need by \"jobs\"");
                }
            }
            throw new BadCommandArgumentException("Slay command has only one argument.");
        }
        if (command.equals(Who.NAME)){
            if(arg.length == 0)
                return new Who();
            throw new BadCommandArgumentException();
        }
        if (arg.length > 0) {
            return new SystemTask(command, arg);
        } else {
            return new SystemTask(command);
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
