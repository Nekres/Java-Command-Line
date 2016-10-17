/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class DirectoryTree implements Command{
    public static final int ARG_AMOUNT = 0;
    private static int inline = 0;
    private final String currentDir;

    public DirectoryTree(String currentDir) {
        this.currentDir = currentDir;
    }
    
    private final void check(List<File> list) throws BadCommandArgumentException{ 
        for(int i = 0; i < list.size(); i++)  
            System.out.println(list.get(i).getName());
        for(int i = 0; i < list.size(); i++)    
            if(list.get(i).isDirectory()){
                System.out.print(list.get(i).getName());
                print();
                System.out.println("{");
                inline++;
                check(Arrays.asList(list.get(i).listFiles()));
                print();
                System.out.println("}");
            }
        inline--;
    }

    private final void print(){
        for(int i = 0; i < inline;i++){
            System.out.print(" ");
        }
    }

    @Override
    public void invoke() throws BadCommandArgumentException {
        List<File> list = Arrays.asList(new File(currentDir));
        check(list);
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
        }
    }

    
}
