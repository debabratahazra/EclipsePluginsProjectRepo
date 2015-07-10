package com.zealcore.se.iw.wizard;

import java.io.File;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.zealcore.se.iw.GenericTextImportData;

public final class StartConfigurePage extends WizardPage {

    private static final String DOT = ".";

    private Text fileSuffixText;

    private Text eventNameText;

    private final File logFile;

    private final GenericTextImportData importData;

    protected StartConfigurePage(final String pageName, final File logFile,
            final GenericTextImportData importData) {
        super(pageName);

        this.logFile = logFile;
        this.importData = importData;
        setDescription("Importer setup");
        setTitle("Logfile: " + this.logFile.getName());
    }

    public void createControl(final Composite parent) {

        final WizardDialog dlg = (WizardDialog) getContainer();
        dlg.addPageChangedListener(new IPageChangedListener() {
            public void pageChanged(final PageChangedEvent event) {
                StartConfigurePage.this.importData
                        .setDefaultEventName(getEventName());
                StartConfigurePage.this.importData.setFileId(getFileId());
            }
        });

        final String logFileName = this.logFile.getName();
        final int pos = logFileName.lastIndexOf(StartConfigurePage.DOT);
        String logfilePrefix;
        if (pos != -1) {
            logfilePrefix = logFileName.substring(0, pos);
        } else {
            logfilePrefix = logFileName;
        }
        final String logfileSuffix = this.importData.getFileId();

        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        setControl(container);

        Label eventNameLabel;

        final Composite composite = new Composite(container, SWT.NONE);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        final FormData fdComposite = new FormData();
        fdComposite.top = new FormAttachment(0, 5);
        fdComposite.bottom = new FormAttachment(0, 75);
        fdComposite.right = new FormAttachment(0, 315);
        fdComposite.left = new FormAttachment(0, 5);
        composite.setLayoutData(fdComposite);
        eventNameLabel = new Label(composite, SWT.NONE);
        eventNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
                false));
        eventNameLabel.setAlignment(SWT.RIGHT);
        eventNameLabel.setText("Event name:");

        this.eventNameText = new Text(composite, SWT.BORDER);
        this.eventNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                false, false));
        this.eventNameText.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent arg0) {
                StartConfigurePage.this.importData
                        .setDefaultEventName(StartConfigurePage.this.eventNameText
                                .getText());
            }
        });
        if (this.importData.getDefaultEventName() != null
                && this.importData.getDefaultEventName().length() > 0) {
            this.eventNameText.setText(this.importData.getDefaultEventName());
        } else {
            this.eventNameText.setText(logfilePrefix);
        }

        final Label suffixToAssosiateLabel = new Label(composite, SWT.NONE);
        suffixToAssosiateLabel.setLayoutData(new GridData(SWT.RIGHT,
                SWT.CENTER, false, false));
        suffixToAssosiateLabel.setAlignment(SWT.RIGHT);
        suffixToAssosiateLabel.setText("File name extension:");
        // eventNameLabel.setText("Name to assosiate with events");

        this.fileSuffixText = new Text(composite, SWT.BORDER);
        this.fileSuffixText.setEditable(false);
        final GridData gdFileSuffixText = new GridData(SWT.FILL, SWT.CENTER,
                false, false);
        gdFileSuffixText.widthHint = 152;
        this.fileSuffixText.setLayoutData(gdFileSuffixText);
        this.fileSuffixText.setText(logfileSuffix);
    }

    private String getFileId() {
        return this.fileSuffixText.getText();
    }

    private String getEventName() {
        return this.eventNameText.getText();
    }

}
