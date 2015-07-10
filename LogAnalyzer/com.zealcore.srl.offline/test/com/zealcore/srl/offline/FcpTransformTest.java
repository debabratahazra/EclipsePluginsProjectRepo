package com.zealcore.srl.offline;

import junit.framework.Assert;

import org.junit.Test;

public class FcpTransformTest {

    @Test
    public void testFcpTransform() {
        final NativeReader nativeReader = new NativeReader(
                "testlogs/srl_test1_normal.srl");
        final Blackbox blackbox = new Blackbox();
        nativeReader.transform(blackbox);

        final int preTransform = blackbox.getLinearMessages().size();

        final FcpTransformer subject = new FcpTransformer();

        subject.transform(blackbox);
		
		//Expected Value changed according to new test-log-file: srl_test1_normal.srl
        Assert.assertEquals(6083,
                blackbox.getCircularMessages().size());

        Assert.assertTrue("Linear FCP Should have been removed",
                preTransform > blackbox.getLinearMessages().size());

        for (final CircularMessage message : blackbox.getCircularMessages()) {
            System.out.println(message);
            System.out.println("Ts " + message.getTs());
            System.out.println("TypeId " + message.getTypeId());
            System.out.println(message.getData().position());
            System.out.println(message.getData().capacity());
        }

    }
}
