package com.odcgroup.aaa.connector.mdfmml;

import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaAttributeNameFromTADictAttributeSQLName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaAttributeNameFromTAFormatElementSQLName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaClassNameFromTADictEntitySQLName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaClassNameFromTAFormatCode;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaNameFromTAEnumName;
import static com.odcgroup.aaa.core.util.NamingHelper.turnCamelCaseIntoSpaces;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test for MetaDict2MML.
 * 
 * @author Michael Vorburger
 */
public class NamingHelperTest {

    @Test
	public void testGetMMLJavaClassNameFromTADictEntitySQLName() {
        Assert.assertEquals("Archive", getMMLJavaClassNameFromTADictEntitySQLName("archive"));
        Assert.assertEquals("AccPeriodParam", getMMLJavaClassNameFromTADictEntitySQLName("acc_period_param"));
        Assert.assertEquals("ListAAA", getMMLJavaClassNameFromTADictEntitySQLName("list"));
        Assert.assertEquals("AccPeriod", getMMLJavaClassNameFromTADictEntitySQLName("acc_period"));
        Assert.assertEquals("", getMMLJavaClassNameFromTADictEntitySQLName(""));
        Assert.assertEquals(null, getMMLJavaClassNameFromTADictEntitySQLName(null));
    }

    @Test
	public void testTurnCamelCaseIntoSpaces() {
        Assert.assertEquals("Mean Invest Cap", turnCamelCaseIntoSpaces("MeanInvestCap"));
        Assert.assertEquals("UPPERCASE", turnCamelCaseIntoSpaces("UPPERCASE"));
        Assert.assertEquals("Upper CASE", turnCamelCaseIntoSpaces("UpperCASE"));
        Assert.assertEquals("SQL Name", turnCamelCaseIntoSpaces("SQLName"));
        Assert.assertEquals("Mean Invest Ca P", turnCamelCaseIntoSpaces("MeanInvestCaP"));
        Assert.assertEquals("Mean Invest Ca PP", turnCamelCaseIntoSpaces("MeanInvestCaPP"));
        Assert.assertEquals("", turnCamelCaseIntoSpaces(""));
        Assert.assertEquals(null, turnCamelCaseIntoSpaces(null));
    }

    @Test
	public void testGetMMLJavaAttributeNameFromTADictAttributeSQLName() {
        // Typical names from format elements
        Assert.assertEquals("sign", getMMLJavaAttributeNameFromTAFormatElementSQLName("SIGN"));
        Assert.assertEquals("breakCriteria", getMMLJavaAttributeNameFromTAFormatElementSQLName("BREAK_CRITERIA"));
        Assert.assertEquals("impvolBlackSholes", getMMLJavaAttributeNameFromTAFormatElementSQLName("IMPVOL_BLACK_SHOLES"));
        Assert.assertEquals("formatElement1SqlName", getMMLJavaAttributeNameFromTAFormatElementSQLName("Format_ELEMENT_1_SQL_name"));
        Assert.assertEquals("meanInvestCap", getMMLJavaAttributeNameFromTAFormatElementSQLName("MeanInvestCap"));
        Assert.assertEquals("nbAccounts", getMMLJavaAttributeNameFromTAFormatElementSQLName("Nb_Accounts"));

        Assert.assertEquals("mktSeg", getMMLJavaAttributeNameFromTAFormatElementSQLName("mkt_seg"));
        Assert.assertEquals("mktSegId", getMMLJavaAttributeNameFromTAFormatElementSQLName("mkt_seg_id"));

        Assert.assertEquals("", getMMLJavaAttributeNameFromTAFormatElementSQLName(""));
        Assert.assertEquals(null, getMMLJavaAttributeNameFromTAFormatElementSQLName(null));
        
        // Typical names from metadict
        Assert.assertEquals("paramName", getMMLJavaAttributeNameFromTADictAttributeSQLName("param_name"));
        Assert.assertEquals("paidCompFreqUnitE", getMMLJavaAttributeNameFromTADictAttributeSQLName("paid_comp_freq_unit_e"));
        Assert.assertEquals("abcissaClassif", getMMLJavaAttributeNameFromTADictAttributeSQLName("abcissa_classif_id"));
        Assert.assertEquals("abcissaRankN", getMMLJavaAttributeNameFromTADictAttributeSQLName("abcissa_rank_n"));
        Assert.assertEquals("defaultC", getMMLJavaAttributeNameFromTADictAttributeSQLName("default_c"));
        Assert.assertEquals("finalD", getMMLJavaAttributeNameFromTADictAttributeSQLName("final_d"));
        Assert.assertEquals("language", getMMLJavaAttributeNameFromTADictAttributeSQLName("language_dict_id"));
        
        
        Assert.assertEquals("", getMMLJavaAttributeNameFromTADictAttributeSQLName(""));
        Assert.assertEquals(null, getMMLJavaAttributeNameFromTADictAttributeSQLName(null));
    }

    @Test
	public void testGetMMLJavaNameFromTAEnumName() {
        Assert.assertEquals("ThirdPartyKnowExpCommodDeriv", getMMLJavaNameFromTAEnumName("ThirdPartyKnow_ExpCommod_Deriv_"));
        Assert.assertEquals("", getMMLJavaNameFromTAEnumName(""));
        Assert.assertEquals(null, getMMLJavaNameFromTAEnumName(null));
        
        Assert.assertEquals("SynchronizedJ", getMMLJavaNameFromTAEnumName("synchronized"));
        Assert.assertEquals("BooleanJ", getMMLJavaNameFromTAEnumName("boolean"));
        Assert.assertEquals("PrivateJ", getMMLJavaNameFromTAEnumName("private"));
    }

    @Test
	public void testGetMMLJavaClassNameFromTAFormatCode() {
        Assert.assertEquals("JlaThirdPartyTest", getMMLJavaClassNameFromTAFormatCode("jlaThirdPartyTest"));
        Assert.assertEquals("", getMMLJavaClassNameFromTAFormatCode(""));
        Assert.assertEquals(null, getMMLJavaClassNameFromTAFormatCode(null));
    }

    
}
