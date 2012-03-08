package me.oncereply.jscompressor.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.preferences.PreferenceConstants;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

public class YUICompressor implements ICompressor {

	private List<String> options = null;
	
	public YUICompressor(){
		init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(String fileInput, String fileOutput) throws Exception {
		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
				.getActiveShell();
		shell.getDisplay().getSyncThread();
		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		Class c = loader
				.loadClass(com.yahoo.platform.yui.compressor.YUICompressor.class
						.getName());
		Method main = c.getMethod("main", new Class[] { String[].class });
		List<String> list = new ArrayList<String>();
		list.add(fileInput);
		list.add("-o");
		list.add(fileOutput);
		list.addAll(options);
		main.invoke(null, new Object[] { list.toArray(new String[] {}) });
	}

	private void init() {
		IPreferenceStore store = Activator.getDefault()
				.getPreferenceStore();
		options = new ArrayList<String>();
		options.add("--charset");
		options.add(store.getString(PreferenceConstants.P_YUI_CHOICE_CHARSET));
		if (store.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_NOMUNGE)) {
			options.add("--nomunge");
		}
		if (store.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_PRESERVE_SEMI)) {
			options.add("--preserve-semi");
		}
		if (store
				.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS)) {
			options.add("--disable-optimizations");
		}
	}

	@Override
	public boolean isCompressable(String extension) {
		if ("js".equalsIgnoreCase(extension)
				|| "css".equalsIgnoreCase(extension)) {
			return true;
		}
		return false;
	}

}
