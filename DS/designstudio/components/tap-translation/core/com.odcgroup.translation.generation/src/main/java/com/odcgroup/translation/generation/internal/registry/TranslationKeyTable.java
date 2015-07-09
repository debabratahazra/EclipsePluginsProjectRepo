package com.odcgroup.translation.generation.internal.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.ITranslationKeyHandler;
import com.odcgroup.translation.generation.ITranslationKeyProvider;
import com.odcgroup.translation.generation.ITranslationKeyTable;

/**
 * @author atr
 */
public class TranslationKeyTable implements ITranslationKeyTable {

	private static Logger logger = LoggerFactory.getLogger(TranslationKeyTable.class);

	private IProject project;

	private List<ITranslationKeyProvider> keyProviders;

	private Set<ITranslationKey> keySet = new HashSet<ITranslationKey>();

	/**
	 * @return
	 */
	protected final IProject getProject() {
		return this.project;
	}

	/**
	 * @return
	 */
	protected final List<ITranslationKeyProvider> getTranslationKeyProviders() {
		return this.keyProviders;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	protected ITranslationKeyProvider getTranslationKeyProvider(final Object object) {

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationKeyProvider provider = null;
		for (ITranslationKeyProvider tkp : getTranslationKeyProviders()) {
			for (Class<?> clazz : tkp.getInputTypes()) {
				if (clazz.isAssignableFrom(object.getClass())) {
					IFilter filter = tkp.getFilter();
					if (filter == null || filter.select(object)) {
						// found one
						provider = tkp;
					}
				}
			}
		}

		// no provider for the given object.
		if (provider != null) {
			logger.trace(TranslationCore.LOG_MARKER, "Translation Key Provider found: {}", provider.getId());
		} else {
			logger.trace(TranslationCore.LOG_MARKER, "Translation Provider not found for object's class {}", object
					.getClass().getName());
		}

		return provider;

	}

	/**
	 * @param translation
	 * @return
	 */
	private ITranslationKey getKey(ITranslation translation) {
		ITranslationKey key = null;
		ITranslationKeyProvider provider = getTranslationKeyProvider(translation.getOwner());
		if (provider != null) {
			try {
				key = provider.getTranslationKey(getProject(), translation);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return key;
	}

	@Override
	public void add(ITranslation translation) {
		ITranslationKey key = getKey(translation);
		if (key != null) {
			keySet.add(key);
		}
	}

	@Override
	public void clear() {
		keySet.clear();
	}

	@Override
	public int size() {
		return keySet.size();
	}

	@Override
	public void visit(ITranslationKeyHandler handler) throws CoreException {
		ITranslationKey keys[] = {};
		keys = keySet.toArray(new ITranslationKey[0]);
		for (ITranslationKey key : keys) {
			handler.notify(key);
		}
	}

	/**
	 * 
	 */
	public TranslationKeyTable(IProject project, List<ITranslationKeyProvider> keyProviders) {
		if (null == project || null == keyProviders) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		this.project = project;
		this.keyProviders = keyProviders;
	}

}
