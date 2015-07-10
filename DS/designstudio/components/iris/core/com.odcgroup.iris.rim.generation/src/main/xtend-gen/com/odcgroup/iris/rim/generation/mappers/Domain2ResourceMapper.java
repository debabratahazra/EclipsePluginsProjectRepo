package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.xtext.generator.IFileSystemAccess;

/**
 * This class is responsible for building the DomainModel from the MdfClass to build rim for the default list enquiry
 * of the application (represented by the MdfClass)
 */
@SuppressWarnings("all")
public class Domain2ResourceMapper {
  /**
   * Since looking for the fact that the resource already exist slow down a lot the generation,
   * allow to skip it. In the case of 500 domain in a 1/2 fully loaded environment, having this flag turned on
   * the rim generation takes 30 seconds. If the flag is turned off, this takes 166 seconds !
   */
  private final static boolean fastMode = "true".equals(System.getProperty("rim.fast.mode"));
  
  public /* DomainModel */Object mapDomain(final /* MdfClass */Object mdfClass, final ModelLoader modelLoader, final IFileSystemAccess fsa) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nState cannot be resolved to a type."
      + "\nThe method or field RimPackage.Literals is undefined for the type Domain2ResourceMapper"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nThe method getType is undefined for the type Domain2ResourceMapper"
      + "\nThe method getExistingEnquiry is undefined for the type Domain2ResourceMapper"
      + "\nCommandFactory cannot be resolved."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nname cannot be resolved"
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
      + "\n!= cannot be resolved"
      + "\nname cannot be resolved"
      + "\nname cannot be resolved"
      + "\ncommands cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateBasePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetBasepath cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateEvent cannot be resolved"
      + "\nhttpMethod cannot be resolved"
      + "\nname cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetTransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public static Map<String, String> buildTransitionParametersItem() {
    final HashMap<String, String> parameters = new HashMap<String, String>();
    parameters.put("id", "{Id}");
    return parameters;
  }
}
