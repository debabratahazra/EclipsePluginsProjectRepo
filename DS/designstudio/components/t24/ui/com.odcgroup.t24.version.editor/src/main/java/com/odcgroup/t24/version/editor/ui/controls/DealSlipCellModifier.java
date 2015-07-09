package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;

import com.odcgroup.t24.swt.util.SetWigetValueCommand;
import com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.impl.DealSlipImpl;

/**
 * This class represents the cell modifier for the Version DealSlips
 */

class DealSlipCellModifier implements ICellModifier {
  private Viewer viewer;
private EditingDomain editingDomain;
private Version version;

  public DealSlipCellModifier(Viewer viewer, EditingDomain editingDomain, Version version) {
    this.viewer = viewer;
    this.editingDomain = editingDomain;
    this.version = version;
  }

  /**
   * Returns whether the property can be modified
   * 
   * @param element
   *            the element
   * @param property
   *            the property
   * @return boolean
   */
  public boolean canModify(Object element, String property) {
    // Allow editing of all values
    return true;
  }

  /**
   * Returns the value for the property
   * 
   * @param element
   *            the element
   * @param property
   *            the property
   * @return Object
   */
  public Object getValue(Object element, String property) {
	  DealSlipImpl dealSlip = (DealSlipImpl) element;
	 
    if ("Format".equals(property))
      return dealSlip.getFormat();
    else if ("Function".equals(property)){
    	//the selected index from the combo
    	DealSlipFormatFunction function = dealSlip.getFunction();
    	DealSlipFormatFunction[] values = DealSlipFormatFunction.values();
    	for (DealSlipFormatFunction dealSlipFormatFunction : values) {
    		if(function!=null && dealSlipFormatFunction.getName().equals(function.getName())){
    			return dealSlipFormatFunction.getValue();
    		}
		}
    }
    else 
    	return null;
	return null;
  }

  /**
   * Modifies the element
   * 
   * @param element
   *            the element
   * @param property
   *            the property
   * @param value
   *            the value
   */
  /* (non-Javadoc)
 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
 */
public void modify(Object element, String property, Object value) {
    if (element instanceof Item)
      element = ((Item) element).getData();
    DealSlipImpl dealSlip = (DealSlipImpl) element;
    
    if ("Format".equals(property))
      dealSlip.setFormat((String) value);
    else if ("Function".equals(property)){
    	DealSlipFormatFunction dealSlipFormatFunction = DealSlipFormatFunction.get((new Integer((Integer)value)).intValue()+1);
    	dealSlip.setFunction(dealSlipFormatFunction);
    	SetWigetValueCommand setValueCmd = new SetWigetValueCommand(editingDomain, version, version.eContainingFeature(), value);
    	editingDomain.getCommandStack().execute(setValueCmd);
    }
      
    // Force the viewer to refresh
    viewer.refresh();
  }
}