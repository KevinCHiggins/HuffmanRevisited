/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 *
 * @author Kevin Higgins
 */
public class HuffmanDecoder {
    public HuffmanTree erectHuffmanTree(byte[] compacted) {
	HuffmanTree t = new HuffmanTree();
	t.setRoot(erectSubtreeFromCompactedData(new BitBuffer(compacted)));
	return t;
    }
    private HuffmanTreeNode erectSubtreeFromCompactedData(BitBuffer bits) {
	HuffmanTreeNode n;
	if (bits.get() == false) { // if it's a parent node, not a leaf
	    HuffmanTreeNode parent = new HuffmanTreeNode();
	    parent.left = erectSubtreeFromCompactedData(bits);
	    parent.right = erectSubtreeFromCompactedData(bits);	 
	    n = parent;
	}
	else {
	    HuffmanTreeNode leaf = new HuffmanTreeNode(BinaryConversions.bitsToChar(bits), 0);
	    n = leaf;
	}
	return n;
    }
    public CharSequence decodeDataUsingCompactedTree(int origCharsAmount, byte[] ba, byte[] compacted) {
	return decodeDataUsingTree(origCharsAmount, ba, erectHuffmanTree(compacted).getRoot());
    }
    public CharSequence decodeDataUsingTree(int origCharsAmount, byte[] ba, HuffmanTreeNode root) {
	/*
	This loop builds up a StringBuilder depicting a trajectory down to
	a terminal node, bit by bit, checking each move to see if 
	it has actually reached a terminal node. The bits are taken from
	a BytesToBitsConverter loaded with the binary data from buf.
	
	*/
	//byte[] ba = new byte[]{12, 13};
	//ByteBuffer test = ByteBuffer.wrap(ba);
	System.out.println("Array length " + ba.length);
	// is there a way we can determine this... doesn't my file
	// format have a count of chars???
	
	System.out.println("Decoding " + origCharsAmount + " chars.");
	char[] dec = new char[origCharsAmount];
	int i = 0;
	BitBuffer bits = new BitBuffer(ba);
	HuffmanTreeNode curr = root;
	do {
	    
	    // if it's a 1 (true)
	    if (bits.get()) {
		curr = curr.right;
		System.out.print("1");
	    }
	    else {
		curr = curr.left;
		System.out.print("0");
	    }
	    
	    if (curr.c != null) {
		dec[i++] = curr.c;
		System.out.println(" makes " + curr.c);
		curr = root;
		origCharsAmount--;
		
	    }
	    
	} while (origCharsAmount > 0);
	
	return CharBuffer.wrap(dec);
    }

}
