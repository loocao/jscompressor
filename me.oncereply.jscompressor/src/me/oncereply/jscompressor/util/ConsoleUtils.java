package me.oncereply.jscompressor.util;

import java.io.PrintStream;

import me.oncereply.jscompressor.Activator;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleUtils {

	private final static String CONSOLE_NAME = "JavaScript Compressor";
	private static MessageConsole myConsole;
	private static MessageConsoleStream out;
	static {
		myConsole = findConsole(CONSOLE_NAME);
		out = myConsole.newMessageStream();
	}

	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

	public static void info(String message) {
		out.setColor(Activator.getDefault().getWorkbench().getDisplay()
				.getSystemColor(SWT.COLOR_BLACK));
		out.println(message);
		activeConsole();
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable e) {
		out.setColor(Activator.getDefault().getWorkbench().getDisplay()
				.getSystemColor(SWT.COLOR_RED));
		out.println(message);
		if (e != null) {
			e.printStackTrace(new PrintStream(out));
		}
		activeConsole();
	}
	
	private static void activeConsole() {
		IWorkbenchPage page = Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		String id = IConsoleConstants.ID_CONSOLE_VIEW;
		try {
			IConsoleView view = (IConsoleView) page.showView(id);
			if (view.getConsole() != myConsole) {
				view.display(myConsole);
			}
		} catch (PartInitException e) {
		}
	}
}
