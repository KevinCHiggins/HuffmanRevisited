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
	HuffmanTree instance = new HuffmanTree();
        HuffmanTreeNode expResult = new HuffmanTreeNode('a', 20);
        instance.setRoot(expResult);
	
	HuffmanTreeNode result = instance.getRoot();
	assertEquals(expResult, result);

    }
    /**
     * Test of rebuildFromCodings method, of class HuffmanTree. NOT USED.
     
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
*/
    /**
     * Test of getCodingsAsMap method, of class HuffmanTree. Make a
     * trivially easily-visualised Huffman tree of three characters.
     */
    
    @Test
    public void testGetCodingsAsMap() {
	System.out.println("getCodingsAsMap");
        // let's manually make the tree that should result
        // from the (Character, Integer) pairs ('a', 3), ('b', 1), ('c', 2)
        //             root
        //            /    \
        //    ('a', 3)     rightParent
        //                 /    \
        //            ('c', 2)  ('b', 1)  <- would be sorted by frequency descending
        HuffmanTreeNode leftLeaf = new HuffmanTreeNode('a', 3);
        HuffmanTreeNode rightRightLeaf = new HuffmanTreeNode('b', 1);
        HuffmanTreeNode rightLeftLeaf = new HuffmanTreeNode('c', 2);
        HuffmanTreeNode rightParent = new HuffmanTreeNode(rightLeftLeaf, rightRightLeaf);
        HuffmanTreeNode r = new HuffmanTreeNode(leftLeaf, rightParent);
	Map<Character, String> expResult = new HashMap<>();
        expResult.put('a', "0");
        expResult.put('b', "11");
        expResult.put('c', "10");
        HuffmanTree instance = new HuffmanTree();
        instance.setRoot(r);
	Map<Character, String> result = instance.getCodingsAsMap();
	assertEquals(expResult, result);
    }
    @Test
    public void testCompact() {
	System.out.println("compact");
	HuffmanEncoder enc = new HuffmanEncoder();
        enc.encode("ABBB");
	byte[] compressed = enc.getEncoded();
	HuffmanTree instance = enc.getTree();
	System.out.println("Chars in map from tree: " + instance.getCodingsAsMap().size());
	byte[] compacted = instance.compact();
        // we expect this tree:
        //          root
        //        /     \
        //    ('B', 3)  ('A', 1)
        // which makes root  left child 'B'               right child 'A'
        //              0          1     0000000001000001  1           0000000001000001
        // which makes, in bytes, with zero-padding added
        //  01000000-00010000-01100000-00001000-00100000
        //  64        16        96        8        64
	assertArrayEquals(new byte[]{(byte) 64, (byte) 16, (byte) 96, (byte) 8, (byte) 64}, compacted);
        
	System.out.println("Compacted tree: " + BinaryConversions.byteArrayToBinCsq(compacted) + ". " + compacted.length + " bytes.");

	
    }
    
}
