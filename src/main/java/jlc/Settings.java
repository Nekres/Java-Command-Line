/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import jlc.parse.impl.JAXBParser;

/**
 *
 * @author desolation
 */
@XmlRootElement
@XmlType(propOrder = {"changeDirectory","dir","directoryTree","logFilePath","supportedCommands"})
public class Settings {
    private String changeDirectory;
    private String dir;
    private String directoryTree;
    private String logFilePath;
    private ArrayList<String> supportedCommands;
    /**
     * @return current filepath of logs
     */
    public String getLogFilePath() {
        return logFilePath;
    }
    /**
     * Sets the filepath where logs will be saved
     * @param logFilePath - filepath
     */
    @XmlElement
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    /**
     * @return current name of {@link jlc.commands.ChangeDirectory ChangeDirectory}
     */
    public String getChangeDirectory() {
        return changeDirectory;
    }
    /**
     * Sets the name of {@link jlc.commands.ChangeDirectory ChangeDirectory} class
     * @param changeDirectory - new name
     */
    @XmlElement
    public void setChangeDirectory(String changeDirectory) {
        this.changeDirectory = changeDirectory;
    }
    /**
     * 
     * @return current name of {@link jlc.commands.Dir Dir}
     */
    public String getDir() {
        return dir;
    }
    /**
     * Sets the name of {@link jlc.commands.Dir Dir} class
     * @param dir name
     */
    @XmlElement
    public void setDir(String dir) {
        this.dir = dir;
    }
    /**
     * @return current name of {@link jlc.commands.DirectoryTree DirectoryTree}
     */
    public String getDirectoryTree() {
        return directoryTree;
    }
    /**
     * 
     * @param directoryTree new name of "directory tree" command
     */
    @XmlElement
    public void setDirectoryTree(String directoryTree) {
        this.directoryTree = directoryTree;
    }
    /**
     * @return list of programs which user marked as supported
     */
    public ArrayList<String> getSupportedCommands() {
        return supportedCommands;
    }
    /**
     * Sets up new list of supported commands
     * @param supportedCommands - list of commands
     */
    @XmlElement
    public void setSupportedCommands(ArrayList<String> supportedCommands) {
        this.supportedCommands = supportedCommands;
    }
    /**
     * 
     * @param file file where to save default settings
     * @throws JAXBException 
     */
    public static void setDefault(File file) throws JAXBException{
        JAXBParser parser = new JAXBParser();
        parser.saveObject(getDefault(), file);
    }
    /**
     * @return default Settings object with simple commands "dir","pwd","cd","tree","info"
     */
    public static Settings getDefault(){
        Settings s = new Settings();
        s.setChangeDirectory("cd");
        s.setDir("dir");
        s.setDirectoryTree("tree");
        s.setLogFilePath(System.getProperty("user.dir"));
        ArrayList<String> list = new ArrayList<>();
        list.add("dir");
        list.add("pwd");
        list.add("cd");
        list.add("tree");
        list.add("info");
        s.setSupportedCommands(list);
        return s;
    }
    
    
    
    
}
