package com.odcgroup.translation.core.internal.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * @author atr
 */
public class TranslationChangeSupport {

	/** */
	private transient ITranslation source;

	/**
	 * 
	 */
	private List<ITranslationChangeListener> listeners;

	/**
	 * 
	 */
	private Map<ITranslationKind, TranslationChangeSupport> kindListeners;

	/**
	 * Fire an existing ITranslationChangeEvent to any registered listeners. No
	 * event is fired if the given event's old and new values are equal and
	 * non-null.
	 * 
	 * @param event
	 *            The ITranslationChangeEvent object.
	 */
	protected void fireTranslationChange(ITranslationChangeEvent event) {
		if (listeners != null) {
			for (ITranslationChangeListener target : listeners) {
				target.notifyChange(event);
			}
		}
		if (kindListeners != null) {
			ITranslationKind kind = event.getKind();
			if (kind != null) {
				TranslationChangeSupport support = kindListeners.get(kind);
				if (support != null) {
					support.fireTranslationChange(event);
				}
			}
		}
	}

	/**
	 * @param listener
	 */
	public synchronized void addTranslationChangeListener(ITranslationChangeListener listener) {

		if (listener == null) {
			return;
		}

		if (listeners == null) {
			listeners = new ArrayList<ITranslationChangeListener>();
		}

		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * @param listener
	 * @param kind
	 */
	public synchronized void addTranslationChangeListener(ITranslationKind kind, ITranslationChangeListener listener) {
		if (listener == null || kind == null) {
			return;
		}
		if (kindListeners == null) {
			kindListeners = new HashMap<ITranslationKind, TranslationChangeSupport>();
		}
		TranslationChangeSupport support = kindListeners.get(kind);
		if (support == null) {
			support = new TranslationChangeSupport(source);
			kindListeners.put(kind, support);
		}
		support.addTranslationChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public synchronized void removeTranslationChangeListener(ITranslationChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param listener
	 * @param kind
	 */
	public void removeChangeListener(ITranslationKind kind, ITranslationChangeListener listener) {
		if (listener == null || kind == null) {
			return;
		}
		if (kindListeners == null) {
			return;
		}
		TranslationChangeSupport support = (TranslationChangeSupport) kindListeners.get(kind);
		if (support == null) {
			return;
		}
		support.removeTranslationChangeListener(listener);
	}
	
	/**
	 * @return all registered generic listeners
	 */
	public ITranslationChangeListener[] getTranslationChangeListeners() {
		ArrayList<ITranslationChangeListener> list = new ArrayList<ITranslationChangeListener>();
		if (listeners != null) {
			list.addAll(listeners);
		}
		return list.toArray(new ITranslationChangeListener[0]);
	}

	/**
	 * @return all listeners registered
	 */
	public ITranslationChangeListener[] getTranslationChangeListeners(ITranslationKind kind) {
		ArrayList<ITranslationChangeListener> list = new ArrayList<ITranslationChangeListener>();
		if (kindListeners != null) {
			for (TranslationChangeSupport support : kindListeners.values()) {
				list.addAll(Arrays.asList(support.getTranslationChangeListeners()));
			}
		}
		return list.toArray(new ITranslationChangeListener[0]);
	}

	/**
	 * @param event
	 */
	public void propagateTranslationChangeEvent(ITranslationChangeEvent event) {
		fireTranslationChange(event);
	}
	
	/**
	 * @param kind
	 */
	public void fireTranslationChange(ITranslationKind kind) {
		fireTranslationChange(new TranslationChangeEvent(source, kind, null, null, null));
	}
	
	/**
	 * @param kind
	 * @param locale
	 * @param oldText
	 * @param newText
	 */
	public void fireTranslationChange(ITranslationKind kind, Locale locale, String oldText, String newText) {
		if (!StringUtils.equals(oldText, newText)) {
			fireTranslationChange(new TranslationChangeEvent(source, kind, locale, oldText, newText));
		}
	}

	/**
	 * @param translation
	 */
	public TranslationChangeSupport(ITranslation translation) {
		source = translation;
	}

}
