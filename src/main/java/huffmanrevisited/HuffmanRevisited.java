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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.nio.file.Files;

/**
 *
 * @author Kevin
 */
public class HuffmanRevisited {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	if (args.length != 3) {
	    System.out.println("Incorrect number of arguments supplied. Use"
		    + "\njava HuffmanRevisited -e <input pathname> <output pathname>"
		    + "\n - or - "
		    + "\njava HuffmanRevisited -d <input pathname> <output pathname>"
		    + "\nto encode or decode, respectively.");
	}
	else {
	    if (args[0] == "-e") {
		File inp = new File(args[1]);
		File outp = new File(args[2]);
		encodeFileTo(inp, outp);
	    }
	    else if (args[0] == "-d") {
		File inp = new File(args[1]);
		File outp = new File(args[2]);
		decodeFileTo(inp, outp);		
	    }
	}
    }
    public static void encodeFileTo(File inp, File outp) {
	try {
	    
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
	    
	    hcf.writeTo(outp);
	    long length = Files.size(outp.toPath());
	    System.out.println("Compression ratio " + (float)fileSize / length);
	}
	catch (IOException e) {
	    System.out.println("No encoding/decoding performed due to problem reading source file. " + e.getMessage());
	}	
    }
    public static void decodeFileTo(File inp, File outp) {
	try {

	    HuffmanCompressedFile loaded = HuffmanCompressedFile.loadFrom(inp);
	    HuffmanDecoder dec = new HuffmanDecoder();
	    //System.out.println("Loaded compacted tree " + BinaryConversions.byteArrayToBinCsq(loaded.compactedTree));
	    
	    String decoded;
	    if (loaded.origCharsAmount > 0) {
		decoded = dec.decodeDataUsingCompactedTree(loaded.origCharsAmount, loaded.encodedData, loaded.compactedTree).toString();
	    }
	    else {
		decoded = "";
	    }
	    System.out.println("Decoded: " + decoded);
	    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outp));
	    osw.write(decoded, 0, decoded.length());
	    System.out.println("Wrote decompressed file.");
	    osw.close();
	}
	catch (IOException ioe) {
	    
	}
    }
    
}
