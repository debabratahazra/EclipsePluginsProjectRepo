/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssethupathi
 */
public class UnsupportedCharConversion {

    public static Map<Character, Character> characterMapT24 = new HashMap<Character, Character>();
    public static Map<Character, Character> characterMapRTC = new HashMap<Character, Character>();
    private static String SPECIAL_DELIMITER = "^";
    private static String forbiddenChars = "\\/:*?\"<>|";

    /**
     * Converts the characters which are not supported by T24 file system.<br>
     * This is required when sending a data file to T24 server.
     * 
     * @param fileName with unsupported characters
     * @return file name converted.
     */
    public static String convertToT24(String fileName) {
        loadCharacterMapT24();
        String convertedFileName = "";
        boolean doConvert = false;
        for (int cnt = 0; cnt < fileName.length(); cnt++) {
            String character = fileName.substring(cnt, cnt + 1);
            if (character.equals(SPECIAL_DELIMITER)) {
                doConvert = true;
            } else {
                if (doConvert) {
                    if (!character.equals("1") && !character.equals("2") && !character.equals("4")) {
                        character = character.replace(character.charAt(0), characterMapT24.get(character.charAt(0)));
                    } else {
                        character = "^" + character;
                    }
                    doConvert = false;
                }
                convertedFileName = convertedFileName + character;
            }
        }
        return convertedFileName;
    }

    /**
     * Converts the characters which are not supported by the local file system.<br>
     * This is required when receiving a data file from T24 server.
     * 
     * @param fileName with unsupported characters.
     * @return file name converted.
     */
    public static String convertFromT24(String fileName) {
        return convertToT24(fileName);
    }

    public static String convertToRTC(String fileName) {
        loadCharacterMapRTC();
        String convertedFileName = "";
        for (int cnt = 0; cnt < fileName.length(); cnt++) {
            String character = fileName.substring(cnt, cnt + 1);
            if (forbiddenChars.indexOf(character) > 0 | character.equals("\\")) {
                character = character.replace(character.charAt(0), characterMapRTC.get(character.charAt(0)));
                convertedFileName = convertedFileName + SPECIAL_DELIMITER + character;
            } else {
                convertedFileName = convertedFileName + character;
            }
        }
        return convertedFileName;
    }

    private static void loadCharacterMapT24() {
        // characterMapT24.put( '1', '\\');
        characterMapT24.put('2', '/');
        characterMapT24.put('3', ':');
        // The ^4 character will be converted into * symbol in T24 End.
        // characterMapT24.put( '4', '*' );
        characterMapT24.put('5', '?');
        characterMapT24.put('6', '"');
        characterMapT24.put('7', '<');
        characterMapT24.put('8', '>');
        characterMapT24.put('9', '|');
    }

    private static void loadCharacterMapRTC() {
        characterMapRTC.put('\\', '1');
        characterMapRTC.put('/', '2');
        characterMapRTC.put(':', '3');
        characterMapRTC.put('*', '4');
        characterMapRTC.put('?', '5');
        characterMapRTC.put('"', '6');
        characterMapRTC.put('<', '7');
        characterMapRTC.put('>', '8');
        characterMapRTC.put('|', '9');
    }
}