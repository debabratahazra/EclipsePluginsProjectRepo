package com.odcgroup.aaa.connector.internal.nls;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.aaa.connector.domainmodel.DictLanguageEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictAttributeLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictEntityLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictPermValueLabelEntity;


/**
 * Triple'A Labels Repository.
 * 
 * Consider this class a "Data Transfer Object": it's an encapsulation of dict_label read from the DB, in the form of an
 * object graph. It's "disconnected" from the database, and could, in theory, e.g. be serialized over the wire to some
 * kind of client.
 * 
 * <p>
 * This class also has a number of "convenience access methods" to retrieve labels.
 * </p>
 * 
 * @author Michael Vorburger (MVO)
 */
public class LabelsRepository {

    private Set<Language> languages;
    private Set<DictPermValueLabelEntity> permValLabels;
    private Set<DictEntityLabelEntity> entityLabels;
    private Set<DictAttributeLabelEntity> attributeLabels;

    /* package-local */
    LabelsRepository(Collection<DictLanguageEntity> languages, List<DictPermValueLabelEntity> permValLabels,
            List<DictEntityLabelEntity> entityLabels, List<DictAttributeLabelEntity> attributeLabels) {
        
        this.languages = new HashSet<Language>(languages);
        this.permValLabels = new HashSet<DictPermValueLabelEntity>(permValLabels);
        this.entityLabels = new HashSet<DictEntityLabelEntity>(entityLabels);
        this.attributeLabels = new HashSet<DictAttributeLabelEntity>(attributeLabels);
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Set<DictPermValueLabelEntity> getPermValLabels() {
        return permValLabels;
    }
    
    public Set<DictEntityLabelEntity> getEntityLabels() {
        return entityLabels;
    }

    public Set<DictAttributeLabelEntity> getAttributeLabels() {
        return attributeLabels;
    }
}
