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
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display informations specific to Triple'A Entity imported from the meta
 * dictionary.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class MdfMetaDictEntityUIPage extends DialogPage {

	/**
	 * Triple'A Entity imported from metadicionary
	 */
	private MdfClass entity;

	private Text mdSQLName;
	private Text mdName;

	protected final String getSQLName() {
		String value = AAAAspect.getTripleAEntitySQLName(entity);
		return value != null ? value : "";
	}

	protected final String getName() {
		String value = AAAAspect.getTripleAEntityName(entity);
		return value != null ? value : "";
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		mdSQLName.setText(getSQLName());
		mdName.setText(getName());
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

		Label label = null;

		// Format code
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.entity.sqlname.label"));
		label.setBackground(container.getBackground());
		mdSQLName = factory.createText(container, null);
		mdSQLName.setLayoutData(fieldGridData);
		mdSQLName.setEditable(false);

		// Format Finantial Function
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("aaa.mdf.metadict.entity.name.label"));
		label.setBackground(container.getBackground());
		mdName = factory.createText(container, null);
		mdName.setLayoutData(fieldGridData);
		mdName.setEditable(false);

		initialize();
		setControl(container);

	}

	/**
	 * @param entity
	 *            Triple'A entity
	 */
	public MdfMetaDictEntityUIPage(MdfClass entity) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.entity.description"));
		this.entity = entity;
	}

}
