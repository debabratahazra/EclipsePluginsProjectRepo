package com.odcgroup.page.ui.properties.sections.matrix;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.InsertConditionalCssClassCommand;
import com.odcgroup.page.ui.command.matrix.UpdateConditionalClassCommand;

/**
 *
 * @author pkk
 *
 */
public class CssConditionControl extends Composite {
	
	private IConditionalCssClass condition;
	private boolean update = false;
	private ICssClass cssClass = null;
	
	private Text namefld;
	private Text codefld;
	private Text resultfld;

	/**
	 * @param parent
	 * @param condition
	 * @param update
	 */
	public CssConditionControl(ICssClass cssClass, Composite parent, IConditionalCssClass condition, boolean update) {
		super(parent, SWT.None);
		this.cssClass = cssClass;
		this.condition = condition;
		this.update = update;
		createControls(parent);
	}
	
	/**
	 * @param composite
	 */
	private void createControls(Composite composite) {		

		Composite body = new Group(composite, SWT.SHADOW_ETCHED_IN);
		GridLayout gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		new Label(body, SWT.NONE).setText("Name");
		namefld = new Text(body, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		namefld.setLayoutData(data);
		namefld.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateChanges();
			}			
		});
		
		Label code = new Label(body, SWT.NONE);
		code.setText("Java code");
		code.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		codefld = new Text(body, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		data = new GridData();
		data.heightHint = 200;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		codefld.setLayoutData(data);
		codefld.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateChanges();
			}			
		});
		
		new Label(body, SWT.NONE).setText("Result");
		resultfld = new Text(body, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		resultfld.setLayoutData(data);
		resultfld.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateChanges();
			}			
		});	
		
		if (update) {
			namefld.setText(condition.getName().getValue());
			codefld.setText(condition.getCondition().getValue());
			resultfld.setText(condition.getResult().getValue());
		}
	}
	
	/**
	 * @return
	 */
	private void updateChanges() {
		BaseCommand cmd = null;
		if (!update) {
			if (!StringUtils.isEmpty(namefld.getText()) 
					&& !StringUtils.isEmpty(codefld.getText()) 
					&& !StringUtils.isEmpty(resultfld.getText())) {
				condition.getName().setValue(namefld.getText());
				condition.getCondition().setValue(codefld.getText());
				condition.getResult().setValue(resultfld.getText());
				cmd = new InsertConditionalCssClassCommand(cssClass, condition);
			}
		} else {
			if (!StringUtils.isEmpty(namefld.getText()) 
				&& !StringUtils.isEmpty(codefld.getText()) 
				&& !StringUtils.isEmpty(resultfld.getText())) {
				String name = namefld.getText();
				String code = codefld.getText();			
				String result = resultfld.getText();						
				cmd = new UpdateConditionalClassCommand(condition, name, code, result);
			}
		}
		if (cmd != null && cmd.canExecute()) {
			executeCommand(cmd);
		}
	}
	
	/**
	 * @param command
	 */
	public void executeCommand(BaseCommand command) {
		//
	}

}
