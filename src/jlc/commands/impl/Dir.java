/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.PatternSyntaxException;
import jlc.commands.Filter;

/**
 *
 * @author desolation
 */
public class Dir implements Command{
    private static final DateFormat DATE = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MONTH_FIELD, SimpleDateFormat.LONG);
    private final String currentDir;
    private String arg;
    static{ DATE.setTimeZone(TimeZone.getTimeZone("UTC"));}
        
    public Dir(String currentDir, String arg) {
        this.currentDir = currentDir;
        this.arg = arg;
        
    }
    public Dir(String currentDir){
        this.currentDir = currentDir;
    }
    
    @Override
    public String invoke() throws BadCommandArgumentException {
        File file = new File(currentDir);
        if (file.isDirectory()){
            if (arg != null){
            try{
                for(File f : file.listFiles(new Filter(arg))) //sort by regex
                System.out.println(f.getName());
            }
            catch (PatternSyntaxException e){
                throw new BadCommandArgumentException("Dangling meta character \"" + arg + "\"");
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
            System.out.println("no files exist.");
        return currentDir;
    }

    @Override
    public String call() throws Exception {
        return this.invoke();
    }
    private final void printInfo(int length){
        int max = 30;
        if(length > max)
            System.out.println();
        for(int i = 0; i < max-length;i++){
            System.out.print(" ");
        }
    }

    
}
