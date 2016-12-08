/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;

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
        System.out.println("+Name\t\tID+");
        for(Command c : map.values()){
            System.out.println(""+c.getName()+"\t\t"+c.getID());
        }
    }

    @Override
    public int argsAmount() {
        return 0;
    }

    @Override
    public void setOutputPath(PrintStream path) {
        this.currentOutput = path;
        this.bw = new BufferedWriter(new PrintWriter(path));
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
