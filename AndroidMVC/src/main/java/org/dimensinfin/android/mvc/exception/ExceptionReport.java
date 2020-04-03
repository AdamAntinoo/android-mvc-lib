package org.dimensinfin.android.mvc.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.core.interfaces.ICollaboration;

public class ExceptionReport implements ICollaboration {
	private Exception exceptionDelegate;
	private String exceptionClass;
	private String exceptionMethodName;

	private ExceptionReport( final Exception exceptionDelegate ) {
		this.exceptionDelegate = exceptionDelegate;
	}

	public String getExceptionCode() {
		return this.exceptionDelegate.getClass().getSimpleName();
	}

	public String getMessage() {
		return this.exceptionDelegate.getMessage();
	}

	public String getExceptionClass() {
		return this.exceptionClass;
	}

	public String getExceptionMethodName() {
		return this.exceptionMethodName;
	}

	// - I C O L L A B O R A T I O N
	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object target ) {
		if (target instanceof ExceptionReport)
			return this.exceptionClass.compareTo( ((ExceptionReport) target).exceptionClass );
		else return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private ExceptionReport onConstruction;

		public Builder( final Exception exception ) {
			this.onConstruction = new ExceptionReport( exception );
		}

		public ExceptionReport build() {
			Objects.requireNonNull( this.onConstruction.exceptionDelegate );
			final StackTraceElement element = this.getCallerDataFromStack( this.onConstruction.exceptionDelegate );
			this.onConstruction.exceptionClass = element.getClassName();
			this.onConstruction.exceptionMethodName = element.getMethodName();
			return this.onConstruction;
		}

		private StackTraceElement getCallerDataFromStack( final Exception exception ) {
			final StackTraceElement[] stack = exception.getStackTrace();
			if (stack.length > 0) return stack[0];
			else return new StackTraceElement( "ExceptionReport",
					"getCallerDataFromStack",
					"ExceptionReport", 1 );
		}
	}
}
