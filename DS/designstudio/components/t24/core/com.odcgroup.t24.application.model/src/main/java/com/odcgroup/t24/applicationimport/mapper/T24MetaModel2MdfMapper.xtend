package com.odcgroup.t24.applicationimport.mapper

import com.google.inject.Inject
import com.odcgroup.domain.annotations.JavaAspectDS
import com.odcgroup.domain.resource.DomainURIEncoder
import com.odcgroup.mdf.ecore.MdfAssociationImpl
import com.odcgroup.mdf.ecore.MdfAttributeImpl
import com.odcgroup.mdf.ecore.MdfClassImpl
import com.odcgroup.mdf.ecore.MdfDomainImpl
import com.odcgroup.mdf.ecore.MdfEnumValueImpl
import com.odcgroup.mdf.ecore.MdfEnumerationImpl
import com.odcgroup.mdf.ecore.MdfFactory
import com.odcgroup.mdf.ecore.MdfPackage
import com.odcgroup.mdf.ecore.MdfPropertyImpl
import com.odcgroup.mdf.ecore.PrimitivesDomain
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.mdf.metamodel.MdfContainment
import com.odcgroup.mdf.metamodel.MdfModelElement
import com.odcgroup.mdf.metamodel.MdfMultiplicity
import com.odcgroup.mdf.metamodel.MdfProperty
import com.odcgroup.mdf.utils.StringUtils
import com.odcgroup.domain.validation.JavaKeywordChecker
import com.odcgroup.t24.applicationimport.T24Aspect
import com.odcgroup.t24.applicationimport.schema.ApplicationEntity
import com.odcgroup.t24.applicationimport.schema.ApplicationField
import com.odcgroup.t24.applicationimport.schema.FieldType
import com.odcgroup.t24.applicationimport.schema.FieldValidValue
import com.odcgroup.t24.applicationimport.schema.Translation
import com.odcgroup.translation.core.ITranslation
import com.odcgroup.translation.core.ITranslationKind
import com.odcgroup.translation.core.TranslationCore
import java.util.HashSet
import java.util.List
import org.apache.commons.lang.LocaleUtils
import org.eclipse.core.resources.IProject
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.util.InternalEList
import org.eclipse.xtext.linking.lazy.LazyLinker
import org.eclipse.xtext.util.EcoreGenericsUtil
import org.eclipse.xtext.util.SimpleCache
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension com.odcgroup.t24.applicationimport.T24Aspect.*

/**
 * Maps T24 Components/Modules/Applications into DS Domain.
 * 
 * Implementation note: This class "isn't" (as in OOP IS-A) a LazyLinker,
 * as in linkModel() never gets called on it; it only extends LazyLinker
 * so that its protected findInstantiableCompatible() helper util can be
 * used here.
 * 
 * @author kosyakov (for Michael Vorburger) - adapted to use new infra instead of using MdfEcoreUtil, @see http://rd.oams.com/browse/DS-7424
 */
class T24MetaModel2MdfMapper extends LazyLinker {
	private static Logger LOGGER = LoggerFactory::getLogger(typeof(T24MetaModel2MdfMapper))

	val instantiableSubTypes = new SimpleCache<EClass, EClass> [
		findInstantiableCompatible
	]

	var IProject project

	@Inject
	DomainURIEncoder domainURIEncoder

	@Inject
	EcoreGenericsUtil ecoreGenericsUtil

	def create mdfDomain: MdfFactory::eINSTANCE.createMdfDomain as MdfDomainImpl mapInternal(String componentName,
		String moduleName, IProject project) throws MappingException {
		this.project = project
		val mdfDomainImpl = (mdfDomain as MdfDomainImpl)
		if (componentName == null || componentName.trim.isEmpty)
			throwMappingError("No Component Name - BUG in T24 XML");
		mdfDomainImpl.name = componentName

		mdfDomainImpl.namespace = "http://www.temenos.com/t24/" + componentName

		mdfDomainImpl.module = moduleName

		// DS-7233
		if (JavaKeywordChecker::getInstance().isKeyword(moduleName.toLowerCase())) {
			val javaPackage = "com.temenos.t24.datamodel." + moduleName.toLowerCase() + "_." + componentName.toLowerCase
			JavaAspectDS::setPackage(mdfDomainImpl, javaPackage)
		} else {
			val javaPackage = "com.temenos.t24.datamodel." + moduleName.toLowerCase + "." + componentName.toLowerCase
			JavaAspectDS::setPackage(mdfDomainImpl, javaPackage)
		}
	}

	def map(String componentName, String moduleName, IProject project) throws MappingException {
		mapInternal(componentName, moduleName, project)
	}

	def map(List<ApplicationEntity> appEntityList, MdfDomainImpl domain, IProject project) throws MappingException {
		this.project = project
		for (application : appEntityList) {
			application.map(domain)
		}
	}

	def create mdfClass: MdfFactory::eINSTANCE.createMdfClass mapInternal(ApplicationEntity application,
		MdfDomainImpl domain) throws MappingException {
		var mdfClassImpl = ( mdfClass as MdfClassImpl )
		mdfClassImpl.name = application.name.toValidName
		if (application.nameT24 != null)
			mdfClassImpl.t24Name = application.nameT24
		else
			mdfClassImpl.t24Name = application.name
		var MdfClassImpl duplicateClazz
		for (mdfClazz : domain.classes) {
			if ((mdfClazz as MdfClass).name.equals(mdfClassImpl.name)) {
				duplicateClazz = mdfClazz as MdfClassImpl
				duplicateClazz.t24Name = mdfClassImpl.t24Name
			}
		}
		if (duplicateClazz != null) {
			duplicateClazz.properties.clear
			duplicateClazz.properties.addAll(mdfClassImpl.properties)
			mdfClassImpl = duplicateClazz
		} else {
			domain.classes += mdfClassImpl
		}
		mdfClassImpl.documentation = application.documentation
		mdfClassImpl.nonStop = application.nonStop
		mdfClassImpl.level = application.level
		mdfClassImpl.type = application.type
		mdfClassImpl.additionalInfo = application.additionalInfo
		mdfClassImpl.allowedFunctions = application.allowedFunctions
		var localRefAllowed = false
		for (field : application.fields) {
			var MdfModelElement mdfModelelement

			//check if the field is T24 Technical filed .if T24 Technical filed skip to creat field.
			if (!localRefAllowed && field.name.toValidName.equals("LOCAL_REF")) {
				localRefAllowed = true
			}
			if (!field.name.toValidName.equals("LOCAL_REF")) {
				if (field.multiValueGroupName != null) {
					field.mapMultiValue(application, mdfClassImpl, domain)
				} else if (field.multiValueGroupName == null && field.subValueGroupName != null) {

					// This should normally never happen... it would be major bug in the code which produced the T24 XML
					throwMappingError(
						application.name + "#" + field.name +
							" has a subValueGroupName but no multiValueGroupName - BUG in T24 XML");
				} else { // Simple Field (not [Sub]MultiValue)
					mdfModelelement = field.map(application, mdfClassImpl, domain)
					if (mdfModelelement != null && !isDuplicateProperty(mdfClassImpl, mdfModelelement.name)) {
						mdfClassImpl.properties += mdfModelelement
						field.installProxies(mdfModelelement)

						//set the label and tooltip translation to the fields.
						mdfModelelement.mapTranslation(field)
					}
				}
			}
		}

		//set the header1 and header2 translations to the Mdfclass.
		mdfClassImpl.mapTranslation(application)

		//set the localRefAllowed annotation to the MdfClass
		mdfClassImpl.setLocalRefAllowed(localRefAllowed)
	}

	def map(ApplicationEntity application, MdfDomainImpl domain) throws MappingException {
		mapInternal(application, domain)
	}

	def create mdfProperty: MdfFactory::eINSTANCE.createMdfClass mapMultiValue(ApplicationField field,
		ApplicationEntity application, MdfClassImpl mdfMainClass, MdfDomainImpl domain) {
		val multiValueClassName = ( application.name + "__" + field.multiValueGroupName ).toValidName
		var multiValueClass = mapMultiOrSubValueClass(multiValueClassName, field.multiValueGroupName.toValidName,
			mdfMainClass, domain)
		if (field.subValueGroupName != null) {
			val subValueClassName = ( application.name + "__" + field.multiValueGroupName + "__" +
				field.subValueGroupName ).toValidName
			var subValueClass = mapMultiOrSubValueClass(subValueClassName, field.subValueGroupName.toValidName,
				multiValueClass, domain)
			var mdfModelElement = field.map(application, subValueClass, domain)
			if (mdfModelElement != null && !isDuplicateProperty(subValueClass, mdfModelElement.name)) {
				subValueClass.properties += mdfModelElement
				field.installProxies(mdfModelElement)
			}
			mdfModelElement.mapTranslation(field)
		} else {
			var mdfModelElement = field.map(application, multiValueClass, domain)
			if (mdfModelElement != null && !isDuplicateProperty(multiValueClass, mdfModelElement.name)) {
				multiValueClass.properties += mdfModelElement
				field.installProxies(mdfModelElement)
			}
			mdfModelElement.mapTranslation(field)
		}
	}

	def create embeddedClass: MdfFactory::eINSTANCE.createMdfClass as MdfClassImpl mapMultiOrSubValueClassInternal(
		String embeddedMultiOrSubValueClassName, String embeddedMultiOrSubValuePropertyName,
		MdfClassImpl mdfMainClass, MdfDomainImpl mdfDomain) {
		val embeddedClassImpl = embeddedClass as MdfClassImpl
		embeddedClassImpl.name = embeddedMultiOrSubValueClassName
		if (!isDuplicateProperty(mdfMainClass, embeddedMultiOrSubValuePropertyName)) {
			var mdfAssociation = MdfFactory::eINSTANCE.createMdfAssociation as MdfAssociationImpl
			mdfAssociation.name = embeddedMultiOrSubValuePropertyName
			mdfAssociation.type = embeddedClass
			mdfAssociation.containment = MdfContainment::BY_VALUE
			mdfAssociation.multiplicity = MdfMultiplicity::MANY_LITERAL
			mdfMainClass.properties += mdfAssociation
		}
		var MdfClassImpl duplicateClazz

		//new check 
		for (mdfClass : mdfDomain.classes) {
			if ((mdfClass as MdfClassImpl).name.equals(embeddedClass.name)) {
				duplicateClazz = mdfClass as MdfClassImpl
			}
		}

		if (duplicateClazz != null) {
			mdfDomain.classes.remove(duplicateClazz)
		}
		mdfDomain.classes += embeddedClass
	}

	def mapMultiOrSubValueClass(String embeddedMultiOrSubValueClassName, String embeddedMultiOrSubValuePropertyName,
		MdfClassImpl mdfMainClass, MdfDomainImpl mdfDomain) {
		mapMultiOrSubValueClassInternal(embeddedMultiOrSubValueClassName, embeddedMultiOrSubValuePropertyName,
			mdfMainClass, mdfDomain)
	}

	def map(ApplicationField applicationField, ApplicationEntity application, MdfClass mdfClass, MdfDomainImpl mdfDomain) {
		var MdfEnumerationImpl enumeration
		var validValues = applicationField.validValue
		var MdfProperty property
		if (validValues.size > 0 && applicationField.refApplication == null) {
			var enumName = (mdfClass.name + "__" + applicationField.name).toValidName
			enumeration = validValues.map(enumName, applicationField, application)
			var MdfEnumerationImpl duplicateEnum

			//new check
			for (mdfEnum : mdfDomain.enumerations) {
				if ((mdfEnum as MdfEnumerationImpl).name.equals(enumName)) {
					duplicateEnum = (mdfEnum as MdfEnumerationImpl)
				}
			}

			if (duplicateEnum != null) {
				if (mdfDomain.getClass(mdfClass.name) != null)
					property = mdfDomain.getClass(mdfClass.name).getProperty(applicationField.name)
				duplicateEnum.values.clear
				duplicateEnum.values.addAll(enumeration.values)
				enumeration = duplicateEnum
			} else {
				mdfDomain.enumerations += enumeration
			}
		}
		if (property != null) {
			(property as MdfAttributeImpl).type = enumeration
		} else if (applicationField.refApplication == null) {
			var mdfAttribute = MdfFactory::eINSTANCE.createMdfAttribute
			var mdfAttributeImpl = (mdfAttribute as MdfAttributeImpl)
			setFieldCommon(mdfAttributeImpl, applicationField)
			if (enumeration != null) {
				mdfAttributeImpl.type = enumeration
			} else {
				mdfAttributeImpl.type = applicationField.mapType
			}

			//set the t24 name as annotation to the model elements.
			mapT24Name(mdfAttributeImpl, applicationField);

			//set the gen-operation as annotation.
			mapGenOperation(mdfAttributeImpl, applicationField);

			//set the  properties to the annotation
			setAnnotations(mdfAttributeImpl, applicationField)
			mdfAttributeImpl
		} else if (applicationField.refApplication.component != null) {
			var mdfAssociation = MdfFactory::eINSTANCE.createMdfAssociation
			var mdfAssociationImpl = (mdfAssociation as MdfAssociationImpl)
			setFieldCommon(mdfAssociationImpl, applicationField)

			//set the t24 name as annotation to the model elements.
			mapT24Name(mdfAssociationImpl, applicationField);

			//set the gen-operation as annotation.
			mapGenOperation(mdfAssociationImpl, applicationField);

			//set the  properties to the annotation
			setAnnotations(mdfAssociationImpl, applicationField);
			mdfAssociationImpl
		} else {

			// We should never hit this - because the XML validation will already have
			// refused files with the <t24:ref-application> without a component problem
			// Just in case, better safe than sorry, we have the if check above and this else.
			val msg = applicationField.nameT24 +
				" has a <t24:ref-application> without a component, skipping it because otherwise all hell breaks loose in DS";
			throwMappingError(msg)
			LOGGER.error(msg);
		}
	}

	def dispatch void installProxies(ApplicationField applicationField, Object object) {
	}

	def dispatch void installProxies(ApplicationField applicationField, MdfAssociationImpl mdfAssociationImpl) {
		var appRef = applicationField.refApplication
		val typeName = appRef.name.toValidName
		mdfAssociationImpl.createAndSetProxyOrObject(MdfPackage.Literals.MDF_ASSOCIATION__TYPE,
			appRef.component + ":" + typeName);

		//		if (mdfAssociationImpl.createAndSetProxyOrObject(MdfPackage.Literals.MDF_ASSOCIATION__TYPE, appRef.component + ":" + typeName)) {
		switch type : mdfAssociationImpl.basicGetType {
			MdfClassImpl: {
				type.name = typeName
			}
		}

	//		}
	}

	@SuppressWarnings("unchecked")
	protected def createAndSetProxyOrObject(EObject obj, EReference eRef, String crossRefNode) {
		val proxyOrObject = createProxy(obj, eRef, crossRefNode);
		if (eRef.isMany()) {
			((obj.eGet(eRef, false) as InternalEList<EObject>)).addUnique(proxyOrObject)
		} else {
			obj.eSet(eRef, proxyOrObject);
		}
	}

	protected def createProxy(EObject obj, EReference eRef, String crossRefString) {
		val resource = obj.eResource
		if (resource == null)
			throw new IllegalStateException("object must be contained in a resource")
		val uri = resource.URI
		val encodedLink = uri.appendFragment(domainURIEncoder.encodeBrokenLink(crossRefString))
		val referenceType = ecoreGenericsUtil.getReferenceType(eRef, obj.eClass())
		val instantiableType = instantiableSubTypes.get(referenceType)
		val EObject proxy = EcoreUtil.create(instantiableType)
		(proxy as InternalEObject).eSetProxyURI(encodedLink)
		proxy
	}

	def setAnnotations(MdfPropertyImpl property, ApplicationField field) {
		property.typeModifiers = field.typeModifiers;
		property.mask = field.mask;
		property.onchangeBehaviour = field.onchangeBehaviour;
		if (field.mvSvExpansionAccess != null) {
			property.mvSvExpansionAccess = field.mvSvExpansionAccess.name;
		}
		if (field.isTextarea != null) {
			property.textArea = field.isTextarea;
		}
		if (field.isImage != null) {
			property.image = field.isImage;
		}
	}

	def setFieldCommon(MdfPropertyImpl property, ApplicationField field) {
		property.name = field.name.toValidName
		property.primaryKey = field.primaryKey
		property.required = field.mandatory
		property.documentation = field.documentation
		property.alignment = field.alignment
		property.inputBehaviour = field.inputBehaviour
		if (field.maxLength != null) {
			property.maxLength = field.maxLength.intValue
		}
		property.multiLanguage = field.multiLanguage
		if (field.sysNumber != null) {
			property.sysNumber = field.sysNumber
		}
		property.core = field.core
		property.businessType = field.businessType
	}

	//set the t24 name as the annotation.
	def mapT24Name(MdfPropertyImpl property, ApplicationField field) {
		property.t24Name = field.nameT24
	}

	//set the gen-operation as the annotation
	def mapGenOperation(MdfPropertyImpl property, ApplicationField field) {
		property.genOperation = field.genOperation;
	}

	def map(List<FieldValidValue> validValueList, String enumName, ApplicationField field, ApplicationEntity entity) {
		var alreadySeenNames = new HashSet<String>()
		var mdfEnumeration = MdfFactory::eINSTANCE.createMdfEnumeration
		var mdfEnumerationImpl = (mdfEnumeration as MdfEnumerationImpl)
		mdfEnumerationImpl.name = enumName
		for (validValue : validValueList) {
			if (validValue.value != null && validValue.value.trim.length > 0) {
				val validName = validValue.value.toValidEnumValueName
				if (alreadySeenNames.contains(validName)) {
					LOGGER.warn(
						"Application " + entity.name + "'s Field " + field.name + " has duplicate valid value " +
							validValue.value);
				} else {
					var mdfEnumValue = MdfFactory::eINSTANCE.createMdfEnumValue
					var mdfenumValueImpl = (mdfEnumValue as MdfEnumValueImpl)
					mdfenumValueImpl.name = validName
					alreadySeenNames.add(validName)
					mdfenumValueImpl.value = validValue.value
					mdfEnumerationImpl.values += mdfEnumValue
				}
			}
		}
		mdfEnumerationImpl.type = mapType(field)
		mdfEnumerationImpl
	}

	def mapType(ApplicationField field) {
		val FieldType type = field.type

		// val String bt = field.businessType
		switch (type) {
			// Mapping confirmed 2013-03-06 Michael with Sathya
			// DS-6379 Mapping for M changed from Integer to Double based on Andrew's analysis 
			case FieldType::S:
				PrimitivesDomain::STRING
			case FieldType::L:
				PrimitivesDomain::LONG_OBJ // ?
			case FieldType::D:
				PrimitivesDomain::DATE
			case FieldType::B:
				PrimitivesDomain::STRING // The so-called "Boolean" enumerations are "True" and "False" values, so need to be of base type String
			case FieldType::C:
				PrimitivesDomain::CHAR_OBJ
			case FieldType::M:
				PrimitivesDomain::DOUBLE_OBJ
			case FieldType::N:
				PrimitivesDomain::INTEGER_OBJ
			case FieldType::T:
				PrimitivesDomain::DATE_TIME
			// 'I' is for I-Descriptor, but that's handled differently now
			default:
				throw new IllegalArgumentException("FieldType cannot be mapped: " + type.name)
		}
	}

	def toValidEnumValueName(String name) {
		var cleanName = name.trim

		// One of the Enums has something both like X. and X-
		// as Values; to avoid both of them becoming X_
		// this is needed.
		cleanName = cleanName.replace('-', '__');
		toValidName(cleanName)
	}

	def toValidName(String name) {
		var cleanName = name.trim
		cleanName = cleanName.replace('.', '_')

		// Here is a toast to some really weired stuff
		cleanName = cleanName.replace("'AND'", "AND"); // DE_WORDS's field
		cleanName = cleanName.replace("\"", ""); // "FIELD/" TEXT \"" !!
		cleanName = cleanName.replace("@", ""); // @ID

		// The following appear in T24 Valid Values
		// we replace them for our Enum Names
		// (the Enum Values stay as in T24)
		cleanName = cleanName.replace(' ', '_');
		cleanName = cleanName.replace('-', '_');
		cleanName = cleanName.replace(':', '_');
		cleanName = cleanName.replace('/', '_');
		cleanName = cleanName.replace('>', '_GT');
		cleanName = cleanName.replace('<', '_LT');
		cleanName = cleanName.replace(",", "COMMA"); // or better "_COMMA_"  ?
		cleanName = cleanName.replace("*", "STAR"); // or better "_STAR_"   ?
		cleanName = cleanName.replace("=", "EQUALS"); // or better "_EQUALS_" ?
		cleanName = cleanName.replace("~", "TILDE"); // or better "_TILDE_"  ?
		cleanName = cleanName.replace("%", "PERCENT");
		cleanName = cleanName.replace("$", "_DOLLAR_");
		cleanName = cleanName.replace("&", "_AND_");
		cleanName = cleanName.replace("#", "NO");
		cleanName = cleanName.replace('\'', '_'); // "ENTRY_ID'S" 

		// These don't occur in the Test XML, just to be safe for the future:
		cleanName = cleanName.replace(':', '_');
		cleanName = cleanName.replace(';', '_');
		cleanName = cleanName.replace('!', '_');
		cleanName = cleanName.replace('+', '_');
		cleanName = cleanName.replace('§', '_');
		cleanName = cleanName.replace('£', '_');
		cleanName = cleanName.replace('¦', '_');
		cleanName = cleanName.replace('|', '_');
		cleanName = cleanName.replace('\\', '_');

		cleanName = cleanName.replace('(', '_');
		cleanName = cleanName.replace(')', '_');
		cleanName = cleanName.replace('[', '_');
		cleanName = cleanName.replace(']', '_');
		cleanName = cleanName.replace('{', '_');
		cleanName = cleanName.replace('}', '_');
		
		cleanName
	}
	
	def throwMappingError(String message) throws MappingException {
	 	throw new MappingException(message);
	}
	
	def mapTranslation(MdfModelElement modelElement , Object xmlModel) {
		// create the MdfTranslation for the model element :
		var translationManger = TranslationCore::getTranslationManager(project);
		var mdfTranslation = translationManger.getTranslation(modelElement)
		if (xmlModel instanceof ApplicationEntity) {
			var applicationEntity = xmlModel as ApplicationEntity
			applicationEntity.header1.setTranslationTex(ITranslationKind::HEADER1, mdfTranslation)
			applicationEntity.header2.setTranslationTex(ITranslationKind::HEADER2, mdfTranslation)
		}

		// if the model is ApplicationField get the label and tooltip translations 
		if (xmlModel instanceof ApplicationField) {
			var applicationField = xmlModel as ApplicationField
			applicationField.label.setTranslationTex(ITranslationKind::NAME, mdfTranslation)
			applicationField.tooltip.setTranslationTex(ITranslationKind::TEXT, mdfTranslation)
		}

	}

	def toLocale(String local) {
		LocaleUtils::toLocale(local)
	}

	def setTranslationTex(List<Translation> translationList, ITranslationKind kind, ITranslation mdfTranslation) {
		for (Translation translation : translationList) {
			var localeStr = translation.locale
			var translationStr = StringUtils::arrangeWhiteSpaces(translation.translation)
			mdfTranslation.setText(kind, localeStr.toLocale, translationStr)
		}
	}

	def setLocalRefAllowed(MdfClassImpl mdfclass, boolean refAllowed) {
		T24Aspect::setLocalRefAllowed(mdfclass, refAllowed)
	}

	//check property exist in the domain class.
	def isDuplicateProperty(MdfClassImpl mdfclass, String propertyName) {
		var duplicate = false
		if (mdfclass.getProperty(propertyName) != null) {
			duplicate = true
			LOGGER.warn("Property " + propertyName + " with the same name already existing in " + mdfclass.name);
		}
		duplicate
	}

}
