package com.odcgroup.page.ui.snippet.controls;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.page.ui.properties.table.controls.StringValueCombo;

/**
 * @author pkk
 *
 */
public abstract class FilterValueControl  {
	
	/** */
	private Text valueText = null;
	/** */
	private Label valueLabel = null;
	/** */
	private Button enumBrowse = null;
	/** */
	private boolean multiple = false;
	/** */
	private List<String> values = new ArrayList<String>();
	/** */
	private StringValueCombo combo = null;
	/** */
	private boolean simple = true;
	/** */
	private String prevVal;

	/**
	 * @param parent
	 * @param multiple
	 * @param values 
	 * @param prevVal 
	 */
	public FilterValueControl(Composite parent, boolean multiple, List<String> values, String prevVal) {
		this.multiple = multiple;
		this.values.addAll(values);
		this.simple = false;
		this.prevVal = prevVal;
		createContents(parent);
	}
	
	/**
	 * @param parent
	 * @param prevVal 
	 */
	public FilterValueControl(Composite parent, String prevVal) {
		this.prevVal = prevVal;
		createContents(parent);
	}
	
	
	/**
	 * @param parent
	 */
	protected void createContents(Composite parent) {
		final Shell shell = parent.getShell();
		Composite valueComp = new Composite(parent, SWT.FILL);
		int columns = simple ? 1 : 2;
		GridLayout gridLayout = new GridLayout(columns, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		valueComp.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		valueComp.setLayoutData(gd);
		if (simple) {
			valueText = new Text(valueComp, SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			valueText.setLayoutData(gd);
			if (prevVal != null) {
				valueText.setText(prevVal);
			}
			valueText.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					valueWidgetSelected(e.widget);
				}				
			});
			valueText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					valueModified(e.widget);
				}
			});

			valueLabel = new Label(valueComp, SWT.NONE);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			valueLabel.setLayoutData(gd);
		} else {
			if (!multiple) {
				combo = new StringValueCombo(valueComp, values, true);
				if (prevVal != null) {
					combo.select(prevVal);
				}
				combo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						valueWidgetSelected(e.widget);
					}
					
				});
				valueComp.getParent().layout(true);
			}  else {
				valueText = new Text(valueComp, SWT.BORDER);
				gd = new GridData(GridData.FILL_HORIZONTAL);
				gd.grabExcessHorizontalSpace = true;
				valueText.setLayoutData(gd);
				valueText.setEditable(false);
				valueText.setBackground(ColorConstants.white);
				if (prevVal != null) {
					valueText.setText(prevVal);
				}
				valueText.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						valueWidgetSelected(e.widget);
					}				
				});
				valueText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						valueModified(e.widget);
					}
				});
				
				enumBrowse = new Button(valueComp, SWT.None);
				enumBrowse.setText("Browse");
				enumBrowse.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						String title = "Values";
						String desc = "Select from possible values";
						String[] enums = values.toArray(new String[0]);
						String[] prev = null;
						String str = valueText.getText();
						if (str != null) {
							prev = StringUtils.split(str, ",");				
						}
						QueryAttributesSelectionDialog dialog = new QueryAttributesSelectionDialog(shell, enums, prev, title, desc);
						if (dialog.open() == Window.OK) {
							String selection = StringUtils.join(dialog.getSelectedAttributes(), ",");
							valueText.setText(selection);
							valueWidgetSelected(e.widget);
						}
					}
				});
				valueComp.getParent().layout(true);
			}	
		}
		
	}
	
	/**
	 * @return value
	 */
	public String getValue() {
		if (combo != null) {
			return combo.getSelectedValue();
		} else {
			return valueText.getText();
		}
	}
	
	/**
	 * @param enable
	 */
	public void enableControl(boolean enable) {
		if (combo != null) {
			combo.setEnabled(enable);
		} else {
			valueText.setEnabled(enable);
			String msg = enable ? "" : "(will be generated by DS)";
			if (valueLabel != null) {
				valueLabel.setText(msg);
			}
		}
	}
	
	/**
	 * @return boolean
	 */
	public boolean isEnabled() {
		if (combo != null) {
			return combo.getCombo().isEnabled();
		} else {
			return valueText.isEnabled();
		}
	}
	
	/**
	 * @param widget
	 */
	public abstract void valueWidgetSelected(Widget widget);
	
	/**
	 * @param widget
	 */
	public abstract void valueModified(Widget widget);

}
