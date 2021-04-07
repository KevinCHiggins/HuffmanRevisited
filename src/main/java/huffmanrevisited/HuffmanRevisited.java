/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 *
 * @author Kevin
 */
public class HuffmanRevisited {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	try {
	    File inp = new File("C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\hungry.txt");
	    InputStreamReader isr = new InputStreamReader(new FileInputStream(inp), "UTF-8");
	    long fileSize = inp.length();
	    System.out.println("Input file size: " + fileSize);
	    int buffSize = 0;
	    if (fileSize > Integer.MAX_VALUE) {
		throw new IOException("File must be smaller than 2GB.");
	    }
	    else {
		buffSize = (int) fileSize;
	    }
	    char[] cbuf = new char[buffSize];
	    isr.read(cbuf, 0, buffSize);
	    CharSequence csq = CharBuffer.wrap(cbuf);

	    HuffmanEncoder enc = new HuffmanEncoder();

	    byte[] compressed = enc.encode(csq);
	    HuffmanTree t = enc.getTree();
	    HuffmanCompressedFile hcf = HuffmanCompressedFile.buildFrom(t.compact(), compressed, buffSize);
	    File outp = new File("C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\output.huf");
	    hcf.writeTo(outp);
	    HuffmanCompressedFile loaded = HuffmanCompressedFile.loadFrom(outp);
	    HuffmanDecoder dec = new HuffmanDecoder();
	    System.out.println("Loaded compacted tree " + BinaryConversions.byteArrayToBinCsq(loaded.compactedTree));
	    System.out.println("Result:" + dec.decodeDataUsingCompactedTree(loaded.origCharsAmount, loaded.encodedData, loaded.compactedTree));
	    System.out.println("Compression ratio " + (float)fileSize / (loaded.encodedData.length + loaded.compactedTree.length + 8));
	}
	catch (IOException e) {
	    System.out.println("No encoding/decoding performed due to problem reading source file. " + e.getMessage());
	} 
}
    
}
