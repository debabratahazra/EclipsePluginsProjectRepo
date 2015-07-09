package com.odcgroup.translation.ui.editor.controls;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.deferred.DeferredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.handlers.IHandlerService;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.editor.model.IMultiTranslationTable;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.translation.ui.editor.model.ITranslationTableModel;
import com.odcgroup.translation.ui.editor.model.TranslationTableItem;
import com.odcgroup.translation.ui.editor.model.TranslationTableModel;
import com.odcgroup.translation.ui.internal.TranslationTableManager;
import com.odcgroup.translation.ui.views.ITranslationTableProvider;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 *
 * @author pkk
 *
 */
public class TranslationTab extends CTabItem {
	
	private ITranslationCollectorProvider collectorProvider;
	private List<ITranslationCollectorProvider> allProviders = new ArrayList<ITranslationCollectorProvider>();
	private ITranslationPreferences preferences;
	private IWorkbenchPartSite site;
	private IOfsProject ofsProject;
	protected IMultiTranslationTable translationTable;
	private ITranslationTableModel tableModel = null;	
	protected TranslationItemFilter filter;
	
	protected boolean dependentsLoaded = false;
	private ITranslationManager translationManager = null;
	
	private Button editButton;
	
	/**
	 * @param allProviders
	 * @param site
	 * @param ofsProject
	 * @param parent
	 * @param style
	 */
	public TranslationTab(List<ITranslationCollectorProvider> allProviders, IWorkbenchPartSite site, IOfsProject ofsProject, CTabFolder parent, int style) {
		super(parent, style);
		this.allProviders.addAll(allProviders);
		IProject project = ofsProject.getProject();
		translationManager = TranslationCore.getTranslationManager(project);
		preferences = translationManager.getPreferences();
		this.site = site;
		this.ofsProject = ofsProject;
		setText("All");
		setControl(createTabControl(parent, style));
		List<Locale> locales = getLocales();
		filter = new TranslationItemFilter(project, locales);
	}

	/**
	 * @param collectorProvider
	 * @param site
	 * @param ofsProject
	 * @param parent
	 * @param style
	 */
	public TranslationTab(ITranslationCollectorProvider collectorProvider, IWorkbenchPartSite site, IOfsProject ofsProject, CTabFolder parent, int style) {
		super(parent, style);
		this.collectorProvider = collectorProvider;
		IProject project = ofsProject.getProject();
		translationManager = TranslationCore.getTranslationManager(project);
		preferences = translationManager.getPreferences();
		this.site = site;
		this.ofsProject = ofsProject;
		setText(collectorProvider.getDisplayName());
		setControl(createTabControl(parent, style));
		List<Locale> locales = getLocales();
		filter = new TranslationItemFilter(project, locales);
	}
	
	/**
	 * @param parent
	 * @param style
	 */
	protected Control createTabControl(CTabFolder parent, int style) {	
		
		Composite tab = new Composite(parent, SWT.FILL);
		GridLayout layout = new GridLayout(2, false);
		tab.setLayout(layout);
		tab.setLayoutData(new GridData(GridData.FILL_BOTH));	
		IProject project = ofsProject.getProject();
		ITranslationTableProvider tableProvider = new TranslationTableManager(project).getTranslationTableProvider();
		translationTable = tableProvider.getMultiTranslationTable();
		if (collectorProvider != null) {
			translationTable.setModelLabelProvider(collectorProvider.getModelLabelProvider());
		}
		translationTable.createControls(tab, preferences);
		
		createButtonControls(tab);		
		
		final TableViewer tableViewer = translationTable.getTableViewer();
		
		// selection listener
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem item = (TableItem) e.item;
				TranslationTableItem model = (TranslationTableItem) item.getData();
				if (model != null) {
					URI uri = URI.createURI(model.getResourceURI());
					updateButtons(uri.fileExtension());
					getSite().getSelectionProvider().setSelection(new StructuredSelection(model));
				}
				
			}			
		});
		
		// double-click listener
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				openModel();				
			}
		});
        return tab;
	}
	
	/**
	 * 
	 */
	private void updateButtons(String extension) {
		editButton.setEnabled(getOwnerSelector(extension) != null);
	}
	
	/**
	 * @return the translationTable
	 */
	public IMultiTranslationTable getTranslationTable() {
		return translationTable;
	}
	
	/**
	 * @param body
	 */
	private void createButtonControls(Composite body) {
		Composite buttonscomp = new Composite(body, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttonscomp.setLayout(layout);
		buttonscomp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		/*
		Button searchButton = new Button(buttonscomp, SWT.NONE);
		GridData data = new GridData();
		data.widthHint = 120;
		searchButton.setText("Search in models");
		searchButton.setLayoutData(data);
		
		searchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openSearchDialog();
			}
		});
		*/
		
		editButton = new Button(buttonscomp, SWT.NONE);
		editButton.setText("Edit Source Model");
		GridData data = new GridData();
		editButton.setLayoutData(data);
		editButton.setVisible(true);  // not yet fully implemented
		editButton.setEnabled(false);
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openModel();
			}
		});
	}
	
	/**
	 * @throws CoreException
	 */
	@SuppressWarnings("unused")
	private void openSearchDialog() {
		IStructuredSelection selection = (IStructuredSelection) translationTable.getTableViewer().getSelection();
		if (!selection.isEmpty()) {
			TranslationTableItem model = (TranslationTableItem) selection.getFirstElement();
			Clipboard clipboard= new Clipboard(getSite().getShell().getDisplay());
			try {
				
				clipboard.setContents(new String[] {model.getText() },	new Transfer[] { TextTransfer.getInstance() });	
			} finally {
				clipboard.dispose();
			}
		}
		IHandlerService handlerService = (IHandlerService) getSite().getWorkbenchWindow().getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.eclipse.search.ui.openSearchDialog", null);
		} catch (Exception e) {			
			TranslationUICore.getDefault().logError("Unable to open Search Dialog", e);
		}
		
	}
	
	/**
	 * open the editor associated with the owner of the translation
	 */
	private void openModel() {
		IStructuredSelection selection = (IStructuredSelection) translationTable.getTableViewer().getSelection();
		if (!selection.isEmpty()) {
			TranslationTableItem model = (TranslationTableItem)selection.getFirstElement();
			URI uri = URI.createURI(model.getResourceURI());
			try {
				IOfsModelResource mResource = ofsProject.getOfsModelResource(uri);
				if (mResource != null) {
					IEditorPart part = OfsEditorUtil.openEditor(mResource);
					if (part != null) {
						ITranslationOwnerSelector selector = getOwnerSelector(uri.fileExtension());
						if (selector != null) {
//							String ID = model.getResourceFragment();
//							if (StringUtils.isEmpty(ID)) {
								selector.select(part, model.getTranslation());	
//							} else {
								//selector.select(part, ID);	
//							}
						}
					}
				}
			} catch (ModelNotFoundException e) {
				String errorMsg = "Unable to Open Editor for resource "+uri.toString();
				TranslationUICore.getDefault().logError(errorMsg, e);
			}
		}
	}
	
	/**
	 * @return
	 */
	private ITranslationOwnerSelector getOwnerSelector(String extension) {
		if (collectorProvider != null) {
			return collectorProvider.getTranslationOwnerSelector();
		} else if(!allProviders.isEmpty()) {
			for (ITranslationCollectorProvider provider : allProviders) {
				String[] extensions = provider.getModelExtensions();
				for (String string : extensions) {
					if (extension.equals(string)) {
						return provider.getTranslationOwnerSelector();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @return the site
	 */
	public IWorkbenchPartSite getSite() {
		return site;
	}

	/**
	 * 
	 */
	public void setInput() {		
		getTranslationTableModel();		
		translationTable.setTranslations(tableModel);
	}
	
	/**
	 * 
	 */
	protected void getTranslationTableModel() {
		if (tableModel == null) {
			List<ITranslation> translations;
			try {
				translations = loadTranslations(IOfsProject.SCOPE_PROJECT);
				tableModel = new TranslationTableModel(translationManager.getPreferences(), translations);
			} catch (TranslationLoadException e) {
				showErrorDialog(e);
			}
		}
	}
	
	/**
	 * @param exception
	 */
	protected void showErrorDialog(TranslationLoadException ex) {
		String message = "Unable to load translations!!!";
		ErrorDialog.openError(getSite().getShell(), "Translations", message, ex.getStatus(), IStatus.ERROR);
	}
	
	/**
	 * @param scope
	 * @return
	 */
	protected String getScopeType(int scope) {
		String type = "";
		if (scope == IOfsProject.SCOPE_DEPENDENCY) {
			return "dependency";
		} 
		return type;
	}
	
	/**
	 * @param scope
	 */
	protected List<ITranslation> loadTranslations(final int scope) throws TranslationLoadException {
		final List<ITranslation> translations = new ArrayList<ITranslation>();
		if (allProviders.isEmpty() && collectorProvider != null) {  
			final ITranslationCollector collector = collectorProvider.getTranslationCollector(ofsProject);
			if (collector == null) {
				return translations;
			}			
			IRunnableWithProgress operation = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					String modelName = collectorProvider.getDisplayName();
					monitor.setTaskName("loading translations for "+modelName+" "+getScopeType(scope)+" models ....");
					monitor.beginTask("Loading "+getScopeType(scope)+" "+modelName+" translations ...", 1);
					try {
						translations.addAll(collector.collectTranslations(scope));
					} catch (CoreException e) {
						throw new InvocationTargetException(e, e.getLocalizedMessage());
					}
					monitor.worked(1);					
				}
			};
			try {
				new ProgressMonitorDialog(site.getShell()).run(false, false, operation);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID, e.getLocalizedMessage(), e);
				throw new TranslationLoadException(status);
			}
		} else if (!allProviders.isEmpty()) {
			// all model translations
			final List<ITranslationCollector> collectors = new ArrayList<ITranslationCollector>();
			for (ITranslationCollectorProvider provider : allProviders) {
				collectors.add(provider.getTranslationCollector(ofsProject));
			}
			IRunnableWithProgress operation = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.setTaskName("loading translations for all models ....");
					monitor.beginTask("Loading all "+getScopeType(scope)+"  translations ...", 1);
					try {
						for (ITranslationCollector collector : collectors) {
							translations.addAll(collector.collectTranslations(scope));
						}
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
					monitor.worked(1);					
				}
			};
			try {
				new ProgressMonitorDialog(site.getShell()).run(false, false, operation);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, TranslationUICore.PLUGIN_ID, e.getLocalizedMessage(), e);
				throw new TranslationLoadException(status);
			}
		}
		return translations;
	}
	
	/**
	 * @param filterEmpty
	 * @param filterDep
	 */
	public void filterTranslations(boolean filterEmpty, boolean filterDep, String search) {
		if (getTableModel() == null) {
			setInput();
		}
		if (filter != null) {
			filter.toggleFilterEmpty(filterEmpty);
			filter.toggleFilterDependents(filterDep);
			if (!filterDep && !dependentsLoaded) {
				List<ITranslation> deps;
				try {
					deps = loadTranslations(IOfsProject.SCOPE_DEPENDENCY);
					tableModel.addTranslations(deps);
					dependentsLoaded = true;
				} catch (TranslationLoadException e) {
					showErrorDialog(e);
				}
			}
			filter.setSearch(search);
		}
		DeferredContentProvider contentProvider = (DeferredContentProvider) translationTable.getTableViewer().getContentProvider();
		contentProvider.setFilter(filter);
	}
	
	/**
	 * @return
	 */
	protected List<Locale> getLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(preferences.getDefaultLocale());
		locales.addAll(preferences.getAdditionalLocales());
		return locales;
	}
	
	/**
	 * @return the tableModel
	 */
	public ITranslationTableModel getTableModel() {
		return tableModel;
	}
	
	/**
	 * @return the ofsProject
	 */
	public IOfsProject getOfsProject() {
		return ofsProject;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.custom.CTabItem#dispose()
	 */
	@Override
	public void dispose() {
		if (tableModel != null) {
			translationTable.dispose();
		}
		super.dispose();
	}
}
