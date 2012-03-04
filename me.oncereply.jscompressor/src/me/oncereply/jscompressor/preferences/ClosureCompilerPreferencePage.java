package me.oncereply.jscompressor.preferences;

import me.oncereply.jscompressor.Activator;

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
		addField(new ComboFieldEditor(
				PreferenceConstants.P_CLOSURE_CHOICE_CHARSET, "Charset:",
				new String[][] { { "GB2312", "GB2312" }, { "GBK", "GBK" },
						{ "ISO-8859-1", "ISO-8859-1" },
						{ "US-ASCII", "US-ASCII" }, { "UTF-16", "UTF-16" },
						{ "UTF-8", "UTF-8" } }, getFieldEditorParent()));

		// TODO Closure Compiler的preferences配置
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
