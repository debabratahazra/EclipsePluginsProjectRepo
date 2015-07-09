/*
 * $RCSfile$
 * $Author$
 * $Revision$
 * $Date$
 *
 * Copyright (c) 2001-2014 TEMENOS HEADQUARTERS SA. All rights reserved.
 *
 * This source code is protected by copyright laws and international copyright treaties,
 * as well as other intellectual property laws and treaties.
 *  
 * Access to, alteration, duplication or redistribution of this source code in any form 
 * is not permitted without the prior written authorisation of TEMENOS HEADQUARTERS SA.
 * 
 */

package com.odcgroup.edge.t24.generation.version;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.BusinessDayControl;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipTrigger;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Function;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.translation.translationDsl.Translations;

/**
 * Proxy class to generate a "full view" (i.e. every field from the domain) version<p>
 *
 * NB: Use Eclipse's Generate "Delegate methods" to update when needed.
 * 
 * @author sakbar
 *
 */
public class FullViewVersionProxy extends ApplicationToVersionProxy
{
    private final Version m_originalVersion;

    public FullViewVersionProxy(Version p_version)
    {
        super( p_version.getForApplication() );
        
        m_originalVersion = p_version;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.notify.Notifier#eAdapters()
     */
    @Override
    public EList<Adapter> eAdapters()
    {
        return m_originalVersion.eAdapters();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
     */
    @Override
    public boolean eDeliver()
    {
        return m_originalVersion.eDeliver();
    }

    /**
     * @param p_deliver
     * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
     */
    @Override
    public void eSetDeliver(boolean p_deliver)
    {
        m_originalVersion.eSetDeliver( p_deliver );
    }

    /**
     * @param p_notification
     * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void eNotify(Notification p_notification)
    {
        m_originalVersion.eNotify( p_notification );
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eClass()
     */
    @Override
    public EClass eClass()
    {
        return m_originalVersion.eClass();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eResource()
     */
    @Override
    public Resource eResource()
    {
        return m_originalVersion.eResource();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainer()
     */
    @Override
    public EObject eContainer()
    {
        return m_originalVersion.eContainer();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
     */
    @Override
    public EStructuralFeature eContainingFeature()
    {
        return m_originalVersion.eContainingFeature();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
     */
    @Override
    public EReference eContainmentFeature()
    {
        return m_originalVersion.eContainmentFeature();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getForApplication()
     */
    @Override
    public MdfClass getForApplication()
    {
        return m_originalVersion.getForApplication();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContents()
     */
    @Override
    public EList<EObject> eContents()
    {
        return m_originalVersion.eContents();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setForApplication(com.odcgroup.mdf.metamodel.MdfClass)
     */
    @Override
    public void setForApplication(MdfClass p_value)
    {
        m_originalVersion.setForApplication( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getShortName()
     */
    @Override
    public String getShortName()
    {
        return m_originalVersion.getShortName();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eAllContents()
     */
    @Override
    public TreeIterator<EObject> eAllContents()
    {
        return m_originalVersion.eAllContents();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setShortName(java.lang.String)
     */
    @Override
    public void setShortName(String p_value)
    {
        m_originalVersion.setShortName( p_value );
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eIsProxy()
     */
    @Override
    public boolean eIsProxy()
    {
        return m_originalVersion.eIsProxy();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getT24Name()
     */
    @Override
    public String getT24Name()
    {
        return m_originalVersion.getT24Name();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setT24Name(java.lang.String)
     */
    @Override
    public void setT24Name(String p_value)
    {
        m_originalVersion.setT24Name( p_value );
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
     */
    @Override
    public EList<EObject> eCrossReferences()
    {
        return m_originalVersion.eCrossReferences();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getMetamodelVersion()
     */
    @Override
    public String getMetamodelVersion()
    {
        return m_originalVersion.getMetamodelVersion();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setMetamodelVersion(java.lang.String)
     */
    @Override
    public void setMetamodelVersion(String p_value)
    {
        m_originalVersion.setMetamodelVersion( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getGroup()
     */
    @Override
    public String getGroup()
    {
        return m_originalVersion.getGroup();
    }

    /**
     * @param p_feature
     * @return
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public Object eGet(EStructuralFeature p_feature)
    {
        return m_originalVersion.eGet( p_feature );
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setGroup(java.lang.String)
     */
    @Override
    public void setGroup(String p_value)
    {
        m_originalVersion.setGroup( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getNumberOfAuthorisers()
     */
    @Override
    public Integer getNumberOfAuthorisers()
    {
        return m_originalVersion.getNumberOfAuthorisers();
    }

    /**
     * @param p_feature
     * @param p_resolve
     * @return
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature, boolean)
     */
    @Override
    public Object eGet(EStructuralFeature p_feature, boolean p_resolve)
    {
        return m_originalVersion.eGet( p_feature, p_resolve );
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setNumberOfAuthorisers(java.lang.Integer)
     */
    @Override
    public void setNumberOfAuthorisers(Integer p_value)
    {
        m_originalVersion.setNumberOfAuthorisers( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getDescription()
     */
    @Override
    public Translations getDescription()
    {
        return m_originalVersion.getDescription();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setDescription(com.odcgroup.translation.translationDsl.Translations)
     */
    @Override
    public void setDescription(Translations p_value)
    {
        m_originalVersion.setDescription( p_value );
    }

    /**
     * @param p_feature
     * @param p_newValue
     * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     */
    @Override
    public void eSet(EStructuralFeature p_feature, Object p_newValue)
    {
        m_originalVersion.eSet( p_feature, p_newValue );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getExceptionProcessing()
     */
    @Override
    public Integer getExceptionProcessing()
    {
        return m_originalVersion.getExceptionProcessing();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setExceptionProcessing(java.lang.Integer)
     */
    @Override
    public void setExceptionProcessing(Integer p_value)
    {
        m_originalVersion.setExceptionProcessing( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getInterfaceException()
     */
    @Override
    public Integer getInterfaceException()
    {
        return m_originalVersion.getInterfaceException();
    }

    /**
     * @param p_feature
     * @return
     * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public boolean eIsSet(EStructuralFeature p_feature)
    {
        return m_originalVersion.eIsSet( p_feature );
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setInterfaceException(java.lang.Integer)
     */
    @Override
    public void setInterfaceException(Integer p_value)
    {
        m_originalVersion.setInterfaceException( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getBusinessDayControl()
     */
    @Override
    public BusinessDayControl getBusinessDayControl()
    {
        return m_originalVersion.getBusinessDayControl();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setBusinessDayControl(com.odcgroup.t24.version.versionDSL.BusinessDayControl)
     */
    @Override
    public void setBusinessDayControl(BusinessDayControl p_value)
    {
        m_originalVersion.setBusinessDayControl( p_value );
    }

    /**
     * @param p_feature
     * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public void eUnset(EStructuralFeature p_feature)
    {
        m_originalVersion.eUnset( p_feature );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getKeySequence()
     */
    @Override
    public EList<String> getKeySequence()
    {
        return m_originalVersion.getKeySequence();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getOtherCompanyAccess()
     */
    @Override
    public YesNo getOtherCompanyAccess()
    {
        return m_originalVersion.getOtherCompanyAccess();
    }

    /**
     * @param p_operation
     * @param p_arguments
     * @return
     * @throws InvocationTargetException
     * @see org.eclipse.emf.ecore.EObject#eInvoke(org.eclipse.emf.ecore.EOperation, org.eclipse.emf.common.util.EList)
     */
    @Override
    public Object eInvoke(EOperation p_operation, EList<?> p_arguments) throws InvocationTargetException
    {
        return m_originalVersion.eInvoke( p_operation, p_arguments );
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setOtherCompanyAccess(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setOtherCompanyAccess(YesNo p_value)
    {
        m_originalVersion.setOtherCompanyAccess( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAutoCompanyChange()
     */
    @Override
    public YesNo getAutoCompanyChange()
    {
        return m_originalVersion.getAutoCompanyChange();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setAutoCompanyChange(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setAutoCompanyChange(YesNo p_value)
    {
        m_originalVersion.setAutoCompanyChange( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getOverrideApproval()
     */
    @Override
    public YesNo getOverrideApproval()
    {
        return m_originalVersion.getOverrideApproval();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setOverrideApproval(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setOverrideApproval(YesNo p_value)
    {
        m_originalVersion.setOverrideApproval( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipFormats()
     */
    @Override
    public EList<DealSlip> getDealSlipFormats()
    {
        return m_originalVersion.getDealSlipFormats();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipTrigger()
     */
    @Override
    public DealSlipTrigger getDealSlipTrigger()
    {
        return m_originalVersion.getDealSlipTrigger();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setDealSlipTrigger(com.odcgroup.t24.version.versionDSL.DealSlipTrigger)
     */
    @Override
    public void setDealSlipTrigger(DealSlipTrigger p_value)
    {
        m_originalVersion.setDealSlipTrigger( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getDealSlipStyleSheet()
     */
    @Override
    public String getDealSlipStyleSheet()
    {
        return m_originalVersion.getDealSlipStyleSheet();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setDealSlipStyleSheet(java.lang.String)
     */
    @Override
    public void setDealSlipStyleSheet(String p_value)
    {
        m_originalVersion.setDealSlipStyleSheet( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getRecordsPerPage()
     */
    @Override
    public String getRecordsPerPage()
    {
        return m_originalVersion.getRecordsPerPage();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setRecordsPerPage(java.lang.String)
     */
    @Override
    public void setRecordsPerPage(String p_value)
    {
        m_originalVersion.setRecordsPerPage( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getFieldsPerLine()
     */
    @Override
    public String getFieldsPerLine()
    {
        return m_originalVersion.getFieldsPerLine();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setFieldsPerLine(java.lang.String)
     */
    @Override
    public void setFieldsPerLine(String p_value)
    {
        m_originalVersion.setFieldsPerLine( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getInitialCursorPosition()
     */
    @Override
    public String getInitialCursorPosition()
    {
        return m_originalVersion.getInitialCursorPosition();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setInitialCursorPosition(java.lang.String)
     */
    @Override
    public void setInitialCursorPosition(String p_value)
    {
        m_originalVersion.setInitialCursorPosition( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getBrowserToolbar()
     */
    @Override
    public String getBrowserToolbar()
    {
        return m_originalVersion.getBrowserToolbar();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setBrowserToolbar(java.lang.String)
     */
    @Override
    public void setBrowserToolbar(String p_value)
    {
        m_originalVersion.setBrowserToolbar( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getLanguageLocale()
     */
    @Override
    public EList<String> getLanguageLocale()
    {
        return m_originalVersion.getLanguageLocale();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getHeader1()
     */
    @Override
    public Translations getHeader1()
    {
        return m_originalVersion.getHeader1();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setHeader1(com.odcgroup.translation.translationDsl.Translations)
     */
    @Override
    public void setHeader1(Translations p_value)
    {
        m_originalVersion.setHeader1( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getHeader2()
     */
    @Override
    public Translations getHeader2()
    {
        return m_originalVersion.getHeader2();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setHeader2(com.odcgroup.translation.translationDsl.Translations)
     */
    @Override
    public void setHeader2(Translations p_value)
    {
        m_originalVersion.setHeader2( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getHeader()
     */
    @Override
    public Translations getHeader()
    {
        return m_originalVersion.getHeader();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setHeader(com.odcgroup.translation.translationDsl.Translations)
     */
    @Override
    public void setHeader(Translations p_value)
    {
        m_originalVersion.setHeader( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getFooter()
     */
    @Override
    public Translations getFooter()
    {
        return m_originalVersion.getFooter();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setFooter(com.odcgroup.translation.translationDsl.Translations)
     */
    @Override
    public void setFooter(Translations p_value)
    {
        m_originalVersion.setFooter( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getNextVersion()
     */
    @Override
    public Version getNextVersion()
    {
        return m_originalVersion.getNextVersion();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setNextVersion(com.odcgroup.t24.version.versionDSL.Version)
     */
    @Override
    public void setNextVersion(Version p_value)
    {
        m_originalVersion.setNextVersion( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getNextVersionFunction()
     */
    @Override
    public Function getNextVersionFunction()
    {
        return m_originalVersion.getNextVersionFunction();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setNextVersionFunction(com.odcgroup.t24.version.versionDSL.Function)
     */
    @Override
    public void setNextVersionFunction(Function p_value)
    {
        m_originalVersion.setNextVersionFunction( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getNextTransactionReference()
     */
    @Override
    public String getNextTransactionReference()
    {
        return m_originalVersion.getNextTransactionReference();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setNextTransactionReference(java.lang.String)
     */
    @Override
    public void setNextTransactionReference(String p_value)
    {
        m_originalVersion.setNextTransactionReference( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersions()
     */
    @Override
    public EList<Version> getAssociatedVersions()
    {
        return m_originalVersion.getAssociatedVersions();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getIncludeVersionControl()
     */
    @Override
    public YesNo getIncludeVersionControl()
    {
        return m_originalVersion.getIncludeVersionControl();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setIncludeVersionControl(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setIncludeVersionControl(YesNo p_value)
    {
        m_originalVersion.setIncludeVersionControl( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutines()
     */
    @Override
    public EList<Routine> getAuthorizationRoutines()
    {
        return m_originalVersion.getAuthorizationRoutines();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAuthorizationRoutinesAfterCommit()
     */
    @Override
    public EList<Routine> getAuthorizationRoutinesAfterCommit()
    {
        return m_originalVersion.getAuthorizationRoutinesAfterCommit();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getInputRoutines()
     */
    @Override
    public EList<Routine> getInputRoutines()
    {
        return m_originalVersion.getInputRoutines();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getInputRoutinesAfterCommit()
     */
    @Override
    public EList<Routine> getInputRoutinesAfterCommit()
    {
        return m_originalVersion.getInputRoutinesAfterCommit();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getKeyValidationRoutines()
     */
    @Override
    public EList<Routine> getKeyValidationRoutines()
    {
        return m_originalVersion.getKeyValidationRoutines();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getPreProcessValidationRoutines()
     */
    @Override
    public EList<Routine> getPreProcessValidationRoutines()
    {
        return m_originalVersion.getPreProcessValidationRoutines();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getWebValidationRoutines()
     */
    @Override
    public EList<Routine> getWebValidationRoutines()
    {
        return m_originalVersion.getWebValidationRoutines();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getConfirmVersion()
     */
    @Override
    public Version getConfirmVersion()
    {
        return m_originalVersion.getConfirmVersion();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setConfirmVersion(com.odcgroup.t24.version.versionDSL.Version)
     */
    @Override
    public void setConfirmVersion(Version p_value)
    {
        m_originalVersion.setConfirmVersion( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getPreviewVersion()
     */
    @Override
    public Version getPreviewVersion()
    {
        return m_originalVersion.getPreviewVersion();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setPreviewVersion(com.odcgroup.t24.version.versionDSL.Version)
     */
    @Override
    public void setPreviewVersion(Version p_value)
    {
        m_originalVersion.setPreviewVersion( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getChallengeResponse()
     */
    @Override
    public String getChallengeResponse()
    {
        return m_originalVersion.getChallengeResponse();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setChallengeResponse(java.lang.String)
     */
    @Override
    public void setChallengeResponse(String p_value)
    {
        m_originalVersion.setChallengeResponse( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAttributes()
     */
    @Override
    public EList<String> getAttributes()
    {
        return m_originalVersion.getAttributes();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getPublishWebService()
     */
    @Override
    public YesNo getPublishWebService()
    {
        return m_originalVersion.getPublishWebService();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setPublishWebService(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setPublishWebService(YesNo p_value)
    {
        m_originalVersion.setPublishWebService( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceActivity()
     */
    @Override
    public String getWebServiceActivity()
    {
        return m_originalVersion.getWebServiceActivity();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setWebServiceActivity(java.lang.String)
     */
    @Override
    public void setWebServiceActivity(String p_value)
    {
        m_originalVersion.setWebServiceActivity( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceFunction()
     */
    @Override
    public Function getWebServiceFunction()
    {
        return m_originalVersion.getWebServiceFunction();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setWebServiceFunction(com.odcgroup.t24.version.versionDSL.Function)
     */
    @Override
    public void setWebServiceFunction(Function p_value)
    {
        m_originalVersion.setWebServiceFunction( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceDescription()
     */
    @Override
    public String getWebServiceDescription()
    {
        return m_originalVersion.getWebServiceDescription();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setWebServiceDescription(java.lang.String)
     */
    @Override
    public void setWebServiceDescription(String p_value)
    {
        m_originalVersion.setWebServiceDescription( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getWebServiceNames()
     */
    @Override
    public EList<String> getWebServiceNames()
    {
        return m_originalVersion.getWebServiceNames();
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getGenerateIFP()
     */
    @Override
    public YesNo getGenerateIFP()
    {
        return m_originalVersion.getGenerateIFP();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setGenerateIFP(com.odcgroup.t24.version.versionDSL.YesNo)
     */
    @Override
    public void setGenerateIFP(YesNo p_value)
    {
        m_originalVersion.setGenerateIFP( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getAssociatedVersionsPresentationPattern()
     */
    @Override    
    public AssociatedVersionsPresentationPattern getAssociatedVersionsPresentationPattern()
    {
        return m_originalVersion.getAssociatedVersionsPresentationPattern();
    }

    /**
     * @param p_value
     * @see com.odcgroup.t24.version.versionDSL.Version#setAssociatedVersionsPresentationPattern(com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern)
     */
    @Override
    public void setAssociatedVersionsPresentationPattern(AssociatedVersionsPresentationPattern p_value)
    {
        m_originalVersion.setAssociatedVersionsPresentationPattern( p_value );
    }

    /**
     * @return
     * @see com.odcgroup.t24.version.versionDSL.Version#getFields()
     */
    @Override
    public EList<Field> getFields()
    {
        // Get the fields from the domain
        //
        return super.getFields();
        // return m_originalVersion.getFields();
    }
}
