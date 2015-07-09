package com.odcgroup.ocs.support.ui.preferences.pages;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * File Field chooser which doesn't remove its error message from
 * the page when it loses the focus.
 * @author yan
 */
public abstract class OcsFileFieldEditor extends FileFieldEditor {

    public OcsFileFieldEditor(String name, String labelText, Composite parent) {
    	super(name, labelText, parent);
        setValidateStrategy(VALIDATE_ON_KEY_STROKE);
        getTextControl().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	refreshValidState();
            }
            public void keyReleased(KeyEvent e) {
            	refreshValidState();
            }
        });
        getTextControl().addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                refreshValidState();
            }

            public void focusLost(FocusEvent e) {
            	refreshValidState();
            }
        });
    }

	@Override
	protected boolean checkState() {
		return checkPageState();
	}

	/**
	 * @return
	 */
	protected abstract boolean checkPageState();

}
