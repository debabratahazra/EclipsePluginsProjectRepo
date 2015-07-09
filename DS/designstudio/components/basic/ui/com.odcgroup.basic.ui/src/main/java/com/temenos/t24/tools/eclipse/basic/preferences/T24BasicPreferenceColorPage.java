package com.temenos.t24.tools.eclipse.basic.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

/**
 * Colors preference page.
 */
public class T24BasicPreferenceColorPage extends PreferencePage
		implements IWorkbenchPreferencePage {
    public static final String PREFERENCES_PAGE_ID = "t24.tools.eclipse.T24BasicPreferences.EditorColors";
    public static final String COLOR_PREFERENCE_PAGE_TITLE = "T24 Basic Editor Colors";
    
    private static IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
    private ColorFieldEditor backgroundColorField;
    private ColorFieldEditor commentColorField;
    private ColorFieldEditor commonVariableColorField;
    private ColorFieldEditor defaultColorField;
    private ColorFieldEditor keywordColorField;
    private ColorFieldEditor literalColorField;    
    private ColorFieldEditor hyperlinkColorField;    
    
	public T24BasicPreferenceColorPage() {
		super();
	}

	/**
     * {@inheritDoc} 
	 */
	public void init(IWorkbench workbench) {
        // initialise the preference store we wish to use
        setPreferenceStore(store);	    
	}

    /*
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        
        Composite topComposite = new Composite(parent, SWT.NULL);
        // Sets the layout for the composite's children to populate.
        topComposite.setLayout(new GridLayout());        
        // Sets the layout data for the top composite's place in its parent's layout.
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        topComposite.setLayoutData(data);

        Group colorGroup = new Group(topComposite, SWT.NONE);
        colorGroup.setText("Basic editor colors");
        colorGroup.setLayout(new GridLayout());
        GridData colorData = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = false;
        colorGroup.setLayoutData(colorData);        
        
        // ***************************************************************************
        //                          COLORS
        // ***************************************************************************        
        commentColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_COMMENT, "C&omment", colorGroup);
        commentColorField.setPreferenceStore(store);
        RGB rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_COMMENT);
        PreferenceConverter.setValue(store, commentColorField.getPreferenceName(), rgb);
        commentColorField.load();

        
        backgroundColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_BACKGROUND, "B&ackground", colorGroup);
        backgroundColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_BACKGROUND);
        PreferenceConverter.setValue(store, backgroundColorField.getPreferenceName(), rgb);
        backgroundColorField.load();

        
        commonVariableColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_COMMON_VARIABLE, "Common V&ar", colorGroup);
        commonVariableColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_COMMON_VARIABLE);
        PreferenceConverter.setValue(store, commonVariableColorField.getPreferenceName(), rgb);
        commonVariableColorField.load();
        
        
        defaultColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_DEFAULT, "C&ode", colorGroup);
        defaultColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_DEFAULT);
        PreferenceConverter.setValue(store, defaultColorField.getPreferenceName(), rgb);
        defaultColorField.load();

        
        keywordColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_KEYWORD, "K&eyword", colorGroup);
        keywordColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_KEYWORD);
        PreferenceConverter.setValue(store, keywordColorField.getPreferenceName(), rgb);
        keywordColorField.load();
        
        
        literalColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_LITERAL, "L&iteral", colorGroup);
        literalColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_LITERAL);
        PreferenceConverter.setValue(store, literalColorField.getPreferenceName(), rgb);
        literalColorField.load();


        hyperlinkColorField = new ColorFieldEditor(PluginConstants.T24_EDITOR_COLOR_HYPERLINK, "H&yperlink", colorGroup);
        hyperlinkColorField.setPreferenceStore(store);
        rgb = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_HYPERLINK);
        PreferenceConverter.setValue(store, hyperlinkColorField.getPreferenceName(), rgb);
        hyperlinkColorField.load();
        
        return topComposite;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performOk() {

        if (isControlCreated()) {
            RGB rgb = backgroundColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_BACKGROUND, rgb);
            
            rgb = commentColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_COMMENT, rgb);
            
            rgb = commonVariableColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_COMMON_VARIABLE, rgb);
            
            rgb = defaultColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_DEFAULT, rgb);
            
            rgb = keywordColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_KEYWORD, rgb);
            
            rgb = literalColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_LITERAL, rgb);
            
            rgb = hyperlinkColorField.getColorSelector().getColorValue();
            PreferenceConverter.setValue(store, PluginConstants.T24_EDITOR_COLOR_HYPERLINK, rgb);
            
        }
        return super.performOk();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void performDefaults() {
        super.performDefaults();

        if (isControlCreated()) {
            backgroundColorField.loadDefault();
            commentColorField.loadDefault();
            commonVariableColorField.loadDefault();        
            defaultColorField.loadDefault();
            keywordColorField.loadDefault();
            literalColorField.loadDefault();
            hyperlinkColorField.loadDefault();
        }
    }    
}