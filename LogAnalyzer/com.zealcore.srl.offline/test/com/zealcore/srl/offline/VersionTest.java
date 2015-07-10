package com.zealcore.srl.offline;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;


public class VersionTest {

    @Test
    public void testCompareTo() {
        Version subject = Version.valueOf(1,2,3);
        Version eq = Version.valueOf(1,2,3);
        Version gt1 = Version.valueOf(2,2,3);
        Version gt2 = Version.valueOf(1,3,3);
        Version gt3 = Version.valueOf(1,2,4);
        Version lt1 = Version.valueOf(0,2,3);
        Version lt2 = Version.valueOf(1,1,3);
        Version lt3 = Version.valueOf(1,2,2);
        
        ArrayList<Version> sortedVersions = new ArrayList<Version>();
        sortedVersions.add(subject);
        sortedVersions.add(eq);
        sortedVersions.add(gt1);
        sortedVersions.add(gt2);
        sortedVersions.add(gt3);
        sortedVersions.add(lt1);
        sortedVersions.add(lt2);
        sortedVersions.add(lt3);
        
        Collections.sort(sortedVersions);
        
        Assert.assertTrue(sortedVersions.get(0).equals(lt1));
        Assert.assertTrue(sortedVersions.get(1).equals(lt2));
        Assert.assertTrue(sortedVersions.get(2).equals(lt3));
        Assert.assertTrue(sortedVersions.get(3).equals(subject));
        Assert.assertTrue(sortedVersions.get(4).equals(eq));
        Assert.assertTrue(sortedVersions.get(5).equals(gt3));
        Assert.assertTrue(sortedVersions.get(6).equals(gt2));
        Assert.assertTrue(sortedVersions.get(7).equals(gt1));
    }
}
