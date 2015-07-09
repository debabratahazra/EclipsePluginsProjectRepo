package com.odcgroup.aaa.connector.domainmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.odcgroup.aaa.connector.internal.nls.TranslateableAdapter;

/**
 * A T'A DictEntity as JPA Entity.
 *
 * @author Michael Vorburger, Camille Weber
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class DictEntityEntity extends TranslateableAdapter {

    @Id
    @Column(insertable=false, updatable = false, nullable=false)
    private long dict_id;

    @Column(insertable=false, updatable = false, nullable=true)
    private String name;

    @Basic(optional=false)
    @Column(insertable=false, updatable = false, nullable=true)
    private String sqlname_c;

    @Column(insertable=false, updatable = false, nullable=false)
    private boolean logical_f;
    
	@Column(insertable=false, updatable = false, nullable=false)
	private Integer nature_e;

	@Column(insertable=false, updatable = false, nullable=false)
	private Integer xd_status_e;

	@Transient
    // @see MetaDictDAO
    private Set<DictAttribute> attributes = null;

    public DictEntityEntity() {
        attributes = new HashSet<DictAttribute>();
    }

    // For Test Cases only
    public DictEntityEntity(long id) {
        this();
        this.dict_id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<DictAttribute> getSortedAttributes() {
    	
    	return Collections.unmodifiableList(sortEntitesByAttDictId(attributes));
    }

    /**
	 * DS-4074 Sort order necessary when importing attributes from MD
	 *
	 * @param attributes
	 * @return
	 */
	private List<DictAttribute> sortEntitesByAttDictId(Collection<DictAttribute> attributes) {
		List<DictAttribute> entitesAsList = new ArrayList<DictAttribute>(attributes);

		Comparator<DictAttribute> comparator = new Comparator<DictAttribute>() {

			@Override
			public int compare(DictAttribute dictAttribute1, DictAttribute dictAttribute2) {
				long dictId1 = dictAttribute1.getDictID();
				long dictId2 = dictAttribute2.getDictID();
				return Long.valueOf(dictId1).compareTo(dictId2);
			}
		};

    	Collections.sort(entitesAsList, comparator);

		return entitesAsList;
	}

// DS-5136 Do not work with IDs anymore!
//    public long getDictID() {
//        return dict_id;
//    }

    public String getSQLName() {
        return sqlname_c;
    }

    // Only used in unit tests
	public void setSQLName(String sqlName) {
		this.sqlname_c = sqlName;
	}
    
    public boolean isLogical() {
    	return logical_f;
    }
    
	public Integer getNature() {
		return nature_e;
	}

	public Integer getStatus() {
		return xd_status_e;
	}

	private static final ToStringStyle TSS = new ToStringStyle() {
            {
                // This is the constructor of this anonymous inner classes
                setUseShortClassName(false);
                setUseClassName(false);
                setUseIdentityHashCode(true);
            }
            @SuppressWarnings("rawtypes")
			protected void appendDetail(StringBuffer buffer, String fieldName, Collection value) {
                if (fieldName.equals("attributes")) {
                    buffer.append("...(size=" + value.size() + ")");
                }
              }
        };

    public String toString()
    {
        // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
        return ToStringBuilder.reflectionToString(this, TSS);
    }

    /**
	 * @param attribute
	 */
	public void addAttribute(DictAttribute attribute) {
		this.attributes.add(attribute);
	}
}
