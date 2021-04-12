/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huffmanrevisited;

import java.io.File;

/**
 *
 * @author Kevin Higgins
 */
public class TestData {
    // '!'
    static byte[] ZERO_BYTE = new byte[]{0};
    static byte[] UP_TO_EIGHT_INSTANCES_OF_SINGLE_CHAR_ENCODED_DATA = ZERO_BYTE;
    static byte[] ONE_CHAR_COMPACTED_TREE = new byte[]{(byte)128, (byte)16, (byte)128}; 
    static String IN_PATH = "C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\input.txt";
    static String COMP_PATH = "C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\compressed.huf";
    static String ALT_PATH = "C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\compressed2.huf";

    static String DEC_PATH = "C:\\Users\\Kevin\\Documents\\NetBeansProjects\\HuffmanRevisited\\src\\main\\java\\huffmanrevisited\\output.txt";

    static File IN_FILE = new File(IN_PATH);
    static File COMP_FILE = new File(COMP_PATH);
    static File DEC_FILE = new File(DEC_PATH);
    static File ALT_FILE = new File(ALT_PATH);
}
