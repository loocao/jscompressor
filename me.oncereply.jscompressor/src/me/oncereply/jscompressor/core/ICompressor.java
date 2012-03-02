package me.oncereply.jscompressor.core;

import java.util.List;

public interface ICompressor {

	public void compress(String... args) throws Exception;

	void setOptions(List<String> options);
}
