package com.odcgroup.translation.ui.internal.views;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.views.ITranslationInfo;
import com.odcgroup.translation.ui.views.ITranslationKindSelector;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationTable;
import com.odcgroup.translation.ui.views.ITranslationTableProvider;
import com.odcgroup.translation.ui.views.ITranslationText;
import com.odcgroup.translation.ui.views.ITranslationViewer;

/**
 * The Translation Viewer
 * 
 * @author atr
 */
public class TranslationViewer implements ITranslationViewer {
	
	/** The factory */
	private ITranslationTableProvider tableProvider;
	/** The root control */
	private Composite vbox;
	/** */
	private ITranslationInfo information;
	/** This control contains the selector */
	private Composite selectorPanel;
	/** */
	private ITranslationKindSelector selector;
	/**	 */
	private ITranslationTable table;
	/** */
	private IProject project;
	private ITranslationText text;
	private Composite hbox;
	


	/**
	 * @param model
	 * @param parent
	 */
	private void hookTranslationInfo(Composite parent) {
		Composite vbox = new Composite(parent, SWT.NONE);
		vbox.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		vbox.setLayout(layout);
		vbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		information = createTranslationInfo(vbox);
	}
	
	/**
	 * @param model
	 * @param parent
	 */
	private void hookTranslationSelector(Composite parent) {
		selectorPanel = new Composite(parent, SWT.NONE);
		selectorPanel.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		selectorPanel.setLayout(layout);
		selectorPanel.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		selector = createTranslationSelector(selectorPanel);
	}
	
	/**
	 * @param model
	 * @param parent
	 */
	private void hookTranslationTable(Composite parent) {
		Composite vbox = new Composite(parent, SWT.NONE);
		vbox.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		vbox.setLayout(layout);
		vbox.setLayoutData(new GridData(GridData.FILL_BOTH));
		hookTranslationInfo(vbox);
		table = createTranslationTable(vbox);
		text = createTranslationText(vbox);
	}
	
	/**
	 * @param model
	 * @param parent
	 * @param style
	 */
	private void createControls(Composite parent, int style) {
		
		vbox = new Composite(parent, style);
		vbox.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		vbox.setLayout(layout);
		vbox.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
		hbox = new Composite(vbox, SWT.NONE);
		hbox.setBackground(parent.getBackground());
		layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		hbox.setLayout(layout);
		hbox.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		hookTranslationSelector(hbox);
		hookTranslationTable(hbox);
		((TranslationKindSelector)selector).getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				TreeViewer tv = (TreeViewer) event.getSource();
				ITranslationKind kind = (ITranslationKind)((IStructuredSelection)tv.getSelection()).getFirstElement();
            	if(((TranslationKindSelector)selector).getTranslationModel().hasMultiLinesEditor(kind)) {
            		show();
            		hideButtons();
            		hideTable();
            		hbox.layout(false);
            	} else {
            		showButtons();
            		showTable();
            		hide();	
            		hbox.layout(false);
            	}
				
			}
		});
	}
	
	/**
	 * @param model
	 * @param parent
	 * @return TranslationInfo
	 */
	protected TranslationInfo createTranslationInfo(Composite parent) {
		return new TranslationInfo(parent);
	}	
	/**
	 * @param model
	 * @param parent
	 * @return TranslationSelector
	 */
	protected TranslationKindSelector createTranslationSelector(Composite parent) {
		return new TranslationKindSelector(parent);
	}
	

	/**
	 * @param model
	 * @param parent
	 * @return TranslationTable
	 */
	protected ITranslationTable createTranslationTable(Composite parent) {
		return tableProvider.getTranslationTable(project, parent);
	}

	/**
	 * @param model
	 * @param parent
	 * @return TranslationTable
	 */
	protected ITranslationText createTranslationText(Composite parent) {
		return tableProvider.getTranslationText(project, parent);
	}
	
	@Override
	public void hideButtons() {
		if (table != null) {
			table.hideButtons();
		}
	}

	@Override
	public void hideKindSelector() {
		if (selectorPanel != null) {
			selectorPanel.dispose();
			selectorPanel = null;
			vbox.layout();
		}
	}
	
	@Override
	public void hideOrigin() {
		if (table != null) {
			table.hideOrigin();
		}
	}

	@Override
	public void showButtons() {
		if (table != null) {
			table.showButtons();
		}
	}
	
	@Override
	public void showKindSelector() {
		if (null == selectorPanel) {
			hookTranslationSelector(this.vbox);
			vbox.layout();
		}
	}	
	
	@Override
	public void showOrigin() {
		if (table != null) {
			table.showOrigin();
		}
	}

	@Override
	public void setTranslationModel(ITranslationModel model) {
		if (information != null) {
			information.setTranslationModel(model);
		}
		if (selectorPanel != null) {
			selector.setTranslationModel(model);
		}
		if (table != null) {
			table.setTranslationModel(model);
		}
		if (text != null ) {
			text.setTranslationModel(model);
		}
	}
	
	/**
	 * @return Control
	 */
	public final Control getControl() {
		return vbox;
	}
	
	@Override
	public void dispose() {
		
		if (information != null) {
			information.dispose();
		}
		if (selector != null) {
			selector.dispose();
		}
		
		if (table != null) {
			table.dispose();
		}
		
		if (text != null) {
			text.dispose();
		}
	}

	/**
	 * Creates a new TranslationViewer
	 * 
	 * @param model
	 * @param parent
	 * @param style
	 */
	public TranslationViewer(IProject project, Composite parent, int style, ITranslationTableProvider tableProvider) {
		this.tableProvider = tableProvider;
		this.project = project;
		createControls(parent, style);
	}

	/**
	 * Creates a new TranslationViewer
	 * 
	 * @param model
	 * @param parent
	 */
	public TranslationViewer(IProject project, Composite parent, ITranslationTableProvider tableProvider) {
		this(project, parent, SWT.NONE, tableProvider);

	}
      
	/**hide the transaltion kind .
	 * @param translationkind
	 */
	public void hideTranslationKind(final ITranslationKind translationkind){
	    ((TranslationKindSelector)selector).getViewer().addFilter(new ViewerFilter() {
		public boolean select(Viewer viewer, Object parentElement, Object element) {
		    if(((ITranslationKind)element).equals(translationkind)){
			return false;
		    }
		    return true;
		}
	    });
	}

	@Override
	public void show() {
		if(text!=null) {
			text.show();
		}
	}

	@Override
	public void hide() {
		if (text != null) {
			text.hide();
		}
	}

	@Override
	public void showTable() {
		if (table != null) {
			table.showTable();
		}
	}

	@Override
	public void hideTable() {
		if (table != null) {
			table.hideTable();
		}
	}
}
