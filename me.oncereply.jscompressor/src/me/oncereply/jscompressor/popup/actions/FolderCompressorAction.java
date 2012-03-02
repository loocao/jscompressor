package me.oncereply.jscompressor.popup.actions;

import me.oncereply.jscompressor.util.ConsoleUtils;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.internal.PluginAction;

@SuppressWarnings("restriction")
public class FolderCompressorAction extends AbstractCompressorAction {

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
						handleResource(resouce, outFolder);
					}
				}
			}
			ConsoleUtils.info("Compress end.");
		}
	}

}
