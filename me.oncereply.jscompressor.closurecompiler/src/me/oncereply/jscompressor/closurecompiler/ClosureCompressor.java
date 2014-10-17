package me.oncereply.jscompressor.closurecompiler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.oncereply.jscompressor.closurecompiler.preferences.PreferenceConstants;
import me.oncereply.jscompressor.core.ICompressor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import com.google.javascript.jscomp.CommandLineRunner;

public class ClosureCompressor implements ICompressor {
	private List<String> options = null;

	public ClosureCompressor() {
		init();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

		//->@wjw_add
		IPreferenceStore localStore = Activator.getDefault().getPreferenceStore();
		if (localStore.getBoolean(PreferenceConstants.P_CREATE_SOURCE_MAP)) {
			cmdLine.add("--source_map_format");
			cmdLine.add("V3");  //source map的版本,目前一律采用V3
			cmdLine.add("--create_source_map");
			cmdLine.add(fileOutput+".map");
		}
        //<-@wjw_add		
	
		if (options != null) {
			cmdLine.addAll(options);
		}
		cmds = cmdLine.toArray(new String[] {});
		CommandLineRunner cmd = (CommandLineRunner) constructor
				.newInstance(new Object[] { cmds });
		Method method = sc.getDeclaredMethod("doRun");
		method.setAccessible(true);
		method.invoke(cmd, new Object[] {});
	}

	private void init() {
		IPreferenceStore localStore = Activator.getDefault()
				.getPreferenceStore();

		options = new ArrayList<String>();
		options.add("--compilation_level");
		options.add(localStore
				.getString(PreferenceConstants.P_CLOSURE_CHOICE_COMPILATION_LEVEL));
		if (localStore
				.getBoolean(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRETTY_PRINT)) {
			options.add("--formatting");
			options.add("pretty_print");
		}
		if (localStore
				.getBoolean(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRINT_INPUT_DELIMITER)) {
			options.add("--formatting");
			options.add("print_input_delimiter");
		}
	}

	@Override
	public boolean isCompressable(String extension) {
		if ("js".equalsIgnoreCase(extension)) {
			return true;
		}
		return false;
	}

}
