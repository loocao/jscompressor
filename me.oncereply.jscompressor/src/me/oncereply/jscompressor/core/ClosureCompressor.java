package me.oncereply.jscompressor.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.widgets.Shell;

import com.google.javascript.jscomp.CommandLineRunner;

public class ClosureCompressor implements ICompressor {

	private List<String> options = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(final String fileInput, final String fileOutput)
			throws Exception {
		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
				.getActiveShell();
		shell.getDisplay().getSyncThread();
		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		Class c = loader
				.loadClass(com.google.javascript.jscomp.CommandLineRunner.class
						.getName());
		Class sc = c.getSuperclass();
		// 参数
		String[] cmds = {};
		Constructor constructor = c.getDeclaredConstructor(new Class[] { cmds
				.getClass() });
		constructor.setAccessible(true);
		List<String> cmdLine = new ArrayList<String>();
		cmdLine.add("--js");
		cmdLine.add(fileInput);
		cmdLine.add("--js_output_file");
		cmdLine.add(fileOutput);
		cmdLine.addAll(options);
		cmds = cmdLine.toArray(new String[] {});
		CommandLineRunner cmd = (CommandLineRunner) constructor
				.newInstance(new Object[] { cmds });
		Method method = sc.getDeclaredMethod("doRun");
		method.setAccessible(true);
		method.invoke(cmd, new Object[] {});
	}

	@Override
	public void setOptions(List<String> options) {
		this.options = options;
	}

	@Override
	public boolean isCompressable(String extension) {
		if ("js".equalsIgnoreCase(extension) && !switch_javascript) {
			return true;
		}
		return false;
	}

}
