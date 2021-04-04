/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.nio.ByteBuffer;

/**
 * Need to investigate if I can legit call this a "stream"
 * It takes a ByteBuffer and then feeds it back in single bits
 * to any client calling its nextBit() method
 * @author Kevin Higgins
 */
public class BytesToBitStream {
    private ByteBuffer b;
    private byte currByte;
    private int inc;
    public BytesToBitStream(byte[] ba) {
	b = ByteBuffer.wrap(ba);
	currByte = b.get();
	inc = 7;
    }

    // not currently used as I decrement a count of characters in 
    // HuffmanDecoder and stop calling this class's nextBit() when it runs out,
    // so never reach the end of the ButeBuffer b in here
    public boolean hasBit() {
	return b.hasRemaining() || inc > 0;
    }
    public boolean nextBit() {
	if (inc < 0) {
	    inc = 7;
	    currByte = b.get();
	}
	// mask off everything but the bit at the position of our
	// increment (and do the incrementation)
	byte bitmask = (byte) (Math.pow(2, inc));
	//System.out.println("Byte " + currByte + ", inc " + inc + ", bitmask " + bitmask);
	inc--;
	return (currByte & bitmask) != 0;
    }
}
