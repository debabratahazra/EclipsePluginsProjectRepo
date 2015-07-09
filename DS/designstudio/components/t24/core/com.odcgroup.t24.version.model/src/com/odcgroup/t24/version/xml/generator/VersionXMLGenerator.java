package com.odcgroup.t24.version.xml.generator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.workbench.generation.xml.AbstractXmlCodeGenerator;

public class VersionXMLGenerator extends AbstractXmlCodeGenerator {

	@Override
	protected void transform(EObject root ,Resource resource) throws Exception {
		// DS-5686 transform the outgoing field name to T24 name:
		VersionUtils.transformFieldNameToT24Names(root ,resource);
		VersionUtils.replaceVersionReferenceWithT24Name(root , resource);
		VersionUtils.removeT24EdgeConnectFields(root, resource);
		VersionUtils.addInheritedTranslations(root, resource);
	}

	@Override
	protected String getModelsExtension() {
		return "version";
	}

}