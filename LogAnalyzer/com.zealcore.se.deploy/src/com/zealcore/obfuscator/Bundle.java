/**
 * 
 */
package com.zealcore.obfuscator;

import java.util.ArrayList;
import java.util.List;

import com.zealcore.obfuscator.PluginReader.BundleLibrary;
import com.zealcore.obfuscator.PluginReader.ExportedPackage;
import com.zealcore.obfuscator.PluginReader.RequiredBundle;

public class Bundle {
    private List<RequiredBundle> requiredBundles = new ArrayList<RequiredBundle>();

    private List<BundleLibrary> libraries = new ArrayList<BundleLibrary>();

    private List<ExportedPackage> exportedPackages = new ArrayList<ExportedPackage>();

    public List<RequiredBundle> getRequiredBundles() {
        return requiredBundles;
    }

    public List<BundleLibrary> getLibraries() {
        return libraries;
    }

    public List<ExportedPackage> getExportedPackages() {
        return exportedPackages;
    }
    
    @Override
    public String toString() {
        String tos = "Bundle[   req=";
        tos += requiredBundles + ",\n           lib=";
        tos += libraries + ",\n         exp=";
        tos += exportedPackages + "";
        
        
        return tos;
    }
}