package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeySequenceText;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.keys.model.BindingElement;
import org.eclipse.ui.internal.keys.model.KeyController;
import org.eclipse.ui.keys.IBindingService;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyAttributeWidget;

/**
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class SimpleTextWidget extends AbstractPropertyAttributeWidget  {	
	
	private static final String NUMPAD = "Numpad_";
	private static final String NUMPAD_ADD = "NUMPAD_ADD";
	private static final String NUMPAD_DECIMAL = "NUMPAD_DECIMAL";
	private static final String NUMPAD_DIVIDE = "NUMPAD_DIVIDE";
	private static final String NUMPAD_MULTIPLY = "NUMPAD_MULTIPLY";
	private static final String NUMPAD_SUBTRACT = "NUMPAD_SUBTRACT";
	private static final String KEYBOARD = "keyboard";
	private static final String KEYBOARD_SHORTCUT = "Keyboard Shortcut";
	private static final String SHORTCUT = "shortcut";
	private boolean multiline;
	private boolean fillHorizontal = false;
	private Text textControl = null;
	private KeySequenceText shortcutKeySequenceText;
	private KeyController keyController;
	private IBindingService fBindingService;
	private boolean acceptSpaceCharacter = true;
	
	/**
	 * @param attribute
	 * @param label
	 */
	public SimpleTextWidget(EAttribute attribute, String label) {
		this(attribute, label, null, true);
	}
	
	/**
	 * @param attribute
	 * @param label
	 */
	public SimpleTextWidget(EAttribute attribute, String label, boolean acceptSpaceCharacters) {
		this(attribute, label, null, acceptSpaceCharacters);
	}

	/**
	 * @param attribute
	 * @param label
	 * @param tooltip
	 */
	public SimpleTextWidget(EAttribute attribute, String label, String tooltip) {
		this(attribute, label, tooltip, true);
	}
	
	/**
	 * @param attribute
	 * @param label
	 * @param tooltip
	 */
	public SimpleTextWidget(EAttribute attribute, String label, String tooltip, boolean acceptSpaceCharacters) {
		super(attribute, label, tooltip);
		this.acceptSpaceCharacter = acceptSpaceCharacters;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(final Composite body) {
		SWTWidgetFactory.createLabel(body, getLabel(), getTooltip(), getAttribute().isRequired());
		if (isMultiline() || isFillHorizontal()) {
			this.textControl = SWTWidgetFactory.createTextField(body, isMultiline());			
		} else {
			Composite textComp = SWTWidgetFactory.createComposite(body, 2, false);
			this.textControl = SWTWidgetFactory.createTextField(textComp, false);
			SWTWidgetFactory.createFiller(textComp);
		}
		if (getAttribute().isUnsettable()) {
			this.textControl.setEditable(false);
			this.textControl.setText(getAttribute().getDefaultValue().toString());
		}		
		this.textControl.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (isValidInput())
					notifyPropertyFeatureChange(textControl.getText().trim());
			}			
		});	
		
		textControl.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!acceptSpaceCharacter && SWT.SPACE == e.keyCode) {
					// filter space character.
					e.doit = false;
				}
			}
		});
		
		if (getLabel() != null && getLabel().equals(KEYBOARD_SHORTCUT)) {
			textControl.setData(SHORTCUT, KEYBOARD);
			textControl.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					fBindingService.setKeyFilterEnabled(false);
				}

				public void focusLost(FocusEvent e) {
					fBindingService.setKeyFilterEnabled(true);
				}
			});
			textControl.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if (!fBindingService.isKeyFilterEnabled()) {
						fBindingService.setKeyFilterEnabled(true);
					}
				}
			});
			
			shortcutKeySequenceText = new KeySequenceText(this.textControl);
			shortcutKeySequenceText.setKeyStrokeLimit(1);
			shortcutKeySequenceText
					.addPropertyChangeListener(new IPropertyChangeListener() {
						public final void propertyChange(
								final PropertyChangeEvent event) {
							if (!event.getOldValue().equals(event.getNewValue())) {
								final KeySequence keySequence = shortcutKeySequenceText
										.getKeySequence();
								if (!keySequence.isComplete()) {
									return;
								}

								if (keyController!=null && keyController
										.getBindingModel()!=null) {
									
									BindingElement activeBinding = (BindingElement) keyController
											.getBindingModel()
											.getSelectedElement();
									if (activeBinding != null) {
										activeBinding.setTrigger(keySequence);
									}
								}
								//when number keys are pressed in numpad keyboard retain number and exclude numpad_ 
								String sequence = keySequence.toString();
								if (sequence.contains(NUMPAD_ADD)) {
									textControl.setText(sequence.replaceAll(NUMPAD_ADD,"+"));
								} else if (sequence.contains(NUMPAD_DECIMAL)) {
									textControl.setText(sequence.replaceAll(NUMPAD_DECIMAL,"."));
								} else if (sequence.contains(NUMPAD_DIVIDE)) {
									textControl.setText(sequence.replaceAll(NUMPAD_DIVIDE,"/"));
								} else if (sequence.contains(NUMPAD_MULTIPLY)) {
									textControl.setText(sequence.replaceAll(NUMPAD_MULTIPLY,"*"));
								} else if (sequence.contains(NUMPAD_SUBTRACT)) {
									textControl.setText(sequence.replaceAll(NUMPAD_SUBTRACT,"-"));
								} else  if (keySequence.format().contains(NUMPAD)){
									String reducedKey = keySequence.toString().replaceAll("NUMPAD_", "");
									textControl.setText(reducedKey);
								}//below invalid special keys were discarded from displaying 
								else if(keySequence.format().contains("PageUp") || keySequence.format().contains("PageDown") || keySequence.format().contains("Up") || keySequence.format().contains("Down")
										|| keySequence.format().contains("Left") || keySequence.format().contains("Right")){
									textControl.setText("");
								} 
								textControl.setSelection(textControl
										.getTextLimit());
							}
						}
					});
		}
		
	}
	
	/**
	 * @return
	 */
	private boolean isValidInput() {
		String str = textControl.getText();
		// check if the attribute is ID & check for uniqueness
		String originalVal = null;
		Object objVal = getElement().eGet(getAttribute());
		if (objVal != null) {
			originalVal = objVal.toString();
		} else {
			originalVal = "";
		}
		if (str!=null && str.equals(originalVal)) {
			return false;
		}
		if (getAttribute().isRequired()){
			if (str == null || str.trim().length() == 0) {
				textControl.setText(originalVal);
			} else if (str != null && str.trim().length() > 0 
					&& !str.equals(originalVal)) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyAttributeWidget#initPropertyControls()
	 */
	@Override
	public void initPropertyControls() {
		if (getElement() != null) {
			Object data = getElement().eGet(getAttribute());			
			if (data == null) {
				data = "";
			}
			if(textControl != null){
				textControl.setText(data.toString());
				if (textControl.getData(SHORTCUT)!=null) {
					keyController = new KeyController();
					keyController.init(PlatformUI.getWorkbench());
					fBindingService = (IBindingService) PlatformUI.getWorkbench()
							.getService(IBindingService.class);
				}
			}
		}		
	}
	
	/**
	 * @return the multiline
	 */
	public boolean isMultiline() {
		return multiline;
	}

	/**
	 * @param multiline the multiline to set
	 */
	public void setMultiline(boolean multiline) {
		this.multiline = multiline;
	}
	/**
	 * @return the fillHorizontal
	 */
	public boolean isFillHorizontal() {
		return fillHorizontal;
	}

	/**
	 * @param fillHorizontal the fillHorizontal to set
	 */
	public void setFillHorizontal(boolean fillHorizontal) {
		this.fillHorizontal = fillHorizontal;
	}


}
