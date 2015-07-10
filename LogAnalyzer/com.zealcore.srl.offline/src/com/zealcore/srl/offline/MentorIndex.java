package com.zealcore.srl.offline;

import java.util.HashMap;
import java.util.Map;

public class MentorIndex {
    private Map<Integer, String> signalMap = new HashMap<Integer, String>();

    private Map<Integer, String> clazzMap = new HashMap<Integer, String>();

    private Map<Integer, String> stateMap = new HashMap<Integer, String>();

    public void putSignal(final int id, final String name) {
        signalMap.put(id, name);
    }

    public String getSignal(final int id) {
        String signal = signalMap.get(Integer.valueOf(id));
        if (signal == null) {
            signal = "signal" + Integer.toString(id);
        }
        return signal;
    }

    public void putClazz(final int id, final String name) {
        clazzMap.put(id, name);
    }

    public String getClazz(final int id) {
        String clazz = clazzMap.get(Integer.valueOf((int)id));
        if (clazz == null) {
            clazz = "class" + Integer.toString(id);
        }
        return clazz;
    }

    public void putState(final int id, final String name) {
        stateMap.put(id, name);
    }

    public String getState(final int id) {
        String state = stateMap.get(Integer.valueOf(id));
        if (state == null) {
            state = "state" + Integer.toString(id);
        }
        return state;
    }
    
    public void clear() {
        signalMap.clear();
        clazzMap.clear();
        stateMap.clear();
    }
}
