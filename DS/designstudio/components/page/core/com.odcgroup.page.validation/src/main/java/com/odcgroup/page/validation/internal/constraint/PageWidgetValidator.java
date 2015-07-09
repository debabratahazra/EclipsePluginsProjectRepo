package com.odcgroup.page.validation.internal.constraint;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ext.constraints.ConstraintsAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.ModuleContainmentUtil;
import com.odcgroup.page.model.util.SearchModuleUtils;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.page.validation.internal.PageConstraints;
import com.odcgroup.page.validation.internal.PageWidgetEventValidator;
import com.odcgroup.page.validation.internal.PageWidgetTranslationValidator;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Validation rules for widgets
 * 
 * @author atr
 */
public class PageWidgetValidator {

	
	
	/** */
	private PageValidationListener listener;
	
	private String getTabName(Widget tab) {
		if (tab.getTranslationId() > 0) {
			for (Translation trans : tab.getLabels()) {
				if ("en".equals(trans.getLanguage())) {
					String name = trans.getMessage();
					if (null == name) {
						name = "";
					}
					return name;
				}
			}
		}
		int index = tab.getParent().getContents().indexOf(tab);
		return String.valueOf(index+1);
	}

	/**
	 * @param widget
	 * @param STATUS
	 *            the new value of the <em>STATUS</em> property.
	 * @return IStatus
	 */
	private IStatus checkIncludeWidget(Widget widget) {

		// optimistic
		IStatus status = Status.OK_STATUS;

		if (WidgetTypeConstants.INCLUDE.equals(widget.getTypeName())) {

			// get the include property
			Property include = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
			Model model = include.getModel();
			boolean hasValue = !StringUtils.isBlank(include.getValue());

			// is there a model ?
			if (model == null && !hasValue) {
				String message = "Included resource URI is empty.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}

			// is the resource available ?
			else if (model != null && model.eResource() == null) {
				String message = "Included resource is not found. Please check if it needs migration or if it has been deleted.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}

			// has the included model a root widget ?
			else if (model != null && model.getWidget() == null) {
				String message = "Included resource is not not valid. It does not contain a widget.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}

			else {
				// The included model seems to be ok
				// Now check if that included widget is a module and can be
				// contained in the parent module
				Widget rootWidget = widget.getRootWidget();
				String includedWidgetTypeName = include.getModel().getWidget().getTypeName();
				if (rootWidget.getTypeName().equals(WidgetTypeConstants.MODULE)
						&& !ModuleContainmentUtil.canContainModules(rootWidget)
						&& includedWidgetTypeName.equals(WidgetTypeConstants.MODULE)) {

					Property containment = rootWidget.findProperty(PropertyTypeConstants.MODULE_CONTAINMENT);
					String moduleName = rootWidget.eResource().getURI().lastSegment();
					moduleName = moduleName.substring(0, moduleName.lastIndexOf("."));

					String message = "Module \'" + moduleName + "\' cannot contain other module(s)";
					IStatus st = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
					listener.onValidation(st, containment);
					// status = ConstraintStatus.createStatus(context,
					// Collections.singleton(containment),
					// , (Object[]) null);
				} else if (includedWidgetTypeName.equals(WidgetTypeConstants.FRAGMENT)) {
					String dom_assoc = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
					if (!StringUtils.isEmpty(dom_assoc)) {
						Widget includeWidget = include.getModel().getWidget();
						MdfDataset ids = PageConstraints.getDatasetFromWidget(includeWidget);
						MdfDataset ds = PageConstraints.getDatasetFromWidget(rootWidget);
						if (ds != null) {
							MdfDatasetProperty prop = ds.getProperty(dom_assoc);
							if (ids == null || !ids.equals(prop.getType())) {
								String message = "Included Fragment must be based on linked dataset in the Domain Association";
								status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
							}
						}
					}
				}
				
			}
		}

		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableHasColumns(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableHasColumns(widget)) {
			String message = "A Table/Tree must have at least one column.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkMultipleDisplayGroupingColumns(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkMultipleDisplayGroupingColumns(widget)) {
			String message = "A Tree must have exactly one column with 'Display Grouping' set to true.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkConditionWidgetCode(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkConditionWidgetCode(widget)) {
			String message = "Condition java code must not be empty";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkCodeWidgetCode(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkCodeWidgetCode(widget)) {
			String message = "Code must not be empty";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkHierarchyDefinitionLevel(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkHierarchyDefinitionLevel(widget)) {
			String message = "The hierarchy can only be declared on the top level group.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkMultipleHierarchies(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkMultipleHierarchies(widget)) {
			String message = "Only one hierarchy can be declared in the table.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDisplayGroupingColumnNotanAggregate(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkDisplayGroupingColumnNotanAggregate(widget)) {
			String message = "The column [" + PageConstraints.getTableColumnName(widget)
					+ "] cannot be set to display grouping and display aggregate.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkRenderingModeForHierarchyRawData(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkRenderingModeForHierarchyRawData(widget)) {
			String message = "Grouping with a \"raw data\" hierarchy is specified "
					+ "for \"summary-delegate\" rendering mode";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkAggregatesForHierarchyRawData(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkAggregatesForHierarchyRawData(widget)) {
			String message = "Aggregates found when a grouping is defined with a \"raw data\" hierarchy";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkTableItemWidth(Widget widget) {
		IStatus status = Status.OK_STATUS;
		int row = PageConstraints.checkTableItemWidth(widget);
		if (row>-1) {
			String message = "The sum of the width values of all items in line "+(row+1)+" must be equal to 100";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableDuplicateDomainColumn(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableDuplicateDomainColumn(widget)) {
			String message = "The column [" + PageConstraints.getTableColumnName(widget)
					+ "] cannot be displayed more than once.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	@SuppressWarnings("unused")
	private IStatus checkTableDomainColumnHasGroup(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableDomainColumnHasGroup(widget)) {
			String message = "The domain column [" + PageConstraints.getTableColumnName(widget)
					+ "] cannot be a grouping column and appear as a domain column at the same time.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableComputedColumnHasComputation(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableComputedColumnHasComputation(widget)) {
			String message = "The computed column [" + PageConstraints.getTableColumnName(widget)
					+ "] computation must be set";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableComputedColumnHasParameters(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableComputedColumnHasParameters(widget)) {
			String message = "The computed column [" + PageConstraints.getTableColumnName(widget)
					+ "] must have at least one parameter selected";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableComputedColumnComputation(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableComputedColumnComputation(widget)) {
			String message = "The computed column [" + PageConstraints.getTableColumnName(widget)
					+ "] 'type' must be set to 'amount'.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableComputedColumnHasDisplayType(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableComputedColumnHasDisplayType(widget)) {
			String message = "The computed column [" + PageConstraints.getTableColumnName(widget)
					+ "] 'type' must be set.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableGroupsOnDisplayCheckbox(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableGroupsOnDisplayCheckbox(widget)) {
			String message = "Checkbox on tree nodes requires atleast one group on the table";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkCheckboxOnPlaceholder(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkCheckboxOnPlaceholder(widget)) {
			String message = "Table properties on checkboxes set to true " +
					"require checkboxes in a placeholder column";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkSeveralCheckboxesWhenExclusive(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkSeveralCheckboxesWhenExclusive(widget)) {
			String message = "Table property [Make multiple checkboxes exclusive], when true, " +
					"requires several checkboxes in placholder column";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkCheckBoxIdentifierIsNotEmpty(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkCheckBoxIdentifierIsNotEmpty(widget)) {
			String message = "Identifier column is mandatory";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableMultiSelectionAndRenderingMode(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableMultiSelectionAndRenderingMode(widget)) {
			String message = "The multi-selection applies only if for delegating detailed table";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTableMultiSelectionAndSelectDeselectAll(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableMultiSelectionAndSelectDeselectAll(widget)) {
			String message = "The property [Select/de-select all] cannot be true when multiple checkboxes are designed";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkGroupsForHierarchyRawData(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkGroupsForHierarchyRawData(widget)) {
			String message = "Only one grouping is allowed when a \"raw data\" hierarchy is defined";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDisplayGroupingForPlaceHolder(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkDisplayGroupingForPlaceHolder(widget)) {
			String message = "Display grouping must be set on  a placeholder column when a hierarchy is with \"raw data\"";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * DS-3926
	 * 
	 * @param widget
	 * @return
	 */
	private IStatus checkDatasetAttributesInConditionForPlaceHolder(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkDomainAttributesInConditionForPlaceHolder(widget)) {
			String message = "Domain Attributes are not allowed in condition when used in TableTree";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDomainAssociation(Widget widget) {
		IStatus status = Status.OK_STATUS;
		List<Property> properties = widget.getProperties();
		for (Property property : properties) {
			if (PropertyTypeConstants.DOMAIN_ATTRIBUTE.equals(property.getTypeName())) {
				String domAttr = property.getValue();
				if (!StringUtils.isEmpty(domAttr)) {
					Widget root = widget.getRootWidget();
					Property prop = root.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
					String datasetName = prop != null ? prop.getValue() : "";
					if (StringUtils.isNotEmpty(datasetName)) {
						IOfsProject ofsProject = OfsResourceHelper.getOfsProject(root.getModel().eResource());
						if (ofsProject != null) {
							DomainRepository repository = DomainRepository.getInstance(ofsProject);		
							MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
							if (dataset != null) {
								if (dataset.getProperty(domAttr) == null) {
									String message = "The referenced attribute {0} is not found in Dataset {1}"; //$NON-NLS-1$
									message = MessageFormat.format(message, new Object[] { domAttr, datasetName });
									status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
								}
							} 
						}
					}
				}
			}
		}
		return status;		
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkPlaceHolderDisplayGroupingOnCheckbox(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkPlaceHolderDisplayGroupingOnCheckbox(widget)) {
			ITableColumn column = TableHelper.getTableColumn(widget);
			String message = "Placeholder column ["+column.getColumnName()+"] cannot " +
					"use Display grouping and checkbox together";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	@SuppressWarnings("unused")
	private IStatus checkTableFilterOnPlaceholderWithCheckbox(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableFilterOnPlaceholderWithCheckbox(widget)) {
			String message = "Filter on table is not allowed when " +
					"Placeholder column with checkbox is designed";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkIsValidComputation(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkIsValidComputation(widget)) {
			String message = "The computation '" + PageConstraints.getColumnComputation(widget)
					+ "' is not valid for the rendering mode '" + PageConstraints.getTableRenderingMode(widget) + "'.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkHasValidAggregatedComputation(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (PageConstraints.getValidAggregatedComputation(widget) != null) {
			String message = "The aggregation computation '" + PageConstraints.getValidAggregatedComputation(widget)
					+ "' is not valid for the rendering mode '" + PageConstraints.getRenderingMode(widget) + "'.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkTypeforaCheckboxPlaceholder(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTypeforaCheckboxPlaceholder(widget)) {
			String message = "The type of a checkbox column must be set to boolean";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkAggregatesInSummaryMode(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkAggregatesInSummaryMode(widget)) {
			String message = "The column [" + PageConstraints.getTableColumnName(widget)
					+ "] and its parameters must have an aggregate defined.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkGroupingsInSummaryMode(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkGroupingsInSummaryMode(widget)) {
			String message = "At least one grouping must be declared in 'summary-delegated' rendering mode";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	// private IStatus checkTableAggregateHasGroup(Widget widget) {
	// IStatus status = Status.OK_STATUS;
	// if (!PageConstraints.checkTableAggregateHasGroup(widget)) {
	// String message = "The aggregated column [" +
	// PageConstraints.getTableAggregateColumnName(widget)
	// + "] must be displayed on at least one Group.";
	// status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message,
	// null);
	// }
	// return status;
	// }

	/**
	 * @param widget
	 * @return IStatus
	 */
	// private IStatus checkTableAggregateNotOnFirstColumn(Widget widget) {
	// IStatus status = Status.OK_STATUS;
	// if (!PageConstraints.checkTableAggregateNotOnFirstColumn(widget)) {
	// String message = "The aggregated column [" +
	// PageConstraints.getTableAggregateColumnName(widget)
	// + "] must not be the left most column.";
	// status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message,
	// null);
	// }
	// return status;
	// }

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkComboboxDomainHasPermittedValues(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkComboboxDomainHasPermittedValues(widget)) {
			String domainAttributeName = WidgetUtils.getDomainAttribute(widget);
			String message = "This attribute [" + domainAttributeName
					+ "] doesn't support combo box, please check attribute type or annotation.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkComboboxInEditableTableDomainHasPermittedValues(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkComboboxInEditableTableDomainHasPermittedValues(widget)) {
			String domainAttributeName = widget.getPropertyValue(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
			String message = "This attribute [" + domainAttributeName
					+ "] doesn't support combo box, please check attribute type or annotation.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	
	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkImageIsAvailable(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkImageIsAvailable(widget)) {
			String message = "The icon [" + PageConstraints.getImageName(widget) + "] used in ["
					+ PageConstraints.getModelName(widget) + "] is not available in the list of available icons.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkImageExists(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkImageExists(widget)) {
			String message = "The icon [" + PageConstraints.getImageLocation(widget) + "] used in ["
					+ PageConstraints.getModelName(widget)
					+ "] is not available in the repository. The repository should be updated.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkIsSortDefinedOnDetailedDelegate(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkIsSortDefinedOnDetailedDelegate(widget)) {
			String message = "At least one sort must be declared in 'detailed-delegated' rendering mode";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * search modules (container/input) should not include fragments based on
	 * domain entity
	 * 
	 * @param module
	 * @return
	 */
	private IStatus checkModuleHasDomainFragments(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.moduleWithDomainBasedFragments(module)) {
			String message = "Contains fragment(s) that are based on a domain entity.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * A container module is allowed to include only one input module
	 * 
	 * @param module
	 * @return
	 */
	private IStatus containerHasMoreInputModules(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.hasMoreInputModules(module)) {
			String message = "More than one input modules are included. Only one input module is supported.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param module
	 * @return status
	 */
	private IStatus checkSupportWidgetsForInputModules(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.checkSupportedWidgetsForInput(module)) {
			String message = "Contains widget(s) that are not supported";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param module
	 * @return status
	 */
	private IStatus checkSupportWidgetsForContainerModules(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.checkSupportedWidgetsForContainer(module)) {
			String message = "Contains widget(s) that are not supported";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * A container module is allowed to include only one output module
	 * 
	 * @param module
	 * @return
	 */
	private IStatus containerHasMoreOutputModules(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.hasMoreOutputModules(module)) {
			String message = "More than one output modules are included. Only one output module is supported.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;

	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkAtLeastOneColumnInFilter(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableAtLeastOnColumnInFilter(widget)) {
			String message = "At least one column must have \"is part of the filter\" set to True";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
//	/**
//	 * @param widget
//	 * @return
//	 */
//	private IStatus checkSortColumnOnGroup(Widget widget) {
//		IStatus status = Status.OK_STATUS;
//		if (!PageConstraints.checkSortColumnExistsAsColumn(widget)) {
//			String message = "The sorting column of a grouping has to be added in the table (we recommend to add it as a Table Extra)";
//			status = getErrorStatus(message);
//		}
//		return status;
//	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkGroupForRaditoButton(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkRadioButtonGroup(widget)) {
			String message = "Group must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkIDForRaditoButton(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkRadioButtonID(widget)) {
			String message = "ID must be specified";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	@SuppressWarnings("unused")
	private IStatus checkSearchEventOnSearchField(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkSearchEvent(widget)) {
			String message = "Search Field must contain an event of type \"search\"";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkOtherEventsOnSearchField(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkOtherEventsOnSearchField(widget)) {
			String message = "Event types OnClick, OnChange, OnEnter cannot be set along with Search Event";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkWidgetGroupOnSearchField(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkWidgetGroupOnSearchField(widget)) {
			String message = "\"Widget Group\" property value must start with value set in the event parameter \"widget-group-ref\"";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkAutoCompleteMandatoryFields(Widget widget) {
		IStatus status = Status.OK_STATUS;
		String typeName = widget.getTypeName();
		if (WidgetTypeConstants.SEARCH_FIELD.equals(typeName) 
				|| WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(typeName)) {
			ISearchField sf = WidgetHelper.getSearchField(widget);
			if (sf.isAutoCompleteOnly() || sf.isAutoCompleteAndSearch()) {
				String msg = "";
				if (sf.getOutputDesign() == null) {
					msg = "Auto-complete Design Module is required";
				} else if (sf.getNumberOfCharacters() <= 0) {
					msg = "No. of character is not specified";
				}
				if (!StringUtils.isEmpty(msg)) {
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, msg, null);					
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMandatoryFieldsAutoCompleteDesign(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (widget.getTypeName().equals("AutoCompleteDesign")) {
			IAutoCompleteDesign design = WidgetHelper.getAutoCompleteDesign(widget);
			if (StringUtils.isEmpty(design.getTitleAttribute()) && (design.getLines().isEmpty())) {
				String msg = "Title Attribute or atleast one line is mandatory";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, msg, null);	
			}
		}
		return status;
	}

	/**
	 * A non search module should not contain input/output modules
	 * 
	 * @param module
	 * @return
	 */
	private IStatus checkSearchWidgets(Widget module) {
		IStatus status = Status.OK_STATUS;
		if (!SearchModuleUtils.checkSearchWidgetsinNonSearchModule(module)) {
			String message = "Search specific modules found";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;

	}

	/**
	 * @param fragment
	 * @return
	 */
	private IStatus checkFragmentsWithDefaultLinkedDatasets(Widget fragment) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkFragmentsWithDefaultLinkedDataset(fragment)) {
			String entity = fragment.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			String message = "A default fragment with the same domain entity [" + entity + "] is already defined";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param include
	 * @return
	 */
	private IStatus validateIncludedFragment(Widget include) {
		IStatus status = Status.OK_STATUS;
		if (isInclude(include)) {
			if (!PageConstraints.checkComboboxValidationInIncludes(include)) {
				String message = "Included fragment contains combobox that expects a MML annotation on domain attribute";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus validateGridColumnsTotalWidth(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkGridColumnTotalWidth(widget)) {
			String message = "Sum of grid columns width exceeds 100%, please review width repartition";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus validateGridColumnWidth(Widget widget) {
		IStatus status = Status.OK_STATUS;
		int val = PageConstraints.checkGridColumnsWidthNotSet(widget);
		if (val > -1) {
			String message = "Grid Column [" + val + "] width is 0, please enter a value greater than that";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	private IStatus validateTranslationId(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTranslationId(widget)) {
			String message = "The translationId must have a value when at least one translation exists, please check the migration";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isInclude(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.INCLUDE);
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isExternalInclude(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.EXTERNAL_INCLUDE_WIDGET);
	}

	private static boolean isDynamicTabbedPane(Widget tabbedPane) {
		boolean result = false;
		if ("TabbedPane".equals(tabbedPane.getTypeName())) {
			Property p = tabbedPane.findProperty("hide-empty-tabs");
			result = (p != null && StringUtils.equalsIgnoreCase("true", p.getValue()));
		}
		return result;
	}

	private static boolean isTab(Widget tab) {
		return "Tab".equals(tab.getTypeName());
	}
	
	private static boolean isDynamicTab(Widget tab) {
		boolean result = false;
		if ("Tab".equals(tab.getTypeName())) {
			Property p = tab.findProperty("hide-empty-tab");
			result = (p != null && StringUtils.equalsIgnoreCase("true", p.getValue()));
		}
		return result;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDynamicTabbedPaneInContainer(Widget tabbedPane) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTabbedPane(tabbedPane)) {
			Widget module = tabbedPane.getRootWidget();
			if (!ModuleContainmentUtil.isContainerModule(module)) {
				String message = "Dynamic tabbed pane must be in container module";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDynamicTabbedPaneFilteredAttribute(Widget tabbedPane) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTabbedPane(tabbedPane)) {
			Property p = tabbedPane.findProperty("tabs-filtered-attribute");
			if (p != null && StringUtils.isEmpty(p.getValue())) {
				String message = "Dynamic tabbed pane requires an attribute.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	/**
	 * @param tab
	 * @return
	 */
	private IStatus checkDynamicTabBelongsToDynamicTabbedPane(Widget tab) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTab(tab)) {
			if (!isDynamicTabbedPane(tab.getParent())) {
				String name = getTabName(tab);
				String message = "The Dynamic tab ["+name+"] (hidden if empty) must belong to a dynamic tabbed pane.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	/**
	 * @param tab
	 * @return
	 */
	private IStatus checkFirstTabIsNotDynamic(Widget tab) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTab(tab)) {
			int pos = tab.getParent().getContents().indexOf(tab);
			if (0 == pos) {
				String message = "The first tab of a dynamic tabbed pane cannot be hidden if empty.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	/**
	 * If the tab Contains an event of type on-click with method = submit having a
	 * filter set defined)
	 */
	@SuppressWarnings("unused")
	private IStatus checkDynamicTabWithOnClickSubmitHavingFilterSet(Widget tab) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTab(tab)) {
			for (Event event : tab.getEvents()) {
				if ("OnClick".equalsIgnoreCase(event.getEventName()) &&
					"submit".equalsIgnoreCase(event.getFunctionName())) {
					for (Snippet snippet : event.getSnippets()) {
						if ("FilterSet".equalsIgnoreCase(snippet.getTypeName())) {
							String message = "Dynamic Tab with an event of type OnClick with function submit having a filter set defined could lead to incoherent behavior.";
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
						}
					}
				}
			}
		}
		return status;
	}

	/**
	 * If the tab Contains an event of type on-click with method = submit having a
	 * filter set defined)
	 */
	private IStatus checkDynamicTabWithFilter(Widget tab) {
		IStatus status = Status.OK_STATUS;
		Widget tabbedPane = tab.getParent();
		if (isDynamicTab(tab) && isDynamicTabbedPane(tabbedPane)) {
			Property p = tabbedPane.findProperty("tabs-filtered-attribute");
			if (p != null && !StringUtils.isEmpty(p.getValue())) {
				p = tab.findProperty("tab-filter");
				if (p != null && StringUtils.isEmpty(p.getValue())) {
					String message = "Dynamic Tab ["+getTabName(tab)+"] must have a filter defined.";
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
				}
			}
		}
		return status;
	}
	
	/**
	 * The filter level must be in range 1..99 for dynamic tabbed pane
	 */
	private IStatus checkDynamicTabbedPaneFilterLevel(Widget tabbedPane) {
		IStatus status = Status.OK_STATUS;
		if (isDynamicTabbedPane(tabbedPane)) {
			Property p = tabbedPane.findProperty("filter-level");
			if (p != null) {
				int level = p.getIntValue();
				if (level <= 0 || level > 99) {
					String message = "Dynamic TabbedPane's filter level must be in range 1..99.";
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
				}
			}
		}
		return status;
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private IStatus checkDynamicTabContentsWithFilter(Widget tab) {
		IStatus status = Status.OK_STATUS;
		Widget tabbedPane = tab.getParent();
		if (isDynamicTab(tab) && isDynamicTabbedPane(tabbedPane)) {
			List<Widget> contents = tab.getContents();
			for (Widget widget : contents) {
				if(containsWidgetsWithKeepFilter(widget)) {
					String message = "Table keep filter doesn't participate to the tab filtering. " +
							"Please integrate the keep filter into definition of format" +
							" element used for filtering the tabbed pane.";
					return new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, message, null);					
				}
			}
		}
		return status;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private boolean containsWidgetsWithKeepFilter(Widget widget) {
		if ("Include".equals(widget.getTypeName())) {
			Property property = widget.findProperty("includeSrc");
			Model model = property.getModel();
			if (model != null) {
				List<Widget> contents = model.getWidget().getContents();
				for (Widget child : contents) {
					if (containsWidgetsWithKeepFilter(child)) {
						return true;
					} 
				}
			}
		} else if ("TableTree".equals(widget.getTypeName())) {
			ITable table = TableHelper.getTable(widget);
			if (!table.getKeepFilters().isEmpty()) {
				return true;
			}
		} else if ("Matrix".equals(widget.getTypeName())) {
			IMatrix matrix = MatrixHelper.getMatrix(widget);
			if (!matrix.getKeepFilters().isEmpty()) {
				return true;
			}
		} else {
			List<Widget> contents = widget.getContents();
			for (Widget child : contents) {
				if (containsWidgetsWithKeepFilter(child)) {
					return true;
				} 
			}
		}
		return false;
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private IStatus checkDynamicTabbedPaneWithOnClickOnTab(Widget tab) {
		IStatus status = Status.OK_STATUS;
		Widget tabbedPane = tab.getParent();
		if (isTab(tab) && isDynamicTabbedPane(tabbedPane)) {
			for (Event event : tab.getEvents()) {
				if (!"OnClick".equals(event.getEventName()) 
						|| !"submit".equals(event.getFunctionName())) {
					String message = "Event set on dynamic tabs must be of type OnClick with function submit";
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);					
				}
			}
		}
		return status;
	}
	
	/**
	 * @param tab
	 * @return
	 */
	private IStatus checkCustomEventParamsOnDynamicTab(Widget tab) {
		IStatus status = Status.OK_STATUS;
		Widget tabbedPane = tab.getParent();
		
		Map<String, String> props = new HashMap<String, String>();
		props.put(ParameterTypeConstants.METHOD, "post");
		props.put(ParameterTypeConstants.ONLY_CHANGED, "true");
		props.put(ParameterTypeConstants.TARGET, ParameterTypeConstants.TARGET_DEFAULT_VALUE);
		props.put(ParameterTypeConstants.CALL_URI, ParameterTypeConstants.SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE);
		
		if (isTab(tab) && isDynamicTabbedPane(tabbedPane)) {
			for (Event event : tab.getEvents()) {
				if ("OnClick".equals(event.getEventName()) 
						&& "submit".equals(event.getFunctionName())) {
					List<Parameter> params = event.getParameters();
					for (Parameter param : params) {
						if (props.containsKey(param.getName()) 
								&& !StringUtils.isEmpty(param.getValue())
								&& ! props.get(param.getName()).equals(param.getValue())) {
							String message = "Property "+param.getName()+" is already used for the dynamic " +
									"tab event Design Studio generates with the value " + 
									props.get(param.getName()) +
									". Same value is required";
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);					
						}
					}
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkColumnsInMatrixContentCell(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELL.equals(widget.getTypeName())) {
			IMatrixContentCell cell = MatrixHelper.getMatrixContentCell(widget);
			if (cell.getItems().isEmpty()) {
				String message = "At least one column must be selected to be displayed in the matrix cells.";
				return getErrorStatus(message);
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkAxisColumnsInMatrixContentCell(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELL.equals(widget.getTypeName())) {
			IMatrixContentCell cell = MatrixHelper.getMatrixContentCell(widget);
			IMatrix matrix = cell.getParent();
			List<String> columns = new ArrayList<String>();
			columns.add(matrix.getXAxis().getDomainAttribute().getValue());
			columns.add(matrix.getYAxis().getDomainAttribute().getValue());
			for(IMatrixContentCellItem item : cell.getItems()) {
				String column = item.getDomainAttribute().getValue();
				if (columns.contains(column)) {
					String message = "The column ["+column+"] cannot be displayed in a cell and in an axis at the same time.";
					return getErrorStatus(message);
				}
			}
		}
		return status;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDuplicateColumnsInAxis(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_AXIS.equals(widget.getTypeName())) {
			IMatrixAxis axis = MatrixHelper.getMatrixAxis(widget);
			IMatrix matrix = axis.getParent();
			IMatrixAxis otherAxis = null;
			if (axis.isXAxis()) {
				otherAxis = matrix.getYAxis();
			} else {
				otherAxis = matrix.getXAxis();
			}
			String column = axis.getDomainAttribute().getValue();
			if (!StringUtils.isEmpty(column)) {
				if (column.equals(otherAxis.getDomainAttribute().getValue())) {
					String message = "The column ["+column+"] cannot be displayed in both axis.";
					return getErrorStatus(message);
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDuplicateColumnsInCell(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELL.equals(widget.getTypeName())) {
			IMatrixContentCell cell = MatrixHelper.getMatrixContentCell(widget);
			List<IMatrixContentCellItem> items = cell.getDomainItems();
			List<String> domAttributes = new ArrayList<String>();
			String domAttr = null;
			for (IMatrixContentCellItem item : items) {
				domAttr = item.getDomainAttribute().getValue();
				if (domAttributes.contains(domAttr)) {
					String message = "The column ["+domAttr+"] cannot be displayed more than once in the cells";
					return getErrorStatus(message);
				} else {
					domAttributes.add(domAttr);
				}
			}
		}
		return status;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixExtras(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX.equals(widget.getTypeName())) {
			IMatrix matrix = MatrixHelper.getMatrix(widget);
			IMatrixContentCell cell = matrix.getMatrixCell();
			List<IMatrixContentCellItem> items = cell.getDomainItems();
			Map<String, String> domAttributes = new HashMap<String, String>();
			String domAttr = null;
			for (IMatrixContentCellItem item : items) {
				domAttr = item.getDomainAttribute().getValue();
				domAttributes.put(domAttr, item.getAggregationType().getValue());				
			}
			for(IMatrixExtra extra : matrix.getExtras()) {
				if ("sum".equals(extra.getAggregationType())) {
					String str = extra.getColumnName();
					MdfDatasetProperty dsProperty = extra.getAttribute();
					DataType type = getOperatorKeyType(dsProperty.getType());
					if (!type.equals(DataType.AMOUNT) && !type.equals(DataType.NUMBER)) {
						String message = "Matrix Extra Data ["+str+"] doesnot support SUM aggregation type";
						return getErrorStatus(message);
					}
				}				
			}
		}
		return status;	
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDuplicateAggregations(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem cItem = MatrixHelper.getMatrixContentCellItem(widget);
			IMatrixContentCell cell = cItem.getMatrix().getMatrixCell();
			List<IMatrixContentCellItem> items = cell.getDomainItems();
			Map<String, String> domAttributes = new HashMap<String, String>();
			String domAttr = null;
			for (IMatrixContentCellItem item : items) {
				domAttr = item.getDomainAttribute().getValue();
				domAttributes.put(domAttr, item.getAggregationType().getValue());				
			}
			for(String str :cItem.getComputationParameters()) {
				if (domAttributes.containsKey(str) && 
						!domAttributes.get(str).equals(cItem.getAggregationType().getValue())) {
					String message = "The aggregation-type must be the same for the " +
							"computed column[comp_"+cItem.getID()+"] and the attribute["+str+"].";
					return getErrorStatus(message);
				}
			}
		} else if (WidgetTypeConstants.MATRIX.equals(widget.getTypeName())) {
			IMatrix matrix = MatrixHelper.getMatrix(widget);
			IMatrixContentCell cell = matrix.getMatrixCell();
			List<IMatrixContentCellItem> items = cell.getDomainItems();
			Map<String, String> domAttributes = new HashMap<String, String>();
			String domAttr = null;
			for (IMatrixContentCellItem item : items) {
				domAttr = item.getDomainAttribute().getValue();
				domAttributes.put(domAttr, item.getAggregationType().getValue());				
			}
			for(IMatrixExtra extra : matrix.getExtras()) {
				String str = extra.getColumnName();
				if (domAttributes.containsKey(str) && 
						!domAttributes.get(str).equals(extra.getAggregationType())) {
					String message = "The aggregation-type must be the same for the " +
							"matrix extra["+str+"] and the cell item["+str+"].";
					return getErrorStatus(message);
				}
			}
		}
		return status;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkColumnInMatrixAxis(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_AXIS.equals(widget.getTypeName())) {
			IMatrixAxis axis = MatrixHelper.getMatrixAxis(widget);
			String value = axis.getDomainAttribute().getValue();
			if (StringUtils.isEmpty(value)) {
				String axisType = axis.isXAxis() ? "x-axis" : "y-axis";
				String message = "A column must be chosen for the "+axisType;
				return getErrorStatus(message);
			}
		}
		return status;		
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkExtraColumnItemsDisplay(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_EXTRACOLUMN.equals(widget.getTypeName())) {
			IMatrixExtraColumn extraColumn = MatrixHelper.getMatrixExtraColumn(widget);
			List<IMatrixExtraColumnItem> items = extraColumn.getItems();
			int i = 0;
			for (IMatrixExtraColumnItem item : items) {
				if (item.displayLastRow()) {
					i++;
				}
			}
			if (i > 1) {
				String message = "Only one extra column can be displayed as the last column of a matrix.";
				return getErrorStatus(message);
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixCompType(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			if (item.isComputed()) {
				String computation = item.getComputation().getValue();
				if (StringUtils.isEmpty(computation)) {
					return status;
				} else {
					String displayType = item.getDisplayFormat().getValue();
					if (StringUtils.isEmpty(displayType)) {
						String message = "An appropriate display format is required for the computed column.";
						return getErrorStatus(message);
					} else {
						if (("relative-percent".equals(computation) && !displayType.startsWith("percentTA"))
								||("make-amount".equals(computation) && !displayType.startsWith("amount")) 
								||("compute-percentage".equals(computation) && !displayType.startsWith("percent"))) {
							String message = "Wrong display format selected for the ["+computation+"] computation.";
							return getErrorStatus(message);							
						}
					}
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixItemSimplifiedCondition(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			String conditionType = item.getEnabledIsBasedOn().getValue();
			String enabled = item.getEnabled().getValue();
			if (!("advanced".equals(conditionType)) && "conditional".equals(enabled)) {
				IMatrix matrix = item.getMatrix();
				IMatrixContentCell cell = matrix.getMatrixCell();
				List<IMatrixContentCellItem> items = cell.getDomainItems();
				for (IMatrixContentCellItem cItem : items) {
					if (conditionType.equals(cItem.getDomainAttribute().getValue())) {
						return status;
					}
				}
				List<IMatrixExtra> extras = matrix.getExtras();
				for (IMatrixExtra extra : extras) {
					if (conditionType.equals(extra.getColumnName())) {
						return status;
					}
				}
				String message = "Attribute ["+conditionType+"] must be dropped in the cell or should be part of matrix extra columns";
				return getErrorStatus(message);
			}
			
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixComputationItemWeightedMean(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			if (item.isWeightedMeanAggregation()) {
				String param = null;
				if (item.isComputed() ) {
					List<String> params = item.getComputationParameters();
					if (params.size() > 0) {
						param = params.get(0);						
					}
				} else {
					param = item.getDomainAttribute().getValue();
				}
				if (param != null) {
					MdfDatasetProperty dsProperty = item.getDatasetProperty(param);
					DataType type = getOperatorKeyType(dsProperty.getType());
					if (!type.equals(DataType.NUMBER) && !type.equals(DataType.AMOUNT)) {
						String message = "The main column of the weighted-mean aggregation must be of type number.";
						return getErrorStatus(message);
					} 
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixItemMeanWeightType(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			if (item.isWeightedMeanAggregation()) {
				String meanWeight = item.getMeanWeight().getValue();
				if (!StringUtils.isEmpty(meanWeight)) {
					MdfDatasetProperty dsProperty = item.getDatasetProperty(meanWeight);
					DataType type = getOperatorKeyType(dsProperty.getType());
					if (!type.equals(DataType.AMOUNT) && !type.equals(DataType.NUMBER)) {
						String message = "The weighted-mean's weight must be of type amount or number.";
						return getErrorStatus(message);
					} 
				} else {
					String message = "The weighted-mean's weight is mandatory and cannot be empty.";
					return getErrorStatus(message);
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixAggregateItemsId(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CELLITEM.equals(widget.getTypeName())) {
			IMatrixCellItem item = MatrixHelper.getMatrixCellItem(widget);
			Widget matrixCell = item.getWidget().getParent();
			Widget matrix = matrixCell.getParent();
			List<IMatrixContentCellItem> matrixContentCellItem = new ArrayList<IMatrixContentCellItem>();
			Widget matrixContentCell = getMatrixContentCell(matrix, matrixContentCellItem);
			getMatrixContentCellFromMatrix(matrixContentCell,WidgetTypeConstants.MATRIX_CONTENTCELLITEM,matrixContentCellItem);
			List<String> matrixContentCellItemAggrItems = new ArrayList<String>();
			for (IMatrixContentCellItem mCItem : matrixContentCellItem) {
				matrixContentCellItemAggrItems.addAll(mCItem.getAggregationItems());
			}
			if (item.isComputed() && !matrixContentCellItemAggrItems.contains(item.getID())) {
				Object[] cellItem = {item.getColumnName()};
				MessageFormat mf = new MessageFormat("The matrix cell item {0} points to unknown aggregate items. Please redo the design of the {0}.");
				String message = mf.format(cellItem);
				return getErrorStatus(message);
			} 
		}
		return status;
	}

	/**
	 * @param matrix
	 * @param matrixContentCellItem
	 * @return
	 */
	private Widget getMatrixContentCell(Widget matrix,
			List<IMatrixContentCellItem> matrixContentCellItem) {
		List<Widget> widgets = matrix.getContents();
		for (Widget wid : widgets) {
			String type = wid.getTypeName();
			if(type.equals(WidgetTypeConstants.MATRIX_CONTENTCELL)){
				return wid;
			}
		}
		return null;
	}
		
	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkMatrixComputationParamType(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			if (item.isComputed()) {
				String computation = item.getComputation().getValue();
				if (StringUtils.isEmpty(computation)) {
					return status;
				}
				if ("compute-percentage".equals(computation)) {					
					List<String> params = item.getComputationParameters();
					String message = "A Compute Percentage computation can only use one attribute.";
					if (params.size() != 1) {
						return getErrorStatus(message);
					}		
					MdfDatasetProperty dsProperty = null;
					dsProperty = item.getDatasetProperty(params.get(0));
					DataType type = getOperatorKeyType(dsProperty.getType());
					if (!type.equals(DataType.AMOUNT) && !type.equals(DataType.NUMBER)) {
						message = "A Compute Percentage computation can only use one attribute of type amount or number.";
						return getErrorStatus(message);
					} 
				} else if ("difference".equals(computation)) {
					List<String> params = item.getComputationParameters();
					MdfDatasetProperty dsProperty = null;
					for (String string : params) {
						dsProperty = item.getDatasetProperty(string);
						DataType type = getOperatorKeyType(dsProperty.getType());
						if (!type.equals(DataType.NUMBER)) {
							String message = "A ["+computation+"] computation can only use attributes of type number.";
							return getErrorStatus(message);
						} 
					}
				} else if("make-amount".equals(computation)) {
					List<String> params = item.getComputationParameters();
					String message = "A make amount computation needs 2 parameters: " +
							"the first one contains the number, the second one the currency.";
					if (params.size() != 2) {
						return getErrorStatus(message);
					}		
					MdfDatasetProperty dsProperty = null;
					boolean amtOrNum = false;
					DataType string = null;
					
					// first param should be either an amount or number type
					String str = params.get(0);
					dsProperty = item.getDatasetProperty(str);
					if(dsProperty==null) {
						return getErrorStatus("The type of the first parameter cannot be resolved.");
					}
					DataType type = getOperatorKeyType(dsProperty.getType());
					if (DataType.NUMBER.equals(type)) {
						amtOrNum = true;
					} else if (DataType.AMOUNT.equals(type)) {
						amtOrNum = true;
					} 
					if (!amtOrNum) {
						return getErrorStatus("The type of the first parameter is not allowed. " +
								"This parameter does not contain a number.");
					}
					
					// second param should be a string type
					str = params.get(1);
					dsProperty = item.getDatasetProperty(str);
					type = getOperatorKeyType(dsProperty.getType());
					if (DataType.STRING.equals(type)) {
						string = type;
					}					
					if (string == null) {
						return getErrorStatus("The type of the second parameter is not allowed. " +
								"This parameter does not contain a currency.");
					}				
				} else if ("exchange".equals(computation)) {
					List<String> params = item.getComputationParameters();
					MdfDatasetProperty dsProperty = null;
					DataType number = null;
					DataType amount = null;
					DataType string = null;
					for (String str : params) {
						dsProperty = item.getDatasetProperty(str);
						DataType type = getOperatorKeyType(dsProperty.getType());
						if (DataType.NUMBER.equals(type)) {
							number = type;
						} else if (DataType.AMOUNT.equals(type)) {
							amount = type;
						} else if (DataType.STRING.equals(type)) {
							string = type;
						}
					}
					if (params.size() != 3 
							|| (number == null || amount == null || string == null)) {

						String message = "An [exchange] computation needs three attributes: " +
								"an amount, a number and a String (final currency).";
						return getErrorStatus(message);
					}					
				} else if ("multiply".equals(computation)) {
					List<String> params = item.getComputationParameters();
					if (!params.isEmpty()) {
						MdfDatasetProperty dsProperty = item.getDatasetProperty(params.get(0));
						DataType type = getOperatorKeyType(dsProperty.getType());
						if (!DataType.AMOUNT.equals(type)) {
							String message = "A [multiply] computation can only use " +
									"attributes of type amount for the first column";
							return getErrorStatus(message);
						}
						if (params.size()> 1) {
							for(int ii = 1;ii < params.size();ii++) {
								dsProperty = item.getDatasetProperty(params.get(ii));
								type = getOperatorKeyType(dsProperty.getType());
								if (!DataType.NUMBER.equals(type)) {
									String message = "A [multiply] computation can only use attributes of" +
											" type amount or number for the first column and of " +
											"type number for other columns.";
									return getErrorStatus(message);
									
								}
							}
						}
					}
				}
			}
		}
		return status;		
	}
	
	private IStatus checkDynamicColumnSummaryDelegatedMode(Widget widget) {   /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			if (!column.getTable().getRenderingMode().getValue().equals("summary-delegated")) {
				String message = "A Dynamic column can be used only for summary delegated table";
				status = getErrorStatus(message);
			}
		}
		return status;
	}
	
	private IStatus checkDynamicColumnIsLast(Widget widget) { /* ok */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic() && !column.isLast()) {
			String message = "The Dynamic Column must be the last column";
			status = getErrorStatus(message);
		}
		return status;
	}	

	private IStatus checkDynamicColumnGroupExists(Widget widget) {
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			int count = 0;
			for (ITableGroup group : column.getTable().getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					count++;
					break;
				}
			}
			if (0 == count) {
				String message = "A group with dynamic columns = true must exists";
				status = getErrorStatus(message);
			}
		}
		return status;
	}
	
	private IStatus checkDynamicColumnIsLinkedToLastGroup(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			List<ITableGroup> groups = column.getTable().getGroupsByRank(); 
			int position = -1;
			for (ITableGroup group : groups) {
				position++;
				if (group.isUsedForDynamicColumn()) {
					break;
				}
			}
			if (position < (groups.size()-1)) {
				String message = "Grouping with dynamic columns must be the last one in the list of groups.";
				status = getErrorStatus(message);
			}
		}
		return status;
	}	
	
	private IStatus checkDynamicColumnHasAGroupBefore(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			List<ITableGroup> groups = column.getTable().getGroupsByRank(); 
			int position = -1;
			for (ITableGroup group : groups) {
				position++;
				if (group.isUsedForDynamicColumn()) {
					break;
				}
			}
			if (position < 1) {
				String message = "Grouping with dynamic column must have a grouping (or hierarchy) before";
				status = getErrorStatus(message);
			}
		}
		return status;
	}	

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkDynamicColumnIsUnique(Widget widget) {  /* OK */
		IStatus status = Status.OK_STATUS;
		ITable table = PageConstraints.getTable(widget);
		if (table == null) {
			return status;
		}
		int count = 0;
		for (ITableColumn col : table.getColumns()) {
			if (col.isDynamic()) {
				count++;
				if (count > 1) {
					String message = "Only one dynamic column can be designed on a table";
					status = getErrorStatus(message);
					break;
				}
			}
		}
		return status;
	}	
	
	private IStatus checkDynamicColumnMaxGroupingValue(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			for (ITableGroup group : column.getTable().getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					if (group.getMaxGrouping() != 0) {
						String message = "Max grouping musts be = 0 if dynamic column selected for a grouping.";
						status = getErrorStatus(message);
						break;
					}
				}
			}
		}
		return status;
	}		
	
	private IStatus checkDynamicColumnCollapseValue(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			for (ITableGroup group : column.getTable().getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					if (group.isCollapsed()) {
						String message = "Collapse flag must not be checked";
						status = getErrorStatus(message);
						break;
					}
				}
			}
		}
		return status;
	}		

	private IStatus checkDynamicColumnHierarchyValue(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			for (ITableGroup group : column.getTable().getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					if (group.isHierarchy()) {
						String message = "Grouping cannot be a Hierarchy if dynamic columns selected for a grouping";
						status = getErrorStatus(message);
						break;
					}
				}
			}
		}
		return status;
	}		
	
	private IStatus checkTableDynamicGroupHasDynamicColumns(Widget widget) { /* OK */
		IStatus status = Status.OK_STATUS;
		ITable table = PageConstraints.getTable(widget);
		if (table != null) {
			for (ITableGroup group : table.getGroups()) {
				if (group.isUsedForDynamicColumn()) {
					// check the table has a dynamic column
					boolean hasDynamicColumn = false;
					for (ITableColumn column : table.getColumns()) {
						if (column.isDynamic()) {
							hasDynamicColumn = true;
							break;
						}
					}
					if (!hasDynamicColumn) {
						String message = "Add a Dynamic Column for the Grouping ["+group.getColumnName()+"]";
						status = getErrorStatus(message);
						break;
					}
				}
			}
		}
		return status;
	}		

	/**
	 * @param entity
	 * @return operator type
	 */
	private static DataType getOperatorKeyType(MdfEntity entity) {
		if (entity instanceof MdfBusinessType) {
			return getOperatorKeyType(((MdfBusinessType) entity).getType());
		}
		if (entity != null && entity.isPrimitiveType()) {
			if (entity.getQualifiedName().equals(PrimitivesDomain.STRING.getQualifiedName())) {
				return DataType.STRING;
			} else if(entity.getQualifiedName().equals(PrimitivesDomain.DATE.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.DATE_TIME.getQualifiedName())) {
				return DataType.DATE;
			} else if (entity.getQualifiedName().equals(PrimitivesDomain.BOOLEAN.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.BOOLEAN_OBJ.getQualifiedName())) {
				return DataType.FLAG;
			} else if (entity.getQualifiedName().equals(PrimitivesDomain.INTEGER.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.INTEGER_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.SHORT.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.SHORT_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.LONG.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.LONG_OBJ.getQualifiedName())) {
				return DataType.NUMBER;
			} else if (entity instanceof MdfEnumeration) {
				return DataType.ENUMERATION;
			} else if ( entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.DOUBLE_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.FLOAT.getQualifiedName()) 
					|| entity.getQualifiedName().equals(PrimitivesDomain.FLOAT_OBJ.getQualifiedName())
					|| entity.getQualifiedName().equals(PrimitivesDomain.DECIMAL.getQualifiedName())) {
				return DataType.AMOUNT;
			}
		}
		return DataType.DEFAULT;
	}
	
	/**
	 *
	 */
	public enum DataType {
		ENUMERATION, FLAG, STRING, NUMBER, AMOUNT, DATE, DEFAULT
	}
	
	/**
	 * @param message
	 * @return
	 */
	private IStatus getErrorStatus(String message) {
		return getErrorStatus(message, null);
	}
	
	/**
	 * @param message
	 * @param t
	 * @return
	 */
	private IStatus getErrorStatus(String message, Throwable t) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, t);
	}

	/**
	 * @param model
	 *            the model to be validated
	 */
	public void accept(Widget widget) {
		listener.onValidation(checkAggregatesInSummaryMode(widget));
		listener.onValidation(checkComboboxDomainHasPermittedValues(widget));
		listener.onValidation(checkComboboxInEditableTableDomainHasPermittedValues(widget));
		listener.onValidation(checkConditionWidgetCode(widget));
		listener.onValidation(checkCodeWidgetCode(widget));
		listener.onValidation(checkDisplayGroupingColumnNotanAggregate(widget));
		listener.onValidation(checkGroupingsInSummaryMode(widget));
		listener.onValidation(checkHasValidAggregatedComputation(widget));
		listener.onValidation(checkHierarchyDefinitionLevel(widget));
		listener.onValidation(checkImageIsAvailable(widget));
		listener.onValidation(checkImageExists(widget));
		listener.onValidation(checkIncludeWidget(widget));
		listener.onValidation(checkIsValidComputation(widget));
		listener.onValidation(checkMultipleDisplayGroupingColumns(widget));
		listener.onValidation(checkMultipleHierarchies(widget));
		// listener.onValidation(checkTableAggregateHasGroup(widget));
		// listener.onValidation(checkTableAggregateNotOnFirstColumn(widget));
		listener.onValidation(checkTableDuplicateDomainColumn(widget));
		//DS-6760	listener.onValidation(checkTableDomainColumnHasGroup(widget));
		listener.onValidation(checkTableComputedColumnHasComputation(widget));
		listener.onValidation(checkTableComputedColumnHasParameters(widget));
		listener.onValidation(checkTableComputedColumnComputation(widget));
		listener.onValidation(checkTableComputedColumnHasDisplayType(widget));
		listener.onValidation(checkTableHasColumns(widget));
		listener.onValidation(checkIsSortDefinedOnDetailedDelegate(widget));
		listener.onValidation(checkAtLeastOneColumnInFilter(widget));

		listener.onValidation(checkPlaceHolderDisplayGroupingOnCheckbox(widget)); //OK 1
		//listener.onValidation(checkTableFilterOnPlaceholderWithCheckbox(widget)); //OK 2
		listener.onValidation(checkTableGroupsOnDisplayCheckbox(widget)); // OK 3
		listener.onValidation(checkCheckboxOnPlaceholder(widget)); // OK 4
		listener.onValidation(checkSeveralCheckboxesWhenExclusive(widget)); //OK 5
		listener.onValidation(checkTableMultiSelectionAndSelectDeselectAll(widget));//OK 6
		// DS-6437 listener.onValidation(checkMultipleCheckboxesOnTableColumn(widget));//OK 7
		listener.onValidation(checkCheckBoxIdentifierIsNotEmpty(widget));//OK 7
		listener.onValidation(checkTableMultiSelectionAndRenderingMode(widget));//OK
		listener.onValidation(checkTypeforaCheckboxPlaceholder(widget)); // OK
		//DS-3847 listener.onValidation(checkSortColumnOnGroup(widget));
		listener.onValidation(checkCheckBoxWithEnumValue(widget)); // DS-4763

		// DS-3034 Hierarchical Trees
		listener.onValidation(checkGroupsForHierarchyRawData(widget));
		listener.onValidation(checkDisplayGroupingForPlaceHolder(widget));
		listener.onValidation(checkRenderingModeForHierarchyRawData(widget));
		listener.onValidation(checkAggregatesForHierarchyRawData(widget));
		listener.onValidation(checkTableItemWidth(widget));

		// search validations
		listener.onValidation(checkModuleHasDomainFragments(widget));
		listener.onValidation(containerHasMoreInputModules(widget));
		listener.onValidation(containerHasMoreOutputModules(widget));
		listener.onValidation(checkSupportWidgetsForInputModules(widget));
		listener.onValidation(checkSupportWidgetsForContainerModules(widget));
		listener.onValidation(checkSearchWidgets(widget));

		// radiobutton
		listener.onValidation(checkGroupForRaditoButton(widget));
		listener.onValidation(checkIDForRaditoButton(widget));
		listener.onValidation(checkRadioButtonIsOfValidType(widget));

		listener.onValidation(checkFragmentsWithDefaultLinkedDatasets(widget));

		// included fragment DS-3059
		listener.onValidation(validateIncludedFragment(widget));

		// include external widget
		listener.onValidation(validateExternalIncludeWidgetId(widget));
		listener.onValidation(validateExternalIncludeWidgetHasValidExternalReference(widget));
		
		// grid column widths DS-3098
		listener.onValidation(validateGridColumnWidth(widget));
		listener.onValidation(validateGridColumnsTotalWidth(widget));

		// translation
		listener.onValidation(validateTranslationId(widget));

		// dynamic tabbed pane
		listener.onValidation(checkDynamicTabbedPaneInContainer(widget));
		listener.onValidation(checkDynamicTabbedPaneFilteredAttribute(widget));
		listener.onValidation(checkDynamicTabbedPaneFilterLevel(widget));
		listener.onValidation(checkDynamicTabBelongsToDynamicTabbedPane(widget));
		listener.onValidation(checkFirstTabIsNotDynamic(widget));
		//DS-5529
		//listener.onValidation(checkDynamicTabWithOnClickSubmitHavingFilterSet(widget));
		listener.onValidation(checkDynamicTabWithFilter(widget));
		listener.onValidation(checkDynamicTabbedPaneWithOnClickOnTab(widget));
		listener.onValidation(checkDynamicTabContentsWithFilter(widget));
		
		// matrix
		listener.onValidation(checkColumnsInMatrixContentCell(widget));
		listener.onValidation(checkColumnInMatrixAxis(widget));
		listener.onValidation(checkAxisColumnsInMatrixContentCell(widget));
		listener.onValidation(checkDuplicateColumnsInAxis(widget));
		listener.onValidation(checkExtraColumnItemsDisplay(widget));
		listener.onValidation(checkMatrixAggregateItemsId(widget)); 
		
		listener.onValidation(checkMatrixComputationParamType(widget));
		listener.onValidation(checkMatrixCompType(widget));
		listener.onValidation(checkDuplicateColumnsInCell(widget));
		listener.onValidation(checkDuplicateAggregations(widget));
		listener.onValidation(checkMatrixExtras(widget));
		listener.onValidation(checkMatrixItemSimplifiedCondition(widget));
		
		// Dynamic Columns in Table/Tree
		listener.onValidation(checkTableDynamicGroupHasDynamicColumns(widget)); /* OK */
		listener.onValidation(checkDynamicColumnSummaryDelegatedMode(widget)); /* OK */
		listener.onValidation(checkDynamicColumnGroupExists(widget)); /* OK */
		listener.onValidation(checkDynamicColumnIsLast(widget)); /* ok */
		listener.onValidation(checkDynamicColumnIsLinkedToLastGroup(widget)); /* OK */
		listener.onValidation(checkDynamicColumnHasAGroupBefore(widget));/* OK */
		listener.onValidation(checkDynamicColumnIsUnique(widget)); /*OK*/
		listener.onValidation(checkDynamicColumnMaxGroupingValue(widget)); /*OK*/
		listener.onValidation(checkDynamicColumnCollapseValue(widget)); /*OK*/
		listener.onValidation(checkDynamicColumnHierarchyValue(widget)); /*OK*/
		
		//DS-4298 weighted-mean for tables and matrices
		listener.onValidation(checkMatrixComputationItemWeightedMean(widget));
		listener.onValidation(checkMatrixItemMeanWeightType(widget));
		
		//DS-4058
		//listener.onValidation(checkDynamicColumnSortOrderIsSet(widget));
		
		//DS-4059
		listener.onValidation(checkDynamicColumnContainsValueWithColumnDefined(widget));
		
		
		//DS-3926
		listener.onValidation(checkDatasetAttributesInConditionForPlaceHolder(widget));
		
		//DS-4035
		listener.onValidation(checkDomainAssociation(widget));
		
		//auto-complete
		//listener.onValidation(checkSearchEventOnSearchField(widget));
		listener.onValidation(checkOtherEventsOnSearchField(widget));
		listener.onValidation(checkWidgetGroupOnSearchField(widget));
		listener.onValidation(checkAutoCompleteMandatoryFields(widget));
		listener.onValidation(checkMandatoryFieldsAutoCompleteDesign(widget));

		listener.onValidation(checkMaxDBMaxDisplayLength(widget));
		listener.onValidation(checkSortIsNotSetOnSummaryTable(widget));
		listener.onValidation(checkSortingColumnGroupHasAggregateDefined(widget));
		
		//textfield DS-4558 Precision and scale
		listener.onValidation(checkPrecisionAndScaleAreNotPartiallyComplete(widget));
		//commented DS#4558
		listener.onValidation(checkDiffOfPrecisionAndScaleValue(widget));
		
		// Checks all events
		new PageWidgetEventValidator(listener, widget).checkConstraints();
		
		// Translation validation
		new PageWidgetTranslationValidator(listener, widget).checkConstraints();
		//check for the dataset bound to the xtooltip type frgaments.
		listener.onValidation(checkIncludeFragmentDataSet(widget));
		//DS-5389 xtooltip validation - check xtooltip attributes are configured in table widget
		listener.onValidation(checkEachAttributeOfIncludedXtooltipFragment(widget));

		// check for table column editable widgets
		listener.onValidation(checkTableColumnEditableWidgetInRegularModule(widget));
		listener.onValidation(checkTableColumnTextSumTreeColEvent(widget));
		listener.onValidation(checkTableDatasetBasedAttributes(widget));
		
		//DS-4992
		listener.onValidation(checkDynamicGroupHasXtooltip(widget));
		//DS-4989
		//listener.onValidation(checkTableColumnTypeForGroup(widget));
		//DS-5356
		listener.onValidation(checkSecurityColumnOnCheckbox(widget));
		
		//DS-5529
		listener.onValidation(checkCustomEventParamsOnDynamicTab(widget));
		
		//DS-4871
		listener.onValidation(checkValidTableGroupForCheckBox(widget));
		
		//DS-6063
		listener.onValidation(checkModuleforIncludeXSP(widget));
	}
	
	
	public IStatus checkModuleforIncludeXSP(Widget widget) {

		IStatus status = Status.OK_STATUS;
		String typeName = widget.getTypeName();
		if (WidgetTypeConstants.INCLUDE_XSP.equals(typeName)) {
			Widget root = widget.getRootWidget();
			if (WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
				if (!"none".equals(root.getPropertyValue(PropertyTypeConstants.SEARCH)) || (!"container".equals(root.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT)))) {
					String message = "The widget can only be contained in a " +
							"regular Module with container containment";
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
				}
			}
		}
		return status;
		}

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkSecurityColumnOnCheckbox(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkSecurityColumnOnCheckbox(widget)) {
			String message = "Security column and enable property cannot be used simultaneously";
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}
	

	/*private IStatus checkTableColumnTypeForGroup(Widget widget) {
		IStatus status = Status.OK_STATUS;
		if (!PageConstraints.checkTableColumnType(widget)) {
			String message = "Table groups can only be placed in the PlaceHolder table column.";
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;		
	}*/

	/**
	 * @param widget
	 * @return
	 */
	private IStatus checkTableDatasetBasedAttributes(Widget widget) {
		IStatus status = Status.OK_STATUS;
		String typeName = widget.getTypeName();
		if (WidgetTypeConstants.TABLE_TREE.equals(typeName)) {
			String dsname = widget.getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			// editable dataset is set on table
			if (StringUtils.isNotEmpty(dsname)) {
				// check row identifier
				String rowId = widget.getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_ROW_IDENTIFIER);
				if (StringUtils.isBlank(rowId)) {
    				String message = "Dataset Identifier Attribute is mandatory if \"Dataset\" is set";
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);					
				}
				// check format identifier
				String formatId = widget.getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_FORMAT_IDENTIFIER);
				if (StringUtils.isBlank(formatId)) {
    				String message = "Format Identifier Attribute is mandatory if \"Dataset\" is set";
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);					
				}
			}
		}		
		return status;
	}
	
	
//	/**
//	 * @param widget
//	 * @return
//	 */
//	private IStatus checkTableRowPermissionAttribute(Widget widget) {
//		IStatus status = Status.OK_STATUS;
//		String typeName = widget.getTypeName();
//		if (WidgetTypeConstants.TABLE_TREE.equals(typeName)) {
//			Widget root = widget.getRootWidget();
//			if (WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
//				String search = root.getPropertyValue(PropertyTypeConstants.SEARCH);
//				if (StringUtils.isNotEmpty(search) && "none".equals(search)) {	
//					Property prop = root.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
//					String dsname = prop != null ? prop.getValue() : "";
//					if (StringUtils.isNotBlank(dsname)) {
//						IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
//		        		DomainRepository repository = DomainRepository.getInstance(ofsProject);
//		        		MdfDataset ds = repository.getDataset(MdfNameFactory.createMdfName(dsname));
//		        		
//		        		String rowId = widget.getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_FORMAT_IDENTIFIER);
//		        		if (StringUtils.isNotEmpty(rowId) && !attributeExists(ds, rowId)) {
//	        				String message = "The row identifier attribute must exist in the format dataset";
//	    					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
//		        		}
//		        		
//		        		String permission = widget.getPropertyValue(PropertyTypeConstants.TABLE_FORMAT_ROW_PERMISSION);
//		        		if (StringUtils.isNotEmpty(permission) && !attributeExists(ds, permission)) {
//	        				String message = "The row permission attribute must exist in the format dataset";
//	    					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
//		        		}
//		        		
//		        		if (StringUtils.isNotEmpty(rowId) && StringUtils.isNotEmpty(permission) ) {
//		        			if (rowId.equals(permission)) {
//		        				String message = "The row permission attribute must be different from the Format Identifier attribute";
//		    					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
//		        			}
//		        		}
//		        		
//					}
//				}
//			}
//		}
//		return status;
//	}
	
//	/**
//	 * @param ds
//	 * @param attr
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	private boolean attributeExists(MdfDataset ds, String attr) {
//		List<MdfDatasetProperty> props = ds.getProperties();
//		for (MdfDatasetProperty prop : props) {
//			if (prop.getName().equals(attr)) {
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * @param widget
	 * @return
	 */
	IStatus checkTableColumnEditableWidgetInRegularModule(Widget widget) {
		IStatus status = Status.OK_STATUS;
		String typeName = widget.getTypeName();
		if ("TableColumnText".equals(typeName) 
				||"TableColumnTextArea".equals(typeName) 
				||"TableColumnCheckbox".equals(typeName) 
				||"TableColumnSearch".equals(typeName) 
				||"TableColumnCalendar".equals(typeName) 
				||"TableColumnCombobox".equals(typeName)) {
			Widget root = widget.getRootWidget();
			if (WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
				if (!"none".equals(root.getPropertyValue(PropertyTypeConstants.SEARCH))) {
					String message = "The widget can only be contained in a " +
							"TableColumn of a Table in a regular Module";
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
				}
			}
		}
		return status;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	IStatus checkTableColumnTextSumTreeColEvent(Widget widget) {
		IStatus status = Status.OK_STATUS;
		String message = "";
		boolean okstatus = true;
		List<Event> events = widget.getEvents();
		for (Event event : events) {
			if ("OnChange".equals(event.getEventName()) && "sumTreeCol".equals(event.getFunctionName())) {
				String typeName = widget.getTypeName();
				if (WidgetTypeConstants.TABLE_COLUMN_TEXT_ITEM.equals(typeName) && (null != widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN))) {
					ITableColumn tcol = TableHelper.getTableColumn(widget.getParent());
					ITable table = tcol.getTable();
					if (table.hasGroups()) {
						List<ITableAggregate> aggregates = table.getAggregatedColumns();
						if (aggregates.size() > 0) {
							String domAttr = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
							if (StringUtils.isNotBlank(domAttr)) {
								boolean comp = false;
								for (ITableAggregate aggr : aggregates) {
									if (domAttr.equals(aggr.getColumnName()) && "sum".equals(aggr.getComputation())) {
										comp = true;
										break;
									}
								}
								okstatus = comp;
							}
						} else {
							// no aggreates defines, The event is accepted only if the table
							// contains ONE group based on a hierarchy.
							List<ITableGroup> groups = table.getGroups();
							okstatus = groups.size() == 1 && groups.get(0).isHierarchy();
						}
						if (!okstatus) {
							message = "The Event OnChange/sumTreeCol only valid with Text Field " +
									"in Table column having an aggregate sum or a hierarchy.";
						}
					} else {
						okstatus = false;
						message = "The Event OnChange/sumTreeCol is only valid with a table having grouping.";
					}
				} else {
					okstatus = false;
					message = "The Event OnChange/sumTreeCol is only valid for a text field in an editable table.";
				}				
			}
		}
		if (!okstatus) {
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
		}
		return status;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private IStatus checkWithinValidBusinessTypeRange(Widget widget, String propertyName) {
		
		IStatus status = Status.OK_STATUS;
		
		if (WidgetTypeConstants.TEXTFIELD.equals(widget.getTypeName())) {
		
			String domainAttributeProperty = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);

			if (StringUtils.isNotBlank(domainAttributeProperty)) {
				MdfDataset dataset = PageConstraints.getDatasetFromWidget(widget);
				
				MdfDatasetProperty datasetProperty = dataset.getProperty(domainAttributeProperty);
				MdfEntity datasetPropertyType = datasetProperty.getType();
				
				if (datasetPropertyType.getParentDomain().getName().equalsIgnoreCase("AAABusinessTypes")) {
				
					Property property = widget.findProperty(propertyName);
					String propertyValue = property.getValue();
				
					if (StringUtils.isNotBlank(propertyValue) && (!propertyValue.contains("(BT)"))) {
				
						List<MdfAnnotationImpl> annotations = datasetPropertyType.getAnnotations();
				
						for (MdfAnnotationImpl annotation : annotations) {
							if (annotation.getName().equalsIgnoreCase("constraints")) {
								List<MdfAnnotationPropertyImpl> annotationProperties = annotation.getProperties();
								for (MdfAnnotationPropertyImpl annotationProperty : annotationProperties) {
									String propertyTypeName = property.getTypeName();
									String annotationPropertyName = annotationProperty.getName();
				
									if (annotationPropertyName.equalsIgnoreCase(propertyTypeName)) {
										String annotationPropertyValue = annotationProperty.getValue();
										try {
											int currentPropertyValue = Integer.parseInt(propertyValue);
											int annotationPropertyIntValue = Integer.parseInt(annotationPropertyValue);
				
											if (currentPropertyValue > annotationPropertyIntValue) {
												String message = propertyName + " value must be less than or equal to the default maximimum value("+ annotationPropertyIntValue+") for Business Type :" + propertyTypeName;
												status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
											}
				
										} catch (NumberFormatException e) {
											String message = propertyName + " value must be a valid integer number.";
											status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return status;
	}
	
	
	protected IStatus checkPrecisionAndScaleAreNotPartiallyComplete(Widget widget) {
		IStatus status = Status.OK_STATUS;
		
		if (widget != null) {
			String widgetTypeName = widget.getTypeName();
			if (WidgetTypeConstants.TEXTFIELD.equalsIgnoreCase(widgetTypeName)) {
				
				Property scaleProperty     = widget.findProperty(PropertyTypeConstants.SCALE);
				Property precisionProperty = widget.findProperty(PropertyTypeConstants.PRECISION);
				if (precisionProperty != null && scaleProperty != null) {
					String scaleValue     = scaleProperty.getValue();
					String precisionValue = precisionProperty.getValue();
					if(StringUtils.isEmpty(scaleValue)){
						scaleValue=getPropertyFromDataset(widget, PropertyTypeConstants.SCALE);
					}
					if(StringUtils.isEmpty(precisionValue)){
						precisionValue=getPropertyFromDataset(widget, PropertyTypeConstants.PRECISION);
					}
					if(scaleValue==null&&precisionValue==null){
						return status;
					}
					if ((StringUtils.isNotEmpty(scaleValue) ^ StringUtils.isNotEmpty(precisionValue))) {
						String message = "Both precision and scale values must be entered.";
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
					}
				}
			}
		}
		
		return status;
	}
   /*
    * validate the 	-Precision - scale ≥ 0
    * @param widget
    * @return IStatus
    */
	protected IStatus checkDiffOfPrecisionAndScaleValue(Widget widget){
          IStatus status = Status.OK_STATUS;
	      if (widget != null) {
			String widgetTypeName = widget.getTypeName();
			if (WidgetTypeConstants.TEXTFIELD.equalsIgnoreCase(widgetTypeName)) {
				Property scaleProperty     = widget.findProperty(PropertyTypeConstants.SCALE);
				Property precisionProperty = widget.findProperty(PropertyTypeConstants.PRECISION);
				if (precisionProperty != null && scaleProperty != null) {
					String scaleValue     = scaleProperty.getValue();
					String precisionValue = precisionProperty.getValue();
					if(StringUtils.isEmpty(scaleValue)){
						scaleValue=getPropertyFromDataset(widget, PropertyTypeConstants.SCALE);
					}
					if(StringUtils.isEmpty(precisionValue)){
						precisionValue=getPropertyFromDataset(widget, PropertyTypeConstants.PRECISION);
					}
					if(scaleValue==null&&precisionValue==null){
						return status;
					}
					else if(StringUtils.isEmpty(precisionValue)||StringUtils.isEmpty(scaleValue)){
						return status;
					}
					int iscaleValue=0;
					int iprecisionValue=0;
					try{
					if (StringUtils.isNotBlank(scaleValue)) { 
						if(scaleValue.contains("(BT)")){
							scaleValue=scaleValue.substring(0, scaleValue.indexOf("(BT)"));
						}
						iscaleValue=Integer.parseInt(scaleValue.trim());
					}
					if (StringUtils.isNotBlank(precisionValue)) {
						if(precisionValue.contains("(BT)")){
							precisionValue=precisionValue.substring(0, precisionValue.indexOf("(BT)"));
						}
						iprecisionValue=Integer.parseInt(precisionValue.trim());
					}
					}catch(NumberFormatException e){
						
					}
					
					if(iprecisionValue-iscaleValue <0){
						String message = "Total Digits must be greater or equals to Fractional digits";
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
					}
				
				}
			}
	      }
	      return status;
	}
	
	/**
	 * populates MatrixContentCellItem from the matrix.
	 * @param widget
	 * @param matrixType
	 * @param matrixContentCellItem
	 */
	private void getMatrixContentCellFromMatrix(Widget widget,String matrixType,List<IMatrixContentCellItem> matrixContentCellItem){
		List<Widget> widgets = widget.getContents();
		for (Widget wid : widgets) {
			String type = wid.getTypeName();
			if (type.equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM)) {
				IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(wid);
				matrixContentCellItem.add(item);
			}
			else if (!wid.getContents().isEmpty()) {
				getMatrixContentCellFromMatrix(wid, matrixType, matrixContentCellItem);
			}
		}
	}
	
	/*
	 * get the property values from the dataset.
	 * @param widget
	 * @param propertyName
	 * @return
	 */
	private String getPropertyFromDataset(Widget widget,String propertyName){
		String dAttr="";
		String propertyValue=null;
		 dAttr= widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		
		if (!StringUtils.isEmpty(dAttr)) {
			MdfDataset ds= PageConstraints.getDatasetFromWidget(widget);
			if (ds != null) {
				MdfDatasetProperty dataSetprop = ds.getProperty(dAttr);
				MdfEntity entity = dataSetprop.getType();
				MdfAnnotationProperty property=null;
				String dataType=null;
				if (entity instanceof MdfBusinessType) {
					dataType=((MdfBusinessType)entity).getType().getName();
				if(dataType.equalsIgnoreCase("double")||dataType.equals("decimal")||dataType.equalsIgnoreCase("float")){
					MdfAnnotation constraints=ConstraintsAspect.getConstraints((MdfBusinessType)entity);
					if(constraints!=null){
						//get the precision /scale property .
						property=constraints.getProperty(propertyName);
						if(property!=null){
							propertyValue=property.getValue();
						}
						
					}
					 
				}
			}
		 }	
		}
		return propertyValue;
	}
	private IStatus validateExternalIncludeWidgetHasValidExternalReference(Widget widget) {
		IStatus status = Status.OK_STATUS;

		if (WidgetTypeConstants.EXTERNAL_INCLUDE_WIDGET.equals(widget.getTypeName())) {
			String urlString = widget.getPropertyValue(PropertyTypeConstants.URL);
			
			String domainAttribute = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE_WITHOUT_VALIDATOR);
			boolean domainAttributePopulated = false;
			
			if(domainAttribute != null && domainAttribute.length() > 0) {
				domainAttributePopulated = true;
			}
			else {
				domainAttributePopulated = false;
			}
			
			boolean urlStringPopulated = false;
			
			if (urlString != null && urlString.length() > 0) {
				urlStringPopulated = true;	
			}
			else {
				urlStringPopulated = false;
			}
			if(!domainAttributePopulated & !urlStringPopulated) {
				String message = "A Domain Attribute or URL must be entered.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
			else if(domainAttributePopulated & urlStringPopulated) {
				String message = "only one of the Domain Attribute or URL should be populated.";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		
		return status;
	}

	private IStatus validateExternalIncludeWidgetId(Widget include) {
		IStatus status = Status.OK_STATUS;
		if (isExternalInclude(include)){
			String id = include.getPropertyValue(PropertyTypeConstants.ID);
			if(id.length() == 0) {
				String message = "External include widget cannot have an id property that is blank";
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			}
		}
		return status;
	}

	

	public IStatus checkRadioButtonIsOfValidType(Widget widget) {
		IStatus status = Status.OK_STATUS; 

		if (widget != null) {
			if (widget.getType() != null) {
				WidgetType widgetType = widget.getType();
				if (widgetType != null) {
					String widgetTypeName = widgetType.getName();
					if (widgetTypeName.equalsIgnoreCase(WidgetTypeConstants.RADIO_BUTTON)) {
						Property domainAttributeProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
						if (domainAttributeProperty != null) {
							MdfDataset dataset = PageConstraints.getDatasetFromWidget(widget);
							if (dataset == null) {
								if (StringUtils.isNotEmpty(domainAttributeProperty.getValue())) {
									return getErrorStatus("Domain Attribute property defined on radio button but no Domain Entity defined on module.");
								} // else ok because it is allowed to have no domain and no domain attribute property defined
							} else {
								if (StringUtils.isNotEmpty(domainAttributeProperty.getValue())) {
									MdfDatasetProperty domainAttributeDatasetProperty = dataset.getProperty(domainAttributeProperty.getValue());
									if (domainAttributeDatasetProperty != null) {
										MdfEntity type = domainAttributeDatasetProperty.getType();
									    if ((type instanceof MdfEnumerationImpl) 
									    		|| (type.getQualifiedName().equals(PrimitivesDomain.BOOLEAN.getQualifiedName()) 
									    		|| type.getQualifiedName().equals(PrimitivesDomain.BOOLEAN_OBJ.getQualifiedName()))) {
											if((type instanceof MdfEnumerationImpl)){
												Property enumProperty = widget.findProperty(PropertyTypeConstants.ENUM_VALUE);
												
												if(!isEnumContainValues((MdfEnumeration)type, enumProperty.getValue())){
													return getErrorStatus("Enumerated value does not exist in the underlying enumeration");
												}
											}else
									    	return Status.OK_STATUS;
										} else {
											return getErrorStatus("Domain attribute is constrained to only attributes of type boolean and enumeration.");
										}
									} else {
										// domainAttributeDatasetProperty has not been set no need to check if of correct type
										return status;
									}
								} // else ok because it is allowed to have a domain but not domain attribute defined
							}
						} else {
							// domainAttributeProperty has not been set no need to check if of correct type
							return status;
						}
					}
					else {
						//Not a radio button so no need to check for domainAttribute
						return status;
					}
				}
			}
		}
	 
		return status;
	}
	
	
	// DS-4763
	protected IStatus checkCheckBoxWithEnumValue(Widget widget) {
		IStatus status = Status.OK_STATUS; 
		if (widget != null) {
			if (widget.getType() != null) {
				WidgetType widgetType = widget.getType();
				if (widgetType != null) {
					String widgetTypeName = widgetType.getName();
					if (widgetTypeName.equalsIgnoreCase(WidgetTypeConstants.CHECKBOX)) {						
						Property enumProperty = widget.findProperty(PropertyTypeConstants.ENUM_VALUE);
						if (enumProperty != null && StringUtils.isNotBlank(enumProperty.getValue())) {
							Property domainAttributeProperty = widget.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
							if (domainAttributeProperty != null) {
								MdfDataset dataset = PageConstraints.getDatasetFromWidget(widget);
								MdfDatasetProperty domainAttributeDatasetProperty = dataset.getProperty(domainAttributeProperty.getValue());
								if (domainAttributeDatasetProperty != null) {
									MdfEntity type = domainAttributeDatasetProperty.getType();
								    if (type instanceof MdfEnumerationImpl) {
										if(!isEnumContainValues((MdfEnumeration)type, enumProperty.getValue())){
											status = getErrorStatus("Enumerated value does not exist in the underlying enumeration");
										}
									} else {
										status = getErrorStatus("Domain attribute is constrained to only attributes of type Enumeration");
									}
								}
							}
						}
					}
				}
			}
		}
	 
		return status;
	}

	
   /*
    * check if MdfEnumeration has values. 
    */
	@SuppressWarnings("unchecked")
	private boolean isEnumContainValues(MdfEnumeration mdfEnum,String enumPropValue){
		boolean flag=false;
	  List<MdfEnumValue> enmValues=mdfEnum.getValues();
	  if(enmValues.isEmpty()||enumPropValue.isEmpty()){
		  return false;
	  }
	  for(MdfEnumValue value:enmValues){
		 if(value.getName().equals(enumPropValue)){
			flag=true; 
		 }
	  }
	  return flag;
	}
	IStatus checkSortingColumnGroupHasAggregateDefined(Widget widget) {
			IStatus status = Status.OK_STATUS;
			ITable table = PageConstraints.getTable(widget);
			if (table != null) {
				boolean isSummary = TableHelper.getTableUtilities()
						.isSummaryRenderingMode(table.getRenderingMode());
				List<ITableGroup> groups = table.getGroups();
				if (isSummary && groups.size() > 0) {
					ArrayList<String> names = buildNameListOfAggregrateCols(table);
					for (ITableGroup tableGroup : groups) {
						String sortingColumn = tableGroup.getSortingColumnName();
						if (!StringUtils.isEmpty(sortingColumn)) {
							if (!names.contains(sortingColumn)) {
								return getErrorStatus("Aggregate function required for sorting grouping column: " + tableGroup.getSortingColumnName());
							}
						}
					}
				}
			}
			return status;
		}


		
		private ArrayList<String> buildNameListOfAggregrateCols(ITable table) {
			ArrayList<String> listOfNames = new ArrayList<String>(); 
			List<ITableAggregate> aggregatedCols = table.getAggregatedColumns();
			for (ITableAggregate iTableAggregate : aggregatedCols) {
				listOfNames.add(iTableAggregate.getColumnName());
			}
			return listOfNames;
		}
	
	 IStatus checkSortIsNotSetOnSummaryTable(Widget widget) {
		IStatus status = Status.OK_STATUS;
		ITable table = PageConstraints.getTable(widget);
		if(table != null) {
		boolean isSummary = TableHelper.getTableUtilities().isSummaryRenderingMode(table.getRenderingMode());
		if(isSummary && table.getSorts().size() > 0) {
			return getErrorStatus("Sort cannot be set on a summary table");
		}
		}
		return status;
	}

	private IStatus checkMaxDBMaxDisplayLength(Widget widget) {
		IStatus status = Status.OK_STATUS;
		Property charsprop = widget.findProperty(PropertyTypeConstants.CHARS);
		if (charsprop != null && widget.isDomainWidget()) {
			// attempt to retrieve the default value from the domain
			IOfsProject ofsProject = OfsResourceHelper.getOfsProject(widget
					.getModel().eResource());
			if (ofsProject != null) { // could be null when the model is not yet
										// saved
				DomainRepository repository = DomainRepository
						.getInstance(ofsProject);
				if (repository != null) {
					MdfDatasetProperty attribute = (MdfDatasetProperty) widget
							.findDomainObject(repository);
					if (attribute != null) {
						int maxChars = charsprop.getIntValue();
						int maxDbLength = 0;
						String value = ModelAnnotationHelper
								.getMaxDatabaseLength(
										attribute.getParentDataset(), attribute);
						if (value != null) {
							if (StringUtils.isNotBlank(value)) {
								try {
									maxDbLength = Integer.valueOf(value)
											.intValue();
								} catch (NumberFormatException ex) {
									return getErrorStatus("The value of the annotation [TripleA/AttrMaxDbLength] is not a number. (Please, check the domain).");
			    }
								if (maxChars > maxDbLength) {
									return getErrorStatus("The property 'Maximum Characters' ("
											+ maxChars
											+ ") cannot be greater than database length ("
											+ maxDbLength + ").");
			}
			}
		}
		}
	}
						}
					}
		return status;
	}

	protected IStatus checkDynamicColumnContainsValueWithColumnDefined(Widget widget) {
		IStatus status = Status.OK_STATUS;
		ITableColumnItem item = PageConstraints.getTableColumnItem(widget);
		if (item != null) {
			ITableColumn column = item.getTableColumn();
			if (column.isDynamic()) {
				String dataref = item.getColumn();
				if (StringUtils.isEmpty(dataref)) {
					return getErrorStatus("Dynamic columns must contain value with a Column specified.");
				} else {
					boolean found = false;
					for (ITableColumn tableColumn: column.getTable().getColumns()) {
						// Ignore the column itself
						if (tableColumn == column) {
							continue;
						}
						
						// If the column referenced a computed column or a column bound to the domain, 
						// there is no problem
						if (tableColumn.getColumnName().equals(dataref)) {
							if (tableColumn.isComputed() || tableColumn.isBoundToDomain()) {
								found = true;
								break;
							}
						}
					}
					if (!found) {
						return getErrorStatus("Dynamic column must refer to a column (attribute or computed one column) dropped in the table.");
					}
				}
				
			}
		}
		return status;
	}

	 IStatus checkDynamicColumnSortOrderIsSet(Widget widget) {
		IStatus status = Status.OK_STATUS;
		ITableColumn column = PageConstraints.getTableColumn(widget);
		if (column != null && column.isDynamic()) {
			List<ITableGroup> groups = column.getTable().getGroupsByRank();
			for (ITableGroup group : groups) {
				if(group.isUsedForDynamicColumn() && (group.getSortingColumnName() == null || group.getSortingColumnName().equals(""))) {
					 return getErrorStatus("Sorting required on the group used for dynamic columns.");
				}
			}
		}
		return status;
	}
    /**
     * check if the included xtooltip fragment is bounded to the same dataset   
     * @return
     */
	 IStatus checkIncludeFragmentDataSet(Widget widget){
		 IStatus status = Status.OK_STATUS;
		 Widget rootWidgte=widget.getRootWidget();
		 Property domainEntity=rootWidgte.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
		 Property includeProperty=widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT);
		 if(domainEntity!=null&&includeProperty!=null){
			String rootDataSetName=domainEntity.getValue();
			Model model=includeProperty.getModel();
			if(model!=null){
				Property  modelDomainEntity=model.getWidget().findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
			   if(modelDomainEntity!=null){
				   if(!rootDataSetName.equals(modelDomainEntity.getValue())){
					   return getErrorStatus("Dataset bound to the xtooltip fragment and "+rootWidgte.getTypeName()+" dataset must be same");
				   }
			   }
			}
		 } 
	  
	  return  status;
		 
	 }
	 
	 /**
	  * DS-5389
      * check if each attribute of the included xtooltip fragment also exists in the table   
      * @return
      */
	 public IStatus checkEachAttributeOfIncludedXtooltipFragment(Widget widget){
			 IStatus status = Status.OK_STATUS;
			 Widget rootWidget=widget.getRootWidget();
			 Property domainEntity=rootWidget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
			 Property includeProperty=widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT);
			 if(domainEntity!=null&&includeProperty!=null){
				Widget tableWidget=getTableTreeWidget(widget);
				if (tableWidget!=null && includeProperty.getModel()!=null ) {
					Model model = includeProperty.getModel();
					boolean isAttrMatched = compareWidgets(model.getWidget(), tableWidget);
					if (!isAttrMatched) {
						return getErrorStatus("The attribute(s) "+ errortableWidgets.toString() +" found in fragment "+ trimmer(model.eResource().getURI().path()) +" should be configured in Table");
					}
				}
			 }
		  return  status;
	}
	 
	/**
	 * @param toBeTrimmed
	 * @return
	 */
	private String trimmer(String toBeTrimmed){
		String[] split = toBeTrimmed.split("/");
		int last = split.length;
		String filename = last>0 ? split[last-1].substring(0, split[last-1].lastIndexOf(".")) : toBeTrimmed;
		return filename;
	} 
	 
	/**
	 * Collect all Xtooltip attributes domain entity value
	 * @param xtooltipWidget
	 * @param attrValues
	 */
	private void collectXtooltipAttrWidgetValue(Widget xtooltipWidget, List<String> attrValues) {
		for(Widget wid: xtooltipWidget.getContents()){
			Property domainEntity=wid.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
			if(domainEntity!=null && !domainEntity.getValue().isEmpty() && !attrValues.contains(domainEntity.getValue())){
				attrValues.add(domainEntity.getValue());	
			}
			else{
				if(!wid.getContents().isEmpty()){
					collectXtooltipAttrWidgetValue(wid,attrValues);
				}
			}
		}
	}
	
	/**
	 * Collect all TableWidget child widgets
	 * @param rootTableWidget
	 * @param tableChildWidgets
	 */
	private void collectTableWidgetChildrens(Widget rootTableWidget, List<Widget> tableChildWidgets) {
		for(Widget wid: rootTableWidget.getContents()){
				tableChildWidgets.add(wid);	
				if(!wid.getContents().isEmpty()){
					collectTableWidgetChildrens(wid,tableChildWidgets);
				}
		}
	}

	/**
	 * Compare table widget value has xtooltip attribute values
	 * @param xtooltipWidget
	 * @param tableWidget
	 * @return
	 */
	boolean compareWidgets(Widget xtooltipWidget, Widget tableWidget){
		// construct a list to populate all xtooltip widgets from table widget
		// collect the xtooltip attribute values
		List<String> attrValues = new ArrayList<String>();
		collectXtooltipAttrWidgetValue(xtooltipWidget,attrValues);

		// collect the widgets within the table 
		List<Widget> tableWidgets = new ArrayList<Widget>();
		collectTableWidgetChildrens(tableWidget,tableWidgets);
		
		List<String> validatedVals = new ArrayList<String>();
		
		for (Widget tableWidAttr : tableWidgets){
			if(tableWidAttr.getType().getName().equals(WidgetTypeConstants.TABLE_COLUMN) || tableWidAttr.getType().getName().equals("TableExtra")){
				if(tableWidAttr.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE)!=null){
					Property dom = tableWidAttr.findProperty(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
					if(attrValues.contains(dom.getValue()) && !validatedVals.contains(dom.getValue())){
						validatedVals.add(dom.getValue());
					}
				}
				if(tableWidAttr.findProperty(PropertyTypeConstants.COLUMN_NAME)!=null){
					Property colName = tableWidAttr.findProperty(PropertyTypeConstants.COLUMN_NAME);
					if(attrValues.contains(colName.getValue()) && !validatedVals.contains(colName.getValue())){
						validatedVals.add(colName.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals(WidgetTypeConstants.TABLE_COLUMN_ITEM)){
				if(tableWidAttr.findProperty("item-column")!=null){
					Property colName = tableWidAttr.findProperty("item-column");
					if(attrValues.contains(colName.getValue()) && !validatedVals.contains(colName.getValue())){
						validatedVals.add(colName.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals(WidgetTypeConstants.CHECKBOX)){
				if(tableWidAttr.findProperty("column-checkbox-identifier")!=null){
					Property colName = tableWidAttr.findProperty("column-checkbox-identifier");
					if(attrValues.contains(colName.getValue()) && !validatedVals.contains(colName.getValue())){
						validatedVals.add(colName.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals("TableGroup")){
				if(tableWidAttr.findProperty("group-column-name")!=null){
					Property dom = tableWidAttr.findProperty("group-column-name");
					if(attrValues.contains(dom.getValue()) && !validatedVals.contains(dom.getValue())){
						validatedVals.add(dom.getValue());
					}
				}
				if(tableWidAttr.findProperty("group-sorting-column-name")!=null){
					Property colName = tableWidAttr.findProperty("group-sorting-column-name");
					if(attrValues.contains(colName.getValue()) && !validatedVals.contains(colName.getValue())){
						validatedVals.add(colName.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals("TableSort")){
				if(tableWidAttr.findProperty("sort-column-name")!=null){
					Property dom = tableWidAttr.findProperty("sort-column-name");
					if(attrValues.contains(dom.getValue()) && !validatedVals.contains(dom.getValue())){
						validatedVals.add(dom.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals("TableAggregate")){
				if(tableWidAttr.findProperty("aggregate-column-name")!=null){
					Property dom = tableWidAttr.findProperty("aggregate-column-name");
					if(attrValues.contains(dom.getValue()) && !validatedVals.contains(dom.getValue())){
						validatedVals.add(dom.getValue());
					}
				}
			}
			else if(tableWidAttr.getType().getName().equals("TableKeepFilter")){
				if(tableWidAttr.findProperty("keep-filter-column-name")!=null){
					Property dom = tableWidAttr.findProperty("keep-filter-column-name");
					if(attrValues.contains(dom.getValue()) && !validatedVals.contains(dom.getValue())){
						validatedVals.add(dom.getValue());
					}
				}
			}
		}
		if(validatedVals.size()==attrValues.size()){
			reset(attrValues,tableWidgets,validatedVals);
			return true;
		}
		errortableWidgets.clear();
		attrValues.removeAll(validatedVals);
		errortableWidgets.addAll(attrValues);
		reset(attrValues,tableWidgets,validatedVals);
		return false;
	}
	
	static List<String> errortableWidgets = new ArrayList<String>();
	
	/**
	 * clears the list
	 * @param attrValues
	 * @param tableWidgets
	 * @param validatedVals
	 */
	void reset(List<String> attrValues,List<Widget> tableWidgets,List<String> validatedVals){
		attrValues.clear();	
		tableWidgets.clear();
		validatedVals.clear();
	}
	
	/**
	 * return TableTree widget from any child element 
	 * @param childWidget
	 * @return
	 */
	Widget getTableTreeWidget(Widget childWidget){
		if (childWidget!=null) {
			if(childWidget.getType().getName().equalsIgnoreCase(WidgetTypeConstants.TABLE_TREE))
			{
				return childWidget;	
			}else {
				return getTableTreeWidget(childWidget.getParent());
			}
		}
		return null;	
	}
	
	 
	 
	/**
	 * @param listener
	 */
	public PageWidgetValidator(PageValidationListener listener) {
		this.listener = listener;
	}

	
	public IStatus checkDynamicGroupHasXtooltip(Widget widget){
	    IStatus status = Status.OK_STATUS;
	    String typeName = widget.getTypeName();
	    if (typeName.equals("TableGroup")) {	
		if(PageConstraints.isDynamicGroup(widget)){
		    Property xtipPro=widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT);
		    if(xtipPro !=null && xtipPro.getModel()!=null){
			return getErrorStatus("Xtooltip can not be set on Dynamic Group");
		    }
		}
	    }
	    return  status;
	}
	
	/**
	 * DS-4871 check if each groupname configured in checkbox exist in Table/Tree 
	 * 
	 * @return
	 */
	public IStatus checkValidTableGroupForCheckBox(Widget widget) {
		IStatus status = Status.OK_STATUS;
		String typeName = widget.getTypeName();
		if (typeName.equals(WidgetTypeConstants.CHECKBOX)) {
			Property domainEntity = widget.findProperty("checkbox-group-names");
			Widget tableWidget = widget.findAncestor(WidgetTypeConstants.TABLE_TREE);
			if (tableWidget != null && domainEntity != null && !StringUtils.isEmpty(domainEntity.getValue())
					&& !PageConstraints.checkGroupNameInTable(tableWidget,domainEntity.getValue())) {
				return getErrorStatus("Apply on group property refers to unknown group name.");
			}
		}
		return status;
	}

}
