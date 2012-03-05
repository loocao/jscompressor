package me.oncereply.jscompressor.preferences;

import me.oncereply.jscompressor.Activator;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ClosureCompilerPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public ClosureCompilerPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Closure Compiler Compressor Settings");
	}

	@Override
	protected void createFieldEditors() {
		// 压缩级别
		addField(new ComboFieldEditor(
				PreferenceConstants.P_CLOSURE_CHOICE_COMPILATION_LEVEL,
				"&Compilation level",
				new String[][] { { "WHITESPACE_ONLY", "WHITESPACE_ONLY" },
						{ "SIMPLE_OPTIMIZATIONS", "SIMPLE_OPTIMIZATIONS" },
						{ "ADVANCED_OPTIMIZATIONS", "ADVANCED_OPTIMIZATIONS" } },
				getFieldEditorParent()));
		// 美化代码
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRETTY_PRINT,
				"Pretty print", getFieldEditorParent()));
		//Print input delimiter
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRINT_INPUT_DELIMITER,
				"Print input delimiter", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
