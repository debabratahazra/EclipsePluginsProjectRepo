package com.odcgroup.iris.rim.generation.mappers;

import com.odcgroup.iris.generator.Application;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.Resource;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.iris.rim.generation.mappers.CommandFactory;
import com.odcgroup.iris.rim.generation.mappers.EventFactory;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import java.util.Map;

/**
 * Super class which will provide common functionality required for different types of Version2Resource Mappers
 * 
 * @author sjunejo
 */
@SuppressWarnings("all")
public abstract class T24VersionMapper {
  protected ModelLoader loader;
  
  protected /* MdfClass */Object mdfClass;
  
  protected Application application;
  
  protected Version version;
  
  protected EMEntity emEntity;
  
  protected String entityName;
  
  protected String thisVersionCollectionResource;
  
  protected /* ResourceInteractionModel */Object rim;
  
  protected EventFactory eventFactory;
  
  protected CommandFactory commandFactory;
  
  protected String applicationType;
  
  protected String additionalInfo;
  
  protected boolean isAAVersion;
  
  protected boolean isAAArr;
  
  protected FieldTypeChecker fieldTypeChecker;
  
  /**
   * A Flag to know if we already tried to get the errorResource or not
   */
  protected static boolean errorsResourceSearched = false;
  
  protected static /* State */Object checkIfErrorResource = null;
  
  protected static /* State */Object processErrorResource = null;
  
  protected static /* State */Object errorsResource = null;
  
  protected static /* State */Object customErrorHandler = null;
  
  protected static /* State */Object metadataResource = null;
  
  protected static /* State */Object regularMetadataResourceForAADeals = null;
  
  public T24VersionMapper(final ModelLoader _loader, final /* MdfClass */Object _mdfClass, final Application _application, final Version _version, final /* ResourceInteractionModel */Object _rim) {
    this.loader = _loader;
    this.mdfClass = _mdfClass;
    this.application = _application;
    this.version = _version;
    this.rim = _rim;
    FieldTypeChecker _fieldTypeChecker = new FieldTypeChecker(_version);
    this.fieldTypeChecker = _fieldTypeChecker;
    this.initialiseRIM();
  }
  
  private void initialiseRIM() {
    throw new Error("Unresolved compilation problems:"
      + "\nCommandFactory cannot be resolved."
      + "\nRimFactory.eINSTANCE cannot be resolved to a type."
      + "\nThe method getType is undefined for the type T24VersionMapper"
      + "\nThe method getAdditionalInfo is undefined for the type T24VersionMapper"
      + "\nThe method isAAResource is undefined for the type T24VersionMapper"
      + "\ncommands cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateBasePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetBasepath cannot be resolved"
      + "\nname cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  private Object initialiseErrorHandler() {
    throw new Error("Unresolved compilation problems:"
      + "\nState cannot be resolved to a type."
      + "\nState cannot be resolved to a type."
      + "\nState cannot be resolved to a type."
      + "\nThe method or field RimPackage.Literals is undefined for the type T24VersionMapper"
      + "\nThe method or field RimPackage.Literals is undefined for the type T24VersionMapper"
      + "\nThe method or field RimPackage.Literals is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createPOST is undefined for the type T24VersionMapper"
      + "\nLiterals cannot be resolved"
      + "\nSTATE cannot be resolved"
      + "\nLiterals cannot be resolved"
      + "\nSTATE cannot be resolved"
      + "\nLiterals cannot be resolved"
      + "\nSTATE cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nstates cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public abstract void generate();
  
  public static /* TransitionRef */Object createContextEnquiryTransition(final ModelLoader loader, final Version version, final String entityName, final EventFactory eventFactory) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method createGET is undefined for the type T24VersionMapper");
  }
  
  public static /* State */Object createNewResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createCopyResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createDeliveryPreviewResource(final CommandFactory commandFactory, final String entityName, final EventFactory eventFactory) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public static /* State */Object getDealSlipsResource(final CommandFactory commandFactory, final String entityName, final EventFactory eventFactory) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved");
  }
  
  public static /* State */Object createPasteResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createInputResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createCustomeErrorHandlerResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createVerifyResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createHoldResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createAuthoriseResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createChangedValuesResource(final CommandFactory commandFactory, final String entityName, final String command) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createRestoreResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createAuditResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createReverseResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createDeleteResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createValidateResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  /**
   * Create the resource for 'http://temenostech.temenos.com/rels/see'
   * t24Type can be :
   *     null  -> live
   *    "inau" -> IAuth
   *    "hist" -> "HAuth"
   */
  public static /* State */Object createSeeResource(final String t24Type, final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createRegularMetadataResourceForAADeals(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createMetadataResource(final CommandFactory commandFactory, final String entityName, final boolean isAAResource) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createEntryResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
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
  
  public static /* State */Object createPopulateResource(final CommandFactory commandFactory, final String entityName, final boolean isAAVersion) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createAAPopulateResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRelationConstant cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nrelations cannot be resolved"
      + "\nadd cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved");
  }
  
  public static /* State */Object createAutoResource(final CommandFactory commandFactory, final String entityName) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
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
      + "\nsetImpl cannot be resolved");
  }
  
  public static String buildDefaultListEnquiryStateName(final Application application) {
    String _t24Name = application.getT24Name();
    final String applicationName = EMUtils.camelCaseName(_t24Name);
    Resource.RESOURCE_TYPE _TYPE_ENQLIST = T24ResourceModelsGenerator.TYPE_ENQLIST();
    final String resourceName = (_TYPE_ENQLIST + applicationName);
    String enqListName = (((("T24." + resourceName) + ".") + resourceName) + "s");
    return enqListName;
  }
  
  public static /* TransitionRef */Object addOkCondition(final /* TransitionRef */Object transition, final /* State */Object targetResource) {
    throw new Error("Unresolved compilation problems:"
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateExpression cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateOKFunction cannot be resolved"
      + "\nsetState cannot be resolved"
      + "\nexpressions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nspec cannot be resolved"
      + "\nsetEval cannot be resolved");
  }
  
  /**
   * Create the resource returning all the contextEnquiries'
   * 
   * all the List & Search (Live, Unauth, Hist) + a link to the ContextEnquiries resource
   */
  public static /* State */Object createContextEnquiriesResource(final Application application, final CommandFactory commandFactory, final EventFactory eventFactory, final String entityName, final String applicationType, final boolean isAAArr) {
    throw new Error("Unresolved compilation problems:"
      + "\nTransitionRef cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createResourceCommand is undefined for the type T24VersionMapper"
      + "\nThe method createMethod is undefined for the type T24VersionMapper"
      + "\nRimFactory cannot be resolved to a type."
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\nThe method createGET is undefined for the type T24VersionMapper"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateState cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nsetEntity cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateImplRef cannot be resolved"
      + "\nactions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nmethods cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsetImpl cannot be resolved"
      + "\neINSTANCE cannot be resolved"
      + "\ncreatePath cannot be resolved"
      + "\nsetName cannot be resolved"
      + "\nsetPath cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public static void embedErrorResource(final /* State */Object resource, final /* Event */Object event, final String title, final Map<String, String> params) {
    throw new Error("Unresolved compilation problems:"
      + "\n!= cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public static void embedConditionalErrors(final /* State */Object resource, final /* Event */Object event, final String title, final Map<String, String> params) {
    throw new Error("Unresolved compilation problems:"
      + "\n!= cannot be resolved"
      + "\n&& cannot be resolved"
      + "\n!= cannot be resolved"
      + "\ntransitions cannot be resolved"
      + "\nadd cannot be resolved");
  }
}
