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
            System.setProperty("user.dir", result);
            return;
        }
        for (File file : new File(currentDir).listFiles()) {   
            if (arg.equals(file.getName()) && new File(currentDir + SPLITTER +arg).isDirectory()) {
                System.setProperty("user.dir", currentDir + SPLITTER + arg);
                return;
            }
            else
                throw new BadCommandArgumentException("Ошибка: Неправильный аргумент \""+arg+"\"");
        }
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

    
}
