package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.EventNature;
import com.odcgroup.page.model.Widget;

/**
 * Control part of the general tab of the event definition dialog
 * (toggle for simplified & advanced event definition)
 *
 * @author pkk
 *
 */
public class GenericEventControl implements  IEventControl, SelectionListener {

	/** */
	private Widget widget;
	/**	 */
	private Event event;
	/**  */
	private Composite parent;
	/** radio button for simple event*/
	private Button simpleRadio;
	/** radio button for advanced event*/
	private Button advancedRadio;
	/**  */
	private Composite controlBody;
	/** */
	private IEventControl eventControl;
	/** */
	private EventModelChangeListener eventListener;

	/**
	 * @param parent
	 * @param widget
	 * @param event
	 */
	public GenericEventControl(Composite parent, Widget widget, Event event) {
		this.event = event;
		this.widget = widget;
		createControls(parent);
	}

	/**
	 * @param parent
	 */
	protected void createControls(Composite parent) {
		this.parent = parent;
		// radio buttons section
		createChoiceSection(parent);

		initializeControls();
	}

	/**
	 * @param body
	 */
	protected void createChoiceSection(Composite body) {

		Composite choiceSection = new Composite(body, SWT.FILL);
		GridLayout gridLayout = new GridLayout(4, false);
		choiceSection.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		choiceSection.setLayoutData(gridData);

		simpleRadio = new Button(choiceSection, SWT.RADIO);
		simpleRadio.setBackground(choiceSection.getBackground());
		simpleRadio.setText("Simplified Event");
		simpleRadio.setSelection(false);
		simpleRadio.addSelectionListener(this);

		advancedRadio = new Button(choiceSection, SWT.RADIO);
		advancedRadio.setBackground(choiceSection.getBackground());
		advancedRadio.setText("Advanced Event");
		advancedRadio.setSelection(false);
		advancedRadio.addSelectionListener(this);

	}

	/**
	 *
	 */
	private void initializeControls() {
		if (event.isSimplifiedEvent()) {
			simpleRadio.setSelection(true);
			createSimpleControls();
		} else {
			advancedRadio.setSelection(true);
			createAdvancedControls();
		}
	}

	/**
	 *
	 */
	private void createSimpleControls() {
		createControlBody(parent);
		eventControl = new SimpleEventDefinitionControl(controlBody, event, widget);
		parent.layout();
	}

	/**
	 *
	 */
	private void createAdvancedControls() {
		createControlBody(parent);
		eventControl = new AdvancedEventDefinitionControl(controlBody, event, widget);
		parent.layout();
	}



	/**
	 * composite that is used to toggle simplified/advanced controls
	 *
	 * @param body
	 */
	private void createControlBody(Composite body) {
		if (controlBody != null) {
			controlBody.dispose();
			controlBody = null;
		}
		if (eventControl != null) {
			eventControl = null;
		}
		controlBody = new Composite(body, SWT.FILL);
		GridLayout gridLayout = new GridLayout(1, false);
		controlBody.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		controlBody.setLayoutData(gridData);
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.widget instanceof Button){
			Button btn = (Button)e.widget;
			if (btn.getSelection()) {
				int eventType = -1;
				if (e.widget.equals(simpleRadio) && !event.isSimplifiedEvent()) {
					eventType = EventNature.SIMPLIFIED_VALUE;
				} else if (e.widget.equals(advancedRadio) && !event.isAdvancedEvent()) {
					eventType = EventNature.ADVANCED_VALUE;
				}
				handleSelectionEvent(eventType);
			}
		}
	}

	/**
	 * @param eventType
	 */
	private void handleSelectionEvent(int eventType) {
		boolean isOK = false;
		if (eventType > -1 ) {
			isOK = confirmToggleEvent(eventType);
		}
		if (isOK) {
			if (EventNature.SIMPLIFIED_VALUE == eventType) {
				createSimpleControls();
			} else if (EventNature.ADVANCED_VALUE == eventType) {
				createAdvancedControls();
			}
		} else {
			advancedRadio.setSelection(event.isAdvancedEvent());
			simpleRadio.setSelection(event.isSimplifiedEvent());
		}
	}

	 /**
     * Confirmation Dialog toggle between advanced/simplified event
     *
     * @param eventType
     * @return boolean
     */
	private boolean confirmToggleEvent(int eventType) {

		//DS-3916 Confirmation no longer required going from
		//simple event to advanced event
		if (eventType == EventNature.SIMPLIFIED_VALUE) {
			boolean okPressed = MessageDialog.openConfirm(parent.getShell(),
					"Confirm", "Converting from Simplified Event to Advanced Event will remove any existing attributes assigned to functions, are you sure you want to convert ?");
			if (okPressed){
				event = EventUtil.changeAdvancedEventToSimple(event);
				notifyEventModelChange(event);
				return true;
			}
		} else if (eventType == EventNature.ADVANCED_VALUE) {
			event = EventUtil.changeSimpleToAdvancedEvent(event);
			notifyEventModelChange(event);
			return true;
		}

		return false;
	}


	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#getErrorMessage()
	 */
	public String getErrorMessage() {
		if (eventControl != null) {
			return eventControl.getErrorMessage();
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#isValid()
	 */
	public boolean isValid() {
		if (eventControl != null) {
			return eventControl.isValid();
		}
		return false;
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#addEventModelChangeListener(com.odcgroup.page.pageflow.integration.ui.dialog.controls.EventModelChangeListener)
	 */
	public void addEventModelChangeListener(EventModelChangeListener listener) {
		this.eventListener = listener;
	}

	/**
	 * @see com.odcgroup.page.pageflow.integration.ui.dialog.controls.IEventControl#notifyEventModelChange(com.odcgroup.page.model.Event)
	 */
	public void notifyEventModelChange(Event event) {
		if (eventListener != null) {
			eventListener.eventModelChanged(event);
		}
	}

}
