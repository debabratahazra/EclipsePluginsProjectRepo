package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledFormText;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.document.parser.BatchInfo;
import com.temenos.t24.tools.eclipse.basic.document.parser.BatchesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.parser.TablesWrapper;

public class DocInputDecorator {

    private static int DATA_NUM_COLUMNS = 4;
    private static String[] data_titles = { "Name", "Description", "Type", "Classification" };
    private static int[] data_bounds = { 200, 480, 60, 60 };
    private static int JOB_NUM_COLUMNS = 2;
    private static String[] job_titles = { "Name", "Description" };
    private static int[] job_bounds = { 200, 480 };
    private final static int CHARS_PER_LINE = 80;

    public static TableViewer getDataNodeTables(Composite composite) {
        TableDecorator decorator = new TableDecorator(composite, data_titles, DATA_NUM_COLUMNS, data_bounds);
        return decorator.createNonEditableTableViewer();
    }

    private static TableViewer getBatchTables(Composite composite) {
        TableDecorator decorator = new TableDecorator(composite, job_titles, JOB_NUM_COLUMNS, job_bounds);
        return decorator.createNonEditableTableViewer();
    }

    public static Composite getOverviewComposite(Composite parent, String overViewInfo, String itemName) {
        ScrolledComposite overviewComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        overviewComposite.setBackground(parent.getBackground());
        Composite childComp = new Composite(overviewComposite, SWT.SCROLL_LINE);
        childComp.setBackground(overviewComposite.getBackground());
        childComp.setLayout(new FillLayout());
        StringBuffer overStr = new StringBuffer();
        FormText overviewText = new FormText(childComp, SWT.NONE);
        if (overViewInfo != null) {
            // TODO: Sethu- Is it going to help?
            // overviewText.setParagraphsSeparated(true);
            // overviewText.setWhitespaceNormalized(true);
            overviewText.setForeground(getDescColor(parent));
            overviewText.setImage("image", T24BasicPlugin.getDefault().getImageRegistry().get(T24BasicPlugin.IMG_SAMPLE));
            overviewText.setColor("header", getHeaderColor(parent));
            overviewText.setColor("desc", getDescColor(parent));
            overviewText.setFont("header", JFaceResources.getHeaderFont());
            overviewText.setFont("code", JFaceResources.getTextFont());
            overviewText.setFont("desc", JFaceResources.getTextFont());
            overviewText.setFont("bold", JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
            overviewText.setFont("italic", JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
            overviewText.setBackground(overviewText.getParent().getBackground());
            String overview = addNewLineChar(overViewInfo);
            overStr.append("<form><p><span color=\"header\" font=\"header\">" + itemName + "</span></p>");
            overStr.append("<p>" + overview + "</p></form>");
            overviewText.setText(overStr.toString(), true, false);
            overviewText.pack();
            GridData layoutData = new GridData(SWT.FILL);
            layoutData.widthHint = 1190;
            layoutData.horizontalAlignment = GridData.FILL;
            overviewText.setLayoutData(layoutData);
            childComp.setLayout(new GridLayout());
            childComp.pack();
            childComp.redraw();
        }
        overviewComposite.setContent(childComp);
        overviewComposite.setMinSize(childComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        overviewComposite.setExpandHorizontal(true);
        overviewComposite.setExpandVertical(true);
        return overviewComposite;
    }

    public static Composite getTablesComposite(Composite parent, TablesWrapper tablesWrapper) {
        ScrolledComposite tablesComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        tablesComposite.setBackground(parent.getBackground());
        Composite childComp = new Composite(tablesComposite, SWT.SCROLL_LINE);
        childComp.setBackground(tablesComposite.getBackground());
        childComp.setLayout(new FillLayout());
        ScrolledFormText tablesText = new ScrolledFormText(childComp, true);
        tablesText.getFormText().setColor("header", getHeaderColor(parent));
        tablesText.getFormText().setFont("header", JFaceResources.getHeaderFont());
        tablesText.getFormText().setFont("code", JFaceResources.getTextFont());
        tablesText.setText("<form><p><span color = \"header\" font = \"header\">" + tablesWrapper.getOverview()
                + "</span></p></form>");
        GridData layoutData = new GridData(SWT.FILL);
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.minimumWidth = 100;
        layoutData.horizontalAlignment = GridData.FILL;
        tablesText.setLayoutData(layoutData);
        tablesText.setBackground(childComp.getBackground());
        TableViewer viewer = DocInputDecorator.getDataNodeTables(childComp);
        viewer.setContentProvider(new TableContentProvider());
        viewer.setLabelProvider(new TableLabelProvider());
        viewer.setInput(tablesWrapper.getTables());
        childComp.setLayout(new GridLayout());
        childComp.pack();
        childComp.redraw();
        tablesComposite.setContent(childComp);
        tablesComposite.setMinSize(childComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        tablesComposite.setExpandHorizontal(true);
        tablesComposite.setExpandVertical(true);
        return tablesComposite;
    }

    public static Composite getBatchComposite(Composite parent, BatchesWrapper batchesWrapper) {
        ScrolledComposite scrollableComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        scrollableComposite.setBackground(parent.getBackground());
        Composite childComp = new Composite(scrollableComposite, SWT.SCROLL_LINE);
        childComp.setBackground(scrollableComposite.getBackground());
        childComp.setLayout(new FillLayout());
        ScrolledFormText batchDetailsText;
        for (BatchInfo batchInfo : batchesWrapper.getBatches()) {
            String batchName = batchInfo.getBatchName();
            batchDetailsText = new ScrolledFormText(childComp, true);
            batchDetailsText.getFormText().setColor("header", getHeaderColor(parent));
            batchDetailsText.getFormText().setFont("header", JFaceResources.getHeaderFont());
            batchDetailsText.getFormText().setColor("desc", getDescColor(parent));
            batchDetailsText.getFormText().setFont("desc", JFaceResources.getTextFont());
            batchDetailsText.setText("<form><p><span color = \"header\" font = \"header\">" + batchName + "</span></p></form>");
            batchDetailsText.setBackground(childComp.getBackground());
            batchDetailsText.getFormText().pack();
            TableViewer viewer = getBatchTables(childComp);
            viewer.setContentProvider(new JobTableContentProvider());
            viewer.setLabelProvider(new JobTableLabelProvider());
            viewer.setInput(batchInfo.getJobs());
            childComp.setLayout(new GridLayout());
            childComp.pack();
            childComp.redraw();
        }
        scrollableComposite.setContent(childComp);
        scrollableComposite.setMinSize(childComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        scrollableComposite.setExpandHorizontal(true);
        scrollableComposite.setExpandVertical(true);
        return scrollableComposite;
    }

    private static Color getHeaderColor(Composite parent) {
        return parent.getDisplay().getSystemColor(SWT.COLOR_DARK_RED);
    }

    private static Color getDescColor(Composite parent) {
        return parent.getDisplay().getSystemColor(SWT.COLOR_BLUE);
    }

    public static Composite getEmptyComposite(Composite parent) {
        Composite childComp = new Composite(parent, SWT.NONE);
        return childComp;
    }

    public static String addNewLineChar(String str) {
        str = str.replaceAll("\n", "<br/>");
        str = str.replaceAll("<li", "</temp><li");
        str = str.replaceAll("</li>", "</li><temp>");
        str = str.replaceAll("<p>", "</temp><p>");
        str = str.replaceAll("</p>", "</p><temp>");
        // temporary variable - so that <p></p> doesnt get replaced twice
        str = str.replaceAll("<temp>", "<p>");
        str = str.replaceAll("</temp>", "</p>");
        str = str.replaceAll("&", "&amp;");
        // TODO: Raga - include space in formatting
        str = str.replaceAll(":", ": ");
        return str;
    }

    public static String wrapText(String original) {
        StringBuilder oldText = new StringBuilder(original);
        String currentLine = original;
        StringBuilder newText = new StringBuilder();
        int startPos = 0;
        int endPos = CHARS_PER_LINE;
        while (true) {
            if (oldText.length() <= CHARS_PER_LINE) {
                newText.append(oldText.toString());
                break;
            }
            currentLine = oldText.substring(startPos, endPos);
            int lastIndex = currentLine.lastIndexOf(' ');
            if (lastIndex < 0) {
                lastIndex = endPos;
            }
            newText.append(oldText.substring(startPos, lastIndex));
            newText.append("\n");
            oldText.replace(startPos, lastIndex + 1, "");
        }
        return newText.toString();
    }
}
