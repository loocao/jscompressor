package me.oncereply.jscompressor.preferences;

import me.oncereply.jscompressor.Activator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

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

public class CompressorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public CompressorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		 setDescription("JavaScript Compressor Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extension = registry
				.getExtensionPoint("me.oncereply.jscompressor.compressors");
		IConfigurationElement[] configElements = extension
				.getConfigurationElements();
		String[][] compressors = new String[configElements.length][2];
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement ce = configElements[i];
			compressors[i][0] = ce.getAttribute("name");
			compressors[i][1] = ce.getAttribute("id");
		}

		// 选择压缩器
		addField(new ComboFieldEditor(PreferenceConstants.P_CHOICE_COMPRESSOR, "&Compressor:", compressors, getFieldEditorParent()));
		
		// 是否在压缩后的文件名中带min标记
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN_MIN_SYMBOL,
				"Export with \"min\" symbol.", getFieldEditorParent()));

		// JavaScript压缩开关
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN_JAVASCRIPT_SWITCH,
				"Don't compress JavaScript files.", getFieldEditorParent()));
		// CSS压缩开关
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN_CSS_SWITCH,
				"Don't compress CSS files.", getFieldEditorParent()));
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