package org.dimensinfin.android.mvc.exception;

import java.text.MessageFormat;

public enum MVCErrorInfo {
	NULL_PARAMETER_EXCEPTION( "The required {0} parameter was found null.",
			ExceptionTypes.INVALID_PARAMETER_EXCEPTION ),
	UNREGISTERED_TARGET_ACTIVITY( "The selected page target code of {0} does not match any registered page activity.",
			ExceptionTypes.ACTIVITY_NOTREGISTERED );

	private String message;
	private ExceptionTypes exceptionType;

	MVCErrorInfo( final String message, final ExceptionTypes exceptionType ) {
		this.message = message;
		this.exceptionType = exceptionType;
	}

	public MVCException generateException( final String... arguments ) {
		final String content = MessageFormat.format( this.message,  arguments );
		return this.generator( content );
	}

	private MVCException generator( final String message ) {
		switch (this.exceptionType) {
			case INVALID_PARAMETER_EXCEPTION:
				return new InvalidParameterException( message );
			case ACTIVITY_NOTREGISTERED:
				return new ActivityNotRegisteredException( message );
			default:
				return new NotFoundException( message );
		}
	}
}
