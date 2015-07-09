package com.temenos.t24.tools.eclipse.basic.editors.regions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Contains the business logic that allows the creation of a GOSUB region.
 * This involves the user having highlighted a piece of code in the BASIC 
 * program.
 * This piece of code will be copied at the end of the document with a label
 * name at the beginning, and a return at the end. The original code will be 
 * substituted by a GOSUB LABEL call. 
 *
 */
public class RegionUtil {

    /**
     * 
     * @param contents - String with the contents to be parsed looking for regions.
     * @return 
     */
    public Map<Integer, Integer> findRegions(String contents) {
        Map<Integer, Integer> regions = new HashMap<Integer, Integer>();
        int searchOffset = -1;
        // offset of *** <region
        int regionStartOffset = -1;
        // offset of *** </region        
        int regionEndOffset = -1;
        int level = 0;
        Map<Integer, Integer> openRegionMap = new HashMap<Integer, Integer>();
        regionStartOffset = contents.indexOf("*** <region", searchOffset + 1);
        regionEndOffset = contents.indexOf("*** </region", searchOffset + 1);
        while ((regionStartOffset >= 0) || (regionEndOffset >= 0)) {
            if ((regionStartOffset >= 0) && (regionStartOffset < regionEndOffset)) {
                // The beginning of a region has been found
                // increase level depth
                level++;
                // add the level,start_offset pair to the map
                openRegionMap.put(level, regionStartOffset);
                searchOffset = regionStartOffset;
            } else if (level == 0 && regionStartOffset > regionEndOffset) {
                if (regionEndOffset == -1) {
                    regionStartOffset = -1;
                    break;
                } else {
                    searchOffset = regionEndOffset;
                }
            } else if (level == 0 && regionStartOffset < regionEndOffset) {
                if (regionStartOffset == -1) {
                    regionEndOffset = -1;
                    break;
                } else {
                    searchOffset = regionStartOffset;
                }
            } else if (level > 0) {
                // The end of a region has been found
                // Get the starting offset of the region from the map.
                regionStartOffset = (int) ((Integer) openRegionMap.remove(level));
                // correct the end point
                regionEndOffset = regionEndOffset + "*** </region".length() + 1;
                regions.put(regionStartOffset, regionEndOffset);
                // decrement region level
                level--;
                searchOffset = regionEndOffset;
            }
            regionStartOffset = contents.indexOf("*** <region", searchOffset + 1);
            regionEndOffset = contents.indexOf("*** </region", searchOffset + 1);
        }
        return regions;
    }

    /**
     * Performs the business logic of creating a GOSUB region.
     * @param regionName - name of the label
     * @param regionDesc - textual description
     */
    public void createGosub(T24BasicEditor editor, IDocument doc, String regionName, String regionDesc) {
        if (editor != null) {
            // Get Highlighted region
            TextSelection selectedText = (TextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
            int blockOffset = selectedText.getOffset();
            int blockLength = selectedText.getLength();
            try {
                // Build the GOSUB block, that will be inserted in the document
                String regionBlock = buildGosubBlock(regionName, regionDesc, doc.get(blockOffset, blockLength));
                // Append separator 
                regionBlock = "\r\n*-----------------------------------------------------------------------------\r\n\r\n"
                        + regionBlock;
                // Add the GOSUB block to the document, following these rules:
                // If and END is found as the last statement, add it right above it
                // Any other case, add it right at the bottom of the doc. 
                int endLine = findLastLineWithEND(doc);
                if (endLine != -1) {
                    // END is the last statement of the document
                    IRegion endRegion = doc.getLineInformation(endLine);
                    regionBlock = regionBlock + "\r\nEND\r\n";
                    doc.replace(endRegion.getOffset(), endRegion.getLength(), regionBlock);
                } else {
                    // END is not the last statement of the document. Add the GOSUB right at the end then.
                    String docContents = doc.get();
                    docContents = docContents + regionBlock;
                    doc.set(docContents);
                }
                // Replace highlighted code with a GOSUB LABEL estatement
                String newGosubLine = "GOSUB " + regionName + " ; *" + this.buildDescription(regionDesc);
                doc.replace(blockOffset, blockLength, newGosubLine);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses the contents of the passed document, looking for an END statement.
     * If the last statement of the doc is END it will return the line in which it 
     * was found, otherwise it'll return -1. 
     * @param doc
     * @return document line if END is the last satatement, or -1 if it is not.
     */
    private int findLastLineWithEND(IDocument doc) {
        // Check if the last statement of the document is an END
        int noLines = doc.getNumberOfLines();
        try {
            for (int line = noLines - 1; line >= 0; line--) {
                IRegion lineRegion = doc.getLineInformation(line);
                String lineString = doc.get(lineRegion.getOffset(), lineRegion.getLength());
                lineString = lineString.trim();
                if ("".equals(lineString)) {
                    // empty line, don't do anything, carry on parsing the doc
                } else if ("END".equals(lineString) && !EditorDocumentUtil.isWithinComment(lineString, 1)) {
                    // END statement found (not inside a comment), so return
                    // current line
                    return line;
                } else {
                    // A line of code was found, it wasn't an END statement
                    return -1;
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        // This point is reached if the document is empty.
        return -1;
    }

    /**
     * Performs the business logic of creating a Region.
     * @param regionName - name of the label
     * @param regionDesc - textual description
     */
    public void createRegion(T24BasicEditor editor, IDocument doc, String regionName, String regionDesc) {
        if (editor != null) {
            // Get Highlighted region
            TextSelection selectedText = (TextSelection) editor.getEditorSite().getSelectionProvider().getSelection();
            int highlightOffset = selectedText.getOffset();
            int highlightLength = selectedText.getLength();
            try {
                // Add the region block. This block substitutes the actual code highlighted
                String regionBlock = buildRegionBlock(regionName, regionDesc, doc.get(highlightOffset, highlightLength));
                doc.replace(highlightOffset, highlightLength, regionBlock);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Builds a block with all the region details following an already established
     * template.
     * @param regName - region Name, this will be the GOSUB label call.
     * @param regDesc - region Description
     * @param regContents - region Contents
     * @return String
     */
    public String buildGosubBlock(String regName, String regDesc, String regContents) {
        StringBuffer sb = new StringBuffer();
        sb.append("*** <region name= " + regName + ">\n");
        sb.append(regName + ":\n");
        // append the description
        String description = buildDescription(regDesc);
        sb.append("*** <desc>" + description + "</desc>\n");
        sb.append(regContents + "\n");
        sb.append("RETURN\n");
        sb.append("*** </region>\n");
        return sb.toString();
    }

    /**
     * Builds a block with all the region details following an already established
     * template.
     * @param regName - region Name
     * @param regDesc - region Description
     * @param regContents - region Contents
     * @return String
     */
    public String buildRegionBlock(String regName, String regDesc, String regContents) {
        StringBuffer sb = new StringBuffer();
        sb.append("*** <region name= " + regName + ">\n");
        // append the description
        String description = buildDescription(regDesc);
        sb.append("*** <desc>" + description + "</desc>\n");
        sb.append(regContents + "\n");
        sb.append("*** </region>\n");
        return sb.toString();
    }

    /**
     * Takes a description which can be multiline, and returns it in one single line. 
     * @param regDesc
     * @return desc - single line
     */
    public String buildDescription(String regDesc) {
        StringBuffer sb = new StringBuffer();
        // The desc maybe multiline, break it down into lines, and join them together into one single line later 
        String[] desc = regDesc.split("\\n");
        for (int i = 0; i < desc.length; i++) {
            sb.append(desc[i].trim() + " ");
        }
        return sb.toString();
    }
}
