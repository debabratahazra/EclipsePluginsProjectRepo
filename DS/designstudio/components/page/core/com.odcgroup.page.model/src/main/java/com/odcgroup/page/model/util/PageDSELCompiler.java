package com.odcgroup.page.model.util;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XFeatureCall;
import org.eclipse.xtext.xbase.XIfExpression;
import org.eclipse.xtext.xbase.XMemberFeatureCall;
import org.eclipse.xtext.xbase.compiler.IAppendable;
import org.eclipse.xtext.xbase.compiler.Later;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;

import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.workbench.el.generator.DSELCompiler;

/**
 * XSP Page generation specific DSELCompiler
 *
 * @author Kai Kreuzer - original author
 * @author Michael Vorburger - changes needed for Xtext 2.3.1 to 2.4.3 with ELang v2 -> v3 upgrade
 */
@SuppressWarnings("restriction")
public class PageDSELCompiler extends DSELCompiler {

	private static final URI SYNTHETIC_DOMAIN_URI = URI.createURI("Synthehol.domain"); // @see http://en.memory-alpha.org/wiki/Synthehol

	@Inject 
	private IResourceServiceProvider.Registry rspRegistry; 

	// DO NOT @Inject !!!!!!!!!!!! @see getDomainIJvmModelAssociations() - Folks (offshore), don't touch this unless you really know what you're doing
	private IJvmModelAssociations domainIJvmModelAssociations;

	private boolean udpStyle = false;

//	private DomainRepository domainRepo;
	
	public boolean isUdpStyle() {
		return udpStyle;
	}

	public void setUdpStyle(boolean udpStyle) {
		this.udpStyle = udpStyle;
	}

	/**
	 * NO-OP implementation of declareFreshLocalVariable;
	 * to avoid "AAADSInstrument.InstrumentAll _InstrumentAll = InstrumentAll;"
	 * It's possible that this may not actually be needed anymore thanks to new isVariableDeclarationRequired() { return false }
	 * introduced below after this - but better safe than sorry so keeping it (never makes sense on XSP anyways). 
	 * @see http://rd.oams.com/browse/DS-5547?focusedCommentId=322154#comment-322154
	 */
	@Override
	protected void declareFreshLocalVariable(XExpression expr, ITreeAppendable b, Later expression) {
		if (expr instanceof XFeatureCall) {
			// do nothing
		} else { 
			super.declareFreshLocalVariable(expr, b, expression);
		}
	}

	// =======================
	// @see http://rd.oams.com/browse/DS-5547
	// Following is the magic which makes the DS EL gen. "long-all-in-one" expressions, instead of creating an _equals etc. variable for each FeatureCall. 
	// This is needed because on an XSP (PageDSELCompiler) if isVariableDeclarationRequired then
	// a new org.eclipse.xtext.xbase.lib.Functions.Function0<Boolean>() { public Boolean apply() {...
	// is gen. (because an _equals etc. variable wouldn't be allowed in an Expression otherwise),
	// which is problem because on XSP there are some (at least one) context variable which we CANNOT make final.
	// It's probably ONLY needed in XSP, so the following overriden methods could (should?) be moved from here (DSELCompiler) into PageDSELCompiler? 
	
	@Override
	protected boolean isVariableDeclarationRequired(XExpression expr, ITreeAppendable b) {
		return false;
	}

	// This *IS* still needed, so that an Xbase if expression turn into Java 'cond ? then : else' expression instead of Xbase statements with temporary variables! 
	@Override
	protected void _toJavaExpression(XIfExpression expr, ITreeAppendable b) {
		internalToJavaExpression(expr.getIf(), b);
		b.append(" ? ( ");
		internalToJavaExpression(expr.getThen(), b);
		b.append(" ) : ( ");
		if (expr.getElse() != null) {
			internalToJavaExpression(expr.getElse(), b);
		} else {
			b.append("null");
		}
		b.append(" )");
	}

	// I'm not sure / understand why Kai had done this.. it seems like a bad idea now! Commenting out:
//	protected void _toJavaStatement(final XAbstractFeatureCall expr, final ITreeAppendable b, boolean isReferenced) {
//		_toJavaExpression(expr, b);
//	}

	// From what I understand, I don't think this is needed. Commenting out as afraid of unclear side effects:
//	@Override
//	protected void _toJavaExpression(XAbstractFeatureCall call, ITreeAppendable b) {
//		featureCalltoJavaExpression(call, b, true);
//	}
	
	// NO! Don't do this after all - as it contradicts the last above re. "long-all-in-one" expressions - forced SneakyThrow force new Function0<Boolean>() { which we DON'T WANT 
//	/**
//	 * XbaseCompiler will not be able to detect if "sneaky throw" is needed for <bean:get-property> etc.
//	 * So instead we just always return true. 
//	 * @see http://rd.oams.com/browse/DS-5547
//	 */
//	@Override
//	protected boolean needsSneakyThrow(XExpression obj, Collection<JvmTypeReference> declaredExceptions) {
//		return true;
//	}

	// ====================
	// From here on it's other stuff not related to problem described above anymore
	
	@Override
	protected void _toJavaExpression(XAbstractFeatureCall call, ITreeAppendable b) {
		if (call instanceof XMemberFeatureCall) {
			XMemberFeatureCall featureCall = (XMemberFeatureCall) call;
			String callTarget = featureCall.getMemberCallTarget().toString();
//			ExpressionContext context = getContext(call);
//			EObject bean = null; // dynContext.getDynInstance(callTarget);
			String featureName = call.getFeature().getSimpleName();

			/* if we have chained feature calls, we must check, if the root target is a domain bean */
			while(/*bean==null && */ featureCall.getMemberCallTarget() instanceof XMemberFeatureCall) {
				featureCall = (XMemberFeatureCall) featureCall.getMemberCallTarget();
				callTarget = featureCall.getMemberCallTarget().toString();
//				bean = dynContext.getDynInstance(callTarget);
				featureName = featureCall.getFeature().getSimpleName() + "." + featureName;
			}
			
			/* if the root target is a domain bean, generate bean property access xsp */
			JvmDeclaredType declaringType = ((JvmMember)featureCall.getFeature()).getDeclaringType();
			EObject source = getPrimarySourceElementFromDomainLanguage(declaringType);						
			if(source instanceof MdfEntity) {
				if(udpStyle) {
					b.append("<udp:unformatted-item column=\"" + featureName + "\"/>");
				} else {
					MdfEntity entity = (MdfEntityImpl) source;						
					if(entity != null) {
						entity = getMdfBaseEntity(declaringType, entity, featureName);
					}
					String beanexpr = "<bean:get-property bean=\"" + callTarget + "\" property=\"" + featureName + "\" />";
					_toBeanPropertyStyleExpression(entity, beanexpr, b);
				}
				return;
			}
		}
		super._toJavaExpression(call, b);
	}	

	/**
	 * Helper to work around a... bug (?) in Xtext;
	 * or conceptual problem with the cross-language
	 * design of EL using JvmType from *.domain resources:
	 * 
	 * @Inject IJvmModelAssociations jvmModelAssociations;
	 * jvmModelAssociations.getPrimarySourceElement(declaringType)
	 * cannot take the language into account, and so would inject
	 * the DSEL language's IJvmModelAssociations instead of Domain's.
	 * 
	 * @param jvmElement the JvmDeclaredType, as in org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations.getPrimarySourceElement(EObject)
	 * @return primary source element, as in org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations.getPrimarySourceElement(EObject)
	 */
	private EObject getPrimarySourceElementFromDomainLanguage(JvmDeclaredType jvmElement) {
		return getDomainIJvmModelAssociations().getPrimarySourceElement(jvmElement);
	}

	private IJvmModelAssociations getDomainIJvmModelAssociations() {
		if (domainIJvmModelAssociations == null) {
			IResourceServiceProvider rsp = rspRegistry.getResourceServiceProvider(SYNTHETIC_DOMAIN_URI);
			domainIJvmModelAssociations = rsp.get(IJvmModelAssociations.class);
		}
		return domainIJvmModelAssociations;
	}
	
	private void _toBeanPropertyStyleExpression(MdfEntity entity, String beanexpr, IAppendable b) {
		if (entity == null) {
			b.append(beanexpr);
		} else {
			MdfPrimitive primitive = null;
			if (entity instanceof MdfEnumeration) {
				primitive = ((MdfEnumeration) entity).getType();
			} else if (entity instanceof MdfBusinessType) {
				primitive = ((MdfBusinessType) entity).getType();
			} else if (entity instanceof MdfPrimitive){
				primitive = (MdfPrimitive) entity;
			} else if (entity instanceof MdfDataset) {
				b.append(beanexpr);
			}
			if (primitive != null) {
				DomainEntityUtils.DataType type = DomainEntityUtils.getOperatorKeyType(primitive);
				if (DomainEntityUtils.DataType.AMOUNT == type || DomainEntityUtils.DataType.NUMBER == type) {
					b.append(" (Number) "+beanexpr);
				} else if(DomainEntityUtils.DataType.STRING == type){
					b.append(" (String) "+beanexpr);
				} else if (DomainEntityUtils.DataType.FLAG == type){
					b.append(" (Boolean) "+beanexpr);
				} else {
					b.append(beanexpr);					
				}
			}
		}
	}
	
//	private ExpressionContext getContext(EObject element) {
//		for(Adapter adapter : element.eResource().eAdapters()) {
//			if (adapter instanceof ExpressionContext) {
//				return (ExpressionContext) adapter;
//			}
//		}
//		throw new IllegalStateException("No ExpressionContext Adapter on EObject element: " + element.toString());
//	}

	protected void convertWrapperToPrimitive(
			final JvmTypeReference wrapper, 
			final JvmTypeReference primitive, 
			XExpression context, 
			final ITreeAppendable appendable,
			final Later expression) {
		appendable.append("("+getJvmPrimitiveKind(context)+")");
		expression.exec(appendable);
	}
	
	private String getJvmPrimitiveKind(XExpression context) {
		return getDefaultValueLiteral(context);
	}
	
	@Override
	protected void featureCalltoJavaExpression(final XAbstractFeatureCall call, ITreeAppendable b, boolean unpreparedArguments) {
		ITreeAppendable newTreeAppendable = new DelegatingTreeAppendable(b) {
			@Override
			public ITreeAppendable append(CharSequence content) {
				if ("<".equals(content))
					return super.append("&lt;");
				else if (">".equals(content))
					return super.append("&gt;");
				else
					return super.append(content);
			}
		};
		
		// DS-6553: Undecided whether to a) forward unpreparedArguments as is to super, or b) force to true
		// Background: The old fully-copy-pasted :( code, by Kai, seems to have used true, see diff in JIRA, but it is unclear why.
		// Tests seem to work either way (but may not cover everything). Anton pointed out that "this argument seems not to be used at all".
		// It therefore may well be an argument due to historical reasons which makes no difference in current Xbase anymore?
		// Initially I Michael leaned towards b) with forced isExpressionContext true, just for backward compat. safety, 
		// but Anton's point "just in the case if some super methods start using it in the future" convinced me to put in a) for now.
		//
		// NOT b) super.featureCalltoJavaExpression(call, newTreeAppendable, true);
		// but a) like this:
		super.featureCalltoJavaExpression(call, newTreeAppendable, unpreparedArguments);
	}
	
	@Override
	protected boolean appendReceiver(XAbstractFeatureCall call, ITreeAppendable b, boolean isExpressionContext) {
		if (call instanceof XMemberFeatureCall) {
			XMemberFeatureCall expr = ((XMemberFeatureCall) call);
			if (expr.isNullSafe()) {
				internalToJavaExpression(expr.getMemberCallTarget(), b);
				b.append("==null?");
				JvmTypeReference type = getType(call);
				appendNullValue(type,call,b);
				b.append(":");
			}
		}
		if (call.isStatic()) {
			JvmDeclaredType declaringType = ((JvmMember) call.getFeature()).getDeclaringType();
			EObject source = getPrimarySourceElementFromDomainLanguage(declaringType);						
			if(source instanceof MdfEntity) {
				MdfEntity mdfEntity = (MdfEntity) source;
				String mdfEntityName = JavaAspect.getPackage(mdfEntity) + "." + declaringType.getSimpleName();
				b.append(mdfEntityName /*toMdfJavaType(declaringType)*/);
			} else {
				b.append(declaringType);
			}
			return true;
		}
		XExpression receiver = call.getActualReceiver();
		if (receiver != null) {
			internalToJavaExpression(receiver, b);
			return true;
		} else {
			return false;
		}
	}

//	private String toMdfJavaType(JvmDeclaredType declaringType) {
//		MdfDomain domain = domainRepo.getDomain(declaringType.getPackageName());
//		if(domain!=null) {
//			MdfEnumeration enumeration = domain.getEnumeration(declaringType.getSimpleName());
//			if(enumeration!=null) {
//				String val = JavaAspect.getPackage(enumeration) + "." + declaringType.getSimpleName();
//				return val;
//			} else {
//				throw new UnsupportedOperationException("Enumeration '" + declaringType.getPackageName() + ":" 
//						+ declaringType.getSimpleName() + "' could not be found.");
//			}
//		} else {
//			throw new UnsupportedOperationException("Domain '" + declaringType.getPackageName() + "' could not be found.");
//		}
//	}	

	private MdfEntity getMdfBaseEntity(JvmDeclaredType declaringType, MdfEntity entity, String feature) {
		if (StringUtils.isEmpty(feature)) {
			return null;
		}
			if (entity instanceof MdfDataset) {
				MdfDataset ds = (MdfDataset) entity;
				MdfDatasetProperty prop = ds.getProperty(feature);
				if (prop != null) {
					return prop.getType();
				}
			}
		return null;
	}

}
