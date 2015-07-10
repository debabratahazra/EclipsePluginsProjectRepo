package com.odcgroup.domain.resource;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
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
  
  public JvmTypeReference createTypeRef(final EObject context, final /* MdfEntity */Object mdfEntity) {
    throw new Error("Unresolved compilation problems:"
      + "\neIsProxy cannot be resolved"
      + "\ncreateProxy cannot be resolved");
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
          String _typeName = this.getTypeName(object);
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
  
  protected String getTypeName(final /* MdfEntity */Object mdfEntity) {
    throw new Error("Unresolved compilation problems:"
      + "\nMdfPrimitive cannot be resolved to a type."
      + "\nparentDomain cannot be resolved"
      + "\nname cannot be resolved"
      + "\n== cannot be resolved"
      + "\nfullyQualifiedName cannot be resolved"
      + "\ntoString cannot be resolved");
  }
}
