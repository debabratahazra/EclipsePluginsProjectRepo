package com.temenos.t24.tools.eclipse.basic.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

/**
 * This class represents a preference page that contributes to the Preferences
 * dialog. By sub-classing FieldEditorPreferencePage, we can use the field
 * support built into JFace that allows us to create a page that is small and
 * knows how to save, restore and apply itself.
 * 
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class T24BasicPreferenceCorePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

    public static final String PREFERENCES_PAGE_ID = "t24.tools.eclipse.T24BasicPreferences";
    // URL values needed to form the Http connection to a remote server.
    // Typically a remote server is T24 server with which to exchange
    // commands/info.
    private StringFieldEditor remoteHostnameField; // e.g.//"ukhml-host.temenos.europe" or "localhost"
    private StringFieldEditor remotePortField; // e.g. 8080
    private IntegerFieldEditor httTimeoutMillisField; // e.g. 10000
    private StringFieldEditor remoteDirField; // e.g. "GLOBUS.BP"
    private StringFieldEditor hyperLinkDirField; // e.g. GLOBUS.BP,BP,TEST.BP
    private DirectoryFieldEditor localCurDirField; // e.g. // c:\dev\eclipse\workspace\project\accounting
    private DirectoryFieldEditor localDefaultProjectField; // e.g.  // c:\dev\eclipse\workspace \project\T24BasicProject
    private DirectoryFieldEditor t24docDirField; // e.g. // C:\doc\t24doc
    private IntegerFieldEditor indentationSpacesField; // e.g. 3
    private BooleanFieldEditor compileWithDebugCheck; // true => -D will be added to EB.COMPILE command
    private BooleanFieldEditor promptOverwritingFile; // true => Overwriting of any file would throw a  dialog
    // true => collapses all the region in the document. false=> expands all the region in the document.
    private BooleanFieldEditor alwaysCollapseRegion; // true => a file retrieved by the server will always be locally saved.
    private BooleanFieldEditor saveLocallyCheck;
    private BooleanFieldEditor unitTestCompile; // true -> compile unit test  before running
    private StringFieldEditor localUsernameField;
    private StringFieldEditor localEmailField;
    private StringFieldEditor localTelephoneField;

    // --------------------------------------------------------------------------
    // ---------------
    public T24BasicPreferenceCorePage() {
        super(GRID);
    }

    public void init(IWorkbench workbench) {
    }

    /**
     * Creates the Field Editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences.
     */
    @Override
    public void createFieldEditors() {
        // initialise the preference store we wish to use
        setPreferenceStore((IPreferenceStore) T24BasicPlugin
                .getBean("preferenceStore"));
        // The Environment values are persisted in a local file. Retrieve them now, and use them to pre-populate the Field Editors contents
        /** Plug-in version */
        //fix for: DS-5861 :UI: Remove "Plug-in Version" and rename "T24Basic" to "T24 jBC (TAF/C)" in T24Basic Editor Preferences
        /*pluginVersionField = new StringFieldEditor(
                PluginConstants.PLUGIN_VERSION, "Plug-in Version",
                getFieldEditorParent());
        pluginVersionField.setEnabled(false, getFieldEditorParent());
        addField(pluginVersionField);*/
        // **********************************************************************
        // *****
        // URL - HTTP PARAMETERS
        // **********************************************************************
        // *****
        setDescription("Remote Server URL settings:");
        remoteHostnameField = new StringFieldEditor(
                PluginConstants.T24_REMOTE_HOSTNAME, "Hostname",
                getFieldEditorParent());
        addField(remoteHostnameField);
        remotePortField = new StringFieldEditor(
                PluginConstants.T24_REMOTE_PORT, "Port", getFieldEditorParent());
        addField(remotePortField);
        httTimeoutMillisField = new IntegerFieldEditor(
                PluginConstants.T24_REMOTE_TIMEOUT_MILLIS, "Timeout(Millis)",
                getFieldEditorParent());
        addField(httTimeoutMillisField);
        remoteDirField = new StringFieldEditor(
                PluginConstants.T24_REMOTE_SERVER_DIR, "Server Dir",
                getFieldEditorParent());
        addField(remoteDirField);
        hyperLinkDirField = new StringFieldEditor(
                PluginConstants.T24_HYPERLINK_DIR, "Hyperlink path",
                getFieldEditorParent());
        addField(hyperLinkDirField);
        compileWithDebugCheck = new BooleanFieldEditor(
                PluginConstants.T24_COMPILE_WITH_DEBUG,
                "Compile with DEBUG statements", getFieldEditorParent());
        addField(compileWithDebugCheck);
        promptOverwritingFile = new BooleanFieldEditor(
                PluginConstants.T24_PROMPT_BEFORE_OVERWRITING,
                "Always prompt before overwriting server files",
                getFieldEditorParent());
        addField(promptOverwritingFile);
        alwaysCollapseRegion = new BooleanFieldEditor(
                PluginConstants.T24_ALWAYS_COLLAPSE_REGION,
                "Always collapse regions", getFieldEditorParent());
        addField(alwaysCollapseRegion);
        // **********************************************************************
        // *****
        // LOCAL Directory
        // **********************************************************************
        // *****
        setDescription("Local Directory:");
        localCurDirField = new DirectoryFieldEditor(
                PluginConstants.T24_LOCAL_DIRECTORY, "Local directory",
                getFieldEditorParent());
        addField(localCurDirField);
        localDefaultProjectField = new DirectoryFieldEditor(
                PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH,
                "Local default Project", getFieldEditorParent());
        addField(localDefaultProjectField);     
        t24docDirField = new DirectoryFieldEditor(
                PluginConstants.T24_DOCUMENT_FULLPATH,
                "T24 Doc Location", getFieldEditorParent());
        addField(t24docDirField);     
        saveLocallyCheck = new BooleanFieldEditor(
                PluginConstants.T24_SAVE_LOCALLY_BY_DEFAULT_ON,
                "Save server files always locally", getFieldEditorParent());
        addField(saveLocallyCheck);
        unitTestCompile = new BooleanFieldEditor(
                PluginConstants.T24UNIT_TEST_COMPILE,
                "Compile unit test before execution", getFieldEditorParent());
        addField(unitTestCompile);
        indentationSpacesField = new IntegerFieldEditor(
                PluginConstants.T24_INDENTATION_SPACES, "Indentation spaces",
                getFieldEditorParent());
        addField(indentationSpacesField);
        // **********************************************************************
        // *****
        // LOCAL User
        // **********************************************************************
        // *****
        localUsernameField = new StringFieldEditor(
                PluginConstants.T24_LOCAL_USERNAME, "Username",
                getFieldEditorParent());
        addField(localUsernameField);
        localEmailField = new StringFieldEditor(
                PluginConstants.T24_LOCAL_EMAIL, "Email",
                getFieldEditorParent());
        addField(localEmailField);
        localTelephoneField = new StringFieldEditor(
                PluginConstants.T24_LOCAL_TELEPHONE, "Telephone",
                getFieldEditorParent());
        addField(localTelephoneField);
    }

    /**
     * Checks contents of fields setting appropriate error messages
     */
    @Override
    protected void checkState() {
        super.checkState();
        if (isValid()) {
            setErrorMessage(null);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        /** Currently changes don't trigger any action */
        super.propertyChange(event);
    }

    /**
     * OK (or Valid) button of this page's container has been clicked. Update
     * the plug-in store with the values in the fields.
     * 
     * @return <code>false</code> to abort the container's OK processing and
     *         <code>true</code> to allow the OK to happen
     */
    @Override
    public boolean performOk() {
        super.performOk();
        checkState();
        if (!isValid()) {
            return false;
        }
        remoteHostnameField.store();
        remotePortField.store();
        remoteDirField.store();
        hyperLinkDirField.store();
        httTimeoutMillisField.store();
        localCurDirField.store();
        localDefaultProjectField.store();
        t24docDirField.store();
        saveLocallyCheck.store();
        unitTestCompile.store();
        indentationSpacesField.store();
        localUsernameField.store();
        localEmailField.store();
        localTelephoneField.store();
        compileWithDebugCheck.store();
        promptOverwritingFile.store();
        alwaysCollapseRegion.store();
        /**
         * If DEBUG statements are enabled then Console View will have new icon
         * added to alert developer
         */
        if (compileWithDebugCheck.getBooleanValue()) {
            LogConsole.updateConsoleToolBar(
                    "/icons/debugOptionWarningImage.gif",
                    "Compiling with DEBUG allowed", "debug.id");
        } else {
            LogConsole.removeActionFromConsoleToolBar("debug.id");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performDefaults() {
        super.performDefaults();
        remoteHostnameField.loadDefault(); // e.g. "localhost"
        remotePortField.loadDefault(); // e.g. 8080
        remoteDirField.loadDefault(); // e.g. "GLOBUS.BP"
        hyperLinkDirField.loadDefault();// e.g. "GLOBUS.BP"
        httTimeoutMillisField.loadDefault(); // e.g. 10000
        localCurDirField.loadDefault(); // e.g.// c:\dev\eclipse\workspace\T24BasicProject\accounting
        localDefaultProjectField.loadDefault(); // e.g.// c:\dev\eclipse\workspace\T24BasicProject
        t24docDirField.loadDefault();
        saveLocallyCheck.loadDefault();
        unitTestCompile.loadDefault();
        indentationSpacesField.loadDefault(); // e.g. 3
        localUsernameField.loadDefault();
        localEmailField.loadDefault();
        localTelephoneField.loadDefault();
        compileWithDebugCheck.loadDefault();
        promptOverwritingFile.loadDefault();
        alwaysCollapseRegion.loadDefault();
    }
}
