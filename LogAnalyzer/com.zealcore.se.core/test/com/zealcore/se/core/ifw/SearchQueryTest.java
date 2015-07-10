package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.IObject;

public class SearchQueryTest implements IDataSource, IFilter<IObject> {

    private SearchQuery subject;
    private static final int DEFAULT_LIMIT = 1000;
    private int count;
    
    @Before
    public void setUp() throws Exception {
        count = 0;
        subject = new SearchQuery(this, DEFAULT_LIMIT, null);
        subject.initialize(this);

        Assert.assertEquals(Double.MAX_VALUE, subject.getSearchInfo().getTotalStatistics().getMinimum());
        Assert.assertEquals(0.0, subject.getSearchInfo().getTotalStatistics().getMean());
        Assert.assertEquals(-Double.MAX_VALUE, subject.getSearchInfo().getTotalStatistics().getMaximum());
        
        subject.visitBegin(Reason.QUERY_ADDED);
        
        ArrayList<AbstractLogEvent> eventArray = new ArrayList<AbstractLogEvent>(); 
         
        for(int i=0; i<1010; i++){
        	eventArray.add(new AbstractLogEvent() {});
        	eventArray.get(i).setTs(i + (long)(Math.pow(i, 2)));
        	Assert.assertTrue(subject.visit(eventArray.get(i)));
        	Assert.assertTrue(subject.visit(eventArray.get(i)));
        }
        
        subject.visitEnd(true);
     }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testSimpleSearch() {
    	Assert.assertEquals(2.0, subject.getSearchInfo().getTotalStatistics().getMinimum());
    	Assert.assertEquals(1010.0, subject.getSearchInfo().getTotalStatistics().getMean());
    	Assert.assertEquals(2018.0, subject.getSearchInfo().getTotalStatistics().getMaximum());
    	Assert.assertEquals(2.0, subject.getSearchInfo().getCurrentStatistics().getMinimum());
    	Assert.assertEquals(1000.0, subject.getSearchInfo().getCurrentStatistics().getMean());
    	Assert.assertEquals(1998.0, subject.getSearchInfo().getCurrentStatistics().getMaximum());
   
        final List<IObject> results = subject.getResults();
        Assert.assertEquals(1000, results.size());

    }

    
    @Test(expected = IllegalStateException.class)
    public void sameTwice() {
        subject.visitBegin(Reason.QUERY_ADDED);
    }
    /**
     * IDataSource implementation
     */
    public IArtifact getArtifact(final IArtifactID id) {
        throw new UnsupportedOperationException();
    }

    public List<IArtifact> getArtifacts() {
        throw new UnsupportedOperationException();
    }

    public <T extends IArtifact> List<T> getArtifacts(final Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    public void refresh() {
        throw new UnsupportedOperationException();
    }

    /**
     * IFilter implementation
     */
    public boolean filter(final IObject x) {
        if (count++ % 2 == 0) {
            return true;
        }
        return false;
    }

}
