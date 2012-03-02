package me.oncereply.jscompressor.core;

import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.preferences.PreferenceConstants;

public class CompressorFactory {

	public static ICompressor newCompressor(String compressor) {
		ICompressor c = null;
		if (compressor.equals(Activator.Compressor.YUICompressor)) {
			c = new YUICompressor();
			// 加载 yui的preferences配置
			List<String> options = new ArrayList<String>();
			options.add("--charset");
			options.add(getStoreString(PreferenceConstants.P_YUI_CHARSET_CHOICE));
			if (getStoreBealoon(PreferenceConstants.P_YUI_NOMUNGE_BOOLEAN)) {
				options.add("--nomunge");
			}
			if (getStoreBealoon(PreferenceConstants.P_YUI_NOMUNGE_BOOLEAN)) {
				options.add("--preserve-semi");
			}
			if (getStoreBealoon(PreferenceConstants.P_YUI_NOMUNGE_BOOLEAN)) {
				options.add("--disable-optimizations");
			}
			c.setOptions(options);
		} else if (compressor.equals(Activator.Compressor.ClosureCompiler)) {
			// 加载 Closure-Compile的preferences配置
			// TODO 加载 Closure-Compile的preferences配置
			c = new ClosureCompilerCompressor();
		}
		return c;
	}

	private static String getStoreString(String name) {
		return Activator.getDefault().getPreferenceStore().getString(name);
	}

	private static boolean getStoreBealoon(String name) {
		return Activator.getDefault().getPreferenceStore().getBoolean(name);
	}

}
