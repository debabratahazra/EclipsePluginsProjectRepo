package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Test;

public class DocumentViewerUtilTest {

    private static final String DEFAULT_FILE_NAME = "testfiles\\t24EditorTest_documentViewerUtil_all.BASIC";
    private static String fileContent = getFileContents(DEFAULT_FILE_NAME);
    private static IDocument document = new Document(fileContent);

    @Test
	public void testGetWordStartOffset() {
        int[] keys = { 80, 110, 131, 145, 169, 164, 193, 205 };
        int[] startOffsets = { 73, 110, 127, 145, 164, 164, 189, 201 };
        for (int i = 0; i < keys.length; i++) {
            int offset = DocumentViewerUtil.getWordStartOffset(document, keys[i]);
            Assert.assertTrue(offset == startOffsets[i]);
        }
    }

    @Test
	public void testGetCurrentWordPart() {
        int[] keys = { 80, 110, 131, 145, 169, 164, 193, 205 };
        String[] currentWords = { "I_COMMO", "", "jBC.", "", "TEST1", "", "ARG2", "CALL" };
        for (int i = 0; i < keys.length; i++) {
            String word = DocumentViewerUtil.getCurrentWordPart(document, keys[i]);
            Assert.assertTrue(word.equals(currentWords[i]));
        }
    }

    @Test
	public void testGetPrecedingWord() {
        int[] keys = { 0, 80, 110, 131, 145, 169, 164, 193, 205 };
        String[] precedingWords = { "", "$INSERT", "I", "INCLUDE", "", "TEST1", "CALL", "ARG2", "CALL" };
        for (int i = 0; i < keys.length; i++) {
            String word = DocumentViewerUtil.getPrecedingWord(document, keys[i]);
            Assert.assertTrue(word.equals(precedingWords[i]));
        }
    }

    private static String getFileContents(String filename) {
        StringBuffer sb = new StringBuffer();
        String contents;
        BufferedReader br = null;
        try {
            InputStream is = DocumentViewerUtil.class.getResourceAsStream("/" + filename);
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        contents = sb.toString();
        if (contents == null)
            return "";
        else
            return contents;
    }
}
