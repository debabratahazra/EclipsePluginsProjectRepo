package com.odcgroup.aaa.connector.mdfmml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * Mapper from TAP DictDataType to MDF / Domain Primitives.
 * 
 * @since The Great Refactoring of 2012.10.17 - had to refactor this out of DictAttribute @Entity so that Enhancement doesn't depend on EMF classes.
 * 
 * @author Michael Vorburger
 */
public class DataType2Primitives {
	
    // RegExp pattern to parse Triple'A dict_datatype.equiv_type_c column.
    // (It is static because Pattern is thread safe.  But Matcher is not.)
    private final static Pattern PATTERN = Pattern.compile("(\\w+)(\\((\\d+)(,(\\d+))?\\))?");

	private Map<DictDataType, MdfPrimitive> dataType2Primitives = new HashMap<DictDataType, MdfPrimitive>();
	private Map<DictDataType, MdfBusinessType> dataType2BusinessType = new HashMap<DictDataType, MdfBusinessType>();
	
	public MdfPrimitive getMDFDataType(DictDataType datatype, boolean usePrimitiveType) {
		MdfPrimitive mdfType = dataType2Primitives.get(datatype);
        if (mdfType == null) {
            mdfType = findMDFDataType(datatype, usePrimitiveType);
            dataType2Primitives.put(datatype, mdfType);
        }
        return mdfType;
	}

	public MdfBusinessType getBusinessType(DictDataType datatype, MdfDomain businessTypesDomain) {
		MdfBusinessType mdfBusinessType = dataType2BusinessType.get(datatype);
    	if (mdfBusinessType == null) {
    		mdfBusinessType = findBusinessType(datatype, businessTypesDomain);
    		dataType2BusinessType.put(datatype, mdfBusinessType);
    	}
    	return mdfBusinessType;
	}

	@SuppressWarnings("unchecked")
	private MdfBusinessType findBusinessType(DictDataType datatype, MdfDomain businessTypesDomain) {
    	String sqlName = datatype.getSqlname();
    	
   		//DS-1648 
    	if ("timestamp".equalsIgnoreCase(sqlName)) {
    		sqlName = "timestamp_t";
    	}
    	
    	if (!sqlName.endsWith("_t")) {
        	throw new IllegalArgumentException("DictDataType.name " + sqlName + " is expected to end with _t");
    	}
    	sqlName = sqlName.substring(0, sqlName.length()-2);
    	sqlName = sqlName.replaceAll("\\_", "");
    	
    	for (MdfBusinessType businessType : (List<MdfBusinessType>)businessTypesDomain.getBusinessTypes()) {
    		if (businessType.getName().equalsIgnoreCase(sqlName)) {
    			return businessType;
    		}
    	}
    	throw new IllegalArgumentException("DictDataType.name " + datatype.getName() + " cannot be mapped with existing the business types");
    }
    
    private MdfPrimitive findMDFDataType(DictDataType datatype, boolean usePrimitiveType) {
        String equiv_type_c = datatype.getEquivType();
        if (equiv_type_c == null)
            throw new IllegalArgumentException("null DictDataType.equiv_type_c can not be converted to MdfPrimitive");
        String sqlName = datatype.getSqlname();
        if (sqlName == null)
            throw new IllegalArgumentException("null sqlname_c on DictDataType.equiv_type_c "  + equiv_type_c);
        
        Matcher matcher = PATTERN.matcher(equiv_type_c);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("DictDataType.equiv_type_c equals to '" + equiv_type_c
                    + "' can not be converted to MdfPrimitive (because the RegExp Pattern didn't match)");
        }
        
        String typeName = matcher.group(1);
        String typeSize = matcher.group(3);
        String decimalSize = matcher.group(5);

        int iTypeSize = (typeSize != null && !"".equals(typeSize) ? Integer.parseInt(typeSize) : 0);
        int iDecimalSize = (decimalSize != null && !"".equals(decimalSize) ? Integer.parseInt(decimalSize) : 0);

        // A flag_t (which is tinyint, but not an Integer..)
        if (sqlName.equals("flag_t")) {
            if (usePrimitiveType)
                return PrimitivesDomain.BOOLEAN;
            else
                return PrimitivesDomain.BOOLEAN_OBJ;
        }
        // String  (PS: T'A timestamp here as Java String... but this is never used / actually skipped, for now; @see history in http://rd.oams.com/browse/DS-1648)
        else if ("varchar".equalsIgnoreCase(typeName) || "univarchar".equalsIgnoreCase(typeName)
           || "text".equalsIgnoreCase(typeName) || "binary".equalsIgnoreCase(typeName)) {
            return PrimitivesDomain.STRING;
        }

        // Integer
        else if ("int".equalsIgnoreCase(typeName) || "smallint".equalsIgnoreCase(typeName)
                || "tinyint".equalsIgnoreCase(typeName) || "numeric".equalsIgnoreCase(typeName)
                && (0 < iTypeSize && iTypeSize < 9 && iDecimalSize == 0)) {

            if (usePrimitiveType)
                return PrimitivesDomain.INTEGER;
            else
                return PrimitivesDomain.INTEGER_OBJ;
        }

        // Long
        else if ("numeric".equalsIgnoreCase(typeName) && (8 < iTypeSize && iTypeSize < 19 && iDecimalSize == 0)) {
            if (usePrimitiveType)
                return PrimitivesDomain.LONG;
            else
                return PrimitivesDomain.LONG_OBJ;
        }

        // Date
        else if ("date".equalsIgnoreCase(typeName)) {
            return PrimitivesDomain.DATE;
        }

        // Datetime
        else if ("datetime".equalsIgnoreCase(typeName)) {
            return PrimitivesDomain.DATE_TIME;

        }

        // Double
        else if ("numeric".equalsIgnoreCase(typeName) && (iTypeSize > 18 && iDecimalSize == 0)
                || "numeric".equalsIgnoreCase(typeName) && (iDecimalSize > 0)) {
            if (usePrimitiveType)
                return PrimitivesDomain.DOUBLE;
            else
                return PrimitivesDomain.DOUBLE_OBJ;
        }
        
        
        // bigint
        else if ("bigint".equalsIgnoreCase(typeName)) {
            if (usePrimitiveType)
                return PrimitivesDomain.LONG;
            else
                return PrimitivesDomain.LONG_OBJ;
        }

        // Unknown type
        else {
            throw new IllegalArgumentException("DictDataType.equiv_type_c equals to '" + equiv_type_c
                    + "' (typeName=" + typeName + ", typeSize=" + typeSize + ", decimalSize=" + decimalSize
                    + "' can not be converted to MdfPrimitive");
        }
    }
    
}