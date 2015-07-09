
package com.odcgroup.t24.applicationimport.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.odcgroup.t24.applicationimport.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.odcgroup.t24.applicationimport.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MetaT24 }
     * 
     */
    public MetaT24 createMetaT24() {
        return new MetaT24();
    }

    /**
     * Create an instance of {@link DomainServiceResponse }
     * 
     */
    public DomainServiceResponse createDomainServiceResponse() {
        return new DomainServiceResponse();
    }

    /**
     * Create an instance of {@link DomainServiceRequest }
     * 
     */
    public DomainServiceRequest createDomainServiceRequest() {
        return new DomainServiceRequest();
    }

    /**
     * Create an instance of {@link Translation }
     * 
     */
    public Translation createTranslation() {
        return new Translation();
    }

    /**
     * Create an instance of {@link FieldValidValue }
     * 
     */
    public FieldValidValue createFieldValidValue() {
        return new FieldValidValue();
    }

    /**
     * Create an instance of {@link ApplicationEntity }
     * 
     */
    public ApplicationEntity createApplicationEntity() {
        return new ApplicationEntity();
    }

    /**
     * Create an instance of {@link ApplicationField }
     * 
     */
    public ApplicationField createApplicationField() {
        return new ApplicationField();
    }

    /**
     * Create an instance of {@link Module }
     * 
     */
    public Module createModule() {
        return new Module();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link ApplicationReference }
     * 
     */
    public ApplicationReference createApplicationReference() {
        return new ApplicationReference();
    }

}
