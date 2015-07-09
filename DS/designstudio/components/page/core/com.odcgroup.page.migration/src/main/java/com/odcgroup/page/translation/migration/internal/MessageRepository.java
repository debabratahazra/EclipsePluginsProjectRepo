package com.odcgroup.page.translation.migration.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.ResourceEntityHandlerImpl;
import org.slf4j.Logger;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.translation.migration.IMessageRepository;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * @author atr
 */
public class MessageRepository implements IMessageRepository {

	/** the logger */
	private Logger migrationLogger;

	/** The OFS project */
	private IOfsProject ofsProject;

	/** The translation manager */
	private ITranslationManager manager;

	/** This map contains all translation key extracted from models */
	private Map<String/* key */, Set<EObject>> keyToEObjectsMap;

	/**
	 * This map contains all numerical translation keys extracted from all
	 * dependents translation files
	 */
	private Map<String/* key */, Map<String/* language */, String/* text */>> keyToMessagesMap;

	/** */
	private boolean keysLoaded = false;

	/** */
	private Set<Resource> modifiedResources = new HashSet<Resource>();

	/** tiny cache for locales */
	private Map<String, Locale> locales = new HashMap<String, Locale>();

	
	private String getExtension(String filename) {
		String ext = "";
		int i = filename.lastIndexOf('.');
		if (i > 0 && i < filename.length() - 1) {
			ext = filename.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * load and parse all dependent translation files
	 */
	private void loadKeysFromDependentTranslationsFiles() {

		MessageRepositoryParser parser = new MessageRepositoryParser();

		IDependencyManager depMgr = OfsCore.getDependencyManager(ofsProject.getProject());
		Set<IOfsModelContainer> containers = depMgr.getDependencies(ofsProject);
		for (IOfsModelContainer container : containers) {
			Set<java.net.URI> transUris = depMgr.getFileURIs(container, new Path("archived/translation"));
			if (transUris.size() == 0) {
				// try to locate in the old folder
				transUris = depMgr.getFileURIs(container, new Path("translation/translations"));
			}
			for (java.net.URI uri : transUris) {
				migrationLogger.info("Loading dependent translations from " + uri); //$NON-NLS-1$
				if ("translation".equals(getExtension(uri.toString()))) {
					InputStream is = null;
					try {
						migrationLogger.trace("Loading dependent translations from " + uri); //$NON-NLS-1$
						is = uri.toURL().openStream();
						parser.parse(is, keyToMessagesMap);
					} catch (Exception ex) {
						migrationLogger.error("Unable to read completely the dependent translations file " + uri, ex); //$NON-NLS-1$
					} finally {
						if (is != null) {
							try {
								is.close();
							} catch (IOException ex) {
								migrationLogger.warn("Unable to close properly the input stream for " + uri, ex); //$NON-NLS-1$
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Walks through all models (fragments, modules and pages) in order to find
	 * all declared translation keys and put them into the map
	 * <code>keyToEObjectsMap</code>. This map is usefull to detect then keys
	 * that are no more declared in the local and all dependent translation
	 * files.
	 */
	private void loadKeysFromPageModels() {

		PageModelParser modelParser = new PageModelParser();

		// load all page designer's models with the local scope.
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, new String[] {
				PageConstants.FRAGMENT_FILE_EXTENSION, PageConstants.MODULE_FILE_EXTENSION,
				PageConstants.PAGE_FILE_EXTENSION });
		Set<IOfsModelResource> ofsResources = lookup.getAllOfsModelResources(IOfsProject.SCOPE_PROJECT);

		// Walks through each model resource
		for (IOfsModelResource ofsRes : ofsResources) {
			try {

				/**
				 * The fragmentMap will contain for each xmi-id a set of
				 * translation keys
				 */
				Map<String, Set<String>> fragmentMap = new HashMap<String, Set<String>>();
				InputStream is = ofsRes.getContents();
				modelParser.parse(is, fragmentMap);
				is.close();

				// load the root model element
				Model model = (Model) ofsRes.getEMFModel().get(0);

				// rebuild the map translation-key ==> set of model element
				for (String fragment : fragmentMap.keySet()) {
					EObject element = model.eResource().getEObject(fragment);
					if (element != null) {
						for (String kkey : fragmentMap.get(fragment)) {
							Set<EObject> eObjSet = keyToEObjectsMap.get(kkey);
							if (eObjSet == null) {
								eObjSet = new HashSet<EObject>();
								keyToEObjectsMap.put(kkey, eObjSet);
							}
							eObjSet.add(element);
						}
					}
				}

			} catch (Exception ex) {
				migrationLogger
						.error(
								"Unable read completely the model" + ofsRes.getURI() + " for project: " + ofsProject.getName(), ex); //$NON-NLS-1$
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @return the OFs project
	 */
	protected final IOfsProject getProject() {
		return this.ofsProject;
	}

	/**
	 * Returns the locale given a language
	 * 
	 * @param language
	 * @return the locale
	 */
	protected final Locale getLocale(String language) {
		Locale locale = locales.get(language);
		if (null == locale) {
			locale = new Locale(language);
			locales.put(language, locale);
		}
		return locale;
	}

	/**
	 * @param eObj
	 * @return
	 */
	protected final ITranslation getTranslation(EObject eObj) {
		if (null == manager) {
			manager = TranslationCore.getTranslationManager(getProject().getProject());
		}
		return manager.getTranslation(eObj);
	}

	/**
	 * Converts a message key to translation kind
	 * 
	 * @param messageKey
	 *            the key to be converted
	 * @return the translation kind
	 */
	protected ITranslationKind getTranslationKindFromKey(String messageKey) {
		ITranslationKind kind = null;
		if (messageKey.endsWith(".caption")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".name")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".text")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".tooltip")) { //$NON-NLS-1$
			kind = ITranslationKind.TEXT;
		} else if (messageKey.endsWith(".confirm")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".Module")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".Label")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.endsWith(".Button")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		} else if (messageKey.startsWith("module_")) { //$NON-NLS-1$
			kind = ITranslationKind.NAME;
		}
		return kind;
	}

	/**
	 * Returns a translation identifier (tid) given a message key. The returned
	 * tid is equals to the number for all numerical key, otherwise it is equals
	 * to zero.
	 * <p>
	 * A numerical key start by a digit. If the number has the wrong format, the
	 * tid is set to zero.
	 * <p>
	 * All tid equals to zero will be regenerated later during the migration
	 * processing.
	 * 
	 * @param messageKey
	 *            the message key
	 * @return the translation identifier (tid) given a message key.
	 */
	protected long getTranslationIdFromKey(String messageKey) {
		long tid = 0L;
		if (messageKey.startsWith("module_")) { //$NON-NLS-1$
			// this is very old CDM key
			messageKey = messageKey.replace("module_", ""); //$NON-NLS-1$
		}
		int pos = messageKey.indexOf("."); //$NON-NLS-1$
		if (pos > -1) {
			String digits = messageKey.substring(0, pos);
			try {
				tid = Long.parseLong(digits);
			} catch (NumberFormatException ex) {
				// ignore.
			}
		}
		return tid;
	}

	/**
	 * @param key
	 * @return
	 */
	protected Set<EObject> findModelElements(String key) {

		if (!keysLoaded) {
			loadKeysFromDependentTranslationsFiles();
			loadKeysFromPageModels();
			keysLoaded = true;
		}

		Set<EObject> elements = keyToEObjectsMap.get(key);
		if (elements == null) {
			elements = new HashSet<EObject>();
		}

		return elements;
	}

	@Override
	public boolean acceptKey(String key) {
		return StringUtils.isNotBlank(key) && (key.startsWith("module_") || /*
																			 * CDM:
																			 * very
																			 * old
																			 * key
																			 *///$NON-NLS-1$
				(Character.isDigit(key.charAt(0)) && !key.endsWith(".message"))); /*
																				 * key
																				 * for
																				 * visual
																				 * rule
																				 *///$NON-NLS-1$
	}

	@Override
	public boolean migrateTranslation(TranslationVO vo) {

		boolean processed = false;

		String key = vo.key;
		Locale locale = getLocale(vo.language);
		String text = vo.text;

		if (StringUtils.isNotEmpty(vo.text)) {
			ITranslationKind kind = getTranslationKindFromKey(key);
			if (kind != null) {
				Set<EObject> elements = findModelElements(key);
				if (elements.size() > 0) {
					long[] tidArray = new long[elements.size()];
					if (tidArray.length > 0) {
						// the old key becomes the tid, a new tid will be
						// generated
						// if the same key is shared by several widgets/events
						// to avoid later collision during xsp/nls generation.
						tidArray[0] = getTranslationIdFromKey(key);
					}
					int tidIndex = 0;
					long prevTid = 0;
					for (EObject element : elements) {
						ITranslation translation = getTranslation(element);
						if (translation != null) {
							long tid = tidArray[tidIndex++];
							if (tid == 0) {
								tid = System.nanoTime();
								// ensure the new tid is different from the
								// previous one.
								while (tid == prevTid) {
									tid = System.nanoTime();
								}
								prevTid = tid;
							}
							if (element instanceof Widget) {
								((Widget) element).setTranslationId(tid);
							} else if (element instanceof Event) {
								((Event) element).setTranslationId(tid);
							}
							try {
								Resource resource = element.eResource();
								translation.setText(kind, locale, text);
								modifiedResources.add(resource);
								processed = true;
							} catch (TranslationException ex) {
								migrationLogger.error("Unable to define the translation in the model "
										+ element.eResource().getURI(), ex);
								vo.errorStatus = "Unable to define the translation in the model [" + ex.getMessage()
										+ "] (might be found later in another project)";
								migrationLogger.error("Unable to define the translation in the model " + element.eResource().getURI(), ex);
								vo.errorStatus = "Unable to define the translation in the model ["+ex.getMessage()+"] (might be found later in another project)";
							}
						}
					}
				} else {
					vo.errorStatus = "Unable to find the associated widget or event in models (might be found later in another project)";
				}
			} else {
				vo.errorStatus = "Unable to define the translation kind by the suffix"; //$NON-NLS-1$
			}
		} else {
			vo.errorStatus = "Empty text are not supported"; //$NON-NLS-1$
		}

		if (!processed && StringUtils.isEmpty(vo.errorStatus)) {
			vo.errorStatus = "Unable to find the associated widget or event (might be found later in another project)";
		}

		return processed;
	}

	@Override
	public List<TranslationVO> release() throws TranslationMigrationException {

		List<TranslationVO> errors = new ArrayList<TranslationVO>();

		/**
		 * Walk through all keys extracted from all dependent translation files,
		 * and check if there exist a widget or event that has a reference to
		 * the key. If it is the case the translation is migrated.
		 * 
		 * Only numerical key are taken into account
		 * 
		 */
		for (String key : keyToMessagesMap.keySet()) {
			Set<EObject> eObjSet = keyToEObjectsMap.get(key);
			if (eObjSet != null) {
				long[] tidArray = new long[eObjSet.size()];
				if (tidArray.length > 0) {
					// the old key becomes the tid, a new tid will be generated
					// if the same key is shared by several widgets/events
					// to avoid later collision during xsp/nls generation.
					tidArray[0] = getTranslationIdFromKey(key);
				}
				int tidIndex = 0;
				long prevTid = 0;
				for (EObject eObj : eObjSet) {
					Resource resource = eObj.eResource();
					ITranslation translation = getTranslation(eObj);
					if (translation != null) {
						long tid = tidArray[tidIndex++];
						if (tid == 0) {
							tid = System.nanoTime();
							// ensure the generated tid is different from the
							// previous one.
							while (tid == prevTid) {
								tid = System.nanoTime();
							}
							prevTid = tid;
						}
						if (eObj instanceof Widget) {
							Widget w = (Widget) eObj;
							if (w.getTranslationId() == 0) {
								w.setTranslationId(tid);
								modifiedResources.add(resource);
							}
						} else if (eObj instanceof Event) {
							Event e = (Event) eObj;
							if (e.getTranslationId() == 0) {
								e.setTranslationId(tid);
								modifiedResources.add(resource);
							}
						}
						try {
							Map<String, String> msgMap = keyToMessagesMap.get(key);
							for (String language : msgMap.keySet()) {
								String text = msgMap.get(language);
								ITranslationKind kind = getTranslationKindFromKey(key);
								if (kind != null) {
									translation.setText(kind, getLocale(language), text);
									modifiedResources.add(resource);
								}
							}
						} catch (TranslationException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}
				}
			}
		}

		/*
		 * Builds the error list. For each translation key defined in
		 * keyToMessagesMap that has not been migrated, because no message was
		 * found in translation files, a new instance of a TranslationVO is
		 * added to the error list.
		 */
		for (String key : keyToEObjectsMap.keySet()) {
			boolean isDigitKey = Character.isDigit(key.charAt(0));
			for (EObject eObj : keyToEObjectsMap.get(key)) {
				ITranslation translation = getTranslation(eObj);
				if (translation != null) {
					// this eObj can have translation, so we check if its
					// tid
					// is not zero for the current key
					long tid = 0;
					if (eObj instanceof Widget) {
						tid = ((Widget) eObj).getTranslationId();
					} else if (eObj instanceof Event) {
						tid = ((Event) eObj).getTranslationId();
					}
					if (tid == 0) {
						if (isDigitKey) {
							/*
							 * the tid should be zero only for non numerical
							 * key. So a key declared in the model has not
							 * been migrated, because it doesn't exists in
							 * any dependents translation files.
							 */

							String location = "";
							Resource res = eObj.eResource();
							if (res != null) {
								URI uri = res.getURI();
								location = uri.toString() + "#" + res.getURIFragment(eObj);
							}

							TranslationVO tvo = new TranslationVO(key, "???", "???");
							tvo.errorStatus = "Unable to find a message for the key in all translation files ["
									+ location + "] ";
							if (eObj instanceof Widget) {
								tvo.errorStatus += " widget[" + ((Widget) eObj).getTypeName() + "]";
							} else if (eObj instanceof Event) {
								Widget w = ((Event) eObj).getWidget();
								tvo.errorStatus += " Widget[" + w.getTypeName() + "] Event["
										+ ((Event) eObj).getEventName() + "]";
							}
							tvo.errorStatus += " (might be found later in another project)";
							errors.add(tvo);
						} else {
							/*
							 * The key is certainly a domain key. So what ?
							 */
						}
					}
				}
			}
		}

		/*
		 * Do the work within an operation because this is a long running
		 * activity that modifies the workbench.
		 */
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				// Save all modified resources
				Map<String, Object> saveOptions = new HashMap<String, Object>();
				saveOptions.put(XMLResource.OPTION_URI_HANDLER, new ResourceEntityHandlerImpl("")); //$NON-NLS-1$
				for (Resource resource : modifiedResources) {
					try {
						String msg = "Saving translations in " + resource.getURI();
						monitor.subTask(msg);
						migrationLogger.trace(msg); //$NON-NLS-1$
						migrationLogger.trace("Saving translations in " + resource.getURI());
						resource.save(saveOptions);
						resource.unload();
					} catch (IOException ex) {
						migrationLogger.error("Unable to save translations in" + resource.getURI(), ex); //$NON-NLS-1$
						//throw new TranslationMigrationException("Unable to save " + resource.getURI(), e); //$NON-NLS-1$
						//throw new CoreException("Unable to save " + resource.getURI(), e); //$NON-NLS-1$
					}
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable, null);
		} catch (CoreException e) {
			migrationLogger.error("Unable to save models", e); //$NON-NLS-1$
		}

		runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				// Ensure all new models are up to date
				ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, new String[] {
						PageConstants.FRAGMENT_FILE_EXTENSION, PageConstants.MODULE_FILE_EXTENSION,
						PageConstants.PAGE_FILE_EXTENSION });
				Set<IOfsModelResource> ofsResources = lookup.getAllOfsModelResources(IOfsProject.SCOPE_PROJECT);
				// Save all modified resources
				Map<String, Object> saveOptions = new HashMap<String, Object>();
				saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
						Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
				saveOptions.put(XMLResource.OPTION_URI_HANDLER, new ResourceEntityHandlerImpl("")); //$NON-NLS-1$
				for (IOfsModelResource ofsRes : ofsResources) {
					try {
						String msg = "Checking model " + ofsRes.getURI();
						monitor.subTask(msg);
						migrationLogger.trace(msg);
						Model model = (Model) ofsRes.getEMFModel().get(0);
						if (model.eContents().size() > 0) {
							Widget widget = (Widget) model.getWidget();
							new WidgetFactory().update(widget);
							model.eResource().save(saveOptions);
						}
					} catch (IOException ex) {
						migrationLogger.error("Unable to save model " + ofsRes.getURI(), ex); //$NON-NLS-1$
					} catch (InvalidMetamodelVersionException ex) {
						// can never occur
					} finally {
						ofsRes.unload();
					}
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable, null);
		} catch (CoreException e) {
			migrationLogger.error("Unable to save models", e); //$NON-NLS-1$
		}
		return errors;
	}

	/**
	 * @param logger
	 * @param ofsProject
	 */
	public MessageRepository(Logger logger, IOfsProject ofsProject) {
		if (null == logger || null == ofsProject) {
			throw new IllegalArgumentException("Arguments cannot be null"); //$NON-NLS-1$
		}
		this.migrationLogger = logger;
		this.ofsProject = ofsProject;
		keyToEObjectsMap = new HashMap<String, Set<EObject>>();
		keyToMessagesMap = new HashMap<String, Map<String, String>>();
	}

}
