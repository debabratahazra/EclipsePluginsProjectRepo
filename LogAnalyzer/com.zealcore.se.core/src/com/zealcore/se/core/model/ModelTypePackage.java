/**
 * 
 */
package com.zealcore.se.core.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.zealcore.se.core.dl.AbstractTypePackage;

/**
 * @author mala
 * 
 */
public final class ModelTypePackage extends AbstractTypePackage {

    static final Set<Class<?>> CLASSES = new HashSet<Class<?>>();

    static {
        ModelTypePackage.CLASSES.add(IArtifact.class);
        ModelTypePackage.CLASSES.add(ITask.class);
//         ModelTypePackage.CLASSES.add(IDuration.class);
        ModelTypePackage.CLASSES.add(ILogEvent.class);
        // ModelTypePackage.CLASSES.add(IActivity.class);
        ModelTypePackage.CLASSES.add(ITaskDuration.class);
        ModelTypePackage.CLASSES.add(IObject.class);
        // ModelTypePackage.CLASSES.add(ITimedTransition.class);
        // ModelTypePackage.CLASSES.add(IState.class);
        // ModelTypePackage.CLASSES.add(IStateTransition.class);
        ModelTypePackage.CLASSES.add(IProcessSwitch.class);
        ModelTypePackage.CLASSES.add(AbstractTaskInstance.class);
    }

    @Override
    public String toString() {
        return "Model Type Package";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.dl.ITypePackage#getTypes()
     */
    public Collection<IType> getTypes() {
        return Collections.unmodifiableCollection(ReflectiveType
                .valueOf(ModelTypePackage.CLASSES));
    }

    public void processLogEvent(final ILogEvent abstractLogEvent) {}

}
