package com.zealcore.srl.offline;

import org.junit.Test;

public class SrlTransformTest {
	@Test
	public void testMain() {
		SrlTransform.main(new String[] { "-osrl_test1_normal.srl.bin",
				"testlogs/srl_test1_normal.srl", });
	}
}
