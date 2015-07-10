package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class Version2ResourceMapper {
  private Logger _logger = LoggerFactory.getLogger(Version2ResourceMapper.class);
  
  /**
   * Since looking for the fact that the resource already exist slow down a lot the generation, allow to skip it
   */
  private final static boolean fastMode = "true".equals(System.getProperty("rim.fast.mode"));
  
  public /* DomainModel */Object mapVersion(final ModelLoader loader, final Version version) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nState cannot be resolved to a type."
      + "\nThe method getForApplication is undefined for the type Version2ResourceMapper"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nThe method or field RimPackage.Literals is undefined for the type Version2ResourceMapper"
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nImportedNamespaceFactory cannot be resolved."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nThe method getType is undefined for the type Version2ResourceMapper"
      + "\nInvalid number of arguments. The constructor Application(String, String, String, List<AppField>) is not applicable for the arguments (Object)"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateDomainModel cannot be resolved"
      + "\nLiterals cannot be resolved"
      + "\nSTATE cannot be resolved"
      + "\n!= cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateDomainDeclaration cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrims cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nrims cannot be resolved"
      + "\ncreateUse cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateResourceInteractionModel cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nname cannot be resolved");
  }
}
