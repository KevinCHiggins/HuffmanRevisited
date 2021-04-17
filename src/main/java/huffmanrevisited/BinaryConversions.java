/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

/**
 * A utility class for conversions involving binary data represented as
 * sequences of '1' or '0' chars, which I call "binary strings", or as int or
 * char litte-endian numbers
 * 
 * @author Kevin Higgins
 */
public class BinaryConversions {

    /**
     * Converts an int into a little-endian byte array of length 4.
     *
     * @param i the int to be converted
     * @return  a byte array of length 4 filled with a little-endian byte 
     * representation of i
     */
    public static byte[] intToByteArray(int i) {
	byte[] ba = new byte[4];
	ba[3] = (byte) (i % 256);
	ba[2] = (byte) (i / 256);
	ba[1] = (byte) (i / 65536);
	ba[0] = (byte) (i / 16777216);
	return ba;
    }

    /**
     * Converts a byte array into an int, treating it as a little-endian
     * representation.
     * 
     * @param ba    the byte array to be converted. Must be of length 4.
     * @return      the int result of the conversion
     */
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

    /**
     * Converts a bit buffer into a single char, by reading 16 bits
     * from it as a little-endian representation of an unsigned number.
     * <p>
     * It does not currently check whether the bit buffer has 16 bits to read
     * and this is not a feature currently in BitBuffer!
     *
     * @param bits  the bit buffer to convert
     * @return      the char result of the conversion
     */
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

    /**
     * Converts a character sequence, which must only contain
     * combinations of the characters '1' or '0', into a byte array
     * of the data represented by the original binary string.
     * <p>
     * Binary strings with a length not evenly divisible into bytes
     * are permitted. They will be padded with zeroes to make up a
     * complete byte at the end.
     * <p>
     * Empty binary strings are permitted. They will result in an empty
     * byte array.
     *
     * @param csq   the character sequence holding a binary
     * string to be converted
     * @return      the byte array containing the data in the binary string
     */
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

    /**
     * Converts a single char into a binary string: a character
     * sequence that can only contain the characters '1' or '0'.
     * It treats the char as an unsigned little-endian number.
     *
     * @param c
     * @return
     */
    public static CharSequence charToBinCsq(char c) {
	return byteArrayToBinCsq(new byte[]{(byte) (c - (c % 255)), (byte) (c % 255)});
    }
    
    /**
     * Converts a byte array into a binary string character sequence
     * containing n*8 '1' or '0' characters where n is the length
     * of the byte array.
     *
     * @param ba    the byte array to be converted
     * @return      the character sequence created by the conversion
     */
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
