package me.oncereply.jscompressor.core;

import me.oncereply.jscompressor.util.ConsoleUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class CompressorFactory {

	public static ICompressor newCompressor(String type) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extension = registry
				.getExtensionPoint("me.oncereply.jscompressor.compressors");
		IConfigurationElement[] configElements = extension
				.getConfigurationElements();
		for (IConfigurationElement ce : configElements) {
			if (type.equals(ce.getAttribute("class"))) {
				ICompressor compressor = null;
				try {
					compressor = (ICompressor) ce
							.createExecutableExtension("class");
				} catch (CoreException e) {
					ConsoleUtils.error(
							"Class not found:" + ce.getAttribute("class"), e);
				}
				return compressor;
			}
		}
		return null;
	}

}
