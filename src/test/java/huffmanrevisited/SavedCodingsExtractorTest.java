/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Kevin
 */
public class SavedCodingsExtractorTest {
    
    public SavedCodingsExtractorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of buildCodingsMap method, of class SavedCodingsExtractor.
     */
    @Test
    public void testBuildCodingsMapWithArbitraryData() {
	System.out.println("buildCodingsMap");
	char[] chars = new char[]{'a','b'};
	int[] codeLengths = new int[]{4, 4};
	byte[] chunkedCodes = new byte[]{0};
	SavedCodingsExtractor instance = new SavedCodingsExtractor();
	Map<Character, String> expResult = new HashMap<>();
	expResult.put('a', "0000");
	expResult.put('b', "0000");
	Map<Character, String> result = instance.buildCodingsMap(chars, codeLengths, chunkedCodes);
	for (char k : result.keySet()) {
	    assertEquals(expResult.get(k), result.get(k));
	}
	assertEquals(expResult.size(), result.size());
	

    }
    
}
