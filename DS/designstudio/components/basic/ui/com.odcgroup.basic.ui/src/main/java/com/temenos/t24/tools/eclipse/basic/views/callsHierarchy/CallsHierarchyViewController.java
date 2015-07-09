package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

/**
 * Model controller for {@link CallsHierarchyView}.<br>
 * This object gets the code from T24 and parses it for calls and supply<br>
 * required information to the view.
 * 
 * @author ssethupathi
 * 
 */
public class CallsHierarchyViewController {

    private static final CallsHierarchyViewController viewController = new CallsHierarchyViewController();
    private static RemoteSitesManager rsm = RemoteSitesManager.getInstance();
    private String siteName;

    private CallsHierarchyViewController() {
    }

    public static CallsHierarchyViewController getInstance() {
        return viewController;
    }

    /**
     * Returns the parent (root) node of the tree.
     * 
     * @param siteName name of the remote site
     * @param subroutineName name of the subroutine
     * @return parent node
     */
    public IT24TreeViewNode getParentNode(String siteName, String subroutineName) {
        this.siteName = siteName;
        T24Subroutine subroutine = new T24Subroutine(subroutineName, siteName, new CallLineDetail(-1, ""));
        IT24TreeViewNode parentNode = new CallsHierarchyNode(subroutine, null);
        return parentNode;
    }

    /**
     * Returns the child nodes for the given parent node. These nodes represents<br>
     * the T24 Subroutines which are called from the passed-in parent node.
     * 
     * @param node parent node
     * @return list of child nodes.
     */
    public List<IT24TreeViewNode> getCalledSubroutines(IT24TreeViewNode node) {
        List<IT24TreeViewNode> childNodes = new ArrayList<IT24TreeViewNode>();
        if (!(node instanceof CallsHierarchyNode)) {
            return childNodes;
        }
        T24Subroutine subroutine = ((CallsHierarchyNode) node).getSubroutine();
        IT24FTPClient ftpClient = getFtpClient();
        if (ftpClient == null) {
            return childNodes;
        }
        String fileName = subroutine.getName();
        String remotePath = null;
        String localPath = RemoteFileTransferUtil.getDefaultLocalPath(fileName);
        String[] remoteDirectoryNames = RemoteFileTransferUtil.getHyperLinkDirectories();
        String retrievedPath = null;
        for (String dirName : remoteDirectoryNames) {
            dirName = dirName.trim();
            remotePath = getFullPath(fileName, dirName);
            try {
                retrievedPath = ftpClient.retriveFile(remotePath, localPath);
            } catch (RemoteOperationsException e) {
                retrievedPath = null;
            }
            if (retrievedPath != null) {
                break;
            }
        }
        if (retrievedPath == null) {
            return childNodes;
        }
        IDocument document = getDocument(localPath);
        List<T24Subroutine> calls = getCalls(document);
        for (T24Subroutine call : calls) {
            childNodes.add(new CallsHierarchyNode(call, node));
        }
        return childNodes;
    }

    /**
     * Opens the T24 subroutine from remote site.
     * 
     * @param fileName T24 Subroutine name
     */
    public void openFile(String fileName) {
        String remotePath = null;
        String localPath = RemoteFileTransferUtil.getDefaultLocalPath(fileName);
        String[] remoteDirectoryNames = RemoteFileTransferUtil.getHyperLinkDirectories();
        String retrievedDir = "";
        String retrievedPath = null;
        IT24FTPClient ftpClient = getFtpClient();
        for (String dirName : remoteDirectoryNames) {
            dirName = dirName.trim();
            remotePath = getFullPath(fileName, dirName);
            try {
                retrievedPath = ftpClient.retriveFile(remotePath, localPath);
            } catch (RemoteOperationsException e) {
                retrievedPath = null;
            }
            if (retrievedPath != null) {
                retrievedDir = dirName;
                break;
            }
        }
        if (retrievedPath != null) {
            RemoteFileEditorUtil.openFileWithEditor(getRemoteSite(), getRemotePath(retrievedDir), localPath);
        }else{
            RemoteOperationsLog.error("Unable to open file " + fileName);
        }
    }

    /**
     * Returns the currently used {@link RemoteSite}.
     * 
     * @return {@link RemoteSite} remote site
     */
    public String getCurrentSite() {
        return rsm.getDefaultSiteName();
    }

    /**
     * Returns the currently available {@link RemoteSite}s.
     * 
     * @return list of remote sites.
     */
    public String[] getRemoteSites() {
        return rsm.getRemoteSiteNames();
    }

    private IDocument getDocument(String localPath) {
        IFile file = EditorDocumentUtil.getIFile(localPath);
        FileUtil fu = new FileUtil();
        String fileContents = fu.getFileContents(file);
        IDocument doc = new Document();
        doc.set(fileContents);
        return doc;
    }

    private List<T24Subroutine> getCalls(IDocument document) {
        // TODO A similar logic is used for Variable extraction
        // @ VariablesListBuilder class. Should bere-factored.
        StringUtil su = new StringUtil();
        List<T24Subroutine> calledSubroutines = new ArrayList<T24Subroutine>();
        if (document == null) {
            return calledSubroutines;
        }
        String word = null;
        String previousWord = null;
        int noOfLines = document.getNumberOfLines();
        int lineOffset = 0;
        int lineLength = 0;
        int offset = -1;
        String line = null;
        String lineTrimmed = null;
        try {
            for (int lineNo = 0; lineNo < noOfLines; lineNo++) {
                lineOffset = document.getLineOffset(lineNo);
                lineLength = document.getLineLength(lineNo);
                line = document.get(lineOffset, lineLength);
                if (canSkipLine(line)) {
                    continue;
                }
                lineTrimmed = StringUtil.removeJbcNonwordChar(StringUtil.removeJbcFunction(StringUtil.removeJbcLiteral(StringUtil
                        .removeJbcCommentPart(line))));
                offset = su.getBeginningOfNextWord(lineTrimmed, -1);
                while (offset != -1) {
                    word = su.getWord(lineTrimmed, offset);
                    if (previousWord != null && previousWord.equals("CALL")) {
                        calledSubroutines.add(getSubroutine(trimWord(word), lineNo, line));
                    }
                    previousWord = word;
                    offset = su.getBeginningOfNextWord(lineTrimmed, offset);
                }
                previousWord = null;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return calledSubroutines;
    }

    private T24Subroutine getSubroutine(String subroutineName, int lineNo, String entireLine) {
        return new T24Subroutine(subroutineName, siteName, new CallLineDetail(lineNo, entireLine));
    }

    private String trimWord(String word) {
        return word.trim();
    }

    private boolean canSkipLine(String line) {
        if (StringUtil.isComment(line) || StringUtil.isEmpty(line) || StringUtil.isLabel(line)) {
            return true;
        }
        return false;
    }

    private IT24FTPClient getFtpClient() {
        return rsm.getFTPClient(siteName);
    }

    private RemoteSite getRemoteSite() {
        return rsm.getRemoteSiteFromName(siteName);
    }

    private String getFullPath(String fileName, String serverDir) {
        return getRemotePath(serverDir) + "/" + fileName;
    }

    private String getRemotePath(String dir) {
        RemoteSite site = getRemoteSite();
        return RemoteFileEditorUtil.calculateRemotePath(dir, site);
    }
}
