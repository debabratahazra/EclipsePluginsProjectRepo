package com.odcgroup.aaa.connector.domainmodel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.odcgroup.aaa.connector.domainmodel.ids.NotepadEntityId;



/**
 * Notepad.
 * 
 * @author Michael Vorburger
 * @IdClass(DenominationEntityId.class)
 */
@Entity(name = "Notepad")
@Table(name = "notepad_vw")
@IdClass(NotepadEntityId.class)
public class NotepadEntity
{
    @Id
    @Basic(optional = false)
    @Column(name = "entity_dict_id", nullable=false)  // NEVER NULL
    private long entity;

    @Id
    @Basic(optional = false)
    @Column(name = "object_id", nullable=false)       // NEVER NULL
    private long object;
    
    @Column(name="note_c")
    private String note;
    
    @Id
    @Basic(optional = false)
    @Column(name="note_d")
    private java.util.Date date;

    @Column(name="title_c")
    private String title;

    public String getTitle() {
        return title;
    }
    
    public java.util.Date getDate() {
        return (date == null) ? null : new Date(this.date.getTime());
    }
    
    public String getNote() {
        return note;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return note;
    }
}
