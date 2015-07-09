package com.odcgroup.cdm.generation.custo.generator;

import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_ANNOTATION_NAME;
import static com.odcgroup.cdm.generation.util.CdmConstants.CUSTOM_FIELD_NAMESPACE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.odcgroup.cdm.generation.util.OcsMessagesReader;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * Imports the translations for custom fields.
 * 
 * @author Gary Hayes
 */
public class CustomFieldsTranslationsReader {
    
    /** The domain model. */
    private MdfDomain domain;   
    
    /** The Ocs messages. */
    private OcsMessagesReader ocsMessages;
    
    /** The locale. */
//    private String locale;
    
    /** The list of messages. */
    private List<String> messages;
    
    /**
     * Creates a new CustomFieldsTranslationsReader.
     * 
     * @param ocsMessagesFileName The name of the messages.xml file
     * @param domain The domain model
     * @param projectName The name of the project to import the translations to
     */
    public CustomFieldsTranslationsReader(String ocsMessagesFileName, MdfDomain domain, String projectName) {
        this.domain = domain;
        
//        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        
        ocsMessages = new OcsMessagesReader(ocsMessagesFileName);
//        locale = ocsMessages.getLocale().substring(0, 2);
        
        messages = new ArrayList<String>();
    }
    
    /**
     * Reads the translations and adds them to the message repository.
     */
	@SuppressWarnings("rawtypes")
	public void read() {
        for(Iterator it = domain.getEntities().iterator(); it.hasNext(); ) {
            MdfEntity e = (MdfEntity) it.next();
            if (! (e instanceof MdfClass)) {
                continue;
            }
            MdfClass clazz = (MdfClass) e;
            read(clazz);
        }
        
    }
    
    /**
     * Gets the messages generated during the import.
     * 
     * @return List of messages
     */
    public List<String> getMessages() {
        return messages;
    }   
    
    /**
     * Reads the translations for a single MdfClass.
     * 
     * @param clazz The MdfClass to read the translations for
     */
    @SuppressWarnings("unchecked")
	private void read(MdfClass clazz) {
        for (Iterator it = clazz.getProperties().iterator(); it.hasNext(); ) {
            MdfProperty p = (MdfProperty) it.next();
            read(p);

        }
    }
    
    /**
     * Reads the translation for the specified MdfProperty.
     * 
     * @param property The property to read the translation for
     */
    private void read(MdfProperty property) {
        MdfAnnotation ca = property.getAnnotation(CUSTOM_FIELD_NAMESPACE, CUSTOM_FIELD_ANNOTATION_NAME);
        if (ca == null) {
            // Not a custom field
            return;
        }
        
        String key = "right." + property.getName();
        String value = ocsMessages.getMessages().get(key);
        if (value == null) {
            messages.add("Unable to find the key for " + property.getName());
            return;
        }
        
        MdfClass c = property.getParentClass();
        String newKey = c.getParentDomain().getName() + "." + c.getName() + "." + property.getName() + ".text";
        newKey = newKey.toLowerCase();

    } 
}
