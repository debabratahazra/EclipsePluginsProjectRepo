package com.odcgroup.menu.editor.properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;

import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.help.OfsHelpHelper;

public class MenuPropertiesViewActionHandler {
	
	/**
	 *
	 */
	private class SelectAllActionHandler extends Action {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.Event)
		 */
		public void runWithEvent(Event event) {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				if (activeTextControl instanceof Text)
					((Text) activeTextControl).selectAll();
				if (activeTextControl instanceof StyledText)
					((StyledText) activeTextControl).selectAll();
				return;
			}
			if (selectAllAction != null) {
				selectAllAction.runWithEvent(event);
				return;
			} else {
				return;
			}
		}

		/**
		 * 
		 */
		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (selectAllAction != null) {
				setEnabled(selectAllAction.isEnabled());
				return;
			} else {
				setEnabled(false);
				return;
			}
		}

		/**
		 * 
		 */
		protected SelectAllActionHandler() {
			super("Select All");
			setId("TextSelectAllActionHandler");
			setEnabled(false);
		}
	}

	private class PasteActionHandler extends Action {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.Event)
		 */
		public void runWithEvent(Event event) {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				if (activeTextControl instanceof Text)
					((Text) activeTextControl).paste();
				if (activeTextControl instanceof StyledText)
					((StyledText) activeTextControl).paste();
				return;
			}
			if (pasteAction != null) {
				pasteAction.runWithEvent(event);
				return;
			} else {
				return;
			}
		}

		/**
		 * 
		 */
		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (pasteAction != null) {
				setEnabled(pasteAction.isEnabled());
				return;
			} else {
				setEnabled(false);
				return;
			}
		}

		/**
		 * 
		 */
		protected PasteActionHandler() {
			super("Paste");
			setId("TextPasteActionHandler");
			setEnabled(false);
		}
	}

	private class CopyActionHandler extends Action {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.Event)
		 */
		public void runWithEvent(Event event) {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				if (activeTextControl instanceof Text)
					((Text) activeTextControl).copy();
				if (activeTextControl instanceof StyledText)
					((StyledText) activeTextControl).copy();
				return;
			}
			if (copyAction != null) {
				copyAction.runWithEvent(event);
				return;
			} else {
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (copyAction != null) {
				setEnabled(copyAction.isEnabled());
				return;
			} else {
				setEnabled(false);
				return;
			}
		}

		protected CopyActionHandler() {
			super("Copy");
			setId("TextCopyActionHandler");
			setEnabled(false);
		}
	}

	private class CutActionHandler extends Action {

		public void runWithEvent(Event event) {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				if (activeTextControl instanceof Text)
					((Text) activeTextControl).cut();
				if (activeTextControl instanceof StyledText)
					((StyledText) activeTextControl).cut();
				return;
			}
			if (cutAction != null) {
				cutAction.runWithEvent(event);
				return;
			} else {
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (cutAction != null) {
				setEnabled(cutAction.isEnabled());
				return;
			} else {
				setEnabled(false);
				return;
			}
		}

		protected CutActionHandler() {
			super("Cut");
			setId("TextCutActionHandler");
			setEnabled(false);
		}
	}

	private class DeleteActionHandler extends Action {

		public void runWithEvent(Event event) {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				if (activeTextControl instanceof Text)
					((Text) activeTextControl).clearSelection();
				if (!(activeTextControl instanceof StyledText))
					;
				return;
			}
			if (deleteAction != null) {
				deleteAction.runWithEvent(event);
				return;
			} else {
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (deleteAction != null) {
				setEnabled(deleteAction.isEnabled());
				return;
			} else {
				setEnabled(false);
				return;
			}
		}

		protected DeleteActionHandler() {
			super("Delete");
			setId("TextDeleteActionHandler");
			setEnabled(false);
		}
	}

	private class PropertyChangeListener implements IPropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			if (activeTextControl != null)
				return;
			if (event.getProperty().equals("enabled")) {
				Boolean bool = (Boolean) event.getNewValue();
				actionHandler.setEnabled(bool.booleanValue());
			}
		}

		private IAction actionHandler;

		protected PropertyChangeListener(IAction actionHandler) {
			this.actionHandler = actionHandler;
		}
	}

	/**
	 *
	 */
	private class TextControlListener implements Listener {

		public void handleEvent(Event event) {
			switch (event.type) {
			case 26: // '\032'
				activeTextControl = (Control) event.widget;
				updateActionsEnableState();
				break;

			case 27: // '\033'
				activeTextControl = null;
				updateActionsEnableState();
				break;
			}
		}

		private TextControlListener() {
		}

	}

	/**
	 * @param actionBar
	 */
	public MenuPropertiesViewActionHandler(IActionBars actionBar) {
		textDeleteAction = new DeleteActionHandler();
		textCutAction = new CutActionHandler();
		textCopyAction = new CopyActionHandler();
		textPasteAction = new PasteActionHandler();
		textSelectAllAction = new SelectAllActionHandler();
		deleteActionListener = new PropertyChangeListener(textDeleteAction);
		cutActionListener = new PropertyChangeListener(textCutAction);
		copyActionListener = new PropertyChangeListener(textCopyAction);
		pasteActionListener = new PropertyChangeListener(textPasteAction);
		selectAllActionListener = new PropertyChangeListener(
				textSelectAllAction);
		textControlListener = new TextControlListener();
		mouseAdapter = new MouseAdapter() {

			public void mouseUp(MouseEvent e) {
				updateActionsEnableState();
			}

		};
		keyAdapter = new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				updateActionsEnableState();
			}

		};
		actionBar.setGlobalActionHandler("cut", textCutAction);
		actionBar.setGlobalActionHandler("copy", textCopyAction);
		actionBar.setGlobalActionHandler("paste", textPasteAction);
		actionBar.setGlobalActionHandler("selectAll", textSelectAllAction);
		actionBar.setGlobalActionHandler("delete", textDeleteAction);
		
    	actionBar.getToolBarManager().add(OfsHelpHelper.createHelpAction(IOfsHelpContextIds.PAGEFLOW_PROPERTIES));
	}

	/**
	 * @param textControl
	 */
	public void addText(Text textControl) {
		if (textControl == null) {
			return;
		} else {
			activeTextControl = textControl;
			textControl.addListener(26, textControlListener);
			textControl.addListener(27, textControlListener);
			textControl.addKeyListener(keyAdapter);
			textControl.addMouseListener(mouseAdapter);
			return;
		}
	}

	/**
	 * @param control
	 */
	public void addControl(Control control) {
		if (control == null)
			return;
		if (control instanceof Text) {
			addText((Text) control);
			return;
		}
		if (control instanceof StyledText) {
			activeTextControl = control;
			control.addListener(26, textControlListener);
			control.addListener(27, textControlListener);
			control.addKeyListener(keyAdapter);
			control.addMouseListener(mouseAdapter);
		}
	}

	/**
	 * 
	 */
	public void dispose() {
		setCutAction(null);
		setCopyAction(null);
		setPasteAction(null);
		setSelectAllAction(null);
		setDeleteAction(null);
	}

	/**
	 * @param control
	 */
	public void removeControl(Control control) {
		if (control == null) {
			return;
		} else {
			control.removeListener(26, textControlListener);
			control.removeListener(27, textControlListener);
			control.removeMouseListener(mouseAdapter);
			control.removeKeyListener(keyAdapter);
			activeTextControl = null;
			updateActionsEnableState();
			return;
		}
	}

	/**
	 * @param action
	 */
	public void setCopyAction(IAction action) {
		if (copyAction == action)
			return;
		if (copyAction != null)
			copyAction.removePropertyChangeListener(copyActionListener);
		copyAction = action;
		if (copyAction != null)
			copyAction.addPropertyChangeListener(copyActionListener);
		textCopyAction.updateEnabledState();
	}

	/**
	 * @param action
	 */
	public void setCutAction(IAction action) {
		if (cutAction == action)
			return;
		if (cutAction != null)
			cutAction.removePropertyChangeListener(cutActionListener);
		cutAction = action;
		if (cutAction != null)
			cutAction.addPropertyChangeListener(cutActionListener);
		textCutAction.updateEnabledState();
	}

	/**
	 * @param action
	 */
	public void setPasteAction(IAction action) {
		if (pasteAction == action)
			return;
		if (pasteAction != null)
			pasteAction.removePropertyChangeListener(pasteActionListener);
		pasteAction = action;
		if (pasteAction != null)
			pasteAction.addPropertyChangeListener(pasteActionListener);
		textPasteAction.updateEnabledState();
	}

	/**
	 * @param action
	 */
	public void setSelectAllAction(IAction action) {
		if (selectAllAction == action)
			return;
		if (selectAllAction != null)
			selectAllAction
					.removePropertyChangeListener(selectAllActionListener);
		selectAllAction = action;
		if (selectAllAction != null)
			selectAllAction.addPropertyChangeListener(selectAllActionListener);
		textSelectAllAction.updateEnabledState();
	}

	/**
	 * @param action
	 */
	public void setDeleteAction(IAction action) {
		if (deleteAction == action)
			return;
		if (deleteAction != null)
			deleteAction.removePropertyChangeListener(deleteActionListener);
		deleteAction = action;
		if (deleteAction != null)
			deleteAction.addPropertyChangeListener(deleteActionListener);
		textDeleteAction.updateEnabledState();
	}

	/**
	 * 
	 */
	void updateActionsEnableState() {
		textCutAction.updateEnabledState();
		textCopyAction.updateEnabledState();
		textPasteAction.updateEnabledState();
		textSelectAllAction.updateEnabledState();
		textDeleteAction.updateEnabledState();
	}

	private DeleteActionHandler textDeleteAction;
	private CutActionHandler textCutAction;
	private CopyActionHandler textCopyAction;
	private PasteActionHandler textPasteAction;
	private SelectAllActionHandler textSelectAllAction;
	IAction deleteAction;
	IAction cutAction;
	IAction copyAction;
	IAction pasteAction;
	IAction selectAllAction;
	private IPropertyChangeListener deleteActionListener;
	private IPropertyChangeListener cutActionListener;
	private IPropertyChangeListener copyActionListener;
	private IPropertyChangeListener pasteActionListener;
	private IPropertyChangeListener selectAllActionListener;
	private Listener textControlListener;
	Control activeTextControl;
	private MouseAdapter mouseAdapter;
	private KeyAdapter keyAdapter;
}
