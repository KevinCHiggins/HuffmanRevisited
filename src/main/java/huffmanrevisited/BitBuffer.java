/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.nio.ByteBuffer;

/**
 * Need to investigate if I can legit call this a "stream"
 It takes a ByteBuffer and then feeds it back in single bits
 to any client calling its get() method
 * @author Kevin Higgins
 */
public class BitBuffer {
    private ByteBuffer b;
    private byte currByte;
    private int inc;
    public BitBuffer(byte[] ba) {
	b = ByteBuffer.wrap(ba);
	currByte = b.get();
	inc = 7;
    }
    public byte[] getByteArray() {
	return b.array();
    }
    // only used for testing so far
    public boolean hasBit() {
	return b.hasRemaining() || inc > 0;
    }
    public boolean get() {
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
