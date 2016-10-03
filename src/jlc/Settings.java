/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
    
    
    
    
}
