package com.odcgroup.page.ocs.ui.preferences;

import java.util.List;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;
import com.odcgroup.page.metamodel.display.DisplayFormatUtils;

/**
 * The PreferencePage for the Display Formats
 * 
 * @author atr
 */
public class DisplayFormatPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	/** display formats load/reload button */
	private Button loadBtn;
	
	private List<String> dataFormats = null;
	
	private Font titleLabelFont = null;
	
	private Font loadBtnFont = null;
	
	private void initialize() {
		List<String> formats = DisplayFormatUtils.getDisplayFormat(null).getDataFormats();
		if (formats.isEmpty()) {
			loadBtn.setText("Load");
		} else {
			loadBtn.setText("Reload");
		}
		changeLoadButtonStatus();
	}		

	private void changeLoadButtonStatus() {
		if (!HumanPatternsPreferenceManager.humanPropertiesAvailable()) {
			loadBtn.setEnabled(false);
		} else {
			loadBtn.setEnabled(true);
		}
	}
	
	/**
	 * Creates a Label.
	 * 
	 * @param parent The parent SWT Widget
	 * @param text The text of the Label
	 * @return Label The newly created Label
	 */
	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText(text);
		return label;
	}
	
	/**
	 * @see com.odcgroup.workbench.ui.preferences.FieldEditorOverlayPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		
		Composite displayFormatPanel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
        GridData gData = new GridData(GridData.FILL_BOTH);
        gData.verticalAlignment = SWT.BEGINNING;		
		displayFormatPanel.setLayout(layout);
		displayFormatPanel.setLayoutData(gData);
		
		Label titleLabel = createLabel(displayFormatPanel, "Display Format:");
		titleLabelFont = new Font(parent.getDisplay(), new FontData("Arial", 10, SWT.BOLD));
		titleLabel.setFont(titleLabelFont);
		
		createLabel(displayFormatPanel, "The available display format can be (re)loaded " +
				"from the \n HumanPattern.properties file of your" +
				" Triple'A Plus installation. (Set Triple'A Plus runtime first)");

		loadBtn = new Button(displayFormatPanel, SWT.NULL);
		loadBtnFont = new Font(parent.getDisplay(), new FontData("Arial", 10, SWT.BOLD));
		loadBtn.setFont(loadBtnFont);
		
		
		initialize();
		
		// listen to the changes to the OCS runtime preferences
		OCSSupportUICore.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {			
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().startsWith(OCSRuntimePreference.PREFIX)) {
					if (!loadBtn.isDisposed())
						changeLoadButtonStatus();
				}
			}			
		});
		
		// selection listener
		loadBtn.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				dataFormats = HumanPatternsPreferenceManager.loadHumanPropertiesFromOCSRuntime();
			}
		});
		
		return displayFormatPanel;

	}
	
	/**
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		if (titleLabelFont != null) {
			titleLabelFont.dispose();
		}
		if (loadBtnFont != null) {
			loadBtnFont.dispose();
		}
		super.dispose();
	}

	/**
	 * Called when user clicks Apply or OK
	 * 
	 * @return boolean
	 */
	public boolean performOk() {
		if (dataFormats != null) {
			DisplayFormatUtils.getDisplayFormat(null).setDataFormats(dataFormats);
		}
		return true;
	}	
	
	/**
	 * Called when user clicks Restore Defaults
	 */
	protected void performDefaults() {
		dataFormats = DisplayFormatUtils.getDisplayFormat(null).getDefaultDataFormats();
	}
	
	/*
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * @param title
	 */
	public DisplayFormatPreferencePage() {
	}
	
}
	