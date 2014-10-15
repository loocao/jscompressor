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
		addField(new ComboFieldEditor(PreferenceConstants.P_YUI_CHOICE_CHARSET,
				"Charset:", new String[][] { { "GB2312", "GB2312" },
						{ "GBK", "GBK" }, { "ISO-8859-1", "ISO-8859-1" },
						{ "US-ASCII", "US-ASCII" }, { "UTF-16", "UTF-16" },
						{ "UTF-8", "UTF-8" } }, getFieldEditorParent()));
		// --nomunge
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_YUI_BOOLEAN_NOMUNGE,
				"&Minify only. Do not obfuscate local symbols.",
				getFieldEditorParent()));
		// --preserve-semi
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_YUI_BOOLEAN_PRESERVE_SEMI,
				"&Preserve unnecessary semicolons (such as right before a '}')",
				getFieldEditorParent()));
		// --disable-optimizations
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS,
				"&Disable all the built-in micro optimizations.",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}

}
