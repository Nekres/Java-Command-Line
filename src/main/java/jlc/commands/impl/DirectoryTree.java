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
    private int dirSummary = -1;

    public DirectoryTree() {
    }

    public final List<File> tree(List<File> list) throws BadCommandArgumentException, IOException {
        List<File> result = new ArrayList<>();
        for (File file : list) {
            if (!Files.isReadable(file.toPath())
                    || Files.isSymbolicLink(FileSystems.getDefault().getPath(file.getAbsolutePath()))) {
                continue;
            }
            result.add(file);
            if (file.isDirectory()) {
                if (file.listFiles().length != 0 && file.listFiles() != null) {
                    result.addAll(tree(Arrays.asList(file.listFiles())));
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean call() throws IOException {
        try {
            try(BufferedWriter bw = new BufferedWriter(new PrintWriter(new OutputStreamWriter(currentOutputStream, Charset.forName(ENCODING))))){
            List<File> root = Arrays.asList(new File(System.getProperty("user.dir") + System.getProperty("file.separator")));
            List<File> tree = tree(root);
            for(File f : tree){
                if(f.isDirectory())
                    dirSummary++;
                bw.write(f.getName());
                bw.newLine();
            }
            String summary = "Summary:" + tree.size() + ", directories:"
                    + dirSummary + ", files:" + (tree.size() - dirSummary) + NEXT;
            bw.write(TextStyle.colorText(summary, TextStyle.Color.CYAN));
        }
            ActiveCommandsManager.remove(this.getID());
        } catch (JCLException e) {
            return false;
        }
        return true;
    }

}
