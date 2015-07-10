package com.zealcore.se.ui.search;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.MockUtil;

import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.TestProperty;
import com.zealcore.se.core.model.LogFile;

public class SearchHelperTest {

    private static final float A_FLOAT = 10.23f;

    private static final long THOUSAND = 1000L;

    private static final String DUMMY_NAME = "dummy";

    private static final String NAME = "name";

    private static final String STRING_MATCH_OPERATOR = " LIKE ";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testSetLogFile() {

        // Should check that correct methods are called

        final SearchHelper subject = new SearchHelper();
        final SearchCriteria criteria = EasyMock
                .createMock(SearchCriteria.class);

        org.easymock.EasyMock.expect(criteria.getAttributeClassType())
                .andReturn(LogFile.class);

        org.easymock.EasyMock.expectLastCall().anyTimes();

		criteria.setOperator1(SearchHelperTest.STRING_MATCH_OPERATOR);
		criteria.setOperand1(SearchHelperTest.NAME);
		criteria.setOperand2("");
		criteria.setWildcard(false);

        MockUtil.replay(criteria);
		SearchString searchString=new SearchString();
		searchString.setText1(SearchHelperTest.NAME);

		subject.set(criteria, searchString);

        MockUtil.verify(criteria);

    }

    @Test
    public void testSetLong() {

        // Should check that correct methods are called

        final SearchHelper subject = new SearchHelper();
        final SearchCriteria criteria = new SearchCriteria(new TestProperty("",
                0L));

       		criteria.setOperand1(0);
			SearchString searchString=new SearchString();
			searchString.setText1("1000");
			subject.set(criteria, searchString);
            Assert.assertEquals(SearchHelperTest.THOUSAND, criteria
					.getOperand1());
			searchString.setText1("-1000");
			subject.set(criteria, searchString);
            Assert.assertEquals(-SearchHelperTest.THOUSAND, criteria
					.getOperand1());

			criteria.setOperand2(0);
			searchString.setText2("1000");
			subject.set(criteria, searchString);
			Assert.assertEquals(SearchHelperTest.THOUSAND, criteria
					.getOperand2());
			searchString.setText2("-1000");
			subject.set(criteria, searchString);
			Assert.assertEquals(-SearchHelperTest.THOUSAND, criteria
					.getOperand2());
    }

    @Test
    public void testSetFloat() {

        // Should check that correct methods are called

        final SearchHelper subject = new SearchHelper();
        final SearchCriteria criteria = new SearchCriteria(new TestProperty("",
                10.23f));

    		criteria.setOperand1(0);
			SearchString searchString = new SearchString();
			searchString.setText1("10.23");
			subject.set(criteria, searchString);
            Assert
                    .assertEquals(SearchHelperTest.A_FLOAT, criteria
                            .getOperand1());
			searchString.setText1("-10.23");
			subject.set(criteria, searchString);
            Assert.assertEquals(-SearchHelperTest.A_FLOAT, criteria
					.getOperand1());
			criteria.setOperand2(0);
			searchString.setText2("10.23");
			subject.set(criteria, searchString);
			Assert
			.assertEquals(SearchHelperTest.A_FLOAT, criteria
					.getOperand2());
			searchString.setText2("-10.23");
			subject.set(criteria, searchString);
			Assert.assertEquals(-SearchHelperTest.A_FLOAT, criteria
					.getOperand2());

     }

    @Test
    public void testSetArtifact() {
        final SearchHelper subject = new SearchHelper();
        final SearchCriteria criteria = EasyMock
                .createMock(SearchCriteria.class);

		criteria.setOperator1(SearchHelperTest.STRING_MATCH_OPERATOR);
		criteria.setOperand1(SearchHelperTest.DUMMY_NAME);
		criteria.setOperand2("");
		criteria.setWildcard(false);
        MockUtil.replay(criteria);

		SearchString searchString=new SearchString();
		searchString.setText1(SearchHelperTest.DUMMY_NAME);

		subject.setArtifact(criteria, searchString);
        MockUtil.verify(criteria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUnknownType() {

        // Should check that correct methods are called

        final SearchHelper subject = new SearchHelper();
        final SearchCriteria criteria = EasyMock
                .createMock(SearchCriteria.class);

        org.easymock.EasyMock.expect(criteria.getAttributeClassType())
                .andReturn(UnsupportedOperationException.class);

        org.easymock.EasyMock.expectLastCall().anyTimes();

        MockUtil.replay(criteria);
		SearchString searchString= new SearchString();
		searchString.setText1(SearchHelperTest.DUMMY_NAME);
		subject.set(criteria, searchString);

        Assert.fail("Should throw exception because this "
                + "type is not supported for help");
    }
}
