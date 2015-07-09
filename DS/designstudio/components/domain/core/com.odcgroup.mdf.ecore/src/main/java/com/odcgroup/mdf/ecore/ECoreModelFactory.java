package com.odcgroup.mdf.ecore;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.otf.xml.XMLNameFormat;


public class ECoreModelFactory {

    private static final XMLNameFormat XNF = XMLNameFormat.getDefault();
    public static final ECoreModelFactory INSTANCE = new ECoreModelFactory();

    protected ECoreModelFactory() {
    }

    public void setDocumentation(MdfModelElement model, String documentation) {
        if (documentation != null) {
            documentation = documentation.trim();

            if (documentation.length() > 0) {
                ((MdfModelElementImpl) model).setDocumentation(documentation);
                return;
            }
        }

        ((MdfModelElementImpl) model).setDocumentation(null);
    }

    public boolean isModelFile(String filename) {
        int dot = filename.lastIndexOf('.');

        if (dot > -1) {
            String ext = filename.substring(dot);
            return ext.equals(".domain") || ext.equals(".mml");
        }

        return false;
    }

    public String getModelFileName(MdfDomain domain) {
        return XNF.format(domain.getName()) + ".mml";
    }

    public void setReverseAssociation(MdfAssociation assoc,
            MdfReverseAssociation reverse) {
        ((MdfAssociationImpl) assoc).setReverse(reverse);
    }

    public boolean addAnnotation(MdfModelElement model, MdfAnnotation annotation) {
        return ((MdfModelElementImpl) model).getAnnotations().add(annotation);
    }

    public boolean addAnnotationProperty(MdfAnnotation annot, String name,
            String value) {
        MdfAnnotationProperty prop = createMdfAnnotationProperty(annot, name,
                value);
        return ((MdfAnnotationImpl) annot).getProperties().add(prop);
    }

    public boolean addAnnotationProperty(MdfAnnotation annot,
            MdfAnnotationProperty prop) {
        return ((MdfAnnotationImpl) annot).getProperties().add(prop);
    }

    public boolean addDataset(MdfDomain domain, MdfDataset dataset) {
        return ((MdfDomainImpl) domain).getDatasets().add(dataset);
    }

    public boolean addDatasetProperty(MdfDataset parentDataset,
            MdfDatasetProperty property) {
        return ((MdfDatasetImpl) parentDataset).getProperties().add(property);
    }

    public boolean addEntity(MdfDomain domain, MdfEntity entity) {
        if (entity instanceof MdfClass) {
            return ((MdfDomainImpl) domain).getClasses().add(entity);
        } else if (entity instanceof MdfDataset) {
            return ((MdfDomainImpl) domain).getDatasets().add(entity);
        } else if (entity instanceof MdfEnumeration) {
            return ((MdfDomainImpl) domain).getEnumerations().add(entity);
        } else if (entity instanceof MdfBusinessType) {
            return ((MdfDomainImpl) domain).getBusinessTypes().add(entity);
        } else if (entity instanceof MdfPrimitive) {
            return ((MdfDomainImpl) domain).getPrimitives().add(entity);
        } else {
            return false;
        }
    }

    public boolean addEnumValue(MdfEnumeration parentEnumeration,
            MdfEnumValue value) {
        return ((MdfEnumerationImpl) parentEnumeration).getValues().add(value);
    }

    public boolean addProperty(MdfClass klass, MdfProperty prop) {
        return ((MdfClassImpl) klass).getProperties().add(prop);
    }

    public void copy(MdfAssociation source, MdfAssociation target) {
        MdfAssociationImpl assoc = (MdfAssociationImpl) target;
        assoc.setName(source.getName());
        assoc.setType((MdfClass) source.getType());
        assoc.setContainment(source.getContainment());
        assoc.setMultiplicity(source.getMultiplicity());
        assoc.setBusinessKey(source.isBusinessKey());
        assoc.setRequired(source.isRequired());
        assoc.setPrimaryKey(source.isPrimaryKey());
        handleCrossReferences((MdfClassImpl)assoc.getParentClass());
    }

    public void copy(MdfAttribute source, MdfAttribute target) {
        MdfAttributeImpl attr = (MdfAttributeImpl) target;
        attr.setName(source.getName());
        attr.setType((MdfPrimitive) source.getType());
        attr.setDefault(source.getDefault());
        attr.setPrimaryKey(source.isPrimaryKey());
        attr.setBusinessKey(source.isBusinessKey());
        attr.setRequired(source.isRequired());
        attr.setMultiplicity(source.getMultiplicity());
        handleCrossReferences((MdfClassImpl)attr.getParentClass());
    }
    
    /**
     * handle the cross references of mdfclass in the same-domain
     * 
     * @param mdfClass
     */
    private void handleCrossReferences(MdfClassImpl mdfClass) {
    	Collection<Setting> refs = EcoreUtil.UsageCrossReferencer.find(mdfClass, mdfClass.eContainer());
    	for (Setting setting : refs) {
    		EObject eObject = setting.getEObject();
			if (eObject instanceof MdfDatasetImpl) {
				MdfDatasetImpl ds = (MdfDatasetImpl) eObject;
				ds.setBaseClass(mdfClass);
			} else if (eObject instanceof MdfClassImpl) {
				MdfClassImpl klass = (MdfClassImpl) eObject;
				klass.setBaseClass(mdfClass);
				handleCrossReferences(klass);
			} else if (eObject instanceof MdfAssociationImpl){
				MdfAssociationImpl assoc = (MdfAssociationImpl) eObject;
				assoc.setType(mdfClass);
			}
		}
    }

    public void copy(MdfClass source, MdfClass target) {
        MdfClassImpl klass = (MdfClassImpl) target;
        klass.setName(source.getName());
        klass.setBaseClass(source.getBaseClass());
        klass.setAbstract(source.isAbstract());
        handleCrossReferences(klass);
    }

    public void copy(MdfDataset source, MdfDataset target) {
        MdfDatasetImpl dataset = (MdfDatasetImpl) target;
        dataset.setName(source.getName());
        dataset.setBaseClass(source.getBaseClass());
        dataset.setLinked(source.isLinked());
        dataset.setSync(source.isSync());
    }

    public void copy(MdfDomain source, MdfDomain target) {
        MdfDomainImpl domain = (MdfDomainImpl) target;
        domain.setName(source.getName());
        domain.setNamespace(source.getNamespace());
    }

    public void copy(MdfEnumeration source, MdfEnumeration target) {
        MdfEnumerationImpl enumeration = (MdfEnumerationImpl) target;
        enumeration.setName(source.getName());
        enumeration.setType(source.getType());
        //DS-706
        enumeration.setAcceptNullValue(source.isAcceptNullValue());
    }

    public void copy(MdfEnumValue source, MdfEnumValue target) {
        MdfEnumValueImpl value = (MdfEnumValueImpl) target;
        value.setName(source.getName());
        value.setValue(source.getValue());
    }

    public void copy(MdfReverseAssociation source, MdfReverseAssociation target) {
        MdfReverseAssociationImpl reverse = (MdfReverseAssociationImpl) target;
        reverse.setName(source.getName());
        reverse.setMultiplicity(source.getMultiplicity());
        handleCrossReferences((MdfClassImpl)reverse.getParentClass());
    }

    public MdfAnnotation createMdfAnnotation(String namespace, String name) {
        MdfAnnotationImpl annot = new MdfAnnotationImpl();
        annot.setNamespace(namespace);
        annot.setName(name);
        return annot;
    }

    public MdfAnnotation createMdfAnnotation(String namespace, String name,
            String value) {
        MdfAnnotationImpl annot = (MdfAnnotationImpl) createMdfAnnotation(
                namespace, name);
        MdfAnnotationProperty prop = createMdfAnnotationProperty(annot,
                "value", value);
        annot.getProperties().add(prop);
        return annot;
    }

    public MdfAnnotationProperty createMdfAnnotationProperty(
            MdfAnnotation annot, String name, String value) {       
        return createMdfAnnotationProperty(annot, name, value, false);
    }

	
	public MdfAnnotationProperty createMdfAnnotationProperty(
			MdfAnnotation annot, String name, String value, boolean cData) {
        MdfAnnotationPropertyImpl p = new MdfAnnotationPropertyImpl();
        p.setName(name);
        p.setValue(value);
        p.setCDATA(cData);
		return p;
	}

    public MdfAssociation createMdfAssociation(MdfClass parentClass) {
        return MdfFactoryImpl.eINSTANCE.createMdfAssociation();
    }

    public MdfAttribute createMdfAttribute(MdfClass parentClass) {
        return MdfFactoryImpl.eINSTANCE.createMdfAttribute();
    }

    public MdfClass createMdfClass(MdfDomain parentDomain) {
        return MdfFactoryImpl.eINSTANCE.createMdfClass();
    }

    public MdfDataset createMdfDataset(MdfDomain parentDomain) {
        return MdfFactoryImpl.eINSTANCE.createMdfDataset();
    }

    public MdfDataset createMdfDataset(MdfDomain parentDomain,
            MdfClass baseClass) {
        MdfDatasetImpl dataset = (MdfDatasetImpl) MdfFactoryImpl.eINSTANCE.createMdfDataset();
        dataset.setBaseClass(baseClass);
        return dataset;
    }

    public MdfDomain createMdfDomain() {
        return MdfFactoryImpl.eINSTANCE.createMdfDomain();
    }

    public MdfEnumValue createMdfEnumValue(MdfEnumeration parentEnumeration) {
        return MdfFactoryImpl.eINSTANCE.createMdfEnumValue();
    }

    public MdfEnumeration createMdfEnumeration(MdfDomain parentDomain) {
        return MdfFactoryImpl.eINSTANCE.createMdfEnumeration();
    }

    public MdfReverseAssociation createMdfReverseAssociation(
            MdfAssociation parentAssociation) {
        return MdfFactoryImpl.eINSTANCE.createMdfReverseAssociation();
    }

    public void removeAnnotations(MdfModelElement model, String namespace) {
        MdfModelElementImpl emodel = (MdfModelElementImpl) model;
        Iterator it = emodel.getAnnotations().iterator();

        while (it.hasNext()) {
            MdfAnnotation annot = (MdfAnnotation) it.next();

            if ((annot.getNamespace() != null)
                    && annot.getNamespace().equals(namespace)) {
                it.remove();
            }
        }
    }

    public void copy(MdfDatasetMappedProperty source,
            MdfDatasetMappedProperty target) {
        MdfDatasetMappedPropertyImpl prop = (MdfDatasetMappedPropertyImpl) target;
        prop.setName(source.getName());
        prop.setPath(source.getPath());
        prop.setUnique(source.isUnique());
        prop.setSingleValued(source.isSingleValued());

        MdfEntity type = source.getType();

        if (type instanceof MdfDataset) {
            prop.setLinkDataset((MdfDataset) type);
        } else {
            prop.setLinkDataset(null);
        }
    }

    public void copy(MdfDatasetDerivedProperty source,
            MdfDatasetDerivedProperty target) {
        MdfDatasetDerivedPropertyImpl prop = (MdfDatasetDerivedPropertyImpl) target;
        prop.setName(source.getName());
        prop.setType(source.getType());
        prop.setMultiplicity(source.getMultiplicity());
        prop.setDefault(source.getDefault());
    }

    public MdfDatasetDerivedProperty createMdfDatasetDerivedProperty(
            MdfDataset parentDataset) {
        return MdfFactoryImpl.eINSTANCE.createMdfDatasetDerivedProperty();
    }

    public MdfDatasetMappedProperty createMdfDatasetMappedProperty(
            MdfDataset parentDataset) {
        return MdfFactoryImpl.eINSTANCE.createMdfDatasetMappedProperty();
    }

    public void copy(MdfBusinessType source, MdfBusinessType target) {
        MdfBusinessTypeImpl enumeration = (MdfBusinessTypeImpl) target;
        enumeration.setName(source.getName());
        enumeration.setType(source.getType());
    }

    public MdfBusinessType createMdfBusinessType(MdfDomain parentDomain) {
        return new MdfBusinessTypeImpl();
    }

}
