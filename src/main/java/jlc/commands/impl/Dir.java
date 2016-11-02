/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import jlc.commands.Filter;

/**
 *
 * @author desolation
 */
public class Dir extends AbstractCommand implements Command{
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
    public void invoke() throws BadCommandArgumentException, IOException {
        File file = new File(System.getProperty("user.dir"));
        if (file.isDirectory()){
            if (arg != null){
            try{
                for(File f : file.listFiles(new Filter(arg))) //sort by regex
                bw.write(f.getName());
            }
            catch (PatternSyntaxException e){
                throw new BadCommandArgumentException("Bad argument \"" + arg + "\"");
            }
            }
            else{
                String[] list = file.list();
                Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
                for(File f : file.listFiles()){
                bw.write(f.getName());
                printInfo(f.getName().length());
                    bw.write(DATE.format(new Date(f.lastModified())));
                    bw.write("\n");
                }
            }
        }
        else
            bw.write("Файлов нет.");
        if (!currentOutput.equals(DEFAULT_OUTPUT)){
            bw.close();
        }
        else
            bw.flush();
    }

    private final void printInfo(int length) throws IOException{
        int max = 30;
        if(length > max)
            bw.write("\n");
        for(int i = 0; i < max-length;i++){
            bw.write(" ");
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void setOutputPath(PrintStream path) {
        bw = new BufferedWriter(new PrintWriter(path));
        currentOutput = path;
    }

    @Override
    public String toString() {
        return "_DIR #ID{" + INSTANCE_ID++;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    
    
}
