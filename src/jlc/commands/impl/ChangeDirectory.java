/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class ChangeDirectory implements Command{
    private static final int ARG_AMOUNT = 1;
    private static final String SPLITTER = "/";
    private static final String GET_BACK = "..";
    private final String currentDir;
    private final String arg;

    public ChangeDirectory(String currentDir, String arg) {
        this.currentDir = currentDir;
        this.arg = arg;
    }
    
    @Override
    public String invoke() throws BadCommandArgumentException{
        if (arg == null)
            throw new BadCommandArgumentException("no args");
        if (arg.equals(GET_BACK) && currentDir.split(SPLITTER).length > 1) {     
            String result = "";
            for (int i = 1; i < currentDir.split(SPLITTER).length - 1; i++) {
                result += SPLITTER + currentDir.split(SPLITTER)[i];
            }
            return result;
        }
        for (File file : new File(currentDir).listFiles()) {   
            if (arg.equals(file.getName()) && new File(currentDir + SPLITTER +arg).isDirectory()) {
                return currentDir + SPLITTER + arg;
            }
        }
        return currentDir;
    }

    @Override
    public String call() throws Exception {
        return this.invoke();
    }

    @Override
    public int argsAmount() {
        return ARG_AMOUNT;
    }

    
}
