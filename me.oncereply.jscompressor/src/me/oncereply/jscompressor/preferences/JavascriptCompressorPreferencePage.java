package me.oncereply.jscompressor.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import me.oncereply.jscompressor.Activator;

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

public class JavascriptCompressorPreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public JavascriptCompressorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		// setDescription("Compressor Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		//选择压缩器
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_COMPRESSOR_CHOICE ,
				"Select which one to compress JavaScript:",
				2,
				new String[][] {
					{ "&YUI Compressor", Activator.Compressor.YUICompressor },
					{ "&Closure Compiler",Activator.Compressor.ClosureCompiler } 
				},
				getFieldEditorParent()));
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