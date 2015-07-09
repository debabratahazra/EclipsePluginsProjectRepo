package com.odcgroup.aaa.connector.internal.util;

import static com.odcgroup.aaa.core.util.NamingHelper.getMetaDictPermName;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.odcgroup.aaa.connector.domainmodel.FormatEntity;
import com.odcgroup.aaa.connector.domainmodel.TypeEntity;
import com.odcgroup.aaa.connector.formats.FormatCriteria;
import com.odcgroup.aaa.connector.formats.FormatDAO;
import com.odcgroup.aaa.connector.internal.InvalidDatabaseConnectionException;
import com.odcgroup.aaa.connector.internal.InvalidMetaDictException;
import com.odcgroup.aaa.connector.internal.InvalidTripleAVersionException;
import com.odcgroup.aaa.connector.internal.TANGIJException;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictWithLabelsDAO;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictWithLabelsRepository;
import com.odcgroup.aaa.connector.internal.metadictreader.TAVersion;
import com.odcgroup.aaa.connector.mdfmml.Format2Dataset;
import com.odcgroup.aaa.connector.mdfmml.MetaDict2Mml;
import com.odcgroup.aaa.connector.mdfmml.MetaDictDomains;
import com.odcgroup.aaa.connector.mdfmml.MetaDictMdfDomainHelper;
import com.odcgroup.aaa.connector.mdfmml.TA2MMLException;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;

/**
 * An open connection to a T'A DB.
 * This offers the interesting "business" methods that a user of this library may be interested in.
 *
 * Technical Implementation Note: This contains a JPA EntityManager (EM), which contains a JDBC Connection.
 * Because of this, you need to call close() when you're fully done, or you may want to the close() method
 * as soon as you're done getting what you wanted (but there is no Connection Pooling here [yet?], so next slower).
 *
 * @author Michael Vorburger (MVO)
 */
public class TADatabaseConnection {

	private static final String CONFIGURATION_FILE = "/aaa-connector.properties";
	private static final TAVersion EXPECTED_TRIPLEA_VERSION;
	private static final Logger LOG = Logger.getLogger(TADatabaseConnection.class);

    private final EntityManager em;
    private final MetaDictWithLabelsDAO metaDictDAO;
    private final FormatDAO formatDAO;

	private static final String FORMATS_DOMAIN_PREFIX = AAACore.getFindroot().toUpperCase()+"Formats";

	private static final String FORMATS_NAMESPACE_PREFIX = "http://www.odcgroup.com/"+AAACore.getFindroot().toLowerCase()+"-formats-";
	static {
		Properties props = new Properties();
		try {
			InputStream is =TADatabaseConnection.class.getResourceAsStream(CONFIGURATION_FILE);
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load " + CONFIGURATION_FILE);
		}
        EXPECTED_TRIPLEA_VERSION = new TAVersion(props.getProperty("expected.triplea.version"));
    }

    public TADatabaseConnection(EntityManager em) {
        this.em = em;
        this.metaDictDAO = new MetaDictWithLabelsDAO(em);
        this.formatDAO = new FormatDAO(em);
    }

    /**
     * Test if the connection is valid by executing a query on the format table
     * @throws InvalidDatabaseConnectionException
     * @throws InvalidTripleAVersionException
     */
    public boolean testConnection() throws InvalidDatabaseConnectionException, InvalidTripleAVersionException, InvalidMetaDictException {
    	boolean connFlag = true;
    	try {
    		metaDictDAO.validateConnection();
    	} catch (Exception e) {
    		connFlag = false;
    		throw new InvalidDatabaseConnectionException("Unable to read database", e);
    	}

		if (metaDictDAO.getTAVersion().compareTo(EXPECTED_TRIPLEA_VERSION) < 0) {
			connFlag = false;
			throw new InvalidTripleAVersionException("expected at least version: " + EXPECTED_TRIPLEA_VERSION + ", but was: " + metaDictDAO.getTAVersion());
		}

		StringBuffer metaDictResult = metaDictDAO.validateMetaDict();
		 if(metaDictResult != null ) {
            //DS-4188 We have run the SP to check the metadict and it has returned a list populated with the
			//errors so throw an exception.
			 connFlag = false;
			 throw new InvalidMetaDictException(metaDictResult.toString());
		}
		 
		 return connFlag;
    }

    /**
     * Returns an MDF Domain with entities as defined in the T'A Meta Dictionary.
     *
     * @return MDF Domains (typically aaa.domain, aaa-shorts.domain, ...)
     * @throws MdfParsingException if something went wrong with MDF during the construction of the domain
     * @throws TA2MMLException if something went wrong in the "T'A to MML" translation logic (e.g. something unexpected was encountered in the Meta Dictionary)
     */
    public MetaDictDomains getMetaDictionaryDomains(boolean populateDatasets, MdfDomain businessTypesDomain) throws 
    TA2MMLException {
        checkThatWeAreNotClosedForBusinessAlready();

        MetaDictWithLabelsRepository aaaMetaDict = metaDictDAO.getMetaDictWithLabels();

        MetaDictDomains domains = new MetaDictDomains();
        domains.businessTypesDomain = (MdfDomainImpl) businessTypesDomain;
        domains.businessTypesDomain.setName(domains.businessTypesDomain.getName().replace("AAA", AAACore.getFindroot().toUpperCase()));
        domains.businessTypesDomain.setNamespace(domains.businessTypesDomain.getNamespace().replace("aaa", AAACore.getFindroot().toLowerCase()));
        MetaDict2Mml metaDic2Mml = new MetaDict2Mml();
        metaDic2Mml.createAAADomainFromMetaDict(aaaMetaDict, domains);
        //set the timestamp annotations.
        String timeStamp =  metaDictDAO.getTimeStamp();
        metaDic2Mml.setTimeStampAnnotaiton(domains, timeStamp);
        if (populateDatasets) {
        	throw new IllegalArgumentException("Uops, are we still using the shortDatasetsDomain?! Uncomment that code that was about to go to bit heaven ASAP... ;-)");
        }
        return domains;
    }

    /**
     * Returns a list of all T'A Formats.
     * Typically useful for e.g. the Browse... button in the planned DS UI.
     *
     * @return List of codes of all formats
     */
    public List<FormatVO> getAllFormats() {
        checkThatWeAreNotClosedForBusinessAlready();
        return formatDAO.getAllFormatCodes();
    }

    /**
     * Returns a list of all T'A Format Types.
     * One of these (or null) has to be set into a FormatCriteria when bulk importing.
     *
     * @see FormatCriteria
     * @return List of Format Types (never null, but may be empty list)
     */
    public List<TypeEntity> getAllFormatTypes() {
        checkThatWeAreNotClosedForBusinessAlready();
        return formatDAO.getFormatTypes();
    }

    /**
     * Returns a list of all T'A functions (proc name).
     *
     * @return List of functions (proc name)
     */
    public List<FunctionVO> getAllFunctions() {
        checkThatWeAreNotClosedForBusinessAlready();
        return formatDAO.getAllFunctions();
    }

    /**
     * Returns an MDF Domain to which the MdfDatasets returned by {@link #getDataSetFromFormat(String)} should be added to.
     *
     * @return MdfDomain (typically aaa-formats.mml)
     */
    public MdfDomain getDatasetFormatsDomain() {
        // This method per-se of course doesn't actually require a DB/EM - and could be elsewhere, if needed.
    	MdfDomainImpl domain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
        domain.setName("AAAFormatsReferences");
        domain.setNamespace("http://www.odcgroup.com/aaa-formats-references");
        JavaAspectDS.setPackage(domain, "com.odcgroup.tangij.domain.formats.datasets");
        return domain;
    }

    /**
     * Returns an MDF Domain to which the MdfClasses returned by {@link #getEntityFromFormat(String)} should be added to.
     *
     * @return MdfDomain (typically aaa-formats.mml)
     */
    public MdfDomain getClassFormatsDomain() {
        // This method per-se of course doesn't actually require a DB/EM - and could be elsewhere, if needed.
        MdfDomainImpl domain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
        domain.setName("AAAFormatsLists");
        domain.setNamespace("http://www.odcgroup.com/aaa-formats-lists");
        JavaAspectDS.setPackage(domain, "com.odcgroup.tangij.domain.formats.classes");
        return domain;
    }

    /**
     * Loads, converts and returns a Class corresponding to T'A Format.
     *
     * @see #getDataSetFromFormat(String, MdfDomain, MdfDomain)
     */
    public MdfClass getClassFromFormat(String formatCode, MdfDomain domainForNewFormat, MdfDomain metaDictEntities, MdfDomain metaDictEnums, MdfDomain businessTypesDomain) throws TANGIJException {
        checkThatWeAreNotClosedForBusinessAlready();

        FormatEntity format = formatDAO.getFormat(formatCode);
        MdfClass entity = new Format2Dataset(metaDictEntities, metaDictEnums, businessTypesDomain).
        		convertFormatToClass(domainForNewFormat, format, businessTypesDomain);
        return entity;
    }

    /**
     * Bulk load formats from T'A, convert all to Classes, and add them to an MDF domain.
     *
     * @see #getEntityFromFormat(String, MdfDomain, MdfDomain)
     * @see #addDataSetsFromFormats(String, MmlDomain, MdfDomain) for note about error handling
     */
    public ImportReportVO addClassesFromFormats(FormatCriteria criteria, Map<String, MdfDomainImpl> mmlDomains, MdfDomain metaDictEntities, MdfDomain metaDictEnums, MdfDomain businessTypesDomain) {
        return addDataSetsOrClassFromFormats(criteria, mmlDomains, metaDictEntities, metaDictEnums, businessTypesDomain);
    }
    
    //@SuppressWarnings("unchecked")
	private ImportReportVO addDataSetsOrClassFromFormats(FormatCriteria criteria, Map<String,MdfDomainImpl> functionDomains, MdfDomain metaDictEntities, MdfDomain metaDictEnums, MdfDomain businessTypesDomain)  {
        checkThatWeAreNotClosedForBusinessAlready();

        ImportReportVO report = new ImportReportVO();

        MetaDictMdfDomainHelper metaDictMdfDomainHelper;
        try {
        	metaDictMdfDomainHelper = new MetaDictMdfDomainHelper(metaDictEntities, metaDictEnums, businessTypesDomain);
        } catch (TA2MMLException e) {
            throw new IllegalArgumentException("MetaDictionary Domain has a problem", e);
        }

        Format2Dataset convertor = new Format2Dataset(metaDictMdfDomainHelper);
        
        List<FormatEntity> formats = formatDAO.getFormats(criteria);
        Iterator<FormatEntity> it = formats.iterator();
        while (it.hasNext()) {
            FormatEntity format = it.next();

            String procNameFunction = format.getFunction().getProcName();
            String functionDomainName = ProcNameFunctionFormatter.getCamelCaseProcNameFunction(ProcNameFunctionFormatter.getProcFileNameFunction(procNameFunction)) + ".domain";

            if (!functionDomains.containsKey(functionDomainName)) {
            	MdfDomainImpl formatDomain = createNewMmlDomainForFormat(procNameFunction);
            	functionDomains.put(functionDomainName, formatDomain);
            }
            MdfDomain domain = functionDomains.get(functionDomainName);
            try {
            	MdfEntity entity;
               	entity = convertor.convertFormatToClass(domain, format, businessTypesDomain);
                // remove the entity prior adding
                // Note: we do not support proc name change for a format
                // as we would need to pass all format mml to the meta-dictionary
                // library (raises performance issues) for a rare case.
                Iterator<MdfClass> iterator = domain.getClasses().iterator();
                while (iterator.hasNext()) {
                    MdfClass aClass = iterator.next();
                    if (aClass.getName().equals(entity.getName())) {
                        iterator.remove();
                    }
                }

                domain.getClasses().add(entity);
//                if (dsFormats != null) {
//	                Format dsFormat = converterXText.convertFormat(format);
//	                String formatFileName = ProcNameFunctionFormatter.getCamelCaseProcNameFunction(procNameFunction) +
//	                		"/" + dsFormat.getName() + ".format";
//	                dsFormats.put(formatFileName, dsFormat);
//                }
                
                report.addImportedFormat(functionDomainName, entity.getName());
            } catch (TA2MMLException e) {
                handleExceptionDuringBulkImport(functionDomainName, format, e, report);
            }
        }

        return report;
    }


    /**
	 * @param procName
	 * @return
	 */
	private MdfDomainImpl createNewMmlDomainForFormat(String procNameFunction) {
		MdfDomainImpl mdfDomain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		mdfDomain.setName(FORMATS_DOMAIN_PREFIX + ProcNameFunctionFormatter.getCamelCaseProcNameFunction(getMetaDictPermName(procNameFunction)));
		mdfDomain.setNamespace(FORMATS_NAMESPACE_PREFIX + ProcNameFunctionFormatter.getDashedProcNameFunction(ProcNameFunctionFormatter.getProcFileNameFunction(procNameFunction)));
		return mdfDomain;
	}

	private void handleExceptionDuringBulkImport(String functionDomainName, FormatEntity format, Exception e, ImportReportVO report) {
        String message = "Bulk Import failed for one format (attempted to continue importing others). ";
        if (format != null) {
            message += " Failed Format = " + format.getCode();
        }
        message += e.getMessage();
        LOG.error(message, e);

        report.addErrorMessage(format.getCode(), e.getMessage());
        report.addNonImportedFormatPerDomainFunction(functionDomainName, format.getCode());
    }


    /**
     * Should be called to "destroy" this instance.
     * Closes connection to underlying database.
     * This object will not be usable anymore after this is called.
     */
    public void close() {
       em.close();
    }

    private void checkThatWeAreNotClosedForBusinessAlready() {
        if ( !em.isOpen() ) {
            throw new IllegalStateException("We're closed for business for today!  (I.e. the close() method has already been called.)");
        }
    }
}
