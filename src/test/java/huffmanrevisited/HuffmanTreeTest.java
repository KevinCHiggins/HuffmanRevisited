/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import huffmanrevisited.HuffmanTreeNode;
import huffmanrevisited.HuffmanTree;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Kevin
 */
public class HuffmanTreeTest {
    
    public HuffmanTreeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getRoot method, of class HuffmanTree.
     */
    @Test
    public void testGetRoot() {
	System.out.println("getRoot");
	HuffmanTree instance = null;
	HuffmanTreeNode expResult = null;
	HuffmanTreeNode result = instance.getRoot();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
    /**
     * Test of rebuildFromCodings method, of class HuffmanTree.
     */
    public void testRebuildFromCodings() {
	System.out.println("rebuildFromCodings");
	Map<Character, String> codings = new HashMap<>();
	codings.put('a', "0");
	codings.put('b', "1");
	HuffmanTree instance = new HuffmanTree();
	
	HuffmanTree resultTree = instance.rebuildFromCodings(codings);
	Map<Character, String> resultCodings = resultTree.getCodingsAsMap();
	assertEquals(resultCodings.get('a'), "0");
	assertEquals(resultCodings.get('b'), "1");	
    }

    /**
     * Test of getCodingsAsMap method, of class HuffmanTree.
     */
    @Test
    public void testGetCodingsAsMap() {
	System.out.println("getCodingsAsMap");
	HuffmanTree instance = null;
	HashMap<Character, String> expResult = null;
	HashMap<Character, String> result = instance.getCodingsAsMap();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
    
}
