package com.odcgroup.page.transformmodel.ui.builder;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.COLUMN_NAME;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ATTRIBUTE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FORMAT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.IS_WIDGET_DELETABLE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.PREVIEW_VALUE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.TYPE;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.CDM_COLUMN;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.CDM_COLUMN_HEADER;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_BODY;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.COLUMN_HEADER;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.ITEM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.widgets.matrix.IMatrixCellItem;
import com.odcgroup.page.util.ClassUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * This is the base-class for WidgetBuilders.
 * 
 * @author atr
 */
abstract public class AbstractWidgetBuilder implements WidgetBuilder {

	/**
	 * The builders for column Widgets exists for the specified Widget type then this type of Column should be created
	 * instead of creating the normal labels and textfields.
	 */
	private static final Map<String, Class<?>> COLUMN_BUILDERS;
	static {
		COLUMN_BUILDERS = new HashMap<String, Class<?>>();
		COLUMN_BUILDERS.put(WidgetTypeConstants.TABLE, ColumnBuilder.class);
		COLUMN_BUILDERS.put(WidgetTypeConstants.TABLE_TREE, TableTreeColumnBuilder.class);
		COLUMN_BUILDERS.put(WidgetTypeConstants.CDM_TABLE, CdmColumnBuilder.class);
	}
	
	private static final Map<String, Class<?>> ITEM_BUILDERS;
	static {
		ITEM_BUILDERS = new HashMap<String, Class<?>>();
		ITEM_BUILDERS.put(WidgetTypeConstants.MATRIX_CONTENTCELL, MatrixContentCellItemBuilder.class);
		ITEM_BUILDERS.put(WidgetTypeConstants.MATRIX_AXIS, MatrixContentCellItemBuilder.class);
		ITEM_BUILDERS.put(WidgetTypeConstants.BOX, MatrixContentCellItemBuilder.class);
		ITEM_BUILDERS.put(WidgetTypeConstants.CONDITIONAL_BODY, MatrixContentCellItemBuilder.class);
		ITEM_BUILDERS.put(WidgetTypeConstants.MATRIX_EXTRACOLUMN, MatrixExtraColumnItemBuilder.class);
	}

	/** The binding between the Mdf Attribute Type and the UDP / XGui Type. */
	private static final Map<String, String> MDF_TYPE_BINDINGS;
	static {
		MDF_TYPE_BINDINGS = new HashMap<String, String>();
		MDF_TYPE_BINDINGS.put("mml:boolean", "boolean");
		MDF_TYPE_BINDINGS.put("mml:byte", "");
		MDF_TYPE_BINDINGS.put("mml:char", "");
		MDF_TYPE_BINDINGS.put("mml:date", "date");
		MDF_TYPE_BINDINGS.put("mml:datetime", "datetime");
		MDF_TYPE_BINDINGS.put("mml:decimal", "decimal");
		MDF_TYPE_BINDINGS.put("mml:double", "decimal");
		MDF_TYPE_BINDINGS.put("mml:float", "decimal");
		MDF_TYPE_BINDINGS.put("mml:integer", "integer");
		MDF_TYPE_BINDINGS.put("mml:long", "integer");
		MDF_TYPE_BINDINGS.put("mml:short", "integer");
		MDF_TYPE_BINDINGS.put("mml:media", "");
		MDF_TYPE_BINDINGS.put("mml:percentage", "percent");
		MDF_TYPE_BINDINGS.put("mml:string", "text");
		MDF_TYPE_BINDINGS.put("mml:text", "text");
		MDF_TYPE_BINDINGS.put("mml:time", "datetime");
		MDF_TYPE_BINDINGS.put("mml:uri", "");
	}

	/** The Model Element to build the Widget(s) for. */
	private MdfModelElement modelElement;

	/** The parent Widget to which the child Widgets will be added. */
	private Widget parentWidget;

	/** The WidgetBuilderContext. */
	private WidgetBuilderContext builderContext;

	/**
	 * Creates a new AbstractWidgetBuilder.
	 * 
	 * @param modelElement
	 *            The MdfModelElement to create
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param path
	 *            The MdfPath
	 * @param builderContext
	 *            The context
	 */
	public AbstractWidgetBuilder(MdfModelElement modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		Assert.isNotNull(parentWidget);
		Assert.isNotNull(builderContext);

		this.modelElement = modelElement;
		this.parentWidget = parentWidget;
		this.builderContext = builderContext;
	}

	/**
	 * Creates the Properties. These will be used to update an existing Widget.
	 * 
	 * @return List An empty List
	 */
	public List<Property> buildProperties() {
		return new ArrayList<Property>();
	}

	/**
	 * Gets the Model Element to build the Widget(s) for.
	 * 
	 * @return MdfModelElement
	 */
	protected final MdfModelElement getModelElement() {
		return modelElement;
	}

	/**
	 * Gets the parent Widget.
	 * 
	 * @return Widget
	 */
	protected final Widget getParentWidget() {
		return parentWidget;
	}

	/**
	 * Gets the Corporate Design.
	 * 
	 * @return CorporateDesign The corporate design attached to this builder
	 */
	protected final CorporateDesign getCorporateDesign() {
		return getBuilderContext().getCorporateDesign();
	}

	/**
	 * Gets the Factory used to build Widgets.
	 * 
	 * @return WidgetBuilderFactory
	 */
	protected final WidgetBuilderFactory getBuilderFactory() {
		return getBuilderContext().getBuilderFactory();
	}

	/**
	 * Gets the OFS project for which we are building Widgets.
	 * 
	 * @return IOfsProject the OFS project
	 */
	protected final IOfsProject getOfsProject() {
		return getBuilderContext().getOfsProject();
	}

	/**
	 * Creates a Widget of the specified library and type.
	 * 
	 * @param library
	 *            The library containing the WidgetType
	 * @param type
	 *            The type of Widget to create
	 * @return Widget The newly created Widget
	 */
	protected Widget createWidget(String library, String type) {
		WidgetType wt = getBuilderContext().getMetaModel().findWidgetLibrary(library).findWidgetType(type);
		return getBuilderContext().getWidgetFactory().create(wt);
	}

	/**
	 * Creates a Widget of the specified type which is part of the XGui WidgetLibrary.
	 * 
	 * @param type
	 *            The type of Widget to create
	 * @return Widget The newly created Widget
	 */
	protected Widget createWidget(String type) {
		WidgetType wt = findWidgetType(type);
		return getBuilderContext().getWidgetFactory().create(wt);
	}

	/**
	 * Creates a Property of the specified type which is part of the XGui WidgetLibrary.
	 * 
	 * @param type
	 *            The type of Property to create
	 * @return Property The newly created Property
	 */
	protected Property createProperty(String type) {
		Property p = ModelFactory.eINSTANCE.createProperty();
		p.setTypeName(type);
		p.setLibraryName(WidgetLibraryConstants.XGUI);
		return p;
	}

	/**
	 * Finds the WidgetType for a Widget which is part of the XGui WidgetLibrary.
	 * 
	 * @param type
	 *            The name of the WidgetType
	 * @return WidgetType The WidgetType or null if not WidgetType could be found
	 */
	private WidgetType findWidgetType(String type) {
		return getBuilderContext().getMetaModel().findWidgetLibrary(WidgetLibraryConstants.XGUI).findWidgetType(type);
	}

	/**
	 * Gets the WidgetBuilderContext.
	 * 
	 * @return WidgetBuilderContext The WidgetBuilderContext
	 */
	protected WidgetBuilderContext getBuilderContext() {
		return builderContext;
	}

	/**
	 * Gets the MdfRegistry for this project.
	 * 
	 * @return MdfRegistry
	 */
	protected DomainRepository getDomainRepository() {
		return DomainRepository.getInstance(getOfsProject());
	}

	/**
	 * Returns true if we should create a Column for this type of Widget instead of Labels, and TextFields. This is true
	 * mainly when you drag-and-drop an attribute onto a Table.
	 * 
	 * @param widget
	 *            The Widget
	 * @return boolean
	 */
	protected boolean shouldCreateColumn(Widget widget) {
		// If a ColumnBuilder exists we should create a different Widget
		return COLUMN_BUILDERS.get(widget.getTypeName()) != null;
	}
	
	/**
	 * @param widget the parent widget in which we attempt to drop a dataset attribute
	 * @return true if we are droping the attribute onto a matrix.
	 */
	protected boolean shouldCreateItem(Widget widget) {

		// check if we try to create a widget within a matrix
		boolean dropInMatrix = false;
		Widget tmp = widget;
		for (;;) {
			dropInMatrix = WidgetTypeConstants.MATRIX.equals(tmp.getTypeName());
			if (dropInMatrix) break;
			tmp = tmp.getParent();
			if (tmp == null) break;
		} 
		if (!dropInMatrix) {
			// we are not within a matrix
			// an other construction mechanism will be used
			return false;
		} else {
			// we are within a matrix

			if (widget.getTypeName().equals(WidgetTypeConstants.BOX)) {
				// my direct parent is a box, so determine the specific builder by inspecting the box's parent
				Widget container = MatrixHelper.getParentMatrixContentCell(widget.getParent());
				if (container == null) {
					// no special construction, 
					// and an other construction mechanism will be used
					return false;
				}
			}
			// we are within a matrix and my direct parent is not a box
			return ITEM_BUILDERS.get(widget.getTypeName()) != null;
		}
		
	}

	/**
	 * Gets the primitive type to use in the Type Property from the MdfType.
	 * 
	 * @param entity
	 *            The MdfEntity to get the type for. This should be a primitive type
	 * @return String
	 */
	protected String getPrimitiveType(MdfEntity entity) {
		
		String type = null;
		
		MdfPrimitive primType = null;
		
		if (entity instanceof MdfBusinessType) {
			MdfBusinessType bizType = (MdfBusinessType)entity;
			primType = bizType.getType();
		} else if (entity instanceof MdfPrimitive) {
			primType = (MdfPrimitive) entity;
		}
		if (primType != null) {
			String value = primType.getQualifiedName().toString();
			if (StringUtils.isEmpty(value)) {
				type = "";
			} else {
				type = MDF_TYPE_BINDINGS.get(value.toLowerCase());
			}
		}
		return type == null ? "" : type;
	}
	
	/**
	 * Gets the display format to use in the Format Property from the MdfType.
	 * 
	 * @param entity
	 *            The MdfEntity to get the format for. This should be a business type
	 * @return String
	 */
	protected String getDisplayFormat(MdfEntity entity) {
		String format = "";
		if (entity instanceof MdfBusinessType) {
			MdfBusinessType bizType = (MdfBusinessType)entity;
			format = GUIAspect.getDisplayFormatValue(bizType);
			if (format == null) {
				format = "";
			}
		}
		return format;
	}

	/**
	 * Creates a Column containing a ColumnHeader and a ColumnBody.
	 * 
	 * @param me
	 *            The MdfModelElement
	 * @param parentWidget
	 *            The parent Widget
	 * @return List containing the Column
	 */
	protected List<Widget> createColumn(MdfModelElement me, Widget parentWidget) {
		Class<?> c = COLUMN_BUILDERS.get(parentWidget.getTypeName());
		WidgetBuilder wb = (WidgetBuilder) ClassUtils.newInstance(getClass().getClassLoader(), c.getName(), this, me);
		return wb.buildWidgets();
	}
	
	
	/**
	 * @param me
	 * @param parentWidget
	 * @return
	 */
	protected List<Widget> createItem(MdfModelElement me, Widget parentWidget) {
		Class<?> c = ITEM_BUILDERS.get(parentWidget.getTypeName());
		WidgetBuilder wb = (WidgetBuilder)  ClassUtils.newInstance(getClass().getClassLoader(), c.getName(), this, me);
		return wb.buildWidgets();
	}

	/**
	 * Builds Columns for Tables
	 * 
	 * @author Gary Hayes
	 */
	public class ColumnBuilder implements WidgetBuilder {

		/** The MdfModelElement to create. */
		private MdfModelElement modelElement;

		/**
		 * Creates a new ColumnBuilder.
		 * 
		 * @param modelElement
		 *            The MdfModelElement to create
		 */
		public ColumnBuilder(MdfModelElement modelElement) {
			this.modelElement = modelElement;
		}

		/**
		 * Creates the Widgets. These Widgets may contain child Widgets.
		 * 
		 * @return List The newly created Widgets. These Widgets may contain child Widgets
		 */
		public List<Widget> buildWidgets() {

			Widget column = createWidget(COLUMN);
			column.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());

			Widget header = createWidget(COLUMN_HEADER);
			header.setID(modelElement.getName());
			header.setPropertyValue(COLUMN_NAME, modelElement.getName());
			header.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());

			// propagate the display format
			MdfEntity entity = null;
			if (modelElement instanceof MdfAttribute) {
				entity = ((MdfAttribute)modelElement).getType();
			} else if (modelElement instanceof MdfDatasetProperty) {
				entity = ((MdfDatasetProperty)modelElement).getType();
			}
			String displayFormat = getDisplayFormat(entity);
			header.setPropertyValue(FORMAT, displayFormat);
			
			MdfEntity et = null;
			if (modelElement instanceof MdfAttribute) {
			    et = ((MdfAttribute) modelElement).getType();
			} else if (modelElement instanceof MdfDatasetProperty) {
			    et = ((MdfDatasetProperty) modelElement).getType();
			}
			if (et != null) {
			    header.setPropertyValue(TYPE, getPrimitiveType(et));
			}
			
			column.getContents().add(header);

			Widget body = createWidget(COLUMN_BODY);
			body.setPropertyValue(IS_WIDGET_DELETABLE, "false");
			column.getContents().add(body);

			Widget item = createWidget(ITEM);
			item.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());
			item.setPropertyValue(PREVIEW_VALUE, "Value");
			body.getContents().add(item);

			List<Widget> result = new ArrayList<Widget>();
			result.add(column);
			return result;
		}

		/**
		 * Returns true if instead of adding the built Widgets to the selected Widget it should instead be replaced by
		 * the new Widgets. NOTE that this replacement only occurs if the List contains a single Widget.
		 * 
		 * @return boolean Return false by default
		 */
		public boolean replaceWidget() {
			return false;
		}

		/**
		 * Creates the Properties. These will be used to update an existing Widget.
		 * 
		 * @return List An empty List
		 */
		public List<Property> buildProperties() {
			return new ArrayList<Property>();
		}
	}
	
	/**
	 *
	 * @author pkk
	 *
	 */
	public class MatrixExtraColumnItemBuilder implements WidgetBuilder {
		
		/** The MdfModelElement to create. */
		private MdfModelElement modelElement;
		
		/**
		 * Creates MatrixExtraColumnItemBuilder 
		 * @param modelElement
		 */
		public MatrixExtraColumnItemBuilder(MdfModelElement modelElement) {
			this.modelElement = modelElement;
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.page.transformmodel.ui.builder.WidgetBuilder#buildWidgets()
		 */
		@Override
		public List<Widget> buildWidgets() {		
			/** Uses a script to create a table-column-widget. */
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
			WidgetTemplate template = library.findWidgetTemplate("MatrixExtraColumnItem");
			WidgetFactory factory = new WidgetFactory();

			/** Creates the new column widget from its template. */
			Widget item = factory.create(template);
			item.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName(), true);
			
			List<Widget> result = new ArrayList<Widget>();
			result.add(item);
			return result;
		}		
	}
	
	/**
	 *
	 * @author pkk
	 *
	 */
	public class MatrixContentCellItemBuilder implements WidgetBuilder {
		
		/** The MdfModelElement to create. */
		private MdfModelElement modelElement;
		
		/**
		 * Creates MatrixContentCellItemBuilder 
		 * @param modelElement
		 */
		public MatrixContentCellItemBuilder(MdfModelElement modelElement) {
			this.modelElement = modelElement;
		}

		/* (non-Javadoc)
		 * @see com.odcgroup.page.transformmodel.ui.builder.WidgetBuilder#buildWidgets()
		 */
		@Override
		public List<Widget> buildWidgets() {		
			/** Uses a script to create a table-column-widget. */
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetLibrary library = metamodel.findWidgetLibrary(WidgetLibraryConstants.XGUI);
			WidgetTemplate template = library.findWidgetTemplate("MatrixContentCellItem");
			WidgetFactory factory = new WidgetFactory();

			/** Creates the new column widget from its template. */
			Widget item = factory.create(template);
			item.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName(), true);
			item.setPropertyValue("matrixCellItemType", IMatrixCellItem.ITEMTYPE_DOMAIN);
			
			List<Widget> result = new ArrayList<Widget>();
			result.add(item);
			return result;
		}
		
	}
	
	/**
	 * Builds Columns for new Table/Tree widget 
	 * 
	 * @author atr
	 * @since DS 1.40.0
	 */
	public class TableTreeColumnBuilder implements WidgetBuilder {

		/** name of the xgui library  */
		private static final String XGUI_LIBRARY_NAME = "xgui";

		/** name of the template to create a table column */
		private static final String TABLE_COLUMN_TEMPLATE = "TableColumn";
		
		/** The MdfModelElement to create. */
		private MdfModelElement modelElement;
		
		/**
		 * Creates a new ColumnBuilder.
		 * 
		 * @param modelElement
		 *            The MdfModelElement to create
		 */
		public TableTreeColumnBuilder(MdfModelElement modelElement) {
			this.modelElement = modelElement;
		}

		/**
		 * Creates the Widgets. These Widgets may contain child Widgets.
		 * 
		 * @return List The newly created Widgets. These Widgets may contain child Widgets
		 */
		public List<Widget> buildWidgets() {
			
			/** Uses a script to create a table-column-widget. */
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetLibrary library = metamodel.findWidgetLibrary(XGUI_LIBRARY_NAME);
			WidgetTemplate template = library.findWidgetTemplate(TABLE_COLUMN_TEMPLATE);
			WidgetFactory factory = new WidgetFactory();

			// create the column
			Widget column = factory.create(template);
			column.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName(), true);
			column.setPropertyValue("column-name", modelElement.getName(), true);
			column.setPropertyValue("column-type", "domain", true);
			
			// propagate the display format
			MdfEntity entity = null;
			if (modelElement instanceof MdfAttribute) {
				entity = ((MdfAttribute)modelElement).getType();
			} else if (modelElement instanceof MdfDatasetProperty) {
				entity = ((MdfDatasetProperty)modelElement).getType();
			}
			String displayFormat = getDisplayFormat(entity);
			column.setPropertyValue(FORMAT, displayFormat);
			
			List<Widget> result = new ArrayList<Widget>();
			result.add(column);
			return result;
		}

		/**
		 * Returns true if instead of adding the built Widgets to the selected Widget it should instead be replaced by
		 * the new Widgets. NOTE that this replacement only occurs if the List contains a single Widget.
		 * 
		 * @return boolean Return false by default
		 */
		public boolean replaceWidget() {
			return false;
		}

		/**
		 * Creates the Properties. These will be used to update an existing Widget.
		 * 
		 * @return List An empty List
		 */
		public List<Property> buildProperties() {
			return new ArrayList<Property>();
		}
	}
	
	
	/**
	 * Builds CdmColumns for CdmTables
	 * 
	 * @author Gary Hayes
	 */
	public class CdmColumnBuilder implements WidgetBuilder {

		/** The MdfModelElement to create. */
		private MdfModelElement modelElement;

		/**
		 * Creates a new CdmColumnBuilder.
		 * 
		 * @param modelElement
		 *            The MdfModelElement to create
		 */
		public CdmColumnBuilder(MdfModelElement modelElement) {
			this.modelElement = modelElement;
		}

		/**
		 * Creates the Widgets. These Widgets may contain child Widgets.
		 * 
		 * @return List The newly created Widgets. These Widgets may contain child Widgets
		 */
		public List<Widget> buildWidgets() {

			Widget column = createWidget(WidgetLibraryConstants.CDM_COMP, CDM_COLUMN);
			column.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());

			Widget header = createWidget(WidgetLibraryConstants.CDM_COMP, CDM_COLUMN_HEADER);
			header.setID(modelElement.getName());
			header.setPropertyValue(COLUMN_NAME, modelElement.getName());
			header.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());
			
            MdfEntity et = null;
            if (modelElement instanceof MdfAttribute) {
                et = ((MdfAttribute) modelElement).getType();
            } else if (modelElement instanceof MdfDatasetProperty) {
                et = ((MdfDatasetProperty) modelElement).getType();
            }
            if (et != null) {
                header.setPropertyValue(TYPE, getPrimitiveType(et));
            }			
			
			column.getContents().add(header);

			Widget body = createWidget(COLUMN_BODY);
			body.setPropertyValue(IS_WIDGET_DELETABLE, "false");
			column.getContents().add(body);

			Widget item = createWidget(ITEM);
			item.setPropertyValue(DOMAIN_ATTRIBUTE, modelElement.getName());
			item.setPropertyValue(PREVIEW_VALUE, "Value");
			body.getContents().add(item);

			List<Widget> result = new ArrayList<Widget>();
			result.add(column);
			return result;
		}

		/**
		 * Returns true if instead of adding the built Widgets to the selected Widget it should instead be replaced by
		 * the new Widgets. NOTE that this replacement only occurs if the List contains a single Widget.
		 * 
		 * @return boolean Return false by default
		 */
		public boolean replaceWidget() {
			return false;
		}

		/**
		 * Creates the Properties. These will be used to update an existing Widget.
		 * 
		 * @return List An empty List
		 */
		public List<Property> buildProperties() {
			return new ArrayList<Property>();
		}
	}	
}