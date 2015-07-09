package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMEntityModel;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

/**
 * @author phanikumark & mvorburger
 */
public class IRISMetadataMapper {
	private static Logger LOGGER = LoggerFactory.getLogger(IRISMetadataMapper.class);
	
	private boolean isT24Nature = false;
	
	private final EMEntityModel project;
    private FieldTypeChecker fieldTypeChecker;
    
    protected IRISMetadataMapper(String name) {
		this.project = new EMEntityModel(name);
	}

	public IRISMetadataMapper(String name, URI uri, ModelLoader modelloader) throws Exception {
		this(name);
		Resource resource = modelloader.getResource(uri);
		fieldTypeChecker = new FieldTypeChecker(resource.getContents().get(0));
		isT24Nature = mapResource(uri, modelloader);
	}

	protected boolean isT24Nature(){
		return this.isT24Nature;
	}

	static boolean isInterested(URI uri) {
		String ext = uri.fileExtension();
		// Make sure this remains in line with the if switch below...
		return 	ext.equals(GeneratorConstants.DOMAIN_EXT) || 
				ext.equals(GeneratorConstants.VERSION_EXT) || 
				ext.equals(GeneratorConstants.ENQUIRY_EXT);
	}
	
	private boolean mapResource(URI uri, ModelLoader modelLoader) throws Exception {
		boolean isT24Nature = false;
		String extn = uri.fileExtension();
        // Make sure this remains in line with isInterested above...
		if (extn.equals(GeneratorConstants.DOMAIN_EXT)) {
			MdfDomain domain = modelLoader.getRootEObject(uri, MdfDomain.class);
			isT24Nature = map(domain, modelLoader, fieldTypeChecker);
		} else if (extn.equals(GeneratorConstants.VERSION_EXT)) {
			Version version = modelLoader.getRootEObject(uri, Version.class);
			isT24Nature = map(version, fieldTypeChecker, modelLoader);
		} else if (extn.equals(GeneratorConstants.ENQUIRY_EXT)) {
			Enquiry enquiry = modelLoader.getRootEObject(uri, Enquiry.class);
			MdfClass mdfClass = EMUtils.getAppByEnquiry(modelLoader, enquiry);
			Application application = new Application(mdfClass);
			isT24Nature = map(enquiry, application, fieldTypeChecker);
		}
		return 	isT24Nature;

	}
	
	private boolean map(Enquiry enquiry, Application application, FieldTypeChecker fieldTypeChecker) {
		if (enquiry != null) {
			IRISEnquiryMapper enquiryMapper = new IRISEnquiryMapper();
			EMEntity entity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enquiry);
			project.addEntity(entity);
		}
		return true;
	}

	/**
	 * Map Version to an Entity
	 * @param version
	 * @throws Exception
	 * @return if this is a t24Nature. Always true in the case of a version
	 */
	private boolean map(Version version, FieldTypeChecker fieldTypeChecker, ModelLoader modelLoader) throws Exception{
		if (version != null) {
			MdfClass mdfClass = version.getForApplication();
			Application application = new Application(mdfClass);
			IRISVersionMapper versionMapper = new IRISVersionMapper();
			EMEntity entity = versionMapper.getEntity(version, application, fieldTypeChecker);
			
			// If AAA Type of version, then add PropertyClasses from each Product Line available 
			if (isAAAType(application)) {
				addAAProductLines(entity, version, modelLoader);
			}
			project.addEntity(entity);
		}	
		return true;
	}
	
	/**
	 * Find out if Version is of AAA Type
	 * @param application
	 * @return
	 */
	private boolean isAAAType(Application application) {
		if (application != null && application.getT24Name() != null && application.getT24Name().equals(GeneratorConstants.AAA_TYPE)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method will add all Unique Property Classes in each Product Line introspected in workspace as EMPropertry
	 * @param entity
	 * @param modelLoader 
	 */
	@SuppressWarnings("unchecked")
	private void addAAProductLines(EMEntity entity, Version context, ModelLoader modelLoader) throws Exception {
		if (entity != null) {
			MdfDomain productLineDomain = modelLoader.getNamedEObject(context, QualifiedName.create("ProductLine"), MdfPackage.Literals.MDF_DOMAIN);
			if (productLineDomain != null && productLineDomain.getClasses().size() > 0) {
				IRISDomainMapper domainMapper = new IRISDomainMapper();
				List<MdfClass> productLines = productLineDomain.getClasses();
				// Contains a list of property already included avoid duplicate AA Property 
				List<EMProperty> ignoreEMPropertyList = new ArrayList<EMProperty>();
				for (MdfClass productLine : productLines) {
					entity.addProperties(domainMapper.getDomainAsProperties(productLine, fieldTypeChecker, ignoreEMPropertyList));
				}
			} else { 
				throw new Exception("No AA Product lines found! Either remove AAA version from model or introspect at least one AA Product Line!");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean map(MdfDomain domain, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		boolean isT24Nature = false;
		if (domain != null) {
			IRISDomainMapper domainMapper = new IRISDomainMapper();
			IRISDefaultListEnquiryMapper defaultListEnquiryMapper = new IRISDefaultListEnquiryMapper();
			List<MdfClass> classes = domain.getClasses();
			for (MdfClass mdfClass : classes) {
				// If its a AA Product Line or Property Class, simply skip as that should only generated for AAA Version
				if (!IRISDomainMapper.isAAResource(mdfClass)) {
					if (mdfClass.getName().indexOf("__") == -1) {
						EMEntity domainEntity = domainMapper.getEntity(mdfClass, fieldTypeChecker, null);
						project.addEntity(domainEntity);
						addDefaultDomainListEnquiries(defaultListEnquiryMapper, mdfClass, modelLoader, fieldTypeChecker);
					}
				} else {
					LOGGER.debug("Skipping metadata generaton for AA resource: ["+mdfClass.getName()+"]...");
				}
			}
			isT24Nature = domainMapper.isT24Nature();
		}
		return isT24Nature;
	}
	
	/**
	 * Method to add enqlist<Domain><Nau|Hist> Entities
	 * @param mapper
	 * @param mdfClass
	 * @param modelLoader
	 * @param fieldTypeChecker
	 */
	private void addDefaultDomainListEnquiries(IRISDefaultListEnquiryMapper mapper, MdfClass mdfClass, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		List<EMEntity> defaultListEnquiryEntities = mapper.getEntities(mdfClass, modelLoader, fieldTypeChecker);
		for (EMEntity entity : defaultListEnquiryEntities) {
			getProject().addEntity(entity);
		}
	}
				
	public EMEntityModel getProject() {
		return project;
	}

}
