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
import java.nio.ByteBuffer;
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
	    File file = new File("C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\huffmanrevisited\\twobytes.txt");
	    InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
	    long fileSize = file.length();
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
	    HuffmanDecoder dec = new HuffmanDecoder();


	    System.out.println(dec.decodeDataUsingTree(csq.length(), compressed, t.getRoot()));
   	}
	catch (IOException e) {
	    System.out.println("No encoding/decoding performed due to problem reading source file.");
	} 
}
    
}
