package com.odcgroup.page.ocs.ui.preferences;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.model.corporate.ImageFileDescriptor;
import com.odcgroup.page.ocs.PageOCSUIActivator;

/**
 * The PreferencePage for the Icons
 * 
 * @author atr
 */
public class ImagesPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	/** find missing images files */
	private Button browseBtn;
	
	private org.eclipse.swt.widgets.List missingList;
	
	/**
	 * @return root folder to select missing images (default is Triple'A Plus install directory if defined)
	 */
	private String getRootFolder() {
		String root = OCSPluginActivator.getDefault().getPreferenceManager().getInstallDirectory();
		if (StringUtils.isEmpty(root)) {
			root = System.getProperty("user.home");
		}
		return root;
	}
	
	/**
	 * @return Set<ImageFileDescriptor>
	 */
	private List<ImageFileDescriptor> getMissingFiles() {
		Set<ImageFileDescriptor> missingFiles = new HashSet<ImageFileDescriptor>();
		for (ImageDescriptor descriptor : CorporateImagesUtils.getMissingImageFiles()) {
			missingFiles.add(descriptor.getFileDescriptor());
		}
		List<ImageFileDescriptor> list = Arrays.asList(missingFiles.toArray(new ImageFileDescriptor[]{})); 
		Collections.sort(list, new Comparator<ImageFileDescriptor>(){
			public int compare(ImageFileDescriptor left, ImageFileDescriptor right) {
				String leftName = left.getDirectory()+left.getFilename();
				String rightName = right.getDirectory()+right.getFilename();
				return leftName.compareToIgnoreCase(rightName);
			}			
		});		
		return list;
	}		

	/**
	 * 
	 */
	private void changeLoadButtonStatus() {
		List<ImageFileDescriptor> list = getMissingFiles();
		missingList.removeAll();
		for (ImageFileDescriptor descriptor : list) {
			try {
				missingList.add(descriptor.toURL().toString());
			} catch (MalformedURLException ex) {
				String msg = "Invalid image file";
				PageOCSUIActivator.getDefault().logError(msg, ex);
			}
		}
	}
	
	/**
	 * 
	 */
	private void loadMissingFiles() {
		
		List<ImageFileDescriptor> list = getMissingFiles();
		Set<String> extensions = new HashSet<String>();
		
		// collect all extensions.
		for (ImageFileDescriptor descriptor : list) {
			String filename = descriptor.getFilename();
			int pos = filename.lastIndexOf('.');
			if (pos != -1) {
				extensions.add("*"+filename.substring(filename.lastIndexOf('.'), filename.length()));
			}
		}
		
		// prepare file dialog
		FileDialog fd = new FileDialog(getShell(), SWT.MULTI);
		fd.setText("Select");
		if (list.size() == 1) {
			ImageFileDescriptor ifd = list.get(0);
			fd.setFileName(ifd.getFilename());
			String folder = ifd.getDirectory();
			if (StringUtils.isNotEmpty(folder)) {
				fd.setFilterPath(folder);
			} else {
				fd.setFilterPath(getRootFolder());
			}
		} else if (missingList.getSelectionIndex() != -1) {
			int index = missingList.getSelectionIndex();
			ImageFileDescriptor ifd = list.get(index);
			fd.setFileName(ifd.getFilename());
			String folder = ifd.getDirectory();
			if (StringUtils.isNotEmpty(folder)) {
				fd.setFilterPath(folder);
			} else {
				fd.setFilterPath(getRootFolder());
			}
		} else {
			fd.setFilterPath(getRootFolder());
			fd.setFileName("*");
		}
		String[] filterExt = extensions.toArray(new String[]{});
		fd.setFilterExtensions(filterExt);
		
		// select file and add them to the default repository
		if (fd.open() != null) {
			CorporateImagesUtils.importCustomRegularImages(fd.getFilterPath(), fd.getFileNames());
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
		
		createLabel(displayFormatPanel, "List of unresolved icons:");

		missingList = new org.eclipse.swt.widgets.List(displayFormatPanel, SWT.BORDER | SWT.V_SCROLL);
        gData = new GridData(GridData.FILL_BOTH);
        gData.heightHint = 200;
        missingList.setLayoutData(gData);
		
		createLabel(displayFormatPanel, "In order to view the icons in Design Studio, browse for the missing icon files");

		browseBtn = new Button(displayFormatPanel, SWT.NULL);
		browseBtn.setText("Browse...");
		// selection listener
		browseBtn.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				loadMissingFiles();
				changeLoadButtonStatus();
			}
		});
		
		changeLoadButtonStatus();
		
		return displayFormatPanel;

	}
	
	/**
	 * Called when user clicks Apply or OK
	 * 
	 * @return boolean
	 */
	public boolean performOk() {
		// TODO save into default preferences
		return true;
	}	
	
	/**
	 * Called when user clicks Restore Defaults
	 */
	protected void performDefaults() {
	}
	
	/*
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * @param title
	 */
	public ImagesPreferencePage() {
		noDefaultAndApplyButton();
	}
	
}
	