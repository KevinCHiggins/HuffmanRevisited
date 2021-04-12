/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import huffmanrevisited.HuffmanTreeNode;
import huffmanrevisited.HuffmanEncoder;
import java.util.HashMap;
import java.util.Set;
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
public class HuffmanEncoderTest {
    
    public HuffmanEncoderTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    

    /**
     * Test of calcFreqs method, of class HuffmanEncoder.
     */
    @org.junit.jupiter.api.Test
    public void testCalcFreqsWithMinimalSequence() {
	System.out.println("calcFreqs");
	CharSequence csq = "ab";
	HuffmanEncoder instance = new HuffmanEncoder();
	HashMap<Character, Integer> expResult = new HashMap<>();
	expResult.put('a', 1);
	expResult.put('b', 1);
	HashMap<Character, Integer> result = instance.calcFreqs(csq);
	Set keys = result.keySet();
	for (Character k : result.keySet()) {
	    assertEquals(result.get(k), expResult.get(k));
	}
	assertEquals(result.size(), expResult.size());	
    }
    @org.junit.jupiter.api.Test
    public void testCalcFreqsWithEmptySequence() {
	System.out.println("calcFreqs");
	CharSequence csq = "";
	HuffmanEncoder instance = new HuffmanEncoder();
	HashMap<Character, Integer> result = instance.calcFreqs(csq);
	Set keys = result.keySet();

	assertEquals(result.size(), 0);	
    }
    @org.junit.jupiter.api.Test
    public void testCalcFreqsWithAlphabet() {
	System.out.println("calcFreqs");
	CharSequence csq = "abcdefghijklmnopqrstuvwxyz";
	HuffmanEncoder instance = new HuffmanEncoder();
	HashMap<Character, Integer> expResult = new HashMap<>();
	/* A style question... how "algorithmic" should the generation of
	the test case be? Because if it's as complex as what's being tested
	then you're relying on just as much untested logic, but in the test case
	for (char c : csq.toString().toCharArray()) {
	    expResult.put(c, 1);
	}
	*/
	expResult.put('a', 1);
	expResult.put('b', 1);
	expResult.put('c', 1);
	expResult.put('d', 1);
	expResult.put('e', 1);
	expResult.put('f', 1);
	expResult.put('g', 1);
	expResult.put('h', 1);
	expResult.put('i', 1);
	expResult.put('j', 1);
	expResult.put('k', 1);
	expResult.put('l', 1);
	expResult.put('m', 1);
	expResult.put('n', 1);
	expResult.put('o', 1);
	expResult.put('p', 1);
	expResult.put('q', 1);
	expResult.put('r', 1);
	expResult.put('s', 1);
	expResult.put('t', 1);
	expResult.put('u', 1);
	expResult.put('v', 1);
	expResult.put('w', 1);
	expResult.put('x', 1);
	expResult.put('y', 1);
	expResult.put('z', 1);
	HashMap<Character, Integer> result = instance.calcFreqs(csq);
	Set keys = result.keySet();
	for (Character k : result.keySet()) {
	    assertEquals(result.get(k), expResult.get(k));
	}
	assertEquals(result.size(), expResult.size());	
    }    

    /**
     * Test of encode method, of class HuffmanEncoder.
     */
    @org.junit.jupiter.api.Test
    public void testEncodeWithWikipediaExample() {
	System.out.println("encode");
	// BTW the Wikipedia example actually uses underscores
	// which, I presume due to the vagaries of the sorting method
	// result in a different permutation in the codes. At 
	// least this test proves that the encoded data is the same, optimal length!
	// but perhaps I should replace it with a test for length 
	CharSequence csq = "A DEAD DAD CEDED A BAD BABE A BEADED ABACA BED";
	HuffmanEncoder instance = new HuffmanEncoder();
	byte[] expResult = new byte[]{(byte) 0x87,
	    (byte) 0x48, 
	    (byte) 0xC9,
	    (byte) 0xD9,
	    (byte) 0xC9,
	    (byte) 0x1F,
	    (byte) 0x27,
	    (byte) 0xDF,
	    (byte) 0x88,
	    (byte) 0xFD,
	    (byte) 0x39,
	    (byte) 0x2F,
	    (byte) 0xBA,
	    (byte) 0x3F,
	    (byte) 0x20};
	byte[] result = instance.encode(csq);
	assertArrayEquals(expResult, result);
    }
    /**
     * Test of encode method, of class HuffmanEncoder.
     */
    @Test
    public void testEncodeWithSingleDistinctCharInInputEightTimes() {
	System.out.println("encode");
	// BTW the Wikipedia example actually uses underscores
	// which, I presume due to the vagaries of the sorting method
	// result in a different permutation in the codes. At 
	// least this test proves that the encoded data is the same, optimal length!
	// but perhaps I should replace it with a test for length 
	CharSequence csq = "aaaaaaaa";
	HuffmanEncoder instance = new HuffmanEncoder();
	// because we handled the special case of one distinct character
	// in HuffmanEncoder by making two nodes with the same character
	// the left node with code '0' gets overwritten in the final Map
	// by the right node with code '1' (for the same char) because Maps
	// can't contain duplicate keys, so we expect to get eight ones
	// in the final data which as a signed byte is -1.
	byte[] expResult = new byte[]{(byte) -1};
	byte[] result = instance.encode(csq);
	assertArrayEquals(expResult, result);
    }
    @Test
    public void testEncodeWithSingleDistinctCharInInputOnce() {
	System.out.println("encode");
	CharSequence csq = "a";
	HuffmanEncoder instance = new HuffmanEncoder();
	// because we handled the special case of one distinct character
	// in HuffmanEncoder by making two nodes with the same character
	// the left node with code '0' gets overwritten in the final Map
	// by the right node with code '1' (for the same char) because Maps
	// can't contain duplicate keys, so we expect to get "10000000"
	// in the final data after end-padding with zeros which as a signed byte is -128.
	byte[] expResult = new byte[]{(byte) -128};
	byte[] result = instance.encode(csq);
	assertArrayEquals(expResult, result);
    }
    /**
     * Test of encode method, of class HuffmanEncoder.
     */
    @org.junit.jupiter.api.Test
    public void testEncodeArrayLengthWithExactWikipediaExample() {
	System.out.println("encode");
	// BTW the Wikipedia example actually uses underscores
	// which, I presume due to the vagaries of the sorting method
	// result in a different permutation in the codes. At 
	// least this test proves that the encoded data is the same, optimal length!
	// but perhaps I should replace it with a test for length 
	CharSequence csq = "A_DEAD_DAD_CEDED_A_BAD_BABE_A_BEADED_ABACA_BED";
	HuffmanEncoder instance = new HuffmanEncoder();
	byte[] expResult = new byte[]{(byte) 0x87,
	    (byte) 0x48, 
	    (byte) 0xC9,
	    (byte) 0xD9,
	    (byte) 0xC9,
	    (byte) 0x1F,
	    (byte) 0x27,
	    (byte) 0xDF,
	    (byte) 0x88,
	    (byte) 0xFD,
	    (byte) 0x39,
	    (byte) 0x2F,
	    (byte) 0xBA,
	    (byte) 0x3F,
	    (byte) 0x20};
	byte[] result = instance.encode(csq);
	assertEquals(expResult.length, result.length);

    }
    /**
     * Test of getTree method, of class HuffmanEncoder.
     */
    @org.junit.jupiter.api.Test
    public void testGetTree() {
	System.out.println("getTree");
	HuffmanEncoder instance = new HuffmanEncoder();
	instance.encode("ab");
	HuffmanTreeNode expResultRoot = new HuffmanTreeNode(new HuffmanTreeNode('a', 1), new HuffmanTreeNode('b', 1));
	HuffmanTreeNode resultRoot = instance.getTree().getRoot();
	HuffmanTreeNode left = resultRoot.left;
	HuffmanTreeNode right = resultRoot.right;
	assertEquals(left.c, left.c);
	assertEquals(right.c, right.c);
	assertEquals(left.freq, left.freq);
	assertNull(left.left);
	assertNull(left.right);
	assertNull(right.left);
	assertNull(right.right);

    }
    
}
