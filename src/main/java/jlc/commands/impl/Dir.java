/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import java.io.File;
import java.text.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import jlc.commands.Filter;

/**
 *
 * @author desolation
 */
public class Dir implements Command{
    public static String NAME = "dir";
    private static final int ARG_AMOUNT = 0;
    private static final DateFormat DATE = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MONTH_FIELD, SimpleDateFormat.LONG);
    private String arg;
    static{ DATE.setTimeZone(TimeZone.getTimeZone("UTC"));}
        
    public Dir(String arg) {
        this.arg = arg;
    }
    public Dir(){
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException {
        File file = new File(System.getProperty("user.dir"));
        if (file.isDirectory()){
            if (arg != null){
            try{
                for(File f : file.listFiles(new Filter(arg))) //sort by regex
                System.out.println(f.getName());
            }
            catch (PatternSyntaxException e){
                throw new BadCommandArgumentException("Неправильный аргумент \"" + arg + "\"");
            }
            }
            else{
                String[] list = file.list();
                Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
                for(File f : file.listFiles()){
                System.out.print(f.getName());
                printInfo(f.getName().length());
                    System.out.print(DATE.format(new Date(f.lastModified())));
                    System.out.println();
                }
            }
        }
        else
            System.out.println("Файлов нет.");
    }

    private final void printInfo(int length){
        int max = 30;
        if(length > max)
            System.out.println();
        for(int i = 0; i < max-length;i++){
            System.out.print(" ");
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
