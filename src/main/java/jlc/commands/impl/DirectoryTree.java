/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.util.*;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;

/**
 *
 * @author desolation
 */
public class DirectoryTree extends AbstractCommand implements Command{
    public static String NAME = "tree";
    public static final int ARG_AMOUNT = 0;
    private static int inline = 0;

    public DirectoryTree() {
    }
    
    private final void check(List<File> list, int brackets) throws BadCommandArgumentException, IOException{
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i).getName() + " ");
            }
            bw.write("\n");
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isDirectory()) {
                    bw.write(list.get(i).getName());
                    print(bw);
                    bw.write("{("+ ++brackets + ")");
                    inline++;
                    if (list.get(i).listFiles() != null) {
                        check(Arrays.asList(list.get(i).listFiles()),brackets);
                    }
                   // print(bw);
                    bw.write("}(" + --brackets + ")");
                }
            }
            inline--;
    }

    private final void print(BufferedWriter bw) throws IOException{
        for(int i = 0; i < inline;i++){
            bw.write(" ");
        }
    }

    @Override
    public void invoke() throws BadCommandArgumentException, IOException {
        List<File> list = Arrays.asList(new File(System.getProperty("user.dir")));
        check(list,0);
        if(!currentOutput.equals(DEFAULT_OUTPUT))
            bw.close();
        else
            bw.flush();
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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    @Override
    public void setOutputPath(PrintStream path) {
        this.bw = new BufferedWriter(new PrintWriter(path));
    }

    @Override
    public String toString() {
        return "_TREE #ID{" + INSTANCE_ID++;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
