/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import jlc.commands.impl.sys.SystemTask;
import jlc.commands.impl.rm.RemoteMode;
import java.util.concurrent.ThreadFactory;
import jlc.commands.impl.*;
import jlc.commands.impl.rm.Who;
import jlc.commands.impl.rm.Whisper;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class CommandFactory implements ThreadFactory {
    /**
     * Creates one of the specified command by their full name and arrays of arguments. 
     * @param command - Name of command to create. For example ChangeDirectory.NAME;
     * @param arg - array of arguments to this command. Put null if argument count equals to 0.
     * @return one of the command that implements Command interface.
     * @throws BadCommandArgumentException - if command requires more arguments, less arguments, or arguments are wrong.
     */
    public static Command createCommand(final String command, final String[] arg) throws BadCommandArgumentException {
        if (command.equals(ChangeDirectory.NAME)) {
            if (arg == null || arg.length != 1)
                throw new BadCommandArgumentException();
            else
                return new ChangeDirectory(arg[0]);
        }
        if (command.equals(Dir.NAME)) {
            if (arg == null || arg.length == 0)
                return new Dir();
            if (arg.length == 1) {
                return new Dir(arg[0]);
            }
            throw new BadCommandArgumentException();
        }
        if (command.equals(DirectoryTree.NAME)) {
            if (arg == null || arg.length == 0)
                return new DirectoryTree();
            throw new BadCommandArgumentException();
        }
        if (command.equals(ActiveCommandsManager.NAME)) {
            if (arg.length == 0 || arg == null)
                return new ActiveCommandsManager();
            throw new BadCommandArgumentException();
        }
        if (command.equals(RemoteMode.NAME)){
            if (arg == null || arg.length == 0){
                return new RemoteMode(RemoteMode.DEFAULT_PORT);
            }
            else if (arg.length == 1){
                return new RemoteMode(Integer.parseInt(arg[0]));
            }
            throw new BadCommandArgumentException();
        }
        if (command.equals(Slay.NAME)){
            if (arg.length == 1){
                try{
                int a = Integer.parseInt(arg[0]);
                return new Slay(a);
                }catch(NumberFormatException nfx){
                    BadCommandArgumentException bca = new BadCommandArgumentException("ID of the command must be a number. Look for id of process you need by \"jobs\"", nfx);
                    throw bca;
                }
            }
            throw new BadCommandArgumentException("Slay command should have only one argument.");
        }
        if (command.equals(Who.NAME)){
            if(arg == null || arg.length == 0)
                return new Who();
            throw new BadCommandArgumentException();
        }
        if (command.equals(Whisper.NAME)){
            if(arg == null && arg.length != 1)
                throw new BadCommandArgumentException("Write command requires a message to send.");
            else if(arg.length == 1){
                return new Whisper(arg[0]);}
            throw new BadCommandArgumentException();
        }
        if(arg == null || arg.length == 0)
            return new SystemTask(command);
        else if (arg.length > 0) {
            return new SystemTask(command, arg);
        }
        throw new BadCommandArgumentException();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
