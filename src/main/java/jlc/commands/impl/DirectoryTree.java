/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.*;
import java.nio.charset.Charset;
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
    private static final String NEXT = System.lineSeparator().intern();
    public static String NAME = "tree";
    private int summary = -1; // -1 excluding root of the tree
    private int dirSummary = -1;

    public DirectoryTree() {
    }

    private final void check(List<File> list, BufferedWriter bw) throws BadCommandArgumentException, IOException {
        for (File file : list) {
            if (!Files.isReadable(file.toPath())
                    || Files.isSymbolicLink(FileSystems.getDefault().getPath(file.getAbsolutePath()))) {
                continue;
            }
            summary++;
            bw.write(file.getName());
            bw.write(NEXT);
            bw.flush();
            if (file.isDirectory()) {
                dirSummary++;
                if (file.listFiles().length != 0 && file.listFiles() != null) {
                    check(Arrays.asList(file.listFiles()), bw);

                }
            }
            file = null;
        }
        list = null;
    }

    @Override
    public void invoke() throws BadCommandArgumentException, IOException {
        try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutput, Charset.forName(ENCODING))))){
            List<File> list = Arrays.asList(new File(System.getProperty("user.dir") + System.getProperty("file.separator")));
            check(list, bw);
            String summary = "Summary:" + this.summary + ", directories:"
                    + dirSummary + ", files:" + (this.summary - dirSummary) + NEXT;
            bw.write(TextStyle.colorText(summary, TextStyle.Color.CYAN));
        }
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
    public String toString() {
        return "_tree#id" + this.id;
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
        try {
            invoke();
        } catch (JCLException e) {
            return false;
        }
        return true;
    }

}
