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
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Display Information specific to Triple'A Format Element
 *
 * @author atr
 * @since DS 1.40.0
 */
public class MdfFormatElementUIPage extends DialogPage {

	private static final int TWO_COLUMNS = 2;

	/**
	 *  Triple'A Format Element
	 */
	private MdfAttribute formatElement;

	private Text fmtPropName;
	private Text fmtPropRank;
	private Text fmtPropSortingRank;
	private Text fmtPropAttribute;
	private Text fmtPropScript;

	protected final String getName() {
		String value = AAAAspect.getTripleAFormatElementName(formatElement);
		return value != null ? value : "";
	}

	protected final String getAttribute() {
		String value = AAAAspect.getTripleAAttributeSQLName(formatElement);
		return value != null ? value : "";
	}

	protected final String getScript() {
		String value = AAAAspect.getTripleAFormatElementScript(formatElement);
		return value != null ? value : "";
	}

	protected final String getRank() {
		Integer integerValue = AAAAspect.getTripleAFormatElementRank(formatElement);
		String value = "";
		if(integerValue != null) {
			value = String.valueOf(integerValue.intValue());
		}
		return value;
	}

	protected final String getSortingRank() {
		Integer integerValue = AAAAspect.getTripleAFormatElementSortingRank(formatElement);
		String value = "";
		if(integerValue != null) {
			value = String.valueOf(integerValue.intValue());
		}
		return value;
	}

	/**
	 * Initialize the ui controls
	 */
	private void initialize() {
		fmtPropName.setText(getName());
		fmtPropRank.setText(getRank());
		fmtPropSortingRank.setText(getSortingRank());
		fmtPropAttribute.setText(getAttribute());
		fmtPropScript.setText(getScript());
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
		container.setLayout(new GridLayout(TWO_COLUMNS, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		// Format code
		createLabel(container, "aaa.mdf.format.property.name.label");
		this.fmtPropName = factory.createText(container, null);
		this.fmtPropName.setLayoutData(fieldGridData);
		this.fmtPropName.setEditable(false);

		// Format Rank
		createLabel(container, "aaa.mdf.format.property.rank.label");
		this.fmtPropRank = factory.createText(container, null);
		this.fmtPropRank.setLayoutData(fieldGridData);
		this.fmtPropRank.setEditable(false);

		// Format Sorting Rank
		createLabel(container, "aaa.mdf.format.property.sortingRank.label");
		this.fmtPropSortingRank = factory.createText(container, null);
		this.fmtPropSortingRank.setLayoutData(fieldGridData);
		this.fmtPropSortingRank.setEditable(false);

		// Format Financial Function
		createLabel(container, "aaa.mdf.format.property.attribute.label");
		this.fmtPropAttribute = factory.createText(container, null);
		this.fmtPropAttribute.setLayoutData(fieldGridData);
		this.fmtPropAttribute.setEditable(false);

		// Format AAA Entity name
		Label label = null;
		label = createLabel(container, "aaa.mdf.format.property.script.label");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		fmtPropScript = factory.createText(container, null, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		fmtPropScript.setLayoutData(new GridData(GridData.FILL_BOTH));
		fmtPropScript.setEditable(false);

		initialize();
		setControl(container);
	}

	/**
	 * Creates a label with text provided from Messages by key
	 *
	 * @param container
	 * @param key
	 * @return
	 */
	private Label createLabel(Composite container, String key) {
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString(key));
		label.setBackground(container.getBackground());
		return label;
	}

	/**
	 * @param formatElement  Triple'A Format Element
	 */
	public MdfFormatElementUIPage(MdfAttribute formatElement) {
		setTitle(Messages.getString("aaa.mdf.page.title"));
		setDescription(Messages.getString("aaa.mdf.page.formatelement.description"));
		this.formatElement = formatElement;
	}
}
