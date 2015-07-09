package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import com.acquire.intelligentforms.IPresentationConfigurable;
import com.acquire.intelligentforms.entities.GenericNode;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.GenericNodeWrapper;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;


/**
 * A <code>VisualItemContainerWrapperPair</code> is an immutable pairing of a {@link GenericNodeWrapper wrapper} object instance representating particular a process-side "container" of
 * visual process-side item with its (possible presentation-only) {@link PresentationObjectWrapper} correspondent.<p>
 * 
 * The main motivations for this class were:<p>
 * 
 * <ul>
 *     <li>
 *         To achieve better run-time type safety (i.e. to detect/report some of the more common IFP-breaking logical errors at the time they occur instead of allowing the code to
 *         run on, resulting in a "broken" IFP) - see javadoc for {@link #VisualItemContainerWrapperPair(GenericNodeWrapper, PresentationObjectWrapper) constructor} and other
 *         methods for furher detail.
 *     </li>
 *     <li>
 *         To facilitate easy addition/insertion of {@link VisualWrapperObjectPair} elements into parallel relative (relative to existing {@link VisualElementWrapperPair} children
 *         within an associated pair of process and presentation-side container wrapper objects (see: {@link #addChild}, {@link #insertNewChildBefore}, {@link #insertNewChildAfter});   
 *     </li> 
 * </ul><p>
 * 
 * NB: Given that both members are <code>final</code> they are exposed directly <i>(which is why this class doesn't define any getter" methods)</i>.<p>
 * 
 * @author Simon Hayes
 */
public class VisualItemContainerWrapperPair
{
    /** <code>wrapperObject</code> represents a process-side visual container node <i>(guaranteed non-null)</i> */
    public final GenericNodeWrapper<?> wrapperObject;
    
    /**
     * <code>presWrapperObject</code> represents a (possibly presentation-only) presentation-side container node <i>(guaranteed non null)</i> that has been paired with {@link #wrapperObject} for
     * the purpose of performing tandem-style addition / insertion child wrapper object pairs.
     */
    public final PresentationObjectWrapper<?> presWrapperObject;
    
    public enum RelativePosition {
        BEFORE(0),
        AFTER(1);
        
        public int toInsertionIndex(int p_indexOfExistingItem)
        {
            AssertionUtils.requireNonNegative(p_indexOfExistingItem, "p_indexOfExistingItem");
            return p_indexOfExistingItem + m_offset;
        }
        
        private final int m_offset;
        
        private RelativePosition(int p_offset)
        {
            m_offset = p_offset;
        }
    };
    
    public VisualItemContainerWrapperPair(ItemGroupWrapperPair p_itemGroupWrapperPair)
    {
        AssertionUtils.requireNonNull(p_itemGroupWrapperPair, "p_itemGroupWrapperPair");
        
        wrapperObject = p_itemGroupWrapperPair.wrapperObject;
        presWrapperObject = p_itemGroupWrapperPair.presWrapperObject;
    }
    
    public VisualItemContainerWrapperPair(PhaseWrapperPair p_phaseWrapperPair)
    {
        
        wrapperObject = p_phaseWrapperPair.wrapperObject;
        presWrapperObject = p_phaseWrapperPair.presWrapperObject;
    }
    
    /**
     * @param   p_containerWrapper          an instance of a concrete {@link GenericNodeWrapper} sub-type representing a process-side visual container node<p>
     *  
     * @param   p_presContainerWrapper      an instance of a concrete {@link PresentationObjectWrapper} sub-type representing a (possibly presentation-only) presentation-side container node
     *                                      that is to be paired with <code>p_containerWrapper</code> by this instance for the purpose of performing tandem-style addition / insertion of
     *                                      child wrapper object pairs
     *                                      
     * @throws  IllegalArgumentException    if either argument is <code>null</code> or the {@link GenericNode} sub-type wrapped by either argument is <u>not</u> one representing a visual
     *                                      container node
     *                                      
     * @throws  IllegalStateException       if <code>p_containerWrapper</code> and <code>p_presContainerWrapper</code> have <u>different</u> parentage states
     *                                      (i.e. its fine for <u>both</u> to be parented or <u>both</u> to be unparented, but anything else is so likely to be a programming error that we
     *                                      throw an exception).
     */
    public VisualItemContainerWrapperPair(GenericNodeWrapper<?> p_containerWrapper, PresentationObjectWrapper<?> p_presContainerWrapper)
    {
        AssertionUtils.requireNonNull(p_containerWrapper, "p_containerWrapper");
        AssertionUtils.requireNonNull(p_presContainerWrapper, "p_presContainerWrapper");
        
        if (! IPresentationConfigurable.class.isAssignableFrom(p_containerWrapper.unwrap().getClass()))
            throw new IllegalArgumentException("p_wrapperObj type: " + p_containerWrapper.getClass().getSimpleName() + " represents a non-visual entity !");
        
        if (p_containerWrapper.unwrap().getValidChildClasses().isEmpty())
            throw new IllegalArgumentException("p_wrapperObj type: " + p_containerWrapper.getClass().getSimpleName() + " is not one that supports children !");
        
        if (p_presContainerWrapper.unwrap().getValidChildClasses().isEmpty())
            throw new IllegalArgumentException("p_presContainerWrapper type: " + p_presContainerWrapper.getClass().getSimpleName() + " is not one that supports children !");

        final GenericNode
            parentNode = p_containerWrapper.unwrap().getParent(),
            parentPresNode = p_presContainerWrapper.unwrap().getParent();
        
        final boolean
            nodeHasParent = (parentNode != null),
            presNodeHasParent = (parentPresNode != null);
        
        if (presNodeHasParent != nodeHasParent)
        {
            final String
            containerWrapperClassName = p_containerWrapper.getClass().getSimpleName(),
            containerParentClassName = nodeHasParent ? parentNode.getClass().getSimpleName() : null,
            presContainerWrapperClassName = p_presContainerWrapper.getClass().getSimpleName(),
            presContainerParentClassName = presNodeHasParent ? parentPresNode.getClass().getSimpleName() : null;
            
            final String eMsg = (nodeHasParent ?
                containerWrapperClassName + ": p_containerWrapper has been added to parent " + containerParentClassName + " node: " + parentNode.getFullName() + ", but " + presContainerWrapperClassName + ": p_presContainerWrapper is an orphan" :
                presContainerWrapperClassName + ": p_presContainerWrapper has been added to parent " + presContainerParentClassName + " node: " + parentPresNode.getFullName() + ", but " + containerWrapperClassName + ": p_containerWrapper is an orphan"
            ) + " (either BOTH should be orphans or NEITHER should be orphans)";
            
            throw new IllegalStateException(eMsg);
        }
        
        wrapperObject = p_containerWrapper;
        presWrapperObject = p_presContainerWrapper;
    }
    
    /**
     * @param   p_newVisualChildWrapperPair     the VisualWrapperObjectPair whose {@link VisualWrapperObjectPair#wrapperObject wrapperObject} and
     *                                          {@link VisualWrapperObjectPair#presWrapperObject presWrapperObject} are to be <u>appended</u> (respectively) to the child-lists of <u>our</u>
     *                                          {@link #wrapperObject} and {@link #presWrapperObject} containers.<p>
     *                                          
     * @throws  IllegalArgumentException        if <code>p_newvisualChildWrapper</code> represents a child-type that is invalid for this container or either / both of the wrapper objects in
     *                                          p_newVisualChildWrapperPair are <u>already</u> parented.
     */
    public void addChild(VisualWrapperObjectPair<?,?> p_newVisualChildWrapperPair)
    {
        assertIsAdoptableChildWrapperObjectPair(p_newVisualChildWrapperPair, "p_newVisualChildWrapperPair");
        
        wrapperObject.addChild(p_newVisualChildWrapperPair.wrapperObject);
        presWrapperObject.addChild(p_newVisualChildWrapperPair.presWrapperObject);
    }
    
    /**
     * Inserts a new {@link VisualWrapperObjectPair} into the child-lists of process / presentation container nodes wrapped by this container such that it appears immediately before another
     * existing child {@link VisualWrapperObjectPair} of this container (on both the process and presentation sides).
     *                                                  
     * @param   p_newVisualChildWrapperPair         the VisualWrapperObjectPair whose {@link VisualWrapperObjectPair#wrapperObject wrapperObject} and
     *                                              {@link VisualWrapperObjectPair#presWrapperObject presWrapperObject} are to be <u>inserted</u> (respectively) to the child-lists of <u>our</u>
     *                                              {@link #wrapperObject} and {@link #presWrapperObject} containers.<p>
     *                                          
     * @param   p_existingVisualChildWrapperPair    a VisualWrapperObjectPair representing the process / presentation-side nodes for the <u>existing</u> child (within the collection represented
     *                                              by this instance) before which the process / presentation-side nodes represented by p_newVisualChildWrapperPair are to be added.<p>                             
     *                                          
     * @throws  IllegalArgumentException            if either argument is null, <code>p_newvisualChildWrapper</code> represents a child-type that is invalid for this container, either / both of
     *                                              the underlying nodes referred to by the wrapper objects in p_newVisualChildWrapperPair are found to be <u>already</u> parented, or either / both
     *                                              of the process-side / presentation  elements within p_existingVisualChildWrapperPair are found <u>not</u> to exist as children of the relevant
     *                                              container node wrapped represented by this instance.
     */
    public void insertNewChildBefore(VisualWrapperObjectPair<?,?> p_newVisualChildWrapperPair, VisualWrapperObjectPair<?,?> p_existingVisualChildWrapperPair)
    {
       assertIsAdoptableChildWrapperObjectPair(p_newVisualChildWrapperPair, "p_newVisualChildWrapperPair");
       AssertionUtils.requireNonNull(p_existingVisualChildWrapperPair, "p_existingVisualChildWrapperPair");
       
       insertNewChildRelativeTo(p_newVisualChildWrapperPair, p_existingVisualChildWrapperPair, RelativePosition.BEFORE);
    }
    
    /**
     * Inserts a new {@link VisualWrapperObjectPair} into the child-lists of process / presentation container nodes wrapped by this container such that it appears immediately after another
     * existing child {@link VisualWrapperObjectPair} of this container (on both the process and presentation sides).
     *                                                  
     * @param   p_newVisualChildWrapperPair         the VisualWrapperObjectPair whose {@link VisualWrapperObjectPair#wrapperObject wrapperObject} and
     *                                              {@link VisualWrapperObjectPair#presWrapperObject presWrapperObject} are to be <u>inserted</u> (respectively) to the child-lists of <u>our</u>
     *                                              {@link #wrapperObject} and {@link #presWrapperObject} containers.<p>
     *                                          
     * @param   p_existingVisualChildWrapperPair    a VisualWrapperObjectPair representing the process / presentation-side nodes for the <u>existing</u> child (within the collection represented
     *                                              by this instance) after which the process / presentation-side nodes represented by p_newVisualChildWrapperPair are to be added.<p>                             
     *                                          
     * @throws  IllegalArgumentException            if either argument is null, <code>p_newvisualChildWrapper</code> represents a child-type that is invalid for this container, either / both of
     *                                              the underlying nodes referred to by the wrapper objects in p_newVisualChildWrapperPair are found to be <u>already</u> parented, or either / both
     *                                              of the process-side / presentation  elements within p_existingVisualChildWrapperPair are found <u>not</u> to exist as children of the relevant
     *                                              container node wrapped represented by this instance.
     */
    public void insertNewChildAfter(VisualWrapperObjectPair<?,?> p_newVisualChildWrapperPair, VisualWrapperObjectPair<?,?> p_existingVisualChildWrapperPair)
    {
        assertIsAdoptableChildWrapperObjectPair(p_newVisualChildWrapperPair, "p_newVisualChildWrapperPair");
        AssertionUtils.requireNonNull(p_existingVisualChildWrapperPair, "p_existingVisualChildWrapperPair");
       
        insertNewChildRelativeTo(p_newVisualChildWrapperPair, p_existingVisualChildWrapperPair, RelativePosition.AFTER);
    }

    /**
     * Inserts a new {@link VisualWrapperObjectPair} into the child-lists of process / presentation container nodes wrapped by this container such that it appears immediately after another
     * existing child {@link VisualWrapperObjectPair} of this container (on both the process and presentation sides).
     *                                                  
     * @param   p_newVisualChildWrapperPair         the VisualWrapperObjectPair whose {@link VisualWrapperObjectPair#wrapperObject wrapperObject} and
     *                                              {@link VisualWrapperObjectPair#presWrapperObject presWrapperObject} are to be <u>inserted</u> (respectively) to the child-lists of <u>our</u>
     *                                              {@link #wrapperObject} and {@link #presWrapperObject} containers.<p>
     *                                          
     * @param   p_existingVisualChildWrapperPair    a VisualWrapperObjectPair representing the process / presentation-side nodes for the <u>existing</u> child (within the collection represented
     *                                              by this instance) after which the process / presentation-side nodes represented by p_newVisualChildWrapperPair are to be added.<p>                             
     *                                          
     * @param   p_relativePosition                  the RelativePosition constant indicating whether p_newVisualChildWrapperPair is to be inserted {@link RelativePosition#BEFORE} or
     *                                              {@link RelativePosition#AFTER} p_existingVisualChildWrapperPair<p>
     *                                              
     * @throws  IllegalArgumentException            if any of the arguments are null, <code>p_newvisualChildWrapper</code> represents a child-type that is invalid for this container, either / both of
     *                                              the underlying nodes referred to by the wrapper objects in p_newVisualChildWrapperPair are found to be <u>already</u> parented, or either / both
     *                                              of the process-side / presentation  elements within p_existingVisualChildWrapperPair are found <u>not</u> to exist as children of the relevant
     *                                              container node wrapped represented by this instance.
     */
    public void insertNewChildRelativeTo(VisualWrapperObjectPair<?,?> p_newVisualChildWrapperPair, VisualWrapperObjectPair<?,?> p_existingVisualChildWrapperPair, RelativePosition p_relativePosition)
    {
        assertIsAdoptableChildWrapperObjectPair(p_newVisualChildWrapperPair, "p_newVisualChildWrapperPair");
        AssertionUtils.requireNonNull(p_existingVisualChildWrapperPair, "p_existingVisualChildWrapperPair");
        AssertionUtils.requireNonNull(p_relativePosition, "p_relativePosition");
        
        final int indexOfPreExistingChild = indexOfChild(p_existingVisualChildWrapperPair.wrapperObject);
        AssertionUtils.requireConditionTrue(indexOfPreExistingChild >= 0, "indexOfChild(p_existingVisualChildWrapperPair.wrapperObject) >= 0");
        
        final int indexOfPreExistingPresChild = indexOfChild(p_existingVisualChildWrapperPair.presWrapperObject);
        AssertionUtils.requireConditionTrue(indexOfPreExistingPresChild >= 0, "indexOfChild(p_existingVisualChildWrapperPair.presWrapperObject) >= 0");
        
        wrapperObject.unwrap().addChild(p_newVisualChildWrapperPair.wrapperObject.unwrap(), p_relativePosition.toInsertionIndex(indexOfPreExistingChild));
        presWrapperObject.unwrap().addChild(p_newVisualChildWrapperPair.presWrapperObject.unwrap(), p_relativePosition.toInsertionIndex(indexOfPreExistingPresChild));
    }

    private int indexOfChild(PresentationObjectWrapper<?> p_presChildWrapperObj)
    {
        AssertionUtils.requireNonNull(p_presChildWrapperObj, "p_presChildWrapperObj");
        return presWrapperObject.unwrap().getChildrenList().indexOf(p_presChildWrapperObj.unwrap());
    }

    private int indexOfChild(GenericNodeWrapper<?> p_childWrapperObj)
    {
        return wrapperObject.unwrap().getChildrenList().indexOf(p_childWrapperObj.unwrap());
    }
    
    private void assertIsAdoptableChildWrapperObjectPair(VisualWrapperObjectPair<?,?> p_visibleChildWrapperPair, String p_argName)
    {
        AssertionUtils.requireNonNull(p_visibleChildWrapperPair, p_argName);
        
        final boolean isLink = false;
        
        if (! (
                wrapperObject.unwrap().isValidChild(p_visibleChildWrapperPair.wrapperObject.unwrap(), isLink) &&
                presWrapperObject.unwrap().isValidChild(p_visibleChildWrapperPair.presWrapperObject.unwrap(), isLink)
            )
        ) {
            throw new IllegalArgumentException(p_argName + " is of type: " + p_visibleChildWrapperPair.getClass().getSimpleName() + " which is not a legal child-type for this container");
        }
        
        final GenericNode
            newChildNode = p_visibleChildWrapperPair.wrapperObject.unwrap(),
            newChildParentNode = newChildNode.getParent(),
            
            newChildPresNode = p_visibleChildWrapperPair.presWrapperObject.unwrap(),
            newChildParentPresNode = newChildPresNode.getParent();
        
        if (newChildParentNode != null)
        {
            String eMsg = p_argName + p_visibleChildWrapperPair.getClass().getSimpleName() + ": " + p_argName + " is ALREADY a child of";

            eMsg += (wrapperObject.unwrap() == newChildParentNode) ? " this container: " : ": ";
            
            eMsg += (
                newChildParentNode.getClass().getSimpleName() + ": " + newChildParentNode.getFullName() + " / " + 
                newChildParentPresNode.getClass().getSimpleName() + ": " + newChildPresNode.getFullKey()
            );
            
            throw new IllegalStateException(eMsg);
        }
    }
}
