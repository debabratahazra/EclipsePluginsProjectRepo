package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * xml builder using dom4j
 * 
 * @author sbharathraja
 * 
 */
class CommentsXMLBuilder {

    /** xml document where we going to add our stripped out comments */
    private Document xmlDocument;
    /** collection of stripped out comments */
    private ArrayList<CommentsContainer> strippedCommentsList;

    /**
     * create the new document
     */
    private void createDocument() {
        xmlDocument = DocumentFactory.getInstance().createDocument();
    }

    /**
     * building the xml file with given name, in given path, using given list of
     * comments
     * 
     * @param routineName - subroutine name
     * @param targetPath - target folder path
     * @param commentsContainerList - collection of stripped out comments
     *            container
     */
    protected void buildXML(String routineName, String targetPath, ArrayList<CommentsContainer> commentsContainerList) {
        this.strippedCommentsList = commentsContainerList;
        createDocument();
        if (isEligibleToBuildXML()) {
            getRootElement();
            loadChildElements();
            try {
                FileOutputStream outputStream = new FileOutputStream(new File(targetPath + GenerateDocConstants.PATH_SEPARATOR
                        + routineName.toUpperCase() + GenerateDocConstants.XML_EXTN));
                // Create the pretty print of xml document.
                OutputFormat format = OutputFormat.createCompactFormat();
                // Create the xml writer by passing output stream and format
                XMLWriter writer = new XMLWriter(outputStream, format);
                // Write to the xml document
                writer.write(xmlDocument);
                // Flush after done
                writer.flush();
                // closing the writer
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * building the root element
     * 
     * @return root element
     */
    private Element getRootElement() {
        for (ListIterator<CommentsContainer> listIteration = strippedCommentsList.listIterator(); listIteration.hasNext();) {
            CommentsContainer strippedContainer = listIteration.next();
            if (strippedContainer.getKeyword().equalsIgnoreCase("SubRoutine")
                    || strippedContainer.getKeyword().equalsIgnoreCase("Program")) {
                Element rootElement = xmlDocument.addElement(strippedContainer.getKeyword());
                rootElement.addAttribute(GenerateDocConstants.NAME_OF_ELEMENT, strippedContainer.getIdentifier());
                rootElement.addAttribute(GenerateDocConstants.NAME_OF_SMALL_COMMENT, strippedContainer.getInfo());
                rootElement.addAttribute(GenerateDocConstants.NAME_OF_LARGE_COMMENT, strippedContainer.getMoreInfo());
                return rootElement;
            }
        }
        return null;
    }

    /**
     * loading the child elements.
     * 
     */
    private void loadChildElements() {
        Element rootElement = xmlDocument.getRootElement();
        if (rootElement != null) {
            for (ListIterator<CommentsContainer> listIteration = strippedCommentsList.listIterator(); listIteration.hasNext();) {
                CommentsContainer strippedContainer = listIteration.next();
                if (strippedContainer.getKeyword().equalsIgnoreCase("GoSub")) {
                    Element childElement = rootElement.addElement(strippedContainer.getKeyword());
                    childElement.addAttribute(GenerateDocConstants.IDENTIFIER_OF__ELEMENT, strippedContainer.getIdentifier());
                    childElement.addAttribute(GenerateDocConstants.NAME_OF_SMALL_COMMENT, strippedContainer.getInfo());
                }
            }
        }
    }

    /**
     * checking whether the incoming subroutine information is eligible to
     * convert as xml or not.
     * 
     * @return true if incoming comments container list has some values, false
     *         otherwise
     */
    private boolean isEligibleToBuildXML() {
        if (xmlDocument != null && !strippedCommentsList.isEmpty())
            return true;
        else
            return false;
    }
}
