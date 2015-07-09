package com.odcgroup.domain.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;

/**
 * This is a custom qualified name provider for domain models.
 * The only things referenced from the outside are domains, entities and associations.
 * So we make sure that we do not return anything for the rest (as this would only
 * blow up indexes that Xtext maintains).
 * 
 * @author Kai Kreuzer - original implementation (used to use MdfModelElement getQualifiedName() & MdfNameQualifiedNameUtil)
 * @author Michael Vorburger - rewrite, to move away from MdfName, and hoping for better performance [but impact appears to be minor, despite having shown up as a hotspot?!] (@see DS-7188 & DS-5605)
 */
public class DomainQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		// We used to use basically
		// ~ return MdfNameQualifiedNameUtil.getQualifiedName(mdfModelElement.getQualifiedName())
		// here, but now prefer this:
		
		if (obj.eIsProxy()) {
			return null;
		}
		
		// The order here is chosen based on the likelihood of these types;
		// more entities than domains:
		
		// Entity (Primitive, BusinessType, Class, Dataset, Enumeration
		if (obj instanceof MdfEntity) {
			final MdfEntity mdfEntity = (MdfEntity) obj;
			final String mdfDomainName = getDomainName(mdfEntity);
			if (mdfDomainName == null) {
				return null;
			}
			return QualifiedName.create(mdfDomainName, mdfEntity.getName());			
		
		// Domain
		} else if (obj instanceof MdfDomain) {
			final MdfDomain mdfDomain = (MdfDomain) obj;
			return QualifiedName.create(mdfDomain.getName());
		
		} else if (obj instanceof JvmIdentifiableElement) {
			JvmIdentifiableElement idenitifiableElement = (JvmIdentifiableElement) obj;
			return QualifiedName.create(idenitifiableElement.getQualifiedName('.').split("\\."));

		// others
		} else {
			return null;
		// NOT if (obj instanceof MdfAssociation) {
		// we used to do this, but Itemis recommended solving this differently, in order not to blow up the index (reduce entries)
		}
		
	}

	private final String getDomainName(MdfEntity mdfEntity) {
		return mdfEntity.getParentDomain().getName();
	}

}


