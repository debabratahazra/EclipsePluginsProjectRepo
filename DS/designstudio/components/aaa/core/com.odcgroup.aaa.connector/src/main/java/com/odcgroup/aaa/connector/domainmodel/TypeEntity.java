package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.odcgroup.otf.jpa.internal.openjpa.OpenJPAPerformanceHelper;


/**
 * Type.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "Type")
@Table(name = "type_vw")
public class TypeEntity {

    @Id
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_dict_id", nullable = false)
    private DictAttribute attribute;

    public static final long FORMAT_TYPE_DICTID = 1202018;
    public static final String FORMAT_TYPE_DICTID_STRING = "1202018";
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String code;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic
    private String denom;

    @Basic(optional = false)
    @Column(name = "rank_n", nullable = false)
    // NOTE This rank_n is actually used e.g. in FormatDAO ALLTYPES_JPQL
    private int rank;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_type_id")
    private TypeEntity parentType;

    @Basic(optional = false)
    @Column(name = "autocreated_f", nullable = false)
    private boolean autocreated;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the denom
     */
    public String getDenom() {
        return denom;
    }

    /**
     * @return the parentType
     */
    public TypeEntity getParentType() {
        return parentType;
    }

    /**
     * @return the autocreated
     */
    public boolean isAutocreated() {
        return autocreated;
    }

    /**
     * @return the attribute
     */
    public DictAttribute getAttribute() {
        return attribute;
    }

    /**
     * Better-performing (never lazy-loads) shortcut to getAttribute().getDictId().
     * @return the dict_id of the attribute
     */
    public long getAttributeId() {
        Long attribute_dict_id = (Long) OpenJPAPerformanceHelper.getAssociationRef(this, "attribute");
        if (attribute_dict_id != null) {
            return attribute_dict_id;
        } else {
            return this.attribute.getDictID();
        }
    }

	public int getRank() {
		return rank;
	}

    // instr_nat_e, oper_nat_e, sell_position_f better into a subclass of this class, if ever needed.

    // ud_id?
}
