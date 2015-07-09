package com.odcgroup.aaa.connector.internal.nls;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.odcgroup.aaa.connector.domainmodel.DictLanguageEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictAttributeLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictEntityLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictPermValueLabelEntity;


/**
 * The Data Access Object Assembler for T'A MetaDict Labels.
 * 
 * @author Michael Vorburger (MVO)
 */
public class LabelsDAO {

    private EntityManager em;

    /**
     * DAO Constructor.
     * 
     * @param em EntityManager to retrieve the T'A MetaDict from
     */
    public LabelsDAO(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
    public LabelsRepository getAllLabels() {
        Collection<DictLanguageEntity> langs = em.createQuery("SELECT l FROM DictLanguage l").getResultList();
        
        List<DictPermValueLabelEntity> permValLabels = em.createQuery("SELECT l FROM DictPermValueLabel l").getResultList();
        
        List<DictEntityLabelEntity> entityLabels = em.createQuery("SELECT l FROM DictEntityLabel l").getResultList();
        
        List<DictAttributeLabelEntity> attrLabels = em.createQuery("SELECT l FROM DictAttributeLabel l").getResultList();
        
        return new LabelsRepository(langs, permValLabels, entityLabels, attrLabels);
    }
}
