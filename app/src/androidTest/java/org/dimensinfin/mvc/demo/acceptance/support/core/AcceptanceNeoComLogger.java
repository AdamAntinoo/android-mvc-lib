package org.dimensinfin.mvc.demo.acceptance.support.core;

import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;

public class AcceptanceNeoComLogger extends LoggerWrapper {
	protected AcceptanceNeoComLogger() {}

	public static void info( final String message ) {
		LoggerWrapper.info( "ACCEPTANCE-->" + message );
	}
}
