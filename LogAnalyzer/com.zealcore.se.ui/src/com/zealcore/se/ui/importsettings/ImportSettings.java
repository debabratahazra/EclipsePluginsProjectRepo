package com.zealcore.se.ui.importsettings;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.WorkbenchException;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.ImporterExporter;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.IconManager;

public class ImportSettings extends Wizard implements IExportWizard {
    private static final String INFORMATION = "Information";

    private static final String IMPORT_WIZARD_CONTENT_PAGE = "ImportWizardContentPage";

    private static final String IMPORT = "Import settings";

    private static Path importFilePath = new Path("settings.xml");

    public ImportSettings() {
        setDefaultPageImageDescriptor(IconManager
                .getImageDescriptor(IconManager.ZEALCORE_WIZARD_ICON));
    }
    
    @Override
    public boolean performFinish() {
        final IServiceProvider provider = SeCorePlugin.getDefault();
        final IAssertionRegistry assertions = provider
                .getService(IAssertionRegistry.class);

        // Cast problems: (IPythonQueryRegistry<PythonQuery>)
        final IMementoService memService = provider
                .getService(IMementoService.class);
        IMemento owner = null;
        try {
            owner = memService.createReadRoot(ImportSettings.importFilePath);
        } catch (final IllegalArgumentException e) {
            MessageDialog
                    .openInformation(getShell(), "Import error",
                            "The file specified could not be imported, invalid import file.");
            return false;
        } catch (final FileNotFoundException e) {
            MessageDialog.openInformation(
                    Display.getDefault().getActiveShell(), "File not found",
                    "The file specified could not be found.");
            return false;
        } catch (final WorkbenchException e) {
            throw new RuntimeException(e);
        }
        final ITypeRegistry typeRegistry = provider
                .getService(ITypeRegistry.class);
        final int noOfAssertionSets = ImporterExporter.importAssertions(owner,
                assertions, typeRegistry);

        if (noOfAssertionSets > 0) {
            String importMsg = "Successfully imported ";

            if (noOfAssertionSets > 0) {
                importMsg += noOfAssertionSets + " Assertion Sets";
            }
            importMsg += ".";
            MessageDialog.openInformation(
                    Display.getDefault().getActiveShell(),
                    ImportSettings.INFORMATION, importMsg);
        } else {
            MessageDialog.openInformation(
                    Display.getDefault().getActiveShell(),
                    ImportSettings.INFORMATION, "Nothing was imported.");
        }

        return true;
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {}

    @Override
    public void addPages() {
        super.addPages();
        final WizardPage wp = new MyOther(
                ImportSettings.IMPORT_WIZARD_CONTENT_PAGE, this);
        // addPage(new MyOther(IMPORT_WIZARD_CONTENT_PAGE, this));
        wp
                .setDescription("Import Assertions and Script Searches from the local file system into the workspace.");
        wp.setTitle(ImportSettings.IMPORT);
        addPage(wp);
        dispose();
        setWindowTitle(ImportSettings.IMPORT);
    }

    protected void setPath(final Path path) {
        ImportSettings.importFilePath = path;
    }

    private static class MyOther extends WizardPage {

        private static final int THREE_COLUMNS = 3;

        private final ImportSettings settingsWizard;

        protected static final String SELECT_SOURCE_TITLE = "SELECT_SOURCE_TITLE";

        protected MyOther(final String pageName,
                final ImportSettings settingsWizard) {
            super(pageName);
            this.settingsWizard = settingsWizard;
        }

        private boolean entryChanged;

        private Button sourceBrowseButton;

        public void createControl(final Composite parent) {
            final Composite sourceContainerGroup = new Composite(parent,
                    SWT.NONE);
            setControl(sourceContainerGroup);
            final GridLayout layout = new GridLayout();
            layout.numColumns = MyOther.THREE_COLUMNS;
            sourceContainerGroup.setLayout(layout);
            sourceContainerGroup.setFont(parent.getFont());

            final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                    | GridData.GRAB_HORIZONTAL);

            // Label field
            final Label fileLable = new Label(sourceContainerGroup, SWT.LEFT);
            fileLable.setText("Import from file:");

            // source name entry field
            final Text sourceNameText = new Text(sourceContainerGroup,
                    SWT.BORDER);
            data.verticalSpan = 1 + 1;
            sourceNameText.setLayoutData(data);

            sourceNameText.setFont(parent.getFont());
            sourceNameText.setText(ImportSettings.importFilePath.toString());

            sourceNameText.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {}
            });

            sourceNameText.addKeyListener(new KeyListener() {

                /*
                 * @see KeyListener.keyPressed
                 */
                public void keyPressed(final KeyEvent e) {
                    // If there has been a key pressed then mark as dirty
                    entryChanged = true;
                }

                /*
                 * @see KeyListener.keyReleased
                 */
                public void keyReleased(final KeyEvent e) {
                    // If there has been a key pressed then mark as dirty
                    entryChanged = true;

                    ImportSettings.importFilePath = new Path(sourceNameText
                            .getText());
                    settingsWizard.setPath(ImportSettings.importFilePath);
                }
            });

            sourceNameText.addFocusListener(new FocusListener() {
                /*
                 * @see FocusListener.focusGained(FocusEvent)
                 */
                public void focusGained(final FocusEvent e) {
                // Do nothing when getting focus
                }

                /*
                 * @see FocusListener.focusLost(FocusEvent)
                 */
                public void focusLost(final FocusEvent e) {
                    // Clear the flag to prevent constant update
                    if (entryChanged) {
                        entryChanged = false;
                    }
                }
            });

            // source browse button
            sourceBrowseButton = new Button(sourceContainerGroup, SWT.PUSH);
            sourceBrowseButton.setText("Browse...");
            sourceBrowseButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(final SelectionEvent e) {
                    final FileDialog dialog = new FileDialog(sourceNameText
                            .getShell(), SWT.OPEN);
                    dialog.setText(MyOther.SELECT_SOURCE_TITLE);
                    final String selectedFile = dialog.open();
                    if (selectedFile != null) {
                        ImportSettings.importFilePath = new Path(selectedFile);
                        settingsWizard.setPath(ImportSettings.importFilePath);
                        sourceNameText.setText(ImportSettings.importFilePath
                                .toString());
                        setErrorMessage(null);
                    }
                }
            });

            sourceBrowseButton.setLayoutData(new GridData(
                    GridData.HORIZONTAL_ALIGN_END));
            sourceBrowseButton.setFont(parent.getFont());
            setButtonLayoutData(sourceBrowseButton);
        }
    }
}
