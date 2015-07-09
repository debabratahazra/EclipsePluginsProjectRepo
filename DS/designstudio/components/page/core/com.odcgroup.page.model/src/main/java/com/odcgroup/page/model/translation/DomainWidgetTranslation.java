package com.odcgroup.page.model.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.UnexpectedTranslationException;
import com.odcgroup.translation.core.translation.InheritableTranslation;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * The class <code>DomainWidgetTranslation</code> implements the interface
 * <code>ITranslation</code> manages the translations of a <code>Widget</code>.
 * It inherits the behavior defined in class <code>InheritableTranslation</code>
 * in order to support inherited translation of the bounded domain element.
 * 
 * @author atr
 */
public class DomainWidgetTranslation extends InheritableTranslation implements ITranslation, ITranslationChangeListener {

	/** */
	private static final Map<TranslationSupport, ITranslationKind[]> TRANSLATION_KINDS_MAP;
	static {
		TRANSLATION_KINDS_MAP = new HashMap<TranslationSupport, ITranslationKind[]>();
		TRANSLATION_KINDS_MAP.put(TranslationSupport.NONE, new ITranslationKind[] {});
		TRANSLATION_KINDS_MAP.put(TranslationSupport.NAME, new ITranslationKind[] { ITranslationKind.NAME });
		TRANSLATION_KINDS_MAP.put(TranslationSupport.TEXT, new ITranslationKind[] { ITranslationKind.TEXT });
		TRANSLATION_KINDS_MAP.put(TranslationSupport.NAME_AND_TEXT, new ITranslationKind[] { ITranslationKind.NAME,
				ITranslationKind.TEXT });
	};

	/** The wrapped widget */
	private Widget widget;

	/** */
	private Property dataset;
	
	/** */
	private Property datasetProperty;

	private String enumName;

	/**
	 * @param widget
	 * @param locale
	 * @return Translation
	 */
	private final Translation findLabel(Widget widget, Locale locale) {
		Translation result = null;
		for (Translation label : widget.getLabels()) {
			if (locale.toString().equals(label.getLanguage())) {
				result = label;
				break;
			}
		}
		return result;
	}

	/**
	 * Update the translation identifier
	 */
	private void updateTranslationId() {
		synchronized (widget) {
			int count = widget.getLabels().size() + widget.getToolTips().size();
			if (count == 0) {
				widget.setTranslationId(0);
			} else if (widget.getTranslationId() == 0) {
				widget.setTranslationId(System.nanoTime());
			}
		}
	}
	
	/**
	 * @param locale
	 * @param newValue
	 * @return the old value of the label
	 */
	private final String setLabel(Locale locale, String newValue) {
		String oldValue = null;
		Translation label = findLabel(this.widget, locale);
		if (label != null) {
			oldValue = label.getMessage();
			label.setMessage(newValue);
		} else {
			String language = locale.toString();
			label = ModelPackage.eINSTANCE.getModelFactory().createTranslation();
			label.setLanguage(language);
			label.setMessage(newValue);
			this.widget.getLabels().add(label);
			updateTranslationId();
		}
		return oldValue;
	}

	/**
	 * Delete the designated translation and notify listeners
	 * 
	 * @param kind
	 *            the translation kind
	 * @param locale
	 *            the locale
	 * @return the text of the deleted label
	 */
	private final String deleteLabel(ITranslationKind kind, Locale locale) {
		String oldText = null;
		String language = locale.toString();
		List<Translation> labels = widget.getLabels();
		for (Translation label : labels) {
			if (language.equals(label.getLanguage())) {
				oldText = label.getMessage();
				labels.remove(label);
				updateTranslationId();
				fireChangeTranslation(kind, locale, oldText, null);
				break;
			}
		}
		return oldText;
	}

	/**
	 * @param widget
	 * @param locale
	 * @return Translation
	 */
	private final Translation findToolTip(Widget widget, Locale locale) {
		Translation result = null;
		for (Translation toolTip : widget.getToolTips()) {
			if (locale.toString().equals(toolTip.getLanguage())) {
				result = toolTip;
				break;
			}
		}
		return result;
	}

	/**
	 * @param locale
	 * @param newValue
	 * @return the old value of the toolTip
	 */
	private final String setToolTip(Locale locale, String newValue) {
		String oldValue = null;
		Translation toolTip = findToolTip(this.widget, locale);
		if (toolTip != null) {
			oldValue = toolTip.getMessage();
			toolTip.setMessage(newValue);
		} else {
			String language = locale.toString();
			toolTip = ModelPackage.eINSTANCE.getModelFactory().createTranslation();
			toolTip.setLanguage(language);
			toolTip.setMessage(newValue);
			this.widget.getToolTips().add(toolTip);
			updateTranslationId();
		}
		return oldValue;
	}

	/**
	 * Delete the designated translation and notify listeners
	 * 
	 * @param kind
	 *            the translation kind
	 * @param locale
	 *            the locake
	 * @return the text of the deleted toolTip
	 */
	private final String deleteToolTip(ITranslationKind kind, Locale locale) {
		String oldText = null;
		String language = locale.toString();
		List<Translation> toolTips = widget.getToolTips();
		for (Translation toolTip : toolTips) {
			if (language.equals(toolTip.getLanguage())) {
				oldText = toolTip.getMessage();
				toolTips.remove(toolTip);
				updateTranslationId();
				fireChangeTranslation(kind, locale, toolTip.getMessage(), null);
				break;
			}
		}
		return oldText;
	}

	@Override
	protected ITranslation doGetDelegate() throws TranslationException {
		ITranslation delegate = null;
		MdfModelElement element = null;
		IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
		if (ofsProject != null) {
			element = widget.findDatasetProperty(DomainRepository.getInstance(ofsProject), dataset.getValue(), datasetProperty.getValue());
			if (!StringUtils.isEmpty(enumName)) {
				if (element instanceof MdfDatasetMappedProperty) {
					MdfDatasetMappedProperty mProperty = (MdfDatasetMappedProperty) element;
					if(mProperty.getType() instanceof MdfEnumeration) {
						element = ((MdfEnumeration) mProperty.getType()).getValue(enumName);
					}
				}	
			}
			if (element != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(getProject());
				delegate = manager.getTranslation(element);
			} else {
				String widgetName = getWidget().getTypeName();
				String datasetName = dataset.getValue();
				String attributeName = datasetProperty.getValue();
				Resource resource = getWidget().eResource();
				String uri = resource != null ? resource.getURI().toString() : "???";
				throw new UnexpectedTranslationException(
						"Cannot load inherited translation dataset=["+datasetName+"] attribute=["+attributeName+"] widget=["+widgetName+"] uri=["+uri+"]");
			}
		} else {
			String widgetName = getWidget().getTypeName();
			Resource resource = getWidget().eResource();
			String uri = resource != null ? resource.getURI().toString() : "???";
			throw new UnexpectedTranslationException("Cannot load inherited translation widget=["+widgetName+"] uri=["+uri+"]");
		}
		return delegate;
	}

	@Override
	protected String doDelete(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;

		switch (kind) {

		case NAME: {
			oldText = deleteLabel(kind, locale);
			break;
		}

		case TEXT: {
			oldText = deleteToolTip(kind, locale);
			break;
		}

		default: {
			break;
		}
		}

		return oldText;
	}

	@Override
	protected String doGetText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		return doGetText(getWidget(), kind, locale);
	}

	@Override
	protected String doSetText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {

		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;

		switch (kind) {

		case NAME: {
			oldText = setLabel(locale, newText);
			fireChangeTranslation(kind, locale, oldText, newText);
			break;
		}

		case TEXT: {
			oldText = setToolTip(locale, newText);
			fireChangeTranslation(kind, locale, oldText, newText);
			break;
		}

		default:
			break;

		}
		return oldText;
	}

	/**
	 * @return the wrapped widget
	 */
	protected final Widget getWidget() {
		return this.widget;
	}

	/**
	 * @param widget
	 * @param kind
	 * @param locale
	 * @return the text of the message
	 * @throws TranslationException
	 */
	protected String doGetText(Widget widget, ITranslationKind kind, Locale locale) throws TranslationException {

		String text = null;

		switch (kind) {

		case NAME: {
			Translation translation = findLabel(widget, locale);
			if (translation != null) {
				text = translation.getMessage();
			}
			break;
		}

		case TEXT: {
			Translation translation = findToolTip(widget, locale);
			if (translation != null) {
				text = translation.getMessage();
			}
			break;
		}

		default: {
			break;
		}

		}
		return text;
	}
	
	@Override
	public boolean acceptRichText(ITranslationKind kind) {
		return ITranslationKind.NAME.equals(kind) && widget.getType().isRichtext();
	}

	@Override
	public final ITranslationKind[] getTranslationKinds() {
		WidgetType wt = widget.getType();
		return TRANSLATION_KINDS_MAP.get(wt.getTranslationSupport());
	}

	@Override
	public final Object getOwner() {
		return this.widget;
	}

	@Override
	public final boolean isInheritable() {
		return true;
	}

	@Override
	public boolean isReadOnly() throws TranslationException {
		if (isProtected())
			return true;
		
		Resource resource = getWidget().eResource();
		if (resource != null) {
			URI resourceUri = resource.getURI();
			if(!ModelURIConverter.isModelUri(resourceUri)) {
				return true;
			}
			URI uri = ModelURIConverter.toResourceURI(resourceUri);
			IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
			IOfsModelResource ofsResource = 
				ofsProject.getModelResourceSet().getOfsModelResource(uri, IOfsProject.SCOPE_PROJECT);
			if (ofsResource != null) {
				return ofsResource.isReadOnly();
			} else {
				// the widget is in a standard project.
				return true;
			}
		} else {
			// the widget is either in a jar or not yet attach to a resource
			return true;
		}
	}
	
	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}

	/**
	 * Receives notification change from the delegated translation.
	 * <p>
	 * The listeners are notified.
	 */
	@Override
	public void notifyChange(ITranslationChangeEvent event) {
		propagateTranslationChangeEvent(event);
	}

	/**
	 * Constructor
	 * 
	 * @param provider
	 * @param project
	 * @param widget
	 * @param dataset
	 * @param datasetProperty
	 */
	public DomainWidgetTranslation(ITranslationProvider provider, IProject project, Widget widget, Property dataset, Property datasetProperty, String enumName) {
		this(provider, project, widget, dataset, datasetProperty);
		this.enumName = enumName;
	}	
	
	/**
	 * Constructor
	 * 
	 * @param provider
	 * @param project
	 * @param widget
	 * @param dataset
	 * @param datasetProperty
	 */
	public DomainWidgetTranslation(ITranslationProvider provider, IProject project, Widget widget, Property dataset, Property datasetProperty) {
		super(provider, project);

		if (widget == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		this.widget = widget;
		this.dataset = dataset;
		this.datasetProperty = datasetProperty;

		// install listeners on domain properties
		EList<Adapter> datasetAdapters = dataset.eAdapters();
		Adapter datasetListener = new DomainPropertyListener(this);
		datasetAdapters.add(datasetListener);
		
		EList<Adapter> datasetPropertyAdapters = datasetProperty.eAdapters();
		Adapter datasetPropertyListener = new DomainPropertyListener(this);
		datasetPropertyAdapters.add(datasetPropertyListener);
	}

}
