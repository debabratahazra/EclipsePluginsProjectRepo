package com.zealcore.se.ui.export;

import java.io.IOException;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.ImporterExporter;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.IconManager;

public class ExportSettings extends Wizard implements IExportWizard {

    private static final String SETTINGS_XML = "settings.xml";

    private static final String EXPORT_SETTINGS = "Export Settings";

    private static final String ASSERTION_REGISTRY_NODE = "AssertionRegistry";

    private static final String ROOT = "Root";

    private static Path exportFilePath = new Path(ExportSettings.SETTINGS_XML);

    public ExportSettings() {
        setDefaultPageImageDescriptor(IconManager
                .getImageDescriptor(IconManager.ZEALCORE_WIZARD_ICON));
    }

    @Override
    public boolean performFinish() {

        final IServiceProvider provider = SeCorePlugin.getDefault();
        final IAssertionRegistry assertions = provider
                .getService(IAssertionRegistry.class);

        final IMementoService memService = provider
                .getService(IMementoService.class);
        final IMemento2 owner = memService.createWriteRoot(ExportSettings.ROOT,
                ExportSettings.exportFilePath);
        ImporterExporter.exportAssertions(owner
                .createChild(ExportSettings.ASSERTION_REGISTRY_NODE),
                assertions);

        try {
            owner.save();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {}

    @Override
    public void addPages() {
        super.addPages();

        final WizardPage wp = new SelectSettingsPage("ExportPage", this);
        wp
                .setDescription("Export Assertions and Script Searches to the local file system.");
        wp.setTitle(ExportSettings.EXPORT_SETTINGS);
        addPage(wp);
        setWindowTitle(ExportSettings.EXPORT_SETTINGS);
    }

    protected void setPath(final Path path) {
        ExportSettings.exportFilePath = path;
    }

    private static class SelectSettingsPage extends WizardPage {

        private static final int THREE_COLUMNS = 3;

        // TODO: Create a tree where the user can check/uncheck the parts that
        // should be exported/imported
        // private CheckboxTreeViewer viewer;

        protected SelectSettingsPage(final String pageName,
                final ExportSettings settingsWizard) {
            super(pageName);
            this.settingsWizard = settingsWizard;
        }

        private final ExportSettings settingsWizard;

        private boolean entryChanged;

        private Button sourceBrowseButton;

        public void createControl(final Composite parent) {
            final Composite sourceContainerGroup = new Composite(parent,
                    SWT.NONE);
            setControl(sourceContainerGroup);
            final GridLayout layout = new GridLayout();
            layout.numColumns = SelectSettingsPage.THREE_COLUMNS;
            sourceContainerGroup.setLayout(layout);
            sourceContainerGroup.setFont(parent.getFont());

            final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                    | GridData.GRAB_HORIZONTAL);
            // Label field
            final Label fileLable = new Label(sourceContainerGroup, SWT.LEFT);
            fileLable.setText("Export to file:");

            // source name entry field
            final Text sourceNameText = new Text(sourceContainerGroup,
                    SWT.BORDER);
            data.verticalSpan = 1 + 1;
            sourceNameText.setLayoutData(data);

            sourceNameText.setFont(parent.getFont());
            sourceNameText.setText(ExportSettings.exportFilePath.toString());

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

                    ExportSettings.exportFilePath = new Path(sourceNameText
                            .getText());
                    settingsWizard.setPath(ExportSettings.exportFilePath);
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
                            .getShell(), SWT.SAVE);
                    dialog.setFilterExtensions(new String[] { "*.xml" });
                    dialog.setFileName(ExportSettings.SETTINGS_XML);

                    final IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    final IWorkspaceRoot workspaceRoot = workspace.getRoot();

                    dialog.setFilterPath(workspaceRoot.getLocation()
                            .toOSString());
                    final String selectedFile = dialog.open();
                    if (selectedFile != null) {
                        ExportSettings.exportFilePath = new Path(selectedFile);
                        settingsWizard.setPath(ExportSettings.exportFilePath);
                        sourceNameText.setText(ExportSettings.exportFilePath
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
