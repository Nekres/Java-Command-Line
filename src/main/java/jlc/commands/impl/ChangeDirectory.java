/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;

/**
 *
 * @author desolation
 */
public class ChangeDirectory extends AbstractCommand implements Command {
    private static final String SPLITTER = System.getProperty("file.separator");
    private static final String RETURN = "..";
    public static String NAME = "cd";
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
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws Exception {
        try{
        String currentDir = System.getProperty("user.dir");
        String dat[], result = "";
        if (SPLITTER.equals("\\"))
        dat = currentDir.split(SPLITTER+SPLITTER);
        else dat = currentDir.split(SPLITTER);
        if (arg.equals(RETURN) && dat.length > 1) {
            if (dat.length >= 3) {
                result = currentDir.substring(0, currentDir.lastIndexOf(SPLITTER)).intern();
            } else {
                result = currentDir.substring(0, currentDir.lastIndexOf(SPLITTER) + 1).intern();
            }
            System.setProperty("user.dir", result);
            return true;
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
                    return true;
                }
            }
        }
        throw new BadCommandArgumentException("Error: No such directory\"" + arg + "\".");
        }catch(JCLException e){
            return false;
        }
    }
    
}
