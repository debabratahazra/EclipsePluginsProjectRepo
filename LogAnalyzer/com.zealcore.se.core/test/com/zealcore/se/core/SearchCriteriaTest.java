package com.zealcore.se.core;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.ISequenceMember;

public class SearchCriteriaTest {

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {

        MockUtil.reset();
    }

    @Test
    public final void testSearchCriteriaMethod() {

        final SearchableClass clazz = new SearchableClass();

        final Collection<IProperty> properties = clazz.getType()
                .getProperties();
        Assert.assertTrue("There must be some properties in this test",
                properties.size() > 0);
        for (final IProperty prop : properties) {
            SearchCriteria subject = new SearchCriteria(prop);
            assertEquals(prop.getDescription(),subject.getDescription());
            assertEquals(prop.getName(),subject.getName());
            assertNotNull(subject.toString());
        }
    }

    private static class SearchableClass extends AbstractLogEvent {

        private int aProperty;

        private ISequenceMember aMember;

        @ZCProperty(name = "AMemb", searchable = true)
        public ISequenceMember getAMember() {
            return this.aMember;
        }

        @ZCProperty(searchable = true)
        public int getAProperty() {
            return this.aProperty;
        }
    }

    @Test
    public void testMatches() {

        final ILogEvent data = new SearchableClass();
        final SearchCriteria subject = new SearchCriteria(new TestProperty(
                "ts", data.getTs()));

		subject.setOperand1(data.getTs());		
            Assert.assertTrue(subject.matches(data));
		
    }
}
