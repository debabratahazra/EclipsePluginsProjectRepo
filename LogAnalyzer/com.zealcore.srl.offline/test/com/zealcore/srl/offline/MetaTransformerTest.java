package com.zealcore.srl.offline;

import junit.framework.Assert;

import org.junit.Test;

public class MetaTransformerTest {

    @Test
    public void testTransform() {
        final NativeReader nativeReader = new NativeReader("testlogs/srl_test1_normal.srl");
        final Blackbox blackbox = new Blackbox();
        nativeReader.transform(blackbox);

        final FcpTransformer fcpTransformer = new FcpTransformer();
        fcpTransformer.transform(blackbox);

        final MetaTransformer subject = new MetaTransformer();
        subject.transform(blackbox);

        System.out.println(blackbox);

        //Expected Value changed according to new test-log-file: srl_test1_normal.srl
        Assert.assertEquals(22, blackbox.getStructs().size());

    }
}
