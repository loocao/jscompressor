package me.oncereply.jscompressor.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	public static final String P_CHOICE_COMPRESSOR = "compressor";

	/**
	 * JavaScript压缩开关,默认false,表示压缩
	 */
	public static final String P_BOOLEAN_JAVASCRIPT_SWITCH = "javascipt_switch";

	/**
	 * CSS压缩开关,默认false,表示压缩
	 */
	public static final String P_BOOLEAN_CSS_SWITCH = "css_switch";

	/**
	 * yui:charset
	 */
	public static final String P_YUI_CHOICE_CHARSET = "yui_charset";

	/**
	 * YUI:--nomunge Minify only. Do not obfuscate local symbols.
	 */
	public static final String P_YUI_BOOLEAN_NOMUNGE = "yui_nomunge";

	/**
	 * YUI:--preserve-semi Preserve unnecessary semicolons (such as right before
	 * a '}') This option is useful when compressed code has to be run through
	 * JSLint (which is the case of YUI for example)
	 */
	public static final String P_YUI_BOOLEAN_PRESERVE_SEMI = "yui_preserve_semi";

	/**
	 * YUI:--disable-optimizations Disable all the built-in micro optimizations.
	 */
	public static final String P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS = "yui_disable_optimizations";

}
