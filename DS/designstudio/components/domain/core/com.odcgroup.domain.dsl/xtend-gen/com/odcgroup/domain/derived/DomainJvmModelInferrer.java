package com.odcgroup.domain.derived;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.odcgroup.domain.derived.DomainJvmDatasetPropertyTypeReferenceProvider;
import com.odcgroup.domain.resource.DomainJvmLinkEncoder;
import com.odcgroup.mdf.ecore.MdfBusinessTypeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmEnumerationLiteral;
import org.eclipse.xtext.common.types.JvmEnumerationType;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmVisibility;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xtype.XComputedTypeReference;
import org.eclipse.xtext.xtype.XtypeFactory;

@SuppressWarnings("all")
public class DomainJvmModelInferrer extends AbstractModelInferrer {
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  @Inject
  @Extension
  private DomainJvmLinkEncoder domainJvmLinkEncoder;
  
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  protected void _infer(final MdfClassImpl mdfClass, @Extension final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method baseClass is undefined for the type DomainJvmModelInferrer"
      + "\nThe method basicGetType is undefined for the type DomainJvmModelInferrer"
      + "\nThe method basicGetType is undefined for the type DomainJvmModelInferrer"
      + "\n!= cannot be resolved"
      + "\n&& cannot be resolved");
  }
  
  protected void _infer(final MdfDatasetImpl mdfDataset, @Extension final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    Boolean _get = FeatureSwitches.domainInferJvmTypes.get();
    boolean _not = (!(_get).booleanValue());
    if (_not) {
      return;
    }
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(mdfDataset);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(mdfDataset, _fullyQualifiedName);
    IJvmDeclaredTypeAcceptor.IPostIndexingInitializing<JvmGenericType> _accept = acceptor.<JvmGenericType>accept(_class);
    final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
      public void apply(final JvmGenericType it) {
        String _documentation = mdfDataset.getDocumentation();
        DomainJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
        List _properties = mdfDataset.getProperties();
        Iterable<MdfDatasetPropertyImpl> _filter = Iterables.<MdfDatasetPropertyImpl>filter(_properties, MdfDatasetPropertyImpl.class);
        for (final MdfDatasetPropertyImpl property : _filter) {
          {
            final XComputedTypeReference jvmTypeReference = XtypeFactory.eINSTANCE.createXComputedTypeReference();
            DomainJvmDatasetPropertyTypeReferenceProvider _domainJvmDatasetPropertyTypeReferenceProvider = new DomainJvmDatasetPropertyTypeReferenceProvider(DomainJvmModelInferrer.this.domainJvmLinkEncoder, property);
            jvmTypeReference.setTypeProvider(_domainJvmDatasetPropertyTypeReferenceProvider);
            String _name = property.getName();
            boolean _isEmpty = _name.isEmpty();
            boolean _not = (!_isEmpty);
            if (_not) {
              EList<JvmMember> _members = it.getMembers();
              String _name_1 = property.getName();
              final Procedure1<JvmField> _function = new Procedure1<JvmField>() {
                public void apply(final JvmField it) {
                  it.setVisibility(JvmVisibility.PUBLIC);
                  String _documentation = property.getDocumentation();
                  DomainJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
                }
              };
              JvmField _field = DomainJvmModelInferrer.this._jvmTypesBuilder.toField(property, _name_1, jvmTypeReference, _function);
              DomainJvmModelInferrer.this._jvmTypesBuilder.<JvmField>operator_add(_members, _field);
            }
          }
        }
      }
    };
    _accept.initializeLater(_function);
  }
  
  protected void _infer(final MdfEnumerationImpl mdfEnumeration, @Extension final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    Boolean _get = FeatureSwitches.domainInferJvmTypes.get();
    boolean _not = (!(_get).booleanValue());
    if (_not) {
      return;
    }
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(mdfEnumeration);
    String _string = _fullyQualifiedName.toString();
    JvmEnumerationType _enumerationType = this._jvmTypesBuilder.toEnumerationType(mdfEnumeration, _string, null);
    IJvmDeclaredTypeAcceptor.IPostIndexingInitializing<JvmEnumerationType> _accept = acceptor.<JvmEnumerationType>accept(_enumerationType);
    final Procedure1<JvmEnumerationType> _function = new Procedure1<JvmEnumerationType>() {
      public void apply(final JvmEnumerationType it) {
        it.setStatic(true);
        String _documentation = mdfEnumeration.getDocumentation();
        DomainJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
        List _values = mdfEnumeration.getValues();
        Iterable<MdfEnumValueImpl> _filter = Iterables.<MdfEnumValueImpl>filter(_values, MdfEnumValueImpl.class);
        for (final MdfEnumValueImpl value : _filter) {
          String _name = value.getName();
          boolean _isEmpty = _name.isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            EList<JvmMember> _members = it.getMembers();
            String _name_1 = value.getName();
            final Procedure1<JvmEnumerationLiteral> _function = new Procedure1<JvmEnumerationLiteral>() {
              public void apply(final JvmEnumerationLiteral it) {
                it.setStatic(true);
                it.setVisibility(JvmVisibility.PUBLIC);
                String _documentation = value.getDocumentation();
                DomainJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
              }
            };
            JvmEnumerationLiteral _enumerationLiteral = DomainJvmModelInferrer.this._jvmTypesBuilder.toEnumerationLiteral(value, _name_1, _function);
            DomainJvmModelInferrer.this._jvmTypesBuilder.<JvmEnumerationLiteral>operator_add(_members, _enumerationLiteral);
          }
        }
      }
    };
    _accept.initializeLater(_function);
  }
  
  protected void _infer(final MdfBusinessTypeImpl mdfBusinessType, @Extension final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    Boolean _get = FeatureSwitches.domainInferJvmTypes.get();
    boolean _not = (!(_get).booleanValue());
    if (_not) {
      return;
    }
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(mdfBusinessType);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(mdfBusinessType, _fullyQualifiedName);
    acceptor.<JvmGenericType>accept(_class);
  }
  
  public void infer(final EObject mdfBusinessType, final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    if (mdfBusinessType instanceof MdfBusinessTypeImpl) {
      _infer((MdfBusinessTypeImpl)mdfBusinessType, acceptor, prelinkingPhase);
      return;
    } else if (mdfBusinessType instanceof MdfEnumerationImpl) {
      _infer((MdfEnumerationImpl)mdfBusinessType, acceptor, prelinkingPhase);
      return;
    } else if (mdfBusinessType instanceof MdfClassImpl) {
      _infer((MdfClassImpl)mdfBusinessType, acceptor, prelinkingPhase);
      return;
    } else if (mdfBusinessType instanceof MdfDatasetImpl) {
      _infer((MdfDatasetImpl)mdfBusinessType, acceptor, prelinkingPhase);
      return;
    } else if (mdfBusinessType != null) {
      _infer(mdfBusinessType, acceptor, prelinkingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mdfBusinessType, acceptor, prelinkingPhase).toString());
    }
  }
}
