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
import java.util.ArrayList;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class SystemTask implements Command{
    private ArrayList<String> task = new ArrayList<>();
    private BufferedWriter bw = new BufferedWriter(new PrintWriter(System.out));
    public SystemTask(String task, String[] args) {
        this.task.add(task);
        for(String s :args)
            this.task.add(s);
    }
    
    public SystemTask(String task) {
        this.task.add(task);
    }
    
    @Override
    public void invoke() throws BadCommandArgumentException {
        try{
        ProcessBuilder pb = new ProcessBuilder(task);
        try {
            Process p = pb.start();
            p.waitFor();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            bw.write("Выполнение команды прервано.");
        }
        }catch(IOException e){
            System.out.println(e.getMessage());
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
        }
    }

    @Override
    public void setOutputPath(PrintStream path) {
        this.bw = new BufferedWriter(new PrintWriter(path));;
    }
    
}
