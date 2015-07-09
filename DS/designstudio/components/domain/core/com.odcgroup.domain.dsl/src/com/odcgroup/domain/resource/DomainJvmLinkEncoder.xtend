package com.odcgroup.domain.resource

import com.google.inject.Inject
import com.google.inject.Singleton
import com.odcgroup.mdf.ecore.MdfEntityImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static com.odcgroup.mdf.ecore.PrimitivesDomain.*
import com.odcgroup.mdf.metamodel.MdfEntity
import com.odcgroup.mdf.metamodel.MdfPrimitive

@Singleton
@SuppressWarnings("restriction")
class DomainJvmLinkEncoder {

	static val DOMAIN_JVM_LINK_PREFIX = "domain_jvmLink_"

	@Inject extension TypesFactory

	@Inject JvmTypesBuilder jvmTypesBuilder

	@Inject extension IQualifiedNameProvider

	def isFragment(String uriFragment) {
		if (uriFragment != null)
			uriFragment.startsWith(DOMAIN_JVM_LINK_PREFIX)
		else
			false
	}

	def decode(String uriFragment) {
		uriFragment.substring(DOMAIN_JVM_LINK_PREFIX.length)
	}

	def createTypeRef(EObject context, MdfEntity mdfEntity) {
		switch mdfEntity {
			MdfEntityImpl:
				if (mdfEntity.eIsProxy) {
					jvmTypesBuilder.newTypeRef(mdfEntity.createProxy)
				} else {
					context.newTypeRef(mdfEntity)
				}
// Don't do this, because it's possible that e.g. during typing, or for invalid resources, a type is null
//			default:
//				throw new IllegalArgumentException("mdfEntity == null, context: " + context.toString )
		}
	}

	def newTypeRef(EObject context, EObject object) {
		switch object {
			MdfEntityImpl case !object.eIsProxy: {
				jvmTypesBuilder.newTypeRef(context, object.typeName)
			}
			default:
				null
		}
	}

	protected def createProxy(MdfEntityImpl mdfEntity) {
		val proxyURI = (mdfEntity as InternalEObject).eProxyURI

		val proxy = createJvmVoid
		val internalProxy = proxy as InternalEObject
		internalProxy.eSetProxyURI(proxyURI.appendFragment('''«DOMAIN_JVM_LINK_PREFIX»«proxyURI.fragment»'''))
		proxy
	}

	protected def getTypeName(MdfEntity mdfEntity) {
		switch mdfEntity {
			MdfPrimitive case mdfEntity.parentDomain?.name == NAME:
				// TODO use com.odcgroup.mdf.ecore.PrimitivesDomain.getJavaType(MdfPrimitive)
				switch (mdfEntity) {
					case STRING: "java.lang.String"
					case DECIMAL: "java.math.BigDecimal"
					case BOOLEAN: "java.lang.Boolean"
					case BOOLEAN_OBJ: "java.lang.Boolean"
					case FLOAT: "java.math.BigDecimal"
					case FLOAT_OBJ: "java.math.BigDecimal"
					case DOUBLE: "java.math.BigDecimal"
					case DOUBLE_OBJ: "java.math.BigDecimal"
					case INTEGER: "java.math.BigDecimal"
					case INTEGER_OBJ: "java.math.BigDecimal"
					case SHORT: "java.math.BigDecimal"
					case SHORT_OBJ: "java.math.BigDecimal"
					case LONG: "java.math.BigDecimal"
					case LONG_OBJ: "java.math.BigDecimal"
					case DATE: "java.util.GregorianCalendar"
					case DATE_TIME: "java.util.GregorianCalendar"
					case BYTE: "java.lang.Byte"
					case BYTE_OBJ: "java.lang.Byte"
					case CHAR: "java.lang.Character"
					case CHAR_OBJ: "java.lang.Character"
					case URI: "java.lang.String"
				}
			MdfEntityImpl:
				mdfEntity.fullyQualifiedName?.toString
		}
	}

}
