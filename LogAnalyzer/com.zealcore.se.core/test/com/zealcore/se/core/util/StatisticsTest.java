package com.zealcore.se.core.util;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {
	private Statistics subject;
	private Statistics subjectZero;
	private Statistics subjectUpperBoundry;
	private Statistics subjectLowerBoundry;
	
	@Before
    public void setUp() throws Exception {
		subject = new Statistics();
		subjectZero = new Statistics();
		subjectUpperBoundry = new Statistics(); 
		subjectLowerBoundry = new Statistics();
		
		Assert.assertEquals(Double.MAX_VALUE, subject.getMinimum());
		Assert.assertEquals(0.0, subject.getMean());
		Assert.assertEquals(-Double.MAX_VALUE, subject.getMaximum());
		
		subject.update(30000);
		subject.update(40000);
		subject.update(50000);
		subject.update(70000);
		
		subjectZero.update(0.0);
		subjectZero.update(0.0);
		subjectZero.update(0.0);
		subjectZero.update(0.0);
		
		subjectUpperBoundry.update(Double.MAX_VALUE);
		subjectUpperBoundry.update(Double.MAX_VALUE);
		subjectUpperBoundry.update(Double.MAX_VALUE);
		subjectUpperBoundry.update(Double.MAX_VALUE);
		subjectUpperBoundry.update(Double.MAX_VALUE);
		
		subjectLowerBoundry.update(-Double.MAX_VALUE);
		subjectLowerBoundry.update(-Double.MAX_VALUE);
		subjectLowerBoundry.update(-Double.MAX_VALUE);
		subjectLowerBoundry.update(-Double.MAX_VALUE);
		subjectLowerBoundry.update(-Double.MAX_VALUE);
		
	}
	
	@Test
	public void testGetMinimum() {
		Assert.assertEquals(30000.0, subject.getMinimum());
		Assert.assertEquals(0.0, subjectZero.getMinimum());
	}

	@Test
	public void testGetMaximum() {
		Assert.assertEquals(70000.0, subject.getMaximum());
		Assert.assertEquals(0.0, subjectZero.getMaximum());
	}

	@Test
	public void testGetMean() {
		Assert.assertEquals(47500.0, subject.getMean());
		Assert.assertEquals(0.0, subjectZero.getMean());
	}

	
	@Test
	public void testGetUpperMinimumBoundry() {
		Assert.assertEquals(Double.MAX_VALUE, subjectUpperBoundry.getMinimum());
	}
	
	@Test
	public void testGetUpperMaximumBoundry() {
		Assert.assertEquals(Double.MAX_VALUE, subjectUpperBoundry.getMaximum());
	}

	@Test (expected = AssertionFailedError.class)
	public void testGetUpperMeanBoundry() {
		Assert.assertEquals(Double.MAX_VALUE, subjectUpperBoundry.getMean());
	}
	
	@Test
	public void testGetLowerMinimumBoundry() {
		Assert.assertEquals(-Double.MAX_VALUE, subjectLowerBoundry.getMinimum());
	}
	
	@Test
	public void testGetLowerMaximumBoundry() {
		Assert.assertEquals(-Double.MAX_VALUE, subjectLowerBoundry.getMaximum());
	}

	@Test (expected = AssertionFailedError.class)
	public void testGetLowerMeanBoundry() {
		Assert.assertEquals(-Double.MAX_VALUE, subjectLowerBoundry.getMean());
	}
}
