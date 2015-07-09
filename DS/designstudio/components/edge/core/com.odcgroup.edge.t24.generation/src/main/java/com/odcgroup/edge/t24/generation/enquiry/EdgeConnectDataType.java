package com.odcgroup.edge.t24.generation.enquiry;

import com.acquire.intelligentforms.entities.types.DateType;
import com.acquire.intelligentforms.entities.types.DecimalType;
import com.acquire.intelligentforms.entities.types.NumberType;
import com.acquire.intelligentforms.entities.types.TextType;
import com.acquire.util.AssertionUtils;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum EdgeConnectDataType {
	DATE(DateType.TYPE),
	DECIMAL(DecimalType.TYPE),
	NUMBER(NumberType.TYPE),
	TEXT(TextType.TYPE),
	BOOLEAN(BasicEnquiryTemplateIFPConstants.Lists.TrueOrFalse.FULLNAME + " List"),
	RELATIVEDATE(TextType.TYPE),
	FREQUENCY(TextType.TYPE);
	
	private final String m_dataTypeName;
	
	public static EdgeConnectDataType findForPrimitive(MdfPrimitive  p_entity, EdgeConnectDataType p_default)
	{
		AssertionUtils.requireNonNull(p_entity, "p_entity");
		
		if (!p_entity.isPrimitiveType() )
        {
			return p_default;
        }
		
		final String businessType = p_entity.getName();
		
		
		//Qn: if display type for some field is set as Date then should the field contain the calendar?
		if ( businessType.equals( "D" ) )
            return DATE;
		
		//browser does not support FQO for enquiries. Newly introduced.
		if ( businessType.equals( "FQU" ) || businessType.equals( "FQO" ))
		    return FREQUENCY;
		
		//browser does not support DP for enquiries. Newly introduced.
		if ( businessType.equals( "DP" ) )
            return RELATIVEDATE;
        

    	final MdfPrimitive mdfPrimitive = (p_entity instanceof MdfBusinessType) ? ((MdfBusinessType) p_entity).getType() : (MdfPrimitive) p_entity;
    	
        if ( mdfPrimitive != null )
        {
            if ( mdfPrimitive.equals( PrimitivesDomain.STRING ) )
                return TEXT;

            if ( mdfPrimitive.equals( PrimitivesDomain.BOOLEAN ) || mdfPrimitive.equals( PrimitivesDomain.BOOLEAN_OBJ ) )
                return BOOLEAN;

            if ( mdfPrimitive.equals( PrimitivesDomain.DATE ) || mdfPrimitive.equals( PrimitivesDomain.DATE_TIME ) )
                return DATE;

			if (
				mdfPrimitive.equals(PrimitivesDomain.SHORT) || mdfPrimitive.equals(PrimitivesDomain.SHORT_OBJ) ||
				mdfPrimitive.equals(PrimitivesDomain.INTEGER) || mdfPrimitive.equals(PrimitivesDomain.INTEGER_OBJ) ||
				mdfPrimitive.equals(PrimitivesDomain.LONG) || mdfPrimitive.equals(PrimitivesDomain.LONG_OBJ)
			) {
                return NUMBER;
            }

			if (
				mdfPrimitive.equals(PrimitivesDomain.DECIMAL) || mdfPrimitive.equals(PrimitivesDomain.FLOAT_OBJ) ||
                    mdfPrimitive.equals( PrimitivesDomain.FLOAT ) || mdfPrimitive.equals( PrimitivesDomain.FLOAT_OBJ ) ||
				mdfPrimitive.equals(PrimitivesDomain.DOUBLE) || mdfPrimitive.equals(PrimitivesDomain.DOUBLE_OBJ)
			) {
                return DECIMAL;
            }
        }
        
        return p_default;
		
	}
	
	private EdgeConnectDataType(String p_dataTypeName)
	{
		m_dataTypeName = AssertionUtils.requireNonNullAndNonEmpty(p_dataTypeName, "p_dataTypeName");
	}
	
	public String getDataTypeName()
	{
		return m_dataTypeName;
	}
}
