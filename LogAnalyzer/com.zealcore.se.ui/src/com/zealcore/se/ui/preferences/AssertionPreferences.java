package com.zealcore.se.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.zealcore.se.core.ifw.assertions.AssertionRunner;
import com.zealcore.se.ui.SeUiPlugin;

public class AssertionPreferences extends PreferencePage implements
        IWorkbenchPreferencePage {

    private Text textbox;

    private static final String ASSERTION_HITS = "assertion_hits";

    public AssertionPreferences() {}

    public AssertionPreferences(final String title) {
        super(title);
    }

    public AssertionPreferences(final String title, final ImageDescriptor image) {
        super(title, image);
    }

    @Override
    protected Control createContents(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FormLayout());

        final Group eventsGroup = new Group(container, SWT.NONE);
        eventsGroup.setText("Assertion Option");

        final FormData fdeventsGroup = new FormData();
        fdeventsGroup.bottom = new FormAttachment(80, 0);
        fdeventsGroup.right = new FormAttachment(100, -5);
        fdeventsGroup.top = new FormAttachment(0, 15);
        fdeventsGroup.left = new FormAttachment(0, 5);
        eventsGroup.setLayoutData(fdeventsGroup);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        eventsGroup.setLayout(gridLayout);

        Label label = new Label(eventsGroup, SWT.NONE);
        label.setText("Max number of results : ");

        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = GridData.CENTER;
        textbox = new Text(eventsGroup, SWT.BORDER);
        textbox.setText(String.valueOf(String.valueOf(SeUiPlugin.getDefault()
                .getPreferenceStore().getInt(ASSERTION_HITS))));
        textbox.setLayoutData(gridData);

        textbox.addKeyListener(new KeyListener(){
            public void keyPressed(final KeyEvent e) {
                int keyValue = e.keyCode;
                if (keyValue == 8 || keyValue == 13 || keyValue == 127
                        || keyValue == 16777219 || keyValue == 16777220) {
                    e.doit = true;
                    return;
                }
                try {
                    Long.parseLong( textbox.getText() + String.valueOf(e.character));
                } catch (Exception ex) {
                    e.doit = false;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });

        textbox.addModifyListener(new ModifyListener(){

            public void modifyText(final ModifyEvent e) {
                if(textbox.getText().equals("")){
                    setValid(false);
                    setErrorMessage("Invalid input");
                }else if(Long.valueOf(textbox.getText()).longValue() < 
                		Long.valueOf(AssertionRunner.MIN_ASSERTION_LIMIT).longValue()){
                    setValid(false);
                    setErrorMessage("Value must be greater or equal to " + AssertionRunner.MIN_ASSERTION_LIMIT);
                }else{
                    setValid(true);
                    setErrorMessage(null);
                }
            }
        });

        return container;
    }

    @Override
    public void setErrorMessage(final String newMessage) {
        super.setErrorMessage(newMessage);
    }

    @Override
    protected void performApply() {
        SeUiPlugin.getDefault().getPreferenceStore().setValue(ASSERTION_HITS,
                Integer.parseInt(textbox.getText().trim()));
        getApplyButton().setEnabled(false);
    }

    @Override
    public boolean performOk() {
        performApply();
        return super.performOk();
    }

    @Override
    protected void performDefaults() {
        textbox.setText(String.valueOf(AssertionRunner.MIN_ASSERTION_LIMIT));
        getApplyButton().setEnabled(true);
    }

    public void init(final IWorkbench workbench) {
        SeUiPlugin.getDefault().getPreferenceStore().setDefault(ASSERTION_HITS,
                AssertionRunner.MIN_ASSERTION_LIMIT);
    }

}
