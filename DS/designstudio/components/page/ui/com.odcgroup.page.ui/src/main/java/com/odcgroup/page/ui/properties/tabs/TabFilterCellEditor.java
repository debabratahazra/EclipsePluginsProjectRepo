package com.odcgroup.page.ui.properties.tabs;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.ui.snippet.controls.FilterDefinitionDialog;

/**
 * @author atr
 */
public class TabFilterCellEditor extends DialogCellEditor {

	/** The Property */
	private Property property;

	/**
	 * @param dataset the name of the dataset
	 * @param attribute the name of an attribute in the dataset
	 * @param value the filter value
	 * @return IFilter
	 */
	private IFilter createFilter(String dataset, String attribute, String value) {
		ISnippetFactory factory = SnippetUtil.getSnippetFactory();
		IFilterSet filterset = factory.createFilterSet();
		filterset.setTargetDataset(dataset);
		filterset.setLevel(99);
		IFilter filter = SnippetUtil.getSnippetFactory().createFilter(filterset);
		filter.setDatasetAttribute(attribute);
		filter.setEditMode("hidden");
		filter.setOperator("EQUAL");
		filter.setValue(value);
		filter.setOtherValue("");
		return filter;
	}

	/**
	 * Open dialog window
	 * 
	 * @param cellEditorWindow
	 *            The cell editor window
	 * @return Object
	 */
	protected Object openDialogBox(Control cellEditorWindow) {

		String result = "";

		// the tab widget
		Widget tab = property.getWidget();

		// tabbed pane
		Widget tabbedPane = tab.getParent();
		if (tabbedPane != null) {
			Property filteredAttribute = tabbedPane.findProperty("tabs-filtered-attribute");
			if (filteredAttribute != null && StringUtils.isNotEmpty(filteredAttribute.getValue())) {
				Property dataset = tabbedPane.findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
				if (dataset != null && StringUtils.isNotEmpty(dataset.getValue())) {
					String datasetName = dataset.getValue();
					/*
					 * create a temporary filter in order to be able to open
					 * the dialog and edit the values.
					 */
					IFilter	filter = createFilter(datasetName, filteredAttribute.getValue(), property.getValue());
					FilterDefinitionDialog dlg = 
						new FilterDefinitionDialog (cellEditorWindow.getShell(), 
													filter.getParent(), 
													filter, 
													datasetName);
					dlg.setEditModeEnabled(false);
					dlg.setOperatorEnabled(false);
					dlg.setAttributeEnabled(false);
					if (Dialog.OK == dlg.open()) {
						result = filter.getValue();
					}
				} else {
					// We cannot define a filter if we cannot find the dataset
					MessageDialog.openError(cellEditorWindow.getShell(), "Error", "No dataset defined");
				}
			} else {
				// We cannot define a filter if we cannot find the filtered
				// attribute
				MessageDialog.openError(cellEditorWindow.getShell(), "Error",
						"No filtered attribute defined on tabbed pane.");
			}
		}
		return result;

	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            The parent component
	 * @param property
	 *            The selected property
	 */
	public TabFilterCellEditor(Composite parent, Property property) {
		super(parent);
		this.property = property;
	}

}
