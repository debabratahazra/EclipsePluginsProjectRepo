package com.odcgroup.aaa.connector.internal.metadictreader;

import java.util.Collection;
import java.util.Set;

import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.internal.nls.Language;


/**
 * A MetaDictRepository which includes labels for all Entities, Attributes and PermValues.
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictWithLabelsRepository extends MetaDictRepository {

    private final Set<Language> languages;

    /* package-local */
    MetaDictWithLabelsRepository(Collection<DictEntity> entities, Set<Language> languages, TAVersion taVersion) {
        super(entities, taVersion);
        this.languages = languages;
    }

    public Set<Language> getLanguages() {
        return languages;
    }
}
