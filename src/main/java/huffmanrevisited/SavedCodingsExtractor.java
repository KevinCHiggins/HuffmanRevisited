/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kevin Higgins
 */
public class SavedCodingsExtractor {
    // breaks up chunked codes and returns them as binary strings, but
    // doesn't match them to a Character - the caller must do that
    // using the order of elements in the arrays passed in and returned -
    // I work in arrays rather than Maps because this method needs the order
    // in which the various code lengths are placed in the raw data, and
    // Maps aren't intrinsically ordered
    private String[] ExtractFromBytes(int[] codeLengths, byte[] chunkedCodes) {
	String[] codes = new String[codeLengths.length];
	BitBuffer bits = new BitBuffer(chunkedCodes);
	for (int i = 0; i < codeLengths.length; i++) {
	    int length = codeLengths[i];
	    StringBuilder code = new StringBuilder();
	    for (int j = length; j > 0; j--) {
		if (bits.get()) {
		    code.append("1");
		}
		else {
		    code.append("0");
		}
	    }
	    codes[i] = code.toString();
	}
	return codes;
    }
    public Map<Character, String> buildCodingsMap(char[] chars, int[] codeLengths, byte[] chunkedCodes) {
	if (chars.length != codeLengths.length) {
	    System.exit(2); // TODO error handling
	}
	// fill an array with all the codes, in order
	String[] codes = ExtractFromBytes(codeLengths, chunkedCodes);
	Map<Character, String> codings = new HashMap<>();
	// relying on the order having been maintained, match
	// up the chars listed in the array chars with the 
	// extracted codes, saving the pair as an entry in the Map codings
	for (int i = 0; i < chars.length; i++) {
	    codings.put(chars[i], codes[i]);
	}
	return codings;
    }
    

}
