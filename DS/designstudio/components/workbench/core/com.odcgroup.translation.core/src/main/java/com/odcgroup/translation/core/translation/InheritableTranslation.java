package com.odcgroup.translation.core.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.UnexpectedTranslationException;

/**
 * The class <code>InheritableTranslation</code> helps to implement inheritable
 * translations.
 * 
 * @author atr
 */
public abstract class InheritableTranslation extends BaseTranslation implements ITranslationChangeListener {

	/**
	 * The instance variable <code>delegate</code> contains a reference to the
	 * inherited translations of of a dependent objects. This delegate is create
	 * by subclass whenever it is required.
	 */
	private ITranslation delegate = null;

	/**
	 * It is set to <code>true</code> whenever the <code>delegate</code> must be
	 * reloaded, otherwise it must be set to <code>false</code>.
	 * <p>
	 * Invariant: if reload is true then delegate is null.
	 */
	private boolean reload = true;

	/**
	 * This method is called each time the current <code>delegate</code> is no
	 * longer required, and so can be released. The <code>reload</code> is then
	 * set to <code>true</code> and existing listeners are notified that the
	 * delegated translation has been released.
	 */
	private synchronized void releaseDelegate() {
		if (delegate != null) {
			delegate.removeTranslationChangeListener(this);
			delegate = null;
		}
		reload = true;
		for (ITranslationKind kind : getAllKinds()) {
			fireChangeTranslation(kind);
		}
	}

	/**
	 * This method is called when the <code>delegate</code> is required. If the
	 * <code>reload</code> is set to <code>true</code> then the delegate is
	 * reloaded (recreated) and listeners are notified that the delegate has
	 * been reloaded.
	 * <p>
	 * 
	 * 
	 * 
	 * @return the reloaded delegate, never <code>null</code>
	 * @throws TranslationException
	 */
	private synchronized ITranslation getDelegate() throws TranslationException {
		if (reload) {

			// check the invariant
			assert null == delegate : "invariant violated: 'delegate' must be null when 'reload' is set to true.";

			/*
			 * Attempt to reload the delegate. If something goes wrong the
			 * method doGetDelagate() will raise an exception
			 */
			try {

				delegate = doGetDelegate();
				if (delegate != null) {
					// listen to any translation changes on this new
					// delegate.
					delegate.addTranslationChangeListener(this);
				}

			} finally {
				/*
				 * DO NOT REMOVE the clause finally. It ensures the reload
				 * is set to <code>false</code> event if something goes
				 * wrong when trying to load the delegate.
				 */

				// no need to reload it, event if something was wrong.
				reload = false;

				/*
				 * Finally, notifies our observer that the delegate is
				 * loaded, even if something was wrong. Indeed this can have
				 * an impact on the rendering part of the application.
				 */
				for (ITranslationKind kind : getAllKinds()) {
					fireChangeTranslation(kind);
				}

			}
		}

		if (null == delegate) {
			/*
			 * This exception is raised ONLY when a reloading is not
			 * necessary AND the last loading failed. This is necessary in
			 * order to inform clients so that they can properly update
			 * their states.
			 */
			throw new UnexpectedTranslationException("The loading of the inherited translation was not possible.");
		}

		assert null != delegate : "The method getDelegate() must NEVER returns null";
		return delegate;
	}

	/**
	 * Subclasses must call this method whenever the delegated translation must
	 * be reloaded for some reasons.
	 */
	public final void invalidateDelegate() {
		releaseDelegate();
	}

	/**
	 * This method must be implemented by subclass. It create a new instance of
	 * the inherited translation and return it.
	 * <p>
	 * If the delegated translation cannot be loaded for any reasons, then a
	 * <code>TranslationException</code> must be raised.
	 * 
	 * @return the inherited translation, NEVER <code>null</code>
	 * 
	 * @throws TranslationException
	 */
	protected abstract ITranslation doGetDelegate() throws TranslationException;

	/**
	 * Removes the designated translation from the underlying object. It returns
	 * also the old value of the message or <code>null</code> if it was not yet
	 * defined.
	 * <p>
	 * If for some reasons, the text of the translation cannot be deleted an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be deleted, , never <code>null</code>
	 * @param locale
	 *            the locale, , never <code>null</code>.
	 * 
	 * @return the text of the deleted message
	 * 
	 * @throws TranslationException
	 */
	protected abstract String doDelete(ITranslationKind kind, Locale locale) throws TranslationException;

	/**
	 * Gets the text of the translation given its kind and locale.
	 * <p>
	 * If there is no translation defined for the designated <code>locale</code>
	 * and <code>kind</code>, an <code>null</code> returned.
	 * <p>
	 * If for some reasons, the text of the translation cannot be determined an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be read, never<code>null</code>
	 * @param locale
	 *            the locale, never <code>null</code>
	 * 
	 * @return the text of the translation
	 * 
	 * @throws TranslationException
	 */
	protected abstract String doGetText(ITranslationKind kind, Locale locale) throws TranslationException;

	/**
	 * This method changes the text of a message identified by its kind of
	 * translation and a locale. It returns always the old value of the message
	 * or <code>null</code> if it is not yet defined. The <code>newText</code>
	 * passed as a parameter cannot be <code>null</code> (in order to delete an
	 * existing translation you have to call the method
	 * {@link ITranslation#delete(ITranslationKind, Locale)}.
	 * <p>
	 * If for some reasons, the text of the translation cannot be changed an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be updated, never <code>null</code>
	 * @param locale
	 *            the locale, never <code>null</code>
	 * @param newText
	 *            the new value of the message, never <code>null</code>.
	 * 
	 * @return the old text of the designated message, or <code>null</code> if
	 *         the text of the message is not yet defined.
	 * 
	 * @throws TranslationException
	 */
	protected abstract String doSetText(ITranslationKind kind, Locale locale, String newText)
			throws TranslationException;

	/**
	 * One of the text of the delegated translation has been changed in the
	 * delegate so we simply propagate the event to our listeners
	 */
	@Override
	public void notifyChange(ITranslationChangeEvent event) {
		propagateTranslationChangeEvent(event);
	}

	@Override
	public final String delete(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = doDelete(kind, locale);
		releaseDelegate();
		return oldText;
	}

	@Override
	public final String getText(ITranslationKind kind, Locale locale) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = doGetText(kind, locale);
		if (text == null) {

			// no message defined in this translation, try to inherit it.

			text = getDelegate().getText(kind, locale);
			/**
			 * If the inherited translation cannot be loaded for any reasons, a
			 * TranslationException is always raised.
			 * <p>
			 * IMPORTANT: The method <code>getDelegate</code> must never returns
			 * <code>null</code>, Hence, a <code>NullPointerException</code> is
			 * raised it means the specification has not been properly
			 * implemented : so check the method <code>doGetTex</code>.
			 */
		}
		return text;
	}

	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {

		boolean inherited = false;
		
		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String text = doGetText(kind, locale);
		if (text == null) {
			String inheritedText = getDelegate().getText(kind, locale);
			inherited = inheritedText != null;
		} 
		
		return inherited;
		
	}
	
	@Override
	public final String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		return getDelegate().getText(kind, locale);
	}
	

	@Override
	public final String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {

		if (null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		String oldText = doSetText(kind, locale, newText);
		releaseDelegate();
		return oldText;
	}

	/**
	 * Constructor
	 * 
	 * @param provider
	 *            the provider that owns this instance
	 * @param project
	 *            the eclipse project
	 */
	protected InheritableTranslation(ITranslationProvider provider, IProject project) {
		super(provider, project);
	}

}
