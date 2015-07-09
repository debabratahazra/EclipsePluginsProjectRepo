package com.odcgroup.aaa.connector.mdfmml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.odcgroup.aaa.connector.domainmodel.FormatElementEntity;
import com.odcgroup.aaa.connector.internal.metadictreader.EntityAttributeSqlNamesPair;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

/**
 * Wrapper around an MdfDomain created from a T'A MetaDictionary.
 * Offers a few convenience helper access methods. 
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictMdfDomainHelper {

    private final MdfDomain metaDictDomain;
    private final MdfDomain businessTypesDomain;    
    private final Map<String, MdfClass> mapEntitySqlNameToMMLClass;
//    private final Map<EntityAttributeSqlNamesPair, MdfProperty> mapAttributeDictIdToMML;
    private final Map<EntityAttributeSqlNamesPair, MdfEnumeration> mapEntityAttributeSqlNameToEnum;

    @SuppressWarnings("unchecked")
    public MetaDictMdfDomainHelper(MdfDomain metaDictionaryDomain, MdfDomain enumerationsDomain, MdfDomain businessTypesDomain) throws TA2MMLException {
        super();
        this.metaDictDomain = metaDictionaryDomain;
        //this.enumerationsDomain = enumerationsDomain;
        this.businessTypesDomain = businessTypesDomain;
        
        List<MdfEntity> entities = metaDictDomain.getEntities();
        List<MdfEntity> enums = enumerationsDomain.getEntities();
        mapEntitySqlNameToMMLClass = new HashMap<String, MdfClass>(entities.size());
//        mapAttributeDictIdToMML = new HashMap<Long, MdfProperty>();
        mapEntityAttributeSqlNameToEnum = new HashMap<EntityAttributeSqlNamesPair, MdfEnumeration>();
        for (MdfEntity mdfEntity: entities) {
            if (mdfEntity instanceof MdfClass) {
                MdfClass mdfClass = (MdfClass) mdfEntity; 
                String sqlName = AAAAspect.getTripleAEntitySQLName(mdfClass);
                if (sqlName == null)
                	throw new TA2MMLException("The MdfClass has no TripleAEntitySQLName " + mdfClass.toString());
                mapEntitySqlNameToMMLClass.put(sqlName, mdfClass);
                
//                List<MdfProperty> attributes = mdfClass.getProperties();
//                for (MdfProperty mdfAttribute : attributes) {
//                    Long attributeDictId = AAAAspect.getDictID(mdfAttribute);
//                    if (attributeDictId != null) {
//                    mapAttributeDictIdToMML.put(attributeDictId, mdfAttribute);
//                }
            }
        }
        
        for (MdfEntity mdfEntity: enums) {
            if (mdfEntity instanceof MdfEnumeration) {
                MdfEnumeration mdfEnum = (MdfEnumeration) mdfEntity;
                String entitySQLName = AAAAspect.getTripleAEntitySQLName(mdfEnum);
        		if (entitySQLName == null) 
                    throw new TA2MMLException("MDF Enum " + mdfEnum.toString() + " is missing expected MDF annotation with T'A Entity SQL Name");
                String attrSQLName = AAAAspect.getTripleAAttributeSQLName(mdfEnum);
        		if (attrSQLName == null) 
                    throw new TA2MMLException("MDF Enum " + mdfEnum.toString() + " is missing expected MDF annotation with T'A Attribute SQL Name");

                EntityAttributeSqlNamesPair key = new EntityAttributeSqlNamesPair(entitySQLName, attrSQLName);
                mapEntityAttributeSqlNameToEnum.put(key, mdfEnum);
            }
        }
    }
    
    public MdfClass getMdfClass(String entitySQLName) {
    	if (entitySQLName == null)
            throw new IllegalArgumentException("entitySQLName == null");
        MdfClass klass = mapEntitySqlNameToMMLClass.get(entitySQLName);
        if (klass == null) {
            throw new IllegalArgumentException("There is no MdfClass with entitySQLName=" + entitySQLName + " in this domain");
        }
        return klass;
    }
    
//    public MdfProperty getMdfProperty(long attributeDictID) {
//        MdfProperty p = mapAttributeDictIdToMML.get(attributeDictID);
//        if (p == null) {
//            throw new IllegalArgumentException("There is no MdfProperty with attributeDictID=" + attributeDictID + " in this domain");
//        }
//        return p;
//    }
    
	public MdfEnumeration getMdfEnum(FormatElementEntity element) {
		String entitySQLName    = element.getAttribute().getDictEntity().getSQLName();
		String attributeSQLName = element.getAttribute().getSQLName();
		
        EntityAttributeSqlNamesPair key = new EntityAttributeSqlNamesPair(entitySQLName, attributeSQLName);
        MdfEnumeration e = mapEntityAttributeSqlNameToEnum.get(key);
        if (e != null) {
            return e;
        } else {
        	if (element.getAttribute().getParentAttribute() != null) {
        		String parentEntitySQLName    = element.getAttribute().getParentAttribute().getDictEntity().getSQLName();
        		String parentAttributeSQLName = element.getAttribute().getParentAttribute().getSQLName();
                key = new EntityAttributeSqlNamesPair(parentEntitySQLName, parentAttributeSQLName);
                e = mapEntityAttributeSqlNameToEnum.get(key);
        	}
            if (e != null) {
                return e;
            } else {
                throw new IllegalArgumentException("There is no MdfEnumeration with entitySQLName/attributeSQLName=" 
                		+ entitySQLName + "/" + attributeSQLName + " (nor for the parentEntity, if there is one) in this domain");
        	}
        }
	}
    
    public final MdfDomain getMdfBusinessTypesDomain() {
    	return businessTypesDomain;
    }
    
}
