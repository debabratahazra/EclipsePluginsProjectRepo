package com.odcgroup.otf.jpa.internal.openjpa;

import java.lang.reflect.Method;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.StoreException;

import com.odcgroup.otf.jpa.EnumWithValue;


/**
 * OpenJPA ValueHandler for custom mapping of Java5 enums with values.
 * 
 * The double "ValueValue" is not a typo, as this is indeed an (OpenJPA) ValueHandler for (my) EnumWithValues.
 * 
 * @see EnumWithValue
 * @see http://openjpa.apache.org/builds/latest/docs/manual/ref_guide_mapping_custom.html#ref_guide_mapping_custom_field
 * @see EnumValueHandler
 * 
 * @author Michael Vorburger (MVO)
 * @since 17.09.2008
 */
public class EnumWithValueValueHandler extends AbstractValueHandler {
	private static final long serialVersionUID = 1L;

	public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        Column col = new Column();
        col.setName(name);
        col.setJavaType(JavaTypes.SHORT);
        return new Column[]{ col };
    }

    public boolean isVersionable() {
        return true;
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
        if (val == null)
            return null;
        else
            // This could ClassCastException, but I'm again assuming all fields using this are of an enum type which implements EnumWithValue
            return Integer.valueOf(((EnumWithValue) val).value());
    }

	@Override
    @SuppressWarnings("unchecked")
    public Object toObjectValue(ValueMapping vm, Object val) {
        if (val == null)
            return null;
        else {
            try {
            	// All EnumWithValue classes will have a static method called 'valueOf(int value)', and we need to find it:
            	Method valueOfMethod = vm.getType().getMethod("valueOf", int.class /* No need for 'new Class[] { int.class }' anymore in Java5! */);
                return valueOfMethod.invoke(vm.getType() /* or null? */, val /* No need for 'new Object [] {val}' anymore in Java5! */);
            } catch (Exception e) {
                throw new StoreException("context: vm=" + vm.toString() + ", value=" + val, e);
            }
        }     
    }   
}
