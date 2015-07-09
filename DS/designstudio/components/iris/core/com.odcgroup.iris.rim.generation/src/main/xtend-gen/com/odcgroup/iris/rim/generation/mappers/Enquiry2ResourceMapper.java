package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISEnquiryMapper;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.EnquiryDrillDownResolver;
import com.odcgroup.iris.rim.generation.mappers.EventFactory;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.t24.enquiry.enquiry.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType;
import com.odcgroup.t24.enquiry.enquiry.DownloadType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.enquiry.EnquiryType;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.JavaScriptType;
import com.odcgroup.t24.enquiry.enquiry.PWProcessType;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEType;
import com.odcgroup.t24.enquiry.enquiry.RunType;
import com.odcgroup.t24.enquiry.enquiry.ScreenType;
import com.odcgroup.t24.enquiry.enquiry.TabbedScreenType;
import com.odcgroup.t24.enquiry.enquiry.UtilType;
import com.odcgroup.t24.enquiry.enquiry.ViewType;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.interaction.rimdsl.rim.BasePath;
import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.DomainDeclaration;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.ImplRef;
import com.temenos.interaction.rimdsl.rim.MethodRef;
import com.temenos.interaction.rimdsl.rim.Path;
import com.temenos.interaction.rimdsl.rim.Ref;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.ResourceInteractionModel;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.RimPackage;
import com.temenos.interaction.rimdsl.rim.State;
import com.temenos.interaction.rimdsl.rim.TransitionRef;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class Enquiry2ResourceMapper {
  private Logger _logger = LoggerFactory.getLogger(Enquiry2ResourceMapper.class);
  
  /**
   * Define if we want the drilldown links to be strict or quoted.
   */
  private final static boolean strictMode = "true".equals(System.getProperty("rim.strict.mode"));
  
  /**
   * Since looking for the fact that the resource already exist slow down a lot the generation, allow to skip it
   */
  private final static boolean fastMode = "true".equals(System.getProperty("rim.fast.mode"));
  
  public DomainModel mapEnquiry(final ModelLoader loader, final Enquiry enquiry, final Application application) throws Exception {
    final EventFactory eventFactory = new EventFactory(loader, enquiry);
    Resource.RESOURCE_TYPE _TYPE_ENQUIRY = T24ResourceModelsGenerator.TYPE_ENQUIRY();
    String _name = enquiry.getName();
    String _camelCaseName = EMUtils.camelCaseName(_name);
    final String entityName = (_TYPE_ENQUIRY + _camelCaseName);
    final String thisEnquiryCollectionResource = (entityName + "s");
    if ((!Enquiry2ResourceMapper.fastMode)) {
      Object _namedEObjectOrProxy = loader.<Object>getNamedEObjectOrProxy(enquiry, thisEnquiryCollectionResource, RimPackage.Literals.STATE, true, false);
      final State targetEnquiryState = ((State) _namedEObjectOrProxy);
      boolean _notEquals = (!Objects.equal(targetEnquiryState, null));
      if (_notEquals) {
        final InternalEObject internal = ((InternalEObject) targetEnquiryState);
        final URI path = internal.eProxyURI();
        String[] _segments = path.segments();
        for (final String oneSegment : _segments) {
          boolean _equals = oneSegment.equals("adopted");
          if (_equals) {
            String _name_1 = enquiry.getName();
            String _plus = ("Skipping generation of [" + _name_1);
            String _plus_1 = (_plus + "] [");
            String _plus_2 = (_plus_1 + thisEnquiryCollectionResource);
            String _plus_3 = (_plus_2 + "], already in our models project");
            this._logger.info(_plus_3);
            return null;
          }
        }
      }
    }
    String _name_2 = enquiry.getName();
    String _plus_4 = ("Generating RIM for [" + _name_2);
    String _plus_5 = (_plus_4 + "]");
    this._logger.info(_plus_5);
    final DomainModel domainModel = RimFactory.eINSTANCE.createDomainModel();
    final IRISEnquiryMapper enquiryMapper = new IRISEnquiryMapper();
    final FieldTypeChecker fieldTypeChecker = new FieldTypeChecker(enquiry);
    final EMEntity emEntity = enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, Resource.RESOURCE_TYPE.enquiry);
    final DomainDeclaration domain = RimFactory.eINSTANCE.createDomainDeclaration();
    String _domain = T24ResourceModelsGenerator.getDomain(null);
    domain.setName(_domain);
    EList<Ref> _rims = domainModel.getRims();
    _rims.add(domain);
    final EList<Ref> modelReferences = domain.getRims();
    ResourceMapperCommon.addUsedDomains(modelReferences);
    final ResourceInteractionModel rim = RimFactory.eINSTANCE.createResourceInteractionModel();
    rim.setName(entityName);
    modelReferences.add(rim);
    EList<Command> _commands = rim.getCommands();
    final CommandFactory commandFactory = new CommandFactory(_commands);
    final BasePath basepath = RimFactory.eINSTANCE.createBasePath();
    basepath.setName("/{companyid}");
    rim.setBasepath(basepath);
    String collectionCommand = "";
    String itemCommand = "";
    boolean _isROEnquiry = Enquiry2ResourceMapper.isROEnquiry(enquiry);
    if (_isROEnquiry) {
      collectionCommand = "GETRODBEntities";
      itemCommand = "GETRODBEntity";
    } else {
      collectionCommand = "GETEntities";
      itemCommand = "GETEntity";
    }
    final State collection = ResourceMapperCommon.createCollectionResource(commandFactory, entityName, thisEnquiryCollectionResource, collectionCommand);
    EList<State> _states = rim.getStates();
    _states.add(collection);
    final State item = ResourceMapperCommon.createItemResource(commandFactory, itemCommand, entityName);
    final EList<TransitionRef> transitions = collection.getTransitions();
    Event _createGET = eventFactory.createGET();
    Map<String,String> _buildTransitionParametersItem = ResourceMapperCommon.buildTransitionParametersItem(emEntity);
    TransitionRef _createTransitionForEach = ResourceMapperCommon.createTransitionForEach(_createGET, item, null, _buildTransitionParametersItem);
    transitions.add(_createTransitionForEach);
    String _t24Name = application.getT24Name();
    boolean _equals_1 = "IM.DOCUMENT.IMAGE".equals(_t24Name);
    if (_equals_1) {
      final State imageStreamingItem = Enquiry2ResourceMapper.createImageStreamingItemResource(commandFactory, "ImageDownload", "T24.enqImageList.StreamImage", "enqImageList");
      Event _createGET_1 = eventFactory.createGET();
      Map<String,String> _buildImageStreamingTransitionParametersItem = Enquiry2ResourceMapper.buildImageStreamingTransitionParametersItem(emEntity);
      TransitionRef _createTransitionForEach_1 = ResourceMapperCommon.createTransitionForEach(_createGET_1, imageStreamingItem, null, _buildImageStreamingTransitionParametersItem);
      transitions.add(_createTransitionForEach_1);
    }
    EList<DrillDown> _drillDowns = enquiry.getDrillDowns();
    for (final DrillDown dd : _drillDowns) {
      {
        final EnquiryDrillDownResolver drillDownResolver = new EnquiryDrillDownResolver(enquiry);
        Resource.RESOURCE_TYPE targetType = T24ResourceModelsGenerator.TYPE_UNKNOWN();
        String target = "";
        final DrillDownType type = dd.getType();
        boolean _notEquals_1 = (!Objects.equal(type, null));
        if (_notEquals_1) {
          if ((type instanceof EnquiryType)) {
            Resource.RESOURCE_TYPE _TYPE_ENQUIRY_1 = T24ResourceModelsGenerator.TYPE_ENQUIRY();
            targetType = _TYPE_ENQUIRY_1;
            String _value = ((EnquiryType)type).getValue();
            target = _value;
          } else {
            if ((type instanceof ScreenType)) {
              Resource.RESOURCE_TYPE _TYPE_VERSION = T24ResourceModelsGenerator.TYPE_VERSION();
              targetType = _TYPE_VERSION;
              String _value_1 = ((ScreenType)type).getValue();
              target = _value_1;
            } else {
              if ((type instanceof CompositeScreenType)) {
                Resource.RESOURCE_TYPE _TYPE_COMPOSITE = T24ResourceModelsGenerator.TYPE_COMPOSITE();
                targetType = _TYPE_COMPOSITE;
                String _value_2 = ((CompositeScreenType)type).getValue();
                target = _value_2;
              } else {
                if ((type instanceof TabbedScreenType)) {
                  Resource.RESOURCE_TYPE _TYPE_COMPOSITE_1 = T24ResourceModelsGenerator.TYPE_COMPOSITE();
                  targetType = _TYPE_COMPOSITE_1;
                  String _value_3 = ((TabbedScreenType)type).getValue();
                  target = _value_3;
                } else {
                  if ((type instanceof FromFieldType)) {
                    Resource.RESOURCE_TYPE _TYPE_DYNAMIC = T24ResourceModelsGenerator.TYPE_DYNAMIC();
                    targetType = _TYPE_DYNAMIC;
                    String _value_4 = ((FromFieldType)type).getValue();
                    target = _value_4;
                  } else {
                    if ((type instanceof QuitSEEType)) {
                      Resource.RESOURCE_TYPE _TYPE_VERSION_1 = T24ResourceModelsGenerator.TYPE_VERSION();
                      targetType = _TYPE_VERSION_1;
                      String _t24Name_1 = application.getT24Name();
                      target = _t24Name_1;
                      EnquiryFactoryImpl _enquiryFactoryImpl = new EnquiryFactoryImpl();
                      final Parameters parameters = _enquiryFactoryImpl.createParameters();
                      parameters.setFunction(FunctionKind.SEE);
                      EList<String> _fieldName = parameters.getFieldName();
                      String _value_5 = ((QuitSEEType)type).getValue();
                      _fieldName.add(0, _value_5);
                      dd.setParameters(parameters);
                    } else {
                      if ((type instanceof ApplicationType)) {
                        Resource.RESOURCE_TYPE _TYPE_VERSION_2 = T24ResourceModelsGenerator.TYPE_VERSION();
                        targetType = _TYPE_VERSION_2;
                        String _value_6 = ((ApplicationType)type).getValue();
                        target = _value_6;
                      } else {
                        if ((type instanceof BlankType)) {
                          Resource.RESOURCE_TYPE _TYPE_UNKNOWN = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                          targetType = _TYPE_UNKNOWN;
                        } else {
                          if ((type instanceof ViewType)) {
                            Resource.RESOURCE_TYPE _TYPE_UNKNOWN_1 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                            targetType = _TYPE_UNKNOWN_1;
                            String _value_7 = ((ViewType)type).getValue();
                            target = _value_7;
                            String _name_3 = enquiry.getName();
                            String _plus_6 = ("Unsupported type of DrillDown in Enquiry [ " + _name_3);
                            String _plus_7 = (_plus_6 + " ] : ");
                            Class<? extends ViewType> _class = ((ViewType)type).getClass();
                            String _name_4 = _class.getName();
                            String _plus_8 = (_plus_7 + _name_4);
                            String _plus_9 = (_plus_8 + "\"");
                            this._logger.error(_plus_9);
                          } else {
                            if ((type instanceof DownloadType)) {
                              Resource.RESOURCE_TYPE _TYPE_UNKNOWN_2 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                              targetType = _TYPE_UNKNOWN_2;
                              String _value_8 = ((DownloadType)type).getValue();
                              target = _value_8;
                              String _name_5 = enquiry.getName();
                              String _plus_10 = ("Unsupported type of DrillDown in Enquiry [ " + _name_5);
                              String _plus_11 = (_plus_10 + " ] : ");
                              Class<? extends DownloadType> _class_1 = ((DownloadType)type).getClass();
                              String _name_6 = _class_1.getName();
                              String _plus_12 = (_plus_11 + _name_6);
                              String _plus_13 = (_plus_12 + "\"");
                              this._logger.error(_plus_13);
                            } else {
                              if ((type instanceof PWProcessType)) {
                                Resource.RESOURCE_TYPE _TYPE_UNKNOWN_3 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                                targetType = _TYPE_UNKNOWN_3;
                                String _value_9 = ((PWProcessType)type).getValue();
                                target = _value_9;
                                String _name_7 = enquiry.getName();
                                String _plus_14 = ("Unsupported type of DrillDown in Enquiry [ " + _name_7);
                                String _plus_15 = (_plus_14 + " ] : ");
                                Class<? extends PWProcessType> _class_2 = ((PWProcessType)type).getClass();
                                String _name_8 = _class_2.getName();
                                String _plus_16 = (_plus_15 + _name_8);
                                String _plus_17 = (_plus_16 + "\"");
                                this._logger.error(_plus_17);
                              } else {
                                if ((type instanceof UtilType)) {
                                  Resource.RESOURCE_TYPE _TYPE_UNKNOWN_4 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                                  targetType = _TYPE_UNKNOWN_4;
                                  String _value_10 = ((UtilType)type).getValue();
                                  target = _value_10;
                                  String _name_9 = enquiry.getName();
                                  String _plus_18 = ("Unsupported type of DrillDown in Enquiry [ " + _name_9);
                                  String _plus_19 = (_plus_18 + " ] : ");
                                  Class<? extends UtilType> _class_3 = ((UtilType)type).getClass();
                                  String _name_10 = _class_3.getName();
                                  String _plus_20 = (_plus_19 + _name_10);
                                  String _plus_21 = (_plus_20 + "\"");
                                  this._logger.error(_plus_21);
                                } else {
                                  if ((type instanceof RunType)) {
                                    Resource.RESOURCE_TYPE _TYPE_UNKNOWN_5 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                                    targetType = _TYPE_UNKNOWN_5;
                                    String _value_11 = ((RunType)type).getValue();
                                    target = _value_11;
                                    String _name_11 = enquiry.getName();
                                    String _plus_22 = ("Unsupported type of DrillDown in Enquiry [ " + _name_11);
                                    String _plus_23 = (_plus_22 + " ] : ");
                                    Class<? extends RunType> _class_4 = ((RunType)type).getClass();
                                    String _name_12 = _class_4.getName();
                                    String _plus_24 = (_plus_23 + _name_12);
                                    String _plus_25 = (_plus_24 + "\"");
                                    this._logger.error(_plus_25);
                                  } else {
                                    if ((type instanceof JavaScriptType)) {
                                      Resource.RESOURCE_TYPE _TYPE_UNKNOWN_6 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                                      targetType = _TYPE_UNKNOWN_6;
                                      String _value_12 = ((JavaScriptType)type).getValue();
                                      target = _value_12;
                                      String _name_13 = enquiry.getName();
                                      String _plus_26 = ("Unsupported type of DrillDown in Enquiry [ " + _name_13);
                                      String _plus_27 = (_plus_26 + " ] : ");
                                      Class<? extends JavaScriptType> _class_5 = ((JavaScriptType)type).getClass();
                                      String _name_14 = _class_5.getName();
                                      String _plus_28 = (_plus_27 + _name_14);
                                      String _plus_29 = (_plus_28 + "\"");
                                      this._logger.error(_plus_29);
                                    } else {
                                      Resource.RESOURCE_TYPE _TYPE_UNKNOWN_7 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
                                      targetType = _TYPE_UNKNOWN_7;
                                      String _name_15 = enquiry.getName();
                                      String _plus_30 = ("Unsupported type of DrillDown in Enquiry [ " + _name_15);
                                      String _plus_31 = (_plus_30 + " ] : ");
                                      Class<? extends DrillDownType> _class_6 = type.getClass();
                                      String _name_16 = _class_6.getName();
                                      String _plus_32 = (_plus_31 + _name_16);
                                      String _plus_33 = (_plus_32 + "\"");
                                      this._logger.warn(_plus_33);
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        } else {
          Resource.RESOURCE_TYPE _TYPE_UNKNOWN_8 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
          targetType = _TYPE_UNKNOWN_8;
          String _name_17 = enquiry.getName();
          String _plus_34 = ("Impossible to determine the type of drillDown in Enquiry [ " + _name_17);
          String _plus_35 = (_plus_34 + " ]");
          this._logger.error(_plus_35);
        }
        Resource.RESOURCE_TYPE _TYPE_DYNAMIC_1 = T24ResourceModelsGenerator.TYPE_DYNAMIC();
        boolean _equals_2 = Objects.equal(targetType, _TYPE_DYNAMIC_1);
        if (_equals_2) {
          String _name_18 = rim.getName();
          Enquiry2ResourceMapper.mapDynamicDrillDown(dd, drillDownResolver, loader, enquiry, target, targetType, _name_18, transitions, eventFactory);
        } else {
          Resource.RESOURCE_TYPE _TYPE_UNKNOWN_9 = T24ResourceModelsGenerator.TYPE_UNKNOWN();
          boolean _notEquals_2 = (!Objects.equal(targetType, _TYPE_UNKNOWN_9));
          if (_notEquals_2) {
            final HashMap<String,String> linkParameters = new HashMap<String, String>();
            final List<EnquiryDrillDownResolver.Parameter> parameters_1 = drillDownResolver.resolveParameters(targetType, target, dd);
            boolean _notEquals_3 = (!Objects.equal(parameters_1, null));
            if (_notEquals_3) {
              for (final EnquiryDrillDownResolver.Parameter oneParam : parameters_1) {
                linkParameters.put(oneParam.type, oneParam.value);
              }
            }
            final String targetDomain = T24ResourceModelsGenerator.getDomain(null);
            String _string = targetType.toString();
            String _camelCaseName_1 = EMUtils.camelCaseName(target);
            final String targetRim = (_string + _camelCaseName_1);
            String _targetResourceName = drillDownResolver.getTargetResourceName();
            String _plus_36 = ((((targetDomain + ".") + targetRim) + ".") + _targetResourceName);
            target = _plus_36;
            if ((!Enquiry2ResourceMapper.strictMode)) {
              target = (("\"" + target) + "\"");
            }
            Event _createGET_2 = eventFactory.createGET();
            Translations _description = dd.getDescription();
            String _languageString = Enquiry2ResourceMapper.getLanguageString(_description, 0);
            String _drill_name = dd.getDrill_name();
            TransitionRef _createTransitionForEach_2 = ResourceMapperCommon.createTransitionForEach(_createGET_2, target, _languageString, linkParameters, _drill_name);
            transitions.add(_createTransitionForEach_2);
          }
        }
      }
    }
    final EList<State> resources = rim.getStates();
    resources.add(collection);
    resources.add(item);
    return domainModel;
  }
  
  public static State createImageStreamingItemResource(final CommandFactory commandFactory, final String commandName, final String resourceName, final String entityName) {
    final State item = RimFactory.eINSTANCE.createState();
    item.setName(resourceName);
    ResourceType _createItemResourceType = ResourceMapperCommon.createItemResourceType();
    item.setType(_createItemResourceType);
    Entity _createEntity = ResourceMapperCommon.createEntity(entityName);
    item.setEntity(_createEntity);
    final ImplRef itemImpl = RimFactory.eINSTANCE.createImplRef();
    HashMap<String,String> _hashMap = new HashMap<String, String>();
    final ResourceCommand itemCommand = commandFactory.createResourceCommand(commandName, _hashMap);
    itemImpl.setView(itemCommand);
    EList<MethodRef> _methods = itemImpl.getMethods();
    MethodRef _createMethod = commandFactory.createMethod("GET", itemCommand);
    _methods.add(_createMethod);
    item.setImpl(itemImpl);
    final Path itemPath = RimFactory.eINSTANCE.createPath();
    itemPath.setName((("/" + entityName) + "s(\'{id}\')"));
    item.setPath(itemPath);
    return item;
  }
  
  public static Map<String,String> buildImageStreamingTransitionParametersItem(final EMEntity emEntity) {
    final HashMap<String,String> parameters = new HashMap<String, String>();
    parameters.put("customer_id", "XXX");
    List<EMProperty> _properties = emEntity.getProperties();
    for (final EMProperty property : _properties) {
      String _t24Name = property.getT24Name();
      boolean _equals = "IMAGE".equals(_t24Name);
      if (_equals) {
        String _name = property.getName();
        String _camelCaseName = EMUtils.camelCaseName(_name);
        String _plus = ("{" + _camelCaseName);
        String _plus_1 = (_plus + "}");
        parameters.put("document_id", _plus_1);
      }
    }
    return parameters;
  }
  
  /**
   * Check if enquiry is from a RO database.
   */
  public static boolean isROEnquiry(final Enquiry enquiry) {
    EnquiryMode _enquiryMode = enquiry.getEnquiryMode();
    boolean _equals = Objects.equal(_enquiryMode, EnquiryMode.DB);
    if (_equals) {
      return true;
    }
    return false;
  }
  
  public static boolean mapDynamicDrillDown(final DrillDown dd, final EnquiryDrillDownResolver drillDownResolver, final ModelLoader loader, final Enquiry enquiry, final String target, final Resource.RESOURCE_TYPE targetType, final String rimName, final EList<TransitionRef> transitions, final EventFactory eventFactory) {
    boolean _xblockexpression = false;
    {
      final HashMap<String,String> linkParameters = new HashMap<String, String>();
      final List<EnquiryDrillDownResolver.Parameter> parameters = drillDownResolver.resolveParameters(targetType, target, dd);
      boolean _notEquals = (!Objects.equal(parameters, null));
      if (_notEquals) {
        for (final EnquiryDrillDownResolver.Parameter oneParam : parameters) {
          linkParameters.put(oneParam.type, oneParam.value);
        }
      }
      final String locatorName = "t24ResourceLocator";
      String _camelCaseName = EMUtils.camelCaseName(target);
      String _plus = ((("locator " + locatorName) + "(\"{") + _camelCaseName);
      final String targetStateName = (_plus + "}\")");
      Event _createGET = eventFactory.createGET();
      Translations _description = dd.getDescription();
      String _languageString = Enquiry2ResourceMapper.getLanguageString(_description, 0);
      TransitionRef transition = ResourceMapperCommon.createTransitionForEach(_createGET, targetStateName, _languageString, linkParameters);
      _xblockexpression = transitions.add(transition);
    }
    return _xblockexpression;
  }
  
  public static TransitionRef buildTransition(final TransitionRef transition, final Event event, final String targetStr, final String titleStr, final Map<String,String> parameters) {
    transition.setEvent(event);
    transition.setName(targetStr);
    return ResourceMapperCommon.buildTransitionSpec(transition, titleStr, parameters);
  }
  
  public static String getLanguageString(final Translations translations, final int language) {
    boolean _notEquals = (!Objects.equal(translations, null));
    if (_notEquals) {
      final EList<EObject> strings = translations.eContents();
      EObject _get = strings.get(language);
      final LocalTranslation translation = ((LocalTranslation) _get);
      boolean _notEquals_1 = (!Objects.equal(translation, null));
      if (_notEquals_1) {
        return translation.getText();
      }
    }
    return null;
  }
}
