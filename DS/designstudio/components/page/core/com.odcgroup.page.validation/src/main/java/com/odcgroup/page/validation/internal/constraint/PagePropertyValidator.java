package com.odcgroup.page.validation.internal.constraint;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.page.validation.internal.PageConstraints;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class verifies constraints of some properties
 * 
 * TODO : replace all this ugly hard coded rules, by scripts, and put them
 *        in the knowledge level (metamodel)
 *        
 * @author atr
 */
public class PagePropertyValidator {

	public static final String MESSAGE_PROPERTY_ID = "The property Id must be set";

	private PageValidationListener listener;

	private static class PathValidator extends PathVisitor {

		private final MdfDataset root;
		private MdfEntity type;
		private IStatus status = ConstraintStatus.OK_STATUS;
		private MdfProperty prop = null;

		private PathValidator(MdfDataset root) {
			this.root = root;
			this.type = root.getBaseClass();
		}

		protected IStatus newStatus(String message, int severity) {
			return new Status(severity, Activator.PLUGIN_ID, -1, message, null);
		}

		public boolean visit(String name) {
			if (type instanceof MdfClass) {
				prop = ((MdfClass) type).getProperty(name);

				if (prop == null) {
					prop = ModelHelper.getReverseAssociation(root.getParentDomain(), (MdfClass) type, name);
				}

				if (prop == null) {
					status = newStatus("Property '" + name + "' does not exist for class "
							+ type.getQualifiedName().toString(), IStatus.ERROR);
					return false;
				} else {
					type = prop.getType();
					return true;
				}
			} else {
				status = newStatus("Property '" + name + "' does not exist for type "
						+ type.getQualifiedName().toString(), IStatus.ERROR);
				return false;
			}
		}

		public IStatus getStatus() {
			return status;
		}
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDatasetInEditableTableTree(Property property) {

		IStatus status = Status.OK_STATUS;

		if (PropertyTypeConstants.TABLE_EDITABLE_DATASET.equals(property.getTypeName())) {

			String datasetName = property.getValue();
			if (StringUtils.isNotEmpty(datasetName)) {

				Model model = property.getWidget().getModel();
				if(model.eIsProxy()) model = (Model) EcoreUtil.resolve(model, property);
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
				if (ofsProject != null) {
					DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
					MdfModelElement dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
					if (dataset == null) {
						String message = "The dataset {0} is not found."; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}
				} else {
					String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { datasetName }), null);
				}

			}

		}

		return status;

	}
	
	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDomainEntity(Property property) {

		IStatus status = Status.OK_STATUS;

		if (PropertyTypeConstants.DOMAIN_ENTITY.equals(property.getTypeName())) {

			String datasetName = property.getValue();
			if (StringUtils.isNotEmpty(datasetName)) {

				Model model = property.getWidget().getModel();
				if(model.eIsProxy()) model = (Model) EcoreUtil.resolve(model, property);
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
				if (ofsProject != null) {
					DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
					MdfModelElement dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
					if (dataset == null) {
						String message = "The dataset {0} is not found."; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}
				} else {
					String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { datasetName }), null);
				}

			}

		}

		return status;

	}
	
	/**
	 * @param property
	 * @param mandatory true if the value is required.
	 * @return status
	 */
	private IStatus checkDatasetAttributeInEditableTableTree(Widget editTable, Property property, boolean mandatory) {

		IStatus status = Status.OK_STATUS;

		String attributeName = property.getValue();

		if (StringUtils.isNotEmpty(attributeName)) {
			
			// Validate the Attribute or Property. It is associated with child Widgets.
			Property prop = editTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
			if (StringUtils.isNotEmpty(datasetName)) {

				Model model = editTable.getModel();
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
				if (ofsProject != null) {
					DomainRepository repository = DomainRepository.getInstance(ofsProject);

					MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
					if (dataset != null) {
						MdfDatasetProperty dsProperty = null;
						int pos = attributeName.indexOf(".");
						if (pos != -1) {
							// the attribute is defined in a linked dataset (association)
							String assoc = attributeName.substring(0,pos);
							// attribute in the linked dataset
							attributeName = attributeName.substring(pos+1);
			    			if (StringUtils.isNotBlank(attributeName)) {
			    				// extract association.
			    				// load the editable dataset.
		    		    		MdfDatasetProperty dp = dataset.getProperty(assoc);
		    		    		if (dp == null) {
									String message = "The linked dataset {0} is not found"; //$NON-NLS-1$
									status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
											new Object[] { assoc }), null);
									return status;
		    		    		}
		    		    		MdfDataset linkedDataset = null;
		    		    		if (dp instanceof MdfDatasetMappedProperty) {
		    		    			MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty) dp;
		    		    			if (dmp.isTypeDataset()) {
		    		    				linkedDataset = ((MdfDatasetMappedPropertyImpl) dmp).getLinkDataset();
		    		    			}
		    		    		}
		    		    		if (linkedDataset == null) {
									String message = "The linked dataset {0} cannot be loaded"; //$NON-NLS-1$
									status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
											new Object[] { assoc }), null);
									return status;
		    		    		}
		    		    		// load the property in the linked dataset
		    		    		datasetName = linkedDataset.getQualifiedName().toString();
		    		    		dsProperty = linkedDataset.getProperty(attributeName);
		    		    		
			    			}
						} else {
							// computed or regular attribute.
							dsProperty = dataset.getProperty(attributeName);
						}

						// check existence
						if (dsProperty == null) {
							String message = "The dataset {0} has no attribute with the name {1}"; //$NON-NLS-1$
							status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
									new Object[] { datasetName, attributeName }), null);

						} else if (dsProperty instanceof MdfDatasetMappedProperty) {
							MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty) dsProperty;
							String path = mappedProperty.getPath();
							PathValidator validator = new PathValidator(mappedProperty.getParentDataset());
							PathVisitor.visitPath(path, validator);
							status = validator.getStatus();
						}

					} else {
						String message = "The dataset {0} is not found"; //$NON-NLS-1$;
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}
				} else {
					String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { datasetName }), null);
				}

			} else {
				String message = "The attribute {0} has no corresponding Dataset"; //$NON-NLS-1$;
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
						new Object[] { attributeName }), null);
			}

		} else if (mandatory) {
			String message = "The dataset attribute {0} is not defined"; //$NON-NLS-1$;
			//DS-3894 - changed status to warning
			status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
					new Object[] { attributeName }), null);
		}
		
		return status;

	}
	
	/**
	 * Check that the Fomat Identifier Attribute is defined only if the Editable Dataset is defined 
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkFormatRowIdentifierInEditableTableTree(Property property) {

		IStatus status = Status.OK_STATUS;
		
		if (PropertyTypeConstants.TABLE_EDITABLE_FORMAT_IDENTIFIER.equals(property.getTypeName())) {			
			
			Widget editTable = property.getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
			if (editTable == null) {
				// wrong context;
				return status;
			}
			
			Property editDataset = editTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			if (editDataset == null) {
				// no property for defining an editable dataset
				return status;
			}
		
			String editDatasetName = editDataset.getValue();
			if (StringUtils.isBlank(editDatasetName)) {
				// the editable dataset is not defined.
				return status;
			}

			String attributeName = property.getValue();

			if (StringUtils.isNotEmpty(attributeName)) {

				// Validate the Attribute or Property. It is associated with
				// child Widgets.
				Widget rootWidget = property.getWidget().getRootWidget();
				Property prop = rootWidget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
				String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
				if (StringUtils.isNotEmpty(datasetName)) {

					Model model = rootWidget.getModel();
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
					if (ofsProject != null) {
						DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
						MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
						if (dataset != null) {
	
							MdfDatasetProperty dsProperty = dataset.getProperty(attributeName);
	
							// check existence
							if (dsProperty == null) {
								String message = "The dataset {0} has no attribute with the name {1}"; //$NON-NLS-1$
								status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
										new Object[] { datasetName, attributeName }), null);
	
								// check the mapped property exists on the base
								// class.
							} else if (dsProperty instanceof MdfDatasetMappedProperty) {
								MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty) dsProperty;
								String path = mappedProperty.getPath();
								PathValidator validator = new PathValidator(mappedProperty.getParentDataset());
								PathVisitor.visitPath(path, validator);
								status = validator.getStatus();
							}
	
						} else {
							String message = "The dataset {0} is not found"; //$NON-NLS-1$;
							status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
									new Object[] { datasetName }), null);
						}
					} else {
						String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}

				} else {
					String message = "The Format Identifier attribute {0} has no corresponding Dataset"; //$NON-NLS-1$;
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { attributeName }), null);
				}

			} else {
				String message = "The Format Identifier Attribute {0} is not defined"; //$NON-NLS-1$;
				//DS-3894 - changed status to warning
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
						new Object[] { attributeName }), null);
			}

		}

		return status;
	}
	
	/**
	 * Check that the Dataset Row Identifier defined in a Editable Table/Tree exist 
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkFormatPermissionAttributeInEditableTableTree(Property property) {
		
		IStatus status = Status.OK_STATUS;
		
		if (PropertyTypeConstants.TABLE_FORMAT_ROW_PERMISSION.equals(property.getTypeName())) {			
			
			Widget editTable = property.getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
			if (editTable == null) {
				// wrong context;
				return status;
			}
			
			Property editDataset = editTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			if (editDataset == null) {
				// no property for defining an editable dataset
				return status;
			}
		
			String editDatasetName = editDataset.getValue(); 
			if (StringUtils.isBlank(editDatasetName)) {
				// the editable dataset is not defined.
				return status;
			}
			
			String attributeName = property.getValue();

			if (StringUtils.isNotBlank(attributeName)) {

				// Validate the Attribute or Property. It is associated with
				// child Widgets.
				Widget rootWidget = property.getWidget().getRootWidget();
				Property prop = rootWidget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
				String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
				if (StringUtils.isNotEmpty(datasetName)) {

					Model model = rootWidget.getModel();
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
					if (ofsProject != null) {
						DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
						MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
						if (dataset != null) {
	
							MdfDatasetProperty dsProperty = dataset.getProperty(attributeName);
	
							// check existence
							if (dsProperty == null) {
								String message = "The format {0} has no attribute with the name {1}"; //$NON-NLS-1$
								status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
										new Object[] { datasetName, attributeName }), null);
								// check the mapped property exists on the base
								// class.
							} else if (dsProperty instanceof MdfDatasetMappedProperty) {
								MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty) dsProperty;
								String path = mappedProperty.getPath();
								PathValidator validator = new PathValidator(mappedProperty.getParentDataset());
								PathVisitor.visitPath(path, validator);
								status = validator.getStatus();
							}
	
						} else {
							String message = "The format {0} is not found"; //$NON-NLS-1$;
							status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
									new Object[] { datasetName }), null);
						}
					} else {
						String message = "The format {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}

				} else {
					String message = "The Format Permission attribute {0} has no corresponding Dataset"; //$NON-NLS-1$;
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { attributeName }), null);
				}

			}

		}

		return status;
	}
	
	
	/**
	 * Check that the Dataset Row Identifier defined in a Editable Table/Tree exist 
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDatasetRowIdentifierInEditableTableTree(Property property) {
		
		IStatus status = Status.OK_STATUS;
		
		if (PropertyTypeConstants.TABLE_EDITABLE_ROW_IDENTIFIER.equals(property.getTypeName())) {
			
			Widget editTable = property.getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
			if (editTable == null) {
				// wrong context;
				return status;
			}
			
			Property editDataset = editTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			if (editDataset == null) {
				// no property for defining an editable dataset
				return status;
			}
		
			if (StringUtils.isBlank(editDataset.getValue())) {
				// the editable dataset is not defined.
				return status;
			}

			status = checkDatasetAttributeInEditableTableTree(editTable, property, true);
		}
		return status;
	}
	
	/**
	 * Check that the dataset attribute defined for a widget TableColumn Editable item exists 
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDatasetAttributeInEditableTableColumnItem(Property property) {
		
		IStatus status = Status.OK_STATUS;
		
		if (PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE.equals(property.getTypeName())) {
			
			Widget editTable = property.getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
			if (editTable == null) {
				// wrong context;
				return status;
			}
			
			Property editDataset = editTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			if (editDataset == null) {
				// no property for defining an editable dataset
				return status;
			}
		
			if (StringUtils.isBlank(editDataset.getValue())) {
				// the editable dataset is not defined.
				return status;
			}
			status = checkDatasetAttributeInEditableTableTree(editTable, property, true);
		}
		
		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDomainAttribute(Property property) {

		IStatus status = Status.OK_STATUS;
		if (PropertyTypeConstants.DOMAIN_ATTRIBUTE.equals(property.getTypeName())) {

			Widget widget = property.getWidget();

			if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELLITEM)) {
				IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
				if (item.isComputed()) {
					return status;
				}
			} else if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CELLITEM)) {
				IMatrixCellItem item = MatrixHelper.getMatrixCellItem(widget);
				if (item.isComputed()) {
					return status;
				}
			} else if (widget.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
				// will be check in dedicated rules.
				return status;
			} else if (widget.getTypeName().equals(WidgetTypeConstants.CHECKBOX)
					&& PageConstraints.isParentTableColumn(widget)) {
				Widget tcwid = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
				if (tcwid != null) {
					ITableColumn column = TableHelper.getTableColumn(tcwid);
					if (column.isPlaceHolder()) {
						return status;
					}
				}
			} else if (widget.getTypeName().equals(WidgetTypeConstants.RADIO_BUTTON)) {
				return status;
			}

			String attributeName = property.getValue();

			if (StringUtils.isNotEmpty(attributeName)) {

				// Validate the Attribute or Property. It is associated with
				// child Widgets.
				Widget rootWidget = property.getWidget().getRootWidget();
				Property prop = rootWidget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
				String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
				if (StringUtils.isNotEmpty(datasetName)) {

					Model model = rootWidget.getModel();
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
					if (ofsProject != null) {
						DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
						MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
						if (dataset != null) {
	
							MdfDatasetProperty dsProperty = dataset.getProperty(attributeName);
	
							// check existence
							if (dsProperty == null) {
								String message = "The dataset {0} has no attribute with the name {1}"; //$NON-NLS-1$
								status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
										new Object[] { datasetName, attributeName }), null);
								// check the mapped property exists on the base
								// class.
							} else if (dsProperty instanceof MdfDatasetMappedProperty) {
								MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty) dsProperty;
								String path = mappedProperty.getPath();
								PathValidator validator = new PathValidator(mappedProperty.getParentDataset());
								PathVisitor.visitPath(path, validator);
								status = validator.getStatus();
							}
	
						} else {
							String message = "The dataset {0} is not found"; //$NON-NLS-1$;
							status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
									new Object[] { datasetName }), null);
						}
					} else {
						String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}

				} else {
					String message = "The attribute {0} has no corresponding Dataset"; //$NON-NLS-1$;
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { attributeName }), null);
				}

			} else {
				String message = "The domain attribute {0} is not defined"; //$NON-NLS-1$;
				//DS-3894 - changed status to warning
				status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
						new Object[] { attributeName }), null);
			}

		}

		return status;

	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkDomainAssociation(Property property) {

		IStatus status = Status.OK_STATUS;

		if (PropertyTypeConstants.DOMAIN_ASSOCIATION.equals(property.getTypeName())) {

			String association = property.getValue();
			if (StringUtils.isNotEmpty(association)) {

				// Validate the Attribute or Property. It is associated with
				// child Widgets.
				Widget rootWidget = property.getWidget().getRootWidget();
				Property prop = rootWidget.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
				String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$

				if (StringUtils.isNotEmpty(datasetName)) {

					Model model = rootWidget.getModel();
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(model.eResource());
					if (ofsProject != null) {
						DomainRepository repository = DomainRepository.getInstance(ofsProject);
	
						MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
						if (dataset != null) {
							if (dataset.getProperty(association) == null) {
								String message = "The dataset {0} has no association with the name {1}"; //$NON-NLS-1$
								status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
										new Object[] { datasetName, association }), null);
							}
						} else {
							String message = "The dataset {0} is not found"; //$NON-NLS-1$
							status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
									new Object[] { datasetName }), null);
						}
					} else {
						String message = "The dataset {0} is not found, due to an internal error. Try to relaunch the validation"; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
								new Object[] { datasetName }), null);
					}

				} else {
					String message = "The association {0} has no correspoding Dataset"; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { association }), null);
				}
			}
		}

		return status;
	}

	/**
	 * @param widget
	 * @return IStatus
	 */
	private IStatus checkWidth(Property property) {

		IStatus status = Status.OK_STATUS;

		String name = property.getTypeName();
		
		if (PropertyTypeConstants.WIDTH.equals(name) || PropertyTypeConstants.COLUMN_WIDTH.equals(name)) {

			String value = property.getValue();
			if (value != null) {
				value = value.trim();
				if (!value.matches("[0-9]*[%]?")) { //$NON-NLS-1$;
					String message = "The value {0} of the property Width is invalid "; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							new Object[] { value }), null);
				}
			}

		}

		return status;
	}
	
	/**
	 * @return true if the HTML ID validation can be ignored for the following widget.
	 * @param widget the widget to be checked
	 */
	private boolean ignoreHTMLIdValidation(String typeName) {
		return WidgetTypeConstants.BOX.equals(typeName) 
		    || WidgetTypeConstants.LABEL.equals(typeName) 
		    || WidgetTypeConstants.LABEL_DOMAIN.equals(typeName);
	}

	private IStatus checkHTMLId(Property property) {IStatus status = Status.OK_STATUS;
		if (property == null) {
			return status;
		}
		String name = property.getTypeName();
		if (PropertyTypeConstants.ID.equals(name)) {
			Widget widget = property.getWidget();
			if (widget != null) {
			String typeName = property.getWidget().getTypeName();
			if (! ignoreHTMLIdValidation(typeName)) {
				if(property.getWidget()!=null && UniqueIdGenerator.generateIdForWidgets.contains(typeName)) {
					String value = property.getValue();
					if (StringUtils.isBlank(value)) {
						String message = MESSAGE_PROPERTY_ID; //$NON-NLS-1$
						status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
					}
				}
			}
		}
		}
		return status;
	}

	// DS-4384
	private IStatus checkSelectedTab(Property property) {
		IStatus status = Status.OK_STATUS;
		String name = property.getTypeName();
		if (PropertyTypeConstants.SELECTED_TAB.equals(name)) {
			String tabName = property.getValue();
			boolean tabFound = false;
			Snippet queryTabDisplaySnippet = (Snippet) property.eContainer();
			Snippet querySnippet = (Snippet) queryTabDisplaySnippet.eContainer();
			Property outputModule = querySnippet.findProperty(PropertyTypeConstants.QUERY_OUTPUTMODULE);
			if(outputModule!=null) {
				Model searchModule = outputModule.getModel();
				if(searchModule!=null) {
					TreeIterator<EObject> contents = searchModule.eAllContents();
					while(contents.hasNext()) {
						EObject eObj = contents.next();
						if (eObj instanceof Widget) {
							Widget w = (Widget) eObj;
							if(WidgetTypeConstants.TAB.equals(w.getTypeName())) {
								String value = w.getPropertyValue(PropertyTypeConstants.ID);
								if(tabName.equals(value)) {
									tabFound = true;
									break;
								}
							}
						}
					}
				}
				if(!tabFound) {
					String message = "The tab {0} cannot be found in the search module {1}."; //$NON-NLS-1$
					status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
							tabName, outputModule.getModelURI().path()), null);
				}
			} else {
				String message = "Property {0} is missing on the parent snippet."; //$NON-NLS-1$
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageFormat.format(message,
						PropertyTypeConstants.QUERY_OUTPUTMODULE), null);
			}
		}
		return status;
		
	}
	
	private IStatus checkCondition(Property property) { 
		IStatus status = Status.OK_STATUS;
		String name = property.getTypeName();
		if (PropertyTypeConstants.CONDITION_LANG.equals(name)) {
/*			
			try {
				// DS-7191 ConditionUtils.getConditionAsJavaCode(property.getWidget(), false);
			} catch (CoreException ex) {
				String msg = ex.getMessage();
				Throwable e = null;
				if (ex.getStatus() != null) {
					if (ex.getStatus().getException() != null) {
						e = ex.getStatus().getException().getCause();
						if (StringUtils.isNotBlank(ex.getStatus().getException().getMessage())) {
							msg = ex.getStatus().getException().getMessage();
						}
					}
				}
				status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, msg, e);
			}
*/			
		}
		return status;
	}
	
	/**
	 * @param listener
	 */
	public PagePropertyValidator(PageValidationListener listener) {
		this.listener = listener;
	}

	/**
	 * @param model
	 *            the model to be validated
	 */
	public void accept(Property property) {
		listener.onValidation(checkDomainEntity(property));
		listener.onValidation(checkDomainAttribute(property));
		listener.onValidation(checkDomainAssociation(property));
		listener.onValidation(checkDatasetInEditableTableTree(property));
		listener.onValidation(checkDatasetRowIdentifierInEditableTableTree(property));
		listener.onValidation(checkFormatPermissionAttributeInEditableTableTree(property));
		listener.onValidation(checkFormatRowIdentifierInEditableTableTree(property));
		listener.onValidation(checkDatasetAttributeInEditableTableColumnItem(property));
		listener.onValidation(checkWidth(property));
		listener.onValidation(checkHTMLId(property));
		listener.onValidation(checkSelectedTab(property));
		listener.onValidation(checkCondition(property));
	}

}
