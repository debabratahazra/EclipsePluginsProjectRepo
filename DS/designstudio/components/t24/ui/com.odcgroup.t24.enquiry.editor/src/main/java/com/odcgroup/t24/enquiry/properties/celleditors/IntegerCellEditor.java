package com.odcgroup.t24.enquiry.properties.celleditors;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author satishnangi
 *
 */
public class IntegerCellEditor extends TextCellEditor {
	
	private boolean allowEmpty;
	
	public IntegerCellEditor(Composite composite) {
		super(composite);
		setValidator(new ICellEditorValidator() {
			public String isValid(Object object) {
				if(object instanceof Integer){
					return null;
				}
				String string = (String) object;
				try {
					if (IntegerCellEditor.this.allowEmpty && string != null && string.length() == 0) {
						return null;
					}
					if(string != null){
						Integer.parseInt(string);
					}
					return null;
				} catch (NumberFormatException exception) {
					return exception.getMessage();
				}
			}
					
		});
	}
	
	/**
	 * Use this constructor when in a celleditor the default value is empty
	 * other than any integer value.
	 * @param composite
	 * @param allowEmpty
	 */
	public IntegerCellEditor(Composite composite, boolean allowEmpty){
		this(composite);
		this.allowEmpty = allowEmpty;
	}

	@Override
	public Object doGetValue() {
		try{
			return Integer.parseInt((String) super.doGetValue());
		}catch (Exception e) {
			return "";
		}
	}

	@Override
	public void doSetValue(Object value) {
		if(value !=null){
		  super.doSetValue(value.toString());
		}
	}

}
