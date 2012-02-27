package me.oncereply.jscompressor.util;

import me.oncereply.jscompressor.Activator;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class LogUtils {

	public static void info(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	public static void error(Throwable exception) {
		error("Unexpected Exception", exception);
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.ERROR, message, exception);
	}

	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, Activator.PLUGIN_ID, code, message,
				exception);
	}

	public static void log(IStatus status) {
		ILog log = Activator.getDefault().getLog();
		log.log(status);
	}
}
