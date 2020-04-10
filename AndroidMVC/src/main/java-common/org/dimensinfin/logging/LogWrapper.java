package org.dimensinfin.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWrapper {
	private static final Logger logger = LoggerFactory.getLogger( LogWrapper.class );

	public static void info( final String message ) {
		final String header = header();
		logger.info( "-- {}{}", header, message );
	}

	public static void info( final String message, final Exception exception ) {
		final String header = header();
		final String exceptionMessage = exception.getMessage();
		logger.info( "-- {}> {}-{}", header, message, exceptionMessage );
	}

	@Deprecated
	public static void info( final String message, String... arguments ) {
		final String headerMessage = header();
		logger.info( "-- {}> {}", headerMessage, message, arguments );
	}

	public static void enter() {
		logger.info( ">> {}", header() );
	}

	public static void enter( final String message, String... arguments ) {
		logger.info( ">> " + header() + "> " + message, arguments );
	}

	public static void exit() {
		logger.info( "<< {}", header() );
	}

	public static void exit( final String message, String... arguments ) {
		logger.info( "<< " + header() + message, arguments );
	}

	public static void error( final Exception exception ) {
		final String headerMessage = header();
		logger.error( ">E {}x {}", headerMessage, exception.getMessage() );
		final String trace = defaultExceptionLogAction( exception );
		logger.debug( trace );
	}

	public static void error( final String message, final Exception exception ) {
		final String headerMessage = header();
		logger.error( ">E {}x {}-{}", headerMessage, message, exception.getMessage() );
		final String trace = defaultExceptionLogAction( exception );
		logger.debug( trace );
	}

	public static String defaultExceptionLogAction( final Exception exception ) {
		final StackTraceElement[] elements = exception.getStackTrace();
		String stack = "";
		for (int i = 0; i < elements.length; i++) {
			stack = stack.concat( "className: " ).concat( elements[i].getClassName() );
			stack = stack.concat( "methodName: " ).concat( elements[i].getMethodName() );
			stack = stack.concat( "fileName: " ).concat( elements[i].getFileName() );
			stack = stack.concat( "lineNumber: " ).concat( Integer.toString( elements[i].getLineNumber() ) );
			stack = stack.concat( "\n" );
		}
		return stack;
	}

	private static String header() {
		return wrapper( generateCaller() );
	}

	private static String wrapper( final String data ) {
		return "[" + data + "]";
	}

	private static String generateCaller() {
		StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		int androidDisplacement = 0;
		if (traceElements[0].getMethodName().equalsIgnoreCase( "getThreadStackTrace" ))
			androidDisplacement = 1;
		final StackTraceElement element = traceElements[4 + androidDisplacement];
		return element.getClassName().substring( element.getClassName().lastIndexOf( '.' ) + 1 ) + "." +
				       element.getMethodName();
	}

	protected LogWrapper() {}
}
