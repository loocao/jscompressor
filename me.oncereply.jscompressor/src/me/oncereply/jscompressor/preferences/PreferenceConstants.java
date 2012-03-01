package me.oncereply.jscompressor.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	public static final String P_PATH = "pathPreference";

	public static final String P_BOOLEAN = "booleanPreference";

	public static final String P_CHOICE = "choicePreference";

	public static final String P_STRING = "stringPreference";

	public static final String P_COMPRESSOR_CHOICE = "compressor";

	/**
	 * yui:charset
	 */
	public static final String P_YUI_CHARSET_CHOICE = "yui_charset";

	/**
	 * YUI:--nomunge Minify only. Do not obfuscate local symbols.
	 */
	public static final String P_YUI_NOMUNGE_BOOLEAN = "yui_nomunge";

	/**
	 * YUI:--preserve-semi Preserve unnecessary semicolons (such as right before
	 * a '}') This option is useful when compressed code has to be run through
	 * JSLint (which is the case of YUI for example)
	 */
	public static final String P_YUI_PRESERVE_SEMI_BOOLEAN = "yui_preserve_semi";
	
	/**
	 * YUI:--disable-optimizations Disable all the built-in micro optimizations.
	 */
	public static final String P_YUI_DISABLE_OPTIMIZATIONS="yui_disable_optimizations";

}
