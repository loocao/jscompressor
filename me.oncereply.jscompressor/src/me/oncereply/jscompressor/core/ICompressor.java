package me.oncereply.jscompressor.core;

import java.util.List;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.preferences.PreferenceConstants;

public interface ICompressor {

	public boolean switch_javascript = Activator.getDefault().getPreferenceStore()
			.getBoolean(PreferenceConstants.P_BOOLEAN_JAVASCRIPT_SWITCH);
	public boolean switch_css = Activator.getDefault().getPreferenceStore()
			.getBoolean(PreferenceConstants.P_BOOLEAN_CSS_SWITCH);

	void setOptions(List<String> options);

	void compress(String fileInput, String fileOutput) throws Exception;

	/**
	 * 检查是否支持压缩
	 * 
	 * @return
	 */
	boolean isCompressable(String extension);
}
