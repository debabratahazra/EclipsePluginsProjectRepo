package com.zealcore.se.core;

import org.junit.Assert;
import org.junit.Test;

import com.zealcore.se.core.model.AbstractObject;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProperty;

public class SearchCriteriaMatchTest {

    private static final int TEN = 10;

    private static final String FOO = "Foo";

      
    @Test   
    public void testMatchLt() {

        final SearchCriteria criteria = new SearchCriteria(new DummyProperty(
                SearchCriteriaMatchTest.FOO, 1));
        final IObject dummy = new AbstractObject() {};
        
		criteria.setOperand2(1);
		Assert.assertFalse(criteria.matches(dummy));

		criteria.setOperand2(SearchCriteriaMatchTest.TEN);
        Assert.assertTrue(criteria.matches(dummy));

    }

    @Test
    public void testMatchGrtEq() {

        final SearchCriteria criteria = new SearchCriteria(new DummyProperty(
                SearchCriteriaMatchTest.FOO, 1));

		criteria.setOperator1(">=");
		criteria.setOperand1(1);
        // This object is not used because of the DummyProperties
        final IObject dummy = new AbstractObject() {};
		Assert.assertTrue(criteria.matches(dummy));	
    }

   

    
    private static class DummyProperty implements IProperty {

        private final String name;

        private final Object value;

        DummyProperty(final String name, final Object value) {
            this.name = name;
            this.value = value;

        }

        public String getDescription() {
            return "";
        }

        public String getName() {
            return this.name;
        }

        @SuppressWarnings("unchecked")
        public Class getReturnType() {
            return this.value.getClass();
        }

        public Object getValue(final IObject object) {
            return this.value;
        }

        public boolean isPlotable() {
            return false;
        }

        public boolean isSearchable() {
            return true;
        }

        public boolean isGeneric() {
            return false;
        }
    }
}
