package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.SearchPattern;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfContentProvider;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;

/**
 *
 * @author phanikumark
 *
 */
public class ModelElementMultiTreeSelectionUI {

	private TreeViewer modelElementViewer = null;
	private TreeViewer selectionViewer = null;
	private Object rootElement;
	private List<MdfProperty> selectedFields = Collections.EMPTY_LIST;
	private StyledText documentText;
	private int multiValue;
	private int subValue;
	protected String filterString = StringUtils.EMPTY;
	protected String selectionFilterString = StringUtils.EMPTY;
	private SearchPattern selectionSearchPattern = null;
	private Text searchText; 
	private Text selectionSearchText;
	private Button mandatoryFieldsFilterCheckBox;
	private FieldSelectionDialog selectionDialog;
	private static MdfElementSorter sorter = null;
	private static final String EXTENSION_ID = "com.odcgroup.mdf.editor.domain_attribute_sorter_override";
	private MdfTreeLabelProvider labelProvider = null;
	private MdfTreeLabelProvider selectionLabelProvider = null;

	private MdfContentProvider contentProvider = null;
	private MdfModelElement selectedElement = null;
	private Button addButton;
	private boolean addRequired = true;

	private List<Object> filteredChildren = new ArrayList<Object>();
	
	private boolean multiSelection = false;
	private boolean embedded = false;

	public ModelElementMultiTreeSelectionUI(boolean multiSelection) {
		selectionSearchPattern = new SearchPattern();
		this.multiSelection = multiSelection;
	}
	
	public ModelElementMultiTreeSelectionUI(boolean multiSelection, boolean embedded) {
		this(multiSelection);
		this.embedded = embedded;
	}

	public Composite createUI(Composite parent) {
		Composite composite = new Composite(parent,SWT.NONE) ;
		composite.setLayout(new GridLayout(1, true));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gridData);
		composite.setBackground(parent.getBackground());
		createElementArea(composite);
		return composite;
	}
	
    public void createElementArea(Composite composite){
		createFilteredList(composite);
		createDocGroup(composite);
    }
    
	protected Text createFilterText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		text.setLayoutData(data);
		text.setFont(parent.getFont());
		return text;
	}
	
	protected Button createMandatoryFilterCheckBox(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.END, SWT.BEGINNING, true, false);
		composite.setLayoutData(data);
		composite.setLayout(new RowLayout());
		composite.setBackground(parent.getBackground());
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Mandatory Fields");
		label.setBackground(parent.getBackground());
		
		Button checkBox = new Button(composite, SWT.CHECK);
		checkBox.setAlignment(SWT.RIGHT);
		
		return checkBox;
	}

	/**
	 * create the document text in the lowerpane.
	 * 
	 * @param parent
	 */
	protected void createDocGroup(Composite parent) {
		Group textGourp = new Group(parent, SWT.NONE);
		textGourp.setText("Documentation");
		textGourp.setLayout(new GridLayout(1, false));
		GridData documentGroupData = new GridData(SWT.FILL, SWT.TOP, true, false);
		textGourp.setLayoutData(documentGroupData);
		textGourp.setBackground(parent.getBackground());

		documentText = new StyledText(textGourp, SWT.BORDER | SWT.WRAP	| SWT.V_SCROLL);
		GridData documentTextData = new GridData(SWT.FILL, SWT.TOP, true, false);
		documentTextData.heightHint = 50;
		documentText.setLayoutData(documentTextData);
		documentText.setEditable(false);
		documentText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	}
	
	private Group createGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout());
		GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(gdata);
		group.setBackground(composite.getBackground());
		return group;
	}

	protected void createFilteredList(Composite parent) {	
		Group composite = createGroup(parent) ;
		composite.setText("Field Selection");
		if (multiSelection) {
			composite.setLayout(new GridLayout(4, false));
		} else {
			composite.setLayout(new GridLayout(1, false));
		}
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setBackground(parent.getBackground());
		composite.setLayoutData(gridData);
		
		// left box
		if (isMultiSelection()) {
			Group leftGroup = createGroup(composite);
			leftGroup.setText("Application Fields");
			mandatoryFieldsFilterCheckBox = createMandatoryFilterCheckBox(leftGroup);
			searchText = createFilterText(leftGroup);
			modelElementViewer = new TreeViewer(leftGroup, SWT.BORDER | SWT.MULTI);
		} else {
			mandatoryFieldsFilterCheckBox = createMandatoryFilterCheckBox(composite);
			searchText = createFilterText(composite);
			modelElementViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);
		}
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 150;
		data.widthHint = 80;
		modelElementViewer.getTree().setLayoutData(data);
		modelElementViewer.setUseHashlookup(true);
		modelElementViewer.setComparator(new ViewerComparator());		
        
		modelElementViewer.setContentProvider(getContentProvider());
		modelElementViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		modelElementViewer.setLabelProvider(getLabelProvider());
		modelElementViewer.setSorter(getSorter());
		modelElementViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelctionChaged(event.getSelection());
				updateControls();
				if(getModelViewerSelection().size()==1){
					updateSelectedPropertyList();
				}
			}
		});
		modelElementViewer.setFilters(new ViewerFilter[] { new TreeViewerFilter() });
		modelElementViewer.setInput(rootElement);
		modelElementViewer.getTree().addListener(SWT.SELECTED, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateSelectedPropertyList();
			}
		});
		searchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			   filterString = searchText.getText();
			   redrawModelViewer();
			}
		});

		mandatoryFieldsFilterCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				redrawModelViewer();
			}
		});

		if (isMultiSelection()) {		
			// picker buttons
			createPickerButtons(composite);
			
			// right box
			Group rightGroup = createGroup(composite);	
			rightGroup.setText("Selected Fields");
			selectionSearchText = createFilterText(rightGroup);
			
			selectionViewer = new TreeViewer(rightGroup, SWT.BORDER | SWT.MULTI) {

				@Override
				protected Object[] getFilteredChildren(Object parent) {
					Object[] children = super.getFilteredChildren(parent);
					if (!filteredChildren.isEmpty()) {
						filteredChildren.clear();
					}
					CollectionUtils.addAll(filteredChildren, children);
					return children;
				}
				
			};
	        selectionViewer.getTree().setLayoutData(data);
	        selectionViewer.setUseHashlookup(true);
	        selectionViewer.setContentProvider(getContentProvider());
	        selectionViewer.setLabelProvider(getSelectionLabelProvider());
	        selectionViewer.setFilters(new ViewerFilter[] { new TreeViewerFilter() });
	        if (selectedFields != null && !selectedFields.isEmpty()) {
	        	IStructuredSelection input = new StructuredSelection(selectedFields);
	            selectionViewer.setInput(input);
				modelElementViewer.refresh();
	        }
	        selectionSearchText.addModifyListener(new ModifyListener() {			
				@Override
				public void modifyText(ModifyEvent e) {
					selectionFilterString = selectionSearchText.getText();
					selectionViewer.refresh();
					selectionViewer.getTree().setRedraw(false);
					selectionViewer.expandAll();
					selectionViewer.getTree().setRedraw(true);	
					if (isAddRequired()) {
						if (selectionFilterString != null 
								&& !selectionFilterString.isEmpty() 
								&& filteredChildren.isEmpty()) {
							addButton.setEnabled(true);
						} else {
							addButton.setEnabled(false);						
						}
					}
				}
			});
	        selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					handleSelctionChaged(event.getSelection());
				}
	        });
	        
	        // sort buttons
	        createSortButtons(composite);
		}
	}
	
	public String getSelectionSearchText() {
		return selectionSearchText.getText();
	}

	private void redrawModelViewer() {
	   modelElementViewer.refresh();
	   modelElementViewer.getTree().setRedraw(false);
	   modelElementViewer.expandAll();
	   modelElementViewer.getTree().setRedraw(true);				
	}

	private MdfElementSorter getSorter() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(EXTENSION_ID);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
					extensions[i].getConfigurationElements();
			if(elements.length > 0) {
				try {
					if(sorter == null)
						sorter = (MdfElementSorter)elements[0].createExecutableExtension("class"); 
						return sorter;
				} catch (CoreException e) {
					if(sorter != null)
						return sorter;
					sorter = new MdfElementSorter();
					return sorter;
				}	
			}
		}			
		if(sorter != null)
			return sorter;
		sorter = new MdfElementSorter();
		return sorter;
	}

	protected void addPressed() {
		String str = selectionSearchText.getText();
		if (!StringUtils.isEmpty(str)) {
			addCustomField(str);
		}
	}
	
	protected void addCustomField(String name) {
		IStructuredSelection selection = (IStructuredSelection) selectionViewer.getInput();
		List<MdfProperty> list = new ArrayList<MdfProperty>();
		if (selection != null) {
			list.addAll(selection.toList());
		}
		MdfAttributeWithRef attr = new MdfAttributeWithRef();
		attr.setName(name);
		list.add(attr);
		IStructuredSelection newSel = new StructuredSelection(list);
		selectionViewer.setInput(newSel);
		manageSelection(Collections.EMPTY_LIST);
		ISelection sel = new StructuredSelection(attr);
		selectionViewer.setSelection(sel);
		selectionSearchText.setText("");

	}
	
	protected void createCustomButtons(Composite composite) {
		// let the subclasses implement this for custom needs
	}
	
	private void createSortButtons(Composite parent) {		
		Composite bar = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		bar.setLayout(layout);
		bar.setBackground(parent.getBackground());
		GridData data = new GridData(SWT.LEFT, SWT.BEGINNING, false, false);
		data.verticalIndent = 15;
		bar.setLayoutData(data);
		
		if (!embedded && isAddRequired()) {		
			addButton = new Button(bar, SWT.NONE);
			GridData addButtonData = new GridData(GridData.FILL_HORIZONTAL);
			addButton.setLayoutData(addButtonData);
			addButton.setText("Add");
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addPressed();
				}
			});
			addButton.setEnabled(false);
		}	
		
		Composite buttonBar = new Composite(bar, SWT.NONE);
		layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		buttonBar.setLayout(layout);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		data.verticalIndent = 100;
		buttonBar.setBackground(bar.getBackground());
		buttonBar.setLayoutData(data);
		
		createCustomButtons(buttonBar);	

		Button upButton = new Button(buttonBar, SWT.NONE);
		GridData upButtonData = new GridData(GridData.FILL_HORIZONTAL);
		upButton.setLayoutData(upButtonData);
		upButton.setText("Up");
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) selectionViewer.getInput();
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection != null && !selection.isEmpty()) {
					List list = new ArrayList<Object>();
					list.addAll(sel.toList());
					Object obj = selection.toList().get(0);
					int index = list.indexOf(obj);
					if (index > 0) {
						list.remove(index);
						list.add(index-1, obj);
						selectionViewer.setInput(new StructuredSelection(list));
						manageSelection(Collections.EMPTY_LIST);
					}
				}
			}
		});
		
		Button downButton = new Button(buttonBar, SWT.NONE);
		GridData downButtonData = new GridData(GridData.FILL_HORIZONTAL);
		downButton.setLayoutData(downButtonData);
		downButton.setText("Down");
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) selectionViewer.getInput();
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection != null && !selection.isEmpty()) {
					List list = new ArrayList<Object>();
					list.addAll(sel.toList());
					Object obj = selection.toList().get(0);
					int index = list.indexOf(obj);
					if (index < list.size()-1) {
						list.remove(index);
						list.add(index+1, obj);
						selectionViewer.setInput(new StructuredSelection(list));
						manageSelection(Collections.EMPTY_LIST);
					}
				}				
			}
		});
	}
	
	private void createPickerButtons(Composite parent) {
		Composite buttonBar = new Composite(parent, SWT.NONE);
		buttonBar.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = layout.marginHeight = 0;
		buttonBar.setLayout(layout);
		GridData data = new GridData(SWT.LEFT, SWT.BEGINNING, false, false);
		data.verticalIndent = 115;
		buttonBar.setLayoutData(data);
		
		Button addButton = new Button(buttonBar, SWT.NONE);
		GridData addButtonData = new GridData(GridData.FILL_HORIZONTAL);
		addButton.setLayoutData(addButtonData);
		addButton.setText(">");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List selectedItems = getModelViewerSelection().toList();
				addSelectionToViewer(selectedItems);
				modelElementViewer.refresh();
				manageSelection(Collections.EMPTY_LIST);
			}
		});
		
		Button remButton = new Button(buttonBar, SWT.NONE);
		GridData remButtonData = new GridData(GridData.FILL_HORIZONTAL);
		remButton.setLayoutData(remButtonData);
		remButton.setText("<");
		remButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if(selection != null) {
					List sel = selection.toList();
					List list = new ArrayList<Object>();
					list.addAll(((IStructuredSelection)selectionViewer.getInput()).toList());
					list.removeAll(sel);
					ISelection newsel = new StructuredSelection(list);
					selectionViewer.setInput(newsel);
					selection = getUpdatedSelection(selection);
					modelElementViewer.setSelection(selection, true);
					modelElementViewer.refresh();
					manageSelection(sel);
				}
			}
		});
		
		Button addAllButton = new Button(buttonBar, SWT.NONE);
		GridData addAllButtonData = new GridData(GridData.FILL_HORIZONTAL);
		addAllButton.setLayoutData(addAllButtonData);
		addAllButton.setText(">>");
		addAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getModelElementViewer().collapseAll();
				getModelElementViewer().getTree().selectAll();
				List selectedItems = getModelViewerSelection().toList();
				addSelectionToViewer(selectedItems);
				getModelElementViewer().getTree().deselectAll();
				modelElementViewer.refresh();
				manageSelection(Collections.EMPTY_LIST);
			}
		});
		
		Button remAllButton = new Button(buttonBar, SWT.NONE);
		GridData remAllButtonData = new GridData(GridData.FILL_HORIZONTAL);
		remAllButton.setLayoutData(remAllButtonData);
		remAllButton.setText("<<");
		remAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionViewer.setInput(new StructuredSelection());
				modelElementViewer.refresh();
				manageSelection(Collections.EMPTY_LIST);
			}
		});

	}
	
	protected IStructuredSelection getUpdatedSelection(IStructuredSelection selection) {
		return selection;
	}
	
	private void addSelectionToViewer(List selectedItems) {
		IStructuredSelection input = (IStructuredSelection) selectionViewer.getInput();
		List<MdfProperty> set = new ArrayList<MdfProperty>();
		if (input != null) {
			set.addAll(input.toList());
		} 
		for (Object object : selectedItems) {
			if (object instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation) object;
				addToCollection(association, true, set);
			} else {
				set.add((MdfProperty) object);
			}
		}
		IStructuredSelection news = new StructuredSelection(set);
		selectionViewer.setInput(news);
		selectionViewer.setSelection(new StructuredSelection(selectedItems));
	}
	
	private void addToCollection(MdfAssociation association, boolean mvgroup, List<MdfProperty> set) {
		List children = ((MdfClass) association.getType()).getProperties(true);
		if (association.getContainment() == MdfContainment.BY_VALUE && children.size() > 1) {
			for (Object child : children) {
				if (!(child instanceof MdfAssociation)) {
					if (!set.contains(child)) {							
						set.add((MdfProperty)child);
					}
				} else {
					MdfAssociation assc = (MdfAssociation) child;
					addToCollection(assc, false, set);
				}
			}
		} else if (association.getContainment() == MdfContainment.BY_VALUE && isIsolatedGroup(association)) {
			MdfProperty property = (MdfProperty) children.get(0);
			if (property instanceof MdfAttribute) {
				set.add(property);
			} else if (property instanceof MdfAssociation){
				MdfAssociation assc = (MdfAssociation) property;
				List<?> aChildren = getProperties((MdfClass) assc.getType());
				set.add((MdfProperty) aChildren.get(0));
			}
		} else {
			set.add(association);
		}
	}
	
	/**
	 * let the calling class override for specific purposes
	 * 
	 * @param properties
	 */
	protected void manageSelection(List<MdfProperty> properties) {		
	}

	/**
	 * add remove the properties base on the selection.
	 * @param event
	 */
	protected void updateSelectedPropertyList() {
		IStructuredSelection selection = getSelection();
		if (selection == null) {
			updateOKButton(false);		
			return;
		}
		List list = selection.toList();
		if (list.isEmpty()) {
			updateOKButton(false);			
		} else {
			updateOKButton(true);			
		}
	}

	private void updateOKButton(boolean enable) {
		if (selectionDialog != null){
			selectionDialog.getOkButton().setEnabled(enable);
		}
	}

	/**
	 * selection changed method to listen viewer changes.
	 */
	protected void handleSelctionChaged(ISelection selection) {
		Object input = ((IStructuredSelection) selection).getFirstElement();
		if (input != null)
			documentText.setText(getTextInput(input));

	}
	
	/**
	 * @return
	 */
	public IStructuredSelection getModelViewerSelection() {
		return (IStructuredSelection) modelElementViewer.getSelection();
	}
	
	public IStructuredSelection getSelection() {
		if (!multiSelection) {
			return (IStructuredSelection) modelElementViewer.getSelection();
		}
		return (IStructuredSelection) selectionViewer.getInput();
	}
	
	/**
	 * @param selection
	 */
	public void setSelection(IStructuredSelection selection, boolean expand) {
		if (expand) {
			modelElementViewer.expandAll();
		}
		modelElementViewer.setSelection(selection, true);
	}
	
	/**
	 * @return
	 */
	public Object getParentTreeItem() {
		TreeItem[] items = modelElementViewer.getTree().getSelection();
		if (items.length > 0) {
			TreeItem item = items[0];
			TreeItem parent = item.getParentItem();
			if (parent != null) {
				return parent.getData();
			}
		}
		return null;
	}
	

	/**
	 * @param input
	 * @return
	 */
	protected Object getTableViewerInput(Object input) {
		List<?> annotationList = null;
		if (input instanceof MdfModelElement) {
			annotationList = ((MdfModelElement) input).getAnnotations();
		}
		return annotationList;
	}

	protected String getTextInput(Object selectedInput) {
		if (selectedInput instanceof MdfModelElement) {
			String documentText = null;
			if (selectedInput instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) selectedInput;
				if (assoc.getContainment() == MdfContainment.BY_VALUE) {
					List<MdfProperty> properties = ((MdfClass) assoc.getType()).getProperties(true);
					if (properties.size() == 1) {
						documentText =  properties.get(0).getDocumentation();
					}
				}
			} else {
				documentText = ((MdfModelElement) selectedInput).getDocumentation();
			}
			if (documentText != null) {
				return documentText;
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * set the input to the tree viewer.
	 * 
	 * @param inputElement
	 */
	public void setInput(Object inputElement) {
		this.rootElement = inputElement;
		modelElementViewer.setInput(inputElement);
	}


	public Object[] getSelectedItem() {
		ISelection selction = modelElementViewer.getSelection();
		if (selction instanceof IStructuredSelection) {
			return ((IStructuredSelection) selction).toArray();
		}
		return null;
	}

	/**
	 * @return the searchText
	 */
	public Text getSearchText() {
		return searchText;
	}
	/**
	 * @return the modelElementViewer
	 */
	public TreeViewer getModelElementViewer() {
		return modelElementViewer;
	}
	
	protected boolean isIsolatedGroup(MdfAssociation association) {
		if (association.getContainment() == MdfContainment.BY_VALUE) {
			List<?> children = getProperties((MdfClass) association.getType());
			if (children.size() == 1) {
				MdfProperty property = (MdfProperty) children.get(0);
				if (property instanceof MdfAttribute) {
					return true;
				} else if(property instanceof MdfAssociation) {
					MdfAssociation assc = (MdfAssociation) property;
					List<?> aChildren = getProperties((MdfClass) assc.getType());
					if (aChildren.size() == 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean noChildrenLeft(MdfAssociation association, List<MdfProperty> selectedList) {
		if (association.getContainment() == MdfContainment.BY_VALUE) {
			List children = getProperties((MdfClass) association.getType());
			if (children.size() > 1) {
				for (Object object : children) {
					MdfProperty prop = (MdfProperty) object;
					if (prop instanceof MdfAttribute) {
						if (!selectedList.contains(prop)) {
							return false;
						}
					} else if (prop instanceof MdfAssociation) {
						MdfAssociation assc = (MdfAssociation) prop;
						if (assc.getContainment() == MdfContainment.BY_REFERENCE) {
							if (!selectedList.contains(assc)) {
								return false;
							}
						} else {
							return noChildrenLeft(assc, selectedList);
						}
					}
				}
			}
		} else if (association.getContainment() == MdfContainment.BY_REFERENCE) {
			if (!selectedList.contains(association)) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	protected List<?> getProperties(MdfClass klass) {
		List<?> props = new ArrayList<Object>();
		props.addAll(klass.getProperties(true));
		return props;
	}
	
	protected boolean isFilteredText(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof MdfAttribute) {
			return filterForText(viewer, element);
		}else if (element instanceof MdfAssociation) {
			Object[] chidlren = getContentProvider().getChildren(element);
			for (Object child : chidlren) {
				if(isFilteredText(viewer, element, child)) {
					return true;
				}
			}
			return filterForText(viewer, element);
		} else {
			return filterForText(viewer, element);
		}
	}

	protected boolean isFilteredOnRequired(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof MdfAttribute) {
			return filterOnRequired(viewer, element);
		} else if (element instanceof MdfAssociation) {
			Object[] chidlren = getContentProvider().getChildren(element);
			for (Object child : chidlren) {
				if (isFilteredOnRequired(viewer, element, child)) {
					return true;
				}
			}
			return filterOnRequired(viewer, element);
		} else {
			return filterOnRequired(viewer, element);
		}
	}

	public boolean filterOnRequired(Viewer viewer, final Object element) {

		if (viewer.equals(modelElementViewer)) {
			if (mandatoryFieldsFilterCheckBox.getSelection()) {
				return filterRequired(element);
			}
		}
		return true;
	}

	public boolean filterForText(Viewer viewer, final Object element) {
		
		if (viewer.equals(modelElementViewer)) {
			if(filterString.trim().isEmpty())
				return true;
			else{
				return filterText(filterString, element);				
			}
		} else if (viewer.equals(selectionViewer)) {
			if(selectionFilterString.trim().isEmpty())
				return true;
			else{
				return filterText(selectionFilterString, element);				
			}			
		}
		return true;
	}
	
	private boolean filterText(String text, final Object element) {
		if (element instanceof MdfModelElement) {
			String elemntName = ((MdfModelElement) element).getName();
			elemntName = elemntName.replace('_', '.');
			selectionSearchPattern.setPattern(text.trim());
			return selectionSearchPattern.matches(elemntName);
		}
		return true;
	}
	
	private boolean filterRequired(final Object element) {
		if (element instanceof MdfProperty) {
			return ((MdfProperty) element).isRequired();
		}
		return false;
	}
	
	public boolean updateControls() {
		boolean isMultiplicityMany = true;
		Object input = getModelViewerSelection().getFirstElement();
		boolean enable = false;
		boolean svedit = false;
		if (input instanceof MdfProperty) {
			enable = isMultiplicityManyAttribute((MdfProperty) input);
			svedit = isSubValuedAttribute((MdfProperty) input);
		}
		if(input instanceof MdfAssociationImpl){
			MdfAssociationImpl associationImpl = (MdfAssociationImpl)input;
			if (associationImpl.getContainment() == MdfContainment.BY_VALUE
					&& associationImpl.getMultiplicity() == MdfMultiplicity.MANY) {
				isMultiplicityMany = false;
			}
		}
		if (enable) {
			multiValue=1;
			if (svedit){
				subValue=1;
			}
			else
				subValue=0;
		}else{
			multiValue=0;
		}
		return isMultiplicityMany;
	}
	/**
	 * @param property
	 * @return
	 */
	private boolean isMultiValued(TreeItem item) {
		Object obj = item.getData();
		if (obj != null && obj instanceof MdfProperty) {
			MdfProperty property = (MdfProperty) obj;
			if (property != null && property instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) property;
				if (assoc.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param property
	 * @return
	 */
	protected boolean isSubValuedAttribute(MdfProperty property) {
		TreeItem item = getSelectionItem();
		if (item != null && property.equals(item.getData())) {
			TreeItem p1 = item.getParentItem();
			if (p1 != null && isMultiValued(p1)) {
				TreeItem p2 = p1.getParentItem();
				if (p2 != null && isMultiValued(p2)) {
					return true;
				}
			}
		}
		return false;
	}
	public int getMVValue() {
		return multiValue;
	}

	public int getSVValue() {
		return subValue;
	}
	
	/**
	 * @param property
	 * @return
	 */
	protected boolean isMultiplicityManyAttribute(MdfProperty property) {
		if (property != null) {
			if(property instanceof MdfAttribute) {
				Object obj = getParentTreeItem();
				if (obj != null && obj instanceof MdfAssociation) {
					MdfAssociation assoc = (MdfAssociation) obj;
					if (assoc.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
						return true;
					}
				}
			} else if (property instanceof MdfAssociation) {
				MdfAssociation cassc = (MdfAssociation) property;
				Object obj = getParentTreeItem();
				if (obj != null && obj instanceof MdfAssociation) {
					MdfAssociation assoc = (MdfAssociation) obj;
					if (cassc.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE
							&& assoc.getContainment() == MdfConstants.CONTAINMENT_BYVALUE) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void setSelectedFields(List<MdfProperty> fields) {
		if (fields != null && !fields.isEmpty()) {
			this.selectedFields = fields;
        	IStructuredSelection input = new StructuredSelection(selectedFields);
        	if (selectionViewer != null) {
	            selectionViewer.setInput(input);	            
	            selectionViewer.refresh();
				modelElementViewer.refresh();
        	}
    		if (!multiSelection) {
	        	final MdfModelElement reference = fields.get(0);
	        	if (reference != null) {
		        	ModelVisitor mv = new ModelVisitor() {
						public boolean accept(MdfModelElement model) {
							if (model.getQualifiedName().equals(reference.getQualifiedName())) {
								selectedElement = model;
								return false;
							}
							return true;
						}
					};
					ModelWalker mw = new ModelWalker(mv);
					mw.visit((MdfModelElement) modelElementViewer.getInput());
					if (selectedElement != null) {
			        	modelElementViewer.setSelection(new StructuredSelection(selectedElement), true);
			        	modelElementViewer.setExpandedState(selectedElement, true);
					} 
	        	}
    		}
		}
	}
	/**
	 * @return
	 */
	public TreeItem getSelectionItem() {
		TreeItem[] items = getModelElementViewer().getTree().getSelection();
		if (items.length > 0) {
			return items[0];
		}
		return null;
	}

	public void setFieldSelectionDialog(FieldSelectionDialog dialog) {
		this.selectionDialog = dialog;
	}
	
	public MdfTreeLabelProvider getLabelProvider() {
		if (labelProvider == null) {
			labelProvider = new MdfTreeLabelProvider();
		}
		return labelProvider;
	}
	
	public void setLabelProvider(MdfTreeLabelProvider labelProvider) {
		if (modelElementViewer != null) {
			modelElementViewer.setLabelProvider(labelProvider);
		}
		this.labelProvider = labelProvider;
	}
	
	public MdfTreeLabelProvider getSelectionLabelProvider() {
		if (selectionLabelProvider == null) {
			return getLabelProvider();
		}
		return selectionLabelProvider;
	}
	
	public void setSelectionLabelProvider(MdfTreeLabelProvider selectionLabelProvider) {
		if (selectionViewer != null) {
			selectionViewer.setLabelProvider(selectionLabelProvider);
		}
		this.selectionLabelProvider = selectionLabelProvider;
	}
	
	public MdfContentProvider getContentProvider() {
		if (contentProvider == null) {
			contentProvider = new MdfContentProvider();
		}
		return contentProvider;
	}
	
	public void setContentProvider(MdfContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public boolean isMultiSelection() {
		return multiSelection;
	}
	
	public TreeViewer getSelectionViewer() {
		return selectionViewer;
	}
	
	public boolean isAddRequired() {
		return addRequired;
	}
	
	public void setAddRequired(boolean addRequired) {
		this.addRequired = addRequired;
	}
	

	/**
	 * filter viewer call to filter the elements in the viewer.
	 * 
	 */
	private class TreeViewerFilter extends ViewerFilter {
		
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			boolean val = true;
			if (viewer.equals(modelElementViewer)) {
				val = isFilteredOnRequired(viewer, parentElement, element);
				if (val && !filterString.trim().isEmpty()) {
					val = isFilteredText(viewer, parentElement, element);
				}
				if (val && isMultiSelection()) {
					IStructuredSelection selection = (IStructuredSelection) selectionViewer.getInput();
					if (selection != null && !selection.isEmpty()) {
						List list = selection.toList();
						if (element instanceof MdfAssociation) {
							MdfAssociation assc = (MdfAssociation) element;
							if (isByReference(assc)) {
								// association by reference
								if (list.contains(element)) {
									return false;
								}
							} else {
								// association by value
								if (isIsolatedGroup(assc)) {
									return selectIsolatedGroup(assc, list);
								} else if (noChildrenLeft(assc, list)) {
									return false;
								}
							}
						} else if (element instanceof MdfAttribute) {						
							return selectAttribute((MdfAttribute) element, list);
						}
					}
				}
			} else {
				if(!selectionFilterString.trim().isEmpty()){
					return isFilteredText(viewer, parentElement, element);
				}
			}
			return val;
		}
		
		/**
		 * selection based on isolated group (association with single attribute)
		 * @param assc
		 * @param list
		 * @return
		 */
		private boolean selectIsolatedGroup(MdfAssociation assc, List list) {
			List<?> children = getProperties((MdfClass) assc.getType());
			MdfProperty prop = (MdfProperty) children.get(0);
			if (prop instanceof MdfAttribute && list.contains(prop)) {
				return false;
			} else if (prop instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) prop;
				if (isByReference(assoc)) {
					if (list.contains(assoc)) {
						return false;
					} 
				} else {
					List<?> aChildren = getProperties((MdfClass) assoc.getType());
					if (list.contains(aChildren.get(0))) {
						return false;
					}
				}
			}
			return true;		
		}
		
		/**
		 * selection based on attributes and also check the same on extended domain (if any)
		 * 
		 * @param element
		 * @param list
		 * @return
		 */
		private boolean selectAttribute(MdfAttribute element, List list) {
			if(list.contains(element)) {
				return false;
			} else {
				// extended domains
				MdfAttribute attr = (MdfAttribute) element;
				MdfClass parentClass = attr.getParentClass();
				if(parentClass.getName().startsWith("X_")){
					MdfClass base = parentClass.getBaseClass();
					for(Object obj : base.getProperties()){
						if (obj instanceof MdfAttribute) {											
							MdfAttribute attrb = (MdfAttribute) obj;
							if (attr.getName().equals(attrb.getName()) 
									&& list.contains(attrb)) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		
		private boolean isByReference(MdfAssociation association) {
			return (association.getContainment() == MdfContainment.BY_REFERENCE);
		}

	}

}
