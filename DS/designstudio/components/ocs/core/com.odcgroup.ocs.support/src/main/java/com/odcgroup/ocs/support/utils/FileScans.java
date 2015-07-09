package com.odcgroup.ocs.support.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.odcgroup.ocs.support.OCSPluginActivator;

public class FileScans {

    @SuppressWarnings({"unchecked", "rawtypes"})
	public static List addLibraries(List libs, File folder) {
        if (folder.exists() && folder.isDirectory()) {
            File[] ajar = folder.listFiles(JarFileFilter.getFileFilter());
            libs.addAll(Arrays.asList(ajar));
        }

        return libs;
    }

    static public void createJarArchive(File archiveFile, File srcdir) {
        ZipOutputStream out = null;

        try {
            // Open archive file
            out = new ZipOutputStream(new FileOutputStream(archiveFile));
            scanforfile(out, srcdir, "", srcdir.list()); //$NON-NLS-1$
        } catch (IOException e) {
            OCSPluginActivator.getDefault().logError(e.getMessage(), e);
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    OCSPluginActivator.getDefault().logError(e.getMessage(), e);
                }
        }
    }

    @SuppressWarnings("rawtypes")
	static public List createFileList(File srcdir) {
        return scanforfiles(new ArrayList(), srcdir, "", srcdir.list()); //$NON-NLS-1$
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	static private List scanforfiles(List lst, File srcdir, String path,
            String[] filelist) {
        for (int i = 0; i < filelist.length; i++) {
            File tobeAdded = new File(srcdir, path + filelist[i]);

            if (tobeAdded == null || !tobeAdded.exists()) {
                continue;
            } else if (tobeAdded.isDirectory()) {
                scanforfiles(lst, srcdir, path + tobeAdded.getName() + "/", //$NON-NLS-1$
                        tobeAdded.list());
                continue;
            }

            lst.add(path + tobeAdded.getName());
        }

        return lst;
    }

    static private void scanforfile(ZipOutputStream out, File srcdir,
            String path, String[] filelist) throws IOException {
        final int BUFFER_SIZE = 10240;
        byte buffer[] = new byte[BUFFER_SIZE];
        File tobeJared;

        for (int i = 0; i < filelist.length; i++) {
            tobeJared = new File(srcdir, path + filelist[i]);

            if (tobeJared == null || !tobeJared.exists()) {
                continue;
            } else if (tobeJared.isDirectory()) {
                scanforfile(out, srcdir, path + tobeJared.getName() + "/", //$NON-NLS-1$
                        tobeJared.list());
                continue;
            }

            // Add archive entry
            ZipEntry jarAdd = new ZipEntry(path + tobeJared.getName());
            jarAdd.setTime(tobeJared.lastModified());
            out.putNextEntry(jarAdd);

            // Write file to archive
            FileInputStream in = new FileInputStream(tobeJared);
            try {
                while (true) {
                    int nRead = in.read(buffer, 0, buffer.length);
                    if (nRead <= 0)
                        break;
                    out.write(buffer, 0, nRead);
                }
            } finally {
                in.close();
            }
        }

    }

}
