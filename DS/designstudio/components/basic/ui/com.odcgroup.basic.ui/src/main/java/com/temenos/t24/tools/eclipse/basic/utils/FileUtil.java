package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.google.common.io.Closeables;

public class FileUtil {

    /**
     * Extracts the ASCII contents of the passed IFile, and stick them into a
     * String
     * 
     * @param file - an IFile
     * @return String with its contents.
     */
    public String getFileContents(IFile file) {
        StringBuffer sb = new StringBuffer();
        Reader r = null;
        try {
            r = new InputStreamReader(file.getContents(), "ASCII");
            int c;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CoreException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(r);
        }
        return sb.toString();
    }

    /**
     * Checks whether the passed file already exists
     * 
     * @param filename
     * @return boolean
     */
    public boolean existFile(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    /******************************************************************************
     * @param contents - String with the file contents.
     * @param filename - e.g. /myfile.txt or full_path\myfile.txt
     *            (c:\\path\\filename)
     * @param overwrite - true=> if the file already exists, the file will be
     *            overwriten.
     * @return result: if -1 => failed. Otherwise (>0) it succeeded.
     */
    public int saveToFile(String contents, String filename, boolean overwrite) {
        int result = 0;
        BufferedWriter out = null;
        File file = null;
        try {
            file = new File(filename);
            if (overwrite) {
                file.delete();
            }
            if (file.createNewFile()) {
                out = new BufferedWriter(new FileWriter(file));
                if (out != null) {
                    out.write(contents);
                    out.flush();
                }
            } else {
                result = -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    /******************************************************************************
     * Utility class.
     * 
     * @param filename - e.g. /myfile.txt or full_path\myfile.txt
     *            (c:\\path\\filename)
     * @return String - contents
     */
    public String getFromFile(String filename) {
        BufferedReader br = null;
        File file = new File(filename);
        try {
            br = new BufferedReader(new FileReader(file));
            return getFromFile(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFromFile(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return getFromFile(br);
    }

    private String getFromFile(BufferedReader br) {
        StringBuffer sb = new StringBuffer();
        String contents = "";
        try {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
            // remove the last "new line"
            contents = sb.toString();
            if (contents.length() > 0)
                contents = contents.substring(0, contents.length() - 2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        if (contents == null)
            return "";
        else
            return contents;
    }
}
