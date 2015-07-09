package com.odcgroup.aaa.core;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.model.translation.ModelLocaleHelper;

public class TALocaleHelperTest  {

	@Test
	public void testConvertToJavaLocale() {
		Assert.assertEquals("iw", ModelLocaleHelper.getInstance().convertToJavaLocale("he"));
		Assert.assertEquals("vls", ModelLocaleHelper.getInstance().convertToJavaLocale("nb"));
		Assert.assertEquals("zh_TW", ModelLocaleHelper.getInstance().convertToJavaLocale("tw"));
		Assert.assertEquals("zh_CN", ModelLocaleHelper.getInstance().convertToJavaLocale("zh"));
		Assert.assertEquals("other", ModelLocaleHelper.getInstance().convertToJavaLocale("other"));
	}
	
	@Test
	public void testConvertToTALocale() {
		Assert.assertEquals("he", ModelLocaleHelper.getInstance().convertToTALocale("iw"));
		Assert.assertEquals("nb", ModelLocaleHelper.getInstance().convertToTALocale("vls"));
		Assert.assertEquals("tw", ModelLocaleHelper.getInstance().convertToTALocale("zh_TW"));
		Assert.assertEquals("zh", ModelLocaleHelper.getInstance().convertToTALocale("zh_CN"));
		Assert.assertEquals("other", ModelLocaleHelper.getInstance().convertToTALocale("other"));
	}
	
}
