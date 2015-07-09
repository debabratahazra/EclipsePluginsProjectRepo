package com.temenos.t24.tools.eclipse.basic.views.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DocInputDecoratorTest {
    
    @Test
	public void testwrapText1() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionaries and the alternate indices for all files";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data dictionaries\nand the alternate indices for all files";       
      assertEquals(expectedText,wrappedText);
         }
    
    @Test
	public void testwrapText2() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionariesss";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data dictionariesss";
      assertEquals(expectedText,wrappedText);
      assertTrue(wrappedText.length()==80);
         }
    
    @Test
	public void testwrapText3() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionaries and";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data dictionaries\nand";           
      assertEquals(expectedText,wrappedText);
         }
    
    @Test
	public void testwrapText4() {
      String originalText = "";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "";
      assertEquals(expectedText,wrappedText);
              }       
    
    @Test
	public void testwrapText5() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionariesdictionaries";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data\ndictionariesdictionaries";
      assertEquals(expectedText,wrappedText);
             }
    /**  
     * Uncomment the test only if the wrapText() adapts to wrap a text of more than length 80 without having any space
    
    
    @Test
	public void testwrapText6() {
        String originalText = "DefinesthelayoutofdatadictionariesDefinesthelayoutofdatadictionariesdictionariesDefines";
        String wrappedText = DocInputDecorator.wrapText(originalText);
        String expectedText = "DefinesthelayoutofdatadictionariesDefinesthelayoutofdatadictionariesdictionaries\nDefines";
        assertEquals(expectedText,wrappedText);
               }
               
                */
    
    
    @Test
	public void testwrapText7() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionaries and the alternate indices for all files Defines the layout of data dictionariesDefines the layout of data dictionaries and the alternate indices for all files";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data dictionaries\nand the alternate indices for all files Defines the layout of data\ndictionariesDefines the layout of data dictionaries and the alternate indices\nfor all files";
      assertEquals(expectedText,wrappedText);
         }
    
    @Test
	public void testwrapText8() {
      String originalText = " ";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = " ";
      assertEquals(expectedText,wrappedText);
              }    
    
    @Test
	public void testwrapText9() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionariesss ";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data\ndictionariesss ";
      assertEquals(expectedText,wrappedText);
      
         }
    
    @Test
	public void testwrapText10() {
      String originalText = "Defines the layout of data dictionariesDefines the layout of data dictionariesss DefinesthelayoutofdatadictionariesDefinesthelayoutofdatadictionariesss";
      String wrappedText = DocInputDecorator.wrapText(originalText);
      String expectedText = "Defines the layout of data dictionariesDefines the layout of data\ndictionariesss\nDefinesthelayoutofdatadictionariesDefinesthelayoutofdatadictionariesss";
      assertEquals(expectedText,wrappedText);
      
         }
}
