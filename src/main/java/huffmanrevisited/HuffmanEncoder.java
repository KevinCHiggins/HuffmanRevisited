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
	System.out.println("Here comes");
	// encode all input data
	
	// I'm using a StringBuilder because there's no obvious limit
	// on how long the code for one char is and I have to append
	// these code sequences onto the remnants of the previous ones.
	StringBuilder holding = new StringBuilder("");
	int bytesWritten = 0;
	for (int i = 0; i < csq.length(); i++) {
	    // get this character's variable-length code from map m using the char as key
	    System.out.println(m.get(csq.charAt(i)));
	    // append current character's code 
	    // after whatever unused bits (up to 7) are left
	    // over in holding
	    holding.append(m.get(csq.charAt(i))); 
	    // process any full bytes straight away
	    // while there are any full bytes in holding
	    while (holding.length() >= 8) {
		CharSequence willMakeAByte = holding.subSequence(0, 8);
		holding = new StringBuilder(holding.subSequence(8, holding.length()));
		// write one byte to the encoded data array
		byte madeIntoByte = binCsqToByte(willMakeAByte);
		enc[bytesWritten++] = madeIntoByte;
	    }

	}
	// zero-wrap incomplete, final byte!! And add it to encoded data
	if (holding.length() > 0) {
	    while (holding.length() < 8) {
		holding.append('0');
	    }
	    
	    System.out.println("Incomplete byte has length " + holding.length());
	    System.out.println("Incomplete byte now padded to " + holding);
	    
	    enc[bytesWritten++] = binCsqToByte(holding); 
	}

	byte[] trimmed = Arrays.copyOf(enc, bytesWritten);
	/*
	System.out.println("Written " + bytesWritten);
	System.out.println("Encoded first byte " + enc[0] + ", trimmed first " + trimmed[0]);
	for (byte b : trimmed) {
	    System.out.println("Testing, byte " + b);
	}
	*/

    
	return trimmed;

    }
    /* file format:
    int length of compressed data in chars
    int amount of distinct chars coded
    int amount of bytes of encoded data (this means it won't
    deal with large files for now... later maybe we'll work
    on saving intermediate work to disk)
    interleaved 16bit char and 8bit byte values - the byte
    is the length in bits of the code for that character!
    block of binary data zero-padded at the end to the next byte boundary
    representing all the character codes as a continuous stream
    so the lengths of the character codes must be used to find the offset
    and length **IN BITS** to extract a particular character code
    continuous stream of encoded data, zero-padded at end
    */
    public HuffmanTree getTree() {
	return t;
    }
    // this method should only be given a String of 8 1s or 0s. If 
    // given a longer String, it will only work from the first 8 chars.
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
    

    
    // a test method
    private void printEncodingsMap(HashMap<Character, String> m) {

	for (Character k : m.keySet()) {
	    System.out.println("Character " + k + " has encoding " + m.get(k));
	}
    }


}
