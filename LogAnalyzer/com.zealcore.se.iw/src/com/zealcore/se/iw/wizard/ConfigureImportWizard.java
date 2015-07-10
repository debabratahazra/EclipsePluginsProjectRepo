package com.zealcore.se.iw.wizard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.iw.GenericTextExtendedImporter;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.GenericTextImporter;
import com.zealcore.se.ui.core.CaseFileManager;

public class ConfigureImportWizard extends Wizard implements IWorkbenchWizard {

    public static final String HELP_ID = "com.zealcore.se.iw.wizard_ImportWizard";

    private static final String ERROR_NULL_FILE = "File may not be null";

    private static final String WINDOW_TITLE = "Generic Import Wizard";

    private static final int MAX_PREVIEW_LINES = 300;

    private static final String IMPORTER_SETUP = "Importer Setup";

    private TextFieldConfigurePage textFieldConfigurePage;

    private StartConfigurePage startConfigurePage;

    private final GenericTextImportData importData;

    private final File logFile;

    private final IFile ifile;

    private HeaderConfigurePage headerConfigurePage;

    public ConfigureImportWizard(final File file, final IFile ifile) {
        if (file == null) {
            throw new IllegalArgumentException(
                    ConfigureImportWizard.ERROR_NULL_FILE);
        }
        this.logFile = file;
        this.ifile = ifile;
        setWindowTitle(ConfigureImportWizard.WINDOW_TITLE);
        this.importData = new GenericTextImportData();

        final int pos = file.getName().lastIndexOf(".");
        String logfilePrefix;
        if (pos != -1) {
            logfilePrefix = file.getName().substring(pos);
        } else {
            logfilePrefix = file.getName();
        }
        this.importData.setFileId(logfilePrefix);
    }

    public ConfigureImportWizard(final File file, final IFile ifile,
            final GenericTextImportData importData) {
        if (file == null) {
            throw new IllegalArgumentException(
                    ConfigureImportWizard.ERROR_NULL_FILE);
        }
        if (importData == null) {
            throw new IllegalArgumentException("ImportData may not be null");
        }
        this.logFile = file;
        this.ifile = ifile;
        setWindowTitle(ConfigureImportWizard.WINDOW_TITLE);
        this.importData = importData;
        setHelpAvailable(true);
    }

    public GenericTextImportData getImportData() {
        return this.importData;
    }

    @Override
    public boolean performFinish() {

        this.textFieldConfigurePage.updateDecorations();
        int importerversion = IFWFacade.IEXTENDEDIMPORTER_VERSION;

        Logset logset = CaseFileManager.getLogset(this.ifile);
        if (!logset.getLogs().isEmpty()) {
            importerversion = logset.getImporterVersionType();
        }

        switch (importerversion) {
        case IFWFacade.IIMPORTER_VERSION:
            try {
                final GenericTextImporter importer = new GenericTextImporter();
                importer.getLogEvents(this.importData, this.logFile);
            } catch (final Exception e) {
                MessageDialog.openError(getShell(), "Unable to import file",
                        e.getMessage());
                return false;
            }

            break;

        case IFWFacade.IEXTENDEDIMPORTER_VERSION:
            try {
                final GenericTextExtendedImporter importer = new GenericTextExtendedImporter();
                importer.getLogEvents(this.importData, this.logFile);
            } catch (final Exception e) {
                MessageDialog.openError(getShell(), "Unable to import file",
                        e.getMessage());
                return false;
            }
            break;
        default:
            break;
        }

        return true;
    }

    @Override
    public void addPages() {
        this.startConfigurePage = new StartConfigurePage(
                ConfigureImportWizard.IMPORTER_SETUP, this.logFile,
                this.importData);
        addPage(this.startConfigurePage);

        this.headerConfigurePage = new HeaderConfigurePage(
                ConfigureImportWizard.IMPORTER_SETUP, this.logFile,
                this.importData);
        addPage(this.headerConfigurePage);

        this.textFieldConfigurePage = new TextFieldConfigurePage(
                "Choose Logfile Type", this.logFile, this.importData);
        addPage(this.textFieldConfigurePage);
    }

    public static void clearCach(final File file) {
        ConfigureImportWizard.cacheMap.remove(file.getAbsoluteFile()
                + Long.toString(file.lastModified()));
    }

    private static Map<String, String> cacheMap = new HashMap<String, String>();

    public static String getLogfileText(final File file) {
        if (ConfigureImportWizard.cacheMap.containsKey(file.getAbsoluteFile()
                + Long.toString(file.lastModified()))) {
            return ConfigureImportWizard.cacheMap.get(file.getAbsoluteFile()
                    + Long.toString(file.lastModified()));
        }
        try {
            final BufferedReader br = new BufferedReader(new FileReader(file));

            final StringBuilder builder = new StringBuilder();
            String line;
            int lineCnt = 0;
            line = br.readLine();
            while (line != null
                    && lineCnt++ < ConfigureImportWizard.MAX_PREVIEW_LINES) {
                builder.append(line);
                builder.append("\r\n");
                line = br.readLine();
            }
            ConfigureImportWizard.cacheMap
                    .put(file.getAbsoluteFile()
                            + Long.toString(file.lastModified()),
                            builder.toString());
            return ConfigureImportWizard.getLogfileText(file);
        } catch (final IOException e1) {
            throw new RuntimeException("Filename " + file.getName(), e1);
        }
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {}
}
