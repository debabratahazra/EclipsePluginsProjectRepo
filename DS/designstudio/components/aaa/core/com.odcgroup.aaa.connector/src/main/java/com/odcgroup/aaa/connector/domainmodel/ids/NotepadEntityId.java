package com.odcgroup.aaa.connector.domainmodel.ids;

import java.io.Serializable;

import com.odcgroup.aaa.connector.domainmodel.NotepadEntity;


/**
 * NotepadEntity's JPA Application identity class (AKA *Ref class, the PK as object).
 * 
 * @see NotepadEntity
 * @author Michael Vorburger
 */
public class NotepadEntityId implements Serializable
{
    private static final long serialVersionUID = -417617944764398375L;

    public long entity;
    public long object;
    public java.util.Date date;
    
    static {
        // register persistent class in JVM (the OpenJPA ApplicationIdTool gen. this; seemed like a good idea)
        try {
            Class.forName(NotepadEntity.class.getName());
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
        result = prime * result + (int) (object ^ (object >>> 32));
        result = prime * result + (int) (date.getTime() ^ (date.getTime() >>> 32));
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
        final NotepadEntityId other = (NotepadEntityId) obj;
        if (entity != other.entity) return false;
        if (object != other.object) return false;
        if (date != other.date) return false;
        return true;
    }
}
