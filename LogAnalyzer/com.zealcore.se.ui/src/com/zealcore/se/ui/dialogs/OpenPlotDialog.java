/**
 * 
 */
package com.zealcore.se.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.se.core.ISearchAdapter;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.ui.internal.assertions.IModifyListener;
import com.zealcore.se.ui.internal.assertions.ModifyEvent;
import com.zealcore.se.ui.search.SearchFilterInput;
import com.zealcore.se.ui.search.UISearchQuery;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;

public class OpenPlotDialog extends Dialog {

    public class PlotComposite extends Composite {
    	public PlotComposite(final Composite parent, final int style) {
			super(parent, style);
		}

		@Override
		public void setLayoutData(final Object layoutData) {
			if (getLayoutData() == null) {
				super.setLayoutData(layoutData);
			}
		}
	}

	private final class PlotTypeListener extends SelectionAdapter {
        private final PlotType type;

        private PlotTypeListener(final PlotType type) {
            this.type = type;
        }

        @Override
        public void widgetSelected(final SelectionEvent e) {
            setPlotType(type);
        }
    }

    private SearchFilterInput filterDialog;

    private ISearchAdapter initialSearchAdapter;

    private static ISearchAdapter lastSearchAdapter;

    private PlotType plotType;
    
    private static final int PLOT_DIALOG_WIDTH = 540;
    
    private static final int PLOT_DIALOG_HEIGHT = 340;

    public OpenPlotDialog(final Shell parentShell) {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MAX);
    }

    public OpenPlotDialog(final Shell parentShell,
            final SearchAdapter preDefSearchAdapter) {
        super(parentShell);
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MAX);
        initialSearchAdapter = preDefSearchAdapter;
    }

    @Override
    protected Control createDialogArea(final Composite parent) {

        getShell().setText("Open Plot");
        getShell().setToolTipText("");

        filterDialog = new SearchFilterInput(SearchFilterInput.PLOTTABLE_TYPES);

        if (initialSearchAdapter != null) {
            filterDialog.setInitialSearchAdapter(initialSearchAdapter);
        } else if (OpenPlotDialog.lastSearchAdapter != null) {
            filterDialog
                    .setInitialSearchAdapter(OpenPlotDialog.lastSearchAdapter);
        }

        Composite composite = new PlotComposite(parent, SWT.NONE);
        final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        composite.setLayoutData(gridData);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.marginBottom = 0;
        gridLayout.marginTop = 0;
        gridLayout.marginLeft = 0;
        gridLayout.marginRight = 0;
        composite.setLayout(gridLayout);
        final ScrolledComposite scrolledComposite = new ScrolledComposite(
                composite, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setLayout(gridLayout);
        scrolledComposite.setLayoutData(new GridData(GridData.FILL,
                GridData.FILL, true, true));
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setMinSize(450, 320);
        
        final Composite container = new Composite(scrolledComposite, SWT.NULL);
        container.setLayout(new FillLayout(SWT.VERTICAL));
        container.setLayout(new GridLayout(2,
                false));

        filterDialog.createContents(container);

        final Composite radioBar = new Composite(container, SWT.NULL);
        radioBar.setLayout(new RowLayout());
        final Button scatterPlot = new Button(radioBar, SWT.RADIO);
        scatterPlot.addSelectionListener(new PlotTypeListener(PlotType.PLOT));
        scatterPlot.setText("Plot");
        final Button linePlot = new Button(radioBar, SWT.RADIO);
        linePlot.addSelectionListener(new PlotTypeListener(PlotType.LINE));
        linePlot.setText("Line Plot");
        linePlot.setSelection(true);
        setPlotType(PlotType.LINE);
        
        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(data);

        /**
         * Check inital settings and set plot type
         */
        if (filterDialog.isOk()) {
            plotType = getPlotType(filterDialog.getSelection(), scatterPlot
                    .getSelection());
            boolean chooseTypeEnabled = true;
            if (plotType == PlotType.BAR) {
                chooseTypeEnabled = false;
            }
            scatterPlot.setEnabled(chooseTypeEnabled);
            linePlot.setEnabled(chooseTypeEnabled);
        }

        filterDialog.addModifyListener(new IModifyListener() {

            public void componentModified(final ModifyEvent event) {
                getShell().layout(true);
                final boolean ok = event.getErrorMessage() == null;
                final Button button = getButton(IDialogConstants.OK_ID);
                if (button != null) {
                    button.setEnabled(ok);
                    if (filterDialog.isOk()) {
                        plotType = getPlotType(filterDialog.getSelection(),
                                scatterPlot.getSelection());
                        boolean chooseTypeEnabled = true;
                        if (plotType == PlotType.BAR) {
                            chooseTypeEnabled = false;
                        }
                        scatterPlot.setEnabled(chooseTypeEnabled);
                        linePlot.setEnabled(chooseTypeEnabled);
                    }
                }
            }
        });
        scrolledComposite.setContent(container);
        return container;
    }

    private boolean isArtifact(final IType selection) {
        return selection.isA(ReflectiveType.valueOf(IArtifact.class));
    }

    private PlotType getPlotType(final IType selection, final boolean isScatter) {
        if (isArtifact(selection)) {
            plotType = PlotType.BAR;
        } else {
            if (isScatter) {
                plotType = PlotType.PLOT;
            } else {
                plotType = PlotType.LINE;
            }
        }
        return plotType;
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Control control = super.createContents(parent);
        getShell().setSize(PLOT_DIALOG_WIDTH, PLOT_DIALOG_HEIGHT);
        getShell().layout(true);
        return control;
    }
    
    @Override
    protected Control createButtonBar(Composite parent) {
    	final Control control = super.createButtonBar(parent);
        getButton(IDialogConstants.OK_ID).setEnabled(true);
        parent.layout(true);
        return control;
    }

    void setPlotType(final PlotType plot) {
        this.plotType = plot;
    }

    @Override
    protected void okPressed() {
        OpenPlotDialog.lastSearchAdapter = filterDialog.createAdapter();
        initialSearchAdapter = null;
        super.okPressed();
    }

    public PlotType getPlotType() {
        return plotType;
    }

    public ISearchAdapter getAdapter() {
        return filterDialog.getAdapter();
    }
    
    
    public UISearchQuery getResult(final Logset connection) {
        return new UISearchQuery(connection, filterDialog.getAdapter());
    }
}
