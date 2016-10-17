/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
@XmlType(propOrder = {"changeDirectory","dir","directoryTree"})
public class Settings {
    private String changeDirectory;
    private String dir;
    private String directoryTree;

    public String getChangeDirectory() {
        return changeDirectory;
    }
    @XmlElement
    public void setChangeDirectory(String changeDirectory) {
        this.changeDirectory = changeDirectory;
    }

    public String getDir() {
        return dir;
    }
    @XmlElement
    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDirectoryTree() {
        return directoryTree;
    }
    @XmlElement
    public void setDirectoryTree(String directoryTree) {
        this.directoryTree = directoryTree;
    }
    public static void setDefaultFile(File file) throws JAXBException{
        Settings s = new Settings();
        s.setChangeDirectory("cd");
        s.setDir("dir");
        s.setDirectoryTree("tree");
        JAXBParser parser = new JAXBParser();
        parser.saveObject(s, file);
    }
    
    
    
    
}
