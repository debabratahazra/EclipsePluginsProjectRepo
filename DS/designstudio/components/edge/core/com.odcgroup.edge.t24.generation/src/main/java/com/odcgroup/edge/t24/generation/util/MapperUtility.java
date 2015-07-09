package com.odcgroup.edge.t24.generation.util;

import gen.com.acquire.intelligentforms.entities.FormListWrapper;
import gen.com.acquire.intelligentforms.entities.ListItemWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationButtonWrapper;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.util.ExceptionUtility;
import com.odcgroup.edge.t24.generation.EGenOptions;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.IFieldMapper;
import com.odcgroup.edge.t24.generation.languagemaps.Language;
import com.odcgroup.edge.t24.generation.languagemaps.TargetTranslationLanguageSet;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public final class MapperUtility
{
    private MapperUtility() {}

    public static FormListWrapper createTrueFalseList(FormContext p_formContext)
    {
        // Create the "TrueFalse" List.
        //
        FormListWrapper trueFalse = FormListWrapper.create( p_formContext );
    
        trueFalse.setName( "TrueFalse" );
    
        // Create the "True (true)" List Item.
        //
        ListItemWrapper trueTrue = ListItemWrapper.create( p_formContext );
    
        trueTrue.setKey( "true" );
    
        trueTrue.setValue( "True" );
    
        // Add the "True (true)" List Item to the "TrueFalse" List.
        //
        trueFalse.addChild(trueTrue);
    
        // Create the "False (false)" List Item.
        //
        ListItemWrapper falseFalse = ListItemWrapper.create( p_formContext );
    
        falseFalse.setKey( "false" );
    
        falseFalse.setValue( "False" );
    
        // Add the "False (false)" List Item to the "TrueFalse" List.
        //
        trueFalse.addChild(falseFalse);
    
        return(trueFalse);
    }

    /**
     * Process t24 name to the equivalent IRIS name.
     *
     * @param p_t24Name the t24 name
     * @return the IRIS name
     */
    public static String processT24NameToIRISName(String p_t24Name)
    {
        return EMUtils.camelCaseName( p_t24Name );
    }
    
    /**
     * In-line sort of mapped fields.
     *
     * @param <T> the generic type
     * @param p_mappedFields the mapped fields
     * @return the list
     */
    public static <T extends IFieldMapper<T>> List<T> sortMappedFields(List<T> p_mappedFields)
    {
        // Sort em by row/col/field order etc
        //
        Collections.sort( p_mappedFields );
        
        // Set previous fields
        //
        for (int i = 1; i < p_mappedFields.size(); i++)
        {
            T field = p_mappedFields.get( i );
            
            field.setPreviousField( p_mappedFields.get( i - 1 ) );
        }
    
        return(p_mappedFields);
    }

    /**
     * Set p_button to be a link button
     * 
     * @param p_button
     */
    public static void setLinkButton(RichHTMLPresentationButtonWrapper p_button)
    {
        p_button.setDesignToUseFromEntityKey( "Link Button" );

        p_button.setEditDesignToUse( "N" );

        p_button.setDisplayType( "Use Link" );

        p_button.setButtonStyle( "LinkButton" );
    }
    
    /**
     * Logs the error message and optional exception and abort by throwing a RuntimeException.<p>
     * 
     * However, if {@link EGenOptions#inDevelopment()} is true, then logs to the error console instead of throwing a RuntimeException.
     * 
     * @param p_logger          
     * @param p_errorMessage
     * @param p_exception       - Optional
     */
    // TODO: Move to builder, once log4j in place
    //
    public static void continueOrAbort(Logger p_logger, String p_errorMessage, Exception p_exception)
    {
        if  ( p_exception != null )
        {
            p_logger.error( p_errorMessage );
        }
        else
        {
            p_logger.error( p_errorMessage, p_exception );
        }
        
        if ( ! EGenOptions.inDevelopment() )
        {
            if  ( p_exception != null )
            {
                ExceptionUtility.outputErrorToSysErr( null, p_exception, p_errorMessage );
                
                throw new RuntimeException(p_errorMessage, p_exception);
            }
            
            throw new RuntimeException(p_errorMessage);
        }
    }
   
    /**
     * Gets the specified annotation of the property
     * 
     * @param p_property
     * @param p_annotationName
     * @return the annotation, null if not found
     */
    public static String getAnnotation(MdfProperty p_property, String p_annotationName)
    {
        String value = null;
        
        List annotations = p_property.getAnnotations();
        
        if  ( annotations != null && annotations.size() > 0 )
        {
            MdfAnnotation mdfAnnotation = (MdfAnnotation) annotations.get( 0 );
            
            MdfAnnotationProperty annotationProperty = mdfAnnotation.getProperty(p_annotationName);
            
            if  ( annotationProperty != null )
            {
                value = annotationProperty.getValue();
            }
        }
        
        return value;
    }
    
    public static String addSlang(EdgeMapper<?> p_edgeMapper, String p_slangKey, TextTranslations p_translations)
    {
        StringBuilder buff = new StringBuilder();
        
        buff.append("$%SLANG ").append(p_slangKey).append( " " ).append( p_translations.getText() ).append("$");
        
        // Store the default text as base text .. but allow it to be overwritten properly later (if available)
        //
        p_edgeMapper.getSlangManager().addSlang(TargetTranslationLanguageSet.getBaseISOLanguageCode().value, p_slangKey, p_translations.getText() );
    
        final Iterator<Language> targetLanguageIter = TargetTranslationLanguageSet.iterator();
        
        while(targetLanguageIter.hasNext())
        {
            final Language targetLanguage = targetLanguageIter.next();
            
            String text = p_translations.getText( targetLanguage.isoCode );
            
            if  ( text != null )
            {
                p_edgeMapper.getSlangManager().addSlang( targetLanguage.isoCode.value, p_slangKey, text );
            }
        }
        
        return buff.toString();
    }
}
