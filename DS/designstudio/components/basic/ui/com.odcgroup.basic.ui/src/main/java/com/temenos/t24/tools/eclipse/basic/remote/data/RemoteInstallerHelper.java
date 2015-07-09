package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;

public final class RemoteInstallerHelper {

    public static JSubroutineParameters getParameters(String[] args) {
        JSubroutineParameters params = new JSubroutineParameters();
        for (String arg : args) {
            JDynArray jdynArray = new JDynArray();
            jdynArray.insert(arg, 1);
            params.add(jdynArray);
        }
        return params;
    }

    public static String[] getStringParams(JSubroutineParameters params) {
        List<String> paramList = new ArrayList<String>();
        for (JDynArray jdynArray : params) {
            paramList.add(jdynArray.get(1));
        }
        String[] returnParam = new String[paramList.size()];
        paramList.toArray(returnParam);
        return returnParam;
    }

    public static String buildString(List<String> items) {
        String str = "";
        int itemsSize = items.size();
        for (int i = 0; i < itemsSize; i++) {
            str +=items.get(i);
            if(!(i==itemsSize-1)){
                str +=" ";
            }
        }
        return str;
    }


    public static List<String> buildList(String value) {
        if (value == null) {
            return null;
        }
        String[] values = value.split(",");
        List<String> items = Arrays.asList(values);
        return items; 
    }
}
