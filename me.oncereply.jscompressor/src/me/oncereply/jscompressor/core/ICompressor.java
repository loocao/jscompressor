package me.oncereply.jscompressor.core;


public interface ICompressor {

	void compress(String fileInput, String fileOutput) throws Exception;

	/**
	 * 检查是否支持压缩
	 * 
	 * @return
	 */
	boolean isCompressable(String extension);
}
