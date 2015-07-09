package com.odcgroup.otf.jpa.spring;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

import com.odcgroup.otf.jpa.exception.OtfJPAInitializationException;

/**
 * Cast JPA EntityManager/Query to OpenJPA OpenJPAEntityManager/OpenJPAQuery in
 * a manner compatible with the Spring SharedEntityManagerCreator magic.
 * 
 * @see http://jira.springframework.org/browse/SPR-5858
 * 
 * @see SharedEntityManagerCreator
 * 
 * @see SpringJPASampleTest for tests
 * 
 * @author Michael Vorburger (MVO)
 */
public abstract class SpringOpenJPAHelper {
	private SpringOpenJPAHelper() {
	}

	/**
	 * Return the OpenJPA facade for a given entity manager.
	 * 
	 * Implementation just calls OpenJPAPersistence.cast(), which itself either
	 * just does a classical type cast, or uses EntityManager.getDelegate(),
	 * which works well.
	 * 
	 * @see OpenJPAPersistence#cast(EntityManager)
	 */
	public static OpenJPAEntityManager cast(EntityManager em) {
		return OpenJPAPersistence.cast(em);
	}

	/**
	 * Return the OpenJPA facade for a given query.
	 * 
	 * If needed (for @NotTransactional EM), implementation does funky stuff
	 * with Proxy/InvocationHandler/Field.setAccessible(true), to access the
	 * private Query target inside a SharedEntityManagerCreator's private inner
	 * DeferredQueryInvocationHandler class. (Don't mess with this unless you
	 * know what you're doing! ;)
	 */
	public static OpenJPAQuery cast(Query q) {
		if (q instanceof OpenJPAQuery) {
			return OpenJPAPersistence.cast(q);
		} else {
			InvocationHandler ih = Proxy.getInvocationHandler(q);
			Query target;
			try {
				target = (Query) getPrivateTargetField().get(ih);
			} catch (IllegalArgumentException e) {
				throw new OtfJPAInitializationException(
						"Unexpected IllegalArgumentException while trying to call "
								+ "java.lang.reflect.Field.get(InvocationHandler), a Reflection to obtain a private inner class and field",
						e);
			} catch (IllegalAccessException e) {
				throw new OtfJPAInitializationException(
						"Unexpected IllegalAccessException while trying to call "
								+ "java.lang.reflect.Field.get(InvocationHandler), a Reflection to obtain a private inner class and field",
						e);
			}
			OpenJPAQuery oq = OpenJPAPersistence.cast(target);
			return oq;
		}
	}

	/**
	 * @return New Field for
	 *         SharedEntityManagerCreator$DeferredQueryInvocationHandler.target
	 *         (created via Reflection giving access to private inner class and
	 *         field)
	 */
	private static Field newPrivateTargetField() {
		Class<?>[] innerClasses = SharedEntityManagerCreator.class.getDeclaredClasses();
		Class<?> deferredQueryInvocationHandlerClass = null;
		for (Class<?> iklass : innerClasses) {
			String n = iklass.getSimpleName();
			if (n.equals("DeferredQueryInvocationHandler")) {
				deferredQueryInvocationHandlerClass = iklass;
				break;
			}
		}
		if (deferredQueryInvocationHandlerClass == null) {
			throw new OtfJPAInitializationException("Unexpected problem while trying to get Spring's"
					+ "SharedEntityManagerCreator$DeferredQueryInvocationHandler inner class; may be "
					+ "Spring implementation changed? innerClasses = " + Arrays.toString(innerClasses));
		}

		Field privateTargetField;
		try {
			privateTargetField = deferredQueryInvocationHandlerClass.getDeclaredField("target");
		} catch (SecurityException e) {
			throw new OtfJPAInitializationException("Unexpected SecurityException while trying to get "
					+ "SharedEntityManagerCreator$DeferredQueryInvocationHandler.target via "
					+ "Reflection giving access to private inner class and field", e);
		} catch (NoSuchFieldException e) {
			throw new OtfJPAInitializationException("Unexpected NoSuchFieldException while trying to get "
					+ "SharedEntityManagerCreator$DeferredQueryInvocationHandler.target via "
					+ "Reflection giving access to private inner class and field", e);
		}
		try {
			privateTargetField.setAccessible(true);
		} catch (SecurityException e) {
			throw new OtfJPAInitializationException("Unexpected SecurityException while trying to setAccessible(true) "
					+ "the already found SharedEntityManagerCreator$DeferredQueryInvocationHandler.target "
					+ "(why no SecurityException earlier?)", e);
		}

		return privateTargetField;
	}

	/**
	 * @return Cached Field for
	 *         SharedEntityManagerCreator$DeferredQueryInvocationHandler.target
	 */
	private static Field getPrivateTargetField() {
		return privateTargetField;
	}

	private static final Field privateTargetField;

	static {
		privateTargetField = newPrivateTargetField();
	}

}
