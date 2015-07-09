package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.ImportedNamespaceFactory;
import com.odcgroup.iris.rim.generation.mappers.MappersConstants;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.iris.rim.generation.mappers.T24VersionMapper;
import com.odcgroup.iris.rim.generation.mappers.Version2ResourceMapperForLiveNauHist;
import com.odcgroup.iris.rim.generation.mappers.Version2ResourceMapperForLiveOnly;
import com.odcgroup.iris.rim.generation.mappers.Version2ResourceMapperForVerifyOnly;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.DomainDeclaration;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;
import com.temenos.interaction.rimdsl.rim.State;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class Version2ResourceMapper {
  private Logger _logger = LoggerFactory.getLogger(Version2ResourceMapper.class);
  
  /**
   * Since looking for the fact that the resource already exist slow down a lot the generation, allow to skip it
   */
  private final static boolean fastMode = "true".equals(System.getProperty("rim.fast.mode"));
  
  public DomainModel mapVersion(final ModelLoader loader, final Version version) throws Exception {
    String _t24Name = version.getT24Name();
    String _plus = ("Generating RIM from Version [" + _t24Name);
    String _plus_1 = (_plus + "]");
    this._logger.info(_plus_1);
    final MdfClass mdfClass = version.getForApplication();
    final Application application = new Application(mdfClass);
    String _t24Name_1 = application.getT24Name();
    boolean _equals = Objects.equal(_t24Name_1, null);
    if (_equals) {
      throw new Exception("Application for Version not found, unable to generate RIM");
    }
    final DomainModel domainModel = RimFactory.eINSTANCE.createDomainModel();
    Resource.RESOURCE_TYPE _TYPE_VERSION = T24ResourceModelsGenerator.TYPE_VERSION();
    String _t24Name_2 = version.getT24Name();
    String _camelCaseName = EMUtils.camelCaseName(_t24Name_2);
    final String entityName = (_TYPE_VERSION + _camelCaseName);
    final String thisVersionCollectionResource = (entityName + "s");
    if ((!Version2ResourceMapper.fastMode)) {
      Object _namedEObjectOrProxy = loader.<Object>getNamedEObjectOrProxy(version, thisVersionCollectionResource, RimPackage.Literals.STATE, true, true);
      final State targetEnquiryState = ((State) _namedEObjectOrProxy);
      boolean _notEquals = (!Objects.equal(targetEnquiryState, null));
      if (_notEquals) {
        final InternalEObject internal = ((InternalEObject) targetEnquiryState);
        final URI path = internal.eProxyURI();
        String[] _segments = path.segments();
        for (final String oneSegment : _segments) {
          boolean _equals_1 = oneSegment.equals(MappersConstants.ADOPTED_RIM_SEGMENT);
          if (_equals_1) {
            String _t24Name_3 = version.getT24Name();
            String _plus_2 = ("Skipping generation of [" + _t24Name_3);
            String _plus_3 = (_plus_2 + "] [");
            String _plus_4 = (_plus_3 + thisVersionCollectionResource);
            String _plus_5 = (_plus_4 + "], already in our models project");
            this._logger.info(_plus_5);
            return null;
          }
        }
      }
    }
    String _t24Name_4 = version.getT24Name();
    String _plus_6 = ("Generating RIM for [" + _t24Name_4);
    String _plus_7 = (_plus_6 + "]");
    this._logger.info(_plus_7);
    final DomainDeclaration domain = RimFactory.eINSTANCE.createDomainDeclaration();
    String _domain = T24ResourceModelsGenerator.getDomain(null);
    domain.setName(_domain);
    EList<Ref> _rims = domainModel.getRims();
    _rims.add(domain);
    final EList<Ref> modelReferences = domain.getRims();
    ResourceMapperCommon.addUsedVersionDomains(modelReferences);
    final ImportedNamespaceFactory useFactory = new ImportedNamespaceFactory(modelReferences);
    useFactory.createUse(MappersConstants.RIM_USE_T24_CONTEXT_ENQUIRY);
    final ResourceInteractionModel rim = RimFactory.eINSTANCE.createResourceInteractionModel();
    rim.setName(entityName);
    modelReferences.add(rim);
    ApplicationType _type = T24Aspect.getType(mdfClass);
    final String applicationType = _type.name();
    T24VersionMapper mapper = null;
    boolean _isUnauthFile = ResourceMapperCommon.isUnauthFile(applicationType);
    if (_isUnauthFile) {
      Version2ResourceMapperForLiveNauHist _version2ResourceMapperForLiveNauHist = new Version2ResourceMapperForLiveNauHist(loader, mdfClass, application, version, rim);
      mapper = _version2ResourceMapperForLiveNauHist;
    } else {
      boolean _isOnlyLiveFile = ResourceMapperCommon.isOnlyLiveFile(applicationType);
      if (_isOnlyLiveFile) {
        Version2ResourceMapperForLiveOnly _version2ResourceMapperForLiveOnly = new Version2ResourceMapperForLiveOnly(loader, mdfClass, application, version, rim);
        mapper = _version2ResourceMapperForLiveOnly;
      } else {
        boolean _isVerifiable = ResourceMapperCommon.isVerifiable(applicationType);
        if (_isVerifiable) {
          Version2ResourceMapperForVerifyOnly _version2ResourceMapperForVerifyOnly = new Version2ResourceMapperForVerifyOnly(loader, mdfClass, application, version, rim);
          mapper = _version2ResourceMapperForVerifyOnly;
        }
      }
    }
    boolean _notEquals_1 = (!Objects.equal(mapper, null));
    if (_notEquals_1) {
      mapper.generate();
    }
    return domainModel;
  }
}
