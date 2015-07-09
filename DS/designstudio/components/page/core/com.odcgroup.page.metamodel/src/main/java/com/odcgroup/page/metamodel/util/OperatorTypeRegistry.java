package com.odcgroup.page.metamodel.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModelFactory;
import com.odcgroup.page.metamodel.PageMetamodelActivator;

/**
 *
 * @author pkk
 */
public final class OperatorTypeRegistry {
	

	/** The default filter operators properties file location	 */
	private static final String FILTEROPERATORS_DEFAULT_LOCATION = "src/main/resources/operator.properties";
	/** */
	private static Map<Type , List<DataValue>> mapOp;
	/** */
	private static Map<Type, Operator> mapDefaultOp;
	
	/**
	 * 
	 */
	OperatorTypeRegistry() {
		loadOperators();
	}
	
	/**
	 * load the operators by type from the operators.properties
	 */
	protected void loadOperators() {
		URL url = FileLocator.find(PageMetamodelActivator.getDefault().getBundle(), new Path(FILTEROPERATORS_DEFAULT_LOCATION), null);
		try { 
			readTypeOperators(url.openStream());
		} catch (IOException ex) {
			String message = "Unable to load operator types";
			IStatus status = new Status(IStatus.ERROR, PageMetamodelActivator.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			PageMetamodelActivator.getDefault().getLog().log(status);
		}
	}
	
	/**
	 * @param entity
	 * @return list
	 */
	public List<DataValue> getOperatorsFor(MdfEntity entity) {
		Type type = FilterUtil.getOperatorKeyType(entity);
		if (mapOp == null) {
			loadOperators();
		}
		return mapOp.get(type);
	}
	
	/**
	 * @param type
	 * @return operator
	 */
	public Operator getDefaultOperator(Type type) {
		return mapDefaultOp.get(type);
	}

	/**
	 * @param inputStream 
	 * @throws IOException 
	 */
	private void readTypeOperators(InputStream inputStream) throws IOException {
		mapOp = new HashMap<Type, List<DataValue>>();
		mapDefaultOp = new HashMap<Type, Operator>();
		Properties properties = new Properties();
		properties.load(inputStream);	
		

		buildDefaultOperator(properties);
		
		buildOperator(properties, "StringOpList", Type.STRING);
		buildOperator(properties, "DateOpList", Type.DATE);
		buildOperator(properties, "NumberOpList", Type.NUMBER);
		buildOperator(properties, "FlagOpList", Type.FLAG);
		buildOperator(properties, "EnumerationOpList", Type.ENUMERATION);
		
	}	
	
	/**
	 * @param props
	 * @param key
	 * @param type
	 */
	private void buildOperator(Properties  props, String key , Type type){
		List<DataValue> operators = new ArrayList<DataValue>();
		String operatorsList = (String) props.get(key);
		String[] splited = operatorsList.split(",");
		int ii = 0;
		int def = 0;
		Operator operator = null;
		for (int i = 0; i < splited.length; i+=2) {
			operator = Operator.valueOf(splited[i]);
			operators.add(getDataValue(operator.name(), splited[i+1], ii));
			if (getDefaultOperator(type).equals(operator)) {
				def = ii;
			}
			ii++;
		}
		
		if (def > 0) {
			DataValue defaultVal = operators.get(def);
			defaultVal.setOrdinal(0);
			DataValue firstVal = operators.get(0);
			firstVal.setOrdinal(def);
			
			Collections.swap(operators, 0, def);
		}
	
		
		mapOp.put(type, operators);
	}
	
	/**
	 * @param value
	 * @param displayName
	 * @param ordinal
	 * @return dataValue
	 */
	private static DataValue getDataValue(String value, String displayName, int ordinal) {
		DataValue dValue = MetaModelFactory.eINSTANCE.createDataValue();
		dValue.setDisplayName(displayName);
		dValue.setOrdinal(ordinal);
		dValue.setValue(value);
		return dValue;
	}	
	
	/**
	 * @param props
	 */
	private static void buildDefaultOperator(Properties  props){
		String operatorsList = (String) props.get("DefaultOp");
		String[] splited = operatorsList.split(",");
		for (int i = 0; i < splited.length; i+=2) {
			mapDefaultOp.put(Type.valueOf(splited[i]),Operator.valueOf(splited[i+1]));
		}
	}
	
	/**
	 *
	 */
	public enum Operator {
		EQUAL, DIFFERENT, CONTAINS, NOT_CONTAINS, BEGINS_WITH, ENDS_WITH, 
		LIKE, NOT_LIKE, IS_NULL, IS_NOT_NULL, IN, NOT_IN,
		GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL,
		BETWEEN, NOT_BETWEEN
	}
	
	/**
	 *
	 */
	public enum Type {
		ENUMERATION, FLAG, STRING, NUMBER, DATE, DEFAULT
	}

}
