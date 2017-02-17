/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import java.io.*;
import java.nio.charset.Charset;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jlc.exceptions.JCLException;

/**
 *
 * @author desolation
 */
public class Dir extends AbstractCommand implements Command{
    public static String NAME = "dir";
    private static final DateFormat DATE = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MONTH_FIELD, SimpleDateFormat.LONG);
    private String arg;
    static{ DATE.setTimeZone(TimeZone.getTimeZone("UTC"));}
        
    public Dir(final String arg) {
        this.arg = arg;
    }
    public Dir(){
    }
    
    private final void printInfo(final int length, final BufferedWriter bw) throws IOException{
        int max = 40;
        if(length > max)
            bw.write(System.lineSeparator());
        for(int i = 0; i < max-length;i++){
            bw.write(" ");
        }
    }

    @Override
    public Boolean call() throws Exception {
        try{
            try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutput,Charset.forName(ENCODING))))){
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
            else {
                String[] list = file.list();
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                for (File f : file.listFiles()) {
                    bw.write(f.getName());
                    printInfo(f.getName().length(),bw);
                    bw.write(DATE.format(new Date(f.lastModified())));
                    bw.write(System.lineSeparator());
                    bw.flush();
                }
            }
        }
        }
        }catch(JCLException e){
            ActiveCommandsManager.remove(this.getID());
            return false;
        }
        ActiveCommandsManager.remove(this.getID());
        return true;
    }
    @Override
    public String getName() {
        return NAME;
    }
    
    private final class Filter implements FilenameFilter{
        
    private Pattern pattern;

    public Filter(final String regex) {
        this.pattern = Pattern.compile(regex);
    }
    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
    
}
    
}
