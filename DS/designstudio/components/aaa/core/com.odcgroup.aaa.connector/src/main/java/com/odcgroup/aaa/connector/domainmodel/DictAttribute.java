package com.odcgroup.aaa.connector.domainmodel;

import static javax.persistence.CascadeType.PERSIST;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.openjpa.persistence.jdbc.Strategy;

import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeCalculated;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeDateTimeWidget;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeEditable;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeForeignKeyPresentation;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributePermAuth;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeTascView;
import com.odcgroup.aaa.connector.internal.nls.TranslateableAdapter;
import com.odcgroup.otf.jpa.internal.openjpa.OpenJPAPerformanceHelper;

/**
 * A T'A DictAttribute as JPA Entity.
 * 
 * @author Camille Weber, Michael Vorburger
 */
@Entity
@Table(name = "dict_attribute_vw")
@SuppressWarnings("serial")
public class DictAttribute extends TranslateableAdapter {
	
	@Id
	@Column(insertable=false, updatable = false, nullable=false)
	private long dict_id;
	
	@Column(insertable=false, updatable = false, nullable=false)
	private String name;

	@Column(insertable=false, updatable = false, nullable=false)
	private int xd_status_e;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="datatype_dict_id", nullable=false, updatable=false)
	private DictDataType datatype; 
	
	@ManyToOne(cascade=PERSIST, fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="entity_dict_id", updatable=false, nullable=false)
	private DictEntity dictEntity;

	@ManyToOne(cascade=PERSIST, fetch=FetchType.LAZY, optional=true) 
    @JoinColumn(name="ref_entity_dict_id", nullable=true)
	private DictEntity referencedDictEntity;
	
    @ManyToOne(cascade=PERSIST, fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name="parent_attribute_dict_id", nullable=true)
    private DictAttribute parentAttribute;
	
//    @OneToMany(cascade=PERSIST, fetch=FetchType.EAGER, mappedBy="attribute")	
////	// NOT HERE: @ElementJoinColumn(name="attribute_dict_id")
////	// No @OrderColumn(name="rank_n")
//    private List<DictPermValue> dictPermValues;

    @Transient
    // @see MetaDictDAO  
    private List<DictPermValue> dictPermValues = new LinkedList<DictPermValue>();
    
	@Column(insertable=false, updatable = false, nullable=false)
	private String sqlname_c;

	@Column(insertable=false, updatable = false, nullable=false)
	private boolean primary_f;

	@Column(insertable=false, updatable = false, nullable=false)
	private boolean mandatory_f;

    @Column(insertable=false, updatable = false, nullable=false)
	private boolean db_mandatory_f;
	
	@Column(insertable=false, updatable = false, nullable=false)
	private boolean business_key_f;

    @Column(insertable=false, updatable = false, nullable=false)
	private boolean logical_f;

    @Column(insertable=false, updatable = false, nullable=false)
    private boolean custom_f;

    @Column(insertable=false, updatable = false, nullable=false)
    private boolean perm_val_f;
    
    @Column(insertable=false, updatable = false, nullable=true)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributeForeignKeyPresentation fk_presentation_e; 
    
    @Column(insertable=false, updatable = false, nullable=true)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributeTascView tasc_view_e; 
    
    @Column(insertable=false, updatable = false, nullable=false)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributeCalculated calculated_e;

    @Column(insertable=false, updatable = false, nullable=false)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributePermAuth perm_auth_f;
    
    @Column(insertable=false, updatable = false, nullable=false)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributeEditable edit_e;
        
    @Column(insertable=false, updatable = false, nullable=false)
    @Strategy(value="com.odcgroup.otf.jpa.internal.openjpa.EnumWithValueValueHandler")
    private DictAttributeDateTimeWidget widget_e;
    
    @Column(insertable=false, updatable = false, nullable=false)
    private int subtype_mask;
    
    @Column(insertable=false, updatable = false, nullable=false)
    private int quick_search_mask;
    
    @Column(insertable=false, updatable = false, nullable=false)
    private int search_mask;

    @Column(insertable=false, updatable = false, nullable=false)
    String default_c;

    @Column(insertable=false, updatable = false, nullable=false)
    private short disp_rank_n;

    @Column(insertable=false, updatable = false, nullable=true)
    private Short short_index_n; 
    
	@Column(insertable=false, updatable = false, nullable=false)
	private String entity_attribute;
    
	@Column(insertable=false, updatable = false, nullable=false)
	private String object_attribute;

	@Column(insertable=false, updatable = false, nullable=false)
	private long ref_entity_attribute_dict_id;

	@Column(insertable=false, updatable = false, nullable=false)
	private String enum_attribute;
	
	@Column(insertable=false, updatable = false, nullable=false)
	private String enum_value;

	 @Column(insertable=false, updatable = false, nullable=false)
	 private int max_db_len_n;
	 
	 @Column(insertable=false, updatable = false, nullable=false)
	 private int default_display_len_n;
	 
	 @Column(insertable=false, updatable = false, nullable=false)
     private int multi_language_f;
	// Documented to be "Not yet implemented", not mapped: security_level_e, key_char_c, key_char_c

    // Most likely not needed: prog_n, prog_pk_n
    
    // ----------------------------------------------------------------------
    
	public String getName() {
		return name;
	}
	
	public int getStatus() {
		return xd_status_e;
	}

	public DictEntity getDictEntity() {
		return dictEntity;
	}

	public long getEntityId() {
		return (Long)OpenJPAPerformanceHelper.getAssociationRef(this, "dictEntity");
	}
	
	public DictDataType getDatatype() {
		return datatype;
	}
	
	public String getSQLName() {
		return sqlname_c;
	}

	public boolean isPrimary() {
		return primary_f;
	}

	public boolean isMandatory() {
		return mandatory_f;
	}

	public boolean isBusinessKey() {
		return business_key_f;
	}
	
    public DictEntity getReferencedDictEntity() {
        return referencedDictEntity;
    }
    
    public DictAttribute getParentAttribute() {
        return parentAttribute;
    }
    
    /**
     * Better-performing (never lazy-loads) shortcut to getParentAttribute().getDictId().
     * @return the dict_id of the parentAttribute (may be null!)
     */
    public Long getParentAttributeDictId() {
        Long parent_attribute_dict_id = (Long) OpenJPAPerformanceHelper.getAssociationRef(this, "parentAttribute");
        if (parent_attribute_dict_id != null) {
            return parent_attribute_dict_id;
        } else {
            if (this.parentAttribute != null) {
                return this.parentAttribute.getDictID();
            } else {
                return null;
            }
        }
    }
    
    public boolean isMandatoryInDB() {
        return db_mandatory_f;
    }

    public boolean isLogical() {
        return logical_f;
    }
    
    public boolean isCustom() {
        return custom_f;
    }
    
    public boolean isPermVal() {
        return perm_val_f;
    }
    
    public void setIsPermVal(boolean perm_val_f) {
    	this.perm_val_f = perm_val_f;
    }
    
    public DictAttributeForeignKeyPresentation getForeignKeyPresentation() {
        return fk_presentation_e;
    }
    
    public DictAttributeTascView getTascView() {
        return tasc_view_e;
    }

    public DictAttributeCalculated getCalculated() {
        return calculated_e;
    }

    public DictAttributePermAuth getPermAuth() {
        return perm_auth_f;
    }
    
    public DictAttributeEditable getEditable() {
        return edit_e;
    }
    
    public DictAttributeDateTimeWidget getDateTimeWidget() {
        return widget_e;
    }
    
    public int getSubtypeMask() {
        return subtype_mask;
    }
    
    public int getQuickSearchMask() {
        return quick_search_mask;
    }

    
    public int getSearchMask() {
        return search_mask;
    }

    
    public String getDefault() {
        return default_c;
    }

    
    public short getDispRank() {
        return disp_rank_n;
    }

	public Short getShortIndex() {
		return short_index_n;
	}

	public long getDictID() {
		return dict_id;
	}
	
	public String getEntityAttribute() {
		return entity_attribute;
	}	

	public String getObjectAttribute() {
		return object_attribute;
	}	

	public long getRefEntityAttributeDictId() {
		return ref_entity_attribute_dict_id;
	}	
	
	public String getEnumAttribute() {
		return enum_attribute;
	}	
	
	public String getEnumValue() {
		return enum_value;
	}		
	
	/**
	 * @return the dictPermValues, never null.
	 */
	public List<DictPermValue> getPermValues() {
		return dictPermValues;
	}

	public int getMaxDbLen() {
		return max_db_len_n;
	}
	
	public int getDefaultDisplayLen() {
		return default_display_len_n;
	}
	
	public int getMultiLangF() {
		return multi_language_f;
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
	        if (value instanceof DictEntity) {
	          value = ((DictEntity)value).getSQLName();
	        }
            if (value instanceof DictDataTypeEntity) {
                value = ((DictDataTypeEntity)value).getName();
            }
	        buffer.append(value);
	      }
	};
	
	public String toString()
	{
	    // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
	    return ToStringBuilder.reflectionToString(this, TSS);
	}

    // ----------------------------------------------------------------------
	// Not be used in real code, but useful for unit tests
	
	public void setReferencedDictEntity(DictEntity referencedDictEntity) {
		this.referencedDictEntity = referencedDictEntity;
	}

	public void setSQLName(String sqlName) {
		this.sqlname_c = sqlName;
	}

	public void setDictEntity(DictEntity mainDictEntity) {
		this.dictEntity = mainDictEntity;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDatatype(DictDataType datatype) {
		this.datatype = datatype;
	}

	public void setCalculated(DictAttributeCalculated calculated) {
		this.calculated_e = calculated;
	}
	
}
