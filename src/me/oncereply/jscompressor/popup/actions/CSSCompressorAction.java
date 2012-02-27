package me.oncereply.jscompressor.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class CSSCompressorAction implements IObjectActionDelegate {
	private Shell shell;

	public CSSCompressorAction() {
	}

	@Override
	public void run(IAction action) {
		//TODO CSS Compressor
		MessageDialog.openInformation(shell, "jscompressor",
				"Sorry,but this feature is comming soon.");
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
