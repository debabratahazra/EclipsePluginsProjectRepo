package com.odcgroup.t24.enquiry.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;

/**
 *
 * @author phanikumark
 *
 */
public class DrillDownTranslation  extends BaseVersionTranslation implements ITranslation {	
	
	private DrillDown drilldown;


	public DrillDownTranslation(ITranslationProvider provider, IProject project, DrillDown drilldown) {
		super(provider, project);
		if (drilldown == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.drilldown = drilldown;	
	}

	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {
		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		String text = null;		
		switch (kind) {
			case NAME: {
				LocalTranslation localTranslation = findTranslation(locale, 
						(LocalTranslations) drilldown.getDescription());
				if (localTranslation != null) {
					text = localTranslation.getText();
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
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = null;
		switch (kind) {
			case NAME: {
				if (drilldown.getDescription() == null) {
					drilldown.setDescription(TranslationDslPackage.eINSTANCE.getTranslationDslFactory()
							.createLocalTranslations());
				}
				oldText = setTranslation((LocalTranslations) drilldown.getDescription(), locale, newText);
				fireChangeTranslation(kind, locale, oldText, newText);
				break;
			}
			default:
				break;
		}
		return oldText;
	}

	@Override
	public Object getOwner() {
		return drilldown.eContainer();
	}

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		String oldText = null;
		LocalTranslations localTranlations =null;  
		switch (kind) {
			case NAME: {
				localTranlations=(LocalTranslations) drilldown.getDescription();
				oldText = deleteTranslation(kind, locale, localTranlations);
				//set the description to null if no description translation.
				if(localTranlations != null)
				if(localTranlations.getTranslations().isEmpty()){
					drilldown.setDescription(null); 
				}
				break;
				
			}
	
			default: {
				break;
			}
		}
		return oldText;
	}
	
	@Override
	public boolean isReadOnly() throws TranslationException {
		return false;
	}

	@Override
	protected ITranslationKind[] getTranslationKinds() {
		return new ITranslationKind[] { ITranslationKind.NAME };
	}

}
