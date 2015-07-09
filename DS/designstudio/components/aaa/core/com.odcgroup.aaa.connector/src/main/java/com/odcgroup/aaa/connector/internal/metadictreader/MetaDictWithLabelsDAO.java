package com.odcgroup.aaa.connector.internal.metadictreader;

import java.util.Set;

import javax.persistence.EntityManager;

import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictAttributeLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictEntityLabelEntity;
import com.odcgroup.aaa.connector.domainmodel.subs.label.DictPermValueLabelEntity;
import com.odcgroup.aaa.connector.internal.nls.LabelsDAO;
import com.odcgroup.aaa.connector.internal.nls.LabelsRepository;
import com.odcgroup.aaa.connector.internal.nls.Language;


/**
 * A MetaDictDAO which also loads all the labels for all Entities, Attributes and PermValues.
 * 
 * The split between the pure MetaDictDAO and the MetaDictWithLabelsDAO is historical only, not really used now.
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictWithLabelsDAO extends MetaDictDAO {

    private LabelsDAO labelsDAO;
    private LabelsRepository labels;

    public MetaDictWithLabelsDAO(EntityManager em) {
        super(em);
        labelsDAO = new LabelsDAO(em);
    }

    public MetaDictWithLabelsRepository getMetaDictWithLabels() {
        Set<DictEntity> entities = getEntities();
        
        labels = labelsDAO.getAllLabels();
        Set<Language> languages = labels.getLanguages();
        wireLabels();
        
        return new MetaDictWithLabelsRepository(entities, languages, getTAVersion());
    }

    private void wireLabels() {
        int nLanguages = labels.getLanguages().size();
        for (DictPermValueLabelEntity permValueLabel : labels.getPermValLabels()) {
            permValueLabel.getForPermValue().addTranslatedLabel(permValueLabel.getLanguage(), permValueLabel.getName(), nLanguages);
        }
        for (DictEntityLabelEntity entityLabel : labels.getEntityLabels()) {
            entityLabel.getForEntity().addTranslatedLabel(entityLabel.getLanguage(), entityLabel.getName(), nLanguages);
        }
        for (DictAttributeLabelEntity attributeLabel : labels.getAttributeLabels()) {
        	if (attributeLabel.getName() == null) {
        		throw new IllegalArgumentException("DictAttributeLabel name == null, for: " + attributeLabel.getDictLabelId().toString());
        	}
           attributeLabel.getForAttribute().addTranslatedLabel(attributeLabel.getLanguage(), attributeLabel.getName(), nLanguages);
        }
    }
}
