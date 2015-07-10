/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.LinkedHashSet;
import java.util.Set;

import com.zealcore.se.core.ifw.Logset;

/**
 * The Class ViewSetChain implements a basic {@link IViewSetChain}. This
 * implementation does not provide any persistence facilities.
 */
class ViewSetChain implements IViewSetChain {

    private final Set<IViewSet> chainedSets = new LinkedHashSet<IViewSet>();

    private boolean ignoreSynch;

    /**
     * {@inheritDoc}
     */
    public void addViewSet(final IViewSet viewset) {
        viewset.addSynchable(this);
        chainedSets.add(viewset);
    }

    /**
     * {@inheritDoc}
     */
    public void removeViewSet(final IViewSet viewset) {
        viewset.removeSynchable(this);
        chainedSets.remove(viewset);
    }

    /**
     * Synchronizes all the ViewSets within the current chain.
     * 
     * @param source
     *                The TimeEvent received from some view set
     */
    public void synch(final TimeEvent source) {
        // Prevent recursive synchronization
        if (ignoreSynch) {
            return;
        }
        ignoreSynch = true;

        long delta = source.getSource().getCurrentTime() - source.getSource().getPrimarySynchronizedEventTime();
        
        Logset currentLogset = Logset.valueOf(source.getSource().getParent().getId());
        
        int offsetCount = currentLogset.getIndexAtTimestamp(source.getSource()
                .getCurrentTime())
                - currentLogset.getIndexAtTimestamp(source.getSource().getPrimarySynchronizedEventTime());
        
        if(offsetCount < 0) {
            offsetCount = -offsetCount;
        }
        
        String sourceLogsetName = source.getSource().getParent().getFolder().getName();
        long clusterTime = -1;

        for (final IViewSet cluster : chainedSets) {
            if (cluster == source.getSource()) {
                continue;
            }
            
//            final long clusterDelta = cluster.getCurrentTime()
//                    + (offsetCount * cluster.getSynchronizationOffset(sourceLogsetName)) + delta;
            
            long deltaOffset = 0;
            
            //clusterTime = source.getPrevious() + cluster.getSynchronizationOffset(sourceLogsetName) + delta;
            clusterTime = cluster.getPrimarySynchronizedEventTime()
                    + (offsetCount * cluster
                            .getSynchronizationOffset(sourceLogsetName))
                    + delta;
            
            cluster.setCurrentTime(clusterTime);
        }
        // enable synchronization again
        ignoreSynch = false;
    }
    
}
