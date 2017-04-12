/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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
public class DirTest {
    
    public DirTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of dir method, of class Dir.
     */
    @Test
    public void testDir() throws Exception {
        System.out.println("Testing method dir");
        String rootDir = "/";
        Dir instance = new Dir();
        List<File> expResult = Arrays.asList(new File("/").listFiles());
        List<File> result = instance.dir(rootDir);
        assertEquals(expResult, result);
        System.out.println("End of testing ");
    }

    /**
     * Test of call method, of class Dir.
     */
    @Test
    public void testCall() throws Exception {
        System.out.println("Testing method call");
        Dir instance = new Dir();
        Boolean expResult = true;
        Boolean result = instance.call();
        assertEquals(expResult, result);
        System.out.println("End of testing ");
    }
    
}
