/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class ChangeDirectory extends AbstractCommand implements Command {
    public static String NAME = "cd";
    private static final int ARG_AMOUNT = 1;
    private static String SPLITTER = System.getProperty("file.separator");
    private static final String RETURN = "..";
    private String arg = "";
    
    public ChangeDirectory(String[] arg) {
        for (String a : arg) {
            this.arg = this.arg.concat(a + " ");
        }
        this.arg = this.arg.trim();
    }

    public ChangeDirectory() {
    }

    @Override
    public void invoke() throws BadCommandArgumentException {
        String currentDir = System.getProperty("user.dir");
        String dat[], result = "";
        if (SPLITTER.equals("\\"))
        dat = currentDir.split(SPLITTER+SPLITTER);
        else dat = currentDir.split(SPLITTER);
        if (arg.equals(RETURN) && dat.length > 1) {
            if (dat.length >= 3) {
                result = currentDir.substring(0, currentDir.lastIndexOf(SPLITTER));
            } else {
                result = currentDir.substring(0, currentDir.lastIndexOf(SPLITTER) + 1);
            }
            System.setProperty("user.dir", result);
            return;
        }
        File nextDir = new File(currentDir);
        
        if (nextDir.listFiles().length != 0) {
            for (File file : nextDir.listFiles()) {
                if (arg.toLowerCase().equals(file.getName().toLowerCase()) && file.isDirectory() && !file.isHidden()) {
                    if (!currentDir.substring(currentDir.length()-1, currentDir.length()).equals(SPLITTER)) {
                        System.setProperty("user.dir", currentDir + SPLITTER + file.getName());
                    } else {
                        System.setProperty("user.dir", currentDir + file.getName());
                    }
                    return;
                }
            }
        }
        throw new BadCommandArgumentException("Error: No such directory\"" + arg + "\".");
    }

    @Override
    public int argsAmount() {
        return ARG_AMOUNT;
    }

    @Override
    public void run() {
        try {
            invoke();
        } catch (BadCommandArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void setOutputPath(PrintStream path) {
    }

    @Override
    public String toString() {
        return "_CD#ID" + this.id;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getID() {
        return this.id;
    }
    
}
