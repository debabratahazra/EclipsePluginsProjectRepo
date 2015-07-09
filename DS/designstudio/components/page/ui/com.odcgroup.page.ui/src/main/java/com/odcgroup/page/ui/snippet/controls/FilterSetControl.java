package com.odcgroup.page.ui.snippet.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 * UI Control to manage the filterSet contents of the widget
 * 
 * @author pkk
 * @param <T>
 */
public abstract class FilterSetControl<T> extends AbstractSnippetControl<T> {

	/** */
	private T filterSetParent;
	/** The table control for filters in the filterset */
	private ListControl<IFilter, IFilterSet> listControl;
	/** */
	protected Text datasetText;
	/** dataset browse button */
	protected Button browse;
	/** */
	private Text level;

	/** */
	protected boolean dsrequired = false;
	/** cancel check box */
	protected Button cancelChk;

	/**
	 * @param parent
	 * @param style
	 * @param dsrequired
	 */
	public FilterSetControl(Composite parent, int style, boolean dsrequired) {
		this.dsrequired = dsrequired;
		createContents(parent, style);
	}

	/**
	 * @param parent
	 * @param style
	 * @return composite
	 */
	protected Composite createContents(Composite parent, int style) {

		Composite body = new Composite(parent, SWT.FILL);
		body.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		if (!(parent.getLayout() instanceof FillLayout)) {
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.grabExcessHorizontalSpace = true;
			body.setLayoutData(gd);
		}

		// filterset attribute controls
		createFilterSetAttributeControls(body);

		// filter list control
		listControl = new FilterListControl(body, SWT.FILL) {

			protected void executeCommand(BaseCommand command) {
				updateFilterSet();
			}

			public String getTargetDatasetName() {
				String targetds = null;
				if (dsrequired) {
					targetds = datasetText.getText();
				} else {
					targetds = getTargetDataset();
				}
				return targetds;
			}

		};
		// logical operator
		// createLogicalOperatorControl(body);

		return body;
	}

	/**
	 * @param body
	 */
	private void createFilterSetAttributeControls(Composite body) {

		Composite filtersetgroup = new Composite(body, SWT.FILL);
		filtersetgroup.setBackground(body.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		filtersetgroup.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		filtersetgroup.setLayoutData(gd);

		// filterset target dataset browse control
		createLabel(filtersetgroup, "Target Dataset");
		Composite textComp = new Composite(filtersetgroup, SWT.FILL);
		textComp.setBackground(filtersetgroup.getBackground());
		layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textComp.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		textComp.setLayoutData(gd);
		datasetText = new Text(textComp, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		datasetText.setLayoutData(gd);

		datasetText.setEditable(false);
		datasetText.setBackground(ColorConstants.white);

		browse = new Button(textComp, SWT.NONE);
		GridData data = new GridData();
		browse.setLayoutData(data);
		browse.setText("&Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		// filterset level
		createLabel(filtersetgroup, "Level");
		level = new Text(filtersetgroup, SWT.BORDER);
		level.setTextLimit(2);
		gd = new GridData();
		gd.widthHint = 20;
		level.setLayoutData(gd);
		level.setToolTipText("level must be an integer value. enter any numebr in the range 1 to 98");
		level.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
            	String s = level.getText();
            	if (!StringUtils.isEmpty(s)) {
					checkLevelValue(level, s);
				} else {
					getFilterSet().setLevel(-1);
						}
						}
        });

		// cancel attribute
		createLabel(filtersetgroup, "Cancel Filter Set with higher level");
		cancelChk = new Button(filtersetgroup, SWT.CHECK);
		cancelChk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				getFilterSet().setCancelled(cancelChk.getSelection());
			}
		});

	}

	/**
	 * check the entered level value is integer and in the range 1 to 98.
	 * 
	 * @param Text
	 *            ,String
	 */
	private void checkLevelValue(Text level, String value) {
		final String levelMessage = "level must be an integer value. enter any numebr in the range 1 to 98";
		try {
			int levelValue = getIntegerValue(value).intValue();
			if (levelValue <= 0 || levelValue > 98) {
				MessageDialog.openError(level.getShell(), "FilterSet Level",
						levelMessage);
				if (getFilterSet().getLevel() != -1) {
					level.setText(getFilterSet().getLevel() + "");
				} else {
					level.setText("");
				}
				level.setFocus();
				return;
			}
			if (getFilterSet().getLevel() != levelValue) {
				getFilterSet().setLevel(levelValue);
				updateFilterSet();
			}
		} catch (Exception ex) {
			if (getFilterSet() != null) {
				MessageDialog.openError(level.getShell(), "FilterSet Level",
						levelMessage);
				if (getFilterSet().getLevel() != -1) {
					level.setText(getFilterSet().getLevel() + "");
				} else {
					level.setText("");
				}
			}
			level.setFocus();
		}
	}

	/**
	 * on click of the browse dataset
	 */
	private void handleBrowse() {
		String previousVal = datasetText.getText();

		MdfEntitySelectionDialog dialog = MdfEntitySelectionDialog
				.createDialog(getShell(), getDomainObjectProvider());
		if (dialog.open() == Window.OK) {
			MdfEntity entity = (MdfEntity) dialog.getFirstResult();
        	if (entity != null) {
				String domainEntity = entity.getQualifiedName()
						.getQualifiedName();
				if (!StringUtils.isEmpty(previousVal)
						&& !previousVal.equals(domainEntity)) {
					boolean okpressed = MessageDialog
							.openConfirm(
									getShell(),
									"Confirm",
							"Are you sure you want to define a new dataset?\n\nAll the filters previously defined will be lost!");
					if (!okpressed) {
						return;
					}
				}
				datasetText.setText(domainEntity);
				if (!previousVal.equals(domainEntity)) {
					getFilterSet().setTargetDataset(domainEntity);
					updateFilterSet();
				}
			}
		}

	}

	/**
	 * @param group
	 */

	/**
	 * @param composite
	 * @param text
	 */
	private void createLabel(Composite composite, String text) {
		CLabel label = new CLabel(composite, SWT.NONE);
		label.setBackground(composite.getBackground());
		label.setText(text);
	}

	/**
	 * @param data
	 * @return int
	 * @throws Exception
	 */
	private Integer getIntegerValue(Object data) throws Exception {
		Integer intVal = 0;
		try {
			intVal = new Integer(data.toString());
		} catch (NumberFormatException nfe) {
			throw new Exception(nfe);
		}
		return intVal;
	}

	/**
	 * @param filterSetContainer
	 */
	public void setInput(T filterSetContainer) {
		this.filterSetParent = filterSetContainer;
		setReflectiveInput(filterSetContainer);
		initFilterSetControls();
	}

	/**
	 * 
	 */
	public void refresh() {
		initFilterSetControls();
		if (listControl != null) {
			listControl.refresh();
		}
	}

	/**
	 * 
	 */
	protected void initFilterSetControls() {
		IFilterSet filterSet = getFilterSet();
		if (filterSet != null) {
			String id = filterSet.getId();
			if (StringUtils.isEmpty(id)) {
				filterSet.setId(SnippetUtil.getSnippetFactory()
						.generateFilterSetID());
			}
			if (dsrequired) {
				String domainEntity = filterSet.getTargetDatasetName();
				if (domainEntity != null) {
					datasetText.setText(domainEntity);
				} else {

				}
			} else {
				if (getTargetDataset() != null)
					filterSet.setTargetDataset(getTargetDataset());
			}
			int fLevel = filterSet.getLevel();
			if (fLevel > 0) {
				level.setText(fLevel + "");
			}
			if (filterSet.isCancelled()) {
				cancelChk.setSelection(true);
			} else {
				cancelChk.setSelection(false);
			}
			if (listControl != null) {
				listControl.setInput(filterSet, null);
			}
		}
	}

	/**
	 */
	private void updateFilterSet() {
		updateFilterSet(this.filterSetParent, getFilterSet());
		refresh();
	}

	/**
	 * @param parent
	 * @param filterSet
	 */
	public abstract void addFilterSet(T parent, IFilterSet filterSet);

	/**
	 * @param parent
	 * @param filterSet
	 */
	public abstract void updateFilterSet(T parent, IFilterSet filterSet);

	/**
	 * @return filterset
	 */
	public abstract IFilterSet getFilterSet();

	/**
	 * @param parent
	 */
	public abstract void setReflectiveInput(T parent);

	/**
	 * @return boolean
	 */
	public abstract String getTargetDataset();

}
