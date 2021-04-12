/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

/**
 *
 * @author Kevin Higgins
 */
public class BinaryConversions {
    public static byte[] intToByteArray(int i) {
	byte[] ba = new byte[4];
	ba[3] = (byte) (i % 256);
	ba[2] = (byte) (i / 256);
	ba[1] = (byte) (i / 65536);
	ba[0] = (byte) (i / 16777216);
	return ba;
    }
    public static int byteArrayToInt(byte[] ba) {
	if (ba.length != 4) {
	    System.exit(2);
	}
	int res = 0;
	// needs to be unsigned
	res += (ba[3] + 256) % 256;
	res += ((ba[2] + 256) % 256) * 256;
	res += ((ba[1] + 256) % 256) * 65536;
	res += ((ba[0] + 256) % 256) * 16777216;
	return res;
    }
    public static char bitsToChar(BitBuffer bits) {
	char c = 0;
	for (int i = 15; i >= 0; i--) {
	    if (bits.get()) {
		//System.out.print("1");
		c += Math.pow(2, i);
	    }
	    else {
		//System.out.print("0");
	    }
	}
	//System.out.println("Returning char with code " + (int) c);
	return c;
    }
    public static byte[] binCsqToByteArray(CharSequence csq) {
	int bytesAmount = csq.length() / 8;
	// incomplete byte, if present, needs to be allocated too
	int paddingLength = 8 - (csq.length() % 8);
	if (paddingLength != 8) { // if it is equal to 8, don't do anything, we were at the byte boundary
	    
	    bytesAmount++;
	    StringBuilder padding = new StringBuilder();

	    for (int i = 0; i < paddingLength; i++) {
		padding.append('0');
	    }
	    //System.out.println("BinaryConversions binCsqToByteArray... padding with " + padding + " bits " + paddingLength);
	    StringBuilder padded = new StringBuilder(csq);
	    csq = padded.append(padding).toString();
	} 
	
	byte[] ba = new byte[bytesAmount];
	int bytesWritten = 0;
	int bitsWritten = 0;
	int bitsToWriteIncludingPadding = bytesAmount * 8;
	while (bitsWritten < bitsToWriteIncludingPadding) {
	    
	    byte madeIntoByte = binCsqToByte(csq.subSequence(bitsWritten, bitsWritten + 8));
	    bitsWritten = bitsWritten + 8;
	    ba[bytesWritten++] = madeIntoByte;
	}

	
	return ba;
    }
 
    private static byte binCsqToByte(CharSequence s) {
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
    public static CharSequence charToBinCsq(char c) {
	return byteArrayToBinCsq(new byte[]{(byte) (c - (c % 255)), (byte) (c % 255)});
    }
    // used by tests so far
    // makes a sequence of the chars '0' and '1'
    public static CharSequence byteArrayToBinCsq(byte[] ba) {
	BitBuffer bits = new BitBuffer(ba);
	StringBuilder sb = new StringBuilder();
	for (int j = 0; j < ba.length * 8; j++) {
	    if (bits.get()) {
		sb.append('1');
	    }
	    else {
		sb.append('0');
	    }
	}
	return sb;
    }
}
