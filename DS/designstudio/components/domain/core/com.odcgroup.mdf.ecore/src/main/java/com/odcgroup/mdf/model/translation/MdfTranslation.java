package com.odcgroup.mdf.model.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslationHelper.IMdfTranslationHelperAdapter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * Translation for MDF domain models
 * @author yan
 */
public class MdfTranslation extends BaseTranslation implements ITranslation {
	
    public static final String NAMESPACE_URI = "http://www.odcgroup.com/mdf/translation";
    public static final String TRANSLATION_LABEL = "Label";
    public static final String TRANSLATION_TOOLTIP = "Tooltip";
	
	protected MdfModelElement mdfModelElement;

	private MdfTranslationHelperAdapter helperAdapter = this.new MdfTranslationHelperAdapter();

	/**
	 * Adapter used by the helper to manipulate standard translations (as well as InheritableTranslation)
	 */
	class MdfTranslationHelperAdapter implements IMdfTranslationHelperAdapter {

		public MdfModelElement getMdfModelElement() {
			return mdfModelElement;
		}

		public void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
			MdfTranslation.this.fireChangeTranslation(kind, locale, oldText, newText);
		}

		public ITranslationKind[] getAllKinds() {
			return MdfTranslation.this.getAllKinds();
		}
		
		public IProject getProject() {
			return MdfTranslation.this.getProject();
		}

		@Override
		public String getAnnotationFromKind(ITranslationKind kind) {
			return MdfTranslation.this.getAnnotationFromKind(kind);
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
	 * Create a translation for MDF domain element
	 * @param project
	 * @param mdfModelElement
	 */
	public MdfTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		super(provider, project);
		if (null == project || null == mdfModelElement) {
			throw new IllegalArgumentException("Arguments can't be null");
		}
		this.mdfModelElement = mdfModelElement;
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		return MdfTranslationHelper.getInstance().delete(helperAdapter, kind, locale);
	}
	
	@Override
	public boolean acceptRichText(ITranslationKind kind) {
		return ITranslationKind.NAME.equals(kind) && mdfModelElement instanceof MdfDatasetDerivedProperty;
	}

	@Override
	public final Object getOwner() {
		return mdfModelElement;
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {
		return MdfTranslationHelper.getInstance().getText(helperAdapter, kind, locale);
	}
	
	@Override
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		return MdfTranslationHelper.getInstance().setText(helperAdapter, kind, locale, newText);
	}

	@Override
	public final boolean isInheritable() {
		return false;
	}

	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {
		return false;
	}	
	
	@Override
	public final String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		return null;
	}		

	@Override
	public boolean isReadOnly() {
		return MdfTranslationHelper.getInstance().isReadOnly(helperAdapter);
	}
	
	@Override
	public boolean isProtected() {
		return MdfTranslationHelper.getInstance().isProtected(helperAdapter);
	}

	@Override
	protected ITranslationKind[] getTranslationKinds() {
		ITranslationKind[] kinds = {};
		if (mdfModelElement instanceof MdfDomain || 
			mdfModelElement instanceof MdfClass  ||
			mdfModelElement instanceof MdfEnumeration) {
			kinds = new ITranslationKind[] {ITranslationKind.NAME }; 
		} else {
			kinds = new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.TEXT };
		}
		return kinds;
	}
	
}
