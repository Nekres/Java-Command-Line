/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;

/**
 *
 * @author desolation
 */
public interface Command extends Runnable {

    void invoke() throws BadCommandArgumentException, IOException;//return currentDir if dir not changed

    int argsAmount(); //minimal count of args

    static void execute(List<Command> commands, boolean daemon, String logFilePath) throws BadCommandArgumentException, IOException, NoSuchCommandException {
        if (commands.isEmpty()) {
            throw new BadCommandArgumentException("Enter the command.");
        }
        if (daemon) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (Command c : commands) {
                            File logDIR = new File(logFilePath + "/logs/" + c.getName().toUpperCase());
                            if (!logDIR.exists()) {
                                logDIR.mkdirs();
                            }
                            File logFile = new File(logFilePath + "/logs/" + c.getName().toUpperCase() + "/" + new Date().toString() + c.toString() + ".txt");
                            logFile.createNewFile();
                            c.setOutputPath(new PrintStream(logFile));
                            c.invoke();
                        }
                    } catch (IOException e) {
                        System.out.println("Error: no such command.\n");
                    } catch (BadCommandArgumentException ex) {
                        System.out.println("Error: bad args.");
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } else {
            for (Command c : commands) {
                c.invoke();
            }
        }
    }

    void setOutputPath(PrintStream path);

    String getName();

}
