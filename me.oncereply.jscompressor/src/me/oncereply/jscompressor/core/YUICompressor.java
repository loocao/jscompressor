package me.oncereply.jscompressor.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.widgets.Shell;

public class YUICompressor implements ICompressor {

	private List<String> options = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(String... args) throws Exception {
		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
				.getActiveShell();
		shell.getDisplay().getSyncThread();
		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		Class c = loader
				.loadClass(com.yahoo.platform.yui.compressor.YUICompressor.class
						.getName());
		Method main = c.getMethod("main", new Class[] { String[].class });
		List<String> list = new ArrayList();
		Collections.addAll(list, args);
		list.addAll(options);
		main.invoke(null, new Object[]{list.toArray(new String[]{})});
	}

	@Override
	public void setOptions(List<String> options) {
		this.options = options;
	}

}
