package com.zealcore.srl.offline.test;

import java.nio.ByteOrder;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.srl.offline.Blackbox;
import com.zealcore.srl.offline.LinearMessage;
import com.zealcore.srl.offline.NativeReader;
import com.zealcore.srl.offline.Version;

public class TestNativeReader {
    @Before
    public void setUp() throws Exception {
        System.out.println("starting");
    }

    @Test
    public void testReadMetaData() {
    	//Expected Values are changed according to new test-log-file: srl_test1_normal.srl
    	final NativeReader subject = new NativeReader(
                "testlogs/srl_test1_normal.srl");
        final Blackbox blackbox = new Blackbox();
        subject.transform(blackbox);
        Assert.assertEquals(102400, blackbox.getBufferSize());
        Assert.assertEquals(Version.valueOf(1,0,4), blackbox.getVersion());
        Assert.assertEquals(ByteOrder.BIG_ENDIAN, blackbox.getByteOrder());
        final Collection<LinearMessage> messages = blackbox.getLinearMessages();
        Assert.assertEquals(212, messages.size());
        final LinearMessage message = (LinearMessage) messages.toArray()[0];
        Assert.assertEquals(1010, message.getTypeId());
        Assert.assertEquals(16, message.getData().capacity());
    }
}
