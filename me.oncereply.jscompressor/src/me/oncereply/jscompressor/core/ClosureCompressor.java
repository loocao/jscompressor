package me.oncereply.jscompressor.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.widgets.Shell;

import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.JSSourceFile;
import com.google.javascript.jscomp.SourceFile;

public class ClosureCompressor implements ICompressor {

	private List<String> options = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(final String fileInput, final String fileOutput)
			throws Exception {
//		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
//				.getActiveShell();
//		shell.getDisplay().getSyncThread();
//		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
//		Thread.currentThread().setContextClassLoader(loader);
//		Class c = loader.loadClass(CommandLineRunner.class.getName());
//		Method main = c.getMethod("main", new Class[] { String[].class });
//		List<String> list = new ArrayList<String>();
//		list.add("--js");
//		list.add(fileInput);
//		list.add("--js_output_file");
//		list.add(fileOutput);
//		list.addAll(options);
//		main.invoke(null, new Object[] { list.toArray(new String[] {}) });
		
		Class c = Class.forName("com.google.javascript.jscomp.CommandLineRunner");
        Class sc = c.getSuperclass();
        String[] cmds = {};
        Constructor constructor = c.getDeclaredConstructor(new Class[] { cmds.getClass() });
        constructor.setAccessible(true);
        List<String> cmdLine = new ArrayList<String>();
        cmdLine.add("--js");
        cmdLine.add(fileInput);
        cmdLine.add("--js_output_file");
        cmdLine.add(fileOutput);
        cmdLine.addAll(options);
        cmds = cmdLine.toArray(cmds);
        CommandLineRunner cmd = (CommandLineRunner) constructor.newInstance(new Object[] { cmds });
        Method method = sc.getDeclaredMethod("doRun");
        method.setAccessible(true);
        method.invoke(cmd, new Object[] {});
		
	}

	@Override
	public void setOptions(List<String> options) {
		this.options = options;
	}

}
