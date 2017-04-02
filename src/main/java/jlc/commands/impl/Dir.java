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
 * Implementation of simple command that shows list of files and specific description to each file.
 * @author desolation
 */
public class Dir extends AbstractCommand implements Command{
    public static String NAME = "dir";
    private static final DateFormat DATE = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MONTH_FIELD, SimpleDateFormat.LONG);
    private String arg;
    static{ DATE.setTimeZone(TimeZone.getTimeZone("UTC"));}
        /**
         * Constructor of Dir command to use it later.
         * @param arg fileFilter. *.xml for example, to find xml-files
         */
    public Dir(final String arg) {
        this.arg = arg;
    }
    public Dir(){
    }
    /**
     * Just used for tab
     * @param length length of the string which will be return
     * @return String with spaces(nothing else)
     * @throws IOException 
     */
    private final String printInfo(final int length) throws IOException{
        int max = 40;
        String res = "";
        if(length > max)
            res = System.lineSeparator();
        for(int i = 0; i < max-length;i++){
            res = res + " ";
        }
        return res;
    }
    /**
     * Method searching for files into directory and gives back list of files.
     * @param rootDir - directory which files to get
     * @return - list of files in this directory
     * @throws BadCommandArgumentException 
     */
    public final List<File> dir(String rootDir) throws BadCommandArgumentException{
        List<File> result = new ArrayList<>();
        File root = new File(rootDir);
        if(root.isDirectory()){
            if(arg != null){
                try{
                    for(File f : root.listFiles(new Filter(arg)))
                        result.add(f);
                }catch(PatternSyntaxException psx){
                    throw new BadCommandArgumentException("Bad argument \"" + arg + "\"",psx);
                }
            }else{
                String[] list = root.list();
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                for(File f : root.listFiles())
                    result.add(f);
            }
        }
        return result;
    }
    /**
     * Invoking dir method and writes to the output which specified by {@link Command#setOutputStream(java.io.OutputStream) setOutputStream}
     * @return true if and only if method has no exceptions occured while run
     * @throws Exception 
     */
    @Override
    public Boolean call() throws Exception {
        try{
            try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutputStream,Charset.forName(ENCODING))))){
            File file = new File(System.getProperty("user.dir"));
                String[] list = file.list();
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                for (File f : dir(file.getAbsolutePath())) {
                    bw.write(f.getName());
                    bw.write(printInfo(f.getName().length()));
                    bw.write(DATE.format(new Date(f.lastModified())));
                    bw.write(System.lineSeparator());
                    bw.flush();
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
