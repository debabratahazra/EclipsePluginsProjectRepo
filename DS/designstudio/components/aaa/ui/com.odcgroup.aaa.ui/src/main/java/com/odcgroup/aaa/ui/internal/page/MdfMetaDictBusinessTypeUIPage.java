package com.odcgroup.aaa.ui.internal.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display Information specific to Triple'A Attribute / Association
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfMetaDictBusinessTypeUIPage extends DialogPage {
	
	/**
	 * Triple'A Attribute
	 */
	private MdfBusinessType businessType;
	
	private Text mdType;
	
	protected final String getType() {
		String value = AAAAspect.getTripleABusinessType(businessType); 
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		mdType.setText(getType());
	}

	/* 
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#doSave(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public void doSave(MdfModelElement element) {
		// nothing to do
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		WidgetFactory factory = getWidgetFactory();		
		
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(parent.getBackground());
		container.setForeground(parent.getForeground());
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(fieldGridData);
		
		// Name
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.businesstype.type.label"));
		label.setBackground(container.getBackground());
		mdType = factory.createText(container, null);
		mdType.setLayoutData(fieldGridData);
		mdType.setEditable(false);
        
		initialize();
		setControl(container);

	}
	
	/**
	 * @param aaaFormat Triple'A Format
	 */
	public MdfMetaDictBusinessTypeUIPage(MdfBusinessType businessType) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.businesstype.description"));
		this.businessType = businessType;
	}

}
