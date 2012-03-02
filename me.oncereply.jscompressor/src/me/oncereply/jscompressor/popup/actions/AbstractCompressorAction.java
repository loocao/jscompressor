package me.oncereply.jscompressor.popup.actions;

import java.io.File;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.core.CompressorFactory;
import me.oncereply.jscompressor.core.ICompressor;
import me.oncereply.jscompressor.preferences.PreferenceConstants;
import me.oncereply.jscompressor.util.ConsoleUtils;
import me.oncereply.jscompressor.util.FileUtils;
import me.oncereply.jscompressor.util.LogUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractCompressorAction implements IObjectActionDelegate {
	
	protected Shell shell;
	/**
	 * 压缩对象
	 */
	protected ICompressor compressor;
	/**
	 * 导出文件夹
	 */
	protected String outFolder;
	/**
	 * 压缩工具的选择:yuicompressor;closure-compiler
	 */
	protected String compressorType;
	/**
	 * Javascript压缩开关
	 */
	protected boolean switch_javascript;
	/**
	 * CSS压缩开关
	 */
	protected boolean switch_css;
	/**
	 * 是否在导出的文件名中显示min标记
	 */
	protected boolean min_symbol;

	protected void initPreferencesSetting() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		compressorType = store
				.getString(PreferenceConstants.P_CHOICE_COMPRESSOR);
		// 实例压缩对象
		compressor = CompressorFactory.newCompressor(compressorType);
		switch_javascript = store
				.getBoolean(PreferenceConstants.P_BOOLEAN_JAVASCRIPT_SWITCH);
		switch_css = store.getBoolean(PreferenceConstants.P_BOOLEAN_CSS_SWITCH);
		min_symbol = store.getBoolean(PreferenceConstants.P_BOOLEAN_MIN_SYMBOL);
	}

	protected void handleResource(IResource resource, String path) {
		// 如果是文件夹
		if ((resource instanceof IFolder)) {
			IFolder folder = (IFolder) resource;
			try {
				IResource[] resources = folder.members();
				for (IResource r : resources) {
					handleResource(r, path + File.separator + r.getName());
				}
			} catch (CoreException e) {
			}
		} else if (resource instanceof IFile) {
			// 输出文件路径
			String fullOutPath = null;
			// 是否在路径中显示min
			if (!min_symbol) {
				fullOutPath = path;
			} else {
				String suffix = path.substring(path.lastIndexOf("."));
				fullOutPath = path.substring(0, path.lastIndexOf(suffix))
						+ ".min" + suffix;
			}

			File temp = new File(fullOutPath);
			if (temp.exists() || FileUtils.mkdirs(temp.getParentFile())) {
				IFile file = (IFile) resource;
				if (compressor != null) {
					if ((file.getFileExtension().equals("js") && !switch_javascript)
							|| (file.getFileExtension().equals("css") && !switch_css)) {

						String args[] = new String[] {
								file.getLocation().toFile().getAbsolutePath(),
								"-o", fullOutPath };
						try {
							compressor.compress(args);
							ConsoleUtils.info("successed: " + fullOutPath);
						} catch (Exception e) {
							ConsoleUtils.error("failed: " + fullOutPath, e);
						}
					}
				}
			} else {
				// 创建文件不成功
				LogUtils.error("File " + temp.getAbsolutePath()
						+ " compress failed.");
				return;
			}
		}
	}

	@Override
	public abstract void run(IAction action);

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
}
