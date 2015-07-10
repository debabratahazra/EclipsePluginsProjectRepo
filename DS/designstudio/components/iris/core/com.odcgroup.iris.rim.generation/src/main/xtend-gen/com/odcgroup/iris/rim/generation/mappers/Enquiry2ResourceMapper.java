package com.odcgroup.iris.rim.generation.mappers;

import com.google.common.base.Objects;
import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.EnquiryDrillDownResolver;
import com.odcgroup.iris.rim.generation.mappers.EventFactory;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
  
  public /* DomainModel */Object mapEnquiry(final ModelLoader loader, final Enquiry enquiry, final Application application) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nState cannot be resolved to a type."
      + "\nThe method or field RimPackage.Literals is undefined for the type Enquiry2ResourceMapper"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nCommandFactory cannot be resolved."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nThe method createGET is undefined for the type Enquiry2ResourceMapper"
      + "\nThe method createGET is undefined for the type Enquiry2ResourceMapper"
      + "\nThe method createGET is undefined for the type Enquiry2ResourceMapper"
      + "\nLiterals cannot be resolved"
      + "\nSTATE cannot be resolved"
      + "\n!= cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateDomainModel cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateDomainDeclaration cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrims cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nrims cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateResourceInteractionModel cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ncommands cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateBasePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetBasepath cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nname cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public static /* State */Object createImageStreamingItemResource(final CommandFactory commandFactory, final String commandName, final String resourceName, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type Enquiry2ResourceMapper"
      + "\nThe method createMethod is undefined for the type Enquiry2ResourceMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nsetView cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static Map<String, String> buildImageStreamingTransitionParametersItem(final EMEntity emEntity) {
    final HashMap<String, String> parameters = new HashMap<String, String>();
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
  
  public static boolean mapDynamicDrillDown(final DrillDown dd, final EnquiryDrillDownResolver drillDownResolver, final ModelLoader loader, final Enquiry enquiry, final String target, final Resource.RESOURCE_TYPE targetType, final String rimName, final /* EList<TransitionRef> */Object transitions, final EventFactory eventFactory) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method createGET is undefined for the type Enquiry2ResourceMapper");
  }
  
  public static /* TransitionRef */Object buildTransition(final /* TransitionRef */Object transition, final /* Event */Object event, final String targetStr, final String titleStr, final Map<String, String> parameters) {
    throw new Error("Unresolved compilation problems:"
      + "\nsetEvent cannot be resolved"
      + "\nsetName cannot be resolved");
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
