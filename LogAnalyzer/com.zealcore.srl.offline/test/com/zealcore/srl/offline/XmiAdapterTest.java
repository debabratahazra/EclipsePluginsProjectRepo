package com.zealcore.srl.offline;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

public class XmiAdapterTest {
    private HashMap<String, UmlStateMachine> stateMachineMap;

    @Test
    public void testParse() throws Exception {
        stateMachineMap = new HashMap<String, UmlStateMachine>();
        XmiAdapter adapter = new XmiAdapter(stateMachineMap);
        adapter.parse(new File("testlogs/homealarm.xmi"));

        System.out.println("Done");
    }

    @Test
    public void testStartElement() {}
}
