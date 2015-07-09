package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 *
 * @author pkk
 *
 */
public class BrowsePropertyWidget extends AbstractPropertyWidget implements SelectionListener {
	
	protected Text textControl = null;
	protected Button browse = null;
	private IPropertySelectionDialogCreator selectionDialogCreator = null;
	private boolean browseOnly = true;
	private boolean fillHorizontal = false;
	private boolean browseDefault = false;
	protected String browseLabel = null;
	protected String browseTooltip = null;
	protected String pfText;
	
	/**
	 * @param attribute
	 * @param label
	 */
	public BrowsePropertyWidget(EStructuralFeature attribute, String label) {
		super(attribute, label, null);
	}
	/**
	 * @param body
	 * @param property
	 */
	public BrowsePropertyWidget(EStructuralFeature attribute, String label, String tooltip) {
		super(attribute, label, tooltip);
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		// create label
		boolean required = false;
		if (getStructuralFeature() != null && getStructuralFeature().isRequired()) {
			required = true;
		}
		SWTWidgetFactory.createLabel(body, getLabel(), getTooltip(), required);
		int columns = 3;
		if (isFillHorizontal()) {
			columns = 2;
		}
		Composite textComp = SWTWidgetFactory.createComposite(body, columns, false);
		this.textControl = SWTWidgetFactory.createTextField(textComp, false);
		this.textControl.setEditable(!isBrowseOnly());
		if (browseLabel == null) {
			browseLabel = "&Browse...";
		}
		this.browse = SWTWidgetFactory.createButton(textComp, browseLabel);
		if (browseTooltip != null) {
			this.browse.setToolTipText(browseTooltip);
		}
		if (!isFillHorizontal())
			SWTWidgetFactory.createFiller(textComp);
		
		if (!isBrowseOnly()) {
			this.textControl.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					if (isValidInput())
						notifyPropertyFeatureChange(textControl.getText());							
				}
				@Override
				public void focusGained(FocusEvent e) {
					BrowsePropertyWidget.this.pfText = textControl.getText();
				}
			});
			
			if (isBrowseDefault()) {
			this.textControl.addKeyListener(new KeyListener() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getSource() instanceof Text){
						if(textControl.getText().equals("") )
							textControl.setText(""); 
						else
							textControl.setText(pfText);	
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			}
			
		}
		
		this.browse.addSelectionListener(this);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		IPropertySelectionDialogCreator selectionDialogCreator = getSelectionDialogCreator();
		if (selectionDialogCreator != null) {
			IPropertySelectionDialog dialog = selectionDialogCreator.createDialog(getShell(), getRootElement());
			final int resultCode = dialog.open();
			if (resultCode == IDialogConstants.OK_ID) {
				Object obj = dialog.getSelection();
				if (obj != null)
					notifyPropertyFeatureChange(dialog.getSelection());
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyAttributeWidget#initPropertyControls()
	 */
	@Override
	public void initPropertyControls() {
		if (getElement() != null) {
			Object data = getElement().eGet(getStructuralFeature());			
			if (data != null && data instanceof EObject) {
				data = getResourceUri((EObject) data);
			} else if (data == null) {
				data = "";
			}
			if(textControl != null)
				this.textControl.setText(data.toString());
		}		
	}	
	
	/**
	 * @param element
	 * @return
	 */
	public String getResourceUri(EObject element) {
		Resource resource = element.eResource();
		URI uri = null;
		if (resource != null) {
			uri = resource.getURI();
		} else {
			if (element.eIsProxy()){
				InternalEObject eObj = (InternalEObject) element;
				uri = eObj.eProxyURI().trimFragment();				
			}
 		}

		if (uri != null) {
			if (uri.isPlatform()) {
				IResource res = (IResource) OfsResourceHelper.getFile(resource);
				if (res != null) {
					uri = ModelURIConverter.createModelURI(res);
				}
			}
			return uri.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * @return
	 */
	protected boolean isValidInput() {
		String str = textControl.getText();
		// check if the attribute is ID & check for uniqueness
		String originalVal = null;
		Object objVal = getOriginalValue();
		if (objVal != null) {
			originalVal = objVal.toString();
		} else {
			originalVal = "";
		}
		if (str!=null && str.equals(originalVal)) {
			return false;
		}
		if (getStructuralFeature().isRequired()){
			if (str == null || str.trim().length() == 0) {
				textControl.setText(originalVal);
			} else if (str != null && str.trim().length() > 0 
					&& !str.equals(originalVal)) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @return
	 */
	private Object getOriginalValue() {
		Object data = getElement().eGet(getStructuralFeature());
		return data;
	}

	/**
	 * @return the selectionDialogCreator
	 */
	public IPropertySelectionDialogCreator getSelectionDialogCreator() {
		return selectionDialogCreator;
	}


	/**
	 * @param selectionDialogCreator the selectionDialogCreator to set
	 */
	public void setSelectionDialogCreator(IPropertySelectionDialogCreator selectionDialogCreator) {
		this.selectionDialogCreator = selectionDialogCreator;
	}


	/**
	 * @return the browseOnly
	 */
	public boolean isBrowseOnly() {
		return browseOnly;
	}


	/**
	 * @param browseOnly the browseOnly to set
	 */
	public void setBrowseOnly(boolean browseOnly) {
		this.browseOnly = browseOnly;
	}
	
	/**
	 * @return
	 */
	public boolean isBrowseDefault() {
		return browseDefault;
	}
	
	/**
	 * @param fromMenu
	 */
	public void setBrowseDefault(boolean fromMenu) {
		this.browseDefault = fromMenu;
	}
	
	/**
	 * @return the browse
	 */
	public Button getBrowse() {
		return browse;
	}

	/**
	 * @return the fillHorizontal
	 */
	public boolean isFillHorizontal() {
		return fillHorizontal;
	}
	/**
	 * @param fillHorizontal the fillHorizontal to set
	 */
	public void setFillHorizontal(boolean fillHorizontal) {
		this.fillHorizontal = fillHorizontal;
	}
	
	/**
	 * @return the browseLabel
	 */
	public String getBrowseLabel() {
		return browseLabel;
	}
	/**
	 * @param browseLabel the browseLabel to set
	 */
	public void setBrowseLabel(String browseLabel) {
		this.browseLabel = browseLabel;
	}
	/**
	 * @return the browseTooltip
	 */
	public String getBrowseTooltip() {
		return browseTooltip;
	}
	/**
	 * @param browseTooltip the browseTooltip to set
	 */
	public void setBrowseTooltip(String browseTooltip) {
		this.browseTooltip = browseTooltip;
	}

}
