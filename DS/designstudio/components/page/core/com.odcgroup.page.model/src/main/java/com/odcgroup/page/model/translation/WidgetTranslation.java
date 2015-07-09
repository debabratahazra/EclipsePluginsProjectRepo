package com.odcgroup.page.model.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * This class help to read/write messages of a <code>Widget</code>
 * 
 * @author atr
 */
public class WidgetTranslation extends BaseTranslation implements ITranslation {

	/** */
	//private static Logger logger = LoggerFactory.getLogger(WidgetTranslation.class);

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
	 * @param locale
	 * @param newValue
	 * @return the old value of the label
	 */
	private final String setLabel(Locale locale, String newValue) {
		String oldValue = null;
		Translation label = findLabel(widget, locale);
		if (label != null) {
			oldValue = label.getMessage();
			label.setMessage(newValue);
		} else {
			String language = locale.toString();
			label = ModelPackage.eINSTANCE.getModelFactory().createTranslation();
			label.setLanguage(language);
			label.setMessage(newValue);
			widget.getLabels().add(label);
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
		List<Translation> labels = widget.getLabels();
		for (Translation label : labels) {
			if (locale.toString().equals(label.getLanguage())) {
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
		List<Translation> toolTips = widget.getToolTips();
		for (Translation toolTip : toolTips) {
			if (locale.toString().equals(toolTip.getLanguage())) {
				oldText = toolTip.getMessage();
				toolTips.remove(toolTip);
				updateTranslationId();
				fireChangeTranslation(kind, locale, toolTip.getMessage(), null);
				break;
			}
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
	protected String getText(Widget widget, ITranslationKind kind, Locale locale) throws TranslationException {

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
	
	protected ITranslationKind[] getSupportedTranslationKinds() {
		WidgetType wt = getWidget().getType();
		return TRANSLATION_KINDS_MAP.get(wt.getTranslationSupport());		
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {

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
	public boolean acceptRichText(ITranslationKind kind) {
		return ITranslationKind.NAME.equals(kind) && widget.getType().isRichtext();
	}

	@Override
	public ITranslationKind[] getTranslationKinds() {
		return getSupportedTranslationKinds();
	}

	@Override
	public final Object getOwner() {
		return this.widget;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		return getText(getWidget(), kind, locale);
	}

	@Override
	public final boolean isInheritable() {
		return false;
	}

	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		return false;
	}

	@Override
	public final String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		return null;
	}
	
	@Override
	public boolean isReadOnly() throws TranslationException {
		if (isProtected())
			return true;
		
		Resource resource = getWidget().eResource();
		if (resource != null) {
			URI uri = ModelURIConverter.toResourceURI(resource.getURI());
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
			return false;
		}
	}

	@Override
	public boolean isProtected() throws TranslationException {
		return false;
	}
	
	@Override
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {

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
	 * Constructor
	 * 
	 * @param provider
	 * @param project
	 *            the project.
	 * @param widget
	 *            the wrapped widget
	 */
	public WidgetTranslation(ITranslationProvider provider, IProject project, Widget widget) {
		super(provider, project);
		if (widget == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.widget = widget;
	}

}
