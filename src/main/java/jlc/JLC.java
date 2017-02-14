/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.*;
import jlc.commands.*;
import jlc.commands.impl.*;
import jlc.exceptions.BadCommandArgumentException;
import jlc.exceptions.JCLException;
import jlc.exceptions.NoSuchCommandException;
import jlc.parse.impl.JAXBParser;
import jlc.view.TextStyle;
import org.apache.commons.io.input.CloseShieldInputStream;

/**
 *
 * @author Миша Шикоряк
 * @version 0.9
 */
public class JLC {
    private static final String OS_ENCODING = System.getProperty("os.name").contains("Windows") ? "866" : "UTF-8";
    private static final String OR = "||".intern(); 
    private static final String AND = "&&".intern();
    private static List<String> settings = new ArrayList<>();
    
    /**
     * Java Command Line allows user to run programs by typing their names.
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        RemoteMode rm = new RemoteMode(4567);
        //PipedInputStream ps = new PipedInputStream(rm.getPos());
        Thread t = new Thread(rm);
        //t.start();
        File file = new File("settings.xml");
        if (!file.exists()) {
            Settings.setDefault(file);
            System.out.println("Running with default settings...");
        }
        
        Settings options = (Settings) new JAXBParser().getObject(file, Settings.class); //settings from settings.xml file
        settings.add(options.getDir());
        Dir.NAME = options.getDir(); //Setting up the new name of command, if user modified it
        settings.add(options.getDirectoryTree());
        DirectoryTree.NAME = options.getDirectoryTree();
        settings.add(options.getChangeDirectory());
        settings.add(Jobs.NAME);
        ChangeDirectory.NAME = options.getChangeDirectory();
        File logs = new File(options.getLogFilePath());
        if (!logs.exists()) {
            if (!logs.mkdirs()) {
                System.out.println("broken logfile path. configure settings.xml");;
                System.exit(0);
            }
        }
        boolean daemon = false;
        System.out.println("Java command line.");
        while (true) {
            String currentDirectory = System.getProperty("user.dir").intern();
            CloseShieldInputStream close = new CloseShieldInputStream(System.in);
            Scanner scan = new Scanner(close,OS_ENCODING);
            System.out.print(TextStyle.colorText(currentDirectory + ":",TextStyle.Color.GREEN));
            String command = scan.nextLine().intern().trim();
            String arr[] = command.intern().split(" ");
            daemon = arr[arr.length - 1].equals("&");
            if(command.equals("quit")){
                System.out.println("Bye.\nWe'll miss you.");
                System.exit(0);
            }
            try {
                List<Command> commandList = analyze(settings, arr);
                Command.execute(commandList, daemon, options.getLogFilePath());
            } catch (JCLException e) {
                System.out.println(e.getMessage());
            }
            scan.close();
        }
    }
    //
    public static List<Command> analyze(List<String> settings, String[] input) throws NoSuchCommandException, BadCommandArgumentException {
        List<CommandWrapper> commands = new ArrayList<>();
        List<Command> result = new ArrayList<>();
        String c = input[0];
        int mark = 0;
        boolean next = false, found = true;
        String splitter = null;
        for (int i = 0; i < input.length; i++) {
            for (String ks : settings) {
                if (input[i] == ks.intern()) {  //if (input[i].equals(ks)) { look for a bug here
                    c = ks;
                    mark = i; //индекс комманды
                    found = true;
                    next = false;
                    break;
                }
            }
            if ((input[i].intern() == OR || input[i].intern() == AND) && i != 0) {
                next = true;
                splitter = input[i];
                load(commands, c, input, mark + 1, i, splitter);
                found = false;
                splitter = null;
                continue;
            }
            if (next) {
                c = input[i];
                mark = i;
                found = true;
            }
            if (c != null && i == input.length - 1 && found) {
                if (!input[input.length - 1].equals("&")) {
                    load(commands, c, input, mark + 1, input.length, splitter);
                } else {
                    load(commands, c, input, mark + 1, input.length - 1, splitter);
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
            System.out.println(h.getNext());
        }
        list.add(h);
        c = null;
    }

}
