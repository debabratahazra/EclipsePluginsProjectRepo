package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import com.acquire.intelligentforms.IPresentationConfigurable;
import com.acquire.intelligentforms.entities.GenericNode;
import com.acquire.intelligentforms.entities.ItemGroup;
import com.acquire.intelligentforms.entities.Phase;
import com.acquire.intelligentforms.entities.presentation.IPresentationOnly;
import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreak;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreak;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;


/**
 * <code>VisualWrapperObjectPair</code> provides an abstract type-paramaterised base for concrete sub-classes whose instances are effectively immutable pairings of process-side wrapper objects
 * with their (<u>non</u> presentation-only) presentation-side counterparts.<p>
 * 
 * <b>IMPORTANT:</b> This class is NOT intended for use as a base for classes representing pairings featuring "presentation-only" {@link PresentationObjectWrapper} sub-types (the
 * {@link #VisualWrapperObjectPair(GenericNodeWrapper, PresentationObjectWrapper) constructor} includes runtime checks that would prevent any such attempt from getting to first base).<p>
 * 
 * <i>(If you're actually looking for a class to manage parallel addition of child wrapper pairs into a process-side element (e.g. a {@link Phase} or an {@link ItemGroup}) and a presentation-only container
 * item (e.g. a {@link RichHTMLPresentationFormatBreak} or {@link RichHTMLPresentationColumnBreak}), then {@link VisualItemContainerWrapperPair} is your man).</i><p> 
 *
 * For simplicity / consistency of use across all sub-types, this class provides direct access to its two <code>public final</code> fields <i>(hence the absences of "getter" methods for these)</i><p>
 *
 * Given the checks performed on {@link #VisualWrapperObjectPair(GenericNodeWrapper, PresentationObjectWrapper) construction}, the holder of a non-null reference is <u>guaranteed</u>:<p>
 * 
 * <ul>
 *     <li>
 *         that {@link #wrapperObject} and {@link #presWrapperObject} are <u>both</u> non <code>null</code>
 *     </li>
 *     
 *     <li>
 *         that {@link #wrapperObject} wraps a {@link GenericNodeWrapper} sub-type corresponding a <u>visual</u> process-side entity (i.e. one mapped to a {@link PresentationObject}
 *         sub-type from which all applicable sub-types pertinent to that {@link GenericNodeWrapper} sub-type are derived)
 *     </li>
 *     <li>
 *         that {@link #presWrapperObject} wraps a non-{@link IPresentationOnly} {@link PresentationObject} sub-type that is valid for the particular {@link GenericNode} sub-type wrapped by
 *         {@link #wrapperObject}
 *     </li>
 *     <li>
 *         that {@link #presWrapperObject} refers to {@link #wrapperObject} as its process-side counterpart (and not some other instance of the same type)
 *     </li>
 * </ul>
 * 
 * @see     VisualItemContainerWrapperPair
 * 
 * @author  Simon Hayes
 */
public abstract class VisualWrapperObjectPair<WrapperObjType extends GenericNodeWrapper<?>, PresWrapperObjType extends PresentationObjectWrapper<?>>
{
    /** <code>wrapperObject</code> is the wrapper for the the visual process-side entity in this pair <i>(guaranteed non null)</i> */
    public final WrapperObjType wrapperObject;
    
    /** <code>presWrapperObject</code> is the wrapper for the presentation-side counterpart for {@link #wrapperObject} <i>(guaranteed non null)</i> */
    public final PresWrapperObjType presWrapperObject;
    
    /**
     * @param p_wrapperObj                  the wrapper for the visual, process-side entity in this pair <i>(must be non <code>null</code>)</i>
     * @param p_presWrapperObj              the wrapper for the presentation-side counterpart for {@link #wrapperObject} <i>(guaranteed non null)</i>
     * 
     * @throws  IllegalArgumentException    if either argument is <code>null</code>, <code>p_wrapperObj</code> wraps a non-visual entity, <code>p_presWrapperObj</code> wraps a
     *                                      presentation-only entity (or an entity that is not a valid presentation counterpart for <code>p_wrapperObj</code>), or
     *                                      <code>p_presWrapperObj</code> does not refer to <code>p_wrapperObj</code> as its process-side entity wrapper
     *                                      
     * @throws  IllegalStateException       if <code>p_wrapperObj</code> and <code>p_presWrapperObj</code> have <u>different</u> parentage states
     *                                      (i.e. its fine for <u>both</u> to be parented or <u>both</u> to be unparented, but anything else is so likely to be a programming error
     *                                      that we throw an exception).
     */
    protected VisualWrapperObjectPair(WrapperObjType p_wrapperObj, PresWrapperObjType p_presWrapperObj)
    {
        AssertionUtils.requireNonNull(p_wrapperObj, "p_wrapperObj");
        AssertionUtils.requireNonNull(p_presWrapperObj, "p_presWrapperObj");
        
        if (! IPresentationConfigurable.class.isAssignableFrom(p_wrapperObj.unwrap().getClass()))
            throw new IllegalArgumentException("p_wrapperObj type: " + p_wrapperObj.getClass().getSimpleName() + " wraps a non-visual entity !");
        
        if (IPresentationOnly.class.isAssignableFrom(p_presWrapperObj.unwrap().getClass()))
            throw new IllegalArgumentException("p_presWrapperObj type: " + p_presWrapperObj.getClass().getSimpleName() + " represents a presentation-only entity (which is not what " + VisualWrapperObjectPair.class.getSimpleName() + " is for !)");
        
        final IPresentationConfigurable presConfigurableWrappedEntity = (IPresentationConfigurable) p_wrapperObj.unwrap();
        
        if (! presConfigurableWrappedEntity.getPresentationObjectClass().isAssignableFrom(p_presWrapperObj.unwrap().getClass()))
            throw new IllegalArgumentException("p_presWrapperObj type: " + p_presWrapperObj.getClass().getSimpleName() + " is not the right " + PresentationObject.class.getSimpleName() + " counterpart for p_wrapperObj type: " + p_wrapperObj.getClass().getSimpleName());
        
        if (p_presWrapperObj.getEntityNodeWrapper() != p_wrapperObj)
            throw new IllegalArgumentException("p_presWrapperObj was not created for p_wrapperObj !");

        final GenericNode
            parentNode = p_wrapperObj.unwrap().getParent(),
            parentPresNode = p_presWrapperObj.unwrap().getParent();
        
        final boolean
            nodeHasParent = (parentNode != null),
            presNodeHasParent = (parentPresNode != null);
        
        if (presNodeHasParent != nodeHasParent)
        {
            final String
                wrapperObjClassName = p_wrapperObj.getClass().getSimpleName(),
                presWrapperObjClassName = p_presWrapperObj.getClass().getSimpleName(),
                
                parentNodeClassName = nodeHasParent ? parentNode.getClass().getSimpleName() : null,
                parentPresNodeClassName = presNodeHasParent ? parentPresNode.getClass().getSimpleName() : null;
            
            final String eMsg = (nodeHasParent ?
                wrapperObjClassName + ": p_wrapperObj has been added to parent " + parentNodeClassName + " node: " + parentNode.getFullName() + ", but " + presWrapperObjClassName + ": p_presWrapperObj is an orphan" :
                presWrapperObjClassName + ": p_presWrapperObj has been added to parent " + parentPresNodeClassName + " node: " + parentPresNode.getFullName() + ", but " + wrapperObjClassName + ": p_wrapperObj is an orphan"
            ) + " (either BOTH should be orphans or NEITHER should be orphans)";
            
            throw new IllegalStateException(eMsg);
        }

        wrapperObject = p_wrapperObj;
        presWrapperObject = p_presWrapperObj;
    }
}
