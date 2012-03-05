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
			options.add(getStoreString(PreferenceConstants.P_YUI_CHOICE_CHARSET));
			if (getStoreBealoon(PreferenceConstants.P_YUI_BOOLEAN_NOMUNGE)) {
				options.add("--nomunge");
			}
			if (getStoreBealoon(PreferenceConstants.P_YUI_BOOLEAN_PRESERVE_SEMI)) {
				options.add("--preserve-semi");
			}
			if (getStoreBealoon(PreferenceConstants.P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS)) {
				options.add("--disable-optimizations");
			}
			c.setOptions(options);
		} else if (compressor.equals(Activator.Compressor.ClosureCompiler)) {
			// 加载 Closure-Compile的preferences配置
			c = new ClosureCompressor();
			List<String> options = new ArrayList<String>();
			options.add("--compilation_level");
			options.add(getStoreString(PreferenceConstants.P_CLOSURE_CHOICE_COMPILATION_LEVEL));
			if (getStoreBealoon(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRETTY_PRINT)) {
				options.add("--formatting");
				options.add("pretty_print");
			}
			if (getStoreBealoon(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRINT_INPUT_DELIMITER)) {
				options.add("--formatting");
				options.add("print_input_delimiter");
			}
			c.setOptions(options);
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
