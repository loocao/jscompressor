package me.oncereply.jscompressor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import me.oncereply.jscompressor.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_CHOICE_COMPRESSOR,
				Activator.Compressor.YUICompressor);
		store.setDefault(PreferenceConstants.P_BOOLEAN_JAVASCRIPT_SWITCH, false);
		store.setDefault(PreferenceConstants.P_BOOLEAN_CSS_SWITCH, false);
		// yui
		store.setDefault(PreferenceConstants.P_YUI_CHOICE_CHARSET, "UTF-8");
		store.setDefault(PreferenceConstants.P_YUI_BOOLEAN_NOMUNGE, false);
		store.setDefault(PreferenceConstants.P_YUI_BOOLEAN_PRESERVE_SEMI, false);
		store.setDefault(PreferenceConstants.P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS, false);
	}

}
