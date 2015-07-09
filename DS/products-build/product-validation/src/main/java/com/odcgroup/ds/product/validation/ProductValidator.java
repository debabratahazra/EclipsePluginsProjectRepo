package com.odcgroup.ds.product.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ProductValidator {
	
	@SuppressWarnings("serial")
	public static class ProductValidationFailed extends Exception {
		public ProductValidationFailed(String message) {
			super(message);
		}
	};
	
	/**
	 * @param args
	 * @throws ProductValidationFailed 
	 */
	public static void main(String[] args) throws ProductValidationFailed {
		System.out.println("Staring ProductValidator for");
		System.out.println(StringUtils.join(args, "\n"));
		if (args.length == 0 || args.length % 2 == 1) {
			throw new IllegalArgumentException("sets 2 arguments pairs are expected: product descriptor (.product) file path and product zip path. Got " + args.length + ": \n" + StringUtils.join(args, "\n"));
		}
		
		for (int i=0; i<args.length / 2; i++) {
			File productDescriptor = new File(args[i*2]);
			File productZip = new File(args[i*2+1]);
			
			System.out.println("Validating " + productZip.getAbsolutePath() + " with " + productDescriptor.getAbsolutePath() + " ...");
			
			new ProductValidator().validate(productDescriptor, productZip);
			
			System.out.println("The product definition is valid.");
		}
	}

	public void validate(File productDescriptor, File productZip) throws ProductValidationFailed {
		List<String> declaredPlugins;
		List<String> inlcudedPlugins;
		
		try {
			declaredPlugins = getDeclaredPlugins(productDescriptor);
		} catch (Exception e) {
			throw new ProductValidationFailed("Unable to read the product definition : " + productDescriptor.getAbsolutePath());
		}
		
		try {
			inlcudedPlugins = getIncludedPlugins(productZip);
		} catch (IOException e) {
			throw new ProductValidationFailed("Unable to read the product zip : " + productZip.getAbsolutePath());
		}
		
		List<String> undeclaredPlugins = getUndeclaredPlugins(declaredPlugins, inlcudedPlugins);
		if (undeclaredPlugins.size() != 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("There are undeclared plugin(s) in " + productZip.getAbsolutePath() + "\n");
			for (String undeclaredPlugin : undeclaredPlugins) {
				sb.append(undeclaredPlugin);
				sb.append("\n");
			}
			throw new ProductValidationFailed(sb.toString());
		}

	}

	List<String> getUndeclaredPlugins(
			List<String> declaredPlugins, List<String> inlcudedPlugins) {
		List<String> undeclaredPlugins = new ArrayList<String>();
		for (String includedPlugin : inlcudedPlugins) {
			includedPlugin = extractPluginId(includedPlugin);
			
			if (isException(includedPlugin))
				continue;
			
			if (!declaredPlugins.contains(includedPlugin)) {
				undeclaredPlugins.add(includedPlugin);
			}
		}
		return undeclaredPlugins;
	}

	private String extractPluginId(String includedPlugin) {
		String result = StringUtils.removeStart(includedPlugin, "plugins/");
		return StringUtils.substringBefore(result, "_");
	}

	private boolean isPlugin(String path) {
		if (path.startsWith("plugins/")) {
			if (path.endsWith(".jar") && StringUtils.countMatches(path, "/") == 1)
				return true;
			if (path.endsWith("/") && StringUtils.countMatches(path, "/") == 2)
				return true;
		}
		return false;
	}

	boolean isException(String includedPlugin) {
		return includedPlugin.contains(".win32.x86") ||
				includedPlugin.startsWith("org.eclipse.") ||
				includedPlugin.startsWith("javax.");
	}

	List<String> getDeclaredPlugins(File productDescriptor) throws JDOMException, IOException {
		List<String> result = new ArrayList<String>();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(productDescriptor);
		List<Element> plugins = document.getRootElement().getChild("plugins").getChildren("plugin");
		for (Element plugin: plugins) {
			result.add(plugin.getAttributeValue("id"));
		}
		return result;
	}

	List<String> getIncludedPlugins(File productZip) throws IOException {
		List<String> result = new ArrayList<String>();
		for (String file :  listZipContent(productZip)) {
			if (isPlugin(file)) {
				result.add(file);
			}
		}
		return result;
	}
	
	List<String> listZipContent(File zip) throws IOException {
		List<String> includedPlugins = new ArrayList<String>();
		ZipArchiveInputStream zipInput = null;
		try {
			zipInput = new ZipArchiveInputStream(new FileInputStream(zip));
	        ZipArchiveEntry entry;
	        while (null!=(entry=zipInput.getNextZipEntry())) {
	        	includedPlugins.add(entry.getName());
	        }
		} finally {
			IOUtils.closeQuietly(zipInput);
		}
        return includedPlugins;
	}

}
