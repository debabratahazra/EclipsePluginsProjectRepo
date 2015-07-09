package com.odcgroup.page.ui.properties;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.ui.dialog.image.ImageDialog;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Defines a Image Chooser 
 * 
 * @author atr
 */
public class ImageCellEditor extends DialogCellEditor {
	
	/**
	 * @return the list of all disabled image descriptors
	 */
	private List<ImageDescriptor> getEnabledImageDescriptors() {
		IOfsProject ofsProject = EclipseUtils.findCurrentProject();
		List<ImageDescriptor> descriptors = 
			CorporateImagesUtils.getCorporateImages(ofsProject).getEnabledImageDescriptors();		
		Collections.sort(descriptors, new Comparator<ImageDescriptor>(){
			public int compare(ImageDescriptor left, ImageDescriptor right) {
				return left.getName().compareToIgnoreCase(right.getName());
			}			
		});
		return descriptors;
	}	

	/**
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		String name = null;
		ImageDialog dialog = new ImageDialog(cellEditorWindow.getShell(), false);
		dialog.setImageDescriptors(getEnabledImageDescriptors());
		if (Dialog.OK == dialog.open()) {
			List<ImageDescriptor> descriptors = dialog.getSelectedDescriptors();
			if (descriptors.size() > 0) {
				ImageDescriptor descriptor = descriptors.get(0);
				name = descriptor.getName();
			}
		}
		return name;
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            The parent component
	 */
	public ImageCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            The parent component
	 * @param style
	 *            The style to set
	 */
	public ImageCellEditor(Composite parent, int style) {
		super(parent, style);
	}
}