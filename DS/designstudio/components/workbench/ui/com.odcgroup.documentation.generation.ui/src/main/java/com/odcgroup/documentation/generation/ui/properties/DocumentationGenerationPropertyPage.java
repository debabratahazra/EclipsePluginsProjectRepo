package com.odcgroup.documentation.generation.ui.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.documentation.generation.DocGenCartridge;
import com.odcgroup.documentation.generation.DocumentationCore;
import com.odcgroup.documentation.generation.ui.DocumentationUICore;
import com.odcgroup.documentstion.generation.cartridge.DocGenCategory;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.PreferenceConstants;
import com.odcgroup.workbench.ui.preferences.FieldEditorOverlayPage;

public class DocumentationGenerationPropertyPage extends FieldEditorOverlayPage {

	Set<TreeItem> cartridgeTreeItems;
	private Text activityText;

	/**
	 * Constructor for GenerationPropertyPage.
	 */
	public DocumentationGenerationPropertyPage() {
		super("Documentation Generation Settings");
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
		tabItem.setText(DocumentationUICore.getDefault().getString("DocumentationGenerationPropertyPage.cartridgeTabText"));
		tabItem.setControl(createCartridgeSelectionPane(tabFolder));
		
		// settings tab
		CTabItem settTabItem = new CTabItem(tabFolder, SWT.NONE);		
		settTabItem.setText(DocumentationUICore.getDefault().getString("DocumentationGenerationPropertyPage.settingTabText"));
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
		label.setText(DocumentationUICore.getDefault().getString("DocumentationGenerationPropertyPage.settingDocGenFolder"));
		
		activityText = new Text(setGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		activityText.setLayoutData(gd);
		
		getPreferenceStore().getString(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME);
		
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
        codeCartridgesLabel.setText(DocumentationUICore.getDefault().getString("DocumentationGenerationPropertyPage.cartridgeLabelText")); //$NON-NLS-1$
        
        Tree tree = new Tree(catGroup, SWT.BORDER | SWT.CHECK);
        GridLayout layout = new GridLayout();
        layout.marginLeft = 20;
        layout.marginTop = 20;
        tree.setLayout(layout);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
        rootItem.setText("All Categories");
        
        for(DocGenCategory category : DocGenCategory.values()) {
        	DocGenCartridge[] cartridges = filterByCategory(DocumentationCore.getRegisteredDocGenCartridges(), category);
        	
        	if(cartridges.length>0) {
	        	TreeItem categoryNode = new TreeItem(rootItem, SWT.CHECK);
	        	categoryNode.setText(category.toString());
	    		for(DocGenCartridge cartridge : cartridges) {
	    			if(cartridge.getCategory().equals(category)){
	    				TreeItem cartridgeItem = new TreeItem(categoryNode, SWT.CHECK);
	    				cartridgeItem.setText(cartridge.getName());	
	    				cartridgeItem.setData(cartridge);
	    				cartridgeTreeItems.add(cartridgeItem);	
	    			}
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
	
    
/*	private void createCartridgeSelectionPane( final Composite parent )
    {
		cartridgeTreeItems = new HashSet<TreeItem>();
		
        Label codeCartridgesLabel = new Label(parent, SWT.NONE);
        codeCartridgesLabel.setText(
        		DocumentationUICore.getDefault().getString("DocumentationGenerationPropertyPage.cartridgeLabelText")); //$NON-NLS-1$
        
        Tree tree = new Tree(parent, SWT.BORDER | SWT.CHECK);
        GridLayout layout = new GridLayout();
        layout.marginLeft = 20;
        layout.marginTop = 20;
        tree.setLayout(layout);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
        rootItem.setText("All Cartridges");
        
		for(DocGenCartridge cartridge : DocumentationCore.getRegisteredDocGenCartridges()) {
			if (cartridge.getDocGen().isValidForProject(getProject())) { //DS-1614
				TreeItem cartridgeItem = new TreeItem(rootItem, SWT.CHECK);
				cartridgeItem.setText(cartridge.getName());
				cartridgeItem.setData(cartridge);
				cartridgeTreeItems.add(cartridgeItem);
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

    }*/

	private DocGenCartridge[] filterByCategory(
			DocGenCartridge[] allCartridges,
			DocGenCategory category) {
		List<DocGenCartridge> filteredCartridges = new ArrayList<DocGenCartridge>();
		
		for (DocGenCartridge cartridge: allCartridges) {
			if (category.equals(cartridge.getCategory())) {
				filteredCartridges.add(cartridge);
			}
		}
		
		return filteredCartridges.toArray(new DocGenCartridge[filteredCartridges.size()]);
	}

	@Override
	protected String getPluginId() {
    	return DocumentationUICore.PLUGIN_ID;
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
		
        ProjectPreferences preferences = new ProjectPreferences(getProject(), DocumentationUICore.PLUGIN_ID);
		for(TreeItem item : cartridgeTreeItems) {
			DocGenCartridge cartridge = (DocGenCartridge) item.getData();
			// check if enabled
			item.setChecked(preferences.getBoolean(cartridge.getId(), cartridge.isEnabledByDefault()));
			item.setGrayed(false);
			setCheckOnParent(item);
		}
		activityText.setText(preferences.get(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\"));
	}

	@Override
	protected void performDefaults() {
        ProjectPreferences preferences = new ProjectPreferences(null, DocumentationUICore.PLUGIN_ID);
		
		for(TreeItem item : cartridgeTreeItems) {
			DocGenCartridge cartridge = (DocGenCartridge) item.getData();
			item.setChecked(preferences.getBoolean(cartridge.getId(), false));
			setCheckOnParent(item);
		}
		activityText.setText("C:\\DesignStudio\\");
		preferences.put(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\");
	}

	@Override
    public boolean performOk() {
    	super.performOk();
    	
        ProjectPreferences preferences = new ProjectPreferences(getProject(), DocumentationUICore.PLUGIN_ID);
		
		for(TreeItem item : cartridgeTreeItems) {
			DocGenCartridge cartridge = (DocGenCartridge) item.getData();
			preferences.putBoolean(cartridge.getId(), item.getChecked());
		}
		String activity = activityText.getText();
		String pref = preferences.get(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\");
		if (!StringUtils.isEmpty(activity) && !activity.equals(pref)) {
			preferences.put(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, activityText.getText());
		}
		preferences.flush();
		return true;
    }
	
	
}