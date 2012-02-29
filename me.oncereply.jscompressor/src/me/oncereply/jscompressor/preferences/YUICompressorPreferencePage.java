package me.oncereply.jscompressor.preferences;

import me.oncereply.jscompressor.Activator;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;
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
				PreferenceConstants.P_YUICOMPRESSOR_CHARSET_CHOICE, "Charset:",
				new String[][] {
						{"UTF-8","UTF-8"},
						{"GBK","GBK"}
				}, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUICOMPRESSOR_NOMUNGE_BOOLEAN,
				"&Minify only. Do not obfuscate local symbols.", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUICOMPRESSOR_PRESERVE_SEMI_BOOLEAN,
				"&Preserve unnecessary semicolons (such as right before a '}')", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_YUICOMPRESSOR_NOMUNGE_BOOLEAN,
				"&Minify only. Do not obfuscate local symbols.", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}

}
