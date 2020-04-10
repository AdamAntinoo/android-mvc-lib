package org.dimensinfin.android.mvc.exception;

import org.dimensinfin.core.interfaces.Converter;

public class ExceptionToExceptionReportConverter implements Converter<Exception, ExceptionReport> {

	@Override
	public ExceptionReport convert( final Exception input ) {
		try {
			return new ExceptionReport.Builder( input ).build();
		} catch (final NullPointerException npe) { // Intercept tha case where the original exception generates another exception.
			return new ExceptionReport.Builder( npe ).build();
		}
	}
}
