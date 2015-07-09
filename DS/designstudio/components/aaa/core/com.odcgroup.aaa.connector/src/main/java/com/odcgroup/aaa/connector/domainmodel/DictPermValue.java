package com.odcgroup.aaa.connector.domainmodel;

import static javax.persistence.CascadeType.PERSIST;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.odcgroup.aaa.connector.internal.nls.TranslateableAdapter;

/**
 * A T'A Dict Perm Value as JPA Entity.
 * 
 * @author Michael Vorburger
 */
@Entity
@Table(name = "dict_perm_value_vw")
@SuppressWarnings("serial")
public class DictPermValue extends TranslateableAdapter {

	@Id
	@Column(insertable=false, updatable = false, nullable=false)
	@SuppressWarnings("unused")
	private long dict_id;
	
    @ManyToOne(cascade=PERSIST, fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="attribute_dict_id", nullable=false)
    private DictAttribute attribute;

	@Column(insertable=false, updatable = false, nullable=false)
	private String name;

	@Column(insertable=false, updatable = false, nullable=false)
	private int perm_val_nat_e;

	@Column(insertable=false, updatable = false, nullable=false)
	private int xd_status_e;

	@Column(insertable=false, updatable = false, nullable=false)
	// NOTE: rank_n is ALSO mapped in DictAttribute (to determines the load order of the List there)
	private int rank_n;
	
//	public long getDictID() {
//		return dict_id;
//	}

	public DictAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(DictAttribute attribute) {
		this.attribute = attribute;
	}
	
	public int getRank() {
		return rank_n;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPermValNatE() {
		return perm_val_nat_e;
	}
        
	public int getStatus() {
		return xd_status_e;
	}

	// ----------------------------------------------------------------------
    
	private static final ToStringStyle TSS = new ToStringStyle() {
	    {
	        // This is the constructor of this anonymous inner classes
	        setUseShortClassName(false);    
            setUseClassName(false);    
	        setUseIdentityHashCode(false);
	    }
	    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
	        if (value instanceof DictAttribute) {
	          value = ((DictAttribute)value).getSQLName();
	        }
	        buffer.append(value);
	      }
	};
	
	public String toString()
	{
	    // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
	    return ToStringBuilder.reflectionToString(this, TSS);
	}
}
