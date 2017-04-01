/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc;

import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author desolation
 */
public class SettingsTest {
    Settings instance = new Settings();
    public SettingsTest() {
        instance.setChangeDirectory("cd");
        instance.setDir("dir");
        instance.setDirectoryTree("tree");
        instance.setLogFilePath("/home/desolation");
        instance.setSupportedCommands(new ArrayList<>());
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ArrayList<String> list = new ArrayList<>();
        list.add("ls");
        list.add("ps");
        instance.setSupportedCommands(list);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLogFilePath method, of class Settings.
     */
    @Test
    public void testGetLogFilePath() {
        System.out.println("getLogFilePath");
        String expResult = "/home/desolation";
        String result = instance.getLogFilePath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     //   fail("The test case is a prototype.");
    }

    /**
     * Test of setLogFilePath method, of class Settings.
     */
    @Test
    public void testSetLogFilePath() {
        System.out.println("setLogFilePath");
        String logFilePath = "";
        instance.setLogFilePath(logFilePath);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of getChangeDirectory method, of class Settings.
     */
    @Test
    public void testGetChangeDirectory() {
        System.out.println("getChangeDirectory");
        String result = instance.getChangeDirectory();
        assertNotNull(result);
    }

    /**
     * Test of setChangeDirectory method, of class Settings.
     */
    @Test
    public void testSetChangeDirectory() {
        System.out.println("setChangeDirectory");
        String changeDirectory = "";
        instance.setChangeDirectory(changeDirectory);
    }

    /**
     * Test of getDir method, of class Settings.
     */
    @Test
    public void testGetDir() {
        System.out.println("getDir");
        String result = instance.getDir();
        assertNotNull(result);
    }

    /**
     * Test of setDir method, of class Settings.
     */
    @Test
    public void testSetDir() {
        System.out.println("setDir");
        String dir = "somedir";
        instance.setDir(dir);
    }

    /**
     * Test of getDirectoryTree method, of class Settings.
     */
    @Test
    public void testGetDirectoryTree() {
        System.out.println("getDirectoryTree");
        String result = instance.getDirectoryTree();
        assertNotNull(result);
    }

    /**
     * Test of setDirectoryTree method, of class Settings.
     */
    @Test
    public void testSetDirectoryTree() {
        System.out.println("setDirectoryTree");
        String directoryTree = "";
        instance.setDirectoryTree(directoryTree);
    }

    /**
     * Test of getSupportedCommands method, of class Settings.
     */
    @Test
    public void testGetSupportedCommands() {
        System.out.println("getSupportedCommands");
        ArrayList<String> result = instance.getSupportedCommands();
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setSupportedCommands method, of class Settings.
     */
    @Test
    public void testSetSupportedCommands() {
        System.out.println("setSupportedCommands");
        ArrayList<String> supportedCommands = null;
        instance.setSupportedCommands(supportedCommands);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setDefault method, of class Settings.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDefault() throws Exception {
        System.out.println("setDefault");
        File file = null;
        Settings.setDefault(file);
    }

    /**
     * Test of getDefault method, of class Settings.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        Settings result = Settings.getDefault();
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
