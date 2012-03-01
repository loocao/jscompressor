package me.oncereply.jscompressor.preferences;

import me.oncereply.jscompressor.Activator;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class YUICompressorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public YUICompressorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("YUI Compressor Settings");
	}

	@Override
	protected void createFieldEditors() {
		addField(new ComboFieldEditor(
				PreferenceConstants.P_YUI_CHARSET_CHOICE, "Charset:",
				new String[][] {
						{"UTF-8","UTF-8"},
						{"GBK","GBK"}
				}, getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUI_NOMUNGE_BOOLEAN,
				"&Minify only. Do not obfuscate local symbols.", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUI_PRESERVE_SEMI_BOOLEAN,
				"&Preserve unnecessary semicolons (such as right before a '}')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUI_DISABLE_OPTIMIZATIONS,
				"&Disable all the built-in micro optimizations.", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}

}
