package com.odcgroup.edge.t24.generation.version;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import com.acquire.util.DetailFormatter;
import com.acquire.util.IDebuggable.DebugLevel;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.util.ApplicationUtility;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.BusinessDayControl;
import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipTrigger;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.DisplayType;
import com.odcgroup.t24.version.versionDSL.Expansion;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Function;
import com.odcgroup.t24.version.versionDSL.InputBehaviour;
import com.odcgroup.t24.version.versionDSL.PopupBehaviour;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.translation.translationDsl.impl.TranslationDslFactoryImpl;

/**
 * Proxy class to allow an application (i.e. first class from a domain) to mimic a version.
 * 
 * Most methods in here deliberately throw an UnsupportedOperationException so that if they get used we can decide what the correct action should be taken.
 *
 * @author saleem.akbar
 *
 */
public class ApplicationToVersionProxy implements Version
{
    private final MdfDomain              m_mdfDomain;
    private final MdfClass               m_forApplication;
    private Translations                 m_header1;
    private Translations                 m_description;
    private EList<Field>                 m_fields;
    private int                          m_mvCount = 0;

    public ApplicationToVersionProxy(MdfClass p_forApplication)
    {
        m_mdfDomain = p_forApplication.getParentDomain();
        
        m_forApplication = p_forApplication; 
    }

    @Override
    public MdfClass getForApplication()
    {
        return m_forApplication;
    }

    @Override
    public EList<Field> getFields()
    {
        if  ( m_fields == null )
        {
            m_fields = new BasicEList<Field>();
            
            List<MdfProperty> properties = m_forApplication.getProperties();
            
            loadFieldProxies( properties, 0, 0, 0 );
        }
        
        return m_fields;
    }

    /**
     * @param p_properties
     */
    @SuppressWarnings("unchecked")
    protected void loadFieldProxies(List<MdfProperty> p_properties, final int p_level, final int p_mvGroup, final int p_svGroup)
    {
        // Three levels of nesting:
        //  0 = Top level
        //  1 = MV
        //  2 = SV
        //
        
        if  ( p_level > 2 ) throw new RuntimeException("Only expecting upto 2 levels of nesting!");
        
        int mvGroup = p_mvGroup;
        int svGroup = p_svGroup;

        for (MdfProperty property : p_properties)
        {
            boolean isGroup = ( property instanceof MdfAssociation && ((MdfAssociation)property).getContainment() == MdfConstants.CONTAINMENT_BYVALUE );
            
            if  ( isGroup )
            {
                if  ( p_level == 0 )
                {
                    mvGroup++;
                }
                else if  ( p_level == 1 )
                {
                    svGroup++;
                }
                
                MdfClass mdfClass = (MdfClass) property.getType();
                
                loadFieldProxies( mdfClass.getProperties(), p_level + 1, mvGroup, svGroup );
            }
            else
            {
                FieldProxy fieldProxy = this.new FieldProxy(property, p_mvGroup, p_svGroup);
                m_fields.add( fieldProxy );
            }
        }
    }

    @Override
    public Translations getHeader1()
    {
        if  ( m_header1 == null )
        {
            m_header1 = createEnglishTranslation( m_mdfDomain.getDocumentation() );
        }

        return m_header1;
    }

    @Override
    public Translations getHeader()
    {
        return null;
    }

    @Override
    public Translations getHeader2()
    {
        return null;    
    }

    @Override
    public EList<Version> getAssociatedVersions()
    {
        return null;
    }

    @Override
    public String getShortName()
    {
        return m_mdfDomain.getName();
    }

    @Override
    public String getT24Name()
    {
        return m_mdfDomain.getName();
    }

    @Override
    public String getMetamodelVersion()
    {
        return m_mdfDomain.getMetamodelVersion();
    }

    @Override
    public YesNo getGenerateIFP()
    {
        return YesNo.YES;
    }
    
    private Translations createEnglishTranslation(String p_englishText)
    {
        LocalTranslation localTranslation = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslation();
        localTranslation.setLocale(Locale.ENGLISH.getLanguage());
        localTranslation.setText(p_englishText);
        
        LocalTranslations localTranslations = TranslationDslFactoryImpl.eINSTANCE.createLocalTranslations();
        localTranslations.getTranslations().add(localTranslation);
        
        return(localTranslations);
    }
    
    private final class FieldProxy implements Field
    {
        private final MdfProperty m_mdfProperty;
        private Integer           m_mvGroup;
        private Integer           m_svGroup;

        private FieldProxy(MdfProperty p_property, int p_mvGroup, int p_svGroup)
        {
            m_mdfProperty   = p_property;
            m_mvGroup       = Integer.valueOf( p_mvGroup );
            m_svGroup       = Integer.valueOf( p_svGroup );
        }

        @Override
        public EClass eClass()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Resource eResource()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EObject eContainer()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EStructuralFeature eContainingFeature()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EReference eContainmentFeature()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<EObject> eContents()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public TreeIterator<EObject> eAllContents()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean eIsProxy()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<EObject> eCrossReferences()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object eGet(EStructuralFeature p_feature)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object eGet(EStructuralFeature p_feature, boolean p_resolve)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void eSet(EStructuralFeature p_feature, Object p_newValue)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean eIsSet(EStructuralFeature p_feature)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void eUnset(EStructuralFeature p_feature)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object eInvoke(EOperation p_operation, EList<?> p_arguments) throws InvocationTargetException
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<Adapter> eAdapters()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean eDeliver()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void eSetDeliver(boolean p_deliver)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void eNotify(Notification p_notification)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName()
        {
            return m_mdfProperty.getName();
        }

        @Override
        public void setName(String p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public DisplayType getDisplayType()
        {
            return DisplayType.NONE;
        }

        @Override
        public void setDisplayType(DisplayType p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public InputBehaviour getInputBehaviour()
        {
            // FIXME: Can convert?? return T24Aspect.getInputBehaviour( m_property );
            return InputBehaviour.NONE;
        }

        @Override
        public void setInputBehaviour(InputBehaviour p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public CaseConvention getCaseConvention()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCaseConvention(CaseConvention p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getMaxLength()
        {
            return T24Aspect.getMaxLength(m_mdfProperty);
        }

        @Override
        public void setMaxLength(Integer p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getEnrichmentLength()
        {
            return 0;
            //return 20;
        }

        @Override
        public void setEnrichmentLength(Integer p_value)
        {
            throw new UnsupportedOperationException();        
        }

        @Override
        public Expansion getExpansion()
        {
            return Expansion.NONE;
        }

        @Override
        public void setExpansion(Expansion p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getRightAdjust()
        {
            return YesNo.NO;
        }

        @Override
        public void setRightAdjust(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getEnrichment()
        {
            return YesNo.NO;
        }

        @Override
        public void setEnrichment(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getColumn()
        {
            return 0;
        }

        @Override
        public void setColumn(Integer p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getRow()
        {
            return 0;
        }

        @Override
        public void setRow(Integer p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getMandatory()
        {
            return ApplicationUtility.isMandatory(m_mdfProperty) ? YesNo.YES : YesNo.NO;
        }

        @Override
        public void setMandatory(YesNo p_value)
        {
            throw new UnsupportedOperationException();        
        }

        @Override
        public YesNo getRekeyRequired()
        {
            return YesNo.NO;
        }

        @Override
        public void setRekeyRequired(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getHyperlink()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setHyperlink(String p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getHotValidate()
        {
            return YesNo.NO; // FIXME: Correct? throw new UnsupportedOperationException();
        }

        @Override
        public void setHotValidate(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getHotField()
        {
            return YesNo.NO; // FIXME: Correct?throw new UnsupportedOperationException();
        }

        @Override
        public void setHotField(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public YesNo getWebValidate()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWebValidate(YesNo p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getSelectionEnquiry()
        {
            return null;
        }

        @Override
        public void setSelectionEnquiry(String p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getEnquiryParameter()
        {
            return null;
        }

        @Override
        public void setEnquiryParameter(String p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public PopupBehaviour getPopupBehaviour()
        {
            return null;
        }

        @Override
        public void setPopupBehaviour(PopupBehaviour p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<Default> getDefaults()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Translations getLabel()
        {
            // FIXME: get real label .. create util and also use in ApplicationFieldMapper
            //
            return createEnglishTranslation( StringUtils.getWords( m_mdfProperty.getName().toLowerCase(), StringUtils.DEFAULT_WORD_SEPARATORS) );
        }

        @Override
        public void setLabel(Translations p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Translations getToolTip()
        {
            return null;
        }

        @Override
        public void setToolTip(Translations p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<Routine> getValidationRoutines()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public EList<String> getAttributes()
        {
            // FIXME: ???
            return null;
        }

        @Override
        public Integer getMV()
        {
            return m_mvGroup;
        }

        @Override
        public void setMV(Integer p_value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer getSV()
        {
            return m_svGroup;
        }

        @Override
        public void setSV(Integer p_value)
        {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public String toString()
        {
            StringBuilder buff = new StringBuilder("Field Proxy For ");
            
            DetailFormatter.toString( m_mdfProperty, DebugLevel.HIGH, buff );
            
            buff.append( " -> " ).append(m_mdfProperty);
            
            return buff.toString();
        }
    }

    @Override
    public Translations getDescription()
    {
        if  ( m_description == null )
        {
            m_description = createEnglishTranslation( m_mdfDomain.getDocumentation() );
        }

        return m_description;
    }
    
// ******************** Rest of the methods thrown an UnsupportedOperationException ************************  

    @Override
    public Version getNextVersion()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EClass eClass()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Resource eResource()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EObject eContainer()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EStructuralFeature eContainingFeature()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EReference eContainmentFeature()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<EObject> eContents()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public TreeIterator<EObject> eAllContents()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean eIsProxy()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<EObject> eCrossReferences()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object eGet(EStructuralFeature p_feature)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object eGet(EStructuralFeature p_feature, boolean p_resolve)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void eSet(EStructuralFeature p_feature, Object p_newValue)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean eIsSet(EStructuralFeature p_feature)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void eUnset(EStructuralFeature p_feature)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object eInvoke(EOperation p_operation, EList<?> p_arguments) throws InvocationTargetException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Adapter> eAdapters()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean eDeliver()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void eSetDeliver(boolean p_deliver)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void eNotify(Notification p_notification)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setForApplication(MdfClass p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setShortName(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setT24Name(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetamodelVersion(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGroup()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGroup(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getNumberOfAuthorisers()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNumberOfAuthorisers(Integer p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDescription(Translations p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getExceptionProcessing()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExceptionProcessing(Integer p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getInterfaceException()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInterfaceException(Integer p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public BusinessDayControl getBusinessDayControl()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBusinessDayControl(BusinessDayControl p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<String> getKeySequence()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public YesNo getOtherCompanyAccess()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOtherCompanyAccess(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public YesNo getAutoCompanyChange()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAutoCompanyChange(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public YesNo getOverrideApproval()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOverrideApproval(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<DealSlip> getDealSlipFormats()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public DealSlipTrigger getDealSlipTrigger()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDealSlipTrigger(DealSlipTrigger p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDealSlipStyleSheet()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDealSlipStyleSheet(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRecordsPerPage()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRecordsPerPage(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFieldsPerLine()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFieldsPerLine(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInitialCursorPosition()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInitialCursorPosition(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBrowserToolbar()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBrowserToolbar(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<String> getLanguageLocale()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader1(Translations p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader2(Translations p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader(Translations p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Translations getFooter()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFooter(Translations p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextVersion(Version p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Function getNextVersionFunction()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextVersionFunction(Function p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNextTransactionReference()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextTransactionReference(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public YesNo getIncludeVersionControl()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIncludeVersionControl(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getAuthorizationRoutines()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getAuthorizationRoutinesAfterCommit()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getInputRoutines()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getInputRoutinesAfterCommit()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getKeyValidationRoutines()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getPreProcessValidationRoutines()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<Routine> getWebValidationRoutines()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Version getConfirmVersion()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setConfirmVersion(Version p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Version getPreviewVersion()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPreviewVersion(Version p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getChallengeResponse()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setChallengeResponse(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<String> getAttributes()
    {
        return null;
    }

    @Override
    public YesNo getPublishWebService()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPublishWebService(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getWebServiceActivity()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWebServiceActivity(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Function getWebServiceFunction()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWebServiceFunction(Function p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getWebServiceDescription()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWebServiceDescription(String p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EList<String> getWebServiceNames()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGenerateIFP(YesNo p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public AssociatedVersionsPresentationPattern getAssociatedVersionsPresentationPattern()
    {
        return null;
    }

    @Override
    public void setAssociatedVersionsPresentationPattern(AssociatedVersionsPresentationPattern p_value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        StringBuilder buff = new StringBuilder("Application Proxy For ");
        
        DetailFormatter.toString( m_mdfDomain, DebugLevel.HIGH, buff );
        
        buff.append( " -> " ).append(m_mdfDomain);
        
        return buff.toString();
    }

    @Override
    public FieldsLayoutPattern getFieldsLayoutPattern()
    {
        return null;
    }

    @Override
    public void setFieldsLayoutPattern(FieldsLayoutPattern p_value)
    {
        throw new UnsupportedOperationException();
    }
}
