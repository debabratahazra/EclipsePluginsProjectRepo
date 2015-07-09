package com.temenos.t24.tools.eclipse.basic.document.capture;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class DocumentHtmlValidatorTest {

    @Test
	public void testValidatePositive() {
        DocumentHtmlValidator validator = DocumentHtmlValidator.newInstance();
        String document = "This is a document. This has no bold nothing! ";
        // case 0
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 1
        document = "This is a document <b>This is bold</b>test";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 2
        document = "This is a document<p></p>test";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 3
        document = "<br>test</br>test";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 4
        document = "This is <li>test</li>";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 5
        document = "<p>test</p><b>a</b>sdfsfsfa<br>sdfsdfa</br>a<li>\n\nasd</li>";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
        // case 6
        document = "<p>t</p><b> </b>s<br>  </br><li></li><br/>";
        try {
            validator.parse(document);
        } catch (InvalidDocumentException e) {
            fail(e.getMessage());
        }
    }

    @Test
	public void testValidateNegative() {
        DocumentHtmlValidator validator = DocumentHtmlValidator.newInstance();
        // case 0
        String document = null;
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 1
        document = "</b>This<br/> is bold</b>test";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 2
        document = "This is a document<est";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 3
        document = "<br>test";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 4
        document = "This is <li>te<b>st</li>tyest</b>";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 5
        document = "<p>test</p><b>a</b>sdfsfsfa< br>sdfsdfa</br>a<li>\n\nasd</li>";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 6
        document = "<p>t</p><b> </b>s<br>  </br><li></ li>";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // case 7
        document = "teatsli></ li>";
        try {
            validator.parse(document);
            fail("Should have been failed");
        } catch (InvalidDocumentException e) {
            assertNotNull(e);
        }
        // TODO: Sethu - more failure cases!
    }
}
