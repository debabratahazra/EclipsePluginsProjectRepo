package com.odcgroup.aaa.connector.domainmodel.ids;

import java.io.Serializable;

import com.odcgroup.aaa.connector.domainmodel.DenominationEntity;


/**
 * DenominationEntity's JPA Application identity class (AKA *Ref class, the PK as object).
 * 
 * @see DenominationEntity
 * @author Michael Vorburger
 */
public class DenominationEntityId implements Serializable
{
    private static final long serialVersionUID = -417617944764398375L;

    public long entity;
    public long object;
    public long language;
    
    static {
        // register persistent class in JVM (the OpenJPA ApplicationIdTool gen. this; seemed like a good idea)
        try {
            Class.forName(DenominationEntity.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (entity ^ (entity >>> 32));
        result = prime * result + (int) (language ^ (language >>> 32));
        result = prime * result + (int) (object ^ (object >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final DenominationEntityId other = (DenominationEntityId) obj;
        if (entity != other.entity) return false;
        if (language != other.language) return false;
        if (object != other.object) return false;
        return true;
    }
}
