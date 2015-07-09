package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventTypeConstants;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.DataTypeFilter;
import com.odcgroup.page.ui.properties.table.controls.DataTypeCombo;
import com.odcgroup.pageflow.editor.diagram.custom.dialog.PageflowSelectionDialog;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Simple Event Control
 *
 * @author pkk
 *
 */
public class SimpleEventDefinitionControl  implements  IEventControl, SelectionListener, ModifyListener {

	/**	 */
	private Composite parent;
	/**	 */
	private Button transitionRadio;
	/**	 */
	private Button widgetGroupRefChkBox;
	/**	 */
	private Button pageflowRadio;
	/**  */
	private Button browse;
	/**	 */
	private Text transitionText;
	/**	 */
	private Text widgetGroupRefText;
	/**	 */
	private Text pageflowText;
	/**	 */
	private DataTypeCombo targetCombo;
	/** */
	private Event event;
	/** */
	private Widget widget;
	/** */
	private boolean pageflowBased = false;
	/**  */
	private String errorMessage = null;
	
	/**
	 * @param parent
	 * @param event
	 */
	public SimpleEventDefinitionControl(Composite parent, Event event, Widget widget) {
		this.event = event;
		this.parent = parent;
		this.widget = widget;
		createControls(parent);
		initializeControls(false);
	}	
	
	
	/**
	 * @param parent
	 */
	private Composite createControls(Composite parent) {
		
		Composite simpleBody = new Composite(parent, SWT.FILL | SWT.BORDER);
		GridLayout gridLayout = new GridLayout(3, false);
		simpleBody.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		simpleBody.setLayoutData(gridData);
		
		Label targetLabel = new Label(simpleBody, SWT.LEFT);
		targetLabel.setText("      Target: ");
		targetCombo = new DataTypeCombo(simpleBody, EventUtil.getTargetDataType(event), new TargetFilter(), true);
		targetCombo.addSelectionListener(this);
		new Label(simpleBody, SWT.LEFT);

		transitionRadio = new Button(simpleBody, SWT.RADIO);
		transitionRadio.setText(" Outgoing Transition's Id: ");
		transitionRadio.setBackground(simpleBody.getBackground());
		transitionRadio.addSelectionListener(this);		

		transitionText = new Text(simpleBody, SWT.BORDER | SWT.FILL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalAlignment = GridData.CENTER;
		transitionText.setLayoutData(data);
		transitionText.addModifyListener(this);

		new Label(simpleBody, SWT.LEFT);
		
		widgetGroupRefChkBox = new Button(simpleBody, SWT.CHECK);
		data = new GridData();
		data.horizontalIndent= 35;
		widgetGroupRefChkBox.setLayoutData(data);
		widgetGroupRefChkBox.setText(" Widget Group Ref.: ");
		widgetGroupRefChkBox.setBackground(simpleBody.getBackground());
		widgetGroupRefChkBox.addSelectionListener(this);

		widgetGroupRefText = new Text(simpleBody, SWT.BORDER | SWT.FILL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalAlignment = GridData.CENTER;
		widgetGroupRefText.setLayoutData(data);
		widgetGroupRefText.addModifyListener(this);

		new Label(simpleBody, SWT.LEFT);
		
		pageflowRadio = new Button(simpleBody, SWT.RADIO);
		pageflowRadio.setText(" New Pageflow: ");
		pageflowRadio.setBackground(simpleBody.getBackground());
		pageflowRadio.addSelectionListener(this);

		pageflowText = new Text(simpleBody, SWT.BORDER | SWT.FILL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalAlignment = GridData.CENTER;
		pageflowText.setLayoutData(data);
		pageflowText.setEditable(false);

		browse = new Button(simpleBody, SWT.PUSH);
		browse.setText("Browse");
		browse.addSelectionListener(this);
		
		return simpleBody;
	}
	
	/**
	 * @param afterToggle
	 */
	protected void initializeControls(boolean afterToggle) {
		if (EventUtil.isSimplifiedEventWithPageflow(event) || afterToggle) {
			widgetGroupRefChkBox.setEnabled(false);
			widgetGroupRefChkBox.setSelection(false);
			widgetGroupRefText.setText("");
			pageflowRadio.setSelection(true);
			setInputControlsEnabled(false);
			pageflowText.setText(EventUtil.getPageflowURI(event));			
			pageflowBased = true;
		} else {
			widgetGroupRefChkBox.setEnabled(true);
			String widgetGroupRef = EventUtil.getWidgetGroupRefParameter(event).getValue();
			widgetGroupRefChkBox.setSelection(!StringUtils.isEmpty(widgetGroupRef));
			widgetGroupRefText.setText(widgetGroupRef);				
			transitionRadio.setSelection(true);
			setInputControlsEnabled(true);
			transitionText.setText(EventUtil.getTransitionId(event));
			pageflowBased = false;
		}
		
		String target = EventUtil.getTargetParameter(event).getValue();
		if (StringUtils.isEmpty(target)) {
			target = ParameterTypeConstants.TARGET_DEFAULT_VALUE;
			setTarget(target);
		}
		targetCombo.select(target);
		
	}
	
	/**
	 * @param value
	 */
	private void setTarget(String value) {
		
		
	   EventUtil.getTargetParameter(event).setValue(value);
		
	}
	
	/**
	 * @param value
	 */
	private void setWidgetGroupRef(String value) {
		EventUtil.getWidgetGroupRefParameter(event).setValue(value);
	}

	/**
	 * @param text
	 */
	private void updateTransitionId(String text) {
		if (!StringUtils.isEmpty(text))
			EventUtil.getTransitionParameter(event).setValue(text);
	}
	
	/**
	 * @param text
	 */
	private void updateWidgetGroupRef(String text) {
		if (!StringUtils.isEmpty(text))
			EventUtil.getWidgetGroupRefParameter(event).setValue(text);
	}
	
	/**
	 * handles enabling/disabling of input controls
	 * 
	 * @param enable
	 */
	private void setInputControlsEnabled(boolean enable) {
		transitionText.setEnabled(enable);
		widgetGroupRefText.setEnabled(widgetGroupRefChkBox.getSelection());
		pageflowText.setEnabled(!enable);
		if (!enable) {
			pageflowText.setBackground(ColorConstants.white);
		} else {
			pageflowText.setBackground(parent.getBackground());
		}
		browse.setEnabled(!enable);
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (e.widget instanceof Button) {
			Button btn = (Button) e.widget;
			if (btn.equals(transitionRadio) && btn.getSelection()) {
				if (pageflowBased) { 
					if(confirmToggleEvent(true)) {
						setInputControlsEnabled(true);
						pageflowBased = false;
					} else {
						transitionRadio.setSelection(false);
						widgetGroupRefChkBox.setSelection(false);
						pageflowRadio.setSelection(true);
					}
				}
			} else if (btn.equals(widgetGroupRefChkBox)) {
				String widgetGroupRef = "";
				if (btn.getSelection()) {
					widgetGroupRef = ParameterTypeConstants.WIDGET_GROUP_REF_DEFAULT_VALUE;
				} 
				widgetGroupRefText.setText(widgetGroupRef);
				setWidgetGroupRef(widgetGroupRef);
				widgetGroupRefText.setEnabled(btn.getSelection());
				
			} else if (btn.equals(pageflowRadio) && btn.getSelection()) {
				if (!pageflowBased) { 
					if(confirmToggleEvent(false)) {
						setInputControlsEnabled(false);
						pageflowBased = true;
					} else {
						transitionRadio.setSelection(true);
						pageflowRadio.setSelection(false);					
					}
				}
			} else if (btn.equals(browse)) {
				handleBrowse();
			}
		} else if(e.widget == targetCombo.getCombo()){
		     setTarget(targetCombo.getSelectedValue().getValue());
		}
	}

	/**
	 * launches dialog to select a suitable pageflow
	 * 
	 */
	private void handleBrowse() {
		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		PageflowSelectionDialog dialog = PageflowSelectionDialog.createDialog(shell, ofsProject);
		dialog.setMultipleSelection(false);
		if (dialog.open() == 0) {
			final Pageflow root = (Pageflow) dialog.getFirstResult();
			URI uri = root.eResource().getURI();
			pageflowText.setText(uri.toString());
			EventUtil.getPageflowParameter(event).setValue(uri.toString());
		}
	}
	
	/**
     * Confirmation Dialog toggle between simplified/advanced event
     * 
     * @param transition
     * @return boolean
     */
    private boolean confirmToggleEvent(boolean transition) {
    	String newEvent = null;
    	String oldEvent = null;
    	if (transition) {
    		newEvent = "Outgoing Transition ID";
    		oldEvent = "New Pageflow";
    	} else {
    		newEvent = "New Pageflow";
    		oldEvent = "Outgoing Transition ID";
    		
    	}
    	boolean okpressed = MessageDialog.openConfirm(parent.getShell(), 
				"Confirm", 
				"Are you sure you want to define a  \'"
				+newEvent+"\'?\n\nAll data with the \'"+oldEvent+"\' will be lost!");
		if (okpressed){
			if (transition) {
				pageflowText.setText("");
				String val = EventUtil.SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE;
				EventUtil.getPageflowParameter(event).setValue(val);
				//EventUtil.getTargetParameter(event).setValue("");
				Parameter param = ModelFactory.eINSTANCE.createParameter();
				param.setName(EventUtil.FLOW_ACTION_PARAMETER);
				param.setUserDefined(true);
				event.getParameters().add(param);
				initializeControls(false);
			} else {
				transitionText.setText("");
				event.getParameters().remove(EventUtil.getTransitionParameter(event));
				EventUtil.getPageflowParameter(event).setValue("");
				Parameter p = EventUtil.getTransitionParameter(event);
				this.event.getParameters().remove(p);
				
				widgetGroupRefText.setText("");
				setWidgetGroupRef("");
				
				initializeControls(true);
			}
			return true;
		}
		return false;
    }

    /**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		if (e.widget instanceof Text) {
			Text txt = (Text) e.widget;
			if (txt == transitionText) {
				updateTransitionId(txt.getText());
			} else if (txt == widgetGroupRefText) {
				updateWidgetGroupRef(txt.getText());
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}


	/* (non-Javadoc)
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#isValid()
	 */
	@Override
	public boolean isValid() {
		if (transitionRadio.getSelection()) {
			if (StringUtils.isEmpty(transitionText.getText())) {
				errorMessage = "Transition ID is required";
				return false;
			}
			if (widgetGroupRefChkBox.getSelection()) {
				if (StringUtils.isEmpty(widgetGroupRefText.getText())) {
					errorMessage = "Widget Group Ref is required";
					return false;
				}
			}
		} else if (pageflowRadio.getSelection()) {
			if (StringUtils.isEmpty(pageflowText.getText())) {
				errorMessage = "Pageflow is not selected";
				return false;			
			} else if (StringUtils.isEmpty(targetCombo.getSelectedValue().getValue())) {
				errorMessage = "Target cannot be empty";
				return false;
			}
		}
		errorMessage = null;
		return true;	
	}	
	
	
	
	/**
	 *
	 */
	static class TargetFilter implements DataTypeFilter {

		/**
		 * @see com.odcgroup.page.model.filter.DataTypeFilter#filter(com.odcgroup.page.metamodel.DataType)
		 */
		public List<DataValue> filter(DataType dataType) {
			List<DataValue> values = new ArrayList<DataValue>();
			values.addAll(dataType.getValues());
			for (DataValue dataValue : dataType.getValues()) {
				if (dataValue.getValue().equals("")) {
					values.remove(dataValue);
				}
			}
			return values;
		}
		
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#addEventModelChangeListener(com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventModelChangeListener)
	 */
	public void addEventModelChangeListener(EventModelChangeListener listener) {		
	}


	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#notifyEventModelChange(com.odcgroup.page.model.Event)
	 */
	public void notifyEventModelChange(Event event) {		
	}

}
