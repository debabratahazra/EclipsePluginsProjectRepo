package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.SearchPattern;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.editors.providers.AnnotationTableLabelProvider;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfAnnotationPropertyContentProvider;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfContentProvider;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * ModelElementTreeSelectionDialog class to create the Dialog with Multi pane.
 * 
 * @author snn
 * 
 */
public class ModelElementTreeSelectionUI {

	private TreeViewer modelElementViewer = null;
	private Object rootElement;
	private TableViewer tableViewer;
	private StyledText documentText;
	private int multiValue;
	private int subValue;
	private ILabelDecorator decorator;
	protected String filterString = StringUtils.EMPTY;
	private static final int FIELD_SELECTION_VIWER_HEIGHT = 100;
	private Button reqCheckBtn;
	private SearchPattern fieldSearchPattern = null;
    private ArrayList<MdfProperty> selectedProperties ;
	private Text searchText; 
	private FieldSelectionDialog selectionDialog;

	public ModelElementTreeSelectionUI() {
		this.decorator = MdfPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator();
		fieldSearchPattern = new SearchPattern();
	}

	public Control createUI(Composite parent) {
		Composite composite = new Composite(parent,SWT.NONE) ;
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));
		createElementArea(composite);
		return composite;
	}
    public void createElementArea(Composite composite){
    	createFilterText(composite);
		createFilteredList(composite);
		createLowerGroups(composite);
    }
	protected Text createFilterText(Composite parent) {
		Group grp = new Group(parent, SWT.None);
		grp.setText("Search");
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_composite.widthHint = 462;
		gd_composite.heightHint = 34;
		grp.setLayoutData(gd_composite);
		grp.setLayout(new GridLayout(2, false));
		searchText = new Text(grp, SWT.BORDER);
		searchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			   filterString = searchText.getText();
			   modelElementViewer.refresh();
			   modelElementViewer.getTree().setRedraw(false);
			   modelElementViewer.expandAll();
			   modelElementViewer.getTree().setRedraw(true);
			   modelElementViewer.setSelection(new StructuredSelection(selectedProperties));
			}
		});
		GridData data = new GridData();
		data.grabExcessVerticalSpace = false;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		searchText.setLayoutData(data);
		searchText.setFont(parent.getFont());
		reqCheckBtn = new Button(grp, SWT.CHECK);
		reqCheckBtn.setText("Required");
		reqCheckBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		reqCheckBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(reqCheckBtn.getSelection() == true){
					reqCheckBtn.setSelection(true);
					if(searchText.getText().isEmpty())
						searchText.setText("");
					else{
						searchText.setText(searchText.getText());
					}
				}else{
					reqCheckBtn.setSelection(false);
					if(searchText.getText().isEmpty())
						searchText.setText("");
					else{
						searchText.setText(searchText.getText());
					}
				}
			}
		});
		return searchText;
	}

	/**
	 * create the lower pane groups.
	 * 
	 * @param parent
	 */
	protected void createLowerGroups(Composite parent) {
		tableGroup(parent);
		// text group
		textGroup(parent);
	}

	/**
	 * create the document text in the lowerpane.
	 * 
	 * @param parent
	 */
	protected void textGroup(Composite parent) {
		Group textGourp = new Group(parent, SWT.NONE);
		textGourp.setText("Documentation");
		textGourp.setLayout(new GridLayout());
		GridData documentGroupData = new GridData();
		documentGroupData.grabExcessVerticalSpace = false;
		documentGroupData.grabExcessHorizontalSpace = true;
		documentGroupData.horizontalAlignment = GridData.FILL;
		documentGroupData.verticalAlignment = GridData.FILL;
		textGourp.setLayoutData(documentGroupData);

		documentText = new StyledText(textGourp, SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		GridData documentTextData = new GridData();
		documentTextData.grabExcessVerticalSpace = false;
		documentTextData.grabExcessHorizontalSpace = true;
		documentTextData.horizontalAlignment = GridData.FILL;
		documentTextData.verticalAlignment = GridData.BEGINNING;
		documentTextData.heightHint = 108;
		documentText.setLayoutData(documentTextData);
		documentText.setEditable(false);
		documentText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
	}

	/**
	 * create the table group in the lower pane.
	 * 
	 * @param parent
	 * @return
	 */
	protected Table tableGroup(Composite parent) {
		// table group
		Group tableGourp = new Group(parent, SWT.NONE);
		GridData data = new GridData();
		data.grabExcessVerticalSpace = false;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		tableGourp.setLayoutData(data);
		tableGourp.setLayout(new GridLayout());
		tableGourp.setFont(parent.getFont());
		tableGourp.setText("Annotation Properties");
		// table viewer
		tableViewer = new TableViewer(tableGourp, SWT.V_SCROLL | SWT.BORDER);
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setWidth(170);
		nameColumn.setText("Name");
		TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText("Value");
		valueColumn.setWidth(240);
		GridData viewerdata = new GridData();
		viewerdata.grabExcessVerticalSpace = false;
		viewerdata.heightHint = 108;
		viewerdata.grabExcessHorizontalSpace = true;
		viewerdata.horizontalAlignment = GridData.FILL;
		viewerdata.verticalAlignment = GridData.BEGINNING;
		tableViewer.getTable().setLayoutData(viewerdata);
		tableViewer.setLabelProvider(new AnnotationTableLabelProvider());
		tableViewer.setContentProvider(new MdfAnnotationPropertyContentProvider());
		
		return table;
	}

	protected void createFilteredList(Composite parent) {
		modelElementViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI);
		GridData data = new GridData();
		data.heightHint = FIELD_SELECTION_VIWER_HEIGHT;
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
        data.heightHint =250;
		modelElementViewer.getTree().setLayoutData(data);
		modelElementViewer.setUseHashlookup(true);
		modelElementViewer.setComparator(new ViewerComparator());
		modelElementViewer.setContentProvider(new MdfContentProvider());
		modelElementViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		modelElementViewer.setLabelProvider(new DecoratingLabelProvider(
				new MdfTreeLabelProvider(), decorator));
		modelElementViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelctionChaged();
				updateControls();
				if(getSelection().size()==1){
					updateSelectedPropertyList();
				}
			}
		});
		modelElementViewer
		.setFilters(new ViewerFilter[] { new TreeViewerFilter() });
		modelElementViewer.setInput(rootElement);
		selectedProperties= new ArrayList<MdfProperty>();
		modelElementViewer.getTree().addListener(SWT.SELECTED, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateSelectedPropertyList();
			}
		});
	}

	/**
	 * add remove the properties base on the selection.
	 * @param event
	 */
	protected void updateSelectedPropertyList() {
		IStructuredSelection selection = getSelection();
		Iterator iter = selection.iterator();
		updateOKButton(false);
		while (iter.hasNext()) {
			Object input = iter.next();
			if(input instanceof MdfAssociation ){
				MdfAssociation association = (MdfAssociation)input;
				if(association.getContainment() != MdfContainment.BY_VALUE){
					if(!selectedProperties.contains(input)){
						selectedProperties.add((MdfProperty)input);
					}
					updateOKButton(true);
				}
			}else {
				if(!selectedProperties.contains(input)){
					selectedProperties.add((MdfProperty)input);
				}
				updateOKButton(true);
			}
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
	protected void handleSelctionChaged() {
		IStructuredSelection selction = getSelection();
		Object input = selction.getFirstElement();
		tableViewer.getTable().removeAll();
		tableViewer.setInput(getTableViewerInput(input));
		documentText.setText(getTextInput(input));

	}
	
	/**
	 * @return
	 */
	public IStructuredSelection getSelection() {
		return (IStructuredSelection) modelElementViewer.getSelection();
	}
	
	public List<MdfProperty> getSelectedProperties(){
		return selectedProperties;
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
			String documentText = ((MdfModelElement) selectedInput)
					.getDocumentation();
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
	 * @return the reqCheckBtn
	 */
	public Button getReqCheckBtn() {
		return reqCheckBtn;
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

	/**
	 * filter viewer call to filter the elements in the viewer.
	 * 
	 * 
	 */
	private class TreeViewerFilter extends ViewerFilter {

		public boolean select(Viewer viewer, Object parentElement,
				 Object element) {
			if(!filterString.trim().isEmpty()){
			  return isFilteredText(parentElement, element);
			}
			return true;

		}

	}
	
	protected boolean isFilteredText(Object parentElement, Object element) {
		if (reqCheckBtn.getSelection() == true) {
			if (element instanceof MdfAttribute) {
				if (((MdfAttribute) element).isRequired() || ((MdfAttribute) element).getMultiplicity() > 1) {
					return filterForText(element);
				} 
			} else if (element instanceof MdfAssociation) {
				if (((MdfAssociation) element).isRequired() || ((MdfAssociation) element).getMultiplicity() > 1) {
					return filterForText(element);
				} 
			}
			return false;
		} else {
			if (element instanceof MdfAttribute) {
				return filterForText(element);
			}else if (element instanceof MdfAssociation) {
				return filterForText(element);
			}
			else
			return filterForText(element);
		}
	}
	
	/**
	 * @param element
	 */
	public boolean filterForText(final Object element) {
		if(filterString.trim().isEmpty())
			return true;
		else{
			if (element instanceof MdfModelElement) {
				String elemntName = ((MdfModelElement) element).getName();
				fieldSearchPattern.setPattern(filterString.trim());
				return fieldSearchPattern.matches(elemntName);
			}
			
		}
		return true;
	}
	
	
	public boolean updateControls() {
		boolean isMultiplicityMany = true;
		Object input = getSelection().getFirstElement();
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
}
