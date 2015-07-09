package com.odcgroup.page.metamodel.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.odcgroup.page.metamodel.EventModel;
import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.FunctionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.ParameterType;

/**
 * This Helper class shall be used to retrieve the set of FunctionType 
 * supported by an EventType.<p>
 * 
 * This class is used by the MetaModelRegistry
 * 
 * @author Alain Tripod
 * @version 1.0
 */
final class FunctionRegistry {
	
	/** The mapping between the EventType and the FunctionTypes. */
	private Map<EventType, Set<FunctionType>> functionsMap = new HashMap<EventType, Set<FunctionType>>();
	
	/** The mapping between the Function name and the FunctionType. */
	private Map<String, FunctionType> functionTypes = new HashMap<String, FunctionType>();
	
	/** The parameter types for each function type */
	private Map<String, Map<String, ParameterType>> funcParameterTypes = new HashMap<String, Map<String, ParameterType>>();
	
	/**
	 * Initializes this registry.
	 * 
	 * @param metamodel The MetaModel
	 */
	FunctionRegistry(MetaModel metamodel) {
		EventModel em = metamodel.getEventModel();
		if (em != null) {
			for (FunctionType funcType : em.getFunctions()) {
				functionTypes.put(funcType.getName(), funcType);
				for (EventType eventType : funcType.getEventTypes()) {
					bindFunctionType(eventType, funcType);
				}
				Map<String, ParameterType> map = new HashMap<String, ParameterType>();
				funcParameterTypes.put(funcType.getName(), map);
				for (ParameterType pt : funcType.getParameters()) {
					map.put(pt.getName(), pt);
				}
				
			}
		}
	}
	
	/**
	 * Binds the FunctionType.
	 * 
	 * @param et The EventType
	 * @param ft The FunctionType to bind
	 */
	private final void bindFunctionType(EventType et, FunctionType ft) {
		getFunctions(et).add(ft);
	}

	/**
	 * Gets the FunctionTypes for the specified EventType.
	 * @param et The EventType
	 * @return Set of Functiontypes
	 */
	private Set<FunctionType> getFunctions(EventType et) {
		Set<FunctionType> functions = functionsMap.get(et);
		if (functions == null) {
			functions = new HashSet<FunctionType>();
			functionsMap.put(et, functions);
		}
		return functions;
	}

	/**
	 * @param et an EventType instance
	 * @return a set of supported Function Type given a EventType 
	 */
	Set<FunctionType> getFunctionsFor(EventType et) {
		return getFunctions(et);
	}
	
	/**
	 * Finds the FunctionType given its name.
	 * 
	 * @param functionName The name of the function
	 * @return FunctionType or null if no function could be found
	 */
	public FunctionType findFunctionType(String functionName) {
		return functionTypes.get(functionName);
	}	
	
	/**
	 * Gets the parameterTypes supported by the designated function
	 * @param functionName the name of a function
	 * @return the parameter types map
	 */
	Map<String, ParameterType> findFunctionParameterTypes(String functionName) {
		Map<String, ParameterType> map = funcParameterTypes.get(functionName);
		if (map == null) {
			map = new HashMap<String, ParameterType>();
		}
		return Collections.unmodifiableMap(map);
	}	

}
