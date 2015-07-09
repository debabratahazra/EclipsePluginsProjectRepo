package com.odcgroup.service.gen.t24.internal.cartridges.mapper

import com.odcgroup.mdf.ecore.MdfPrimitiveImpl
import com.odcgroup.mdf.ecore.util.DomainRepository
import com.odcgroup.mdf.metamodel.MdfAssociation
import com.odcgroup.mdf.metamodel.MdfAttribute
import com.odcgroup.mdf.metamodel.MdfClass
import com.odcgroup.mdf.metamodel.MdfEntity
import com.odcgroup.mdf.metamodel.MdfProperty
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor
import com.odcgroup.service.gen.t24.internal.data.Cardinality
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor
import com.odcgroup.service.gen.t24.internal.data.Complexity
import com.odcgroup.service.gen.t24.internal.data.Direction
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor
import com.odcgroup.service.model.component.Argument
import com.odcgroup.service.model.component.Component
import com.odcgroup.service.model.component.Method
import com.odcgroup.workbench.core.repository.OfsResourceHelper
import java.util.List
import com.odcgroup.service.model.component.Multiplicity
import com.odcgroup.service.model.component.InOutType
import com.odcgroup.service.model.component.Content

class ServiceModel2ServiceDescriptionMapper {
	def create sDescriptor : new ServiceDescriptor(component.name, newArrayList(), newArrayList(),component.getMetamodelVersion) map (Component component)
	{
		for(tempContent : component.content){
			for(operation : tempContent.method) {
				var oDescriptor = operation.mapOperation
				for(annotation:operation.annotations) {
					oDescriptor.addStereotype(annotation.name)
				}
				sDescriptor.operations += oDescriptor
				var ofsProject = OfsResourceHelper::getOfsProject(component.eResource)
				var repo = DomainRepository::getInstance(ofsProject)
				var allDomains = repo.domains
				for(domain : allDomains) {
					var classes = domain.classes
					for(klazz : classes) {
						var defDecs = (klazz as MdfClass).mapClassDefDescriptor
						if(!(sDescriptor.classDefDescriptors.contains(defDecs))) {
							sDescriptor.classDefDescriptors += defDecs
						}
						(klazz as MdfClass).mapAssociations(sDescriptor)
					}
				}	
			}
		}
	}
	
	def mapAssociations(MdfClass klazz, ServiceDescriptor descriptor) { 
		for(property : klazz.properties) {
			if(property instanceof MdfAssociation) {
				var entity = (property as MdfAssociation).type
				var klazz1 = (entity as MdfClass)
				var assocDesc1 = klazz1.mapClassDefDescriptor			
				if(!(descriptor.classDefDescriptors.contains(assocDesc1))) {
					descriptor.classDefDescriptors += assocDesc1
					klazz1.mapAssociations(descriptor)				
				}				
			}	
		}		
	}
	
	def mapClassDefDescriptor(MdfClass klassObj) {
		val List<AttributeDescriptor> attrList = newArrayList()
		for(attribute : klassObj.properties) {
			if(attribute instanceof MdfProperty) {
				attrList += (attribute as MdfProperty).mapAttributeDescriptor
			}
		}
		new ClassDefDescriptor(klassObj.name, klassObj.name, attrList)
	}
	
	def mapAttributeDescriptor(MdfProperty property) { 
		var card = (Multiplicity::get(property.multiplicity)).mapCardinality;
		var typeName = property.type.name		
		if(typeName.equals("string")) {
			typeName = "String"
		}
		new AttributeDescriptor(property.name, typeName, card);
	}
	
	def  mapAttributeDescripor(MdfProperty property) { 
		var card = (Multiplicity::get(property.multiplicity)).mapCardinality;
		var typeName = property.type.name		
		new AttributeDescriptor(property.name, typeName, card);
	}

	
	def mapOperation (Method operation) 
	{
		//service name
		val serviceName = (operation.eContainer.eContainer as Component).name 
		//name of the operation
		val name = operation.name
		//list of param descriptor
		val List<ParamDescriptor> paramDescList = newArrayList()
		for(argument : operation.arguments) {
			paramDescList += argument.mapArgument
		}
		// get access specifier
		val scope = operation.getAccessSpecifier()
		//return param desc
		val retParamDesc = new ParamDescriptor("argName", "void", null, Direction::OUT, null, true, null);
		new OperationDescriptor(serviceName, name, paramDescList, retParamDesc,scope)
	}
	
	def mapArgument (Argument argument) {
		//name
		val argName = argument.name
		//type name
		var typeName = argument.type.name
		//cardinality
		val cardinality = argument.multiplicity.mapCardinality
		
		if(typeName.equals("string")) {
			typeName = "String"
		}
		
		//direction
		val direction = argument.inout.mapDirection
		//complexity
		val complexity = argument.type.mapComplexity
		//mandatory
		val mandatory = argument.multiplicity == Multiplicity::MANDATORY || argument.multiplicity == Multiplicity::ONETOMANY
		//attributes - AttributeDescriptor
		val List<AttributeDescriptor> attrList = newArrayList()
		if(!(argument instanceof MdfPrimitiveImpl)) {
			var attrDesc = argument.mapAttributeDescriptor
			if(!attrList.contains(attrDesc)) 
				attrList += attrDesc 
		}
		new ParamDescriptor(argName, typeName, cardinality, direction, complexity, mandatory, attrList)
	}
	
	def mapCardinality (Multiplicity multiplicityabc) {
		if(multiplicityabc == Multiplicity::MANY) {
			Cardinality::MULTIPLE
		} else if(multiplicityabc == Multiplicity::ONETOMANY) {
			Cardinality::MULTIPLE
		} else if(multiplicityabc == Multiplicity::OPTIONAL) {
			Cardinality::SINGLE
		} else if(multiplicityabc == Multiplicity::MANDATORY) {
			Cardinality::SINGLE
		}
	}
	
	def mapDirection (InOutType type) {
		if(type == InOutType::IN) {
			Direction::IN
		} else if(type == InOutType::OUT) {
			Direction::OUT
		} else if(type == InOutType::INOUT) {
			Direction::INOUT
		}
	}
	
	def mapComplexity (MdfEntity entity) {
		if(entity instanceof MdfPrimitiveImpl) {
			Complexity::PRIMITIVE
		} else {
			Complexity::COMPLEX
		}
	}
	
	def mapAttributeDescriptor (Argument entity) {
		var card = (Multiplicity::get(entity.multiplicity.value)).mapCardinality;
		var typeName = entity.type.name
		if(typeName.equals("string")) {
			typeName = "String"
		}
		new AttributeDescriptor(entity.name, typeName, card);
	}
	
	def mapAttributeDescriptor (MdfAttribute entity) {
		var card = (Multiplicity::get(entity.multiplicity)).mapCardinality;
		var typeName = entity.type.name
		if(typeName.equals("string")) {
			typeName = "String"
		}
		new AttributeDescriptor(entity.name, typeName, card);
	}
}