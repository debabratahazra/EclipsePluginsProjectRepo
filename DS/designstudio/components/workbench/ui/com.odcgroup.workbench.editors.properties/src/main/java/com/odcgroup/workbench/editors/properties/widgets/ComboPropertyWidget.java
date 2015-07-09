package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;

/**
 *
 * @author pkk
 *
 */
public abstract class ComboPropertyWidget extends AbstractPropertyWidget {
	
	/**		 */
	private ComboViewer viewer;

	/**
	 * @param feature
	 * @param label
	 */
	public ComboPropertyWidget(EStructuralFeature feature, String label) {
		this(feature, label, null);		
	}

	/**
	 * @param feature
	 * @param label
	 * @param tooltip
	 */
	public ComboPropertyWidget(EStructuralFeature feature, String label, String tooltip) {
		super(feature, label, tooltip);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.ISWTWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		// create label
		SWTWidgetFactory.createSimpleLabel(body, getLabel());
		viewer = new ComboViewer(body, SWT.DROP_DOWN | SWT.READ_ONLY);
		Combo combo = viewer.getCombo();
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		combo.setLayoutData(gridData);
		viewer.setContentProvider(new ComboContentProvider());
		viewer.setLabelProvider(new LabelProvider(){
			public String getText(Object element) {
				return getItemDisplayText(element);
			}
		});
		
		viewer.getCombo().addSelectionListener(new SelectionAdapter() {		
			
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				Object val = ((IStructuredSelection) selection).getFirstElement();
				notifyPropertyFeatureChange(val);
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		viewer.setInput(getRootElement());	
		if (getElement() != null) {
			Object obj = getElement().eGet(getStructuralFeature());
			if (obj != null) {
				if (obj instanceof EObject &&  ((EObject) obj).eIsProxy()) {
					obj = EcoreUtil.resolve((EObject) obj, getRootElement());
				}
				viewer.setSelection(new StructuredSelection(obj), true);
			}
		}
	}
	
	/**
	 * @param element
	 * @return
	 */
	public abstract String getItemDisplayText(Object element);
	
	/**
	 * @param element
	 * @return
	 */
	public abstract Object[] getComboItems(Object element);	
	
	
	/**
	 *
	 */
	class ComboContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return getComboItems(inputElement);
		}

		public void dispose() {			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {			
		}
		
	}

}
