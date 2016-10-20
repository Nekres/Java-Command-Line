/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class SystemTask implements Command{
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
    public void invoke() throws BadCommandArgumentException {
        ProcessBuilder pb = new ProcessBuilder(task);
        try {
            Process p = pb.start();
            p.waitFor();
        } catch (IOException ex) {
            System.out.println("Ошибка. Нет такой команды");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Выполнение команды прервано.");
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
    
}
