/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.util.Objects;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class ChangeDirectory extends AbstractCommand implements Command {
    private static final String SPLITTER = System.getProperty("file.separator");
    private static final String RETURN = "..";
    public static String NAME = "cd";
    private String arg = "";
    
    public ChangeDirectory(String arg) {
        this.arg = arg;
        this.arg = this.arg.trim();
    }

    public ChangeDirectory() {
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws BadCommandArgumentException {
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
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.arg);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChangeDirectory other = (ChangeDirectory) obj;
        if (!Objects.equals(this.arg, other.arg)) {
            return false;
        }
        return true;
    }
    
}
