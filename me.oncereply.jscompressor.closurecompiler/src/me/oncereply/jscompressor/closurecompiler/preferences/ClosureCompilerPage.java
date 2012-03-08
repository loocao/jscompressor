package me.oncereply.jscompressor.closurecompiler.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import me.oncereply.jscompressor.closurecompiler.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ClosureCompilerPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public ClosureCompilerPage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Closure Compiler Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		// 压缩级别
		addField(new RadioGroupFieldEditor(PreferenceConstants.P_CLOSURE_CHOICE_COMPILATION_LEVEL,"Compilation level:", 1, new String[][] { { "&Whitespace", "WHITESPACE_ONLY" },
				{ "&Simple", "SIMPLE_OPTIMIZATIONS" },
				{ "&Advanced", "ADVANCED_OPTIMIZATIONS" } },getFieldEditorParent()));
		// 美化代码
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRETTY_PRINT,
				"Pretty print", getFieldEditorParent()));
		// Print input delimiter
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRINT_INPUT_DELIMITER,
				"Print input delimiter", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}