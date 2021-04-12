/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Kevin Higgins
 */
public class HuffmanEncoder {
    private HuffmanTree t;
    /*private public for testing */ public HashMap<Character, Integer> calcFreqs(CharSequence csq) {
	HashMap<Character, Integer> freqs = new HashMap<>();
	// first generate freqs
	for (int i = 0; i < csq.length(); i++) {
	    Character c = csq.charAt(i);
	    if (freqs.containsKey(c)) {
		int amount = freqs.get(c) + 1;
		freqs.put(c, amount);
	    }
	    else {
		freqs.put(c, 1);
	    }
	}
	return freqs;
    }
    public byte[] encode(CharSequence csq) {
	HashMap<Character, Integer> freqs = calcFreqs(csq);

	t = new HuffmanTree(freqs);
	/*
	System.out.println("Amount of chars (summed frequency at root): " + subtrees.get(0).freq);
	*/
	//printEncodings(getEncodingsFromTree(subtrees.get(0)));
	HashMap<Character, String> m = (t.getCodingsAsMap());
	byte[] enc = new byte[1048576]; // 1MB
	//System.out.println("Here comes");
	// encode all input data
	
	// I'm using a StringBuilder because there's no obvious limit
	// on how long the code for one char is and I have to append
	// these code sequences onto the remnants of the previous ones.
	StringBuilder holding = new StringBuilder("");
	int bytesWritten = 0;
	for (int i = 0; i < csq.length(); i++) {
	    // get this character's variable-length code from map m using the char as key
	    // append current character's code 
	    // after whatever unused bits (up to 7) are left
	    // over in holding
	    holding.append(m.get(csq.charAt(i))); 	    
	}
	return BinaryConversions.binCsqToByteArray(holding);
    }

    public HuffmanTree getTree() {
	return t;
    }
    // this method should only be given a CharSequence (or String) of 8 1s or 0s. If 
    // given a longer one, it will only work from the first 8 chars.
    // Chars other than 1 will be treated as 0s.
    private byte binCsqToByte(CharSequence s) {
	byte b = 0;
	if (s.charAt(0) == '1') b+= 128;
	if (s.charAt(1) == '1') b+= 64;
	if (s.charAt(2) == '1') b+= 32;
	if (s.charAt(3) == '1') b+= 16;
	if (s.charAt(4) == '1') b+= 8;
	if (s.charAt(5) == '1') b+= 4;
	if (s.charAt(6) == '1') b+= 2;
	if (s.charAt(7) == '1') b+= 1;
	return b;
    }
}
