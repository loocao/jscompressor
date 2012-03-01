package me.oncereply.jscompressor.core;


public interface ICompressor {
	
	public void compress(String...args) throws Exception;

	public void setPreferences(String[] preferences);
}
