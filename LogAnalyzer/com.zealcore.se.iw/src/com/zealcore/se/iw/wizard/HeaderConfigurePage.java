package com.zealcore.se.iw.wizard;

import java.io.File;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import com.zealcore.se.iw.GenericTextImportData;

public final class HeaderConfigurePage extends WizardPage {

    private static final int MINIMUM_OF_LINES_LEFT = 2;

    private StyledText preview;

    private Spinner noOfHeaderLines;

    private final File logFile;

    private Composite headerContainer;

    private final GenericTextImportData importData;

    protected HeaderConfigurePage(final String pageName, final File logFile,
            final GenericTextImportData importData) {
        super(pageName);
        this.logFile = logFile;
        this.importData = importData;

        setDescription("Header setup");
        setTitle("Logfile: " + this.logFile.getName());

    }

    public void createControl(final Composite parent) {
        final WizardDialog dlg = (WizardDialog) getContainer();
        dlg.addPageChangedListener(new IPageChangedListener() {
            public void pageChanged(final PageChangedEvent event) {
                HeaderConfigurePage.this.importData
                        .setNoOfHeaderLines(getNoOfHeaderLines());
                HeaderConfigurePage.this.importData.setSkipEmptyLines(true);
            }
        });

        this.headerContainer = new Composite(parent, SWT.NONE);
        this.headerContainer.setLayout(new FormLayout());
        setControl(this.headerContainer);

        Label numberOfHeaderLabel;

        Label filePreviewLabel;

        final int properties = SWT.V_SCROLL | SWT.READ_ONLY;

        numberOfHeaderLabel = new Label(this.headerContainer, SWT.NONE);
        final FormData fdnumberOfHeaderLabel = new FormData();
        fdnumberOfHeaderLabel.left = new FormAttachment(0, 2);
        fdnumberOfHeaderLabel.top = new FormAttachment(0, 10);
        numberOfHeaderLabel.setLayoutData(fdnumberOfHeaderLabel);
        numberOfHeaderLabel.setText("Number of header lines");

        this.noOfHeaderLines = new Spinner(this.headerContainer, SWT.BORDER);
        this.noOfHeaderLines.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                if (HeaderConfigurePage.this.preview != null) {
                    HeaderConfigurePage.this.noOfHeaderLines
                            .setMaximum(HeaderConfigurePage.this.preview
                                    .getLineCount()
                                    - HeaderConfigurePage.MINIMUM_OF_LINES_LEFT);
                } else {
                    HeaderConfigurePage.this.noOfHeaderLines.setMaximum(0);
                }
                HeaderConfigurePage.this.noOfHeaderLines.getSelection();
                updatePreview(HeaderConfigurePage.this.noOfHeaderLines
                        .getSelection());
            }
        });
        final FormData fdignoreLinesSpinner = new FormData();
        fdignoreLinesSpinner.left = new FormAttachment(0, 188);
        fdignoreLinesSpinner.right = new FormAttachment(0, 235);
        fdignoreLinesSpinner.top = new FormAttachment(0, 5);
        this.noOfHeaderLines.setLayoutData(fdignoreLinesSpinner);
        filePreviewLabel = new Label(this.headerContainer, SWT.NONE);
        final FormData fdfilePreviewLabel = new FormData();
        fdfilePreviewLabel.left = new FormAttachment(0, 2);
        fdfilePreviewLabel.right = new FormAttachment(0, 100);
        fdfilePreviewLabel.bottom = new FormAttachment(0, 93);
        fdfilePreviewLabel.top = new FormAttachment(0, 80);
        fdfilePreviewLabel.width = 100;
        filePreviewLabel.setLayoutData(fdfilePreviewLabel);
        filePreviewLabel.setText("File Preview");
        this.preview = new StyledText(this.headerContainer, properties
                | SWT.H_SCROLL | SWT.BORDER);
        final FormData fdPreview = new FormData();
        fdPreview.height = 200;
        fdPreview.bottom = new FormAttachment(100, -5);
        fdPreview.left = new FormAttachment(0, 5);
        fdPreview.right = new FormAttachment(100, -5);
        fdPreview.top = new FormAttachment(0, 110);
        this.preview.setLayoutData(fdPreview);

        this.preview
                .setText(ConfigureImportWizard.getLogfileText(this.logFile));
        final int numHeaderLines = this.importData.getNoOfHeaderLines();
        this.noOfHeaderLines.setSelection(numHeaderLines);
    }

    protected void updatePreview(final int linesToIgnore) {

        final StyleRange styleRange1 = new StyleRange();
        styleRange1.start = 2;
        styleRange1.length = 3;
        styleRange1.foreground = this.headerContainer.getDisplay()
                .getSystemColor(SWT.COLOR_BLUE);
        styleRange1.background = this.headerContainer.getDisplay()
                .getSystemColor(SWT.COLOR_YELLOW);
        styleRange1.fontStyle = SWT.BOLD;

        int i;
        for (i = 0; i < linesToIgnore; i++) {
            final Color elinGreen = new Color(null, 118, 144, 90);
            this.preview.setLineBackground(i, 1, elinGreen);
        }
        for (; i < this.preview.getLineCount(); i++) {
            this.preview.setLineBackground(i, 1, this.headerContainer
                    .getDisplay().getSystemColor(SWT.COLOR_WHITE));
        }
    }

    public int getNoOfHeaderLines() {
        return this.noOfHeaderLines.getSelection();
    }

    /**
     * @deprecated Use {@link ConfigureImportWizard#getLogfileText(File)}
     *             instead
     */
    @Deprecated
    public static String getLogfileText(final File file) {
        return ConfigureImportWizard.getLogfileText(file);
    }
}
