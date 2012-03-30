package me.oncereply.jscompressor.core;

import jargs.gnu.CmdLineParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.CompressorException;
import me.oncereply.jscompressor.preferences.PreferenceConstants;
import me.oncereply.jscompressor.util.LogUtils;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YUICompressor implements ICompressor {

	private List<String> options = null;

	public YUICompressor() {
		init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void compress(String fileInput, String fileOutput) throws Exception {
		Shell shell = Activator.getDefault().getWorkbench().getDisplay()
				.getActiveShell();
		shell.getDisplay().getSyncThread();
		ClassLoader loader = Activator.getDefault().getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		Class c = loader.loadClass(YUICompressorTool.class.getName());
		Method main = c.getMethod("main", new Class[] { String[].class });
		List<String> list = new ArrayList<String>();
		list.add(fileInput);
		list.add("-o");
		list.add(fileOutput);
		list.addAll(options);
		main.invoke(null, new Object[] { list.toArray(new String[] {}) });
	}

	private void init() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		options = new ArrayList<String>();
		options.add("--charset");
		options.add(store.getString(PreferenceConstants.P_YUI_CHOICE_CHARSET));
		if (store.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_NOMUNGE)) {
			options.add("--nomunge");
		}
		if (store.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_PRESERVE_SEMI)) {
			options.add("--preserve-semi");
		}
		if (store
				.getBoolean(PreferenceConstants.P_YUI_BOOLEAN_DISABLE_OPTIMIZATIONS)) {
			options.add("--disable-optimizations");
		}
	}

	@Override
	public boolean isCompressable(String extension) {
		if ("js".equalsIgnoreCase(extension)
				|| "css".equalsIgnoreCase(extension)) {
			return true;
		}
		return false;
	}

	private static class YUICompressorTool {

		@SuppressWarnings("unused")
		public static void main(String args[]) throws CompressorException {

			CmdLineParser parser = new CmdLineParser();
			CmdLineParser.Option typeOpt = parser.addStringOption("type");
			CmdLineParser.Option verboseOpt = parser.addBooleanOption('v',
					"verbose");
			CmdLineParser.Option nomungeOpt = parser
					.addBooleanOption("nomunge");
			CmdLineParser.Option linebreakOpt = parser
					.addStringOption("line-break");
			CmdLineParser.Option preserveSemiOpt = parser
					.addBooleanOption("preserve-semi");
			CmdLineParser.Option disableOptimizationsOpt = parser
					.addBooleanOption("disable-optimizations");
			CmdLineParser.Option helpOpt = parser.addBooleanOption('h', "help");
			CmdLineParser.Option charsetOpt = parser.addStringOption("charset");
			CmdLineParser.Option outputFilenameOpt = parser.addStringOption(
					'o', "output");

			Reader in = null;
			Writer out = null;

			try {

				parser.parse(args);

				Boolean help = (Boolean) parser.getOptionValue(helpOpt);
				if (help != null && help.booleanValue()) {
					throw new CompressorException("help参数格式有误");
				}

				boolean verbose = parser.getOptionValue(verboseOpt) != null;

				String charset = (String) parser.getOptionValue(charsetOpt);
				if (charset == null || !Charset.isSupported(charset)) {
					// charset = System.getProperty("file.encoding");
					// if (charset == null) {
					// charset = "UTF-8";
					// }

					// UTF-8 seems to be a better choice than what the system is
					// reporting
					charset = "UTF-8";

					if (verbose) {
						System.err.println("\n[INFO] Using charset " + charset);
					}
				}

				int linebreakpos = -1;
				String linebreakstr = (String) parser
						.getOptionValue(linebreakOpt);
				if (linebreakstr != null) {
					try {
						linebreakpos = Integer.parseInt(linebreakstr, 10);
					} catch (NumberFormatException e) {
						throw new CompressorException(e);
					}
				}

				String type = (String) parser.getOptionValue(typeOpt);
				if (type != null && !type.equalsIgnoreCase("js")
						&& !type.equalsIgnoreCase("css")) {
					throw new CompressorException("type参数格式有误");
				}

				String[] fileArgs = parser.getRemainingArgs();
				List<String> files = Arrays.asList(fileArgs);
				if (files.isEmpty()) {
					if (type == null) {
						throw new CompressorException("type参数格式有误");
					}
					files = new ArrayList<String>();
					files.add("-"); // read from stdin
				}

				String output = (String) parser
						.getOptionValue(outputFilenameOpt);
				String pattern[] = output != null ? output.split(":")
						: new String[0];

				Iterator<String> filenames = files.iterator();
				while (filenames.hasNext()) {
					String inputFilename = (String) filenames.next();

					try {
						if (inputFilename.equals("-")) {

							in = new InputStreamReader(System.in, charset);

						} else {

							if (type == null) {
								int idx = inputFilename.lastIndexOf('.');
								if (idx >= 0
										&& idx < inputFilename.length() - 1) {
									type = inputFilename.substring(idx + 1);
								}
							}

							if (type == null || !type.equalsIgnoreCase("js")
									&& !type.equalsIgnoreCase("css")) {
								throw new CompressorException("type参数格式有误");
							}

							in = new InputStreamReader(new FileInputStream(
									inputFilename), charset);
						}

						String outputFilename = output;
						// if a substitution pattern was passed in
						if (pattern.length > 1 && files.size() > 1) {
							outputFilename = inputFilename.replaceFirst(
									pattern[0], pattern[1]);
						}

						if (type.equalsIgnoreCase("js")) {

							try {

								JavaScriptCompressor compressor = new JavaScriptCompressor(
										in, new ErrorReporter() {

											public void warning(String message,
													String sourceName,
													int line,
													String lineSource,
													int lineOffset) {
												if (line < 0) {
													System.err
															.println("\n[WARNING] "
																	+ message);
												} else {
													System.err
															.println("\n[WARNING] "
																	+ line
																	+ ':'
																	+ lineOffset
																	+ ':'
																	+ message);
												}
											}

											public void error(String message,
													String sourceName,
													int line,
													String lineSource,
													int lineOffset) {
												if (line < 0) {
													System.err
															.println("\n[ERROR] "
																	+ message);
												} else {
													System.err
															.println("\n[ERROR] "
																	+ line
																	+ ':'
																	+ lineOffset
																	+ ':'
																	+ message);
												}
											}

											public EvaluatorException runtimeError(
													String message,
													String sourceName,
													int line,
													String lineSource,
													int lineOffset) {
												error(message, sourceName,
														line, lineSource,
														lineOffset);
												return new EvaluatorException(
														message);
											}
										});

								// Close the input stream first, and then open
								// the output stream,
								// in case the output file should override the
								// input file.
								in.close();
								in = null;

								if (outputFilename == null) {
									out = new OutputStreamWriter(System.out,
											charset);
								} else {
									out = new OutputStreamWriter(
											new FileOutputStream(outputFilename),
											charset);
								}

								boolean munge = parser
										.getOptionValue(nomungeOpt) == null;
								boolean preserveAllSemiColons = parser
										.getOptionValue(preserveSemiOpt) != null;
								boolean disableOptimizations = parser
										.getOptionValue(disableOptimizationsOpt) != null;

								compressor.compress(out, linebreakpos, munge,
										verbose, preserveAllSemiColons,
										disableOptimizations);
							} catch (EvaluatorException e) {
								throw new CompressorException(e);
							}

						} else if (type.equalsIgnoreCase("css")) {

							CssCompressor compressor = new CssCompressor(in);

							// Close the input stream first, and then open the
							// output stream,
							// in case the output file should override the input
							// file.
							in.close();
							in = null;

							if (outputFilename == null) {
								out = new OutputStreamWriter(System.out,
										charset);
							} else {
								out = new OutputStreamWriter(
										new FileOutputStream(outputFilename),
										charset);
							}

							compressor.compress(out, linebreakpos);
						}

					} catch (IOException e) {
						throw new CompressorException(e);

					} finally {

						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (CmdLineParser.OptionException e) {
				throw new CompressorException(e);
			}
		}

	}
}
