package me.oncereply.jscompressor.popup.actions;

import java.io.IOException;

import me.oncereply.jscompressor.util.ConsoleUtils;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.internal.PluginAction;

@SuppressWarnings("restriction")
public class JavascriptCompressorAction extends AbstractCompressorAction {

	/**
	 * Constructor for Action1.
	 */
	public JavascriptCompressorAction() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
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
					//->@wjw_add  当添加min后缀时,设置缺省路径为要压缩的js文件所在的目录
					try {
						dialog.setFilterPath(resource.getLocation().toFile().getParentFile().getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					//<-@wjw_add
					
					name = name.substring(0, name.lastIndexOf(".js"))
							+ ".min.js";
				}
				dialog.setFileName(name);
				dialog.setFilterExtensions(new String[] { "js" });
				String path = dialog.open();
				if (path != null) {
					if (!path.endsWith(".js")) {
						path += ".js";
					}
					handleResource(resource, path);
				}
			}
			ConsoleUtils.info("Compress end.");
		}
	}

}
