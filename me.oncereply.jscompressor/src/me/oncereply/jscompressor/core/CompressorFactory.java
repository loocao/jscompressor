package me.oncereply.jscompressor.core;

import java.util.HashMap;
import java.util.Map;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.preferences.PreferenceConstants;

public class CompressorFactory {

	private static Map<String, ICompressor> compressorMap = new HashMap<String, ICompressor>();

	public static ICompressor newCompressor(String compressor) {
		ICompressor c = compressorMap.get(compressor);
		if (c == null) {
			if (compressor.equals(Activator.Compressor.YUICompressor)) {
				// 加载 yui的preferences配置
				String[] preferences = new String[] {
						getStoreString(PreferenceConstants.P_YUI_CHARSET_CHOICE),
						getStoreString(PreferenceConstants.P_YUI_NOMUNGE_BOOLEAN),
						getStoreString(PreferenceConstants.P_YUI_PRESERVE_SEMI_BOOLEAN),
						getStoreString(PreferenceConstants.P_YUI_DISABLE_OPTIMIZATIONS) };
				c = new YUICompressor();
				c.setPreferences(preferences);
			} else if (compressor.equals(Activator.Compressor.ClosureCompiler)) {
				// 加载 Closure-Compile的preferences配置
				// TODO 加载 Closure-Compile的preferences配置
				c = new ClosureCompilerCompressor();
			}
			compressorMap.put(compressor, c);
		}
		return c;
	}

	private static String getStoreString(String name) {
		return Activator.getDefault().getPreferenceStore()
				.getDefaultString(name);
	}

}
