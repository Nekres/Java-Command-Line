/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.util.ArrayList;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

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
    public void invoke() throws BadCommandArgumentException, IOException {
        ProcessBuilder pb = new ProcessBuilder(task);
        try {
            Process p = pb.start();
            p.waitFor();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            bw.write("Выполнение команды прервано.");
        }
    }

    @Override
    public int argsAmount() {
        return task.size()-1;
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
        this.bw = new BufferedWriter(new PrintWriter(path));;
    }
    
    @Override
    public String toString() {
        return "_"+ task.get(0) + "#ID{" + INSTANCE_ID++;
    }

    @Override
    public String getName() {
        return task.get(0);
    }
    
}
