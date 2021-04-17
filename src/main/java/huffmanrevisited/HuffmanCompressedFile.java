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
 * A structuring of all the data needed to store compressed text in
 * HuffmanRevisited's proprietary file format, plus methods to read and
 * write from/to those files.... (I'm not happy
 * with how clearly I've named this class! Kind of need to find an example.)
 *
 * @author Kevin Higgins
 */

public class HuffmanCompressedFile {
    /**
     * a sequence of bits stored end-padded in a byte array,
     * representing Huffman tree nodes in pre-order, by the following scheme
     * 0 is a parent node, 1 is a leaf, and the 16 bits following the indicator
     * for a leaf are the char at that leaf.
     */
    byte[] compactedTree;
    
    /**
     * how many chars are encoded in the compressed data, needed when
     * decoding to distinguish end-padding zeroes from data
     */
    int origCharsAmount;
    /**
     * the compressed (encoded) data
     */
    byte[] encodedData;

    /*private - public for testing */ public HuffmanCompressedFile() {
	
    }

    /**
     * Creates an exact representation of a .huf file.
     *
     * @param compactedTree     a byte array, end-padded, representing a Huffman
     * tree according to the system described above
     * @param origCharsAmount   the count of chars that have been encoded,
     * needed when decoding to distinguish end-padding from meaningful data
     * @param encodedData       a byte array containing the Huffman encoded
     * text, end-padded
     */
    public HuffmanCompressedFile (byte[] compactedTree, int origCharsAmount, byte[] encodedData) {	
	this.encodedData = encodedData;
        this.origCharsAmount = origCharsAmount;
	this.compactedTree = compactedTree;
    }

    /**
     * Writes this Huffman compressed file to the given pathname.
     *
     * @param f the pathname to write to. I like the idea of the filenames
     * having a .huf extension but this is not enforced here.
     * @throws IOException
     */
    public void writeTo(File f) throws IOException {
	FileOutputStream fos = new FileOutputStream(f);
	fos.write(BinaryConversions.intToByteArray(compactedTree.length)); // length in bytes of compacted tree
	System.out.println("Writing length of compacted tree: " + (compactedTree.length));
	fos.write(compactedTree);
	fos.write(BinaryConversions.intToByteArray(origCharsAmount)); // length in chars of original uncompressed text
	System.out.println("Writing length of original text " + origCharsAmount);
	fos.write(encodedData);

    }

    /**
     * Reads the three components of a Huffman compressed file from the
     * given pathname and creates a representation of it
     *
     * @param f the pathname to load data from
     * @return  a Huffman compressed file object containing the data
     * @throws IOException
     */
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
	return new HuffmanCompressedFile(tree, charsAmount, encoded);
	
    }
    

}
