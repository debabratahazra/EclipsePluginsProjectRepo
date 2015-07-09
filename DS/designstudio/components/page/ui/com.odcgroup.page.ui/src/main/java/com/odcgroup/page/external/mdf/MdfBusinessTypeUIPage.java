package com.odcgroup.page.external.mdf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;

/**
 * @author pkk
 *
 */
public class MdfBusinessTypeUIPage extends DialogPage implements  SelectionListener {
	
	/**	 */
	private MdfBusinessType businessType;

	/**	 */
	private Combo widgetName;	

	/**	 */
	private AnnotationWidgetPropertyComposite tableComp;

	/**	 */
	private List<WidgetType> widgets = new ArrayList<WidgetType>();

	/**
	 * @param businessType
	 */
	public MdfBusinessTypeUIPage(MdfBusinessType businessType) {
		super();
		setTitle("UI");
		setDescription("Edits the UI extensions of this Element.");
		this.businessType = businessType;
		fetchDerivableWidgets();
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		WidgetFactory factory = getWidgetFactory();

		Composite container = factory.createComposite(parent);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout(2, false));

        {
            Group widgetGroup = factory.createGroup(container, "Widget");
            widgetGroup.setLayout(new GridLayout(2, false));
            GridData gData = new GridData(GridData.FILL_BOTH);
            gData.verticalAlignment = SWT.BEGINNING;
            widgetGroup.setLayoutData(gData);

    		factory.createLabel(widgetGroup, "&Name:");
    		
    		Composite comp = factory.createComposite(widgetGroup);
    		comp.setLayout(new GridLayout(2, false));
    		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
    		widgetName = factory.createCombo(comp, getSupportedWidgetTypes());
    		widgetName.setLayoutData(new GridData(GridData.FILL_BOTH));
    		Label spacer = factory.createLabel(comp, "");
    		gData = new GridData(GridData.END);
    		gData.widthHint=60;
    		spacer.setLayoutData(gData);
            
    		Label label = factory.createLabel(widgetGroup, "&Properties:");
    		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
    		
    		widgetName.addSelectionListener(this);
    		
    		tableComp = new AnnotationWidgetPropertyComposite(widgetGroup, factory);
    		tableComp.getAddButton().addSelectionListener(this);
    		tableComp.getEditButton().addSelectionListener(this);
    		tableComp.getRemoveButton().addSelectionListener(this);
    		
    		tableComp.getTableViewer().addFilter(new ViewerFilter() {				
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					if (element instanceof MdfAnnotationProperty) {
						MdfAnnotationProperty property = (MdfAnnotationProperty) element;
						if (property.getName().equals(GUIAspect.DISPLAY_TYPE)) {
							return false;
						}
					}
					return true;
				}
    			
    		});
        }
        
        initialize();
	}
	
	/**
	 * 
	 */
	private void initialize() {
		String widgetType = GUIAspect.getDisplayTypeValue(businessType);
		if (StringUtils.isNotEmpty(widgetType)){
			widgetName.select(getSelectionIndex(widgetType, getSupportedWidgetTypes()));
			tableComp.setWidgetType(getWidgetType(widgetName.getText()));
			tableComp.setAnnotation(getAssociatedAnnotation());
		} else {
			tableComp.getAddButton().setEnabled(false);
		}
	}
	
	/**
	 * @param val
	 * @param selection 
	 * @return int
	 */
	private int getSelectionIndex(String val, String[] selection)  {
		for (int i=0;i<selection.length;i++) {
			String widgetType = selection[i];
			if (val.equals(widgetType)) {
				return i;
			}
		}
		return -1;
	}	
	
	/**
	 * @param name
	 * @return WidgetType
	 */
	private WidgetType getWidgetType(String name) {
		for(WidgetType widget : widgets) {
			if (widget.getName().equals(name)) {
				return widget;
			}
		}
		return null;
	}
	
	/**
	 * @return String[]
	 */
	private String[] getSupportedWidgetTypes() {
		List<String> widgetNames = new ArrayList<String>();
		for(WidgetType widget : widgets) {
			widgetNames.add(widget.getName());
		}
		return widgetNames.toArray(new String[0]);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void fetchDerivableWidgets() {
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetLibrary library = metaModel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		Collection filteredWidgets = CollectionUtils.select(library.getWidgetTypes(), new DerivableWidgetsPedicate());
		for (Object object : filteredWidgets) {
			widgets.add((WidgetType) object);
		}
	}
	
	
	/**
	 *
	 */
	class DerivableWidgetsPedicate implements Predicate {
		/**
		 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object object) {
			if (object instanceof WidgetType) {
				WidgetType widget = (WidgetType) object;
				if (widget.isDerivable()) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
        widgetSelected(e);		
	}
	
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
        if (e.widget == tableComp.getAddButton()) {
            onAdd();
        } else if (e.widget == tableComp.getEditButton()) {
            onEdit();
        } else if (e.widget == tableComp.getRemoveButton()) {
            onRemove();
        } else if (e.widget == widgetName /*|| e.widget == displayFormatCombo*/) {
    		tableComp.setWidgetType(getWidgetType(widgetName.getText()));
    		int nbRows = tableComp.getTableViewer().getTable().getItemCount();
			for (int rx=0; rx < nbRows; rx++) {
				MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)tableComp.getTableViewer().getElementAt(rx);
				getAssociatedAnnotation().getProperties().remove(pr);
			}
			update();
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void handleWidgetNameChange() {
		
    	MdfAnnotation annotation = getAssociatedAnnotation();
    	if (annotation == null) {
			annotation = ModelFactory.INSTANCE.createMdfAnnotation(
					GUIAspect.NAMESPACE_URI, GUIAspect.WIDGET);
    	}

		annotation.getProperties().remove(GUIAspect.getDisplayType(businessType));
    	MdfAnnotationProperty distypeProp = ModelFactory.INSTANCE.createMdfAnnotationProperty(
				annotation, GUIAspect.DISPLAY_TYPE, widgetName.getText(), false);
		
    	annotation.getProperties().add(distypeProp);

    	businessType.getAnnotations().add(annotation);
        
	}	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void onAdd() {
		
		WidgetType widgetType = getWidgetType(widgetName.getText());
        MdfAnnotation annotation = getAssociatedAnnotation();
		AnnotationWidgetPropertyEditDialog dialog = getAnnotationWidgetPropertyDialog(widgetType, annotation.getProperties());
		
        if (dialog.open() == Dialog.OK) {
            String name = dialog.getAnnotationName();
            String value = dialog.getAnnotationValue();
            MdfAnnotationProperty property = 
            	ModelFactory.INSTANCE.createMdfAnnotationProperty(annotation, name, value);
            annotation.getProperties().add(property);
			update();
        }
		
	}
	
	/**
	 * 
	 */
	private void onEdit() {
		IStructuredSelection selection = (IStructuredSelection)tableComp.getTableViewer().getSelection();
		MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)selection.getFirstElement();

		WidgetType widgetType = getWidgetType(widgetName.getText());
		AnnotationWidgetPropertyEditDialog dialog = getAnnotationWidgetPropertyDialog(widgetType, null);
        dialog.setAnnotationName(pr.getName());
        dialog.setAnnotationValue(pr.getValue());
        if (dialog.open() == Dialog.OK) {
        	pr.setName(dialog.getAnnotationName());
        	pr.setValue(dialog.getAnnotationValue());
			update();
        }
	}
	
	/**
	 * 
	 */
	private void onRemove() {
		IStructuredSelection selection = (IStructuredSelection)tableComp.getTableViewer().getSelection();
		MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)selection.getFirstElement();
		String message = MdfPlugin.getResourceString("annotationsPro.confirm.msg");
		boolean confirm = MessageDialog.openConfirm(getShell(), MdfPlugin.getResourceString("annotationsPro.confirm.remove"), message);
		if (confirm) {
			getAssociatedAnnotation().getProperties().remove(pr);
			update();
		}
	}
	
	/**
	 * 
	 */
	private void update() {
		setDirty(true);
		tableComp.getTableViewer().refresh();
	}
	
	/**
	 * @return MdfAnnotation
	 */
	private MdfAnnotation getAssociatedAnnotation() {
		return GUIAspect.getWidget(businessType);
	}
		
	/**
	 * @param widgetType
	 * @param excludedProperties
	 * @return AnnotationPropertyEditDialog
	 */
	private AnnotationWidgetPropertyEditDialog getAnnotationWidgetPropertyDialog(WidgetType widgetType, List<MdfAnnotationProperty> excludedProperties) {
		AnnotationWidgetPropertyEditDialog dialog = new AnnotationWidgetPropertyEditDialog(getShell(), widgetType, excludedProperties);
        dialog.setWindowTitle(MdfPlugin.getResourceString("annotationsPro.editDialog.addTitle"));
        return dialog;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#doSave(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public void doSave(MdfModelElement element) {
		handleWidgetNameChange();
	}

}
