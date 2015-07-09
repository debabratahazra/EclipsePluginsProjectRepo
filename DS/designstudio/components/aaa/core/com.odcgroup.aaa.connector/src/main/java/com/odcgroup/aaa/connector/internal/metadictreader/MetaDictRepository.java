package com.odcgroup.aaa.connector.internal.metadictreader;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.FastHashMap;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;


/**
 * Triple'A Meta Dictionary.
 *
 * Consider this class a "Data Transfer Object": it's an encapsulation of a complete meta dictionary read from the DB,
 * in the form of an object graph. It's "disconnected" from the database, and could, in theory, e.g. be serialized over
 * the wire to some kind of client.
 *
 * Note: Formats are not part of the Meta Dictionary.
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictRepository {

    private final Set<DictEntity> entities;
    private final Map<Long, List<DictPermValue>> permValues;
    private final Map<EntityAttributeSqlNamesPair, DictAttribute> attributesMap;
    private final TAVersion taVersionInfo;

    @SuppressWarnings("unchecked")
    public MetaDictRepository(Collection<DictEntity> entities, TAVersion taVersionInfo) {
    	this.taVersionInfo = taVersionInfo;
		this.entities = Collections.unmodifiableSet(new HashSet<DictEntity>(entities));
        FastHashMap localAttributesMap = new FastHashMap();
        FastHashMap localPermValues = new FastHashMap();
        for (DictEntity dictEntity : entities) {
            for (DictAttribute dictAttribute : dictEntity.getSortedAttributes()) {
            	EntityAttributeSqlNamesPair key = new EntityAttributeSqlNamesPair(dictAttribute);
                localAttributesMap.put(key, dictAttribute);
                if (dictAttribute.getPermValues().size() > 0) {
                    localPermValues.put(dictAttribute.getDictID(), dictAttribute.getPermValues());
                }
            }
        }
        localAttributesMap.setFast(true);
        localPermValues.setFast(true);

        this.permValues = Collections.unmodifiableMap(localPermValues);
        this.attributesMap = Collections.unmodifiableMap(localAttributesMap);
    }

    /**
     * Get all Entities of this T'A Meta Dictionary.
     */
    public Set<DictEntity> getEntities() {
        return this.entities;
    }

    /**
     * Get directly all Attributes of this T'A Meta Dictionary. This is a convenience method only; normally, all
     * Attributes can of course be found on an Entity.
     */
	public DictAttribute getDictAttribute(String entitySQLName, String attributeSQLName) {
        return attributesMap.get(new EntityAttributeSqlNamesPair(entitySQLName, attributeSQLName));
	}
    
    /**
     * Get T'A's PermValues.
     *
     * @return Map where the key is the attribute DictID of the DictAttribute which had these perm. values, and value of
     *         Map is a List of DictPermValue.
     */
    public Map<Long, List<DictPermValue>> getPermValues() {
        return this.permValues;
    }

	/**
	 * @return the taVersionInfo
	 */
	public String getTaVersionInfo() {
		return taVersionInfo.getVersion();
	}

}
