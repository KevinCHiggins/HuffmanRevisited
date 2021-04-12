/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kevin Higgins
 */
/* file format:
continuous stream of bits representing the tree nodes in pre-order, where
0 is a parent node, 1 is a leaf, and the 16 bits following the indicator
for a leaf are the char at that leaf.
int length of compressed data in chars
continuous stream of encoded data, zero-padded at end
*/
public class HuffmanCompressedFile {
    byte[] compactedTree;
    byte[] encodedData;
    int origCharsAmount;
    /*private - public for testing */ public HuffmanCompressedFile() {
	
    }
    /*
    static private byte[] nodesToBits(List<HuffmanTreeNode> l) {
	StringBuilder binary = new StringBuilder();
	
	for (HuffmanTreeNode n : l) {
	    if (n.c != null) {
		System.out.println("Char " + n.c);
		binary.append("1");
		BitBuffer bits = new BitBuffer(new byte[]{(byte)(n.c / 256), (byte)(n.c % 256)});

		for (int i = 0; i < 16; i++) {
		    if (bits.get()) {
			binary.append("1");
		    }
		    else {
			binary.append("0");
		    }
		}
	    }
	    else {
		binary.append("0");
	    }
	}
	String s = binary.toString();
	int bytesAmount = s.length() / 8;
	if (s.length() % 8 != 0) bytesAmount++;
	byte[] ba = new byte[bytesAmount];
	

	int bytesWritten = 0;

	while (binary.length() >= 8) {
	    CharSequence willMakeAByte = binary.subSequence(0, 8);
	    binary = new StringBuilder(binary.subSequence(8, binary.length()));
	    // write one byte to the encoded data array
	    byte madeIntoByte = binCsqToByte(willMakeAByte);
	    ba[bytesWritten++] = madeIntoByte;
	}

	
	// now to zero-wrap any data left over to complete a final byte!! And add it to encoded data
	if (binary.length() > 0) {
	    while (binary.length() < 8) {
		binary.append('0');
	    }
	    
	    System.out.println("Incomplete byte has length " + binary.length());
	    System.out.println("Incomplete byte now padded to " + binary);
	    
	    ba[bytesWritten++] = binCsqToByte(binary); 
	}

	byte[] trimmed = Arrays.copyOf(ba, bytesWritten);
	return trimmed;
    }
    static private byte binCsqToByte(CharSequence s) {
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
*/
    // actually, I think this can just be a constructor but I'll change it later
    public static HuffmanCompressedFile buildFrom(byte[] compactedTree, byte[] encodedData, int origCharsAmount) {
	
	HuffmanCompressedFile building = new HuffmanCompressedFile();
	building.encodedData = encodedData;
	building.origCharsAmount = origCharsAmount;
	building.compactedTree = compactedTree;
	/*
	int charsAmount = codings.size();
	building.codes = new String[charsAmount];
	building.chars = new char[charsAmount];
	int i = 0; // need to declare separately because Map can't be indexed
	for (Character k : codings.keySet()) {
	    building.codes[i] = codings.get(k);
	    building.chars[i] = k;
	    i++;
	}
	*/
	return building;
    }
    // would a boolean return parameter indicating success be good practice?
    public void writeTo(File f) throws IOException {
	FileOutputStream fos = new FileOutputStream(f);
	fos.write(BinaryConversions.intToByteArray(compactedTree.length)); // length in bytes of compacted tree
	System.out.println("Writing length of compacted tree: " + (compactedTree.length));
	fos.write(compactedTree);
	fos.write(BinaryConversions.intToByteArray(origCharsAmount)); // length in chars of original uncompressed text
	System.out.println("Writing length of original text " + origCharsAmount);
	fos.write(encodedData);
	/*
	fos.write(codes.length);
	fos.write(encodedData.length);
	// interleave chars and the * length in bits * of their code
	for (int i = 0; i < codes.length; i++) {
	    fos.write(chars[i]);
	    fos.write((byte)codes[i].length()); // length of bits for this char's code
	}
	*/
	
    }
    public static HuffmanCompressedFile loadFrom(File f) throws IOException {
	FileInputStream fis = new FileInputStream(f);
	byte[] compactedTreeLengthData = new byte[4];
	fis.read(compactedTreeLengthData);
	System.out.println("Int is represented by bytes " + BinaryConversions.byteArrayToBinCsq(compactedTreeLengthData));
	int compactedTreeLength = BinaryConversions.byteArrayToInt(compactedTreeLengthData);
	System.out.println("Compacted tree is, apparently " + compactedTreeLength + " bytes long.");
	byte[] tree = new byte[compactedTreeLength];
	fis.read(tree);
	byte[] origLengthData = new byte[4];
	fis.read(origLengthData);
	int charsAmount = BinaryConversions.byteArrayToInt(origLengthData);
	byte[] encoded = fis.readAllBytes();
	return buildFrom(tree, encoded, charsAmount);
	
    }
    

}
