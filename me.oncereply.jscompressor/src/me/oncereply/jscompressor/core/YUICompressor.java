package me.oncereply.jscompressor.core;

import java.lang.reflect.Method;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.widgets.Shell;

public class YUICompressor implements ICompressor {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(String... args) throws Exception {
		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
				.getActiveShell();
		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
		shell.getDisplay().getSyncThread();
		Thread.currentThread().setContextClassLoader(loader);
		Class c = loader.loadClass(com.yahoo.platform.yui.compressor.YUICompressor.class.getName());
		Method main = c.getMethod("main", new Class[] { String[].class });
		main.invoke(null, new Object[] { args });
	}

	@Override
	public void setPreferences(String[] preferences) {
		// TODO 加载 yui的preferences配置
		
	}

}
