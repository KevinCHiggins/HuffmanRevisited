/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import java.io.File;
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
public class HuffmanCompressedFileTest {
    
    public HuffmanCompressedFileTest() {
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
     * Test of buildFrom method, of class HuffmanCompressedFile.
     */
    @Test
    public void testBuildFrom() {
	System.out.println("buildFrom");
	byte[] compactedTree = null;
	byte[] encodedData = null;
	int origCharsAmount = 0;
	HuffmanCompressedFile expResult = null;
	HuffmanCompressedFile result = new HuffmanCompressedFile(compactedTree, origCharsAmount, encodedData);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of writeTo method, of class HuffmanCompressedFile.
     */
    @Test
    public void testWriteTo() throws Exception {
	System.out.println("writeTo");
	File f = new File("C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\output.huf");
	HuffmanCompressedFile instance = new HuffmanCompressedFile();
	instance.compactedTree = TestData.ONE_CHAR_COMPACTED_TREE;
	instance.encodedData = TestData.UP_TO_EIGHT_INSTANCES_OF_SINGLE_CHAR_ENCODED_DATA;
	instance.origCharsAmount = 3;
	instance.writeTo(f);
	// TODO review the generated test code and remove the default call to fail.
	//fail("The test case is a prototype.");
    }

    /**
     * Test of loadFrom method, of class HuffmanCompressedFile.
     */
    @Test
    public void testLoadFrom() throws Exception {
	System.out.println("loadFrom");
	File f = null;
	HuffmanCompressedFile expResult = null;
	HuffmanCompressedFile result = HuffmanCompressedFile.loadFrom(f);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
    
}
