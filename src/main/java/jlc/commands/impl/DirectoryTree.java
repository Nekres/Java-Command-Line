/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
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
    public static String NAME = "tree";
    public static final int ARG_AMOUNT = 0;
    private static int inline = 0;
    private BufferedWriter bw = new BufferedWriter(new PrintWriter(System.out));

    public DirectoryTree() {
    }
    
    private final void check(List<File> list) throws BadCommandArgumentException{
        try {
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i).getName());
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isDirectory()) {
                    bw.write(list.get(i).getName());
                    print(bw);
                    bw.write("{");
                    inline++;
                    if (list.get(i).listFiles() != null) {
                        check(Arrays.asList(list.get(i).listFiles()));
                    }
                    print(bw);
                    bw.write("}");
                }
            }
            inline--;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final void print(BufferedWriter bw) throws IOException{
        for(int i = 0; i < inline;i++){
            bw.write(" ");
        }
    }

    @Override
    public void invoke() throws BadCommandArgumentException {
        List<File> list = Arrays.asList(new File(System.getProperty("user.dir")));
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

    @Override
    public void setOutputPath(PrintStream path) {
        this.bw = new BufferedWriter(new PrintWriter(path));
    }

    
}
