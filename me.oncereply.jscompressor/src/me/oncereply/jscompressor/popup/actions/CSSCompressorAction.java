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
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PluginAction;

@SuppressWarnings("restriction")
public class CSSCompressorAction implements IObjectActionDelegate {
	private Shell shell;
	private String compressorType;
	private ICompressor compressor;
	private boolean min_symbol;

	public CSSCompressorAction() {
	}

	@Override
	public void run(IAction action) {
		PluginAction opAction = (PluginAction) action;
		ISelection selection = opAction.getSelection();
		if ((selection instanceof TreeSelection)) {
			ConsoleUtils.info("Compress starting...");
			// 加载preferences配置
			initPreferencesSetting();

			Object element = ((TreeSelection) selection).getFirstElement();
			if (element instanceof IResource) {
				IResource resource = (IResource) element;
				FileDialog dialog = new FileDialog(shell);
				String name = resource.getName();
				if (min_symbol) {
					name = name.substring(0, name.lastIndexOf(".css"))
							+ ".min.css";
				}
				dialog.setFileName(name);
				dialog.setFilterExtensions(new String[] { "css" });
				String path = dialog.open();
				if (path != null) {
					if (!path.endsWith(".css")) {
						path += ".css";
					}
					handleResource(resource, path);
				}
			}
			ConsoleUtils.info("Compress end.");
		}
	}

	private void initPreferencesSetting() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		compressorType = store
				.getString(PreferenceConstants.P_CHOICE_COMPRESSOR);
		// 实例压缩对象
		compressor = CompressorFactory.newCompressor(compressorType);
		min_symbol = store.getBoolean(PreferenceConstants.P_BOOLEAN_MIN_SYMBOL);
	}

	private void handleResource(IResource resource, String fullOutPath) {
		// 输出文件路径
		File temp = new File(fullOutPath);
		if (temp.exists() || FileUtils.mkdirs(temp.getParentFile())) {
			IFile file = (IFile) resource;
			if (compressor != null) {
				String args[] = new String[] {
						file.getLocation().toFile().getAbsolutePath(), "-o",
						fullOutPath };
				try {
					compressor.compress(args);
					ConsoleUtils.info("successed: " + fullOutPath);
				} catch (Exception e) {
					ConsoleUtils.error("failed: " + fullOutPath, e);
				}
			}
		} else {
			// 创建文件不成功
			LogUtils.error("File " + temp.getAbsolutePath()
					+ " compress failed.");
			return;
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
