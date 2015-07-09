package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.FileEditorInput;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;

/**
 */
public class EditorDocumentUtil {

    /**
     * Holds the current active multi-page editor It is populated each time a
     * multi-page editor is made visible. (see <code>StartUpActions</code> for
     * the listener that traps this)
     */
    public static T24BasicMultiPageEditor activeT24BasicMultiPageEditor = null;

    /**
     * Checks whether the passed IFile is opened in a MultiPageEditor. If so,
     * the editor is returned, otherwise null is returned.
     * 
     * @param iFile
     * @return either the associated opened MultiPageEditor, or null (if none
     *         found)
     */
    public synchronized static T24BasicMultiPageEditor findOpenMultipageEditor(IFile iFile) {
        if (iFile == null) {
            return null;
        }
        // Get refs to opened editors
        IEditorReference[] refs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
        for (int i = 0; i < refs.length; i++) {
            if (refs[i].getEditor(false) instanceof T24BasicMultiPageEditor) {
                T24BasicMultiPageEditor multiPageEditor = (T24BasicMultiPageEditor) refs[i].getEditor(true);
                T24BasicEditor editor = (T24BasicEditor) multiPageEditor.getSourceEditor();
                IFile openedIFile = editor.getIFile();
                if (openedIFile == null) {
                    /**
                     * There is an opened editor, but has no iFile associated
                     * with it.
                     */
                    return null;
                }
                String passedIFileLocation = iFile.getRawLocation().toOSString();
                String openedIFileLocation = openedIFile.getRawLocation().toOSString();
                if (passedIFileLocation.equals(openedIFileLocation)) {
                    // A match has been found, return the multipage editor.
                    return multiPageEditor;
                }
            }
        }
        // This point is reached if no editor was found.
        return null;
    }

    /**
     * Checks whether the passed IFile is opened in a Editor. If so, the editor
     * is returned, otherwise null is returned.
     * 
     * @param iFile file passed to get it's editor
     * @return Either the editor associated with the iFile or null (if none
     *         found)
     */
    public synchronized static T24BasicEditor findOpenEditor(IFile iFile) {
        T24BasicMultiPageEditor multiPageEditor = findOpenMultipageEditor(iFile);
        if (multiPageEditor != null) {
            return multiPageEditor.getSourceEditor();
        }
        return null;
    }

    /**
     * @return true => editor is linked to a local file (in file system), false =>
     *         editor is linked to a remote server file.
     */
    public synchronized static boolean isEditorLocalFile() {
        return ((T24BasicEditor) getActiveMultiPageEditor().getSourceEditor()).isLocal();
    }

    /**
     * @return true => active editor has an iFile associated, false otherwise.
     */
    public synchronized static boolean hasActiveEditorLocalFile() {
        boolean hasLocalFile;
        IFile iFile = ((T24BasicEditor) getActiveMultiPageEditor().getSourceEditor()).getIFile();
        if (iFile == null) {
            hasLocalFile = false;
        } else {
            hasLocalFile = true;
        }
        return hasLocalFile;
    }

    /**
     * Checks whether the passed iFile is opened in an Editor, and it has been
     * modified. If so, then save it locally.
     * 
     * @param iFile
     * @return true the file was found and saved, false nothing was done.
     */
    public synchronized static boolean ifIFileIsOpenAndDirtyThenSave(IFile iFile) {
        T24BasicMultiPageEditor multiPageEditor = findOpenMultipageEditor(iFile);
        if (multiPageEditor != null && multiPageEditor.isDirty()) {
            multiPageEditor.doSave(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves the current active editor in the local workspace.
     */
    public synchronized static void saveActiveMultiPageEditorLocally() {
        T24BasicMultiPageEditor activeEditor = getActiveMultiPageEditor();
        /**
         * Perform the save only if there is an active editor, AND it has a file
         * in the workspace associated with it, i.e. is not in memory
         */
        if (activeEditor != null && hasActiveEditorLocalFile()) {
            activeEditor.doSave(null);
        }
    }

    /**
     * Saves locally all dirty editors.
     */
    public synchronized static void SaveAllDirtyEditors() {
        IEditorPart[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getDirtyEditors();
        for (int i = 0; i < editors.length; i++) {
            editors[i].doSave(null);
        }
    }

    /**
     * Given a fullPath and a filename, it'll return the associated eclipse's
     * IFile.
     * 
     * @param localFullPath (e.g. C:\dev\eclipse\EclipseApplication\Account")
     * @param basicFilenameNoPrefix name without prefix ".b" (e.g.
     *            ACCOUNT.TRADE)
     * @return the associated IFile, or null if no IFile found
     */
    public synchronized static IFile getIFile(String localFullPath, String basicFilenameNoPrefix) {
        String filePath = localFullPath + System.getProperty("file.separator") + basicFilenameNoPrefix
                + PluginConstants.LOCAL_BASIC_DOT_PREFIX;
        return getIFile(filePath);
    }

    /**
     * Given an absolute path of a file, it'll return the associated eclipse's
     * IFile.
     * 
     * @param fullPath absolute path of the file (e.g.,
     *            C:\dev\eclipse\EclipseApplication\Account\ACCOUNT.TRADE.b)
     * @return the associated IFile, or null if no IFile found
     */
    public synchronized static IFile getIFile(String filePath) {
        IFile iFile = null;
        try {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot root = workspace.getRoot();
            IPath path = new Path(filePath);
            iFile = root.getFileForLocation(path);
            // Refresh one level the iFile, in case it has just been added.
            iFile.refreshLocal(IResource.DEPTH_ONE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return iFile;
    }

    /**
     * Opens the filename passed in an editor. The file is expected to exist in
     * the current local project.
     * 
     * @param localFullPath - full path where the file is to be saved.
     * @param basicFilenameNoPrefix - BASIC module filename (e.g.
     *            ACCT.STMT.DATE)
     * @param serverDir - remote server dir from which the file was opened, e.g.
     *            GLOBUS.BP
     * @param isLocalFile : true => a file opened locally, false => represents a
     *            file retrieved from the server.
     * @return if there was an error it'll return the error messsage ready to be
     *         displayed in the console. If everything was ok, it'll return "".
     */
    public synchronized static String openFileWithEditor(String localFullPath, String basicFilenameNoPrefix, String serverDir,
            boolean isLocalFile) {
        String message = "";
        IFile iFile = getIFile(localFullPath, basicFilenameNoPrefix);
        if (iFile != null) {
            message = openFileWithEditor(iFile, serverDir, isLocalFile);
        } else {
            message = "Problem trying to open " + basicFilenameNoPrefix + PluginConstants.LOCAL_BASIC_DOT_PREFIX
                    + " with an editor.\n";
        }
        return message;
    }

    /**
     * Opens the filename passed in an editor
     * 
     * @param iFile - BASIC file, laying somewhere in a project in the
     *            localworkspace
     * @param serverDir - remote server dir, e.g. GLOBUS.BP
     * @param isSaveLocallyOn : true => a file opened locally, false =>
     *            represents a file retrieved from the server.
     * @return if there was an error it'll return the error messsage, else it'll
     *         return "".
     */
    public synchronized static String openFileWithEditor(IFile iFile, String serverDir, boolean isLocalFile) {
        String message = "";
        String basicFilenameWithPrefix = iFile.getName();
        try {
            // Refresh file, in case it has been just added
            iFile.refreshLocal(IResource.DEPTH_ONE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        if (iFile.isAccessible()) {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (window == null) {
                return "Problem trying to open " + basicFilenameWithPrefix + PluginConstants.LOCAL_BASIC_DOT_PREFIX
                        + " with an editor. No Window found\n";
            }
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(iFile.getName());
                try {
                    IEditorPart editor = page.openEditor(new FileEditorInput(iFile), desc.getId());
                    editor.setFocus();
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
                // Now the an editor has been opened, associate the underlying
                // iFile to it
                T24BasicEditor activeEditor = (T24BasicEditor) EditorDocumentUtil.getActiveEditor();
                activeEditor.setIFile(iFile);
                activeEditor.setLocal(isLocalFile);
                activeEditor.setServerDirectory(serverDir);
                activeEditor.setBasicFilenameNoPrefix(StringUtil.removeBasicExtension(basicFilenameWithPrefix));
            }
        } else {
            message = "Problem trying to open " + basicFilenameWithPrefix + " with an editor.\n" + "File couldn't be found";
        }
        return message;
    }

    /**
     * Searches for the pattern <Rating></Rating>, returns the current rating
     * in the file, or "" if no rating was found.
     * 
     * @param basicIFile
     */
    public synchronized static String getFileRating(String contents) {
        String ratingOpen = "<Rating>";
        String ratingClose = "</Rating>";
        String newRating = "";
        int startIdx = contents.indexOf(ratingOpen);
        int endIdx = contents.indexOf(ratingClose);
        if (startIdx != -1 && endIdx != -1) {
            newRating = contents.substring(startIdx + ratingOpen.length(), endIdx);
        }
        return newRating;
    }

    /**
     * Returns the BASIC files (those ending with .b prefix) within the current
     * local directory.
     * 
     * @return ArrayList with all the file names (e.g. ACCOUNTING, TRADING)
     */
    public synchronized static ArrayList<String> getFilesInCurLocalDirectory() {
        StringUtil su = new StringUtil();
        ArrayList<String> filesInDir = new ArrayList<String>();
        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
        String localDir = store.getString(PluginConstants.T24_LOCAL_DIRECTORY);
        // Check whether a path was found, if it doesn't then leave filesInDir
        // emtpy.
        if (!"".equals(localDir) && (localDir != null)) {
            File folder = new File(localDir);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && su.isBasicFile(listOfFiles[i].getName())) {
                    filesInDir.add(StringUtil.removeBasicExtension(listOfFiles[i].getName()));
                }
            }
            Collections.sort(filesInDir);
        }
        return filesInDir;
    }

    /**
     * Returns the TUnitTest files (those following the pattern Test_*.b )
     * present in the given project path.
     * 
     * @return ArrayList with all the TUnitTest file names
     */
    public synchronized static ArrayList<String> getTUnitTestFilesInCurProject(String projectPath) {
        StringUtil su = new StringUtil();
        ArrayList<String> filesInProject = new ArrayList<String>();
        // Check whether a path was found, if it doesn't then leave projectPath
        // emtpy.
        if (!"".equals(projectPath) && (projectPath != null)) {
            File folder = new File(projectPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && su.isTUnitTestFile(listOfFiles[i].getName())) {
                    filesInProject.add(StringUtil.removeBasicExtension(listOfFiles[i].getName()));
                }
            }
            Collections.sort(filesInProject);
        }
        return filesInProject;
    }

    /**
     * Returns the list of basic file names (those following the pattern *.b )
     * present in the given project path.
     * 
     * @return ArrayList with all the basic file names
     */
    public synchronized static ArrayList<String> getBasicFilesInCurProject(String projectPath) {
        StringUtil su = new StringUtil();
        ArrayList<String> filesInProject = new ArrayList<String>();
        // Check whether a path was found, if it doesn't then leave projectPath
        // emtpy.
        if (!"".equals(projectPath) && (projectPath != null)) {
            File fileList = new File(projectPath);
            File[] listOfFiles = fileList.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && su.isBasicFile((listOfFiles[i].getName()))) {
                    filesInProject.add(StringUtil.removeBasicExtension(listOfFiles[i].getName()));
                }
            }
            Collections.sort(filesInProject);
        }
        return filesInProject;
    }

    /**
     * Returns the current location - relative to the workspace. So it'll return
     * something like "project\folder1\folder2\"
     * 
     * @return file's location relative to the workspace
     */
    public synchronized static String getLocalRelativeLocation(IFile iFile) {
        String relativePath = "";
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        // workspace full location (e.g. C:\dev\eclipse\workspace")
        String workspacePath = root.getLocation().toOSString();
        // file's full location, minus the last segment (this segement is the
        // file's name)
        // e.g. (c:\dev\eclipse\workspace\myproject\bp)
        String fullFilePath = iFile.getLocation().removeLastSegments(1).toOSString();
        int match = fullFilePath.indexOf(workspacePath);
        if (match != -1) {
            relativePath = fullFilePath.substring(workspacePath.length());
        }
        return relativePath;
    }

    /**
     * Removes all the comments
     * 
     * @param doc
     * @return
     */
    public synchronized static String getCodeOnly(String contents) {
        int commentIdx = -1;
        String[] lines = contents.split("\n");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            // remove any \r (Carry Return) lyring around
            line = line.replaceAll("\\r", "");
            if (StringUtil.isComment(line)) {
                // don't append this line
            } else if ((commentIdx = getCommentStart(line)) != -1) {
                // there is a comment embedded in the line.
                // e.g. > CALL MODULE.ACCOUNT ; * COMMENT
                // Remove the comment and append the line
                sb.append(line.substring(0, commentIdx) + "\r\n");
            } else {
                // it is a normal line
                sb.append(line + "\r\n");
            }
        }
        return sb.toString();
    }

    public synchronized static int getCommentStart(String line) {
        int startComment = -1;
        int semicolonIdx1 = line.lastIndexOf(";*", line.length());
        int semicolonIdx2 = line.lastIndexOf("; *", line.length());
        int remIdx = line.lastIndexOf("REM", line.length());
        if ((semicolonIdx1 == -1) && (semicolonIdx2 == -1) && (remIdx == -1)) {
            // No comment pattern found
            return -1;
        } else {
            startComment = (new StringUtil()).max(semicolonIdx1, semicolonIdx2, remIdx);
        }
        return startComment;
    }

    /**
     * @return String - active editor's filename (e.g. "ACCOUNT.b", or "" if
     *         there is no active editor.
     */
    public synchronized static String getActiveEditorFilename() {
        T24BasicEditor editor = getActiveEditor();
        if (editor != null) {
            return editor.getTitle();
        } else {
            return "";
        }
    }

    /**
     * @return IRegion - highlighted region within the active editor. If there
     *         is no active editor it'll return null.
     */
    public synchronized static IRegion getHighlightedRegion() {
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        IRegion region = null;
        if (editor != null) {
            region = editor.getHighlightRange();
        }
        return region;
    }

    /**
     * @return T24BasicEditor - returns either the current editor
     *         (T24BasicEditor) or null if no editor is currently active.
     */
    public synchronized static T24BasicEditor getActiveEditor() {
        T24BasicEditor activeEditor = null;
        IWorkbench wrkbnch = PlatformUI.getWorkbench();
        if (wrkbnch == null) {
            return null;
        }
        IWorkbenchWindow activeWorkbenchWindow = wrkbnch.getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
            return null;
        }
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        if (activePage == null) {
            return null;
        }
        try {
            IEditorPart currentEditor = activePage.getActiveEditor();
            if (currentEditor instanceof T24BasicEditor) {
                return (T24BasicEditor) currentEditor;
            }
            T24BasicMultiPageEditor multipage = (T24BasicMultiPageEditor) activePage.getActiveEditor();
            if (multipage == null) {
                return null;
            }
            activeEditor = multipage.getSourceEditor();
        } catch (java.lang.ClassCastException e) {
            activeEditor = null;
        }
        return activeEditor;
    }

    /**
     * @return T24BasicMultiPageEditor - returns either the current editor
     *         (T24BasicMultiPageEditor) or null if no editor is currently
     *         active.
     */
    public synchronized static T24BasicMultiPageEditor getActiveMultiPageEditor() {
        T24BasicMultiPageEditor activeEditor = null;
        IWorkbench wrkbnch = PlatformUI.getWorkbench();
        if (wrkbnch == null) {
            return null;
        }
        IWorkbenchWindow activeWorkbenchWindow = wrkbnch.getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
            return null;
        }
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        if (activePage == null) {
            return null;
        }
        try {
            T24BasicMultiPageEditor multipage = (T24BasicMultiPageEditor) activePage.getActiveEditor();
            if (multipage == null) {
                return null;
            } else {
                return multipage;
            }
        } catch (java.lang.ClassCastException e) {
            activeEditor = null;
        }
        return activeEditor;
    }

    /**
     * @return String - text contents of the active editor. If no active editor
     *         is found, or contents are empty, it'll return ""
     */
    public synchronized static String getActiveEditorContents() {
        return getEditorContents(getActiveEditor());
    }

    /**
     * @return String - text contents of the associated editor. If no editor is
     *         found, or contents are empty, it'll return ""
     */
    public synchronized static String getEditorContents(T24BasicEditor editor) {
        String editorText = "";
        if (editor != null) {
            editorText = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
        }
        return editorText;
    }

    /**
     * Returns the IDocument object associated to the editor
     * 
     * @param T24BasicEditor - text editor for which its associated document is
     *            required
     * @return - Returns the IDocument object associated to the editor, or null
     *         if no Editor is currently active Note:
     */
    public synchronized static IDocument getDocument(T24BasicEditor editor) {
        IDocument doc = null;
        if (editor != null) {
            doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
        }
        return doc;
    }

    /**
     * Returns the IDocument object associated to the Active editor
     * 
     * @return - Returns the IDocument object associated to the Active editor,
     *         or null if no Editor is currently active Note:
     */
    public synchronized static IDocument getActiveDocument() {
        return getDocument(EditorDocumentUtil.getActiveEditor());
    }

    /**
     * @param window
     * @return String - the absolute path of the file displayed on current
     *         active editor
     */
    public synchronized static String getActiveEditorLocation(IWorkbenchWindow window) {
        String path = "";
        if (window != null) {
            IWorkbenchPage page = window.getActivePage();
            IEditorPart editor = page.getActiveEditor();
            IEditorInput input = editor.getEditorInput();
            IFile ifile = ResourceUtil.getFile(input);
            IPath location = ifile.getLocation();
            path = location.toString();
            // The path is separated by "/", may need to be changed depending on
            // which platform (win, unix, ..) this is running on
            String fileSeparator = System.getProperty("file.separator");
            path = (new StringUtil()).substitute(path, "/", fileSeparator);
        }
        return path;
    }

    /**
     * @param selection
     */
    public synchronized static void highlightInActiveEditor(int offset, int length) {
        if (offset > 0 && length > 0) {
            // Highlight in the active editor
            T24BasicEditor activeEditor = EditorDocumentUtil.getActiveEditor();
            boolean moveCursor = false; // don't move cursor to start of
            // highlighted section
            activeEditor.setHighlightRange(offset, length, moveCursor);
        }
    }

    /**
     * Checks whether an element is within a comment. BASIC comments are formed
     * with: either the keyword REM or the asterisk (*) at the beginning of the
     * line: >REM comment line >* comment line >;* comment line
     * 
     * @param doc - Document containing the text being analysed
     * @param docOffset - offset within the document
     * @return true/false
     */
    public synchronized static boolean isWithinComment(IDocument document, int docOffset) {
        boolean isWithinComment = false;
        if (document == null || document.getLength() == 0 || docOffset < 0 || docOffset > document.getLength())
            return false;
        try {
            IRegion lineRegion = document.getLineInformationOfOffset(docOffset);
            int lineOffset = docOffset - lineRegion.getOffset();
            String lineTxt = document.get(lineRegion.getOffset(), lineRegion.getLength());
            isWithinComment = isWithinComment(lineTxt, lineOffset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return isWithinComment;
    }

    /**
     * Finds out whether the offset passed lies within a comment. e.g. >TEXT
     * EXAMLE ; * COMMENT1 or >REM COMMENT2
     * 
     * @param lineTxt
     * @param offset within the line
     * @return true/false
     */
    public synchronized static boolean isWithinComment(String lineTxt, int offset) {
        boolean isWithinComment = StringUtil.isComment(lineTxt);
        if (isWithinComment) {
            // The whole line is indeed a comment
            return true;
        } else {
            // The whole line is NOT a comment, but maybe part of it IS
            int semicolonIdx1 = lineTxt.lastIndexOf(";*", offset);
            int semicolonIdx2 = lineTxt.lastIndexOf("; *", offset);
            int remIdx = lineTxt.lastIndexOf("REM", offset);
            if ((semicolonIdx1 == -1) && (semicolonIdx2 == -1) && (remIdx == -1)) {
                // No comment pattern found
                return false;
            } else {
                int start = (new StringUtil()).max(semicolonIdx1, semicolonIdx2, remIdx);
                lineTxt = lineTxt.substring(start);
                isWithinComment = StringUtil.isComment(lineTxt);
            }
        }
        return isWithinComment;
    }

    /**
     * Traps region lines, which look like: *** <region name= TEST.GOSUB.LABEL>
     * TEST.GOSUB.LABEL: *** <desc>Line one *** line two *** </desc>
     * 
     * @param line
     * @return
     */
    public synchronized static boolean isLineARegion(String line) {
        if (line == null || "".equals(line)) {
            return false;
        }
        StringUtil su = new StringUtil();
        String firstWord = su.getFirstWordInLine(line);
        if ("".equals(firstWord)) {
            return false;
        }
        if (firstWord.length() >= 3 && firstWord.charAt(0) == '*' && firstWord.charAt(1) == '*' && firstWord.charAt(2) == '*') {
            // The line looks like: "*** comment"
            return true;
        }
        return false;
    }

    /**
     * Checks different conditions under which a semicolon is a BASIC label. A
     * label is the first word in a line that precedes the semicolon.
     * 
     * @param line to be tested for label
     * @return true/false
     */
    public synchronized static boolean isLabel(String line) {
        int semicolonIdx = line.indexOf(":");
        // If there is text after the semicolon then it is NOT a label, unless
        // it's a comment
        // e.g. CHECKFILE(FT4.LINK)="FT.COMMISSION.TYPE": @FM <== this is NOT a
        // label
        // e.g. ACCOUNTING.SUBRTN: *TEST <== this IS a label
        String afterSemicolon = line.substring(semicolonIdx + 1);
        if (!StringUtil.isEmpty(afterSemicolon) && !StringUtil.isComment(afterSemicolon)) {
            /** It is not a label */
            return false;
        }
        /** Check that there is at least one blank space after semicolon */
        if (afterSemicolon.length() > 0 && (afterSemicolon.charAt(0) != ' ')) {
            return false;
        }
        // Now check whether the first word in the line
        // is the one that appears just before the semicolon.
        // i.e. "LABEL: " <== is a label
        // but "OTHER LABEL: " <== is not a a label
        if (semicolonIdx != -1) {
            String beforeSemicolon = line.substring(0, semicolonIdx + 1);
            String str = "(^\\s*[\\S]+:)";
            Pattern ptrn = Pattern.compile(str);
            Matcher matcher = ptrn.matcher(beforeSemicolon);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
