package me.oncereply.jscompressor.popup.actions;

import java.io.File;
import java.lang.reflect.Method;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.util.FileUtils;
import me.oncereply.jscompressor.util.LogUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PluginAction;

import com.yahoo.platform.yui.compressor.YUICompressor;

@SuppressWarnings("restriction")
public class FolderCompressorAction implements IObjectActionDelegate {

	private Shell shell;
	/**
	 * 导出文件夹
	 */
	private String outFolder;

	public FolderCompressorAction() {
		super();
	}

	@Override
	public void run(IAction action) {
		DirectoryDialog d = new DirectoryDialog(shell);
		outFolder = d.open();
		if (outFolder != null) {
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
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
			IFile file = (IFile) resource;
			// 输出文件路径
			String fullOutPath = outFolder + path;
			File temp = new File(fullOutPath);
			if (!temp.exists() && !FileUtils.mkdirs(temp.getParentFile())) {
				// 创建文件不成功
				LogUtils.error("File " + temp.getAbsolutePath()
						+ " compress failed.");
				return;
			}
			// 如果是javascript文件
			if (file.getFileExtension().equals("js")) {
				try {
					ClassLoader loader = Activator.getDefault().getClass()
							.getClassLoader();
					shell.getDisplay().getSyncThread();
					Thread.currentThread().setContextClassLoader(loader);
					Class c = loader.loadClass(YUICompressor.class.getName());
					Method main = c.getMethod("main",
							new Class[] { String[].class });
					String args[] = new String[] {
							file.getLocation().toFile().getAbsolutePath(),
							"-o", fullOutPath };
					main.invoke(null, new Object[] { args });

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (file.getFileExtension().equals("css")) {// 如果是css文件

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

}
