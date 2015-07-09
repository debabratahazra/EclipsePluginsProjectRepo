package com.odcgroup.edge.t24.generation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import java.util.List;

import com.acquire.intelligentforms.entities.types.DecimalType;
import com.acquire.intelligentforms.entities.types.ListType;
import com.acquire.intelligentforms.entities.types.NumberType;
import com.acquire.intelligentforms.entities.types.TextType;
import com.acquire.util.IDebuggable;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.temenos.connect.T24Browser.BrowserUtility;


/**
 * Design time field details (including details about groups), which implements comparable in order to sort the order of field generation.
 *
 * @author saleem.akbar
 *
 */
public interface IFieldMapper<T> extends Comparable<T>, IDebuggable 
{
    public String getName();
    public String getProcessedName();
    public String getQualifiedName();

    public TextTranslations getLabelText(EdgeMapper<?> p_edgeMapper);
    public TextTranslations getHelpText(EdgeMapper<?> p_edgeMapper);
    public TextTranslations getHintText(EdgeMapper<?> p_edgeMapper);
    
    public boolean isMandatory();
    public boolean isReadOnly();
    public RichHTMLPresentationQuestionWrapper.EReadOnlyFormat getReadOnlyFormat(); 

    public boolean isProcessable();
    public boolean isDisplayed();

    public int getMaxInputLength();
    public int getMaxDisplayLength();

    public int getOrder();
    
    public MdfProperty  getMdfProperty();
    public MdfClass     getForApplication();
    public MdfClass     getType();
    public String       getBusinessType(); 
    
    //changes to retrieve the frequency types
    public String       getTypeModifiers();
    @Override
    public int compareTo(T p_field);

    public boolean      isField();
    public boolean      isGroup();
    public int          getGroupMultiplicity();
    public List<T>      getGroupInputFields();
    
    public T            getPreviousField();
    public void         setPreviousField(T   p_previousField);

    public EMappingType getMappingType();

    public enum EMappingType
    {
        SINGLE_INPUT_FIELD, GROUP_OF_INPUT_FIELDS, UNKNOWN;
    }
    
    public String getFieldTypeName();
    
    public EFieldType getFieldType();
    
    public enum EFieldType
    {
        DATE_TIME(BrowserUtility.DATE_TIME_CUSTOM_TYPE, "dateTime", null ), // Disabled as IRIS barfs at the moment if basic type info is set -"Edm.dateTime" ),
        DATE(TextType.TYPE, "date", null),
        INTEGER(NumberType.TYPE, "integer", null ),
        DOUBLE(DecimalType.TYPE, "double", null ),
        STRING(TextType.TYPE, "string", null ),
        ASTERISK(TextType.TYPE, "string", null ),
        LIST(ListType.TYPE, "string", null ),
        FREQUENCY(BrowserUtility.FREQUENCY_CUSTOM_TYPE, "string", null ), // Q. Maybe introduce a custom frequency data type so long as widgets can be associated with those?
        MULTI_LANGUAGE_GROUP(BrowserUtility.MULTI_LANGUAGE_GROUP_CUSTOM_TYPE, "string", null ),
        TEXT_AREA_GROUP(BrowserUtility.TEXT_AREA_GROUP_CUSTOM_TYPE, "string", null) // Ignore the max input length (as we do now) as that's to stop long words rather than the full text
        ;
    
        String m_connectType = null;
        String m_applicationType = null;
        String m_irisType = null;
        
        EFieldType(String p_connectType, String p_applicationType, String p_irisType)
        {
            m_connectType = p_connectType;
            m_applicationType = p_applicationType;
            m_irisType = p_irisType;
        }
        
        /**
         * @return the connectType
         */
        public String getConnectType()
        {
            return m_connectType;
        }
        
        /**
         * @return the applicationType
         */
        public String getApplicationType()
        {
            return m_applicationType;
        }
        
        /**
         * @return the irisType, null if no specific type available
         */
        public String getIrisType()
        {
            return m_irisType;
        }
    }
}
