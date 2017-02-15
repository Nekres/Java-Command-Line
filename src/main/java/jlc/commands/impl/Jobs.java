/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import jlc.commands.Command;
import static jlc.commands.impl.AbstractCommand.ENCODING;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;
import jlc.view.TextStyle;

/**
 *
 * @author desolation
 */
public class Jobs extends AbstractCommand implements Command {
    public static final String NAME = "jobs";
    private static Map<String, Command> map = new HashMap<>();
    
    public static void add(Command c, String unique_id){
        map.put(unique_id,c);
    }
    public static void remove(String unique_id){
        map.remove(unique_id);
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException, IOException {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(currentOutput,Charset.forName(ENCODING)))){
            if (!map.isEmpty()) {
                bw.write("+Name\t\tID+");
                bw.newLine();
                for (Command c : map.values()) {
                    bw.write("" + c.getName() + "\t\t" + c.getID());
                    bw.newLine();
                    bw.flush();
                }
            } else {
                System.out.println(TextStyle.colorText("No processes running in daemon mode.", TextStyle.Color.RED));
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void run() {
        try{
        invoke();
        }catch(IOException e){
            throw new RuntimeException(e);
        }catch(BadCommandArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Boolean call() throws Exception {
        try{
            invoke();
        }catch(JCLException e){
            return false;
        }
        return true;
    }
    
}
