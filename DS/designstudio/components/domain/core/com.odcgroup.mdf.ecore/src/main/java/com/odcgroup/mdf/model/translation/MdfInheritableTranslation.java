package com.odcgroup.mdf.model.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslationHelper.IMdfTranslationHelperAdapter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.InheritableTranslation;

/**
 * Translation for MDF domain models
 * @author yan
 */
public class MdfInheritableTranslation extends InheritableTranslation {

	private static final String READ_ONLY_DOMAINS = "(aaaentities.*|aaaenumerations.*|aaaformats.*)";
	
    public static final String NAMESPACE_URI = "http://www.odcgroup.com/mdf/translation";
    public static final String TRANSLATION_LABEL = "Label";
    public static final String TRANSLATION_TOOLTIP = "Tooltip";
    
	private MdfModelElement mdfModelElement;
	
	private MdfTranslationHelperAdapter helperAdapter = this.new MdfTranslationHelperAdapter();

	/**
	 * Adapter used by the helper to manipulate InheritableTranslation (as well as standard translations)
	 */
	class MdfTranslationHelperAdapter implements IMdfTranslationHelperAdapter {

		public MdfModelElement getMdfModelElement() {
			return mdfModelElement;
		}

		public void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
			MdfInheritableTranslation.this.fireChangeTranslation(kind, locale, oldText, newText);
		}

		public ITranslationKind[] getAllKinds() {
			return MdfInheritableTranslation.this.getAllKinds();
		}
		
		public IProject getProject() {
			return MdfInheritableTranslation.this.getProject();
		}

		@Override
		public String getAnnotationFromKind(ITranslationKind kind) {
			return MdfInheritableTranslation.this.getAnnotationFromKind(kind);
		}
	}
	
	protected String getAnnotationFromKind(ITranslationKind kind) {
		String result = null;
		switch (kind) {
		case NAME:
			result = TRANSLATION_LABEL;
			break;
		case TEXT:
			result = TRANSLATION_TOOLTIP;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}

	/**
	 * Create the inheritable translation
	 * @param provider
	 * @param project
	 */
	public MdfInheritableTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		super(provider, project);
		if (null == project || null == mdfModelElement) {
			throw new IllegalArgumentException("Arguments can't be null");
		}
		this.mdfModelElement = mdfModelElement;
	}
	
	protected String doDelete(ITranslationKind kind, Locale locale) throws TranslationException {
		return MdfTranslationHelper.getInstance().delete(helperAdapter, kind, locale);
	}

	protected ITranslation doGetDelegate() throws TranslationException {
		return MdfTranslationHelper.getInstance().doGetDelegate(helperAdapter);
	}
	
	protected String doGetText(ITranslationKind kind, Locale locale) throws TranslationException {
		return MdfTranslationHelper.getInstance().getText(helperAdapter, kind, locale);
	}

	protected String doSetText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		return MdfTranslationHelper.getInstance().setText(helperAdapter, kind, locale, newText);
	}
	
	@Override
	public boolean acceptRichText(ITranslationKind kind) {
		return ITranslationKind.NAME.equals(kind) && mdfModelElement instanceof MdfDatasetMappedProperty;
	}

	public ITranslationKind[] getTranslationKinds() {
		ITranslationKind[] kinds = {};
		if (mdfModelElement instanceof MdfDataset) {
			kinds = new ITranslationKind[] {ITranslationKind.NAME};
		} else if (mdfModelElement instanceof MdfDatasetMappedProperty) {
			kinds = new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.TEXT};
		} else {
			throw new IllegalArgumentException("MdfInheritableTranslation only supports MdfDataset and MdfDatasetMappedProperty");
		}
		return kinds;
	}

	public Object getOwner() {
		return mdfModelElement;
	}

	public boolean isInheritable() {
		return true;
	}

	public boolean isReadOnly() throws TranslationException {
		return MdfTranslationHelper.getInstance().isReadOnly(helperAdapter);
	}

	public boolean isProtected() throws TranslationException {
		return MdfTranslationHelper.getInstance().isProtected(helperAdapter);
	}

}
