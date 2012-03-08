package me.oncereply.jscompressor.closurecompiler.preferences;

import me.oncereply.jscompressor.closurecompiler.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		//closure
		store.setDefault(PreferenceConstants.P_CLOSURE_CHOICE_COMPILATION_LEVEL, "SIMPLE_OPTIMIZATIONS");
		store.setDefault(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRETTY_PRINT, false);
		store.setDefault(PreferenceConstants.P_CLOSURE_BOOLEAN_FORMATTING_PRINT_INPUT_DELIMITER, false);
	}

}
