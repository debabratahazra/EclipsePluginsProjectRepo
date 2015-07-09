package com.odcgroup.ocs.support.utils;

import java.io.File;
import java.io.FileFilter;

public class JarFileFilter {

    public static boolean accept(String pathname) {
        pathname = pathname.toLowerCase();
        return pathname.endsWith(".jar") || pathname.endsWith(".zip"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static FileFilter getFileFilter() {
        return new FileFilter() {
            public boolean accept(File pathname) {
                return JarFileFilter.accept(pathname.getName());
            }
        };
    }
}
