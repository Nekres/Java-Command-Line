/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import jlc.commands.Command;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;
import jlc.view.TextStyle;

/**
 *
 * @author desolation
 */
public class DirectoryTree extends AbstractCommand implements Command {

    public static String NAME = "tree";
    public static final int ARG_AMOUNT = 0;
    private int summary = -1; // -1 excluding root of the tree
    private int dirSummary = -1;
    private final String next = System.lineSeparator();

    public DirectoryTree() {
    }

    private final void check(List<File> list) throws BadCommandArgumentException, IOException {
        for (File file : list) {
            if (!Files.isReadable(file.toPath())
                    || Files.isSymbolicLink(FileSystems.getDefault().getPath(file.getAbsolutePath()))) {
                continue;
            }
            summary++;
            bw.write(file.getName());
            bw.write(next);
            bw.flush();
            if (file.isDirectory()) {
                dirSummary++;
                if (file.listFiles().length != 0 && file.listFiles() != null) {
                    check(Arrays.asList(file.listFiles()));
                }
            }
        }
    }

    @Override
    public void invoke() throws BadCommandArgumentException, IOException {
        List<File> list = Arrays.asList(new File(System.getProperty("user.dir") + System.getProperty("file.separator")));
        check(list);
        String summary = "Summary:" + this.summary + ", directories:"
                + dirSummary + ", files:" + (this.summary - dirSummary) + next;
        if (!System.getProperty("os.name").contains("Windows")) {
            bw.write(TextStyle.colorText(summary, TextStyle.Color.MAGENTA));
        } else {
            bw.write(summary);
        }
        if (!currentOutput.equals(DEFAULT_OUTPUT)) {
            bw.close();
        } else {
            bw.flush();
        }
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
        return "_TREE#ID{" + this.id;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getID() {
        return this.id;
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
