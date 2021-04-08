/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import static huffmanrevisited.HuffmanRevisitedTest.createInputData;
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
public class UnicodeCodePointsTest {
    
    public UnicodeCodePointsTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testUnicodeAlt() {
	String data = new String();
	char[] ca = new char[10]; //[1048576];

	for (int i = 0; i < ca.length; i++) {
	    ca[i] = (char) (i % 128 + 128);
	}
	  
	data = new String(ca);
	createInputData(data);
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.ALT_FILE);
	HuffmanRevisited.decodeFileTo(TestData.ALT_FILE, TestData.DEC_FILE);
	HuffmanRevisitedTest.FullCycleStatus status = new HuffmanRevisitedTest.FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
//	assertTrue(status.compressedSmallerThanInput());
 	
    }
    @Test
    public void teostUnicode() {
	String data = new String();
	char[] ca = new char[10]; //[1048576];

	for (int i = 0; i < ca.length; i++) {
	    ca[i] = (char) (i % 128 + 80);
	}
	  
	data = new String(ca);
	createInputData(data);
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	HuffmanRevisitedTest.FullCycleStatus status = new HuffmanRevisitedTest.FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
//	assertTrue(status.compressedSmallerThanInput());
 	
    }
}
