package com.odcgroup.workbench.generation.ui.internal.preferences;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.PreferenceConstants;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.ui.GenerationUICore;
import com.odcgroup.workbench.ui.preferences.FieldEditorOverlayPage;

public class CodeGenerationPropertyPage extends FieldEditorOverlayPage {

	Set<TreeItem> cartridgeTreeItems;
	Text activityText;

	/**
	 * Constructor for GenerationPropertyPage.
	 */
	public CodeGenerationPropertyPage() {
		super("Code Generation Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
	}

    /**
     * We override the createContents method in order to apply a specific layout
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents( final Composite parent )
    {
        final Composite comp = new Composite(parent, SWT.NONE);
        final GridLayout layout = new GridLayout();
        comp.setLayout(layout);
        comp.setLayoutData(new GridData(GridData.FILL_BOTH, SWT.BEGINNING, true, true));
        
        createTabbedPane(comp);

        // Project configuration disabled as it is not wanted by arch team 
        // to have different components using different settings
        //createProjectNameDefinitionPane(comp);

        super.createContents(comp);
        
        initialize();
        
        return comp;
    }
    
    /**
     * @param parent
     */
    private void createTabbedPane(final Composite parent) {
    	CTabFolder tabFolder = new CTabFolder(parent, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
		
		// cartridges tab
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);		
		tabItem.setText(GenerationUICore.getDefault().getString("CodeGenerationPropertyPage.cartridgeTabText"));
		tabItem.setControl(createCartridgeSelectionPane(tabFolder));
		
		// settings tab
		CTabItem settTabItem = new CTabItem(tabFolder, SWT.NONE);		
		settTabItem.setText(GenerationUICore.getDefault().getString("CodeGenerationPropertyPage.settingTabText"));
		settTabItem.setControl(createSettingsTabContent(tabFolder));
		
		tabFolder.setSelection(tabFolder.getItem(0));
    }
    
    /**
     * @param parent
     * @return
     */
    private Control createSettingsTabContent(final Composite parent) {
    	Composite setGroup = new Composite(parent, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(2, false);
        setGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		setGroup.setLayoutData(gridData);
		
		CLabel label = new CLabel(setGroup, SWT.NONE);
		label.setBackground(setGroup.getBackground());
		label.setText(GenerationUICore.getDefault().getString("CodeGenerationPropertyPage.settingActivitiyNameText"));
		
		activityText = new Text(setGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		activityText.setLayoutData(gd);
		
		getPreferenceStore().getString(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME);
		
		return setGroup;
    }
	
	/**
	 * @param parent
	 * @return
	 */
	private Control createCartridgeSelectionPane( final Composite parent )  {
		
		Composite catGroup = new Composite(parent, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
        catGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		catGroup.setLayoutData(gridData);
		
		cartridgeTreeItems = new HashSet<TreeItem>();
		
        Label codeCartridgesLabel = new Label(catGroup, SWT.NONE);
        codeCartridgesLabel.setText(GenerationUICore.getDefault().getString("CodeGenerationPropertyPage.cartridgeLabelText")); //$NON-NLS-1$
        
        Tree tree = new Tree(catGroup, SWT.BORDER | SWT.CHECK);
        GridLayout layout = new GridLayout();
        layout.marginLeft = 20;
        layout.marginTop = 20;
        tree.setLayout(layout);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
        rootItem.setText("All Categories");
        
        for(CodeGenCategory category : CodeGenCategory.values()) {
        	CodeCartridge[] cartridges = GenerationCore.getRegisteredCodeCartridges(category);
        	if(cartridges.length>0) {
	        	TreeItem categoryNode = new TreeItem(rootItem, SWT.CHECK);
	        	categoryNode.setText(category.toString());
	    		for(CodeCartridge cartridge : cartridges) {
    				TreeItem cartridgeItem = new TreeItem(categoryNode, SWT.CHECK);
    				cartridgeItem.setText(cartridge.getName());
    				cartridgeItem.setData(cartridge);
    				cartridgeTreeItems.add(cartridgeItem);
	    		}
        	}
        }
        
        tree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
            	if(event.detail == SWT.CHECK) {
            		if(event.item instanceof TreeItem) {
            			TreeItem item = (TreeItem) event.item;
            			setCheckedOnAllChildren(item, item.getChecked());
            			setCheckOnParent(item);
            		}
            	}
            }
          });
        
        return catGroup;

    }

	@Override
	protected String getPluginId() {
    	return GenerationCore.PLUGIN_ID;
	}
	
	private static void setCheckedOnAllChildren(TreeItem item, boolean value) {
		item.setChecked(value);
		item.setGrayed(false);
		for(TreeItem child : item.getItems()) {
			setCheckedOnAllChildren(child, value);
		}
	}
	
	private static void setCheckOnParent(TreeItem item) {
		TreeItem parent = item.getParentItem();
		int numberOfCheckedChildren = 0; 
		if(parent!=null) {
			for(TreeItem child : parent.getItems()) {
				numberOfCheckedChildren += child.getChecked()?1:0;
			}

			if(numberOfCheckedChildren == parent.getItemCount()) {
				parent.setChecked(true);
				parent.setGrayed(false);
			} else if(numberOfCheckedChildren>0) {
				parent.setChecked(true);
				parent.setGrayed(true);
			} else {
				parent.setChecked(false);
				parent.setGrayed(false);
			}
			setCheckOnParent(parent);
		}
	}
	
	protected void initialize() {
		super.initialize();
		
        ProjectPreferences preferences = new ProjectPreferences(getProject(), GenerationCore.PLUGIN_ID);
		
		for(TreeItem item : cartridgeTreeItems) {
			CodeCartridge cartridge = (CodeCartridge) item.getData();
			// check if enabled
			item.setChecked(preferences.getBoolean(cartridge.getId(), cartridge.isEnabledByDefault()));
			item.setGrayed(false);
			setCheckOnParent(item);
		}
		activityText.setText(preferences.get(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, PreferenceConstants.DEFVAL_PAGEFLOW_ACTIVITYNAME));
	}
	
	@Override
	protected void performDefaults() {
        ProjectPreferences preferences = new ProjectPreferences(null, GenerationCore.PLUGIN_ID);
		
		for(TreeItem item : cartridgeTreeItems) {
			CodeCartridge cartridge = (CodeCartridge) item.getData();
			item.setChecked(preferences.getBoolean(cartridge.getId(), false));
			setCheckOnParent(item);
		}
		activityText.setText(PreferenceConstants.DEFVAL_PAGEFLOW_ACTIVITYNAME);
		preferences.put(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, PreferenceConstants.DEFVAL_PAGEFLOW_ACTIVITYNAME);
	}
		   
	@Override
    public boolean performOk() {
    	super.performOk();
    	
    	String errorMsg = null;
        ProjectPreferences preferences = new ProjectPreferences(getProject(), GenerationCore.PLUGIN_ID);
		
		for(TreeItem item : cartridgeTreeItems) {
			CodeCartridge cartridge = (CodeCartridge) item.getData();
			preferences.putBoolean(cartridge.getId(), item.getChecked());
		}
		String activity = activityText.getText();
		String pref = preferences.get(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, PreferenceConstants.DEFVAL_PAGEFLOW_ACTIVITYNAME);
		if (!StringUtils.isEmpty(activity) && !activity.equals(pref)) {
			preferences.put(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, activityText.getText());
		}
		
		IFile file = getProject().getFile(".settings/com.odcgroup.workbench.generation.prefs");
		if (file.exists()) {
			if (file.isReadOnly()) {
				errorMsg = "File " + file.getFullPath() + " is read-only.";
			} else if (!file.isSynchronized(IResource.DEPTH_INFINITE)) {
				errorMsg = "Resource is out of sync with the file system: " + file.getFullPath();
			}
		}
		
		if(StringUtils.isNotBlank(errorMsg)) {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error Occurred", errorMsg);
		} 
		
		preferences.flush();
		
		return true;
    }
}