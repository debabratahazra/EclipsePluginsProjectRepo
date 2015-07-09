package com.odcgroup.translation.ui.editor.controls;

import java.util.Comparator;
import java.util.Locale;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.translation.ui.editor.model.ITranslationTableItem;

/**
 *
 * @author pkk
 *
 */
public class TranslationComparator implements Comparator<ITranslationTableItem> {
	/**  */	
	private int direction = 1;	
	/** */
	private Locale locale;

	/**
	 * @param locale
	 */
	public TranslationComparator(Locale locale) {
		this.locale = locale;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ITranslationTableItem item1, ITranslationTableItem item2) {
		String text1 = "";
		String text2 = "";
		if (locale != null) {
			text1 = item1.getText(locale);
			text2 = item2.getText(locale);
			if (text1.equals(text2) || text1 == text2) {
				return doCompareOnOwner(item1, item2);
			} else {
				return (text1).compareToIgnoreCase(text2)*direction;
			}
		} else {
			return doCompareOnOwner(item1, item2);
		}
	}
	
	/**
	 * @param item1
	 * @param item2
	 * @return
	 */
	private int doCompareOnOwner(ITranslationTableItem item1, ITranslationTableItem item2) {
		String text1 =  getCompareString((EObject)item1.getTranslation().getOwner());
		String text2 =  getCompareString((EObject)item2.getTranslation().getOwner());
		return (text1).compareToIgnoreCase(text2)*direction;
	}
	
	/**
	 * @param object
	 * @return
	 */
	private String getCompareString(EObject object) {
		return object.toString().replaceAll(object.getClass().getName(), "")
				.replaceAll(Integer.toHexString(object.hashCode()), "");
	}

	/**
	 * 
	 */
	public void toggleDirection() {
		direction *= -1;
	}
	
	/**
	 * @return
	 */
	public int getSortDirection() {
		return direction;
	}

}
