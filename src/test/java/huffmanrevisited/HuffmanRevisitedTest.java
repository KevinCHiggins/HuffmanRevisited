/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanrevisited;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class HuffmanRevisitedTest {
    protected static class FullCycleStatus {
	FileReader inputReader;
	FileReader outputReader;
	public FullCycleStatus() {

	    try {
		inputReader = new FileReader(TestData.IN_PATH);
		outputReader = new FileReader(TestData.DEC_PATH);
	    }
	    catch (FileNotFoundException fnfe) {
		
	    }
	
	}
	public boolean inputEqualsDecompressed() {
	    boolean res = false;
	    try {
		// covering the case of empty input and output - technically equal!
		if (!inputReader.ready()) {
		    res = true;
		}
		while (inputReader.ready()) {
		    res = (inputReader.read() == outputReader.read());
		}
	    }
	    catch (IOException ioe) {
		
	    }
	    return res;
	}
	public boolean compressedSmallerThanInput() {
	    boolean res = false;
	    try {
		long inputSize = Files.size(Path.of(TestData.IN_PATH));
		long compressedSize = Files.size(Path.of(TestData.COMP_PATH));
		return (compressedSize < inputSize);
	    }
	    catch (IOException ioe) {
	    }
	    return res;
	}
    }
    
    public HuffmanRevisitedTest() {
    }
    // we could have a method for creating compressed files in order to
    // test decoding with arbitrary data in isolation, but for
    // the moment I'll simply always run encoding and decoding in sequence,
    // relying on the encoding step to provide input for the decoding step
    public static void createInputData(String text) {
	try {
	    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(TestData.IN_PATH));
	    osw.write(text, 0, text.length());	    
	    System.out.println("createInputData: wrote " + text.length() + " chars of test data to " + TestData.IN_PATH);
	    osw.close();
	}
	catch (IOException ioe) {
	    
	}
	
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    // following the Refresh Data testing pattern
    @BeforeEach
    public void clearAllData() {
	new File(TestData.IN_PATH).delete();
	new File(TestData.COMP_PATH).delete();
	new File(TestData.DEC_PATH).delete();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class HuffmanRevisited, focused mainly
     * on showing that receiving correctly-formatted arguments is working.
     * (Which it doesn't really test right now!)
     */
    @Test
    public void testMain() {
	System.out.println("main");
	createInputData("Hello");
	String[] args = new String[]{"-e", TestData.IN_PATH, TestData.COMP_PATH};
	HuffmanRevisited.main(args);
	args = new String[]{"-d", TestData.COMP_PATH, TestData.DEC_PATH};
	HuffmanRevisited.main(args);
	
    }
    @Test
    public void testAbababab() {
	createInputData("abababab");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	
	//assertTrue(status.compressedSmallerThanInput());
	assertTrue(status.inputEqualsDecompressed());
    }
    @Test
    public void testSingleChar() {
	createInputData("c");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	
	//assertTrue(status.compressedSmallerThanInput());
	assertTrue(status.inputEqualsDecompressed());
    }
    @Test
    public void testEmpty() {
	createInputData("");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	
	//assertTrue(status.compressedSmallerThanInput());
	assertTrue(status.inputEqualsDecompressed());
    }
    @Test
    public void testtwoChars() {
	createInputData("ab");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	
	//assertTrue(status.compressedSmallerThanInput());
	assertTrue(status.inputEqualsDecompressed());
    }
    public void testWikipediaText() {
	createInputData("A DEAD DAD CEDED A BAD BABE A BEADED ABACA BED");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
	assertTrue(status.compressedSmallerThanInput());
    }
    @Test
    public void testLongerEnglishText()  {
	createInputData("Why is it such an artificial-feeling situation to create"
		+ " prose simply to fill space? I suppose the primary requirement"
		+ " of language, which is communication, has been dropped."
		+ " Perhaps it also shows that the generativity which comes"
		+ " naturally to any digital computer - simply run the loop"
		+ " more times or go deeper into recursion - is alien to human"
		+ " consciousness which, so the popular science meme goes, can"
		+ " only deal with roughly seven separate items at once without"
		+ " losing track. Makes me think of Chomsky's regular languages"
		+ " - a powerful concept and yet one deceptively far away from"
		+ " human experience and perhaps most human utility.");
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
	assertTrue(status.compressedSmallerThanInput());
    }
    @Test
    public void testFrenchText() {
	String data = "C’est dans la Thébaïde, au haut d’une montagne, sur une plate-forme arrondie en demi-lune,"
		+ " et qu’enferment de grosses pierres. La cabane de l’ermite occupe le fond. Elle est faite de b"
		+ "oue et de roseaux, à toit plat, sans porte. On distingue dans l’intérieur une cruche avec un p"
		+ "ain noir ; au milieu, sur une stèle de bois, un gros livre ; par terre çà et là des filaments "
		+ "de sparterie, deux ou trois nattes, une corbeille, un couteau. À dix pas de la cabane, il y a "
		+ "une longue croix plantée dans le sol ; et à l’autre bout de la plate-forme, un vieux palmier t"
		+ "ordu se penche sur l’abîme, car la montagne est taillée à pic, et le Nil semble faire un lac a"
		+ "u bas de la falaise. La vue est bornée à droite et à gauche par l’enceinte des roches. Mais du"
		+ " côté du désert, comme des plages qui se succéderaient, d’immenses ondulations parallèles d’un"
		+ " blond cendré s’étirent les unes derrière les autres, en montant toujours ; puis au delà des s"
		+ "ables, tout au loin, la chaîne lybique forme un mur couleur de craie, estompé légèrement par d"
		+ "es vapeurs violettes. En face, le soleil s’abaisse. Le ciel, dans le nord, est d’une teinte gr"
		+ "is perle, tandis qu’au zénith des nuages de pourpre, disposés comme les flocons d’une crinière"
		+ " gigantesque, s’allongent sur la voûte bleue. Ces rais de flamme se rembrunissent, les parties"
		+ " d’azur prennent une pâleur nacrée ; les buissons, les cailloux, la terre, tout paraît dur com"
		+ "me du bronze ; et dans l’espace flotte une poudre d’or tellement menue qu’elle se confond avec"
		+ " la vibration de la lumière.";
	createInputData(data);
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
	assertTrue(status.compressedSmallerThanInput());
  
    }
@Test
    public void testOneKilobyte()  {
	String data;
	// A thousand-character string made of ASCII chars should be 1KB in UTF-8.
	char[] ca = new char[1024]; //[1048576];

	for (int i = 0; i < ca.length; i++) {
	    ca[i] = (char) (i % 128); // 128-192 are not Unicode code points so I stop here
	}
	  
	data = new String(ca);
	createInputData(data);
	HuffmanRevisited.encodeFileTo(TestData.IN_FILE, TestData.COMP_FILE);
	HuffmanRevisited.decodeFileTo(TestData.COMP_FILE, TestData.DEC_FILE);
	FullCycleStatus status = new FullCycleStatus();
	assertTrue(status.inputEqualsDecompressed());
	//assertTrue(status.compressedSmallerThanInput()); // unreasonable given the cycling through 128 chars!
    }
    /**
     * Test of encodeFileTo method, of class HuffmanRevisited. This is tested alongside decodeFileTo, above.

    @Test

    }
     */
    /**
     * Test of decodeFileTo method, of class HuffmanRevisited. This is tested alongside EncodeFileTo, above.

    @Test
    public void testDecodeFileTo() {
    }
     */    
}
