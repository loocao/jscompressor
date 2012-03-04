package me.oncereply.jscompressor.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.widgets.Shell;

public class YUICompressor implements ICompressor {

	private List<String> options = null;

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

	@Override
	public void setOptions(List<String> options) {
		this.options = options;
	}

	@Override
	public boolean isCompressable(String extension) {
		if (("js".equalsIgnoreCase(extension) && !switch_javascript)
				|| ("css".equalsIgnoreCase(extension) && !switch_css)) {
			return true;
		}
		return false;
	}

}
