package com.odcgroup.page.ui.preferences;

import static com.odcgroup.page.model.corporate.CorporateImagesConstants.PROPERTY_IMAGE_DESCRIPTORS;
import static com.odcgroup.page.model.corporate.CorporateImagesConstants.PROPERTY_STORE_ID;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.ui.dialog.image.ImageDialog;
import com.odcgroup.page.ui.dialog.image.ImageUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider;
import com.odcgroup.workbench.ui.preferences.table.TableColumnDescriptor;
import com.odcgroup.workbench.ui.preferences.table.TableFieldDescriptor;
import com.odcgroup.workbench.ui.preferences.table.TableFieldEditor;

/**
 * Preferences page for the Images/Icons used in the Page Designer
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class CorporateImagePreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPropertyPage {

	/** The column for the icon */
	private static final String COLUMN_ICON = "Icon";

	/** The column for the name of the image */
	private static final String COLUMN_NAME = "Name";

	/** The column for the filename */
	private static final String COLUMN_FILENAME = "Filename";

	/** The column for the x coordinate */
	private static final String COLUMN_TYPE = "Type";

	/** Stores owning element of properties */
	private IAdaptable element;

	/**
	 * The list of all image descriptors
	 */
	private List<ImageDescriptor> allImageDescriptors;
	
	/** */
	private Map<String, Image> imagesCache = new HashMap<String, Image>();
	
	/**
	 * @param editor
	 */
	private void addImageDescriptors(TableFieldEditor editor) {
		ImageDialog dlg = new ImageDialog(getShell(), true);
		dlg.setImageDescriptors(getDisabledImageDescriptors());
		if (Dialog.OK == dlg.open()) {
			for (ImageDescriptor descriptor : dlg.getSelectedDescriptors()) {
				descriptor.setEnable(true);
				editor.addItem(descriptor);
			}
			editor.refresh();
		}
	}
	
	/**
	 * @param editor
	 * @throws MalformedURLException 
	 */
	private void removeImageDescriptors(TableFieldEditor editor) {
		for (Object obj : editor.removeSelectedItems()) {
			ImageDescriptor descriptor = (ImageDescriptor)obj;
			if (descriptor.getFileDescriptor().isRegular()) {
				getAllImageDescriptors().remove(descriptor);
				// try to delete the file
				try {
					File file = null;
					file = new File(descriptor.getFileDescriptor().toURL().getPath());
					if (file.exists()) {
						file.delete();
					}
				} catch (MalformedURLException ex) {
					// ignore
				}
			}
			descriptor.setDisabled(true);
		}
		editor.refresh();
	}
	
	/**
	 * Comparator for the image descriptor's name
	 */
	private class ImageDescriptorNameComparator implements Comparator<ImageDescriptor> {
		/**
		 * @param left
		 * @param right
		 * @return int
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(ImageDescriptor left, ImageDescriptor right) {
			return left.getName().compareToIgnoreCase(right.getName());
		}
	}
	
	/**
	 * Comparator for the image descriptor's name
	 */
	private final ImageDescriptorNameComparator 
			imageDescriptorNameComparator = new ImageDescriptorNameComparator();
	
	/**
	 * Sets the current list of all image descriptors
	 * @param list
	 */
	protected final void setAllImageDescriptors(List<ImageDescriptor> list) {
		// sort the descriptors by name  
		Collections.sort(list, imageDescriptorNameComparator);
		this.allImageDescriptors = list;
	}
	
	/**
	 * @return list of all image descriptors
	 */
	protected final List<ImageDescriptor> getAllImageDescriptors() {
		return this.allImageDescriptors;
	}
	

	/**
	 * @return the list of all disabled image descriptors
	 */
	@SuppressWarnings("unchecked")
	protected List<ImageDescriptor> getDisabledImageDescriptors() {
		return (List<ImageDescriptor>)
		CollectionUtils.select(this.allImageDescriptors, new Predicate() {
			public boolean evaluate(Object obj) {
				return ((ImageDescriptor)obj).isDisabled();
			}
		});
	}

	/**
	 * @return the list of all disabled image descriptors
	 */
	@SuppressWarnings("unchecked")
	protected List<ImageDescriptor> getEnabledImageDescriptors() {
		return (List<ImageDescriptor>)
		CollectionUtils.select(this.allImageDescriptors, new Predicate() {
			public boolean evaluate(Object obj) {
				return ((ImageDescriptor)obj).isEnabled();
			}
		});
	}

	/**
	 * @return the IOfsProject
	 */
	private IOfsProject getOfsProject() {
		IProject project = (IProject)getElement().getAdapter(IProject.class);
		return OfsCore.getOfsProjectManager().getOfsProject(project);
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	protected void createFieldEditors() {

		TableFieldDescriptor imgTableDescriptor = new TableFieldDescriptor(new TableColumnDescriptor[] {
				new TableColumnDescriptor(COLUMN_ICON, 40), 
				new TableColumnDescriptor(COLUMN_NAME, 110, imageDescriptorNameComparator),
				new TableColumnDescriptor(COLUMN_FILENAME, 230), 
				new TableColumnDescriptor(COLUMN_TYPE, 80), 
		});
		imgTableDescriptor.setHeightHint(150);
		imgTableDescriptor.setStyle(SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		imgTableDescriptor.setDataProvider(new AvailableImagesProvider(imagesCache));

		final TableFieldEditor imagesFieldEditor = new TableFieldEditor(PROPERTY_IMAGE_DESCRIPTORS, "Available Icons:",
				imgTableDescriptor, getFieldEditorParent());
		addField(imagesFieldEditor);

		// install actions
		IAction[] actions = { new Action("Add Icon") {
			public void run() {
				addImageDescriptors(imagesFieldEditor);
			}
		}, new Action("Delete Icon") {
			public void run() {
				removeImageDescriptors(imagesFieldEditor);
			}
		} };
		imagesFieldEditor.installContextMenu(actions);

		Composite hbox = new Composite(getFieldEditorParent(), SWT.NONE);
		hbox.setLayout(new GridLayout(2, true));

		Button btnEdit = new Button(hbox, SWT.NONE);
		GridData gd = new GridData();
		gd.widthHint = 50;
		btnEdit.setLayoutData(gd);
		btnEdit.setText("Add");
		btnEdit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				addImageDescriptors(imagesFieldEditor);
			}
		});

		Button btnRemove = new Button(hbox, SWT.NONE);
		gd = new GridData();
		gd.widthHint = 50;
		btnRemove.setLayoutData(gd);
		btnRemove.setText("Delete");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				removeImageDescriptors(imagesFieldEditor);
			}
		});

	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 */
	protected IPreferenceStore doGetPreferenceStore() {
		ProjectScope scope = CorporateImagesUtils.getCorporateImages(getOfsProject()).getProjectScope();
		return new ScopedPreferenceStore(scope, PROPERTY_STORE_ID);
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#dispose()
	 */
	public void dispose() {
		for (Image img : imagesCache.values()) {
			if (img != null) {
				img.dispose();
			}
		}
		imagesCache.clear();
		allImageDescriptors.clear();
		
		super.dispose();
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return element;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}

	/**
	 * 
	 */
	public CorporateImagePreferencesPage() {
		super("Images Settings", FieldEditorPreferencePage.GRID);
	}

	/**
	 * StandardNamespacesProvider
	 */
	private class AvailableImagesProvider implements ITableFieldDataProvider {

		/** */
		private Map<String, Image> imagesCache = null;

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#getImage(java.lang.Object,
		 *      java.lang.String)
		 */
		public Image getImage(Object data, String columnName) {
			Image image = null;
			ImageDescriptor descriptor = (ImageDescriptor) data;
			if (columnName.equals(COLUMN_ICON) && descriptor.isEnabled()) {
				image = ImageUtils.getImageFromDescriptor(Display.getCurrent(), imagesCache, descriptor);
			}
			return image;
		}

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#getValue(java.lang.Object,
		 *      java.lang.String)
		 */
		public Object getValue(Object data, String columnName) {
			Object value = null;
			if (data instanceof ImageDescriptor) {
				ImageDescriptor info = (ImageDescriptor) data;
				if (columnName.equals(COLUMN_NAME)) {
					value = info.getName();
				} else if (columnName.equals(COLUMN_FILENAME)) {
					value = info.getFileDescriptor().getFilename();
				} else if (columnName.equals(COLUMN_TYPE)) {
					value = info.getType().name();
				}
			}
			return value;
		}

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#setValue(java.lang.Object,
		 *      java.lang.String, java.lang.Object)
		 */
		public void setValue(Object data, String columnName, Object value) {
		}

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#load(java.lang.String)
		 */
		public Object[] load(String name) {
			setAllImageDescriptors(CorporateImagesUtils.getCorporateImages(getOfsProject()).getAllImageDescriptors());
			return getEnabledImageDescriptors().toArray();
		}

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#loadDefault(java.lang.String)
		 */
		public Object[] loadDefault(String preferenceName) {
			setAllImageDescriptors(CorporateImagesUtils.getDefaultImageDescriptors());
			return getEnabledImageDescriptors().toArray();
		}

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#store(java.lang.String,
		 *      java.lang.Object[])
		 */
		public void store(String preferenceName, Object[] data) {
			Map<String, ImageDescriptor> map = new HashMap<String, ImageDescriptor>();
			for (ImageDescriptor id : getAllImageDescriptors()) {
				map.put(id.getName(), id);
			}
			for (Object obj : data) {
				ImageDescriptor id = (ImageDescriptor) obj;
				if (!map.containsKey(id.getName())) {
					map.put(id.getName(), id);
				}
			}
			CorporateImagesUtils.getCorporateImages(getOfsProject())
					.saveImagesDescriptors(new ArrayList<ImageDescriptor>(map.values()));
		}

		/**
		 * @param imagesCache
		 */
		public AvailableImagesProvider(Map<String, Image> imagesCache) {
			this.imagesCache = imagesCache;
		}

	}

}
