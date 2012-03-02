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
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PluginAction;

@SuppressWarnings("restriction")
public class FolderCompressorAction implements IObjectActionDelegate {

	private Shell shell;
	private ICompressor compressor;

	/**
	 * 导出文件夹
	 */
	private String outFolder;
	/**
	 * 压缩工具的选择:yuicompressor;closure-compiler
	 */
	private String compressorType;
	/**
	 * Javascript压缩开关
	 */
	private boolean switch_javascript;
	/**
	 * CSS压缩开关
	 */
	private boolean switch_css;
	/**
	 * 是否在导出的文件名中显示min标记
	 */
	private boolean min_symbol;

	public FolderCompressorAction() {
		super();
	}

	@Override
	public void run(IAction action) {
		DirectoryDialog d = new DirectoryDialog(shell);
		outFolder = d.open();
		if (outFolder != null) {
			ConsoleUtils.info("Compress starting...");
			// 加载preferences配置
			initPreferencesSetting();

			if ((action instanceof PluginAction)) {
				PluginAction opAction = (PluginAction) action;
				ISelection selection = opAction.getSelection();
				if ((selection instanceof TreeSelection)) {
					Object element = ((TreeSelection) selection)
							.getFirstElement();
					if (element instanceof IResource) {
						IResource resouce = (IResource) element;
						handleResource(resouce, "");
					}
				}
			}
			ConsoleUtils.info("Compress end.");
		}
	}

	private void handleResource(IResource resource, String path) {
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
				fullOutPath = outFolder + path;
			} else {
				String suffix = path.substring(path.lastIndexOf("."));
				fullOutPath = outFolder
						+ path.substring(0, path.lastIndexOf(suffix)) + ".min"
						+ suffix;
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
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	private void initPreferencesSetting() {
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

}
