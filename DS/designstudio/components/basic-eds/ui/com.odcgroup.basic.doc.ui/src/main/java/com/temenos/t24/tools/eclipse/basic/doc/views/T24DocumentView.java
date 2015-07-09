package com.temenos.t24.tools.eclipse.basic.doc.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import com.temenos.t24.tools.eclipse.basic.document.DocInput;
import com.temenos.t24.tools.eclipse.basic.document.HTMLDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.IDocInput;
import com.temenos.t24.tools.eclipse.basic.document.ViewSection;
import com.temenos.t24.tools.eclipse.basic.document.WordDocumentSupplier;
import com.temenos.t24.tools.eclipse.basic.document.parser.BatchesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.parser.TablesWrapper;
import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.views.document.DocInputDecorator;
import com.temenos.t24.tools.eclipse.basic.views.document.DocumentViewConstants;
import com.temenos.t24.tools.eclipse.basic.views.document.PresentationHelper;

public class T24DocumentView extends ViewPart {

    public static final String VIEW_ID = "com.temenos.t24.tools.eclipse.basic.T24DocumentView";
    private CTabFolder folder;
    private CTabItem overviewItem;
    private CTabItem tablesItem;
    private CTabItem cobItem;
    private FormToolkit toolkit;
    private String itemName;
    private String overViewInfo;
    private TablesWrapper tablesInfo;
    private BatchesWrapper COBInfo;
    private Composite overviewComposite;
    private Composite batchComposite;
    private Composite tableComposite;
    // action to open the word document
    private Action actionWordDocLink;
    private ISelectionListener listener = new ISelectionListener() {

        public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
            // condition not available in original, added by bharath raja
            // because of this selection listener, if we navigate through
            // views(eclipse/our customized)
            // is error prone. this was happened since
            // T24ComponentViewerController's showView() has changed
            // from showView() to findView()
            if (((org.eclipse.ui.part.WorkbenchPart) sourcepart).getPartName().equalsIgnoreCase("T24 Doc"))
                showSelection(sourcepart, selection);
        }
    };

    public void createPartControl(Composite parent) {
        createActions();
        createToolBar();
        toolkit = new FormToolkit(parent.getDisplay());
        parent.setLayout(new FillLayout());
        parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        folder = new CTabFolder(parent, SWT.TOP);
        folder.setBackground(parent.getBackground());
        overviewItem = new CTabItem(folder, SWT.NONE);
        overviewItem.setText(DocumentViewConstants.overviewTab);
        Label overviewLbl = toolkit.createLabel(folder, DocumentViewConstants.overviewInfo);
        overviewItem.setControl(overviewLbl);
        tablesItem = new CTabItem(folder, SWT.NONE);
        tablesItem.setText(DocumentViewConstants.tablesTab);
        Label tablesLbl = toolkit.createLabel(folder, DocumentViewConstants.overviewInfo);
        tablesItem.setControl(tablesLbl);
        cobItem = new CTabItem(folder, SWT.NONE);
        cobItem.setText(DocumentViewConstants.cobTab);
        Label cobLbl = toolkit.createLabel(folder, DocumentViewConstants.COB_NO_AVAILABALE);
        cobItem.setControl(cobLbl);
        setViewsText();
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
    }

    public void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;
            showArray(ss.toArray());
        }
        if (selection instanceof ITextSelection) {
            IDocument document = EditorDocumentUtil.getActiveDocument();
            String comment = DocumentViewerUtil.getComment(document);
            showText(new DocInput("{item_name}", comment, "", "", ViewSection.OVERVIEW));
        }
    }

    private void showArray(Object[] subroutines) {
        if (subroutines != null && subroutines.length == 1) {
            Object obj = subroutines[0];
            if (obj instanceof IFile) {
                IFile file = ((IFile) obj);
                String doc = DocumentViewerUtil.getDocument(file);
                showText(new DocInput(file.getName(), doc, "", "", ViewSection.OVERVIEW));
            }
        }
    }

    public void showText(IDocInput docInput) {
        if (docInput != null) {
            itemName = docInput.getItemName();
            overViewInfo = PresentationHelper.prepareOverview(docInput.getOverviewInput());
            tablesInfo = docInput.getTableInput();
            COBInfo = docInput.getCobInput();
            if (docInput.activeSection().equals(ViewSection.OVERVIEW)) {
                folder.setSelection(overviewItem);
            }
            if (docInput.activeSection().equals(ViewSection.TABLES)) {
                folder.setSelection(tablesItem);
            }
            if (docInput.activeSection().equals(ViewSection.BATCHES)) {
                folder.setSelection(cobItem);
            }
            setViewsText();
        }
    }

    private void setViewsText() {
        setOverviewInfo();
        setTablesInfo();
        setCOBInfo();
    }

    private void setOverviewInfo() {
        if (overViewInfo != null) {
            if (overviewComposite != null) {
                overviewComposite.dispose();
            }
            overviewComposite = DocInputDecorator.getOverviewComposite(folder, overViewInfo, itemName);
            overviewItem.setControl(overviewComposite);
        } else {
            if (overviewComposite != null) {
                overviewComposite.dispose();
            }
        }
    }

    private void setTablesInfo() {
        if (tablesInfo != null) {
            if (tableComposite != null) {
                tableComposite.dispose();
            }
            tableComposite = DocInputDecorator.getTablesComposite(folder, tablesInfo);
            tablesItem.setControl(tableComposite);
        }
        // if the tablesInfo is null - on load and other cases where there is no
        // wrapper info
        else {
            if (tableComposite != null) {
                tableComposite.dispose();
            }
        }
    }

    private void setCOBInfo() {
        // if the cobinfo has some info but the batches are empty - still fall
        // in this loop
        if (COBInfo != null) {
            if (batchComposite != null) {
                batchComposite.dispose();
            }
            batchComposite = DocInputDecorator.getBatchComposite(folder, COBInfo);
            cobItem.setControl(batchComposite);
        }
        // if the cobinfo is null - on load and other cases where there is no
        // wrapper info
        else {
            if (batchComposite != null) {
                batchComposite.dispose();
            }
        }
    }

    public void setFocus() {
        if (folder != null) {
            folder.setFocus();
        }
    }

    public void dispose() {
        super.dispose();
    }

    /**
     * Add the word document link button on the top of the view
     */
    private void createToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(actionWordDocLink);
    }

    /**
     * load icon for word document link on the top of the view and load the
     * document
     */
    private void createActions() {
        ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(EclipseUtil.getImage("/icons/web.gif"));
        actionWordDocLink = new Action("Click to Open Web Page Document", imageDescriptor) {

            public void run() {
                HTMLDocumentSupplier htmlSupplier = new HTMLDocumentSupplier();
                boolean isHTMLAvailable = htmlSupplier.doOpenHTMLDoc();
                if (!isHTMLAvailable) {
                    WordDocumentSupplier wordSupplier = new WordDocumentSupplier();
                    wordSupplier.doOpenWordFile();
                }
            }
        };
    }
}
