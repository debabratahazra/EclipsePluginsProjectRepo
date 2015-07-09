package com.odcgroup.translation.core.internal.migration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Migrate all translations in the models, then remove the translation 
 * repository
 * @author yan
 */
public class MessageRepositoryMigration {
	
	protected static final String SEPARATOR = ";";

	private static Logger logger = LoggerFactory.getLogger(MessageRepositoryMigration.class);

	/** Extension point */
	private static final String TRANSLATION_MIGRATOR = "com.odcgroup.translation.migration.migrator";
	
	/** Translation problems filename */
	private static final String TRANSLATION_PROBLEMS_FILE = "ds-translation-migration-error-{0}.csv";
	
	Deque<ITranslationModelMigrator> modelMigrators = new LinkedList<ITranslationModelMigrator>();
	
	public MessageRepositoryMigration() {
		loadMigrators();
	}
	
	/**
	 * 
	 */
	private void loadMigrators() {
		
		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all Translation Migrators from Extension Point: {}", TRANSLATION_MIGRATOR);
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_MIGRATOR);
		if (point == null)
		    return;
		IExtension[] extensions = point.getExtensions();
		for (IExtension iExtension : extensions) {
			IConfigurationElement[] configElements = iExtension.getConfigurationElements();
			for (IConfigurationElement configElement : configElements) {
				try {
					ITranslationModelMigrator migrator = (ITranslationModelMigrator) configElement.createExecutableExtension("class");
					boolean addLast = "true".equals(configElement.getAttribute("executeLast"));
					if (addLast) {
						modelMigrators.addLast(migrator);
					} else {
						modelMigrators.addFirst(migrator);
					}
				} catch (CoreException ex) {
					logger.trace(TranslationCore.LOG_MARKER, 
							"(ERROR) A TranslatonModelMigrator cannot be instantiated, please check extension point:"
							+ TRANSLATION_MIGRATOR, ex);
				}
			}
		}
	}

	public void migrate(IOfsProject ofsProject, IFile file, IProgressMonitor monitor) throws TranslationMigrationException {
		InputStream isSource = null;
		try {
			isSource = file.getContents();
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setValidating(false);
			domFactory.setNamespaceAware(true);
			
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			BufferedInputStream bis = new BufferedInputStream(isSource);
			InputSource source = new InputSource(bis);
			org.w3c.dom.Document document = domBuilder.parse(source);
			migrate(ofsProject, document);
		} catch(ParserConfigurationException e) {
			throw new TranslationMigrationException("Parsing error while migrating model '" + file.getLocation() + "'", e);
		} catch (SAXException e) {
			throw new TranslationMigrationException("Parsing error while migrating model '" + file.getLocation() + "'", e);
		} catch (IOException e) {
			throw new TranslationMigrationException("I/O error while migrating model '" + file.getLocation() + "'", e);
		} catch (CoreException e) {
			throw new TranslationMigrationException(e);
		} finally {
			if (isSource != null) {
				IOUtils.closeQuietly(isSource);
			}
		}		
	}

	protected void migrate(IOfsProject ofsProject, org.w3c.dom.Document document) throws TranslationMigrationException {
		logger.debug(TranslationCore.LOG_MARKER, "Starting translations migration...");
		long start = System.currentTimeMillis();

		initMigrationLoggers(ofsProject);
		
		List<TranslationVO> translationsVO = loadTranslations(new DOMBuilder().build(document));
		
		List<TranslationVO> notProcessedTranslations = processTranslations(translationsVO);

		logger.debug(TranslationCore.LOG_MARKER, "Saving all modified models...");
		for (ITranslationModelMigrator migrator : modelMigrators) {
			notProcessedTranslations.addAll(migrator.endMigration());
		}
		
		logProcessedTranslationsSummary(ofsProject, translationsVO, notProcessedTranslations);
		
		ofsProject.refresh();
		
		logger.trace(TranslationCore.LOG_MARKER, 
				"Translations migration done in {} ms",(System.currentTimeMillis()-start));
	}
	
	/**
	 * 
	 */
	private void initMigrationLoggers(IOfsProject ofsProject) {
		for (ITranslationModelMigrator migrator : modelMigrators) {
			migrator.startMigration(logger, ofsProject);
		}
	}

	@SuppressWarnings("unchecked")
	private List<TranslationVO> loadTranslations(Document doc) {

		logger.trace(TranslationCore.LOG_MARKER, "Reading translations...");
		
		List<TranslationVO> translationsVO = new LinkedList<TranslationVO>();
		
		Namespace idns = Namespace
				.getNamespace("xmi", "http://www.omg.org/XMI");

		Element root = doc.getRootElement();

		Map<String, String> messages = new HashMap<String, String>();
		// mid->text
		Map<String, String> languages = new HashMap<String, String>();
		// mid->languages

		Iterator cit = root.getChildren("catalogues").iterator();
		while (cit.hasNext()) {
			Element catalogue = (Element) cit.next();
			String language = catalogue.getAttributeValue("locale");
			Iterator mit = catalogue.getChildren("messages").iterator();
			while (mit.hasNext()) {
				Element message = (Element) mit.next();
				String mid = message.getAttributeValue("id", idns);
				if (StringUtils.isNotEmpty(mid)) {
					String group = message.getAttributeValue("group");
					String value = message.getAttributeValue("value");
					if (StringUtils.isNotEmpty(value)) {
						if (StringUtils.isEmpty(group)) {
							String item = "Catalogue:" + language + " Message:" + mid;
							String diag = "The message is not bound to a group";
							logDiagnostic(item, diag);
						} else {
							messages.put(mid, value);
							languages.put(mid, language);
						}
					} else {
						String item = "Catalogue:" + language + " Message:" + mid;
						String diag = "The message has no text";
						logDiagnostic(item, diag);
					}
				}
			}
		}

		Iterator git = root.getChildren("groups").iterator();
		while (git.hasNext()) {
			Element group = (Element) git.next();
			String gid = group.getAttributeValue("id", idns);
			if (StringUtils.isNotEmpty(gid)) {
				String key = group.getAttributeValue("key");
				String texts = group.getAttributeValue("messages");
				String[] mids = {};
				if (StringUtils.isNotEmpty(texts)) {
					if (texts.indexOf(" ") != -1) {
						mids = texts.split(" ");
					} else {
						mids = new String[] { texts };
					}
				} else {
					String item = "Key:" + key + " Group:" + gid;
					String diag = "There are no messages for the key/group";
					logDiagnostic(item, diag);
				}
				for (int mx = 0; mx < mids.length; mx++) {
					String mid = mids[mx];
					String message = messages.get(mid);
					String language = languages.get(mid);
					if (StringUtils.isNotEmpty(message) && StringUtils.isNotEmpty(language)) {
						translationsVO.add(new TranslationVO(key, language, message));
						messages.remove(mid);
						languages.remove(mid);
					} else {
						String item = "group:" + gid + " message:" + mid;
						String diag = "The message has been deleted";
						logDiagnostic(item, diag);
					}
				}
			}
		}

		Iterator it = messages.keySet().iterator();
		while (it.hasNext()) {
			String mid = (String) it.next();
			String item = "language:" + languages.get(mid) + " message:" + mid;
			String diag = "The message is linked to a deleted group";
			logDiagnostic(item, diag);
		}
		
		Collections.sort(translationsVO);
		return translationsVO;
	}

	/**
	 * @param ofsProject
	 * @param modelURI 
	 * @param translationsVO
	 * @return
	 */
	private List<TranslationVO> processTranslations(List<TranslationVO> translationsVO) {
		logger.trace(TranslationCore.LOG_MARKER, "Processing translations...");
		logger.trace(TranslationCore.LOG_MARKER, "Nb items to be processed: {}", translationsVO.size());

		List<TranslationVO> notProcessedTranslationVO = new LinkedList<TranslationVO>();
		for (TranslationVO vo: translationsVO) {
			logger.trace(TranslationCore.LOG_MARKER, "Processing: {}", vo.key);
			if (StringUtils.isEmpty(vo.key)) {
				notProcessedTranslationVO.add(vo);
			} else {
				boolean migrationOk = processByMigrator(vo);
				if (!migrationOk) {
					notProcessedTranslationVO.add(vo);
				}
			}
		}
		return notProcessedTranslationVO;
	}
	
	/**
	 * @param ofsProject
	 * @param modelURI
	 * @param vo
	 * @return
	 */
	boolean processByMigrator(TranslationVO vo) {
		for (ITranslationModelMigrator migrator : modelMigrators) {
			if (migrator.isKeyAccepted(vo.key)) {
				return migrator.process(vo);
			}
		}
		vo.errorStatus = "No migrator found for the key";
		return false;
	}

	/**
	 * @param item
	 * @param diag
	 */
	private void logDiagnostic(String item, String diag) {
		logger.trace(TranslationCore.LOG_MARKER, "(Inconsistancy) {} : {}" + diag,item);
	}
	
	/**
	 * @param translationsVO
	 * @param notProcessedTranslations
	 */
	private void logProcessedTranslationsSummary(IOfsProject ofsProject, List<TranslationVO> translationsVO,
			List<TranslationVO> notProcessedTranslations) {
		
		String translationMigrationErrorFile = null;
		if (notProcessedTranslations.size() != 0) {
			translationMigrationErrorFile = writeNotProcessTranslations(ofsProject, notProcessedTranslations);
		}

		logger.info(TranslationCore.LOG_MARKER, "********************************");
		logger.info(TranslationCore.LOG_MARKER, " Translations Migration Summary");
		logger.info(TranslationCore.LOG_MARKER, "********************************");
		logger.info(TranslationCore.LOG_MARKER, "Nb translations found: " + translationsVO.size());
		logger.info(TranslationCore.LOG_MARKER, "Nb translations successfully migrated: " + (translationsVO.size()-notProcessedTranslations.size()));
		if (notProcessedTranslations.size() == 0) {
			logger.info(TranslationCore.LOG_MARKER, "Nb translations not migrated: " + notProcessedTranslations.size());
		} else {
			logger.warn(TranslationCore.LOG_MARKER, "Nb translations not migrated: " + notProcessedTranslations.size() + 
					" (see " + translationMigrationErrorFile + " for an extended list)");
		}
		logger.info(TranslationCore.LOG_MARKER, "********************************");
	}

	/**
	 * @param ofsProject
	 * @param notProcessedTranslations
	 */
	protected String writeNotProcessTranslations(IOfsProject ofsProject, List<TranslationVO> notProcessedTranslations) {
		File eclipseHome = new File(Platform.getInstallLocation().getURL().getPath());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String filename = MessageFormat.format(TRANSLATION_PROBLEMS_FILE, sdf.format(new Date()));
		File migrationErrorFile = new File(eclipseHome.getAbsolutePath() + File.separator + filename);
		
		PrintStream printStream = null;
		try {
			printStream = new PrintStream(migrationErrorFile);
			printStream.println("project" + SEPARATOR + 
					"key" + SEPARATOR + 
					"language" + SEPARATOR + 
					"value" + SEPARATOR + 
					"problem");
			for (TranslationVO vo: notProcessedTranslations) {
				printStream.println(ofsProject.getName() +  
						SEPARATOR + (vo.key==null?"":vo.key) + 
						SEPARATOR + (vo.language==null?"":vo.language) + 
						SEPARATOR + (vo.text==null?"":vo.text) + 
						SEPARATOR + vo.errorStatus);
			}
		} catch (Exception e) {
			logger.error("Unable to save migration problems to " + migrationErrorFile.getAbsolutePath());
		} finally {
			if (printStream != null) {
				printStream.close();
			}
		}
		
		return migrationErrorFile.getAbsolutePath();
	}

}
