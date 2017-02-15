/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.xml.bind.JAXBException;
import jlc.commands.*;
import jlc.commands.impl.*;
import jlc.exceptions.JCLException;
import jlc.parse.CLParser;
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
    public static List<String> settings = new ArrayList<>();
    public static String LOG_FILE_PATH;
    
    /**
     * Java Command Line allows user to run programs by typing their names.
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) {
        Settings options = null;
        try{
        File file = new File("settings.xml");
        if (!file.exists()) {
            Settings.setDefault(file);
            System.out.println("Running with default settings...");
        }
         options = (Settings) new JAXBParser().getObject(file, Settings.class); //settings from settings.xml file
        }catch(JAXBException e){
            options = Settings.getDefault();
            System.out.println("");
        }
        settings.add(options.getDir());
        Dir.NAME = options.getDir(); //Setting up the new name of command, if user modified it
        settings.add(options.getDirectoryTree());
        DirectoryTree.NAME = options.getDirectoryTree();
        settings.add(options.getChangeDirectory());
        settings.add(Jobs.NAME);
        settings.add(RemoteMode.NAME);
        ChangeDirectory.NAME = options.getChangeDirectory();
        File logs = new File(options.getLogFilePath());
        if (!logs.exists()) {
            if (!logs.mkdirs()) {
                System.out.println("broken logfile path. configure settings.xml");;
                System.exit(0);
            }
        }
        LOG_FILE_PATH = options.getLogFilePath();
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
                System.out.println(TextStyle.colorText("Bye.\nWe'll miss you.",TextStyle.Color.BRIGHT));
                System.exit(0);
            }
            try {
                List<Command> commandList = CLParser.analyze(settings, arr);
                Command.execute(commandList, daemon, LOG_FILE_PATH);
            } catch (JCLException e) {
                System.out.println(e.getMessage());
            }
            catch(InterruptedException ie){
                throw new RuntimeException();
            }
            catch(ExecutionException ee){
                throw new RuntimeException();
            }
            scan.close();
        }
    }


}
