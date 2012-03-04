package me.oncereply.jscompressor.core;

import java.util.List;

public interface ICompressor {

	void setOptions(List<String> options);

	void compress(String fileInput, String fileOutput) throws Exception;
}
