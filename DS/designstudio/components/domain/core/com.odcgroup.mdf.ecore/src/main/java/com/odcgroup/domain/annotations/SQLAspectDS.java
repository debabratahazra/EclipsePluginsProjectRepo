package com.odcgroup.domain.annotations;

import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class SQLAspectDS extends SQLAspect {

	public static void setJoinSpecification(MdfModelElement model, String parameter, String value) {
		MdfAnnotationsUtil.setAnnotationProperty(model, NAMESPACE_URI, JOIN_SPECIFICATIONS, parameter, value, false);
	}

	/**
	 * Set the MDF SQLName for this model element. E.g. JPA Cartridge uses this
	 * name to construct SELECT queries for classes/attributes. In T/A(ng)ij, on
	 * Classes, this is set to some view, and NOT the same as the T'A Entity's
	 * sqlname_c.
	 * 
	 * @param model The model element
	 * @param sqlName SQL Name
	 */
	public static void setSQLName(MdfModelElement model, String sqlName) {
		MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, SQL_NAME, sqlName);
	}

	/**
	 * Set the MDF SQLType for this model element.
	 * 
	 * @param model
	 *            The model element
	 * @param sqlName
	 *            SQL Name
	 */
	public static void setSQLType(MdfModelElement model, String sqlType) {
		MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, SQL_TYPE, sqlType);
	}

	public static void setIgnore(MdfModelElement model, boolean ignore) {
		MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, IGNORE, "" + ignore);
	}

	/**
	 * Sets the VersionOptimisticLocking annotation.
	 * 
	 * @param klass
	 *            The class to set VersionOptimisticLocking = NONE on
	 * @see com.odcgroup.mdf.ext.sql.SQLAspect#getVersionOptimisticLocking(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public static void setVersionOptimisticLockingNone(MdfClass klass) {
		MdfAnnotationsUtil.setAnnotationValue(klass, NAMESPACE_URI, SQLAspect.SQL_VERSION_OPTIMISTIC_LOCKING, SQL_VERSION_OPTIMISTIC_LOCKING_NONE);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setFullyQualifiedNameAnnotationsUsed(MdfDomain model, boolean b) {
		MdfAnnotationsUtil.setAnnotationProperty(model, NAMESPACE_URI, JPA_GENERATOR, FQN, Boolean.toString(b), false);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setPersistentCollectionUsed(MdfDomain model, boolean b) {
		MdfAnnotationsUtil.setAnnotationProperty(model, NAMESPACE_URI, JPA_GENERATOR, PERSISTENT_COLLECTION, Boolean.toString(b), false);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setPrefixAssociationWithDomainIfSame(MdfDomain domain, boolean b) {
		MdfAnnotationsUtil.setAnnotationProperty(domain, NAMESPACE_URI, JPA_GENERATOR, PREFIX_ASSOCIATIONS, Boolean.toString(b), false);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setAvoidEmbeddable(MdfDomain domain, boolean b) {
		MdfAnnotationsUtil.setAnnotationProperty(domain, NAMESPACE_URI, JPA_GENERATOR, AVOID_EMBEDDABLE, Boolean.toString(b), false);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setMessageLevel(MdfDomain domain, JpaMessageEnum message, LevelEnum level) {
		MdfAnnotationsUtil.setAnnotationProperty(domain, NAMESPACE_URI, JPA_GENERATOR, message.name().toLowerCase(), level.name(), false);
	}

	/**
	 * @see com.odcgroup.otf.jpa.generator.annotations.GeneratorConfiguration
	 */
	public static void setUseSqlNameAsItIs(MdfDomain domain, boolean b) {
		MdfAnnotationsUtil.setAnnotationProperty(domain, NAMESPACE_URI, JPA_GENERATOR, USE_SQLNAME_ASIS, Boolean.toString(b), false);
	}

    public static void setSQLAutoIncrement(MdfModelElement model, boolean sqlAutoIncrement) {
        // Only persist the true value!
        if (sqlAutoIncrement) {
        	MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, SQL_AUTOINCREMENT, "" + sqlAutoIncrement);
        } else if (isSQLRequired(model)) {
        	MdfAnnotationsUtil.removeAnnotation(model, NAMESPACE_URI, SQL_AUTOINCREMENT);
        }
    }

	public static void setSQLRequired(MdfModelElement model, boolean sqlRequired) {
		// Only persist the true value!
		if (sqlRequired) {
			MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, SQL_REQUIRED, "" + sqlRequired);
		} else if (isSQLRequired(model)) {
			MdfAnnotationsUtil.removeAnnotation(model, NAMESPACE_URI, SQL_REQUIRED);
		}
	}

}
