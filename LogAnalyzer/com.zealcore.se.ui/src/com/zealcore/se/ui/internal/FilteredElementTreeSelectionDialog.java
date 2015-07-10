package com.zealcore.se.ui.internal;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import com.zealcore.se.ui.util.StringMatcher;

public class FilteredElementTreeSelectionDialog extends
        ElementTreeSelectionDialog {

    private int fWidth = 60;

    private int fHeight = 18;

    public FilteredElementTreeSelectionDialog(final Shell parent,
            final ILabelProvider labelProvider,
            final ITreeContentProvider contentProvider) {
        super(parent, labelProvider, contentProvider);
    }

    private Composite superCreateDialogArea(final Composite parent) {
        // create a composite with standard margins and spacing
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        applyDialogFont(composite);
        return composite;
    }

    @Override
    protected Control createDialogArea(final Composite parent) {

        Composite composite = superCreateDialogArea(parent);

        final Text filterText = new Text(composite, SWT.BORDER);
        filterText.setText("Enter filter");
        filterText.setSelection(0, filterText.getText().length());

        filterText.addFocusListener(new FocusAdapter() {
            private boolean first = true;

            @Override
            public void focusGained(final FocusEvent e) {
                if (first) {
                    filterText.setText("");
                    first = false;
                }
            }
        });
        filterText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                filterTree(filterText.getText());
            }
        });

        filterText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_UP) {
                    getTreeViewer().getTree().forceFocus();

                }
            }
        });

        GridData textData = new GridData(GridData.FILL_BOTH);
        filterText.setLayoutData(textData);
        Label messageLabel = createMessageArea(composite);
        TreeViewer treeViewer = createTreeViewer(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(fWidth);
        data.heightHint = convertHeightInCharsToPixels(fHeight);

        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        messageLabel.setEnabled(true);
        treeWidget.setEnabled(true);

        treeViewer.setAutoExpandLevel(initialExpansionLevel);
        treeViewer.expandToLevel(initialExpansionLevel);
        return composite;
    }

    @Override
    protected void updateOKStatus() {
        try {
            super.updateOKStatus();
        } catch (IllegalStateException ex) {}

    }

    private TextFilter txtFilter = new TextFilter();

    private int initialExpansionLevel = 2;

    private void filterTree(final String text) {
        txtFilter.pattern = text;
        getTreeViewer().getTree().setLayoutDeferred(true);
        getTreeViewer().removeFilter(txtFilter);
        getTreeViewer().addFilter(txtFilter);
        getTreeViewer().expandToLevel(initialExpansionLevel);
        getTreeViewer().getTree().setLayoutDeferred(false);
    }

    private static class TextFilter extends ViewerFilter {
        private String pattern = "";

        @Override
        public boolean select(final Viewer viewer, final Object parentElement,
                final Object element) {
            TreeViewer sv = (TreeViewer) viewer;
            ILabelProvider txtProvider = (ILabelProvider) sv.getLabelProvider();
            String txt = txtProvider.getText(element);
            StringMatcher matcher = new StringMatcher(pattern + "*", true,
                    false);
            boolean visible = matcher.match(txt);
            if (visible) {
                return visible;
            }

            ITreeContentProvider cp = (ITreeContentProvider) sv
                    .getContentProvider();
            for (Object object : cp.getChildren(element)) {
                if (select(sv, element, object)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void setInitialExpansionLevel(final int initialExpansionLevel) {
        this.initialExpansionLevel = initialExpansionLevel;
    }
}
