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

package com.odcgroup.edge.t24.generation;

import com.acquire.util.ConvertUtility;
import com.acquire.util.EdgeSystem;
import com.acquire.util.StringUtils;
import com.temenos.connect.BrowserOptions;

/**
 * Options class for generation.
 *
 * @author sakbar
 *
 */
public final class EGenOptions
{
    // FIXME: Ideally these need to be configurable options in the DS plugin (especially SOLUTION_PROJECT)
    //
    // We could then create library contexts directly rather than re-load for verify and also we could set the design attributes
    // from the solution before saving the project .. so it would be identical to the loaded one in the IDE, so compares 
    // on the presentation editor don't include false-positives.
    //
    // This could then also be passed to the embedded server.
    //
    private static final String  PROP_SOLUTION_PROJECT_PATH             = "egen.options.solutionProjectPath";
    private static final String  PROP_VERIFY_GENERATED_PROJECT          = "egen.options.verifyGeneratedProject";
    private static final String  PROP_TREAT_VERIFIER_WARNINGS_AS_ERRORS = "egen.options.treatVerifierWarningsAsErrors";
    private static final String  PROP_DISPLAY_STATS                     = "egen.options.displayStats";

    private static final String  PROP_RUN_GC                            = "egen.options.runGC";
    
    // Profile options
    //
    private static final String  PROP_PROFILE_TIME                      = "egen.options.profileTime";
    private static final String  PROP_PROFILE_MEMORY                    = "egen.options.profileMemory";

    // FIXME: Remove these two and associated code as they should be redundant once the "manual take over" approach is proven
    //
    private static final String  PROP_COMPONENT_OUTPUT_LOCATION         = "egen.options.componentOutputLocation";
    private static final String  PROP_COMPONENT_MAP_FILE_PATH           = "egen.options.componentMapFilePath";

    // FIXME: Remove this once we can re-enable displaying of errors (NB: Worth having an official switch instead)?
    //
    private static final String  PROP_THROW_UP_ON_ERROR                 = "egen.options.throwUpOnError";

    private static final String  PROP_FORCE_GENERATION                  = "egen.options.forceGeneration";

    private static final String  PROP_IN_DEVELOPMENT                    = "egen.options.inDevelopment";

    /** The Constant PROP_TEMPLATE_CACHE with value of {@value} - default to <b>{@link TemplateCacheOption#CACHED}</b> */
    private static final String  PROP_TEMPLATE_CACHE                    = "egen.options.template.cache";
    
    private static final String  PROP_MARKUP_LANGUAGE                   = "egen.options.markup.language";

    private static final String  SOLUTION_PROJECT_PATH                  = EdgeSystem.getProperty( PROP_SOLUTION_PROJECT_PATH, "com.odcgroup.edge.solutionProject" );
    private static final boolean VERIFY_GENERATED_PROJECT               = EdgeSystem.getBooleanProperty( PROP_VERIFY_GENERATED_PROJECT, "com.odcgroup.edge.verifyGeneratedProject" );
    private static final boolean TREAT_VERIFIER_WARNINGS_AS_ERRORS      = EdgeSystem.getBooleanProperty( PROP_TREAT_VERIFIER_WARNINGS_AS_ERRORS, "com.odcgroup.edge.treatVerifierWarningsAsErrors" );

    private static final boolean DISPLAY_STATS                          = EdgeSystem.getBooleanProperty( PROP_DISPLAY_STATS, "com.odcgroup.edge.displayStats" );

    private static final boolean PROFILE_TIME                           = EdgeSystem.getBooleanProperty( PROP_PROFILE_TIME, "com.odcgroup.edge.profileTime" );
    private static final boolean PROFILE_MEMORY                         = EdgeSystem.getBooleanProperty( PROP_PROFILE_MEMORY, "com.odcgroup.edge.profileMemory" );

    private static final long    RUN_GC                                 = ConvertUtility.convertToLong( EdgeSystem.getProperty( PROP_RUN_GC ), 0);

    private static final String  COMPONENT_OUTPUT_LOCATION              = EdgeSystem.getProperty( PROP_COMPONENT_OUTPUT_LOCATION, "com.odcgroup.edge.componentOutputLocation" );
    private static final String  COMPONENT_MAP_FILE_PATH                = EdgeSystem.getProperty( PROP_COMPONENT_MAP_FILE_PATH, "com.odcgroup.edge.mapFile" );

    private static final boolean FORCE_GENERATION                       = EdgeSystem.getBooleanProperty( PROP_FORCE_GENERATION );

    private static final boolean IN_DEVELOPMENT                         = EdgeSystem.getBooleanProperty( PROP_IN_DEVELOPMENT );
   
    private static final TemplateCacheOption  TEMPLATE_CACHE            = TemplateCacheOption.valueOf( StringUtils.blankDef( EdgeSystem.getProperty( PROP_TEMPLATE_CACHE ), TemplateCacheOption.CLONED.name() ) ); 
    
    private static final EThrowUpOnError THROWUP_ON_ERROR               = getThrowUpOption(EdgeSystem.getProperty( PROP_THROW_UP_ON_ERROR, "com.odcgroup.edge.throwUp" ));

    private static final boolean MARKUP_LANGUAGE                        = EdgeSystem.getBooleanProperty( PROP_MARKUP_LANGUAGE ); 

    
    public enum EThrowUpOnError
    {
        KEEP_DOWN, AS_SOON_AS_POSSIBLE, AT_THE_END;
    }
    
    public enum TemplateCacheOption
    {
        UNCACHED, CACHED, CLONED;
    }

    private EGenOptions() {}
 
    /**
     * Gets the solution project to use, currently used for verifying generated library projects, see {@link #verifyGeneratedProject()}<p>
     * 
     * Based on the system property - {@value #PROP_SOLUTION_PROJECT_PATH}
     *
     * @return the solution project .ifp, null if not specified
     */
    public static String solutionProjectPath()
    {
        return SOLUTION_PROJECT_PATH;
    }
    
    /**
     * Indicates if we should verify the generated project. Also see {@link #solutionProjectPath()}<p>
     *
     * Based on boolean system property - {@value #PROP_VERIFY_GENERATED_PROJECT}
     * 
     * @return true, if should
     */
    public static boolean verifyGeneratedProject()
    {
        return VERIFY_GENERATED_PROJECT;
    }
    
    /**
     * Indicates if we should treat verify warnings as errors (when used with {@link #verifyGeneratedProject()} )
     *
     * Based on boolean system property - {@value #PROP_TREAT_VERIFIER_WARNINGS_AS_ERRORS}
     * 
     * @return true, if should
     */
    public static boolean treatVerifierWarningsAsErrors()
    {
        return TREAT_VERIFIER_WARNINGS_AS_ERRORS;
    }
    
    /**
     * Indicates if we should display stats for the generated project(s).<p>
     *
     * Based on boolean system property - {@value #PROP_DISPLAY_STATS}
     * 
     * @return true, if should
     */
    public static boolean displayStats()
    {
        return DISPLAY_STATS;
    }
    
    /**
     * Indicates if we should profile time taken for generating projects, which will be displayed as part of the logs and also stats .. see {@link #displayStats()}.<p>
     *
     * Based on boolean system property - {@value #PROP_PROFILE_TIME}
     * 
     * @return true, if should
     */
    public static boolean profileTime()
    {
        return PROFILE_TIME;
    }
    
    /**
     * Indicates if we should profile memory used for generating projects, which will be displayed as part of the logs and also stats .. see {@link #displayStats()}.<p>
     *
     * Based on boolean system property - {@value #PROP_PROFILE_MEMORY}
     * 
     * @return true, if should
     */
    public static boolean profileMemory()
    {
        return PROFILE_MEMORY;
    }
    
    
    /**
     * Indicates if we should run garbage collection, such as before/after generation<p> 
     * 
     * This is also used to indicate how many milliseconds to pause.<p>
     *
     * Based on system property - {@value #PROP_RUN_GC}
     * 
     * @return if > 0, run GC and pause for specified time in ms
     */
    public static long runGC()
    {
        return RUN_GC;
    }
    
    /**
     * Gets the directory to generate components into<p>
     * 
     * Based on the system property - {@value #PROP_COMPONENT_OUTPUT_LOCATION}
     *
     * @return the component output location of the directory, null if not specified
     */
    public static String componentOutputLocation()
    {
        return COMPONENT_OUTPUT_LOCATION;
    }
    
    /**
     * Gets the full location of the component map file to generate into.
     * 
     * Based on the system property - {@value #PROP_COMPONENT_MAP_FILE_PATH}
     *
     * @return the component map file path, null if not specified
     */
    public static String componentMapFilePath()
    {
        return COMPONENT_MAP_FILE_PATH;
    }
    
    
    /**
     * Indicates if we should throw up an exception on error and what type of throwing up required
     *
     * Based on system property - {@value #PROP_THROW_UP_ON_ERROR}
     * 
     * @return true, if should
     */
    public static EThrowUpOnError throwupOnError()
    {
        return THROWUP_ON_ERROR;
    }
    

    /**
     * Indicates if we should force generation, ignoring the modified date of the underlying model. 
     *
     * Based on boolean system property - {@value #PROP_FORCE_GENERATION}
     * 
     * @return true, if should
     */
    public static boolean forceGeneration()
    {
        return FORCE_GENERATION;
    }

    
    /**
     * Indicates if we are in development<p>
     *
     * Based on boolean system property - {@value #PROP_IN_DEVELOPMENT}
     *   
     * @return true, if in development otherwise assume in production
     */
    public static boolean inDevelopment()
    {
        return IN_DEVELOPMENT;
    }

    /**
     * Option for template caching, which is one of the options below, default to CLONED<p>
     * <ul>
     *      <li>{@link TemplateCacheOption#UNCACHED}
     *      <li>{@link TemplateCacheOption#CACHED}
     *      <li>{@link TemplateCacheOption#CLONED}
     * </ul>
     *
     * Based on system property - {@value #PROP_TEMPLATE_CACHE}
     *   
     * @return template cache option chosen
     */
    public static TemplateCacheOption templateCache()
    {
        return TEMPLATE_CACHE;
    }
    
    private static EThrowUpOnError getThrowUpOption(String p_throwUpProperty) 
    {
        EThrowUpOnError throwUp = EThrowUpOnError.KEEP_DOWN;
        
        if  ( StringUtils.isNotBlank( p_throwUpProperty ) )
        {
            if ( "Y".equalsIgnoreCase( p_throwUpProperty ) )
            {
                throwUp = EThrowUpOnError.AS_SOON_AS_POSSIBLE;
            }
            else
            {
                try
                {
                    throwUp = EThrowUpOnError.valueOf( p_throwUpProperty.toUpperCase() );
                }
                catch (Exception p_ex)
                {
                    throwUp = EThrowUpOnError.AT_THE_END;
                }
            }
        }
        
        return throwUp;
    }

    /**
     * Markup's generated language specific text. See {@link BrowserOptions#markupLanguage()} for details.
     * 
     * @return
     */
    public static boolean markupLanguage()
    {
        return MARKUP_LANGUAGE;
    }
}

