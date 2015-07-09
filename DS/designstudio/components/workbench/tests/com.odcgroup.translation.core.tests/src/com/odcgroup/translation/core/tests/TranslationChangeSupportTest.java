package com.odcgroup.translation.core.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.internal.events.TranslationChangeSupport;

/**
 * This test case attempts to test the basic notification mechanism for
 * translation change implemented by the class
 * <code>TranslationChangeSupport</code>
 * 
 * @author atr
 */
public class TranslationChangeSupportTest {

	/**
	 * This test verifies that a new TranslationChangeSupport instance has no
s	 * pre-registered generic listeners
	 */
	@Test
	public void testGenericTranslationChangeHasNoGenericListeners() {
		TranslationChangeSupport support = new TranslationChangeSupport(null);
		Assert.assertTrue("The resulting array cannot be null", support.getTranslationChangeListeners() != null);
		Assert.assertTrue("There must be no generic listeners", support.getTranslationChangeListeners().length == 0);
	}

	/**
	 * This test verifies that a new TranslationChangeSupport instance has no
s	 * pre-registered specific listeners
	 */
	@Test
	public void testGenericTranslationChangeHasNoSpecificListeners() {
		ITranslationKind[] kinds = ITranslationKind.values();
		TranslationChangeSupport support = new TranslationChangeSupport(null);
		for (int kx = 0; kx < kinds.length; kx++) {
			ITranslationKind kind = kinds[kx];
			Assert.assertTrue("The resulting array is not null for kind:"+kind, 
					support.getTranslationChangeListeners(kind) != null);
			Assert.assertTrue("There must be no specific listeners for kind:" + kind, 
					support.getTranslationChangeListeners(kind).length == 0);
		}
		
	}

	/**
	 * This test registers several different ITranslationChangeListener
	 * instances and verify that all have been registered. It checks also there
	 * is no side effect on the registered specific translation change listeners
	 */
	@Test
	public void testRegisterGenericTranslationChangeListeners() {

		// Create the TranslationChangeSupport
		TranslationChangeSupport support = new TranslationChangeSupport(null);

		final int MAX_LISTENERS = 10;
		for (int lx = 0; lx < MAX_LISTENERS; lx++) {
			// create a listener and register it
			ITranslationChangeListener listener = new ITranslationChangeListener() {
				public void notifyChange(ITranslationChangeEvent event) {
				}
			};
			support.addTranslationChangeListener(listener);
			// ensure the new listener has been registered
			Assert.assertTrue("The resulting array cannot be null", 
					support.getTranslationChangeListeners() != null);
			Assert.assertTrue("The " + lx + "th TranslationChangeListener has not been registered", 
					support.getTranslationChangeListeners().length == (lx+1));
			List<ITranslationChangeListener> registeredListeners = 
				Arrays.asList(support.getTranslationChangeListeners());
			Assert.assertTrue("The " + lx + "th TranslationChangeListener seems to be registered but it is not found",
					registeredListeners.contains(listener));
		}
		
	}

	/**
	 * It checks also there is no side effect on the registered specific
	 * translation change listeners
	 */
	@Test
	public void testRegisterSpecificTranslationChangeListeners() {

		ITranslationKind[] kinds = ITranslationKind.values();

		TranslationChangeSupport support = new TranslationChangeSupport(null);

		for (int kx = 0; kx < kinds.length; kx++) {
			ITranslationKind kind = kinds[kx];
			// create a listener and register it
			ITranslationChangeListener listener = new ITranslationChangeListener() {
				public void notifyChange(ITranslationChangeEvent event) {
				}
			};
			support.addTranslationChangeListener(kind, listener);
			// Ensure there is no registered generic listeners
			Assert.assertTrue("", support.getTranslationChangeListeners() != null);
			Assert.assertTrue("", support.getTranslationChangeListeners().length == 0);

			// Ensure the listener is registered for the translation kind
			Assert.assertTrue("", support.getTranslationChangeListeners(kind) != null);
			Assert.assertTrue("", support.getTranslationChangeListeners(kind).length == (kx+1));
			List<ITranslationChangeListener> registeredListeners = 
				Arrays.asList(support.getTranslationChangeListeners(kind));
			Assert.assertTrue("", registeredListeners.contains(listener));
		}
	}

	@Test
	public void testRemoveTranslationChangeListener() {
		
		TranslationChangeSupport support = new TranslationChangeSupport(null);
		ITranslationChangeListener listener = new ITranslationChangeListener() {
			public void notifyChange(ITranslationChangeEvent event) {
			}
		};
		support.addTranslationChangeListener(listener);
		support.removeTranslationChangeListener(listener);
		Assert.assertTrue("", support.getTranslationChangeListeners() != null);
		Assert.assertTrue("", support.getTranslationChangeListeners().length == 0);
	}

}
