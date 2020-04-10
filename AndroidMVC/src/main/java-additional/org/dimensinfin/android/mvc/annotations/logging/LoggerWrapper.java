package org.dimensinfin.android.mvc.annotations.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerWrapper {
	private static final Logger logger = LoggerFactory.getLogger( LoggerWrapper.class );

	public static void info( final String message ) {
		final String header = header();
		logger.info( "-- {}{}", header, message );
	}

	public static void info( final String message, final Exception exception ) {
		final String header = header();
		final String exceptionMessage = exception.getMessage();
		logger.info( "-- {}> {}-{}", header, message, exceptionMessage );
	}

	public static void info( final String message, String... arguments ) {
		logger.info( "-- " + header() + "> " + message, arguments );
	}

	public static void enter() {
		logger.info( ">> " + header() );
	}

	public static void enter( final String message, String... arguments ) {
		logger.info( ">> " + header() + "> " + message, arguments );
	}

	public static void exit() {
		logger.info( "<< " + header() );
	}

	public static void exit( final String message, String... arguments ) {
		logger.info( "<< " + header() + message, arguments );
	}

	public static void error( final Exception exception ) {
		logger.error( ">E " + header() + exception.getMessage() );
		logger.debug( defaultExceptionLogAction( exception ) );
	}

	public static void error( final String message, final Exception exception ) {
		logger.error( ">E " + header() + message + "-" + exception.getMessage() );
		logger.debug( defaultExceptionLogAction( exception ) );
	}

//	public static String toJSON( final Object target ) {
//		return new Gson().toJson( target );
//	}

	public static String defaultExceptionLogAction( final Exception exception ) {
		final StackTraceElement elements[] = exception.getStackTrace();
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

	protected LoggerWrapper() {}
}
