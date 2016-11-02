/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.ProcessOutputReader;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.NoSuchCommandException;

/**
 *
 * @author desolation
 */
public class SystemTask extends AbstractCommand implements Command{
    private ArrayList<String> task = new ArrayList<>();
    public SystemTask(String task, String[] args) {
        this.task.add(task);
        for(String s :args)
            this.task.add(s);
    }
    
    public SystemTask(String task) {
        this.task.add(task);
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException,IOException, NoSuchCommandException {
        Thread t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int argsAmount() {
        return task.size()-1;
    }

    @Override
    public void run() {
        ProcessBuilder pb = new ProcessBuilder(task);
        try{
        try {
            Process p = pb.start();
            ProcessOutputReader por = new ProcessOutputReader(p.getInputStream(),currentOutput);
            por.run();
            p.waitFor();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
                bw.write("The command is aborted.");
                bw.flush();
        } catch (IOException ex){
                bw.write("Error: no such command.");
                bw.flush();
        }
        } catch (IOException ex1) {
                throw new RuntimeException(ex1);
            }
    }

    @Override
    public String toString() {
        return "_"+ task.get(0) + "#ID{" + INSTANCE_ID++ + "}";
    }

    @Override
    public String getName() {
        return task.get(0);
    }
    
}
