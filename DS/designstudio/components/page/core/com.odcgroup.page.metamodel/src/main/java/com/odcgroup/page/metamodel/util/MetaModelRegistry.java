package com.odcgroup.page.metamodel.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.common.ModelRegistry;
import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.ParameterType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.display.DisplayFormatConstants;
import com.odcgroup.page.metamodel.display.DisplayFormatUtils;

/**
 * This is a singleton class containing the main meta-model. In other
 * words it contains the definitions of the WidgetTypes, PropertyTypes etc.
 * 
 * @author Gary Hayes
 * @author Alain Tripod - added event & function registry
 */
/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public class MetaModelRegistry  extends ModelRegistry {
		
	/** The platform-relative path to the resources. */
	private static final String RESOURCES_PATH = "platform:/plugin/com.odcgroup.page.metamodel/src/main/resources";

	/** The location of the meta-model configuration file relative to the project base. */
	private static final String META_MODEL_FILE_LOCATION = RESOURCES_PATH + "/page-designer.metamodel";

	/** The unique instance of the MetaModelRegistry. */
	private static MetaModelRegistry INSTANCE = new MetaModelRegistry();
	
	/** This Registry contains the set of EventType bounded to each WidgetType */ 
	private EventRegistry eventRegistry;
	
	/** This Registry contains the set of FunctionType bounded to each EventType */ 
	private FunctionRegistry functionRegistry;
	
	/** This registry contains the operators by entity type */
	private OperatorTypeRegistry operatorRegistry;
	
	/** This Registry contains the set of SnippetType */ 
	private SnippetRegistry snippetRegistry;

	/**
	 * Creates a new instance of the MetaModelRegistry.
	 */
	private MetaModelRegistry() {
		loadResource(META_MODEL_FILE_LOCATION);
	}
	
	/**
	 * @param name
	 * @param ordinal
	 * @return DataValue
	 */
	private DataValue getDataValue(String name, int ordinal) {
		DataValue dValue = MetaModelFactory.eINSTANCE.createDataValue();
		dValue.setDisplayName(name);
		dValue.setOrdinal(ordinal);
		dValue.setValue(name);
		return dValue;
	}	
	
	/**
	 * @param metamodel
	 */
	private void updateMetaDataWithDisplayFormats(MetaModel metamodel) {

		List<String> values = DisplayFormatUtils.getDisplayFormat(null).getDataFormats();
		
		PropertyType pType = metamodel.findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.FORMAT);
		if (values.size()>0) {
			pType.getDataType().getValues().clear();
			pType.getDataType().getValues().add(getDataValue("", 0));
		}
		int i = 1;
		for (String string : values) {
			pType.getDataType().getValues().add(getDataValue(string, i));
			i++;
		}
	}
	
	/**
	 * @see com.odcgroup.page.common.ModelRegistry#resourceLoaded(org.eclipse.emf.ecore.resource.Resource)
	 */
	protected void resourceLoaded(Resource resource) {
		final MetaModel metamodel = (MetaModel) getModel();	
		eventRegistry = new EventRegistry(metamodel);
		functionRegistry = new FunctionRegistry(metamodel);
		snippetRegistry = new SnippetRegistry(metamodel);
		operatorRegistry = new OperatorTypeRegistry();
		
		updateMetaDataWithDisplayFormats(metamodel);
		
		DisplayFormatUtils.addPreferenceChangeListener(null,
			new IPreferenceChangeListener() {
				public void preferenceChange(PreferenceChangeEvent event) {
					if (event.getKey().equals(DisplayFormatConstants.PROPERTY_IMPORTED_DATA_FORMATS)) {
						updateMetaDataWithDisplayFormats(metamodel);
					}
				}
			}); 
		
		
//		/* dump the all the WidgetType in the console, CSV format */
//		System.out.println("---------------------------");
//		StringBuffer header = new StringBuffer(256);
//		header.append("Library");
//		header.append('@');
//		header.append("Widget Type");
//		header.append('@');
//		header.append("Widget Type (DisplayName)");
//		header.append('@');
//		header.append("Translation Support");
//		header.append('@');
//		header.append("Parent Widget Type");
//		header.append('@');
//		header.append("Property Type");
//		header.append('@');
//		header.append("Property Type (Display Name)");
//		header.append('@');
//		header.append("Default Value");
//		header.append('@');
//		header.append("Data Type");
//		System.out.println(header);
//		for (WidgetLibrary library :  metamodel.getWidgetLibraries()) {
//			String libName = library.getName();
//			for (WidgetType wt : library.getWidgetTypes()) {
//				Map<String, PropertyType> ptMap = wt.getAllPropertyTypes();
//				for (String propertyName : ptMap.keySet()) {
//					PropertyType pt = ptMap.get(propertyName);
//					DataType dataType = pt.getDataType();
//					StringBuffer line = new StringBuffer(256);
//					line.append(libName);
//					line.append('@');
//					line.append(wt.getName());
//					line.append('@');
//					line.append(wt.getDisplayName());
//					line.append('@');
//					line.append(wt.getTranslationSupport().getLiteral());
//					line.append('@');
//					line.append(wt.getParent() != null ? wt.getParent().getName() : "");
//					line.append('@');
//					line.append(propertyName);
//					line.append('@');
//					line.append(pt.getDisplayName());
//					line.append('@');
//					line.append(pt.getDefaultValue());
//					line.append('@');
//					line.append(dataType == null ? "???" : dataType.getName());
//					System.out.println(line);
//				}
//			}
//		}
//		System.out.println("---------------------------");
		
	}
	
	/**
	 * Gets the MetaModel.
	 * 
	 * @return MetaModel The MetaModel
	 */
	public static MetaModel getMetaModel() {
		return (MetaModel)INSTANCE.getModel();
	}
	
	/**
	 * @param wt an WidgetType instance
	 * @return a set of supported EventType given a WidgetType 
	 */
	public static Set<EventType> getEventsFor(WidgetType wt) {
		return INSTANCE.eventRegistry.getEventTypesFor(wt);
	}
	
	/**
	 * @param wt an WidgetType instance
	 * @return a set of supported EventType given a WidgetType 
	 */
	public static boolean supportsEvents(WidgetType wt) {
		return INSTANCE.eventRegistry.getEventTypesFor(wt).size() > 0;
	}	
	
	/**
	 * @param wt widget type
	 * @param eventTypeName the name of an event type
	 * @return {@code true} if the widget supports the given event type
	 */
	public static boolean hasEventType(WidgetType wt, String eventTypeName) {
		return INSTANCE.eventRegistry.hasEventType(wt, eventTypeName);
	}
	
	/**
	 * @param et an EventType instance
	 * @return a set of supported Function Type given a EventType 
	 */
	public static Set<FunctionType> getFunctionsFor(EventType et) {
		return INSTANCE.functionRegistry.getFunctionsFor(et);
	}
	
	/**
	 * Finds the FunctionType given its name.
	 * 
	 * @param functionName The name of the function
	 * @return FunctionType or null if no function could be found
	 */
	public static FunctionType findFunctionType(String functionName) {
		return INSTANCE.functionRegistry.findFunctionType(functionName);
	}
	
	/**
	 * Finds the Function's Parameter Types given a function name.
	 * 
	 * @param functionName The name of the function
	 * @return all parameter types as a map
	 */
	public static Map<String, ParameterType> findFunctionParameterTypes(String functionName) {
		return INSTANCE.functionRegistry.findFunctionParameterTypes(functionName);
	}	
	
	/**
	 * Finds the EventType given its name.
	 * 
	 * @param eventName the name of the Event
	 * @return EventType or null if no event could be found
	 */
	public static EventType findEventType(String eventName) {
	    return INSTANCE.eventRegistry.findEventType(eventName);
	}
	
	/**
	 * Gets the propertyTypes supported by the designated event
	 * @param eventName the name of an event
	 * @return a map of all the property types
	 */
	public static Map<String, PropertyType> findEventPropertyTypes(String snippetTypeName) {
	    return INSTANCE.eventRegistry.findEventPropertyTypes(snippetTypeName);
	}
	
	/**
	 * Finds the EventType given its name.
	 * 
	 * @param eventName the name of the Event
	 * @return EventType or null if no event could be found
	 */
	public static SnippetType findSnippetType(String snippetTypeName) {
	    return INSTANCE.snippetRegistry.findSnippetType(snippetTypeName);
	}
	
	/**
	 * Gets the propertyTypes supported by the designated snippet type
	 * @param snippetTypeName the name of a snippet type
	 * @return a map of all the property types
	 */
	public static Map<String, PropertyType> findSnippetPropertyTypes(String snippetTypeName) {
	    return INSTANCE.snippetRegistry.findSnippetPropertyTypes(snippetTypeName);
	}
	
	/**
	 * @param entity
	 * @return property type
	 */
	public static PropertyType findOperatorPropertyTypeFor(MdfEntity entity) {
		List<DataValue> operators = INSTANCE.operatorRegistry.getOperatorsFor(entity);
		PropertyType pType = getMetaModel().findPropertyType(WidgetLibraryConstants.XGUI, PropertyTypeConstants.OPERATOR);
		if (!operators.isEmpty()) {
			pType.getDataType().getValues().clear();
			pType.getDataType().getValues().addAll(operators);
		}		
		return pType;
	}

}