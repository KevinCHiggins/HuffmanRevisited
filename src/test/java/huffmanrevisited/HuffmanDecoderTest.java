/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import java.nio.CharBuffer;
import java.util.Map;
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
public class HuffmanDecoderTest {
    
    public HuffmanDecoderTest() {
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
     * Test of decodeHuffmanTree method, of class HuffmanDecoder.
     */
    @Test
    public void testErectOneCharHuffmanTree() {
	System.out.println("decodeHuffmanTree");
	// according to my calculations, should encode a leaf node
	// with '!' character followed by 7 bits of zero padding
	byte[] ba = TestData.ONE_CHAR_COMPACTED_TREE;
	HuffmanDecoder instance = new HuffmanDecoder();
	char expResultChar = '!';
	HuffmanTree result = instance.erectHuffmanTree(ba);
	System.out.println(result.getRoot().c);
	assertEquals(expResultChar, (char) result.getRoot().c);
	// TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testErectGeneratedHuffmanTree() {
	System.out.println("erectHuffmanTree");
	HuffmanEncoder enc = new HuffmanEncoder();

	
        enc.encode("Hello");
        byte[] compressed = enc.getEncoded();
	HuffmanTree generated = enc.getTree();
	byte[] generatedCompacted = generated.compact();
	System.out.println("Compacted tree: " + BinaryConversions.byteArrayToBinCsq(generatedCompacted) + ". " + generatedCompacted.length + " bytes.");
	HuffmanCompressedFile hcf = new HuffmanCompressedFile(generatedCompacted, 5, compressed);
	HuffmanDecoder instance = new HuffmanDecoder();

	HuffmanTree result = instance.erectHuffmanTree(hcf.compactedTree);
	Map<Character, String> generatedMap = generated.getCodingsAsMap();
	Map<Character, String> resultMap = result.getCodingsAsMap();

	assertEquals(generatedMap.size(), resultMap.size());
	for (char c : generatedMap.keySet()) {
	    assert(resultMap.containsKey(c));
	}
    }

    /**
     * Test of decodeDataUsingTree method, of class HuffmanDecoder.
     */
    @Test
    public void testDecodeDataUsingTree() {
	System.out.println("decodeDataUsingTree");
	int charsToDecodeCount = 9;
	// 00011100-00000000, see below
        byte[] ba = new byte[] {(byte)28, (byte) 0};
        // Hand-make a tree:
        //          root
        //        /     \
        //    ('s', 6)  ('l', 3)
        HuffmanTreeNode left = new HuffmanTreeNode('s', 6);
        HuffmanTreeNode right = new HuffmanTreeNode('l', 3);
	HuffmanTreeNode root = new HuffmanTreeNode(left, right);
        HuffmanTree tree = new HuffmanTree();
        tree.setRoot(root);
	HuffmanDecoder instance = new HuffmanDecoder();
	CharSequence expResult = "ssslllsss"; // should encode as 000111000 and padding 0000000
	CharSequence result = instance.decodeDataUsingTree(charsToDecodeCount, ba, tree);
	assertEquals(CharBuffer.wrap(expResult), CharBuffer.wrap(result));
	
    }
    
}
