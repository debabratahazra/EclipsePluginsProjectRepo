package com.odcgroup.dataframework.generation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.IRISEnquiryMapper;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMEntityModel;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

public class T24DataFrameworkGenerator implements CodeGenerator2, CodeGenerator {

	// private static final Logger logger = LoggerFactory.getLogger(T24DataFrameworkGenerator.class);
	private static String ENQUIRY_FILE_EXTENSION = "enquiry";

	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {

		Resource resource = loader.getResource(input);
		EObject eObj = resource.getContents().get(0);

		if ((!input.fileExtension().equals(ENQUIRY_FILE_EXTENSION)) || !(eObj instanceof Enquiry)) {
			// Calling with non .enquiry files is expected. If we throw here it breaks the build. So silently fail
			// without producing output.
			return;
		}

		Enquiry enquiry = (Enquiry) eObj;

		// Only generate a .java file if enquiry is from a RO database.
		if (isROEnquiry(enquiry)) {

			// Java file name must be adjusted for camelcase.
			String javaFileName = "com/temenos/dataframework/rodb/" + RESOURCE_TYPE.enquiry
					+ EMUtils.camelCaseName(enquiry.getName()) + ".java";

			// Following plumbing converts Enquiry to the EMEntity form
			// required for IIRISGenerator interface.
			MdfClass mdfClass = EMUtils.getAppByEnquiry(loader, enquiry);
			Application application = new Application(mdfClass);
			IRISEnquiryMapper enquiryMapper = new IRISEnquiryMapper();
			EMEntity entity = enquiryMapper.getEntity(enquiry, application, null, RESOURCE_TYPE.enquiry);

			// Package EMEntity into a EMEntity model.
			EMEntityModel entityModel = new EMEntityModel(enquiry.getName());
			entityModel.addEntity(entity);

			// Generate and save the code. If it throws propagate the exception.
			generate(new javaGenerator(), javaFileName, entityModel, fsa);
		}
	}

	private void generate(javaGenerator generator, String fileName, EMEntityModel entityModel, IFileSystemAccess fsa) {
		HashMap<String, Variable> globalVars = new HashMap<String, Variable>();
		globalVars.put("fileName", new Variable("fileName", fileName));
		generator.doGenerate(entityModel, fsa, globalVars);
	}

	/**
	 * Check if enquiry is from a RO database.
	 */
	private boolean isROEnquiry(Enquiry enquiry) {
		if (enquiry.getEnquiryMode() == EnquiryMode.DB) {
			return (true);
		}
		return (false);
	}

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new NotImplementedException();
	}
}
