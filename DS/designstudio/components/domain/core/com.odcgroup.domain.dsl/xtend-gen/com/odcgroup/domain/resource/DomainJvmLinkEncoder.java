package com.odcgroup.domain.resource;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.Extension;

@Singleton
@SuppressWarnings("all")
public class DomainJvmLinkEncoder {
  private final static String DOMAIN_JVM_LINK_PREFIX = "domain_jvmLink_";

  @Inject
  @Extension
  private TypesFactory _typesFactory;

  @Inject
  private JvmTypesBuilder jvmTypesBuilder;

  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;

  public boolean isFragment(final String uriFragment) {
    boolean _xifexpression = false;
    boolean _notEquals = (!Objects.equal(uriFragment, null));
    if (_notEquals) {
      _xifexpression = uriFragment.startsWith(DomainJvmLinkEncoder.DOMAIN_JVM_LINK_PREFIX);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }

  public String decode(final String uriFragment) {
    int _length = DomainJvmLinkEncoder.DOMAIN_JVM_LINK_PREFIX.length();
    return uriFragment.substring(_length);
  }

  public JvmTypeReference createTypeRef(final EObject context, final MdfEntity mdfEntity) {
    JvmTypeReference _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (mdfEntity instanceof MdfEntityImpl) {
        _matched=true;
        JvmTypeReference _xifexpression = null;
        boolean _eIsProxy = ((MdfEntityImpl)mdfEntity).eIsProxy();
        if (_eIsProxy) {
          JvmVoid _createProxy = this.createProxy(((MdfEntityImpl)mdfEntity));
          _xifexpression = this.jvmTypesBuilder.newTypeRef(_createProxy);
        } else {
          _xifexpression = this.newTypeRef(context, ((EObject)mdfEntity));
        }
        _switchResult = _xifexpression;
      }
    }
    return _switchResult;
  }

  public JvmTypeReference newTypeRef(final EObject context, final EObject object) {
    JvmTypeReference _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (object instanceof MdfEntityImpl) {
        boolean _eIsProxy = ((MdfEntityImpl)object).eIsProxy();
        boolean _not = (!_eIsProxy);
        if (_not) {
          _matched=true;
          String _typeName = this.getTypeName(((MdfEntity)object));
          _switchResult = this.jvmTypesBuilder.newTypeRef(context, _typeName);
        }
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }

  protected JvmVoid createProxy(final MdfEntityImpl mdfEntity) {
    JvmVoid _xblockexpression = null;
    {
      final URI proxyURI = ((InternalEObject) mdfEntity).eProxyURI();
      final JvmVoid proxy = this._typesFactory.createJvmVoid();
      final InternalEObject internalProxy = ((InternalEObject) proxy);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(DomainJvmLinkEncoder.DOMAIN_JVM_LINK_PREFIX, "");
      String _fragment = proxyURI.fragment();
      _builder.append(_fragment, "");
      URI _appendFragment = proxyURI.appendFragment(_builder.toString());
      internalProxy.eSetProxyURI(_appendFragment);
      _xblockexpression = proxy;
    }
    return _xblockexpression;
  }

  protected String getTypeName(final MdfEntity mdfEntity) {
    String _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (mdfEntity instanceof MdfPrimitive) {
        MdfDomain _parentDomain = ((MdfPrimitive)mdfEntity).getParentDomain();
        String _name = null;
        if (_parentDomain!=null) {
          _name=_parentDomain.getName();
        }
        boolean _equals = Objects.equal(_name, PrimitivesDomain.NAME);
        if (_equals) {
          _matched=true;
          String _switchResult_1 = null;
          boolean _matched_1 = false;
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.STRING)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.String";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.DECIMAL)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.BOOLEAN)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Boolean";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.BOOLEAN_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Boolean";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.FLOAT)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.FLOAT_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.DOUBLE)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.DOUBLE_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.INTEGER)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.INTEGER_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.SHORT)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.SHORT_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.LONG)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.LONG_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.math.BigDecimal";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.DATE)) {
              _matched_1=true;
              _switchResult_1 = "java.util.GregorianCalendar";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.DATE_TIME)) {
              _matched_1=true;
              _switchResult_1 = "java.util.GregorianCalendar";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.BYTE)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Byte";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.BYTE_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Byte";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.CHAR)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Character";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.CHAR_OBJ)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.Character";
            }
          }
          if (!_matched_1) {
            if (Objects.equal(mdfEntity, PrimitivesDomain.URI)) {
              _matched_1=true;
              _switchResult_1 = "java.lang.String";
            }
          }
          _switchResult = _switchResult_1;
        }
      }
    }
    if (!_matched) {
      if (mdfEntity instanceof MdfEntityImpl) {
        _matched=true;
        QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(((EObject)mdfEntity));
        String _string = null;
        if (_fullyQualifiedName!=null) {
          _string=_fullyQualifiedName.toString();
        }
        _switchResult = _string;
      }
    }
    return _switchResult;
  }
}
