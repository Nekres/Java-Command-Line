/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.bind.JAXBException;
import jlc.commands.*;
import jlc.commands.impl.*;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;
import jlc.exceptions.NoSuchCommandException;
import jlc.parse.impl.JAXBParser;

/**
 *
 * @author desolation
 */
public class JLC {

    private static final Scanner SCAN = new Scanner(System.in);
    private static String currentDir = System.getProperty("user.dir");
    private static List<String> settings = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        File file = new File("settings.xml");
        if (!file.exists()) {
            Settings.setDefault(file);
            System.out.println("Running with default settings...");
        }
        Settings options = (Settings) new JAXBParser().getObject(file, Settings.class); //settings from "settings.xml" file
        settings.add(options.getDir());
        Dir.NAME = options.getDir();
        settings.add(options.getDirectoryTree());
        DirectoryTree.NAME = options.getDirectoryTree();
        settings.add(options.getChangeDirectory());
        ChangeDirectory.NAME = options.getChangeDirectory();
        File logs = new File(options.getLogFilePath());
        if (!logs.exists()){
            if(!logs.mkdirs()){
                System.out.println("broken logfile path. configure settings.xml");;
                System.exit(0);
            }
        }
        boolean running = true, daemon = false;
        System.out.println("Java command line.");
        while (running) {
            System.out.print(System.getProperty("user.dir") + ":");
            String command = SCAN.nextLine().trim();
            String arr[] = command.split(" ");
            daemon = arr[arr.length - 1].equals("&");
            if (daemon) {
                System.out.println("Running as a daemon.");
            }
            try {
                List<Command> commandList = analyze(settings, arr);
                Command.execute(commandList, daemon, options.getLogFilePath());
            } catch (JCLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<Command> analyze(List<String> settings, String[] input) throws NoSuchCommandException, BadCommandArgumentException {
        String or = "||";
        String and = "&&";
        List<CommandWrapper> commands = new ArrayList<>();
        List<String> splitters = new ArrayList<>(); // && or ||
        List<Command> result = new ArrayList<>();
        String c = input[0];
        int mark = 0;
        boolean next = false, found = true;
        String splitter = null;
        for (int i = 0; i < input.length; i++) {
            for (String ks : settings) {
                if (input[i].equals(ks)) {
                    c = ks;
                    mark = i; //индекс комманды
                    found = true;
                    next = false;
                    break;
                }
            }
            if ((input[i].equals(or) || input[i].equals(and)) && i != 0) {
                next = true;
                splitter = input[i];
                load(commands, c, input, mark + 1, i, splitter);
                found = false;
                splitter = null;
                System.out.println("block a"+ i);
                continue;
            }
            if (next) {
                System.out.println("block b"+i);
                c = input[i];
                mark = i;
                found = true;
            }
            if (c != null && i == input.length - 1 && found) {
                System.out.println("block c"+i);
                if (!input[input.length - 1].equals("&")) {
                    load(commands, c, input, mark + 1, input.length, splitter);
                    System.out.println("block c A"+i);
                } else {
                    load(commands, c, input, mark + 1, input.length - 1, splitter);
                    System.out.println("block c B"+i);
                }
                found = false;
                splitter = null;
            }
            next = false;
        }
        for (CommandWrapper h : commands) {
            Command cmd = CommandFactory.createCommand(h.command, h.arg);
            result.add(cmd);
        }
        return result;
    }

    private static final void load(List<CommandWrapper> list, String c, String[] input, int from, int to, String splitter) {
        CommandWrapper h = new CommandWrapper(c, Arrays.copyOfRange(input, from, to));
        if (splitter != null) {
            h.setNext(splitter);
        }
        list.add(h);
        c = null;
    }

}
