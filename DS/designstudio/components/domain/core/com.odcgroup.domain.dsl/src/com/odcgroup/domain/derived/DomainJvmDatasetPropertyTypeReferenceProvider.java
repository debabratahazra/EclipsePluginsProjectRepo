package com.odcgroup.domain.derived;

import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.xbase.typesystem.util.AbstractReentrantTypeReferenceProvider;
import org.eclipse.xtext.xtype.impl.XComputedTypeReferenceImplCustom;

import com.odcgroup.domain.resource.DomainJvmLinkEncoder;
import com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl;
import com.odcgroup.mdf.metamodel.MdfEntity;

/**
 * TypeReferenceProvider for Dataset property.
 * 
 * This is done "deferred" like this because it cannot be done inside
 * DomainJvmModelInferrer, because we cannot calculate it in the JVM model
 * inferrer phase, as it requires to resolve cross references. Instead, we
 * must do it in the type resolution phase with this class.
 * 
 * @author Michael Vorburger
 */
@SuppressWarnings("restriction")
public class DomainJvmDatasetPropertyTypeReferenceProvider extends AbstractReentrantTypeReferenceProvider {

	private DomainJvmLinkEncoder domainJvmLinkEncoder;
	private MdfDatasetPropertyImpl p;

	DomainJvmDatasetPropertyTypeReferenceProvider(DomainJvmLinkEncoder encoder, MdfDatasetPropertyImpl p) {
		this.domainJvmLinkEncoder = encoder;
		this.p = p;
	}

	@Override
	protected JvmTypeReference doGetTypeReference(/*@NonNull*/ XComputedTypeReferenceImplCustom context) {
		MdfEntity mdfEntityType = p.getType();
		JvmTypeReference jvmTypeRef = domainJvmLinkEncoder.createTypeRef(/* context ? */ p.getParentDataset(), mdfEntityType);
		return jvmTypeRef;
	}

}
