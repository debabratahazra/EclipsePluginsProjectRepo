/*
 * 
 */
package com.zealcore.obfuscator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ResourcePackageAdjuster {
    private static final String DOT = ".";

    private static final String MAP = "map";

    private static final String COMMA = ",";

    private static final int FOUR_KILOBYTE = 4096;

    private static String componentPath;

    private static final String MANIFEST_ENTRY = "META-INF/MANIFEST.MF";

    public static void main(final String[] args) {
        if (args.length > 0) {
            ResourcePackageAdjuster.componentPath = args[0];
        }

        File f = new File("yguardlog.xml");
        if (!f.canRead()) {
            if (args.length > 1) {
                f = new File(args[1] + "/yguardlog.xml");
            } else {
                System.out
                        .println("ERROR, need path to components as param to ResourcePackageAdjuster!!!!!!!!!!!");
                return;
            }
        }

        System.out.println("ResourcePackageAdjuster is starting");
        final File components = new File(ResourcePackageAdjuster.componentPath);
        if (!components.canRead()) {
            System.out.println("ERROR, failed to open components on path: "
                    + ResourcePackageAdjuster.componentPath);
        } else {
            for (final File file : components.listFiles()) {
                ResourcePackageAdjuster.rewrite(file.getAbsolutePath(), f);
            }
            System.out.println("ResourcePackageAdjuster is done");
        }
    }

    private static void rewrite(final String path, final File yguardLogfile) {

        File jarfile = new File(path);
        if (jarfile.isDirectory()) {
            // Directory based plugin, assume third party tools
            ResourcePackageAdjuster.rewriteThirdParty(yguardLogfile, jarfile);
            return;
        }
        System.out.println("Path: " + path);
        final ResourcePackageAdjuster adjuster = new ResourcePackageAdjuster();
        jarfile = new File(path);

        try {
            adjuster.rewriteManifest(yguardLogfile, jarfile);
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final AssertionError e) {
            throw new RuntimeException(e);
        }
    }

    private static void rewriteThirdParty(final File yguardLog, final File dir) {
        final ResourcePackageAdjuster adjuster = new ResourcePackageAdjuster();
        FileInputStream input;
        try {
            final String path = dir.getAbsolutePath() + "/META-INF/MANIFEST.MF";
            input = new FileInputStream(path);
            final Manifest mf = new Manifest(input);
            adjuster.convertManifest(yguardLog, mf);
            input.close();
            final FileOutputStream out = new FileOutputStream(path);
            mf.write(out);
            out.close();

        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void rewriteManifest(final File yguardLog, final File jarfile)
            throws IOException {
        final Manifest manifest = convertManifest(yguardLog, jarfile);

        final ZipInputStream oldZip = new ZipInputStream(new FileInputStream(
                jarfile));

        final String newPath = jarfile.getAbsolutePath() + ".obf.zip";
        final File newFile = new File(newPath);
        final FileOutputStream fileOutputStream = new FileOutputStream(newPath);
        final ZipOutputStream newZip = new ZipOutputStream(fileOutputStream);

        final byte[] buff = new byte[ResourcePackageAdjuster.FOUR_KILOBYTE];
        ZipEntry nextEntry = oldZip.getNextEntry();

        while (nextEntry != null) {

            int read = 0;

            // Replace Manifest entry with a new entry
            if (nextEntry.getName().equals(
                    ResourcePackageAdjuster.MANIFEST_ENTRY)) {
                final ByteArrayOutputStream manifestBytes = new ByteArrayOutputStream();
                manifest.write(manifestBytes);

                nextEntry = new ZipEntry(ResourcePackageAdjuster.MANIFEST_ENTRY);
                nextEntry.setSize(manifestBytes.size());

                newZip.putNextEntry(nextEntry);
                newZip.write(manifestBytes.toByteArray());
                manifestBytes.close();
                // Write back the entry to the new stream
            } else {
                newZip.putNextEntry(nextEntry);
                while ((read = oldZip.read(buff)) != -1) {
                    newZip.write(buff, 0, read);
                }
            }

            newZip.closeEntry();
            oldZip.closeEntry();

            nextEntry = oldZip.getNextEntry();

        }
        oldZip.close();
        newZip.close();

        if (!jarfile.delete()) {
            throw new AssertionError();
        }
        newFile.renameTo(jarfile);
    }

    /**
     * Converts the manifest contained in a jarfile and returns the converted
     * manifest as a manifest. This method does not actually write back the
     * changes to the jarfile.
     * 
     * @param jarfile
     *                the jarfile
     * @param yguardLog
     *                the yguard log
     * 
     * @return the manifest
     * 
     * @throws AssertionError
     *                 the assertion error
     * @throws FileNotFoundException
     *                 the file not found exception
     * @throws IOException
     *                 the IO exception
     */
    Manifest convertManifest(final File yguardLog, final File jarfile)
            throws IOException {
        if (!jarfile.exists()) {
            throw new FileNotFoundException(jarfile.getAbsolutePath());
        }
        final JarFile obfuscated = new JarFile(jarfile);

        final Manifest manifest = obfuscated.getManifest();
        convertManifest(yguardLog, manifest);
        obfuscated.close();
        return manifest;
    }

    private void convertManifest(final File yguardLog, final Manifest manifest) {
        final Attributes mainAttributes = manifest.getMainAttributes();
        System.out.println(mainAttributes.values());

        final String exported = mainAttributes
                .getValue(PluginReader.EXPORT_PACKAGE);
        if (exported == null) {
            return;
        }
        final String[] exports = exported.split(ResourcePackageAdjuster.COMMA);

        final StringBuilder obfExports = new StringBuilder();

        final Map<String, String> names = readLogfile(yguardLog);
        for (int i = 0; i < exports.length; i++) {
            String exp = exports[i];
            if (names.containsKey(exp)) {
                exp = names.get(exp);
            }
            obfExports.append(exp);
            if (i < exports.length - 1) {
                obfExports.append(ResourcePackageAdjuster.COMMA);
            }
        }

        if (obfExports.toString().split(ResourcePackageAdjuster.COMMA).length != exported
                .split(ResourcePackageAdjuster.COMMA).length) {
            throw new AssertionError();
        }

        mainAttributes.putValue(PluginReader.EXPORT_PACKAGE, obfExports
                .toString());

        System.out.println("Old: " + exported);
        System.out.println("New: " + obfExports.toString());
    }

    /**
     * Reads a YGuard obfuscating logfile and returns a map of package
     * obfuscating.
     * 
     * @param file
     *                the logfile file instance
     * 
     * @return a map of real => obfuscated strings
     * 
     * @throws FileNotFoundException
     * 
     */
    @SuppressWarnings("unchecked")
    Map<String, String> readLogfile(final File file) {

        final Map<String, String> packageObfuscateMap = new HashMap<String, String>();
        final Map<String, String> partialMap = new HashMap<String, String>();

        // Request document building without validation
        final SAXBuilder builder = new SAXBuilder(false);
        Document doc = null;
        try {
            doc = builder.build(file);
        } catch (final JDOMException e) {
            throw new RuntimeException("Failed to parse " + file);
        } catch (final IOException e) {
            e.printStackTrace();
            return new HashMap<String, String>();
        }
        final Element root = doc.getRootElement();
        // Print servlet information
        final List servlets = root.getChild(ResourcePackageAdjuster.MAP)
                .getChildren("package");
        for (final Iterator iter = servlets.iterator(); iter.hasNext();) {
            final Element element = (Element) iter.next();
            final String name = element.getAttributeValue("name");
            final String mapped = element
                    .getAttributeValue(ResourcePackageAdjuster.MAP);
            partialMap.put(name, mapped);

        }

        final Set<String> name2 = partialMap.keySet();
        final List<String> names = new ArrayList<String>();
        names.addAll(name2);
        Collections.sort(names, new Comparator<String>() {
            public int compare(final String o1, final String o2) {
                return o1.length() - o2.length();
            }
        });

        int length = 0;
        for (final String name : names) {
            if (name.length() < length) {
                throw new AssertionError();
            }
            length = name.length();
            String mapped = partialMap.get(name);

            if (name.contains(ResourcePackageAdjuster.DOT)) {
                final int i = name.lastIndexOf(ResourcePackageAdjuster.DOT);
                final String parentPkg = name.substring(0, i);
                System.out.println("Parent: " + parentPkg);
                String parentMapped = packageObfuscateMap.get(parentPkg);
                if (parentMapped == null) {
                    parentMapped = parentPkg;
                }
                mapped = parentMapped + ResourcePackageAdjuster.DOT + mapped;

            }
            packageObfuscateMap.put(name, mapped);

            System.out.println(name + " = " + mapped);
            System.out.println("");
        }
        return packageObfuscateMap;
    }
}
