package com.temenos.t24.tools.eclipse.basic.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_POSITION_CATEGORY;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;
import com.temenos.t24.tools.eclipse.basic.views.variables.VariablesListBuilder;

/**
 * OVERVIEW ViewManager is intended to be invoked by client Views in order to
 * get the items they need to display in the View.
 * 
 * Typically a View (e.g. CallsView - the view that shows all the CALLs within a
 * document) will invoke the method getViewItems(document, category) to get an
 * array of items (in this case items will be CALL labels).
 * 
 * ITEMS Items in a View are treated in a standard way through the interface
 * IT24ViewITem. In a simple way, an item has a name, offset, length and
 * category. Once an item has been identified, a Position (see bellow) for that
 * item is created and attached to the document.
 * 
 * POSITIONS Positions are a useful feature provided by the framework. Positions
 * can be attach to documents. They refer to offset and length of items within
 * the document. Whenever the editor is changed (by typing/deleting contents)
 * the framework handles the update of all the declared Positions for that
 * document.
 * 
 * Example: Position position = new Position(offset, length);
 * document.addPosition(POSTION_CATEGORY_XYZ, position);
 * 
 */
public class ViewManager {

    private LogConsole log = LogConsole.getT24BasicConsole();

    /**
     * public constructor
     */
    public ViewManager() {
    }

    /**
     * One of the main methods in this class. Given a document and an Item
     * category this method will build an array of elements belonging to that
     * category found in the document.
     * 
     * @param document - Active document containing the text to be parsed
     * @param itemCategory - either CALLs, INSERTs, ....
     * @return array with the requested items. If no item was found an empty
     *         array is returned.
     */
    public IT24ViewItem[] getViewItems(IDocument document, T24_VIEW_ITEM_CATEGORY itemCategory) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (T24_VIEW_ITEM_CATEGORY.CALL_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.CALLS_POSITION);
            itemsAList = getCalls(document);
        } else if (T24_VIEW_ITEM_CATEGORY.INSERT_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.INSERTS_POSITION);
            itemsAList = getInserts(document);
        } else if (T24_VIEW_ITEM_CATEGORY.LABEL_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.LABELS_POSITION);
            itemsAList = getLabels(document);
        } else if (T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.REGIONS_POSITION);
            itemsAList = getT24Regions(document);
        } else if (T24_VIEW_ITEM_CATEGORY.LABEL_AND_T24REGION_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.LABELS_POSITION);
            addPositionCategory(document, T24_POSITION_CATEGORY.REGIONS_POSITION);
            itemsAList = getLabelsAndT24Regions(document);
            addPositionCategory(document, T24_POSITION_CATEGORY.REGIONS_POSITION);
        } else if (T24_VIEW_ITEM_CATEGORY.TODO_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.TODOS_POSITION);
            itemsAList = getT24TODO(document);
        } else if (T24_VIEW_ITEM_CATEGORY.GOSUB_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.GOSUBS_POSITION);
            itemsAList = getGosubs(document);
        } else if (T24_VIEW_ITEM_CATEGORY.MACRO_ITEM.equals(itemCategory)) {
            itemsAList = null;
            /** for future usage */
        } else if (T24_VIEW_ITEM_CATEGORY.FIRST_SERVER_ITEM.equals(itemCategory)) {
            itemsAList = this.getServerItems(itemCategory);
        } else if (T24_VIEW_ITEM_CATEGORY.SECOND_SERVER_ITEM.equals(itemCategory)) {
            itemsAList = this.getServerItems(itemCategory);
        } else if (T24_VIEW_ITEM_CATEGORY.ONLINE_SRC_ITEM.equals(itemCategory)) {
            itemsAList = this.getOnlineSrcItems();
        } else if (T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM.equals(itemCategory)) {
            addPositionCategory(document, T24_POSITION_CATEGORY.VARIABLES_POSITION);
            itemsAList = getVariables(document);
        } else {
            // default - nothing is retrieved. Array will be empty
        }
        int arraySize = itemsAList.size();
        return ((IT24ViewItem[]) itemsAList.toArray(new IT24ViewItem[arraySize]));
    }

    /**
     * One of the main methods in this class. Given a document and an Item
     * category this method will build an array of GROUPS of elements belonging
     * to that category found in the document. Elements have labels (e.g. INSERT
     * MY.MODULE => "MY.MODULE" would be the label). The same label may appear
     * multiple times. This method groups them under a single class
     * T24ViewItemGroup.
     * 
     * @param document - Active document containing the text to be parsed
     * @param itemCategory - either CALLs, INSERTs, ....
     * @return array with the requested items. If no item was found an empty
     *         array is returned.
     */
    public T24ViewItemGroup[] getViewItemsGroups(IDocument document, T24_VIEW_ITEM_CATEGORY itemCategory) {
        IT24ViewItem[] items = getViewItems(document, itemCategory);
        ArrayList<T24ViewItemGroup> groupsAList = new ArrayList<T24ViewItemGroup>();
        T24ViewItemGroup group;
        /*
         * We have an array with all the view items of a given category. We are
         * now trying to group them by the label and category. It is assumed
         * that they all have the same category (e.g. they are all CALLs or
         * INSERTs, etc.) So a group is a list of items with the same label (and
         * category), but with different positions within the document.
         */
        // iterate through items list searching for equal labels
        for (int i = 0; i < items.length; i++) {
            int groupIndex = alreadyExist(groupsAList, items[i].getLabel(), items[i].getCategory());
            if (groupIndex >= 0) {
                // The label already exist => other items with the same label
                // were found
                // previously. All we do now is add the position of this new
                // item to the
                // appropriate group.
                // Extract the group where the label exists
                group = groupsAList.get(groupIndex);
                // add the new position
                group.addPosition(items[i].getPosition());
            } else {
                // This is a new label, so create a new group for it.
                group = new T24ViewItemGroup(items[i]);
                // Add the group the groups ArrayList
                groupsAList.add(group);
            }
        }
        int arraySize = groupsAList.size();
        T24ViewItemGroup[] groups = new T24ViewItemGroup[arraySize];
        return ((T24ViewItemGroup[]) groupsAList.toArray(groups));
    }

    /**
     * Check whether the passed label and category already exists in the array
     * 
     * @param groups - ArrayList with all the T24ViewItem groups.
     * @param label - String with the label name.
     * @param category -
     * @return the group index where the label was found, or -1 if the label was
     *         not found at all. Note: a group is a class which models a list of
     *         view items within a document which share the same label and
     *         category, but they have different positions in that document.
     *         e.g. if a CALL named "TEST.CALL" appears multiple times; they
     *         will be grouped.
     */
    public int alreadyExist(ArrayList<T24ViewItemGroup> groups, String label, T24_VIEW_ITEM_CATEGORY category) {
        for (int i = 0; i < groups.size(); i++) {
            T24ViewItemGroup group = groups.get(i);
            if (group.getLabel().equals(label) && group.getCategory().equals(category)) {
                return i;
            }
        }
        // This point is reached if no match was found
        return -1;
    }

    /**
     * @param document - document where positions are to be added
     * @param positionCategory - category of positions to add
     */
    public void addPositionCategory(IDocument document, T24_POSITION_CATEGORY positionCategory) {
        if (document != null) {
            String[] posCats = document.getPositionCategories();
            for (int i = 0; i < posCats.length; i++) {
                if (posCats[i].equals(positionCategory))
                    return;
            }
            // if this point is reached then the position category didn't exist
            // in the document
            // so add it now
            document.addPositionCategory(positionCategory.getString());
            IPositionUpdater posUp = new DefaultPositionUpdater(positionCategory.getString());
            document.addPositionUpdater(posUp);
        }
    }

    /**
     * Finds all the CALL elements within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getCalls(IDocument document) {
        StringUtil su = new StringUtil();
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        String docContents = document.get();
        if (!"".equals(docContents)) {
            String CALL = "CALL";
            int CALLstartsAt = 0;
            int startLabelIdx = 0;
            int spaceIdx = 0; // index for the next space " " after the CALL
            // string
            int bracketIdx = 0; // index for the next bracket after the CALL
            // string
            int crIdx = 0; // index for the next carry return after the INSERT
            // string
            int lfIdx = 0; // index for the next carry return after the INSERT
            // string
            int endLabelIdx = 0;
            // Iterate through the contents of the text editor looking for
            // matches
            // of the following pattern:
            // CALL" "CALL_LABEL" " => label finishes with a space
            // CALL" "CALL_LABEL(PARAMETERS) => label finishes with a bracked (
            // CALL" "CALL_LABELeol => label finishes with an end of line
            CALLstartsAt = docContents.indexOf(CALL, CALLstartsAt);
            while (CALLstartsAt != -1) {
                String callLine = "";
                int offsetWithinCallLine = -1;
                try {
                    IRegion reg = document.getLineInformation(document.getLineOfOffset(CALLstartsAt));
                    callLine = document.get(reg.getOffset(), reg.getLength());
                    offsetWithinCallLine = CALLstartsAt - document.getLineOffset(document.getLineOfOffset(CALLstartsAt));
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                // An item has been found. Ignore it if it is within a Comment
                // line, or within a literal ('' or "") and search for the next
                // one.
                if (isCall(docContents, CALLstartsAt) && !EditorDocumentUtil.isWithinComment(callLine, offsetWithinCallLine)
                        && !su.isWithinQuotes(callLine, offsetWithinCallLine)) {
                    startLabelIdx = (new StringUtil()).getBeginningOfNextWord(docContents, CALLstartsAt);
                    spaceIdx = docContents.indexOf(" ", startLabelIdx);
                    bracketIdx = docContents.indexOf('(', startLabelIdx);
                    crIdx = docContents.indexOf('\r', startLabelIdx);
                    lfIdx = docContents.indexOf('\n', startLabelIdx);
                    if (spaceIdx == -1 && bracketIdx == -1 && crIdx == -1 && lfIdx == -1) {
                        // The label reaches up to the EOF
                        endLabelIdx = docContents.length();
                    } else {
                        int _MAX = Integer.MAX_VALUE;
                        endLabelIdx = su.min((spaceIdx == -1) ? _MAX : spaceIdx, (bracketIdx == -1) ? _MAX : bracketIdx,
                                (crIdx == -1) ? _MAX : crIdx, (lfIdx == -1) ? _MAX : lfIdx);
                    }
                    // Now Create a new View Item, and add it to the list of
                    // items.
                    // Create a Position for this item and add it to the
                    // document
                    try {
                        int offset = startLabelIdx;
                        int length = (endLabelIdx - startLabelIdx);
                        Position position = new Position(offset, length);
                        document.addPosition(T24_POSITION_CATEGORY.CALLS_POSITION.getString(), position);
                        // Extract the call label and add it to the array list
                        String label = docContents.substring(startLabelIdx, endLabelIdx);
                        itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.CALL_ITEM));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Search next CALL
                CALLstartsAt = docContents.indexOf(CALL, CALLstartsAt + 1);
            }
        }
        // SORT THE ITEMS BY THEIR LABEL
        Collections.sort(itemsAList, new LabelComparator());
        return itemsAList;
    }

    /**
     * Checks different conditions under which a CALL is a BASIC CALL
     * 
     * @param editorText - contents of editor
     * @param offset - offset where the CALL begins within the editorText
     * @return boolean
     */
    private boolean isCall(String editorText, int offset) {
        boolean isCall = false;
        if (offset <= 0) {
            // CALL is at the beginning of text
            if (editorText.charAt(offset + "CALL".length()) == ' ')
                isCall = true;
        } else {
            // Check if CALL is preceded by a ' ' or \n or \r AND followed by a
            // ' '
            if (((editorText.charAt(offset - 1) == ' ') || (editorText.charAt(offset - 1) == '\r') || (editorText
                    .charAt(offset - 1) == '\n'))
                    && editorText.charAt(offset + "CALL".length()) == ' ') {
                isCall = true;
            }
        }
        return isCall;
    }

    /**
     * Finds all the INSERT elements within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getInserts(IDocument document) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        // Get the contents of the current active editor
        String editorText = document.get();
        if (!"".equals(editorText)) {
            String INSERT = "$INSERT";
            int fromIdx = 0;
            int startLabelIdx = 0;
            int spaceIdx = 0; // index for the next space " " after the INSERT
            // string
            int crIdx = 0; // index for the next carry return after the INSERT
            // string
            int lfIdx = 0; // index for the next carry return after the INSERT
            // string
            int endLabelIdx = 0;
            // Iterate through the contents of the text editor looking for
            // matches
            // of the following pattern:
            // $INSERT" "INSERT_LABEL" " => label finishes with a space or a \n
            // or \r
            fromIdx = editorText.indexOf(INSERT, fromIdx);
            while (fromIdx != -1) {
                // An item has been has been found. Ignore it if it is within a
                // Comment line
                // and search for the next one.
                if (!EditorDocumentUtil.isWithinComment(document, fromIdx)) {
                    startLabelIdx = fromIdx + 8;
                    spaceIdx = editorText.indexOf(" ", startLabelIdx);
                    crIdx = editorText.indexOf('\r', startLabelIdx);
                    lfIdx = editorText.indexOf('\n', startLabelIdx);
                    if (spaceIdx == -1 && crIdx == -1 && lfIdx == -1) {
                        // The label reaches up to the EOF
                        endLabelIdx = editorText.length();
                    } else {
                        StringUtil su = new StringUtil();
                        int _MAX = Integer.MAX_VALUE;
                        endLabelIdx = Math.min((spaceIdx == -1) ? _MAX : spaceIdx, Math.min(((crIdx == -1) ? _MAX : crIdx),
                                ((lfIdx == -1) ? _MAX : lfIdx)));
                        endLabelIdx = su.min((spaceIdx == -1) ? _MAX : spaceIdx, (crIdx == -1) ? _MAX : crIdx, (lfIdx == -1) ? _MAX
                                : lfIdx);
                    }
                    // Now Create a new View Item, and add it to the list of
                    // items.
                    // Create a Position for this item and add it to the
                    // document
                    try {
                        int offset = startLabelIdx;
                        int length = (endLabelIdx - startLabelIdx);
                        Position position = new Position(offset, length);
                        document.addPosition(T24_POSITION_CATEGORY.INSERTS_POSITION.getString(), position);
                        // Extract the call label and add it to the array list
                        String label = editorText.substring(startLabelIdx, endLabelIdx);
                        itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.INSERT_ITEM));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Search for the next item
                fromIdx = editorText.indexOf(INSERT, fromIdx + 1);
            }
        }
        return itemsAList;
    }

    /**
     * Load all the BASIC labels from the current active editor. Labels appear
     * with in BASIC modules as: LABEL.NAME: some BASIC code corresponding to
     * LABEL.NAME
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getLabels(IDocument document) {
        // ArrayList to hold the labels found
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        // Get the contents of the current active editor
        String editorText = document.get();
        if (!"".equals(editorText)) {
            int semicolonIdx = 0;
            // Iterate through the contents of the text editor looking for
            // matches of semicolon
            semicolonIdx = editorText.indexOf(":", semicolonIdx);
            while (semicolonIdx != -1) {
                // Check whether the semicolon ":" is part of a label AND
                // it is not within a comment line
                if (isLabel(document, semicolonIdx) && !EditorDocumentUtil.isWithinComment(document, semicolonIdx)) {
                    // Search backwards for " ", "\n", "\r"
                    int startSpaceIdx = editorText.lastIndexOf(" ", semicolonIdx);
                    int startLFIdx = editorText.lastIndexOf("\n", semicolonIdx);
                    int startCRIdx = editorText.lastIndexOf("\r", semicolonIdx);
                    int startLabelNameIdx = (new StringUtil()).max(startSpaceIdx, startLFIdx, startCRIdx) + 1;
                    int endLabelNameIdx = semicolonIdx;
                    // Now Create a new View Item, and add it to the list of
                    // items.
                    // Create a Position for this item and add it to the
                    // document
                    try {
                        int offset = startLabelNameIdx;
                        int length = (endLabelNameIdx - startLabelNameIdx);
                        Position position = new Position(offset, length);
                        document.addPosition(T24_POSITION_CATEGORY.LABELS_POSITION.getString(), position);
                        // Extract the call label and add it to the array list
                        String label = editorText.substring(startLabelNameIdx, endLabelNameIdx);
                        itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Get the next pattern match
                semicolonIdx = editorText.indexOf(":", semicolonIdx + 1);
            }
        }
        // SORT THE ITEMS BY THEIR LABEL
        Collections.sort(itemsAList, new LabelComparator());
        return itemsAList;
    }

    /**
     * Finds all the Variables within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    private ArrayList<IT24ViewItem> getVariables(IDocument document) {
        ArrayList<IT24ViewItem> variablesList = new ArrayList<IT24ViewItem>();
        if (document != null) {
            variablesList = VariablesListBuilder.getInstance().getVariableViewItems(document);
            // SORT THE ITEMS BY THEIR LABEL
            Collections.sort(variablesList, new LabelComparator());
        }
        return variablesList;
    }

    /**
     * Checks different conditions under which a semicolon is a BASIC label. A
     * label is the first word in a line that precedes the semicolon.
     * 
     * @param document
     * @param semicolonIdx - offset where the potential label's semicolon lies
     *            within the document's text
     * @return boolean
     */
    private boolean isLabel(IDocument document, int semicolonIdx) {
        boolean isLabel = false;
        try {
            IRegion lineRegion = document.getLineInformationOfOffset(semicolonIdx);
            String line = document.get(lineRegion.getOffset(), lineRegion.getLength());
            isLabel = EditorDocumentUtil.isLabel(line);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return isLabel;
    }

    /**
     * Finds all the REGION elements within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getT24Regions(IDocument document) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        // Get the contents of the current active editor
        String editorText = document.get();
        if (!"".equals(editorText)) {
            String REGION_PATTERN = "<region name= ";
            int fromIdx = 0;
            int startRegionNameIdx = 0;
            int endRegionNameIdx = 0;
            int closeBrkIdx = 0; // index for the next >
            // Iterate through the contents of the text editor looking for
            // matchesof the following pattern:
            // <region name= TEST.REGION.NAME>
            fromIdx = editorText.indexOf(REGION_PATTERN, fromIdx);
            while (fromIdx != -1) {
                // An item has been has been found. Ignore it if it is within a
                // Comment line and search for the next one.
                // Note: Regions don't need to be cheked for isWithinComments
                // since they start always with a '*'
                startRegionNameIdx = fromIdx + REGION_PATTERN.length();
                closeBrkIdx = editorText.indexOf(">", startRegionNameIdx);
                if (closeBrkIdx == -1) {
                    // The region was probably malformed (not ending with >
                    break;
                } else {
                    endRegionNameIdx = closeBrkIdx;
                }
                // Now Create a new View Item, and add it to the list of items.
                // Create a Position for this item and add it to the document
                try {
                    int offset = startRegionNameIdx;
                    int length = (endRegionNameIdx - startRegionNameIdx);
                    Position position = new Position(offset, length);
                    document.addPosition(T24_POSITION_CATEGORY.REGIONS_POSITION.getString(), position);
                    // Extract the call label and add it to the array list
                    String label = editorText.substring(startRegionNameIdx, endRegionNameIdx);
                    itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Search for the next item
                fromIdx = editorText.indexOf(REGION_PATTERN, fromIdx + 1);
            }
        }
        return itemsAList;
    }

    /**
     * @param document
     * @return
     */
    public ArrayList<IT24ViewItem> getLabelsAndT24Regions(IDocument document) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        // Add labels first
        itemsAList.addAll(this.getLabels(document));
        // Add regions after
        itemsAList.addAll(this.getT24Regions(document));
        return itemsAList;
    }

    /**
     * Finds all the TODO entries within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getT24TODO(IDocument document) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        // Get the contents of the current active editor
        String editorText = document.get();
        if (!"".equals(editorText)) {
            int fromIdx = 0;
            int startCommentIdx = 0;
            int crIdx = 0; // index for the next carry return after the todo
            // word
            int lfIdx = 0; // index for the next carry return after the todo
            // word
            int endCommentIdx = 0;
            // Iterate through the contents of the text editor looking for
            // matchesof the following pattern:
            // TODO" "comment string" " => label finishes with a space or a \n
            // or \r
            fromIdx = editorText.indexOf("TODO", fromIdx);
            while (fromIdx != -1) {
                // An item has been has been found.
                startCommentIdx = (new StringUtil()).getBeginningOfNextWord(editorText, fromIdx);
                crIdx = editorText.indexOf('\r', startCommentIdx);
                lfIdx = editorText.indexOf('\n', startCommentIdx);
                if (crIdx == -1 && lfIdx == -1) {
                    // The label reaches up to the EOF
                    endCommentIdx = editorText.length();
                } else {
                    StringUtil su = new StringUtil();
                    int _MAX = Integer.MAX_VALUE;
                    endCommentIdx = su.min((crIdx == -1) ? _MAX : crIdx, (lfIdx == -1) ? _MAX : lfIdx);
                }
                // Now Create a new View Item, and add it to the list of items.
                // Create a Position for this item and add it to the document
                try {
                    int offset = startCommentIdx;
                    int length = (endCommentIdx - startCommentIdx);
                    Position position = new Position(offset, length);
                    document.addPosition(T24_POSITION_CATEGORY.TODOS_POSITION.getString(), position);
                    // Extract the call label and add it to the array list
                    String label = editorText.substring(startCommentIdx, endCommentIdx);
                    itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.TODO_ITEM));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Search for the next item
                fromIdx = editorText.indexOf("TODO", fromIdx + 1);
            }
        }
        return itemsAList;
    }

    /**
     * Finds all the GOSUB elements within the current active document
     * 
     * @param document - document whose text is being parsed
     * @return ArrayList - contains items. If none found, then it'll be empty.
     */
    public ArrayList<IT24ViewItem> getGosubs(IDocument document) {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        if (document == null) {
            return itemsAList;
        }
        String editorText = document.get();
        if (!"".equals(editorText)) {
            String GOSUB = "GOSUB";
            int GOSUBstartsAt = 0;
            int startLabelIdx = 0;
            int spaceIdx = 0; // index for the next space " " after the GOSUB
            int bracketIdx = 0; // index for the next bracket after the GOSUB
            int crIdx = 0; // index for the next carry return after the INSERT
            // string
            int lfIdx = 0; // index for the next carry return after the INSERT
            // string
            int endLabelIdx = 0;
            // Iterate through the contents of the text editor looking for
            // matches
            GOSUBstartsAt = editorText.indexOf(GOSUB, GOSUBstartsAt);
            while (GOSUBstartsAt != -1) {
                // An item has been found. Ignore it if it is within a Comment
                // line
                // and search for the next one.
                if (isGosub(editorText, GOSUBstartsAt) && !EditorDocumentUtil.isWithinComment(document, GOSUBstartsAt)) {
                    startLabelIdx = (new StringUtil()).getBeginningOfNextWord(editorText, GOSUBstartsAt);
                    spaceIdx = editorText.indexOf(" ", startLabelIdx);
                    bracketIdx = editorText.indexOf('(', startLabelIdx);
                    crIdx = editorText.indexOf('\r', startLabelIdx);
                    lfIdx = editorText.indexOf('\n', startLabelIdx);
                    if (spaceIdx == -1 && bracketIdx == -1 && crIdx == -1 && lfIdx == -1) {
                        // The label reaches up to the EOF
                        endLabelIdx = editorText.length();
                    } else {
                        StringUtil su = new StringUtil();
                        int _MAX = Integer.MAX_VALUE;
                        endLabelIdx = su.min((spaceIdx == -1) ? _MAX : spaceIdx, (bracketIdx == -1) ? _MAX : bracketIdx,
                                (crIdx == -1) ? _MAX : crIdx, (lfIdx == -1) ? _MAX : lfIdx);
                    }
                    // Now Create a new View Item, and add it to the list of
                    // items.
                    // Create a Position for this item and add it to the
                    // document
                    try {
                        int offset = startLabelIdx;
                        int length = (endLabelIdx - startLabelIdx);
                        Position position = new Position(offset, length);
                        document.addPosition(T24_POSITION_CATEGORY.GOSUBS_POSITION.getString(), position);
                        // Extract the call label and add it to the array list
                        String label = editorText.substring(startLabelIdx, endLabelIdx);
                        itemsAList.add(new T24ViewItem(label, position, T24_VIEW_ITEM_CATEGORY.GOSUB_ITEM));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Search next GOSUB
                GOSUBstartsAt = editorText.indexOf(GOSUB, GOSUBstartsAt + 1);
            }
        }
        // SORT THE ITEMS BY THEIR LABEL
        Collections.sort(itemsAList, new LabelComparator());
        return itemsAList;
    }

    /**
     * Checks different conditions under which a CALL is a BASIC CALL
     * 
     * @param editorText - contents of editor
     * @param offset - offset where the CALL begins within the editorText
     * @return boolean
     */
    private boolean isGosub(String editorText, int offset) {
        boolean isGosub = false;
        if (offset <= 0) {
            // GOSUB is at the beginning of text
            if (editorText.charAt(offset + "GOSUB".length()) == ' ')
                isGosub = true;
        } else {
            // Check if GOSUB is preceeded by a ' ' or \n or \r AND followed by
            // a
            // ' '
            if (((editorText.charAt(offset - 1) == ' ') || (editorText.charAt(offset - 1) == '\r') || (editorText
                    .charAt(offset - 1) == '\n'))
                    && editorText.charAt(offset + "GOSUB".length()) == ' ') {
                isGosub = true;
            }
        }
        return isGosub;
    }

    /**
     * Access the remote server (through the protocol layer) and retrieves the
     * list of items (e.g. files) from a passed directory.
     * 
     * @param itemCategory - category of items to be retrieved.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<IT24ViewItem> getServerItems(T24_VIEW_ITEM_CATEGORY itemCategory) {
        // The results will be held in this array list:
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        if (sesMgr.isUserSignedOn()) {
            MementoUtil mu = MementoUtilFactory.getMementoUtil();
            String serverDir, browseOperand, browsePattern, channelName;
            if (T24_VIEW_ITEM_CATEGORY.FIRST_SERVER_ITEM.equals(itemCategory)) {
                serverDir = mu.getProperty("t24.server.view.remote.server.directory");
                browseOperand = mu.getProperty("t24.remote.server.browse.operation");
                browsePattern = mu.getProperty("t24.remote.server.browse.pattern");
                channelName = mu.getProperty("t24.remote.channel.name");
            } else {
                serverDir = mu.getProperty("t24.server.view.second.remote.server.directory");
                browseOperand = mu.getProperty("t24.remote.server.second.browse.operation");
                browsePattern = mu.getProperty("t24.remote.server.second.browse.pattern");
                channelName = mu.getProperty("t24.remote.second.channel.name");
            }
            // Get list of files from the server
            Response res = sesMgr.getServerFiles(serverDir, browseOperand, browsePattern);
            if (res.getPassed()) {
                // SUCCESS RETRIEVING LIST OF FILES FROM SERVER
                ArrayList<String> files = (ArrayList<String>) res.getObject();
                int numberOfFiles = files.size();
                String message = "";
                if (res.getRespMessage() != "") {
                    message = res.getRespMessage();
                } else {
                    message = numberOfFiles + " items retrieved";
                }
                log.logMessage("(" + res.getActionTimeMillis() + "ms) - " + message + ". From dir: " + serverDir + " - channel: "
                        + channelName + ".");
                // Add at the top the directory.
                T24_VIEW_ITEM_CATEGORY category = T24_VIEW_ITEM_CATEGORY.SERVER_DIRECTORY_ITEM;
                IT24ViewItem viewItem = new T24ViewItem(serverDir, null, category);
                itemsAList.add(viewItem);
                Iterator<String> keysIt = files.iterator();
                while (keysIt.hasNext()) {
                    String item = (String) keysIt.next();
                    category = T24_VIEW_ITEM_CATEGORY.SERVER_FILE_ITEM;
                    // With this information, now create a ViewItem, with enough
                    // info so it can be displayed on an Eclipse's View
                    viewItem = new T24ViewItem(item, null, category);
                    itemsAList.add(viewItem);
                }
            } else {
                // FAIL RETRIEVING LIST OF FILES FROM SERVER
                // An empty list is returned => clear the view
                log.logMessage("Failed to retrieve list of Files from server.\n" + res.getRespMessage());
                T24_VIEW_ITEM_CATEGORY category = T24_VIEW_ITEM_CATEGORY.WARNING_ITEM;
                String message = "COULD NOT RETRIEVE ITEMS";
                IT24ViewItem viewItem = new T24ViewItem(message, null, category);
                itemsAList.add(viewItem);
            }
        } else {
            // USER NOT YET SIGNED ON
            // Display a warning message on the table
            T24_VIEW_ITEM_CATEGORY category = T24_VIEW_ITEM_CATEGORY.WARNING_ITEM;
            String message = "USER HAS NOT SIGNED ON";
            IT24ViewItem viewItem = new T24ViewItem(message, null, category);
            itemsAList.add(viewItem);
        }
        return itemsAList;
    }

    /**
     * Builds an array List with all the online url resources available to
     * users.
     * 
     * @return
     */
    public ArrayList<IT24ViewItem> getOnlineSrcItems() {
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        String TEMENOS_ONLINE = "Temenos Online";
        String JBASE_ONLINE = "jBASE Knowledgebase";
        //String TECHNICAL_SUPPORT_ONLINE = "Direct Technical Support"; // not yet included, don't have the URL
        IT24ViewItem item = new T24ViewItem(TEMENOS_ONLINE, null, T24_VIEW_ITEM_CATEGORY.ONLINE_SRC_ITEM,
                (String) "http://www.temenos.com");
        itemsAList.add(item);
        item = new T24ViewItem(JBASE_ONLINE, null, T24_VIEW_ITEM_CATEGORY.ONLINE_SRC_ITEM,
                (String) "http://www.jbase.com/knowledgebase/");
        itemsAList.add(item);
        return itemsAList;
    }

    /**
     * Internal class. Compares items based on their labels
     */
    private class LabelComparator implements Comparator<IT24ViewItem> {

        public int compare(IT24ViewItem first, IT24ViewItem second) {
            return first.getLabel().compareToIgnoreCase(second.getLabel());
        }
    }
}
