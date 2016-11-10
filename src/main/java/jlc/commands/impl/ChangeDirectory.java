/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.io.PrintStream;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class ChangeDirectory extends AbstractCommand implements Command{
    public static String NAME = "cd";
    private static final int ARG_AMOUNT = 1;
    private static final String SPLITTER = "/";
    private static final String RETURN = "..";
    private  String arg;

    public ChangeDirectory(String arg) {
        this.arg = arg;
    }
    public ChangeDirectory(){
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException{
        String currentDir = System.getProperty("user.dir");
        if (arg.equals(RETURN) && currentDir.split(SPLITTER).length > 1) {     
            String result = "";
            for (int i = 1; i < currentDir.split(SPLITTER).length - 1; i++) {
                result += SPLITTER + currentDir.split(SPLITTER)[i];
            }
            
            if(result.equals("") && System.getProperty("os.name").equals("Linux"))
                result = "/";
            System.setProperty("user.dir", result);
            return;
        }
        File nextDir = new File(currentDir);
        System.out.println(currentDir);
        if(nextDir.listFiles().length != 0)
        for (File file : nextDir.listFiles()) {
            if (arg.toLowerCase().equals(file.getName().toLowerCase()) && new File(currentDir + SPLITTER + arg).isDirectory()) {
                System.setProperty("user.dir", currentDir + SPLITTER + arg);
                return;
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
    public void setOutputPath(PrintStream path) {}

    @Override
    public String toString() {
        return "_CD #ID" + INSTANCE_ID++;
    }

    @Override
    public String getName() {
        return NAME;
    }

    
}
