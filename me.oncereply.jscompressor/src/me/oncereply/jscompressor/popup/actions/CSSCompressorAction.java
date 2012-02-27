package me.oncereply.jscompressor.popup.actions;

import java.lang.reflect.Method;

import me.oncereply.jscompressor.Activator;
import me.oncereply.jscompressor.util.ConsoleUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PluginAction;

import com.yahoo.platform.yui.compressor.YUICompressor;

@SuppressWarnings("restriction")
public class CSSCompressorAction implements IObjectActionDelegate {
	private Shell shell;

	public CSSCompressorAction() {
	}

	@Override
	public void run(IAction action) {
		PluginAction opAction = (PluginAction) action;
		ISelection selection = opAction.getSelection();
		if ((selection instanceof TreeSelection)) {
			Object element = ((TreeSelection) selection).getFirstElement();
			if (element instanceof IResource) {
				IResource resouce = (IResource) element;
				FileDialog dialog = new FileDialog(shell);
				dialog.setFileName(resouce.getName());
				dialog.setFilterExtensions(new String[] { "css" });
				String path = dialog.open();
				if (path != null) {
					if (!path.endsWith(".css")) {
						path += ".css";
					}
					handleResource(resouce, path);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleResource(IResource resouce, String path) {
		IFile file = (IFile) resouce;
		ConsoleUtils.info("Compress successed: " + path);
		try {
			ClassLoader loader = Activator.getDefault().getClass()
					.getClassLoader();
			shell.getDisplay().getSyncThread();
			Thread.currentThread().setContextClassLoader(loader);
			Class c = loader.loadClass(YUICompressor.class.getName());
			Method main = c.getMethod("main", new Class[] { String[].class });
			String args[] = new String[] {
					file.getLocation().toFile().getAbsolutePath(), "-o", path };
			main.invoke(null, new Object[] { args });
		} catch (Exception e) {
			ConsoleUtils.error("Compress failed: " + path, e);
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
