package com.odcgroup.translation.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.editor.controls.ITranslationFilterControl;
import com.odcgroup.translation.ui.editor.controls.TranslationTab;
import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.properties.TranslationPropertySheetPage;
import com.odcgroup.translation.ui.internal.TranslationTableManager;
import com.odcgroup.translation.ui.navigator.TranslationEditorInput;
import com.odcgroup.translation.ui.views.ITranslationTableProvider;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 *
 * @author pkk
 *
 */
public class TranslationEditor extends EditorPart implements ITabbedPropertySheetPageContributor, ISelectionProvider {
	

	/** */
	private Text searchString;

	/** */
	private Font italicFont;
	/** */
	private Font tabFolderFont;
	/** */
	private IOfsProject ofsProject;

	/** */
	private List<ITranslationCollectorProvider> translationCollectors;
	/** */
	private CTabFolder tabFolder;
	/** */
	private Button filterEmptyChk;
	/** */
	private Button filterDepChk;
	/** */
	private List<Button> additionalFilters = new ArrayList<Button>();

	/** */	
	private ISelection selection = null;
	/** */
	private ITranslationFilterControl filtercontrol = null;
	/** */
	private List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);	
		getSite().setSelectionProvider(this);
		
		if (input instanceof TranslationEditorInput) {
			ofsProject = ((TranslationEditorInput) input).getOfsProject();
			setPartName(ofsProject.getName()+" "+getPartName());
		}
		translationCollectors = TranslationUICore.getTranslationCollectors();
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {

		// The table viewer is contained in a 1-column grid layout.
		Composite composite = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);
		int style = GridData.VERTICAL_ALIGN_BEGINNING | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL;
		composite.setLayoutData(style);
		composite.setBackground(parent.getBackground());
		composite.setFont(parent.getFont());
		
		// search group
		createSearchControls(composite);
		// create translation tabs
		createTranslationTabs(composite);	

        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IOfsHelpContextIds.WORKBENCH);
        
        searchString.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {			
				updateFilters();
			}
        });
        
		filterEmptyChk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateFilters();
			}			
		});
		
		filterDepChk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateFilters();
			}			
		});
		
	}
	
	/**
	 * 
	 */
	protected void updateFilters() {
		if (filtercontrol != null) {
			filtercontrol.updateFilters(getTabFolder(), filterEmptyChk.getSelection(), 
					filterDepChk.getSelection(), searchString.getText(), additionalFilters);
		} else {
			TranslationTab tab = (TranslationTab) tabFolder.getSelection();
			tab.filterTranslations(filterEmptyChk.getSelection(), filterDepChk.getSelection(), searchString.getText());
		}
	}
	
	/**
	 * @param composite
	 */
	private void createTranslationTabs(Composite composite) {
		Composite container = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gd);

        tabFolder = new CTabFolder(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
		tabFolderFont = new Font(Display.getCurrent(), "Arial", 8, SWT.BOLD);
		tabFolder.setFont(tabFolderFont);
		tabFolder.setBackground(container.getBackground());
		
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateFilters();
			}
		});
		
		TranslationTab tab = null;
		for (ITranslationCollectorProvider collectionProvider : translationCollectors) {
			tab = createTranslationTab(collectionProvider);
			if (collectionProvider.isDefaultActivated()) {
				tabFolder.setSelection(tab);
				updateFilters();
			}
		}
		
		createAllTranslationTab(translationCollectors);
	}
	
	/**
	 * @return the ofsProject
	 */
	public IOfsProject getOfsProject() {
		return ofsProject;
	}
	
	/**
	 * @param collectionProvider
	 * @return
	 */
	protected TranslationTab createTranslationTab(ITranslationCollectorProvider collectionProvider) {
		if (filtercontrol != null) {
			return filtercontrol.createTranslationTab(collectionProvider, getSite(), ofsProject, tabFolder);
		} else {
			return new TranslationTab(collectionProvider, getSite(), ofsProject, tabFolder, SWT.FILL);
		}
	}
	
	/**
	 * @param providers
	 * @return
	 */
	protected TranslationTab createAllTranslationTab(List<ITranslationCollectorProvider> providers) {
		if (filtercontrol != null) {
			return filtercontrol.createTranslationTab(providers, getSite(), ofsProject, tabFolder);
		} else {
			return new TranslationTab(providers, getSite(), ofsProject, tabFolder, SWT.FILL);
		}
	}
	
	/**
	 * @param composite
	 */
	private void createSearchControls(Composite composite) {
		Composite searchComp = new Composite(composite, SWT.None);
		GridLayout layout = new GridLayout(2, false);
		searchComp.setLayout(layout);
		searchComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		

		createSearchGroup(searchComp);
		createFilterControls(searchComp);
	}
	
	/**
	 * @param composite
	 */
	protected Composite createFilterControls(Composite composite) {
		
		Composite filterGroup = new Composite(composite, SWT.None);
		GridLayout layout = new GridLayout(1, false);
		filterGroup.setLayout(layout);
		filterGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));		

		filterEmptyChk = new Button(filterGroup, SWT.CHECK);
		GridData data = new GridData();
		data.horizontalIndent= 35;
		filterEmptyChk.setLayoutData(data);
		filterEmptyChk.setText(" Filter empty translations ");
		filterEmptyChk.setBackground(filterGroup.getBackground());
		filterEmptyChk.setSelection(true);
		
		filterDepChk = new Button(filterGroup, SWT.CHECK);data = new GridData();
		data.horizontalIndent= 35;
		filterDepChk.setLayoutData(data);
		filterDepChk.setText(" Filter dependent project translations ");
		filterDepChk.setBackground(filterGroup.getBackground());
		filterDepChk.setSelection(true);
		
		ITranslationTableProvider tableProvider = new TranslationTableManager(ofsProject.getProject()).getTranslationTableProvider();
		filtercontrol = tableProvider.getFilterControl();
		if (filtercontrol != null) {
			additionalFilters.addAll(filtercontrol.createAdditionalFilterControls(ofsProject, filterGroup));
		}
		for (Button btn : additionalFilters) {
			btn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					updateFilters();
				}			
			});
		}
		return filterGroup;
		
	}
	
	/**
	 * @param composite
	 */
	private void createSearchGroup(Composite composite) {
		
		Composite searchgroup = new Composite(composite, SWT.None);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		searchgroup.setLayout(layout);
		searchgroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		CLabel label = new CLabel(searchgroup, SWT.LEFT | SWT.TOP);
		label.setBackground(composite.getBackground());
		label.setFont(composite.getFont());
		label.setText("Search string");
		GridData data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data.verticalIndent = 3;
		label.setLayoutData(data);
	
		Composite searchcomp = new Composite(searchgroup, SWT.None);
		layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		searchcomp.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		searchcomp.setLayoutData(data);		
		
		searchString = new Text(searchcomp, SWT.BORDER);
		data = new GridData();		
		data.widthHint = 300;
		searchString.setLayoutData(data);
		
		CLabel helpLabel =  new CLabel(searchcomp, SWT.LEFT);
		helpLabel.setBackground(composite.getBackground());
		FontData fontData = composite.getFont().getFontData()[0];
		italicFont = new Font(composite.getDisplay(), new FontData(fontData.getName(), fontData
			    .getHeight(), SWT.ITALIC));
		helpLabel.setFont(italicFont);
		helpLabel.setText("(* = any string)");
		
		CLabel tipLabel =  new CLabel(searchcomp, SWT.LEFT);
		tipLabel.setBackground(composite.getBackground());
		tipLabel.setFont(italicFont);
		tipLabel.setText("Search done on all languages for selected tab");
	}	

	@Override
	public void dispose() {
		super.dispose();
		if (italicFont != null) {
			italicFont.dispose();
		}
		if (tabFolderFont != null) {
			tabFolderFont.dispose();
		}
		if (!tabFolder.isDisposed()) {
			tabFolder.dispose();
		}
	}

	/**
	 * @return the tabFolder
	 */
	public CTabFolder getTabFolder() {
		return tabFolder;
	}
	
	/**
	 * @return the translationCollectors
	 */
	public List<ITranslationCollectorProvider> getTranslationCollectors() {
		return translationCollectors;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {		
	}

	@Override
	public void doSaveAs() {		
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {		
		tabFolder.setFocus();
	}

	@Override
	public String getContributorId() {
		return getSite().getId();
	} 
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            return new TranslationPropertySheetPage(this);
        }
        return super.getAdapter(adapter);
    }

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		if (getSite().getWorkbenchWindow().getActivePage().getActiveEditor() == this) {
			if (selection != null)
				return selection;
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {	
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		if (getSite().getWorkbenchWindow().getActivePage().getActiveEditor() == this) {
			this.selection = selection;	
			for (ISelectionChangedListener listener : selectionChangedListeners) {
	            listener.selectionChanged(new SelectionChangedEvent(this, selection));			
			}
		}
	}

}
