/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;

/**
 *
 * @author desolation
 */
public class DirectoryTreeTest {
    String separator = System.getProperty("file.separator");
    File root = new File("rootDir");
    File insideDir = new File(root.getName() + System.getProperty("file.separator") + "insideDir");
    File someFile = new File(root.getName() + separator + insideDir.getName() + separator + "somefile");
    
    public DirectoryTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() throws IOException {
        root.mkdir();
        insideDir.mkdir();
        someFile.createNewFile();
        
    }
    
    @After
    public void tearDown() {
        someFile.delete();
        insideDir.delete();
        root.delete();
    }

    /**
     * Test of tree method, of class DirectoryTree.
     */
    @Test
    public void testTree() throws Exception {
        System.out.println("TESTING tree()");
        List<File> list = Arrays.asList(root);
        DirectoryTree instance = new DirectoryTree();
        List<File> expResult = new ArrayList<>();
        expResult.add(root);
       // expResult.add(insideDir);
      //  expResult.add(someFile);
        List<File> result = instance.tree(list);
        assertThat(result, containsInAnyOrder(root,insideDir,someFile));
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getName method, of class DirectoryTree.
     */
    @Ignore("just returns name")
    public void testGetName() {
        System.out.println("getName");
        DirectoryTree instance = new DirectoryTree();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of call method, of class DirectoryTree.
     */
    @Ignore
    public void testCall() throws Exception {
        System.out.println("call");
        DirectoryTree instance = new DirectoryTree();
        Boolean expResult = null;
        Boolean result = instance.call();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
