/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Takes a ByteBuffer and then feeds it back in single bits
 * to any client calling its get() method
 * 
 * @author Kevin Higgins
 */
public class BitBuffer {
    private ByteBuffer b;
    private byte currByte;
    private int inc;

    /**
     * Stores the byte array passed to it and puts the first byte into
     * currByte where it is available one bit at a time to callers of
     * this object's get method.
     * @param ba    the byte array of data to be stored and returned bit-by-bit
     */
    public BitBuffer(byte[] ba) {
        byte[] copy = Arrays.copyOf(ba, ba.length);
	b = ByteBuffer.wrap(copy);
	currByte = b.get();
	inc = 7;
    }
    
    /**
     *  Returns the entirety of the original data that was passed to 
     * this object's constructor.
     * 
     * @return  a byte array identical to that passed to this object's 
     * constructor
     */
    public byte[] getByteArray() {
	return b.array();
    }
    
    // only used for testing so far
    private boolean hasBit() {
	return b.hasRemaining() || inc > 0;
    }

    /**
     * Returns the bit following
     * the bit it returned the previous time it was called, or the first
     * bit if it is being called for the first time since this bit buffer
     * was constructed, from the stored data.
     *
     * @return  the next bit from the stored data
     */
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
